
public class FolowTheLine extends Thread {
	
	
	private Robot2Roues robot; 
	public FolowTheLine(Robot2Roues robotUtilise) {
		robot = robotUtilise;
	}

	public void run() {
		
		robot.followTheLineForEver();
		
	}

}
