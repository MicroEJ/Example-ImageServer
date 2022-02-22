/*
 * Java
 *
 * Copyright 2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.image.server.connectivity;

import ej.ecom.wifi.SecurityMode;

/**
 * Holds Wireless credentials.
 */
public class WirelessCredentials {

	private final String ssId;
	private final String password;
	private final SecurityMode securityMode;

	/**
	 * Creates credentials object for wireless access.
	 *
	 * @param ssId
	 *            the ssId.
	 * @param password
	 *            the password for the wireless network.
	 * @param securityMode
	 *            the security mode of the wireless network.
	 */
	public WirelessCredentials(String ssId, String password, SecurityMode securityMode) {
		this.ssId = ssId;
		this.password = password;
		this.securityMode = securityMode;
	}

	/**
	 * Gets the ssId.
	 *
	 * @return the ssId.
	 */
	public String getSsId() {
		return this.ssId;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password.
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Gets the securityMode.
	 *
	 * @return the securityMode.
	 */
	public SecurityMode getSecurityMode() {
		return this.securityMode;
	}
}
