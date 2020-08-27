package com.dkf.app;

public class Message {
	private String type;
	private double delta;

	public Message(String type, double delta) {
		// type must be of valid type and delta within valid range
		if (type == "Ping" || type == "Pong" || type == "Delta"  && delta >= 0.0 && delta <= 1.0) {
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
}
