package com.dkf.app;

public class Commands {
	long timestamp;
	String message;
	double delta;

	public Commands(String[] cmds) {
		if (Long.parseLong(cmds[0]) >= 0) {
			this.timestamp = Long.parseLong(cmds[0]);
			if (cmds.length == 3) {
				this.delta = Double.parseDouble(cmds[2]);
			}
			this.message = cmds[1];
		}
	}

	public long Timestamp() {
		return this.timestamp;
	}

	public String Message() {
		return this.message;
	}

	public double Delta() {
		return this.delta;
	}
}

