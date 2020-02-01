package main;

import bean.State;
import constants.Constants;

public abstract class Colorer {
	
	/**
	 * Prints the state and the appropriate coloring
	 * @return the coloring
	 */
	public String printColoring() {
		StringBuilder builder = new StringBuilder();
		for (State state : Constants.STATES) {
			builder.append(state.printColor() + "\n");
		}
		builder.append("\nNumber of steps: " + Constants.STEPS + "\n");
		return builder.toString();
	}
	
	public abstract void startColoring();

}
