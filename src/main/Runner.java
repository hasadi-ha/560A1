package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

import constants.Constants;
import readers.Loader;
import readers.TextFileReader;

public class Runner {
	
	//set up parameters here
	public static String fileName = "UnitedStates.txt";
	//set true for local search, false for backtracking
	public static boolean hillClimbing = false;
	//set to how many seconds max you want to run it for
	public static int maxSeconds = 60;
	//set which mode for the backtracking
	public static int mode = 1;
	
	//checking if first time running, if so, print graph
	public static boolean first = true;

	public static void main(String[] args) throws IOException {
		getUserInputs();
	}
	
	public static void run(boolean print) throws IOException {
		loadAndBuildGraphs();
		startColoring(print);
	}
	
	/**
	 * Resets all the counters in the Constants file
	 * Yeah we know this isn't good Java practice, it was just easier in the beginning
	 */
	public static void reset() {
		Constants.COLORS = new ArrayList<>();
		Constants.STATES = new ArrayList<>();
		Constants.CONFLICTS = new LinkedHashSet<>();
		Constants.INDEX = 0;
		Constants.STEPS = 0;
	}
	
	/**
	 * Reads the text file and loads the state objects with links to its connections
	 * Remember that this is an undirected graph
	 * @throws IOException
	 */
	public static void loadAndBuildGraphs() throws IOException {
		//read text file
		TextFileReader reader = new TextFileReader();
		List<List<String>> ary = reader.getTextFileComponents(fileName);
		Loader loader = new Loader();

		//store states and colors (assuming static class)
		Constants.COLORS = loader.loadColors(ary.get(0));
		Constants.STATES = loader.makeGraph(ary, hillClimbing);

		//print graph before coloring if first pass
		if (first)
			loader.printGraph();
		first = false;
	}
	
	/**
	 * Gets the appropriate graphColorer (HillClimber or BackTracker) and then starts coloring the graph
	 * @param print
	 */
	public static void startColoring(boolean print) {
		if (print) {
			String output = hillClimbing ? "Starting Hill Climbing..." : "Starting Backtracking...";
			System.out.println(output +"\n\n");
		}
		Colorer graphColorer = new GraphColoringFactory(hillClimbing).getColorer();
		graphColorer.color(mode, maxSeconds);
	}
	
	/**
	 * Method that gets all the user inputs and then runs the approriate method. No algorithm or anything here.
	 * @throws IOException
	 */
	public static void getUserInputs() throws IOException {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the text file with states, colors, and links");
		fileName = input.nextLine();
		System.out.println("Do you want to use local search? It might not color the graph, but if it does, it'll be fast (y/n). \n"
				+ "@ Andy: Write b to run both.");
		if (input.nextLine().equalsIgnoreCase("b")) {
			//running all the algorithms written
			hillClimbing = false;
			mode = 1;
			run(true);
			mode = 2;
			reset();
			System.out.println("Backtracking with forward tracking...\n\n");
			run(false);
			mode = 3;
			reset();
			System.out.println("Backtracking with forward tracking and most constrained variable...\n\n");
			run(false);
			hillClimbing = true;
			reset();
			System.out.println("Hill Climbing...\n\n");
			run(false);
		}
		else {
			hillClimbing = input.nextLine().equalsIgnoreCase("y");
			if (hillClimbing) {
				System.out.println("How many seconds do you want local search to run? Must enter whole number");
				maxSeconds = Integer.parseInt(input.nextLine());
			} else {
				System.out.println("What mode would you like to run (write integer)?\n"
						+ "(1) Simple Backtracking \n"
						+ "(2) Backtracking with Forward Checking \n"
						+ "(3) Backtracking with Forward Checking and Most Constrained Variable");
				mode = Integer.parseInt(input.nextLine());
			}
			run(true);
		}
		input.close();
	}
	
	

}
