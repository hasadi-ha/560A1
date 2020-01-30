package main;

import bean.State;
import constants.Constants;

public class BackTracker {
	
	/**
	 * Start simple backtracking method to color graph.
	 */
	public void startColoring() {
		//while there's a valid option, traverse through states starting at beginning of list.
		if (!colorState(0)) {
			System.out.println("No solution");
		} else {
			System.out.println(printColoring());
		}
        
	}
	
	/**
	 * Prints the state and the appropriate coloring
	 * @return the coloring
	 */
	public String printColoring() {
		StringBuilder builder = new StringBuilder();
		for (State state : Constants.STATES) {
			builder.append(state.printColor() + "\n");
		}
		builder.append(Constants.STEPS + "\n");
		return builder.toString();
	}
	
	/**
	 * traverse through the colors and assign first color. See if it's a good coloring choice by
	 * checking adjacent states. If it is, go to next state in the list and color that.
	 * @param currentIndex is the current part of the list of states we are at.
	 * @return false when you've exhausted all the colors in the list.
	 */
	public boolean colorState(int currentIndex) {
		State current = Constants.STATES.get(currentIndex);
		Constants.STEPS ++;
		for (int i = 0; i < Constants.COLORS.size(); i++) {
			current.assignColor(Constants.COLORS.get(i));
			if (current.goodColoringChoice()) {
				if (currentIndex == Constants.STATES.size()-1 || colorState(currentIndex+1)) {
					return true;
				}
			}
			current.removeColor();
		}
		return false;
	}

}
