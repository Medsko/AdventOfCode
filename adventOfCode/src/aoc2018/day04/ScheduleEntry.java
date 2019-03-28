package aoc2018.day04;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScheduleEntry implements Comparable<ScheduleEntry> {

	private LocalDateTime timestamp;
		
	private Integer guardId;

	private Command command;

	
	public enum Command {
		BEGIN,
		WAKE,
		SLEEP;
		
		public static Command fromInput(String input) {			
			switch (input.toUpperCase()) {
				case "GUARD": return BEGIN;
				case "WAKES": return WAKE;
				case "FALLS": return SLEEP;
				default: return null;
			}
		}
	}
	
	public ScheduleEntry(String input) {
		// Split the input in a 'date' and a 'command' part. 
		String[] inputParts = input.split("] ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		// Skip the '[' in the input.
		String dateTimeString = inputParts[0].substring(1);
		timestamp = LocalDateTime.parse(dateTimeString, formatter);
		
		inputParts = inputParts[1].split(" ");
		command = Command.fromInput(inputParts[0]);
		
		if (command.equals(Command.BEGIN)) {
			// This a guard declaration. Read the guard's id from the input.
			guardId = Integer.parseInt(inputParts[1].substring(1));
		}
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public Integer getGuardId() {
		return guardId;
	}

	public Command getCommand() {
		return command;
	}

	@Override
	public int compareTo(ScheduleEntry other) {
		return this.timestamp.compareTo(other.timestamp);
	}
}
