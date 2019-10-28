/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.image.server;

import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;

import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.exit.ExitHandler;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.ExplicitFlush;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.microui.util.EventHandler;
import ej.net.util.NetUtil;

/**
 * A class to display an image.
 */
public class ImageDisplay extends Displayable implements EventHandler {

	private static final int FONT_SIZE = 18;

	/**
	 * ":".
	 */
	private static final String PORT_SEPARATION = ":"; //$NON-NLS-1$

	/**
	 * http://.
	 */
	private static final String HTTP = "http://"; //$NON-NLS-1$


	private static final int ERROR_DELAY = 5_000;

	private static final int OFFSET = 10;

	private static final long LONGPRESS_DURATION = 1000;

	private int port;

	private ImageModel imageModel;

	private TimerTask longPressTask;

	private WeakReference<ImageModel> previousImage;

	private boolean connected;

	/**
	 * Instantiates an {@link ImageDisplay}.
	 */
	public ImageDisplay() {
		super(Display.getDefaultDisplay());
		previousImage = new WeakReference<>(null);
		imageModel = new ImageModel(null, Colors.BLACK, Colors.WHITE);
	}

	/**
	 * Sets the displayed text as connected.
	 * 
	 * @param connected
	 *            the connected state.
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
		repaint();
	}


	/**
	 * Sets the image.
	 *
	 * @param image
	 *            the image to set.
	 */
	public synchronized void setImage(ImageModel image) {
		if (image.getImage() != null) {
			previousImage = new WeakReference<>(imageModel);
			this.imageModel = image;
			repaint();
		}
	}

	@Override
	public void paint(GraphicsContext g) {
		int bgColor = imageModel.getBgColor();
		int fgColor = imageModel.getFgColor();
		Image image = imageModel.getImage();

		if (image != null) {
			Display display = Display.getDefaultDisplay();
			int displayWidth = display.getWidth();
			int displayHeight = display.getHeight();
			g.setColor(bgColor);
			g.setBackgroundColor(bgColor);
			g.fillRect(0, 0, displayWidth, displayHeight);
			g.setColor(fgColor);
			g.drawImage(image, 0, 0, 0);
		} else {
			drawDefault();
		}
	}

	@Override
	public EventHandler getController() {
		return this;
	}

	@Override
	protected void hideNotify() {
		cancelLongPress();
		super.hideNotify();
	}

	@Override
	public boolean handleEvent(int event) {
		if (Event.getGeneratorID(event) == Event.POINTER) {
			if (Pointer.isReleased(event)) {
				cancelLongPress();
				toggleImage();
				return true;
			} else if (Pointer.isPressed(event) || Pointer.isEntered(event)) {
				startLongPress();
			}
		} else if (Event.getGeneratorID(event) == Event.BUTTON) {
			if (Buttons.isReleased(event) || Buttons.isClicked(event)) {
				toggleImage();
				return true;
			}
		}
		return false;
	}

	/**
	 * Sets an error to display.
	 *
	 * @param e
	 *            the throwable to display.
	 */
	public void setError(Throwable e) {
		print(e.getClass().getSimpleName() + PORT_SEPARATION + ' ' + e.getMessage());
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(ERROR_DELAY);
				} catch (InterruptedException e) {
					ImageServer.LOGGER.log(Level.WARNING, e.getMessage(), e);
				}
				repaint();

			}
		}).start();
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
		ImageModel tmp = previousImage.get();
		if (tmp != null && tmp.getImage() != null) {
			previousImage = new WeakReference<ImageModel>(imageModel);
			imageModel = tmp;
			repaint();
		}
	}

	private void drawDefault() {
		if (connected) {
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
			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface eth0 = null;
				eth0 = networkInterfaces.nextElement();
				Enumeration<InetAddress> inetAddresses = eth0.getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					InetAddress address = inetAddresses.nextElement();
					if (NetUtil.isValidInetAdress(address)) {
						StringBuilder canonicalHostName = new StringBuilder(HTTP);
						canonicalHostName.append(address.getCanonicalHostName());
						if (port != ImageServer.PORT) {
							canonicalHostName.append(PORT_SEPARATION).append(port);
						}
						strings.add(canonicalHostName.toString());
					}
				}
			}
			strings.add(Strings.EMPTY);
			strings.add(Strings.SWITCH);
			strings.add(Strings.TOUCHING);
			if (ServiceLoaderFactory.getServiceLoader().getService(ExitHandler.class) != null) {
				strings.add(Strings.EMPTY);
				strings.add(Strings.EXIT);
			}
		} catch (SocketException e) {
			strings.add(Strings.IP_ERROR);
			strings.add(e.getMessage());
			ImageServer.LOGGER.log(Level.WARNING, Strings.IP_ERROR, e);
		}
		print(strings.toArray(new String[strings.size()]));
	}

	private void printWaiting() {
		List<String> strings = new ArrayList<>();
		strings.add(Strings.WAITING);
		strings.add(Strings.PLUG);
		ExitHandler exitHandler = ServiceLoaderFactory.getServiceLoader().getService(ExitHandler.class);
		if (exitHandler != null) {
			strings.add(Strings.EMPTY);
			strings.add(Strings.EXIT);
		}
		print(strings.toArray(new String[strings.size()]));
	}

	private static void print(String... text) {
		int y = OFFSET;
		Display display = Display.getDefaultDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();
		ExplicitFlush g = display.getNewExplicitFlush();
		g.setBackgroundColor(Colors.WHITE);
		g.setFont(Font.getFont(Font.LATIN, FONT_SIZE, Font.STYLE_PLAIN));
		g.setColor(Colors.WHITE);
		g.fillRect(0, 0, displayWidth, displayHeight);
		g.setColor(Colors.BLACK);
		for (String string : text) {
			g.drawString(string, OFFSET, y, GraphicsContext.TOP | GraphicsContext.LEFT);
			y += Font.getDefaultFont().getHeight() + OFFSET / 2;
			ImageServer.LOGGER.log(Level.INFO, string);
		}
		g.flush();
	}


	private void cancelLongPress() {
		TimerTask pressTask = longPressTask;
		longPressTask = null;
		if (pressTask != null) {
			pressTask.cancel();
		}
	}

	private void startLongPress() {
		if (longPressTask == null && ServiceLoaderFactory.getServiceLoader().getService(ExitHandler.class) != null) {
			longPressTask = new TimerTask() {

				@Override
				public void run() {
					ExitHandler exitHandler = ServiceLoaderFactory.getServiceLoader().getService(ExitHandler.class);
					if (exitHandler != null) {
						exitHandler.exit();
					}
					longPressTask = null;
				}
			};
			ServiceLoaderFactory.getServiceLoader().getService(Timer.class, Timer.class).schedule(longPressTask,
					LONGPRESS_DURATION);
		}
	}

}
