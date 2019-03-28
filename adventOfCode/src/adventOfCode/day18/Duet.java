package adventOfCode.day18;

public class Duet {

	public int runUntilDeadlock() {
		DuetThread zero = new DuetThread(0);
		DuetThread one = new DuetThread(1);
		one.setDebug(true);
		zero.setDebug(true);
		
		zero.setOther(one);
		one.setOther(zero);

		do {
			
			zero.run();
			one.run();
			
		} while (!one.hasEmptyQueue() || !zero.hasEmptyQueue());
		
		return one.getNrOfSends();
	}
	
}
