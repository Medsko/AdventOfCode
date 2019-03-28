package adventOfCode.day18;

import java.util.ArrayDeque;
import java.util.Queue;

public class DuetThread extends DuetProgram {

	/** The id of this program/thread. */
	private long threadId;
	
	private DuetThread other;
	
	private Queue<Long> queue;
	
	private boolean blocked;
	
	private int nrOfSends;
	
	private boolean terminated;

	public DuetThread(long threadId) {
		this.threadId = threadId;
		nrOfSends = 0;
		queue = new ArrayDeque<>();
	}
	
	/**
	 * Executed when a recover/receive instruction is encountered. In the multi-threaded/B 
	 * implementation, it retrieves the next value from the queue, blocking if the queue is empty.
	 * If both programs are blocked simultaneously, both should terminate.
	 */
	@Override
	protected boolean recover(RegisterInstruction instruction) {
		
		if (recoverFromQueue(instruction)) {
			return true;
		}
//		System.out.println("DuetThread " + threadId + " terminated at " + iterations + iterations 
//				+ System.lineSeparator() + "Number of times a value was sent: " + nrOfSends);		
		return false;
	}
	
	private boolean recoverFromQueue(RegisterInstruction instruction) {
		Long valueFromQueue = queue.poll();
		
		if (valueFromQueue == null) {
			return false;
		} else {
			registers.put(instruction.getRegister(), valueFromQueue);
			return true;
		}
	}
	
	/**
	 * Executed when a sound/send instruction is encountered. In this challenge B implementation,
	 * the value of the specified register is added to the queue of the other program.
	 */
	@Override
	protected boolean sound(RegisterInstruction instruction) {
		Long valueToAdd = getValueForRegister(instruction.getRegister());
		other.addValueToQueue(valueToAdd);
		nrOfSends++;
		
		return true;
	}

	@Override
	protected Long getValueForRegister(Character register) {
		Long value = registers.get(register);
		if (value != null)
			return value;
		else if (register.equals('p'))
			return threadId;
		else
			return 0L;
	}

	public void setOther(DuetThread other) {
		this.other = other;
	}
	
	public void addValueToQueue(long value) {
		queue.add(value);
	}

	public boolean isBlocked() {
		return blocked;
	}
	
	public boolean hasEmptyQueue() {
		return queue.size() == 0;
	}
	
	public int getNrOfSends() {
		return nrOfSends;
	}

	public boolean isTerminated() {
		return terminated;
	}

	public void setQueue(Queue<Long> queue) {
		this.queue = queue;
	}
}
