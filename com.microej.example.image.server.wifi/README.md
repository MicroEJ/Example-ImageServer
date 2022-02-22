# Overview
This example mounts a HTTP server to get an image and display it on the screen. It uses a WiFi connection to upload the image.

1) Connect the board to a network.
2) Use a web browser to go to the IP displayed on the board's screen.
3) Drop your image on the Web page and press upload.
4) You can switch between the last two images by touching the screen.

Advanced settings:
- Format: This will be the format used to uncompress the png, if your platform does not support as many colors, a downscale will be executed at run time:
  - RGB565: There is no alpha, the red color is described with 5 bits, green with 6 bits, blue with 5 bits.
  - RGB888: There is no alpha, the red color is described with 8 bits, green with 8 bits, blue with 8 bits.
  - ARGB8888: The alpha is described with 8 bits, the red color with 8 bits, green with 8 bits, blue with 8 bits.
  - ARGB4444: The alpha is described with 4 bits, the red color with 4 bits, green with 4 bits, blue with 4 bits.
  - ARGB1555: The alpha is described with 1 bit, the red color with 5 bits, green with 5 bits, blue with 5 bits.
  - A8: The alpha is described with 8 bits, the color used will be the color described as Foreground color.
- Foreground color: The HTML code of the color to use for the format A8.
- Background color: The HTML code of the color to use as background.

# Usage
The main class is [com.microej.example.image.server.Main](src\main\java\com\microej\example\image\server\Main.java).

On production you may change the code in [com.microej.example.image.server.ImageServer](src\main\java\com\microej\example\image\server\ImageServer.java)  defining the index in the following way:

ResourceRestEndpoint index = new ResourceRestEndpoint(INDEX_HTML, HTML_FOLDER + INDEX_HTML);


## Run on MicroEJ Simulator
When run on the simulator, the simulator will simply use whatever internet connection you have available on your computer. Any WiFi settings are irrelevant.
You can launch the application using the launcher for SIM and browse to the indicated URL. There you will be able to upload an image. 

A launcher is available:
- `ImageServer WiFi (SIM)`: launches the image server on the simulator of the board.

As exemplary platform, [ESP32WROVER-Platform-GNUv52b96_xtensa-esp32-psram-2.0.0](https://github.com/MicroEJ/Platform-Espressif-ESP-WROVER-KIT-V4.1/) is being used. 


## Run on device
When run on a board, the application will attempt to join a WiFi connection point. The WiFi settings will be read from the SD card inserted in the board. Failing that, the application will attempt to use default, hard coded WiFi settings. 

After building it, you can launch the application using the launcher for EMB and browse to the indicated URL. There you will be able to upload an image. 

#### Build

A launcher is available:
- `ImageServer WiFi (EMB)`: builds a binary of the image server for the board.

As exemplary platform, [ESP32WROVER-Platform-GNUv52b96_xtensa-esp32-psram-2.0.0](https://github.com/MicroEJ/Platform-Espressif-ESP-WROVER-KIT-V4.1/) is being used. 


#### Flash

1. Use the appropriate flashing tool.

    
# Requirements

This example has been tested on:

* MicroEJ `SDK 5.4.0`.
* With the [ESP32WROVER-Platform-GNUv52b96_xtensa-esp32-psram-2.0.0](https://github.com/MicroEJ/Platform-Espressif-ESP-WROVER-KIT-V4.1/) Platform that contains:
    * `EDC-1.3.3`.
    * `BON-1.4.0`.
    * `NET-1.1.2`.
    * `MICROUI-3.0.4`. 
    * `FS-2.0.6`. 

# Dependencies

*All dependencies are retrieved transitively by Ivy resolver*.

# Source

N/A

# Restrictions

None.

---  
_Markdown_   
_Copyright 2021 MicroEJ Corp. All rights reserved._   
_Use of this source code is governed by a BSD-style license that can be found with this software._   
