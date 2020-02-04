package main;

import java.util.List;

import bean.Color;
import bean.State;
import constants.Constants;

public class BackTracker extends Colorer {
	
	/**
	 * Will start coloring the graph
	 * @param mode where 
	 * (1) is simple backtracking 
	 * (2) is simple forward search, and
	 * (3) is most constrained forward search
	 * @param maxSeconds which  is the max seconds the program should run. Does not affect backtracking algorithm though.
	 */
	public void color(int mode, int maxSeconds) {
		if (mode == 1) {
			if (!simpleColorState(0)) {
				System.out.println("No solution");
				return;
			}
		} else if (mode == 2) {
			if (!simpleForwardTracking(0)) {
				System.out.println("No solution");
				return;
			}
		} else {
			if (!mostConstrainedForwardTracking()) {
				System.out.println("No solution");
				return;
			}
		}
		System.out.println(super.printColoring());
	}

	/**
	 * Checks if any state currently has no valid coloring options when forward checking
	 * @return true iff every state has at least one color it can be colored.
	 */
	private boolean isPathValid() {
		for (State s : Constants.STATES) {
			if (s.numberOfPossibleColors() == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * traverse through the colors and assign first color. See if it's a good
	 * coloring choice by checking adjacent states. If it is, go to next state
	 * in the list and color that.
	 * 
	 * @param currentIndex
	 *            is the current part of the list of states we are at.
	 * @return false when you've exhausted all the colors in the list.
	 */
	private boolean simpleColorState(int currentIndex) {		
		State current = Constants.STATES.get(currentIndex);
		Constants.STEPS++;
		
		//traverses through all the possible colors
		for (int i = 0; i < Constants.COLORS.size(); i++) {
			//assign color to state
			current.assignColor(Constants.COLORS.get(i));
			if (current.goodColoringChoice()) {
				//if good coloring choice, start coloring the next state. Stop when at the end of the list
				if (currentIndex == Constants.STATES.size() - 1 || simpleColorState(currentIndex + 1)) {
					return true;
				}
			}
			//if not a good coloring choice, removes the color
			current.removeColor();
		}
		return false;
	}
	
	/**
	 * Goes through each state in the order of appearance in the states file
	 * Starts coloring each state
	 * Checks if there's a conflict based on given coloring
	 * If so, goes back and removes that color and tries the next one
	 * @param currentIndex the currentIndex of the States array
	 * @return true iff a valid coloring for every state was able to be found
	 */
	private boolean simpleForwardTracking(int currentIndex) {
		State current = Constants.STATES.get(currentIndex);
		List<Color> availableColors = current.getAvailableColors();
		Constants.STEPS++;
		for (int i = 0; i < availableColors.size(); i++) {
			current.assignColor(availableColors.get(i));
			current.disableColors();
			if (isPathValid()) {
				if (currentIndex == Constants.STATES.size() - 1 || simpleForwardTracking(currentIndex + 1)) {
					return true;
				}
			}
			current.reEnableColors();
			current.removeColor();
		}
		return false;
	}
	
	/**
	 * Goes through every state in the file, in order of most constrained state (A state is defined as being
	 * most constrained iff it has the least number of color options).
	 * While there is a most constrained state that hasn't been colored yet, it goes through and assigns the state a 
	 * valid color. If the color results in an invalid path, the color is removed and the next color is tried.
	 * @return true iff a valid coloring could be found for every state in the graph
	 */
	private boolean mostConstrainedForwardTracking() {
		//gets most constrained state. If null returned, all states have been colored
		State current = this.getMostConstrainedState();
		if (current == null) {
			return true;
		}
		List<Color> availableColors = current.getAvailableColors();
		Constants.STEPS++;
		
		//traverses through available colors, if it leads to an invalid path, tries next color
		//otherwise, continues coloring states
		for (int i = 0; i < availableColors.size(); i++) {
			current.assignColor(availableColors.get(i));
			current.disableColors();
			if (isPathValid()) {
				return mostConstrainedForwardTracking();
			}
			current.reEnableColors();
			current.removeColor();
		}
		return false;
	}

	/**
	 * Doing bubble sort on the array (sorry) to sort it from most constrained
	 * to least constrained. Then it returns the state that should be analyzed.
	 * @return State to be analyzed next, will return null if not available. Be sure to do a null check
	 */
	private State getMostConstrainedState() {
		List<State> sorted = Constants.STATES;
		
		//sorts the states according to number of possible colors (so most constrained to least constrained)
		for (int i = 0; i < sorted.size() - 1; i++) {
			State current = sorted.get(i);
			State next = sorted.get(i + 1);
			if (next.numberOfPossibleColors() < current.numberOfPossibleColors()) {
				sorted.set(i, next);
				sorted.set(i + 1, current);
			}
		}

		//gets the first state in the sorted list that hasn't been colored
		for (int i = 0; i < sorted.size(); i++) {
			State current = sorted.get(i);
			if (!current.hasColor()) {
				return current;
			}
		}
		
		//returns null if all the states have been colored
		return null;
	}

}
