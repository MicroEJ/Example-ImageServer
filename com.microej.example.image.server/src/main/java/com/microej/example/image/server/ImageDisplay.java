/*
 * Java
 *
 * Copyright 2018-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.image.server;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import ej.annotation.Nullable;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.display.ResourceImage;
import ej.microui.event.Event;
import ej.microui.event.EventHandler;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.net.util.NetUtil;

/**
 * A class to display an image.
 */
public class ImageDisplay extends Displayable implements EventHandler {

	/**
	 * The path to the appropriate font.
	 */
	private static final String FONT_PATH = "/fonts/roboto_condensed_regular_12"; //$NON-NLS-1$

	/**
	 * ":".
	 */
	private static final String PORT_SEPARATION = ":"; //$NON-NLS-1$

	/**
	 * http://.
	 */
	private static final String HTTP = "http://"; //$NON-NLS-1$

	private static final int OFFSET = 10;

	private int port;

	private ImageModel imageModel;

	private @Nullable ImageModel previousImageModel;

	private boolean connected;

	private final List<String> texts;

	/**
	 * Instantiates an {@link ImageDisplay}.
	 */
	public ImageDisplay() {
		this.previousImageModel = null;
		this.imageModel = new ImageModel(null, Colors.BLACK, Colors.WHITE);
		this.texts = new ArrayList<>();
	}

	/**
	 * Sets the displayed text as connected.
	 *
	 * @param connected
	 *            the connected state.
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
		requestRender();
	}

	/**
	 * Sets the image.
	 *
	 * @param newImageModel
	 *            the image model to set.
	 */
	public synchronized void setImage(ImageModel newImageModel) {
		if (newImageModel.getImage() != null) {
			// If available, close the image in the previous ImageModel
			ImageModel oldPreviousImageModel = this.previousImageModel;
			if (oldPreviousImageModel != null && oldPreviousImageModel.getImage() != null) {
				oldPreviousImageModel.close();
			}

			this.previousImageModel = this.imageModel;
			this.imageModel = newImageModel;
			requestRender();
		}
	}

	@Override
	public void render(GraphicsContext g) {
		int bgColor = this.imageModel.getBgColor();
		int fgColor = this.imageModel.getFgColor();
		ResourceImage image = this.imageModel.getImage();

		if (image != null) {
			Display display = Display.getDisplay();
			int displayWidth = display.getWidth();
			int displayHeight = display.getHeight();
			g.setColor(bgColor);
			g.setBackgroundColor(bgColor);
			Painter.fillRectangle(g, 0, 0, displayWidth, displayHeight);
			g.setColor(fgColor);
			Painter.drawImage(g, image, 0, 0);
		} else {
			drawDefault();
		}
		if (!this.texts.isEmpty() && image == null) {
			printTexts(g);
		}
	}

	@Override
	public boolean handleEvent(int event) {
		if (Event.getType(event) == Pointer.EVENT_TYPE && (Buttons.isReleased(event))) {
			toggleImage();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets an error to display.
	 *
	 * @param e
	 *            the throwable to display.
	 */
	public void setError(Throwable e) {
		print(e.getClass().getSimpleName() + PORT_SEPARATION + ' ' + e.getMessage());
		ImageServerLogger.error(e);
		requestRender();
	}

	/**
	 * Sets the port to display.
	 *
	 * @param port
	 *            the port.
	 */
	public void setPort(int port) {
		this.port = port;
	}

	private synchronized void toggleImage() {
		ImageModel tmp = this.previousImageModel;
		if (tmp != null && tmp.getImage() != null) {
			this.previousImageModel = this.imageModel;
			this.imageModel = tmp;
			requestRender();
		}
	}

	private void drawDefault() {
		if (this.connected) {
			printIp();
		} else {
			printWaiting();
		}
	}

	private void printIp() {
		List<String> strings = new ArrayList<>();
		Enumeration<NetworkInterface> networkInterfaces;
		try {
			networkInterfaces = NetworkInterface.getNetworkInterfaces();
			strings.add(Strings.SEND);
			strings.add(Strings.WEB_BROWSER);
			addNetworkInterfaces(strings, networkInterfaces);
			strings.add(Strings.EMPTY);
			strings.add(Strings.SWITCH);
			strings.add(Strings.TOUCHING);
		} catch (SocketException e) {
			strings.add(Strings.IP_ERROR);
			ImageServerLogger.error(e);
		}
		print(strings.toArray(new String[strings.size()]));
	}

	private void addNetworkInterfaces(List<String> strings, @Nullable Enumeration<NetworkInterface> networkInterfaces) {
		if (networkInterfaces != null) {
			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface eth0 = null;
				eth0 = networkInterfaces.nextElement();
				Enumeration<InetAddress> inetAddresses = eth0.getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					InetAddress address = inetAddresses.nextElement();
					if (NetUtil.isValidInetAdress(address)) {
						StringBuilder canonicalHostName = new StringBuilder(HTTP);
						canonicalHostName.append(address.getCanonicalHostName());
						if (this.port != ImageServer.PORT) {
							canonicalHostName.append(PORT_SEPARATION).append(this.port);
						}
						strings.add(canonicalHostName.toString());
					}
				}
			}
		}
	}

	private void printWaiting() {
		List<String> strings = new ArrayList<>();
		strings.add(Strings.WAITING);
		strings.add(Strings.PLUG);
		print(strings.toArray(new String[strings.size()]));
	}

	private void print(String... texts) {
		this.texts.clear();
		for (String text : texts) {
			if (text != null) {
				this.texts.add(text);
			}
		}
	}

	private void printTexts(GraphicsContext g) {
		int y = OFFSET;
		Display display = Display.getDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();

		g.setBackgroundColor(Colors.WHITE);
		g.setColor(Colors.WHITE);
		Painter.fillRectangle(g, 0, 0, displayWidth, displayHeight);
		g.setColor(Colors.BLACK);
		for (String string : this.texts) {
			if (string != null) {
				Painter.drawString(g, string, Font.getFont(FONT_PATH), OFFSET, y);
				y += Font.getDefaultFont().getHeight() + OFFSET / 2;
				ImageServerLogger.info(string);
			}
		}
	}
}
