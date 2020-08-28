package com.dkf.app;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

import com.dkf.app.Log;
import com.dkf.app.Util;

/**
 * Estimates the power consumption from a stream
 * of timestamps with different messages
 */
public class App 
{
    public static void main( String[] args )
    {
		Log history = new Log();
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\\n");

		while (true) {
			System.out.print("> ");
			String line = sc.next();

			// short circuit if empty input 
			if (line.length() == 0) {
				break;
			}

			// separate line into tokens of a Message
			Commands cmds = new Commands(line.split("\\s+"));
			// create new Message from the tokens
			Message msg = new Message(cmds.Message(), cmds.Delta());

			// add Message to mapping
			history.addMessage(cmds.Timestamp(), msg);

			// prevent race conditions when we calculate energy consumption and purge the cache
			final Semaphore mutex = new Semaphore(1);

			// if we receive an end of stream signal
			if (cmds.message.equals("Pong")) {
				try {
					// lock to calculate the consumption so far
					mutex.acquire();
					double energyConsumption = Util.calculateTotal(history);
					Util.print(energyConsumption);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					// after the calculations are done we remove stale data from RAM
					mutex.release();
					history.deleteTo(cmds.Timestamp());
				}
			}

    	}
		sc.close();
	}

}
