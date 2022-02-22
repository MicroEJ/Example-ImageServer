/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.image.server;

import java.io.IOException;

import com.microej.example.image.server.connectivity.ManageableServer;
import com.microej.example.image.server.connectivity.ServerManager;

import ej.microui.MicroUI;

/**
 * Starts and stops the {@link ImageServer}. Uses the connectivity manager to manage connectivity.
 */
public class Main {
	private static final long ONE_HOUR = 60 * 60 * 1000L;

	/**
	 * Entry point.
	 *
	 * @param args
	 *            not used.
	 * @throws InterruptedException
	 *             if the thread is interrupted while waiting for an IP.
	 * @throws IOException
	 *             if the server could not be started.
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		MicroUI.start();

		final ManageableServer imageServer = new ImageServer();
		ServerManager.startServer(imageServer);

		// Stop after 1 hour
		Thread.sleep(ONE_HOUR);

		ServerManager.stopServer(imageServer);
	}
}
