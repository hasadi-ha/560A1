package constants;

import java.util.LinkedHashSet;
import java.util.List;

import bean.Color;
import bean.State;

public class Constants {
	
	public static Color DUMMY_COLOR = new Color("DUMMY");
	
	public static List<Color> COLORS;
	public static List<State> STATES;
	
	public static LinkedHashSet<State> CONFLICTS;
	
	public static int INDEX = 0;
	public static int STEPS = 0;

}
