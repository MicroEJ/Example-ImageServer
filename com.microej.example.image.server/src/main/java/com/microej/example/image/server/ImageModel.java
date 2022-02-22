/*
 * Java
 *
 * Copyright 2018-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */

package com.microej.example.image.server;

import ej.annotation.Nullable;
import ej.microui.display.Colors;
import ej.microui.display.ResourceImage;

/**
 * The model of an image.
 */
public class ImageModel {

	private @Nullable ResourceImage image;
	private int fgColor = Colors.BLACK;
	private int bgColor = Colors.WHITE;

	/**
	 * Instantiates an {@link ImageModel}.
	 *
	 * @param image
	 *            the image to display.
	 * @param fgColor
	 *            the foreground color.
	 * @param bgColor
	 *            the background color.
	 */
	public ImageModel(@Nullable ResourceImage image, int fgColor, int bgColor) {
		super();
		this.image = image;
		this.fgColor = fgColor;
		this.bgColor = bgColor;
	}

	/**
	 * Gets the image.
	 *
	 * @return the image.
	 */
	public @Nullable ResourceImage getImage() {
		return this.image;
	}

	/**
	 * Sets the image.
	 *
	 * @param image
	 *            the image to set.
	 */
	public void setImage(ResourceImage image) {
		this.image = image;
	}

	/**
	 * Gets the fgColor.
	 *
	 * @return the fgColor.
	 */
	public int getFgColor() {
		return this.fgColor;
	}

	/**
	 * Sets the fgColor.
	 *
	 * @param fgColor
	 *            the fgColor to set.
	 */
	public void setFgColor(int fgColor) {
		this.fgColor = fgColor;
	}

	/**
	 * Gets the bgColor.
	 *
	 * @return the bgColor.
	 */
	public int getBgColor() {
		return this.bgColor;
	}

	/**
	 * Sets the bgColor.
	 *
	 * @param bgColor
	 *            the bgColor to set.
	 */
	public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
	}

	/**
	 * Closes the image.
	 */
	public void close() {
		if (this.image != null) {
			this.image.close();
		}
	}
}
