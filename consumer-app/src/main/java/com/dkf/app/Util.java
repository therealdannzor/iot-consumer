package com.dkf.app;

import com.dkf.app.Log;

public final class Util {

	public static double calculateTotal(Log log) {
		double zero = 0.0;
		if (log.isEmpty()) {
			return zero;
		}

		// get first and current timestamp
		Long currTime = log.getFirstTimestamp();
		// check that it is of type Ping
		String firstType = log.getMessage(currTime).getType();
		if (firstType != "Ping") {
			return zero;
		}
		// remove Ping message because it is not an information-bearer of energy consumption like the rest
		log.delete(currTime);

		double totalEnergy = 0.0;
		double currDelta = 0.0;
		//Long lastTime;
		for (long ts : log.getAllTimestamps()) {	
			// determine the last two timestamps
			Long lastTime = currTime;
			currTime = ts;

			// calculate the energy consumption between these two time points
			totalEnergy += calculateCycle(lastTime, currTime, currDelta);
			currDelta = adjustLevel(currDelta, log.getMessage(ts).getDelta());
		}

		return totalEnergy;
	}

	// calculateCycle calculates the energy consumption based on a fixed energy level, within a time period of two timestamps
	private static double calculateCycle(long start, long end, double delta) {
		int SECONDS_PER_HOUR = 3600;
		double FULL_POWER = 10;
		int timeSince = Math.toIntExact(end-start);
		double timeRatio = (double) timeSince/SECONDS_PER_HOUR;

		return delta * timeRatio * FULL_POWER;
	}

	// changeLevel changes the current energy level to a new value
	private static double adjustLevel(double currentLevel, double newLevel) {
		double MAX_LEVEL = 1.0;
		double MIN_LEVEL = 0.0;
		double sum = currentLevel + newLevel;

		if (sum > MAX_LEVEL) {
			return MAX_LEVEL;
		} else if (sum <= MAX_LEVEL && sum > MIN_LEVEL) {
			return sum;
		} else {
			return MIN_LEVEL;
		}
	}
}
	
