/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.image.server;

import ej.microui.display.Colors;
import ej.microui.display.Image;

/**
 * The model of an image.
 */
public class ImageModel {

	private Image image;
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
	public ImageModel(Image image, int fgColor, int bgColor) {
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
	public Image getImage() {
		return image;
	}

	/**
	 * Sets the image.
	 *
	 * @param image
	 *            the image to set.
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Gets the fgColor.
	 *
	 * @return the fgColor.
	 */
	public int getFgColor() {
		return fgColor;
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
		return bgColor;
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

}
