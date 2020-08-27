package com.dkf.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.dkf.app.Log;

/**
 * Unit test for Log
 */
public class LogTest 
{
	Message VALID_MESSAGE = new Message("Delta", 0.25);
	Message INVALID_MESSAGE = new Message("Spam", 999);

    @Test
    public void testNoDuplicateInserts()
    {
		Log log = new Log();
		log.addMessage(0, VALID_MESSAGE);
		log.addMessage(1, VALID_MESSAGE);
		log.addMessage(5, VALID_MESSAGE);
		log.addMessage(5, VALID_MESSAGE); // duplicate
		log.addMessage(9, VALID_MESSAGE);

		String expected = "[0, 1, 5, 9]"; 
		String actual = log.getAllTimestamps().toString();

		assertEquals(expected, actual);
    }

	@Test
	public void testNoDuplicateAndInsertsOrdered()
	{
		Log log = new Log();
		log.addMessage(5, VALID_MESSAGE);
		log.addMessage(1, VALID_MESSAGE);
		log.addMessage(1, VALID_MESSAGE);
		log.addMessage(4, VALID_MESSAGE);
		log.addMessage(3, VALID_MESSAGE);

		String expected = "[1, 3, 4, 5]";
		String actual = log.getAllTimestamps().toString();

		assertEquals(expected, actual);
	}

	@Test
	public void testSomeInvalidMessagesReceivedToIgnore()
	{
		Log log = new Log();
		log.addMessage(1, VALID_MESSAGE);
		log.addMessage(2, INVALID_MESSAGE);
		log.addMessage(3, VALID_MESSAGE);
		log.addMessage(4, INVALID_MESSAGE);
		log.addMessage(5, VALID_MESSAGE);

		String expected = "[1, 3, 5]";
		String actual = log.getAllTimestamps().toString();

		assertEquals(expected, actual);
	}

	@Test
	public void testOnlyInvalidMessagesReceivedToIgnore() {
		Log log = new Log();
		log.addMessage(1, INVALID_MESSAGE);
		log.addMessage(2, INVALID_MESSAGE);
		log.addMessage(3, INVALID_MESSAGE); 

		String expected = "[]"; 
		String actual = log.getAllTimestamps().toString();

		assertEquals(expected, actual);
	}

	@Test
	public void testIgnoreAddInvalidTimestampsButValidMessage() {
		Log log = new Log();
		log.addMessage(-1, VALID_MESSAGE);
		log.addMessage(1, VALID_MESSAGE);
		log.addMessage(-3, VALID_MESSAGE); 
		log.addMessage(2, VALID_MESSAGE); 
		log.addMessage(-9, INVALID_MESSAGE);

		String expected = "[1, 2]";
		String actual = log.getAllTimestamps().toString();

		assertEquals(expected, actual);
	}

	@Test 
	public void testFirstLastAndUpToTimestamps() {
		Log log = new Log(); 
		log.addMessage(1, VALID_MESSAGE);
		log.addMessage(2, VALID_MESSAGE);
		log.addMessage(3, VALID_MESSAGE);
		log.addMessage(4, VALID_MESSAGE);
		log.addMessage(5, VALID_MESSAGE);

		long expectedFirst = 1;
		long actualFirst = log.getFirstTimestamp();
		long expectedLast = 5;
		long actualLast = log.getLastTimestamp();
		String expectedRange = "[1, 2, 3]";
		String actualRange = log.getTimestampsTo(4).toString();

		assertEquals(expectedFirst, actualFirst);
		assertEquals(expectedLast, actualLast);
		assertEquals(expectedRange, actualRange); 
	}

}
