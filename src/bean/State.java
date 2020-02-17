package bean;

import java.util.ArrayList;
import java.util.List;

import constants.Constants;

public class State {
	
	private List<State> connectedTo;
	private String name;
	private Color color;
	private Color initColor;
	
	/** 
	 * This is an unofficial list of colors that the State can currently be assigned based on the current 
	 * assignment of colors of the other states. This is only accessed and relevant in the backtracking methods
	 * with the forward checking
	 */
	private List<BooleanColor> allowedColors;

	/**
	 * Initialize state object
	 * @param name name of the state
	 * @param random true iff you want to initialize with color
	 */
	public State (String name, boolean random) {
		this.name = name;
		this.connectedTo = new ArrayList<>();
		this.color = random ? this.getRandomColor() : Constants.DUMMY_COLOR;		
		this.initColor = this.getColor();
		this.allowedColors = this.convertColorToBoolean();
	}
	

	public void assignColor(Color color) {
		this.color = color;
	}
	
	public void addConnection(State s) {
		connectedTo.add(s);
	}
	
	public List<State> getConnectedTo() {
		return connectedTo;
	}
	
	public String getName() {
		return name;
	}
	
	public Color getColor() {
		return color;
	}
	
	public List<BooleanColor> getBooleanColors() {
		return this.allowedColors;
	}
	
	public void setRandomColor() {
		Color randomColor = this.getRandomColor();
		this.initColor = randomColor;
		this.color = randomColor;
	}
	
	/**
	 * Sets color back to dummy color (or if was assigned random color, the initial random color)
	 */
	public void removeColor() {
		this.color = this.initColor;
	}
	
	/**
	 * @return state name and color
	 */
	public String printColor() {
		return this.getName()  + "\t" + this.color.getColor();
	}

	/**
	 * Prints current state and all the states that are adjacent to it.
	 * @return Current state and all adjacent states
	 */
	public String printRow() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getName()).append("\t -> \t");
		for (State s : connectedTo)  {
			builder.append(s.getName()).append("\t");
		}
		return builder.toString();
	}
	
	/**
	 * Checks if state name is the same as other state name
	 * @param other is the state being comapared to
	 * @return true if and only if this state is the same as the other one
	 */
	public boolean same(State other) {
		if (this.getName().equals(other.getName())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks all adjacent nodes and sees if any of the colors are the same. 
	 * *NOTE* Only checks for adjacent and immediate conflicts
	 * Will automatically ignore if current state has a dummy color
	 * @return true if valid coloring choice
	 */
	public boolean goodColoringChoice() {
		if (!this.hasColor()) {
			return true;
		}
		for (State s : this.connectedTo) {
			if (s.getColor().same(this.getColor())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks all adjacent nodes and sees the number of colors that are the same
	 * @return the integer number of conflicts
	 */
	public int numConflicts() {
		int count =  0;
		for (State s : this.connectedTo) {
			if (s.getColor().same(this.getColor())) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * @return a list of all the colors that can currently be assigned to this state
	 */
	public List<Color> getAvailableColors() {
		List <Color> available = new ArrayList<>();
		for (BooleanColor bc : this.allowedColors) {
			if (bc.isAvailable()) {
				available.add(new Color(bc.getColor()));
			}
		}
		return available;
	}
	
	/**
	 * If current state is now assigned a color, checks all adjacent states and disables that color as a color choice
	 * Ex. If this is now red, all of the states it's connected to can no longer be red.
	 */
	public void disableColors() {
		for (State s : this.connectedTo) {
			for (BooleanColor c : s.getBooleanColors()) {
				if (c.same(this.getColor())) {
					c.disable();
				}
			}
		}
	}
	
	/**
	 * If we find that a coloring choice was a mistake, we want to enable all the colors that we had deleted as valid colors.
	 * Ex. If we labelled this state as red, and disabled all the adjacent states from having red as an option, 
	 * but then we found that it was an invalid path, then we want to reenable all the adjacent states to be allowed
	 * to be red again. Helpful in the backtracking before removing the color
	 */
	public void reEnableColors() {
		for (State s : this.connectedTo) {
			for (BooleanColor c : s.getBooleanColors()) {
				if (c.same(this.getColor())) {
					c.enable();
				}
			}
		}
	}
	
	public int numberOfPossibleColors() {
		return this.getAvailableColors().size();
	}
	
	public boolean hasColor() {
		return !this.color.same(Constants.DUMMY_COLOR);
	}
	
	private Color getRandomColor() {
		int randomIndex = (int)( (Constants.COLORS.size()) * Math.random());
		return Constants.COLORS.get(randomIndex);
	}
	
	/**
	 * Helpful to initializing our State. If we're going to use forward checking, then we want to maintain a list of the 
	 * allowable colors.
	 * @return list of colors that can be enabled or disabled depending on the current graph coloring
	 */
	private List<BooleanColor> convertColorToBoolean() {
		List<BooleanColor> booleans = new ArrayList<>();
		for (Color c : Constants.COLORS) {
			booleans.add(new BooleanColor(c.getColor()));
		}
		return booleans;
	}
	

}
