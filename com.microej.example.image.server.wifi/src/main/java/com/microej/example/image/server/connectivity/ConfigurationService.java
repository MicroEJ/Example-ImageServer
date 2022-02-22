/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.image.server.connectivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.microej.example.image.server.ImageServerLogger;

import ej.annotation.Nullable;
import ej.ecom.wifi.SecurityMode;

/**
 * Provides configuration.
 */
public class ConfigurationService {
	/**
	 * Put here the name of the Wireless network that should be taken if not provided via SD card.
	 */
	private static final String YOUR_LOCAL_WIFI_GATEWAY = "MyWifiGateway"; //$NON-NLS-1$

	/**
	 * Put here the password of the Wireless network that should be taken if not provided via SD card.
	 */
	private static final String YOUR_LOCAL_WIFI_PASSWORD = ""; //$NON-NLS-1$

	/**
	 * Put here the Security Mode of the Wireless network that should be taken if not provided via SD card.
	 */
	private static final SecurityMode YOUR_SECURITY_MODE = SecurityMode.WPA2;
	private static final String USING_DEFAULT_WIRELESS_CREDENTIALS = "Using default wireless credentials"; //$NON-NLS-1$
	private static final String FOUND_CREDENTIALS_SD_CARD = "Found credentials in SD card"; //$NON-NLS-1$
	private static final String READING_SECURITY_MODE = "Reading SecurityMode"; //$NON-NLS-1$
	private static final String CREDENTIALS_FILE_EXISTS = "credentialsFile.exists: "; //$NON-NLS-1$
	private static final String USER_DIR = "user.dir"; //$NON-NLS-1$
	private static final String WORKING_DIRECTORY = "Working Directory: "; //$NON-NLS-1$
	private static final String CREDENTIALS_PATH = "credentials.txt"; //$NON-NLS-1$

	/**
	 * Returns credentials. If possible finds and returns credentials lying on an SD card inserted in the board, under
	 * /usr/credentials.txt. Otherwise it will return default credentials.
	 *
	 * @return Credentials the wireless credentials
	 */
	protected WirelessCredentials getWirelessCredentials() {
		WirelessCredentials credentials = null;
		ImageServerLogger.info(WORKING_DIRECTORY + System.getProperty(USER_DIR));
		final File credentialsFile = new File(CREDENTIALS_PATH);
		ImageServerLogger.info(CREDENTIALS_FILE_EXISTS + credentialsFile.exists());
		if (credentialsFile.exists()) {
			try (BufferedReader fileInputStream = new BufferedReader(
					new InputStreamReader(new FileInputStream(credentialsFile)), 64)) {
				String ssid = fileInputStream.readLine();
				String passphrase = fileInputStream.readLine();
				ImageServerLogger.info(READING_SECURITY_MODE);
				SecurityMode securityMode = getSecurityModeEnumValueFromString(fileInputStream.readLine());
				if (ssid != null && passphrase != null && securityMode != null) {
					ImageServerLogger.info(FOUND_CREDENTIALS_SD_CARD);
					credentials = new WirelessCredentials(ssid, passphrase, securityMode);
				}
			} catch (IOException e) {
				ImageServerLogger.error(e);
			}
		}

		if (null == credentials) {
			credentials = getDefaultWirelessCredentials();
		}
		return credentials;
	}

	/**
	 * Gets the default wireless credentials.
	 *
	 * @return WirelessCredentials the default wireless credentials.
	 */
	private WirelessCredentials getDefaultWirelessCredentials() {
		ImageServerLogger.info(USING_DEFAULT_WIRELESS_CREDENTIALS);
		return new WirelessCredentials(YOUR_LOCAL_WIFI_GATEWAY, YOUR_LOCAL_WIFI_PASSWORD, YOUR_SECURITY_MODE);
	}

	/**
	 * Finds the value of the given enumeration by name, case-insensitive.
	 *
	 * @param givenValue
	 *            the security mode as string
	 * @return the security mode as enumeration value
	 **/
	private @Nullable SecurityMode getSecurityModeEnumValueFromString(@Nullable String givenValue) {
		if (givenValue != null) {
			for (SecurityMode securityMode : SecurityMode.values()) {
				if (securityMode.toString().equalsIgnoreCase(givenValue)) {
					return securityMode;
				}
			}
		}
		return null;
	}
}
