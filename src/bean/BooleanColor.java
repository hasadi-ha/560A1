package bean;

public class BooleanColor extends Color{

	private boolean valid;

	/**
	 * Adds an additional variable to color, a boolean. This boolean continues to check if a color is valid or not 
	 * for the current state. 
	 * @param name is the name of the color
	 */
	public BooleanColor(String name) {
		super(name);
		valid = true;
	}
	
	public void disable() {
		valid = false;
	}
	
	public void enable() {
		valid = true;
	}

	public boolean isAvailable() {
		return valid;
	}
	
}
