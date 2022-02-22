/*
 * Java
 *
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.image.server.connectivity;

import java.io.IOException;

import com.microej.example.image.server.ImageServerLogger;

import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkCapabilities;
import ej.annotation.Nullable;
import ej.net.util.NetUtil;

/**
 * A server that provides start and stop capabilities and connectivity information.
 */
public abstract class ManageableServer extends NetworkCallback {
	private static final String NETWORK_IS_LOST = "Network is lost."; //$NON-NLS-1$
	private static final String NETWORK_IS_AVAILABLE = "Network is available."; //$NON-NLS-1$
	private static final String ACTIVE_NETWORK_CONNECTED = "Connected to the internet = "; //$NON-NLS-1$
	private static final String IP_ADDRESS_OF_THE_BOARD = "The IP address of the board is: "; //$NON-NLS-1$

	/**
	 * Starts the server.
	 *
	 * @throws IOException
	 *             Thrown when the server could not be started.
	 * @throws InterruptedException
	 *             Thrown when the server could not be started.
	 */
	public abstract void start() throws IOException, InterruptedException;

	/**
	 * Stops the server.
	 */
	public abstract void stop();

	@Override
	public void onAvailable(@Nullable Network network) {
		ImageServerLogger.info(NETWORK_IS_AVAILABLE);
		// Get real IP address
		ImageServerLogger.info(IP_ADDRESS_OF_THE_BOARD + NetUtil.getFirstHostAddress());
	}

	@Override
	public void onLost(@Nullable Network network) {
		ImageServerLogger.info(NETWORK_IS_LOST);
	}

	@Override
	public void onCapabilitiesChanged(@Nullable Network network, @Nullable NetworkCapabilities networkCapabilities) {
		boolean hasInternet = networkCapabilities != null
				&& networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
		ImageServerLogger.info(ACTIVE_NETWORK_CONNECTED + hasInternet);
	}
}
