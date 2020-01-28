package bean;

public class Color {
	
	private String c;
	
	public Color(String c) {
		this.c = c;
	}
	
	public String getColor() {
		return c;
	}

	/**
	 * Compare colors using this method.
	 * @param other the color you are comparing current object to
	 * @return true if and only if the colors are the same.
	 */
	public boolean same(Color other) {
		if (this.getColor().equals(other.getColor())) {
			return true;
		}
		return false;
	}
	
}
