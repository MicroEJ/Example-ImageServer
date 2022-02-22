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

/**
 * The Wi-Fi network manager for when the Image Server is run on the simulator.
 *
 * This is a mocked network manager. The simulator does not need Wi-Fi Management because it is running directly on the
 * (developer) machine.
 */
public class SimulatorWifiNetworkManager implements WirelessNetworkManager {
	private static final String INFO_SIMULATOR_WIFI_NETWORK_MANAGER_JOIN_ACCESS_POINT = "SimulatorWifiNetworkManager.joinAccessPoint(accessPoint, password, timeout)"; //$NON-NLS-1$
	private static final String INFO_SIMULATOR_WIFI_NETWORK_MANAGER_DEINIT = "SimulatorWifiNetworkManager.deinit()"; //$NON-NLS-1$
	private static final String INFO_SIMULATOR_WIFI_NETWORK_MANAGER_INIT = "SimulatorWifiNetworkManager.init()"; //$NON-NLS-1$

	@Override
	public void init() throws IOException {
		ImageServerLogger.info(INFO_SIMULATOR_WIFI_NETWORK_MANAGER_INIT);
	}

	@Override
	public void deinit() throws IOException {
		ImageServerLogger.info(INFO_SIMULATOR_WIFI_NETWORK_MANAGER_DEINIT);
	}

	@Override
	public void joinAccessPoint(AccessPoint access, @Nullable String password, int timeout) throws IOException {
		ImageServerLogger.info(INFO_SIMULATOR_WIFI_NETWORK_MANAGER_JOIN_ACCESS_POINT);
	}
}
