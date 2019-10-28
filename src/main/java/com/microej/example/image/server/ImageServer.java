/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.image.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.microej.example.image.server.endpoints.UploadEndpoint;

import ej.microui.MicroUI;
import ej.net.util.NetUtil;
import ej.restserver.RestServer;
import ej.restserver.endpoint.AliasEndpoint;
import ej.restserver.endpoint.GzipResourceEndpoint;
import ej.restserver.endpoint.ResourceRestEndpoint;

/**
 * This server exposes resources from the src/resources folder.
 */
public class ImageServer {

	/**
	 * Logger used for the application.
	 */
	public static final Logger LOGGER = Logger.getLogger("ImageServer"); //$NON-NLS-1$

	/**
	 * Default HTTP port.
	 */
	public static final int PORT = 80;

	private static final String ROOT = "/"; //$NON-NLS-1$
	private static final String GZ = ".gz"; //$NON-NLS-1$
	private static final String PNG_MASCOT_LOGO_HUGE_PNG = "/png/mascot_logo_huge.png"; //$NON-NLS-1$
	private static final String INDEX_HTML = "/index.html"; //$NON-NLS-1$
	private static final String HTML_FOLDER = "/html"; //$NON-NLS-1$

	private static final int JOB_COUNT_BY_SESSION = 1;
	private static final int SIMULTANEOUS_CONNECTION = 5;
	private static final int POLL_RATE = 500;

	private RestServer server;
	private boolean run;

	private ImageDisplay imageDisplay;

	/**
	 * Entry point.
	 *
	 * @param args
	 *            not used.
	 * @throws InterruptedException
	 *             if the thread is interrupted while waiting for an IP.
	 */
	public static void main(String[] args) throws InterruptedException {
		MicroUI.start();
		new ImageServer().start();
	}

	public void start() throws InterruptedException {
		synchronized (this) {
			run = true;

			imageDisplay = new ImageDisplay();
			imageDisplay.show();
		}

		while (NetUtil.getFirstHostAddress() == null && run) {
			Thread.sleep(POLL_RATE);
		}

		int port = PORT;

		synchronized (this) {
			while (server == null && run) {
				try {
					server = new RestServer(port, SIMULTANEOUS_CONNECTION, JOB_COUNT_BY_SESSION);
				} catch (final IOException e) {
					port++;
					LOGGER.log(Level.WARNING, e.getMessage(), e);
				}
			}
			imageDisplay.setPort(port);
			// Used for development
			// ResourceRestEndpoint index = new ResourceRestEndpoint(INDEX_HTML, HTML_FOLDER + INDEX_HTML);
			// Used for production
			if (server != null) {
				final ResourceRestEndpoint index = new GzipResourceEndpoint(INDEX_HTML, HTML_FOLDER + INDEX_HTML + GZ);
				server.addEndpoint(new AliasEndpoint(ROOT, index));
				server.addEndpoint(new UploadEndpoint(imageDisplay));
				server.addEndpoint(
						new GzipResourceEndpoint(PNG_MASCOT_LOGO_HUGE_PNG, HTML_FOLDER + PNG_MASCOT_LOGO_HUGE_PNG + GZ));
				server.start();
				imageDisplay.setConnected(true);
			}
		}
	}

	/**
	 * Shutdowns the server.
	 */
	public synchronized void stop() {
		run = false;
		ImageDisplay imageDisplay = this.imageDisplay;
		if (imageDisplay != null) {
			imageDisplay.hide();
		}

		RestServer server = this.server;
		this.server = null;
		if (server != null) {
			server.stop();
		}
	}

}
