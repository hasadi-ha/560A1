package main;

public class GraphColoringFactory {
	
	private boolean hillClimber;
	
	public GraphColoringFactory(boolean hillClimber) {
		this.hillClimber = hillClimber;
	}
	
	public Colorer getColorer() {
		return hillClimber ? new HillClimber() : new BackTracker();
	}

}
