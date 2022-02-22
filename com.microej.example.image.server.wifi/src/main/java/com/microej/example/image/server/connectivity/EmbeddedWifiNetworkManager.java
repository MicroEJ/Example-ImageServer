/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.image.server.connectivity;

import java.io.IOException;

import com.microej.example.image.server.ImageServerLogger;

import ej.annotation.Nullable;
import ej.ecom.wifi.AccessPoint;
import ej.net.util.NtpUtil;
import ej.net.util.wifi.WifiNetworkManager;

/**
 * The Wi-Fi network manager for when the Image Server is run on embedded.
 */
public class EmbeddedWifiNetworkManager implements WirelessNetworkManager {
	private static final int ACCESS_POINT_JOIN_TIMEOUT = 20000;
	private static final String INFO_EMBEDDED_WIFI_NETWORK_MANAGER_JOIN_ACCESS_POINT = "EmbeddedWifiNetworkManager.joinAccessPoint(accessPoint, password, timeout)"; //$NON-NLS-1$
	private static final String INFO_EMBEDDED_WIFI_NETWORK_MANAGER_DEINIT = "EmbeddedWifiNetworkManager.deinit()"; //$NON-NLS-1$
	private static final String INFO_EMBEDDED_WIFI_NETWORK_MANAGER_INIT = "EmbeddedWifiNetworkManager.init()"; //$NON-NLS-1$
	private final WifiNetworkManager wifiManager;

	/**
	 * Constructs an embedded Wi-Fi manager.
	 *
	 * @throws IOException
	 *             thrown when Wi-Fi manager could not be initialized.
	 */
	public EmbeddedWifiNetworkManager() throws IOException {
		super();
		// Instantiate the wifiManager and initialize the network interface
		this.wifiManager = new WifiNetworkManager();
	}

	@Override
	public void init() throws IOException {
		ImageServerLogger.info(INFO_EMBEDDED_WIFI_NETWORK_MANAGER_INIT);

		// Join the configured Access Point
		WirelessCredentials credentials = new ConfigurationService().getWirelessCredentials();
		this.wifiManager.joinAccessPoint(credentials.getSsId(), credentials.getPassword(),
				credentials.getSecurityMode(), ACCESS_POINT_JOIN_TIMEOUT);

		// Update the local time on the board
		NtpUtil.updateLocalTime();
	}

	@Override
	public void deinit() throws IOException {
		ImageServerLogger.info(INFO_EMBEDDED_WIFI_NETWORK_MANAGER_DEINIT);
		this.wifiManager.leaveAccessPoint();
	}

	@Override
	public void joinAccessPoint(AccessPoint accessPoint, @Nullable String password, int timeout) throws IOException {
		ImageServerLogger.info(INFO_EMBEDDED_WIFI_NETWORK_MANAGER_JOIN_ACCESS_POINT);
		this.wifiManager.joinAccessPoint(accessPoint, password, timeout);
	}
}
