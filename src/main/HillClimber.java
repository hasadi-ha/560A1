package main;

import java.util.LinkedHashSet;

import bean.Color;
import bean.State;
import constants.Constants;

public class HillClimber extends Colorer {
	
	public void startColoring() {
		this.findConflicts();
		if (this.colorStates()) {
			System.out.println(super.printColoring());
		} else {
			System.out.println("Failed to find coloring in 1 minute");
		}
		
	}
	
	/**
	 * Finds conflicts in given coloring of states and attempts to fix them. Does not always work.
	 * Creates a queue of states that have conflicts. If fixes coloring such that there  are less (or equal) conflicts, 
	 * will assign new coloring, reload conflict queue and try again.
	 * Continues until the queue is empty.
	 * @return true iff finds coloring in less than 1 minute
	 */
	private boolean colorStates() {
		long startTime = System.currentTimeMillis();
		while (!Constants.CONFLICTS.isEmpty()) {
			if (System.currentTimeMillis() - startTime >= 60000) {
				return false;
			}			
			Constants.STEPS++;
			State current = Constants.CONFLICTS.iterator().next();
			Constants.CONFLICTS.remove(current);
			
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
	


}
