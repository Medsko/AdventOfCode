package aoc2018.day04;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Stack;

import aoc2018.day04.ScheduleEntry.Command;

/**
 * The wake/sleep schedule of a guard. 
 */
public class GuardSchedule {

	private int guardId;
	
	private Stack<ScheduleEntry> entries;
	
	private final static LocalTime ONE_AM = LocalTime.of(1, 0);
	
	private LocalDateTime previousTimestamp;
	
	private int[] timesAsleepAtMinute;
	
	private int minutesAsleep;
	
	public GuardSchedule(ScheduleEntry firstEntry) {
		this.guardId = firstEntry.getGuardId();
		entries = new Stack<>();
		addEntry(firstEntry);
		timesAsleepAtMinute = new int[60];
		minutesAsleep = 0;
	}
	
	/**
	 * Adds the next entry to this guard schedule. 
	 * Based on the added entry and the previous entry, some processing occurs. 
	 */
	public void addEntry(ScheduleEntry entry) {
		
		LocalDateTime timestamp = entry.getTimestamp();
		
		if (entry.getCommand().equals(Command.BEGIN)) {
			if (timestamp.isAfter(LocalDateTime.of(timestamp.toLocalDate(), ONE_AM))) {
				// If the previous entry started before 0:00, take that time - on the next day - as  
				// the starting point.
				LocalDate startDate = entry.getTimestamp().toLocalDate().plusDays(1L);
				previousTimestamp = LocalDateTime.of(startDate, LocalTime.MIN);
			}
		} else if (entry.getCommand().equals(Command.SLEEP)) {
			
			previousTimestamp = timestamp;
			
		} else if (entry.getCommand().equals(Command.WAKE)) {
			// Calculate the time from the previous entry until the new entry.
			long minutesSlept = previousTimestamp.until(timestamp, ChronoUnit.MINUTES);
			// Add the minutes slept this night to the total of this guard.
			minutesAsleep += minutesSlept;
			// Keep a record of the minutes slept this day.
			int startSleep = previousTimestamp.getMinute();
			for (int i=0; i<minutesSlept && i<timesAsleepAtMinute.length; i++) {
				timesAsleepAtMinute[startSleep + i]++;
			}
			
		}
		entries.push(entry);
	}

	public int getGuardId() {
		return guardId;
	}

	public int getMinutesAsleep() {
		return minutesAsleep;
	}
	
	public int getMinuteMostAsleepAt() {
		int minuteMostAsleepAt = 0;
		int mostMinutes = 0;
		for (int i=0; i<timesAsleepAtMinute.length; i++) {
			if (timesAsleepAtMinute[i] > mostMinutes) {
				mostMinutes = timesAsleepAtMinute[i];
				minuteMostAsleepAt = i;
			}
		}
		return minuteMostAsleepAt;
	}
	
	public int getMostTimesAsleep() {
		int mostMinutes = 0;
		for (int i=0; i<timesAsleepAtMinute.length; i++) {
			if (timesAsleepAtMinute[i] > mostMinutes) {
				mostMinutes = timesAsleepAtMinute[i];
			}
		}
		return mostMinutes;
	}
}
