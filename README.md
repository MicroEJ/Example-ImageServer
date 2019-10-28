# Overview

This example mounts a HTTP server to get an image and display it on the screen.

1) Connect the board to a network.
2) Use a web browser to go to the IP displayed on the board's screen.
3) Drop your image on the Web page and press upload.
4) You can switch between the last two images by touching the screen.

Advanced settings:
- Format: the will be the format used to uncompress the png, if your platform does not support as many colors, a downscale will be executed at run time:
  - RGB565: There is no alpha, the red color is described with 5 bits, green with 6 bits, blue with 5 bits.
  - RGB888: There is no alpha, the red color is described with 8 bits, green with 8 bits, blue with 8 bits.
  - ARGB8888: The alpha is described with 8 bits, the red color with 8 bits, green with 8 bits, blue with 8 bits.
  - ARGB4444: The alpha is described with 4 bits, the red color with 4 bits, green with 4 bits, blue with 4 bits.
  - ARGB1555: The alpha is described with 1 bit, the red color with 5 bits, green with 5 bits, blue with 5 bits.
  - A8: The alpha is described with 8 bits, the color used will be the color described as Foreground color.
- Foreground color: The HTML code of the color to use for the format A8.
- Background color: The HTML code of the color to use as background.

# Usage

## Run the demo

### Run on MicroEJ Simulator

1. Right Click on [com.microej.example.image.server.SimpleServer](src\main\java\com\microej\example\image\server\SimpleServer.java)
2. Select **Run as -> MicroEJ Application**
3. The demo will be running on the PC localhost.
4. Follow the instruction on the display.

### Run on device

#### Build

1. Right Click on [com.microej.example.image.server.SimpleServer](src\main\java\com\microej\example\image\server\SimpleServer.java)
2. Select **Run as -> Run Configuration**
3. Select **MicroEJ Application** configuration kind
4. Click on **New launch configuration** icon
5. In **Execution** tab
   1. In **Target** frame, in **Platform** field, select a relevant platform (but not a virtual device)
   2. In **Execution** frame
      1. Select **Execute on Device**
      2. In **Settings** field, select **Build & Deploy**
   3. In the **Configuration** frame, go to **Target**
      1. Set the **Java heap size** to `400000`
      1. Set the **Immortal heap size** to `15000`
      2. Set the **Number of threads** to `7`
      3. Set the **Number of blocks in pool** to `20`
6. Press **Apply**
7. Press **Run**
8. Copy the generated `.out` file path shown by the console

#### Flash

1. Use the appropriate flashing tool.

    
# Requirements

  - EDC-1.2 or higher
  - BON-1.2 or higher
  - NET-1.1 or higher
  - MICROUI-2.0 or higher

# Dependencies

_All dependencies are retrieved transitively by Ivy resolver_.

# Source

N/A

# Restrictions

None.

---  
_Markdown_   
_Copyright 2018-2019 MicroEJ Corp. All rights reserved._   
_Use of this source code is governed by a BSD-style license that can be found with this software._   
_MicroEJ Corp. PROPRIETARY. Use is subject to license terms._  