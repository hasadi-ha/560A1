package main;

import java.io.IOException;
import java.util.List;

import bean.State;
import readers.Loader;
import readers.TextFileReader;
import constants.Constants;

public class Runner {

	public static void main(String[] args) throws IOException {
		TextFileReader reader = new TextFileReader();
		List<List<String>> ary = reader.getTextFileComponents("UnitedStates.txt");
		Loader loader = new Loader();
		
		Constants.STATES = loader.makeGraph(ary);
		Constants.COLORS = loader.loadColors(ary.get(0));
		
		for (State s : Constants.STATES) {
			System.out.println(s.printRow());
		}
		
		System.out.println();
		
		BackTracker bt = new BackTracker();
		bt.startColoring();

	}

}
