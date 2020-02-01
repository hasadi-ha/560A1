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
	public static String fileName = "Australia.txt";
	//set true for local search, false for backtracking
	public static boolean hillClimbing = true;
	//set to how many seconds max you want to run it for
	public static int maxSeconds = 60;
	
	//checking if first time running, if so, print graph
	public static boolean first = true;

	public static void main(String[] args) throws IOException {
		getUserInputs();
	}
	
	public static void run() throws IOException {
		loadAndBuildGraphs();
		startColoring();
	}
	
	public static void reset() {
		Constants.COLORS = new ArrayList<>();
		Constants.STATES = new ArrayList<>();
		
		Constants.CONFLICTS = new LinkedHashSet<>();
		
		Constants.INDEX = 0;
		Constants.STEPS = 0;
	}
	
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
	
	public static void startColoring() {
		String output = hillClimbing ? "Starting Hill Climbing..." : "Starting Backtracking...";
		System.out.println(output +"\n\n");
		Colorer graphColorer = new GraphColoringFactory(hillClimbing).getColorer();
		graphColorer.startColoring(maxSeconds);
	}
	
	public static void getUserInputs() throws IOException {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the text file withh states, colors, and links");
		fileName = input.nextLine();
		System.out.println("Do you want to use local search? It might not color the graph, but if it does, it'll be fast (y/n). Say b to run both.");
		if (input.nextLine().equalsIgnoreCase("b")) {
			hillClimbing = false;
			run();
			hillClimbing = true;
			reset();
			run();
		}
		else {
			hillClimbing = input.nextLine().equalsIgnoreCase("y");
			if (hillClimbing) {
				System.out.println("How many seconds do you want local search to run? Must enter whole number");
				maxSeconds = Integer.parseInt(input.nextLine());
			}
			run();
		}
		input.close();
	}
	
	

}
