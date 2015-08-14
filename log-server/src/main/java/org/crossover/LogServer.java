package org.crossover;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class LogServer {
	static final Logger logger = Logger.getLogger(LogServer.class);

	public static void main(String[] args) {
		// Configure logger
		BasicConfigurator.configure();
		logger.error("Hello World!");
	}
}