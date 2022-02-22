/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.image.server.connectivity;

import java.io.IOException;

import ej.annotation.Nullable;
import ej.ecom.wifi.AccessPoint;

/**
 * Class implementing this interface provide wireless network management.
 */
public interface WirelessNetworkManager {

	/**
	 * Initialize the network.
	 *
	 * @throws IOException
	 *             if I/O error occurred
	 */
	void init() throws IOException;

	/**
	 * DeInitialize the network.
	 *
	 * @throws IOException
	 *             if I/O error occurred
	 */
	void deinit() throws IOException;

	/**
	 * Joins the given access point.
	 *
	 * @param accessPoint
	 *            The Wireless Access Point.
	 * @param password
	 *            The password for the access point.
	 * @param timeout
	 *            The timeout for accessing the access point.
	 * @throws IOException
	 *             Thrown when an error occurred during the joining.
	 */
	void joinAccessPoint(AccessPoint accessPoint, @Nullable String password, int timeout) throws IOException;
}