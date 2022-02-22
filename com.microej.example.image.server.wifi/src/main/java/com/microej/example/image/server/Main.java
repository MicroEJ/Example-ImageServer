/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.image.server;

import java.io.IOException;

import com.microej.example.image.server.connectivity.EmbeddedWifiNetworkManager;
import com.microej.example.image.server.connectivity.ManageableServer;
import com.microej.example.image.server.connectivity.ServerManager;
import com.microej.example.image.server.connectivity.SimulatorWifiNetworkManager;
import com.microej.example.image.server.connectivity.WirelessNetworkManager;

import ej.bon.Constants;
import ej.microui.MicroUI;
import ej.service.ServiceFactory;

/**
 * Starts and stops the {@link ImageServer}. Uses a specific wireless network manager depending on SIM/EMB. Uses the
 * connectivity manager to manage connectivity.
 */
public class Main {
	/** BON Constant to know if we are running on simulator or on board. **/
	private static final String ON_SIMULATOR = "com.microej.library.microui.onS3"; //$NON-NLS-1$

	private static final long ONE_HOUR = 60 * 60 * 1000L;

	/**
	 * Entry point.
	 *
	 * @param args
	 *            not used.
	 * @throws InterruptedException
	 *             if the thread is interrupted while waiting for an IP
	 * @throws IOException
	 *             when the wifiNetworkManager cannot init/deinit
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		MicroUI.start();

		// WiFi support
		final WirelessNetworkManager wifiNetworkManager;
		if (Constants.getBoolean(ON_SIMULATOR)) {
			wifiNetworkManager = ServiceFactory.getService(WirelessNetworkManager.class,
					SimulatorWifiNetworkManager.class);
		} else {
			wifiNetworkManager = ServiceFactory.getService(WirelessNetworkManager.class,
					EmbeddedWifiNetworkManager.class);
		}
		wifiNetworkManager.init();

		final ManageableServer imageServer = new ImageServer();
		ServerManager.startServer(imageServer);

		// Stop after 1 hour
		Thread.sleep(ONE_HOUR);

		ServerManager.stopServer(imageServer);

		wifiNetworkManager.deinit();
	}
}
