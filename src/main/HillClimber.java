package main;

import java.util.LinkedHashSet;

import bean.Color;
import bean.State;
import constants.Constants;

public class HillClimber extends Colorer {
	
	public void color(int mode, int maxSeconds) {
		this.findConflicts();
		if (this.colorStates(maxSeconds)) {
			System.out.println(super.printColoring());
		} else {
			System.out.println("Failed to find coloring in " +  maxSeconds + " seconds");
			System.out.print("The following states had conflicts: ");
			int i = 0;
			for (State s : Constants.CONFLICTS) {
				if (i == Constants.CONFLICTS.size()-1) {
					System.out.print(s.getName());
				} else {
					System.out.print(s.getName() + ", ");
				}
				i++;
			}
		}
		
	}
	
	/**
	 * This method performs basic hill climbing. No priority is given to any states and entire paths aren't checked
	 * Finds conflicts in given coloring of states and attempts to fix them. Does not always work.
	 * Creates a queue of states that have conflicts. If fixes coloring such that there  are less (or equal) conflicts, 
	 * will assign new coloring, reload conflict queue and try again.
	 * Continues until the queue is empty.
	 * @return true iff finds coloring in less than 1 minute
	 */
	private boolean colorStates(int maxSeconds) {
		long startTime = System.currentTimeMillis();
		long trialTime = System.currentTimeMillis();
		while (!Constants.CONFLICTS.isEmpty()) {
			//checks if we're still under time
			if (System.currentTimeMillis() - startTime >= maxSeconds * 1000) {
				return false;
			}
			
			//if it's not working for a while, try setting new colors and try again
			if (System.currentTimeMillis() - trialTime >= 5*1000) {
				System.out.println("Failed to find, resetting colors...");
				resetColors();
				trialTime = System.currentTimeMillis();
				System.out.println("Current Steps were: " + Constants.STEPS);
				System.out.println("Resetting count.");
				Constants.STEPS = 0;
			}
			
			Constants.STEPS++;
			State current = Constants.CONFLICTS.iterator().next();
			Constants.CONFLICTS.remove(current);
			
			//traverses through all the colors and sees if assigning a different color helps reduce conflicts
			//caused by that one state
			for (int i = 0; i < Constants.COLORS.size(); i++) {
				Color oldColor = current.getColor();
				int oldConflicts = current.numConflicts();
				current.assignColor(Constants.COLORS.get(i));
				if (current.numConflicts() >= oldConflicts) {
					current.assignColor(oldColor);
				} 
			}
			this.findConflicts();
		}
		return true;
	}

	/**
	 * Adds states with adjacent coloring conflicts into a list
	 */
	private void findConflicts() {
		if (Constants.CONFLICTS == null) {
			Constants.CONFLICTS = new LinkedHashSet<>();
		}
		for (State s :  Constants.STATES)  {
			if (!s.goodColoringChoice()) {
				Constants.CONFLICTS.add(s);
			}
		}
		
	}
	
	private void resetColors() {
		for (State s : Constants.STATES) {
			s.setRandomColor();
		}
	}
	


}
