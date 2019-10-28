/*
 * Java
 *
 * Copyright 2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.image.server.sandboxed;

import java.util.logging.Level;

import com.microej.example.image.server.ImageServer;

import ej.wadapps.app.Activity;

/**
 * Activity wrapping the {@link ImageServer}.
 */
public class DivAppActivity implements Activity {

	private ImageServer imageServer;

	@Override
	public String getID() {
		return "DivAppActivity"; //$NON-NLS-1$
	}

	@Override
	public void onCreate() {
		// Nothing to do.

	}

	@Override
	public void onRestart() {
		// Nothing to do.

	}

	@Override
	public void onStart() {
		imageServer = new ImageServer();
		try {
			imageServer.start();
		} catch (InterruptedException e) {
			ImageServer.LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public void onResume() {
		// Nothing to do.

	}

	@Override
	public void onPause() {
		// Nothing to do.

	}

	@Override
	public void onStop() {
		imageServer.stop();
	}

	@Override
	public void onDestroy() {
		// Nothing to do.

	}

}
