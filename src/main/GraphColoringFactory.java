package main;

public class GraphColoringFactory {
	
	private boolean hillClimber;
	
	public GraphColoringFactory(boolean hillClimber) {
		this.hillClimber = hillClimber;
	}
	
	/**
	 * @return BackTracker or HillClimber depending on the Run Mode
	 */
	public Colorer getColorer() {
		return hillClimber ? new HillClimber() : new BackTracker();
	}

}
