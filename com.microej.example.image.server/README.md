<img src="https://shields.microej.com/endpoint?url=https://repository.microej.com/packages/badges/sdk_5.4.json" /> 
<img src="https://shields.microej.com/endpoint?url=https://repository.microej.com/packages/badges/arch_7.14.json" /> 
<img src="https://shields.microej.com/endpoint?url=https://repository.microej.com/packages/badges/gui_3.json" />

# Overview
This example mounts a HTTP server to get an image and display it on the screen.

1) Connect the board to a network.
2) Use a web browser to go to the IP displayed on the board's screen.
3) Drop your image on the Web page and press upload.
4) You can switch between the last two images by touching the screen.

Advanced settings:
- Format: This will be the format used to unarchive the png, if your platform does not support as many colors, a downscale will be executed at run time:
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

On develop you may change the code in [com.microej.example.image.server.ImageServer](src\main\java\com\microej\example\image\server\ImageServer.java) defining the index in the following way:

ResourceRestEndpoint index = new ResourceRestEndpoint(INDEX_HTML, HTML_FOLDER + INDEX_HTML);

On production, define the index in the following way:

ResourceRestEndpoint index = new GzipResourceEndpoint(INDEX_HTML, HTML_FOLDER + INDEX_HTML + GZ);


## Run on MicroEJ Simulator

A launcher is available:
- `ImageServer (SIM)`: launches the image server on the simulator of the STM32F7508 board.

As exemplary platform, [STM32F7508-Platform-CM7hardfp_GCC48 (1.3.2)](https://github.com/MicroEJ/Platform-STMicroelectronics-STM32F7508-DK/) is being used. 


## Run on device
#### Build

A launcher is available:
- `ImageServer (EMB)`: builds a binary of the image server for the STM32F7508 board.

As exemplary platform, [STM32F7508-Platform-CM7hardfp_GCC48 (1.3.2)](https://github.com/MicroEJ/Platform-STMicroelectronics-STM32F7508-DK/) is being used. 

#### Flash

1. Use the appropriate flashing tool.

    
# Requirements

This example has been tested on:

* MicroEJ `SDK 5.4.0`.
* With the [STM32F7508-Platform-CM7hardfp_GCC48 (1.3.2)](https://github.com/MicroEJ/Platform-STMicroelectronics-STM32F7508-DK/) Platform that contains:
    * `EDC-1.3.3`.
    * `BON-1.4.0`.
    * `NET-1.1.1`.
    * `MICROUI-3.0.3`.

# Dependencies

*All dependencies are retrieved transitively by Ivy resolver*.

# Source

N/A

# Restrictions

None.

---  
_Markdown_   
_Copyright 2018-2022 MicroEJ Corp. All rights reserved._   
_Use of this source code is governed by a BSD-style license that can be found with this software._   
