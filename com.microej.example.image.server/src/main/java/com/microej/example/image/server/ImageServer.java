/*
 * Java
 *
 * Copyright 2018-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.image.server;

import java.io.IOException;

import com.microej.example.image.server.connectivity.ManageableServer;
import com.microej.example.image.server.endpoints.UploadEndpoint;

import ej.annotation.Nullable;
import ej.microui.display.Display;
import ej.net.util.NetUtil;
import ej.restserver.RestServer;
import ej.restserver.endpoint.AliasEndpoint;
import ej.restserver.endpoint.GzipResourceEndpoint;
import ej.restserver.endpoint.ResourceRestEndpoint;

/**
 * This server exposes resources from the src/resources folder.
 */
public class ImageServer extends ManageableServer {

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

	private @Nullable RestServer server;
	private boolean run;

	private final ImageDisplay imageDisplay;

	/**
	 * The constructor.
	 */
	public ImageServer() {
		super();
		this.imageDisplay = new ImageDisplay();
	}

	/**
	 * Starts the server.
	 *
	 * @throws InterruptedException
	 *             thrown if the thread sleeping is interrupted.
	 */
	@Override
	public void start() throws InterruptedException {
		synchronized (this) {
			this.run = true;
			Display.getDisplay().requestShow(this.imageDisplay);
		}

		while (NetUtil.getFirstHostAddress() == null && this.run) {
			Thread.sleep(POLL_RATE);
		}

		int port = PORT;

		synchronized (this) {
			while (this.server == null && this.run) {
				try {
					this.server = new RestServer(port, SIMULTANEOUS_CONNECTION, JOB_COUNT_BY_SESSION);
				} catch (final IOException e) {
					ImageServerLogger.error(e);
					port++;
				}
			}
			this.imageDisplay.setPort(port);

			if (this.server != null) {
				final RestServer currentServer = this.server;
				final ResourceRestEndpoint index = new GzipResourceEndpoint(INDEX_HTML, HTML_FOLDER + INDEX_HTML + GZ);
				currentServer.addEndpoint(new AliasEndpoint(ROOT, index));
				currentServer.addEndpoint(new UploadEndpoint(this.imageDisplay));
				currentServer.addEndpoint(new GzipResourceEndpoint(PNG_MASCOT_LOGO_HUGE_PNG,
						HTML_FOLDER + PNG_MASCOT_LOGO_HUGE_PNG + GZ));
				currentServer.start();
				this.imageDisplay.setConnected(true);
			}
		}
	}

	/**
	 * Shutdowns the server.
	 */
	@Override
	public synchronized void stop() {
		this.run = false;
		final ImageDisplay theImageDisplay = this.imageDisplay;
		Display.getDisplay().requestHide(theImageDisplay);

		final RestServer theServer = this.server;
		this.server = null;
		if (theServer != null) {
			theServer.stop();
		}
	}
}
