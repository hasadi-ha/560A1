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
	 * Initialize state object
	 * @param name name of the state
	 * @param random true iff you want to initialize with color
	 */
	public State (String name, boolean random) {
		this.name = name;
		this.connectedTo = new ArrayList<>();
		this.color = random ? this.getRandomColor() : Constants.DUMMY_COLOR;		
		this.initColor = this.getColor();
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
	
	/**
	 * Sets color back to dummy color
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
	 * Will automatically ignore if adjacent states all have "dummy" color
	 * @return true if valid coloring choice
	 */
	public boolean goodColoringChoice() {
		for (State s : this.connectedTo) {
			if (s.getColor().same(this.getColor())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks all adjacent nodes and sees thhe number of colors that are the same
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
	
	private Color getRandomColor() {
		int randomIndex = (int)( (Constants.COLORS.size()) * Math.random());
		return Constants.COLORS.get(randomIndex);
	}

}
