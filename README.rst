.. Copyright 2021 MicroEJ Corp. All rights reserved.
   Use of this source code is governed by a BSD-style license that can be found with this software.

.. image:: https://shields.microej.com/endpoint?url=https://repository.microej.com/packages/badges/sdk_5.4.json
.. image:: https://shields.microej.com/endpoint?url=https://repository.microej.com/packages/badges/arch_7.14.json
.. image:: https://shields.microej.com/endpoint?url=https://repository.microej.com/packages/badges/gui_3.json

=====================
Example: Image Server
=====================

Overview
========

This repository contains the project sources of the Example Image Server project.
It also contains an extended version featuring WiFi connectivity, in case a board with WiFi capabilities is used.

This example mounts a HTTP server to get an image and display it on the screen.

Repository Structure
====================

The following projects that can be found in this repository are:

-  `com.microej.example.image.server <com.microej.example.image.server>`_ : this project is the base project, used for boards with Ethernet capabilities.
-  `com.microej.example.image.server.wifi <com.microej.example.image.server.wifi>`_ : this project uses the base project and provides WiFi capabilities.
