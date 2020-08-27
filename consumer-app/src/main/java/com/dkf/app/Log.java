package com.dkf.app;

import java.util.TreeMap;
import java.util.Set;
import com.dkf.app.Message;

public class Log
{
	// map holds a mapping between unix timestamps and messages. We use a TreeMap which sorts on
	// insertion and amortises the cost of sorting as the set grows.
	private TreeMap<Long, Message> tmap = new TreeMap<Long, Message>();

	public Log() {
		TreeMap<Long, Message> map = new TreeMap<Long, Message>();
		this.tmap = map;
	}

	public Message getMessage(long timestamp) {
		return tmap.get(timestamp);
	}

	// addMessage adds a valid Message to the tree map if its timestamp has not previously been added
	public void addMessage(long timestamp, Message msg) {
		if (tmap.get(timestamp) == null && msg.getType() != "Invalid" && timestamp >= 0) {
			tmap.put(timestamp, msg);
		}
	}

	public Long getFirstTimestamp() {
		return tmap.firstKey();
	}

	public Long getLastTimestamp() {
		return tmap.lastKey();
	}

	public Set<Long> getAllTimestamps() {
		return tmap.keySet();
	}

	// getTimestampsTo returns the set of timestamp less than toKey
	public Set<Long> getTimestampsTo(long toKey) {
		return tmap.headMap(toKey).keySet();
	}
}
