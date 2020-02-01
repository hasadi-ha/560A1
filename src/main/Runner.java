package main;

import java.io.IOException;
import java.util.List;

import constants.Constants;
import readers.Loader;
import readers.TextFileReader;

public class Runner {
	
	//set up parameters here
	public static final String FILE_NAME = "UnitedStates.txt";
	//set true for local search, false for backtracking
	public static final boolean HILL_CLIMBING = true;

	public static void main(String[] args) throws IOException {
		//read text file
		TextFileReader reader = new TextFileReader();
		List<List<String>> ary = reader.getTextFileComponents(FILE_NAME);
		Loader loader = new Loader();
		
		//store states and colors (assuming static class)
		Constants.COLORS = loader.loadColors(ary.get(0));
		Constants.STATES = loader.makeGraph(ary, HILL_CLIMBING);
		
		//print graph before coloring
		loader.printGraph();
		
		Colorer graphColorer = new GraphColoringFactory(HILL_CLIMBING).getColorer();
		graphColorer.startColoring();
		
	}
	
	

}
