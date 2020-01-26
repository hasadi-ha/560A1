package bean;

public class Color {
	
	private String c;
	
	public Color(String c) {
		this.c = c;
	}
	
	public String getColor() {
		return c;
	}

	public boolean same(Color other) {
		if (this.getColor().equals(other.getColor())) {
			return true;
		}
		return false;
	}
	
}
