package readers;

import java.util.ArrayList;
import java.util.List;

import bean.Color;
import bean.State;

public class Loader {
	
	public List<State> makeGraph(List<List<String>> ary) {
		List<State> states = loadStates(ary.get(1));
		
		for (String stateConnection : ary.get(2)) {
			String [] stateLine = stateConnection.split(" ");
			State parentState = new State(stateLine[0]);
			State childState =  new State(stateLine[1]);
			
			State one = new State(" ");
			State two = new State(" ");
			
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
	
	public List<State> loadStates(List<String> states) {
		List<State> s = new ArrayList<>();
		for (String e : states) {
			s.add(new State(e));
		}
		return s;
	}
	
	public List<Color> loadColors(List<String> colors) {
		List<Color> cs = new ArrayList<>();
		for (String s : colors) {
			cs.add(new Color(s));
		}
		return cs;
	}
	
	

}
