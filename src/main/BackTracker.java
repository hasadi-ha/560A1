package main;

import bean.State;
import constants.Constants;

public class BackTracker {
	
	public void startColoring() {
		if (!colorState(Constants.STATES.get(0), 0)) {
			System.out.println("No solution");
			return;
		} else {
			for (State state : Constants.STATES) {
				System.out.println(state.getName() + "\t" + state.printColor());
			}
		}
        
	}
	
	
	public boolean colorState(State current, int currentIndex) {
		if (currentIndex == Constants.STATES.size())
			return true;
		
		for (int i = 0; i < Constants.COLORS.size(); i++) {
			current.assignColor(Constants.COLORS.get(i));
			if (current.goodColoringChoice()) {
				if (currentIndex == Constants.STATES.size()-1) {
					return true;
				}
				if (colorState(Constants.STATES.get(currentIndex+1), currentIndex+1)) {
					return true;
				}
			}
			current.removeColor();
			
		}
		return false;
	}

}
