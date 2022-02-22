/*
 * Java
 *
 * Copyright 2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.image.server;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logs all messages of the image server.
 *
 * Adjust or create more log levels if needed.
 */
public class ImageServerLogger {
	private static final Logger LOGGER = Logger.getLogger(ImageServer.class.getSimpleName());

	private ImageServerLogger() {
		// Forbid instantiation
	}

	/**
	 * Logs an info message.
	 *
	 * @param message the log message.
	 */
	public static void info(final String message) {
		LOGGER.log(Level.INFO, message);
	}

	/**
	 * Logs an error message.
	 *
	 * @param message the log message.
	 */
	public static void error(final String message) {
		LOGGER.log(Level.SEVERE, message);
	}

	/**
	 * Logs an exception.
	 *
	 * @param exception the exception.
	 */
	public static void error(Throwable exception) {
		LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
	}
}