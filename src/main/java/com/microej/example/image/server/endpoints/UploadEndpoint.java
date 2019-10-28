/*
 * Java
 *
 * Copyright 2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.image.server.endpoints;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;

import com.microej.example.image.server.ImageDisplay;
import com.microej.example.image.server.ImageModel;
import com.microej.example.image.server.ImageServer;

import ej.hoka.http.HTTPRequest;
import ej.hoka.http.HTTPResponse;
import ej.hoka.http.body.HTTPPart;
import ej.hoka.http.body.MultiPartBodyParser;
import ej.microui.display.Colors;
import ej.microui.display.Image;
import ej.microui.display.Image.OutputFormat;
import ej.restserver.RestEndpoint;

/**
 * Endpoint receiving the image.
 */
public class UploadEndpoint extends RestEndpoint {

	private static final String HEADER_NAME = "name"; //$NON-NLS-1$
	private static final String HEADER_FILE = "file"; //$NON-NLS-1$
	private static final String HEADER_FORMAT = "format"; //$NON-NLS-1$
	private static final String HEADER_COLOR = "Color"; //$NON-NLS-1$
	private static final String HEADER_FG_COLOR = "fg" + HEADER_COLOR; //$NON-NLS-1$
	private static final String HEADER_BG_COLOR = "bg" + HEADER_COLOR; //$NON-NLS-1$
	private static final String HEADER_FILE_SIZE = "fileSize"; //$NON-NLS-1$
	private static final String FORMAT_1555 = "1555"; //$NON-NLS-1$
	private static final String FORMAT_4444 = "4444"; //$NON-NLS-1$
	private static final String FORMAT_RGB = "RGB"; //$NON-NLS-1$
	private static final String FORMAT_888 = "888"; //$NON-NLS-1$
	private static final String FORMAT_A8 = "A8"; //$NON-NLS-1$
	private static final String FORMAT_RGB888 = FORMAT_RGB + FORMAT_888;
	private static final String FORMAT_ARGB8888 = 'A' + FORMAT_RGB + '8' + FORMAT_888;
	private static final String FORMAT_ARGB4444 = 'A' + FORMAT_RGB + FORMAT_4444;
	private static final String FORMAT_ARGB1555 = 'A' + FORMAT_RGB + FORMAT_1555;
	private static final int BUFFER_SIZE = 512;
	private static final int HEX_RADIX = 16;
	private static final String HTML_COLOR_PREFIX = "#"; //$NON-NLS-1$
	private static final String URI = "/upload"; //$NON-NLS-1$
	private final ImageDisplay imageDisplay;

	/**
	 * Instantiates an {@link UploadEndpoint}.
	 *
	 * @param imageDisplay
	 *            the {@link ImageDisplay} to use to print the image.
	 */
	public UploadEndpoint(ImageDisplay imageDisplay) {
		super(URI);
		this.imageDisplay = imageDisplay;
	}

	@Override
	public HTTPResponse post(HTTPRequest request, Map<String, String> headers, Map<String, String> parameters) {

		MultiPartBodyParser stringBodyParser = new MultiPartBodyParser();
		OutputFormat format = OutputFormat.RGB565;
		int imageSize = 0;
		int fgColor = Colors.BLACK;
		int bgColor = Colors.WHITE;

		Image image = null;
		try {
			stringBodyParser.parseBody(request);
			while (image == null) {
				try (HTTPPart partIS = stringBodyParser.nextPart()) {
					if (partIS == null) {
						break;
					}
					Map<String, String> parseHeaders = partIS.getHeaders();
					String name = parseHeaders.get(HEADER_NAME);
					if (name != null) {
						if (name.equalsIgnoreCase(HEADER_FILE)) {
							image = Image.createImage(partIS, imageSize, format);
						} else if (name.equalsIgnoreCase(HEADER_FORMAT)) {
							switch (read(partIS).trim()) {
							case FORMAT_A8:
								format = OutputFormat.A8;
								break;
							case FORMAT_ARGB1555:
								format = OutputFormat.ARGB1555;
								break;
							case FORMAT_ARGB4444:
								format = OutputFormat.ARGB4444;
								break;
							case FORMAT_ARGB8888:
								format = OutputFormat.ARGB8888;
								break;
							case FORMAT_RGB888:
								format = OutputFormat.RGB888;
								break;
							default:
								format = OutputFormat.RGB565;
								break;
							}
						} else if (name.equalsIgnoreCase(HEADER_FG_COLOR)) {
							fgColor = toColor(read(partIS).trim(), fgColor);
						} else if (name.equalsIgnoreCase(HEADER_BG_COLOR)) {
							bgColor = toColor(read(partIS).trim(), bgColor);
						} else if (name.equalsIgnoreCase(HEADER_FILE_SIZE)) {
							imageSize = Integer.parseInt(read(partIS));
						}
					}
				}
			}
		} catch (Throwable e) {
			imageDisplay.setError(e);
			ImageServer.LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
		try {
			if (image != null) {
				imageDisplay.setImage(new ImageModel(image, fgColor, bgColor));
				return HTTPResponse.RESPONSE_OK;
			}
		} catch (Throwable e) {
			imageDisplay.setError(e);
			ImageServer.LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
		return HTTPResponse.RESPONSE_BAD_REQUEST;
	}

	private static String read(InputStream stream) throws IOException {
		StringBuilder body = new StringBuilder(stream.available());
		int readLen = -1;
		byte[] buff = new byte[BUFFER_SIZE];
		while (true) {
			readLen = stream.read(buff);
			if (readLen == -1) {
				break;
			}

			body.append(new String(buff, 0, readLen));
		}
		return body.toString();
	}

	private static int toColor(String colorString, int defaultColor) {
		int color = defaultColor;

		if (colorString != null && colorString.length() > 0 && colorString.startsWith(HTML_COLOR_PREFIX)) {
			try {
				color = Integer.parseInt(colorString.substring(1), HEX_RADIX);
			} catch (NumberFormatException e) {
				ImageServer.LOGGER.log(Level.WARNING, e.getMessage(), e);
			}
		}

		return color;
	}

}
