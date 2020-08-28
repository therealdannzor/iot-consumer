package com.dkf.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.dkf.app.Util;

/**
 * Unit tests for Util
 */
public class UtilTest {
	double DIFF = 0;

	@Test
    public void testCalculateOneHourFullPower()
    {
		// corresponds to two messages: 
		// 1: Message("Delta", 1) 
		// 2: Message("Pong", 0);
		double[][] input = {{1, 1.0}};
		// add three messages in total to Log object:
		// 1: log.addMessage(0, Message("Ping", 0))
		// 2: log.addMessage(1, Message("Delta", 1))
		// 3: log.addMessage(3601, Message("Pong", 0))
		Log log = mockGoodStream(0, input, 3601);

		double expected = 10.0;
		double actual = Util.calculateTotal(log);

		assertEquals(expected, actual, DIFF);
    }

	@Test
	public void testCalculateFewHoursAndFurtherAdjustments()
	{
		double[][] input = {{3, 0.5}, {3603, -0.25}, {5403, 1.0}};
		Log log = mockGoodStream(2, input, 7203);

		double expected = 11.25;
		double actual = Util.calculateTotal(log);

		assertEquals(expected, actual, DIFF);
	}

	@Test
	public void testUnorderedMessagesSimple()
	{
		double[][] input = {{3603, -0.25}, {5403, 1.0}, {3, 0.5}};
		Log log = mockGoodStream(2, input, 7203);

		double expected = 11.25;
		double actual = Util.calculateTotal(log);

		assertEquals(expected, actual, DIFF);
	}

	@Test
	public void testIncreaseAboveMax() {
		double[][] input = {{1, 1.0}, {3601, 0.5}, {7201, 1.0}};
		Log log = mockGoodStream(0, input, 10801);

		double expected = 30;
		double actual = Util.calculateTotal(log);

		assertEquals(expected, actual, DIFF);
	}

	@Test
	public void testDecreaseBelowMin() {
		double[][] input = {{1, 1.0}, {3601, -1.0}, {7201, -0.5}};
		Log log = mockGoodStream(0, input, 10801);
		
		double expected = 10;
		double actual = Util.calculateTotal(log);

		assertEquals(expected, actual, DIFF);
	}

	@Test
	public void testValidStreamIOButInvalidMessage() {
		Log log = new Log();
		Message inv = new Message("XYZ", 0.31415);
		log.addMessage(0, new Message("Ping", 0));
		log.addMessage(1, inv);
		log.addMessage(2, inv);
		log.addMessage(3, new Message("Pong", 0));

		double expected = 0.0;
		double actual = Util.calculateTotal(log);

		assertEquals(expected, actual, DIFF);
	}

	@Test
	public void testInvalidStreamIO() {
		Log log = new Log();
		Message invalidOpen = new Message("P!ng", 0);
		Message msg = new Message("Delta", 0.5);
		Message invalidClose = new Message("P0ng", 0);
		log.addMessage(0, invalidOpen);
		log.addMessage(1, msg);
		log.addMessage(2, invalidClose);

		double expected = 0.0;
		double actual = Util.calculateTotal(log);

		assertEquals(expected, actual, DIFF);
	}

	// mockGoodStream contains an array of pairs (timestamp, delta). The first pair is assumed to be the message
	// after Ping.
	private Log mockGoodStream(int pingTimestamp, double[][] sequence, int pongTimestamp) {
		Log log = new Log();
		Message init = new Message("Ping", 0);
		log.addMessage(pingTimestamp, init);

		for (double[] pair : sequence) {
			long timestamp = (long) pair[0];
			double delta = pair[1];
			Message m = new Message("Delta", delta);
			log.addMessage(timestamp, m);
		}

		Message end = new Message("Pong", 0);
		log.addMessage(pongTimestamp, end);

		return log;
	}

}
