package com.dkf.app;

public class Message {
	private String type;
	private double delta;

	public Message(String type, double delta) {
		// type must be a valid type and delta within valid range
		if (hasValidType(type) && hasValidRange(delta)) {
			this.type = type;
			this.delta = delta;
		} else {
			this.type = "Invalid";
		}
	}

	public String getType() {
		return type;
	}

	public double getDelta() {
		return delta;
	}

	public boolean hasDelta() {
		return delta != 0;
	}

	private static boolean hasValidType(String candidate) {
		if (candidate.compareTo("Ping") == 0 ) {
			return true;
		} else if (candidate.compareTo("Pong") == 0) {
			return true;
		} else if (candidate.compareTo("Delta") == 0) {
			return true;
		} 

		return false;
	}

	private static boolean hasValidRange(double delta) {
		if (delta >= -1.0 && delta <= 1.0) {
			return true;
		}
		
		return false;
	}

}
