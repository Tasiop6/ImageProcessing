# CE326 HW3 - Image Processing in Java

This project implements a simple image processing application in Java, supporting both **console-based** and **GUI-based** user interaction.

## Features

- Grayscale conversion
- Image scaling (double and half size)
- 90° clockwise rotation
- RGB <-> YUV conversion
- Histogram equalization (Y channel only)
- PPM and YUV image format support
- Photo stacking from multiple images

## How to Run

Make sure you're using an IDE like IntelliJ IDEA. Then:

### Option 1: Graphical User Interface
Run:
```
ce326.hw3.ImageProcessingUI
```

### Option 2: Console Application
Run:
```
ce326.hw3.ImageProcessingConsole
```

## Project Structure

- `RGBPixel`, `RGBImage`: Handle RGB pixel and image logic
- `YUVPixel`, `YUVImage`: Handle YUV image format and conversions
- `Histogram`: Calculates and equalizes image histogram
- `PPMImage`, `PPMImageStacker`: For reading/writing `.ppm` files and performing image stacking
- `UnsupportedFileFormatException`: Custom exception for invalid formats
- `FileFilters.java`: File filter utilities for choosing images
- `Image`: Interface defining image operations (e.g. `grayscale()`, `doublesize()`)
- `ImageProcessingUI`: Main class for launching the GUI
- `ImageProcessingConsole`: Main class for launching the CLI version

## File Formats Supported

- `.ppm` (Portable Pixmap Format)
- `.yuv` (Custom text-based format as specified)
- Common image formats (PNG, JPG) are also supported in GUI mode (read-only)

## Notes

- `MAX_COLORDEPTH` is fixed at 255.
- For histogram equalization, the Y channel is scaled to a maximum value of 235.
- Pixel operations are done using integer math and bitwise operations.

## Author

Konstantinos Tasiopoulos  
ECE326 - Spring 2025  
University of Thessaly

---
