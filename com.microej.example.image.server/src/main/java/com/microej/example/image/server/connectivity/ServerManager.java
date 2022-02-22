/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.image.server.connectivity;

import java.io.IOException;

import com.microej.example.image.server.ImageServer;
import com.microej.example.image.server.ImageServerLogger;

import android.net.ConnectivityManager;
import ej.net.util.connectivity.ConnectivityUtil;
import ej.service.ServiceFactory;

/**
 * Starts and stops the server.
 */
public class ServerManager {
	private static final String STOPPING_SERVER = "Stopping server.."; //$NON-NLS-1$
	private static final String NO_CONNECTIVITY_MANAGER = "No connectivity manager found."; //$NON-NLS-1$

	private ServerManager() {
		// No instantiation intended.
	}

	/**
	 * Starts the {@link ImageServer} and registers it with the connectivity manager.
	 *
	 * @param manageableServer
	 *            the manageable Server
	 *
	 * @throws InterruptedException
	 *             Thrown when the server could not be started.
	 * @throws IOException
	 *             Thrown when the server could not be started.
	 */
	public static void startServer(ManageableServer manageableServer) throws InterruptedException, IOException {
		ConnectivityManager connectivityManager = ServiceFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (connectivityManager != null) {
			manageableServer.start();
			ConnectivityUtil.registerAndCall(connectivityManager, manageableServer);
		} else {
			ImageServerLogger.info(NO_CONNECTIVITY_MANAGER);
		}
	}

	/**
	 * Stops the {@link ImageServer} and unregisters it with the connectivity manager.
	 *
	 * @param manageableServer
	 *            the manageable Server
	 */
	public static void stopServer(ManageableServer manageableServer) {
		ImageServerLogger.info(STOPPING_SERVER);
		ConnectivityManager connectivityManager = ServiceFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (connectivityManager != null) {
			connectivityManager.unregisterNetworkCallback(manageableServer);
			manageableServer.stop();
		}
	}
}
