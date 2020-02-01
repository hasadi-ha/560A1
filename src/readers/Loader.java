package readers;

import java.util.ArrayList;
import java.util.List;

import bean.Color;
import bean.State;
import constants.Constants;

public class Loader {
	
	/**
	 * Creates the graph and all connections
	 * @param ary is a list with <List of colors, list of states, list of adjacent states>
	 * @return the list of states
	 */
	public List<State> makeGraph(List<List<String>> ary, boolean random) {
		List<State> states = loadStates(ary.get(1), random);
		
		for (String stateConnection : ary.get(2)) {
			String [] stateLine = stateConnection.split(" ");
			State parentState = new State(stateLine[0], random);
			State childState =  new State(stateLine[1], random);
			
			State one = new State(" ", random);
			State two = new State(" ", random);
			
			for (State s : states)  {
				if (s.same(parentState)) {
					one = s;
				} else if (s.same(childState)) {
					two = s;
				}
			}
			
			one.addConnection(two);
			two.addConnection(one);
		}
		
		return states;
		
	}
	
	/**
	 * Given a list of String states, converts to type State
	 * @param states list of String states
	 * @return list of State states
	 */
	public List<State> loadStates(List<String> states, boolean random) {
		List<State> s = new ArrayList<>();
		for (String e : states) {
			s.add(new State(e, random));
		}
		return s;
	}
	
	/**
	 * Given a list of String colors, converts to type Color
	 * @param states list of String colors
	 * @return list of Color colors
	 */
	public List<Color> loadColors(List<String> colors) {
		List<Color> cs = new ArrayList<>();
		for (String s : colors) {
			cs.add(new Color(s));
		}
		return cs;
	}
	
	/**
	 * Prints the current graph adjacency matrix
	 */
	public void printGraph() {
		for (State s : Constants.STATES) {
			System.out.println(s.printRow());
		}
		System.out.println();
	}
	
	

}
