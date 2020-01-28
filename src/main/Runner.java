package main;

import java.io.IOException;
import java.util.List;

import constants.Constants;
import readers.Loader;
import readers.TextFileReader;

public class Runner {

	public static void main(String[] args) throws IOException {
		//read text file
		TextFileReader reader = new TextFileReader();
		List<List<String>> ary = reader.getTextFileComponents("Australia.txt");
		Loader loader = new Loader();
		
		//store states and colors (assuming static class)
		Constants.STATES = loader.makeGraph(ary);
		Constants.COLORS = loader.loadColors(ary.get(0));
		
		//print graph before coloring
		loader.printGraph();
		
		//start coloring through simple backtracking
		BackTracker simpleBackTracker = new BackTracker();
		simpleBackTracker.startColoring();

	}
	
	

}
