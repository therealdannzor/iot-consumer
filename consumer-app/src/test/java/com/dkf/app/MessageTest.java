package com.dkf.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.dkf.app.Message;

/**
 * Unit tests for Message
 */
public class MessageTest 
{
    @Test
    public void testCorrectMessageTypeAndDelta()
    {
        Message msg = new Message("Delta", 0.5);
		String expectedType = "Delta";
		double expectedChange = 0.5;
		double discrepancy = 0.0;

		assertEquals(expectedType, msg.getType());
		assertEquals(expectedChange, msg.getDelta(), discrepancy);
    }

	@Test
	public void testIncorrectPositiveDelta()
	{
		Message msg = new Message("Delta", 999999999);
		String expectedType = "Invalid";
		boolean expectedHasDelta = false;

		assertEquals(expectedType, msg.getType());
		assertEquals(expectedHasDelta, msg.hasDelta());
	}

	@Test 
	public void testIncorrectNegativeDelta()
	{
		Message msg = new Message("Delta", -999999999);
		String expectedType = "Invalid";
		boolean expectedHasDelta = false;

		assertEquals(expectedType, msg.getType());
		assertEquals(expectedHasDelta, msg.hasDelta());
	}

	@Test
	public void testPingMessageType()
	{
		Message msg = new Message("Ping", 0); 
		String expectedType = "Ping";
		boolean expectedHasDelta = false;

		assertEquals(expectedType, msg.getType());
		assertEquals(expectedHasDelta, msg.hasDelta());
	}

	@Test
	public void testUnknownMessageType()
	{
		Message msg = new Message("Spam", 1);
		String expectedType = "Invalid";
		boolean expectedHasDelta = false;

		assertEquals(expectedType, msg.getType());
		assertEquals(expectedHasDelta, msg.hasDelta());
	}

	@Test
	public void testMissingMessageType()
	{
		Message msg = new Message("", 0.5);
		String expectedType = "Invalid";
		boolean expectedHasDelta = false;

		assertEquals(expectedType, msg.getType());
		assertEquals(expectedHasDelta, msg.hasDelta());
	}


}
