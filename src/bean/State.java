package bean;

import java.util.ArrayList;
import java.util.List;

import constants.Constants;

public class State {
	
	private State connectedFrom;
	private List<State> connectedTo;
	private String name;
	private Color color;
	
	public State(String name) {
		this.name = name;
		this.connectedTo = new ArrayList<>();
		this.color = Constants.DUMMY_COLOR;
	}
	
	public void assignColor(Color color) {
		this.color = color;
	}
	
	public void addConnection(State s) {
		connectedTo.add(s);
	}
	
	public void addConnectedFrom(State s) {
		this.connectedFrom = s;
	}
	
	public List<State> getConnectedTo() {
		return connectedTo;
	}
	
	public State getConnectedFrom() {
		return this.connectedFrom;
	}
	
	public String getName() {
		return name;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void removeColor() {
		this.color = Constants.DUMMY_COLOR;
	}
	
	
	public void setChildrenColors() {
		for (State c : this.getConnectedTo()) {
			c.addConnectedFrom(this);
			if (c.getColor().same(Constants.DUMMY_COLOR)) {
				
				c.assignColor(Constants.COLORS.get(0));
				if (this.goodColoringChoice()) {
					c.setChildrenColors();
				} else {
					
				}
			}
		}
		
	}
	
	public String printRow() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getName()).append("\t -> \t");
		for (State s : connectedTo)  {
			builder.append(s.getName()).append("\t");
		}
		return builder.toString();
	}
	
	public boolean same(State other) {
		if (this.getName().equals(other.getName())) {
			return true;
		}
		return false;
	}
	
	public boolean goodColoringChoice() {
		for (State s : this.connectedTo) {
			if (s.getColor().same(this.getColor())) {
				return false;
			}
		}
		return true;
	}

}
