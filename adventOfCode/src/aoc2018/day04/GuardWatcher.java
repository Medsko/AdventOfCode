package aoc2018.day04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aoc2018.AbstractAocAlgorithm;

public class GuardWatcher extends AbstractAocAlgorithm {

	private List<ScheduleEntry> entries;
	
	private Map<Integer, GuardSchedule> schedules;
	
	// First try: 50644 (too low - 84636 was correct).
	
	public GuardWatcher(boolean isCommandLineFine) {
		super(isCommandLineFine);
	}
	
	public boolean initialize() {
		if (!super.initialize(2018, 4)) {
			return false;
		}

		entries = new ArrayList<>();
		for (String inputLine : fetcher.getInputLines()) {
			ScheduleEntry entry = new ScheduleEntry(inputLine);
			entries.add(entry);
		}
		schedules = new HashMap<>();
		
		return true;
	}

	public void watch() {
		
		Collections.sort(entries);
		
		Integer currentGuardId = null;
		
		for (ScheduleEntry entry : entries) {
			
			GuardSchedule schedule = null;
			if (entry.getGuardId() != null) {
				currentGuardId = entry.getGuardId();
			}
			
			schedule = schedules.get(currentGuardId);
			
			if (schedule != null) {
				schedule.addEntry(entry);
			} else {
				schedule = new GuardSchedule(entry);
				schedules.put(entry.getGuardId(), schedule);
			}
		}	
	}
	
	public int determineSleepiestGuard(boolean isChallengeB) {
		GuardSchedule sleepiestGuard;
		if (!isChallengeB) {
			sleepiestGuard = Collections.max(schedules.values(), 
					new Comparator<GuardSchedule>() {
				@Override
				public int compare(GuardSchedule o1, GuardSchedule o2) {
					if (o1.getMinutesAsleep() > o2.getMinutesAsleep())
						return 1;
					if (o1.getMinutesAsleep() < o2.getMinutesAsleep())
						return -1;
					return 0;
				}
			});
		} else {
			sleepiestGuard = Collections.max(schedules.values(), 
					new Comparator<GuardSchedule>() {
				@Override
				public int compare(GuardSchedule o1, GuardSchedule o2) {
					if (o1.getMostTimesAsleep() > o2.getMostTimesAsleep())
						return 1;
					if (o1.getMostTimesAsleep() < o2.getMostTimesAsleep())
						return -1;
					return 0;
				}
			});
		}
		System.out.println("Id of sleepiest guard: " + sleepiestGuard.getGuardId());
		System.out.println("Minute he was most often asleep: " 
				+ sleepiestGuard.getMinuteMostAsleepAt());
		
		return sleepiestGuard.getMinuteMostAsleepAt() * sleepiestGuard.getGuardId();
	}
}
