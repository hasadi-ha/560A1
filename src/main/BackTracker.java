package main;

import bean.State;
import constants.Constants;

public class BackTracker {
	
	public void startColoring() {
		for (State state : Constants.STATES) {
			state.assignColor(Constants.COLORS.get(Constants.INDEX));
			Constants.INDEX++;
		}
	}

}
