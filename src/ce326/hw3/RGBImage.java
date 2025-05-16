package ce326.hw3;

public class RGBImage implements Image {
    /**
     * Maximum allowed color depth.
     */
    public static final int MAX_COLORDEPTH = 255;

    int width;
    int height;
    int colorDepth;
    RGBPixel[][] pixels;

    /**
     * Default constructor. Required for subclassing (e.g., PPMImage).
     */
    public RGBImage() {
        // no functionality
    }

    /**
     * Constructs an RGBImage of given dimensions and color depth.
     *
     * @param width      image width
     * @param height     image height
     * @param colorDepth maximum brightness (0..MAX_COLORDEPTH)
     */
    public RGBImage(int width, int height, int colorDepth) {
        this.width = width;
        this.height = height;
        this.colorDepth = colorDepth;
        pixels = new RGBPixel[height][width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                pixels[r][c] = new RGBPixel((short) 0, (short) 0, (short) 0);
            }
        }
    }

    /**
     * Copy constructor. Creates a deep copy of another RGBImage.
     *
     * @param copyImg image to copy
     */
    public RGBImage(RGBImage copyImg) {
        this(copyImg.width, copyImg.height, copyImg.colorDepth);
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                this.pixels[r][c] = new RGBPixel(copyImg.pixels[r][c]);
            }
        }
    }

    public RGBImage(YUVImage YUVImg) {
        // TODO: convert YUVImage to RGBImage
    }

    // Return image width
    public int getWidth() {
        return width;
    }

    /**
     * @return image height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return color depth
     */
    public int getColorDepth() {
        return colorDepth;
    }

    /**
     * Returns the pixel at (row, col).
     *
     * @param row row index (0..height-1)
     * @param col column index (0..width-1)
     * @return RGBPixel at given position
     */
    public RGBPixel getPixel(int row, int col) {
        return pixels[row][col];
    }

    /**
     * Sets the pixel at (row, col).
     *
     * @param row   row index (0..height-1)
     * @param col   column index (0..width-1)
     * @param pixel pixel to set
     */
    public void setPixel(int row, int col, RGBPixel pixel) {
        pixels[row][col] = pixel;
    }

    /**
     * Converts the image to grayscale using Gray = 0.3*R + 0.59*G + 0.11*B
     */
    @Override
    public void grayscale() {

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                RGBPixel pixel = pixels[row][col];
                short gray  = getGray(pixel);
                pixel.setRGB(gray, gray, gray);
            }
        }
    }

    /**
     * Doubles the image size by replicating each pixel in a 2x2 block.
     */
    @Override
    public void doublesize() {
        // compute new dimensions as twice the original
        int newHeight = height * 2;
        int newWidth  = width * 2;
        // create new pixel array with doubled resolution
        RGBPixel[][] newPixels = new RGBPixel[newHeight][newWidth];

        // duplicate each pixel into four positions
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                RGBPixel p = pixels[r][c];
                newPixels[2*r][2*c]     = new RGBPixel(p); // top-left
                newPixels[2*r+1][2*c]   = new RGBPixel(p); // bottom-left
                newPixels[2*r][2*c+1]   = new RGBPixel(p); // top-right
                newPixels[2*r+1][2*c+1] = new RGBPixel(p); // bottom-right
            }
        }

        // update the image with the new pixels and dimensions
        pixels = newPixels;
        height = newHeight;
        width  = newWidth;
    }

    /**
     * Halves the image size by averaging each 2x2 block's brightness.
     */
    @Override
    public void halfsize() {
        // compute new dimensions as half of the original
        int newHeight = height / 2;
        int newWidth  = width / 2;
        // create new pixel array at half resolution
        RGBPixel[][] newPixels = new RGBPixel[newHeight][newWidth];

        // for each pixel in the smaller image
        for (int row = 0; row < newHeight; row++) {
            for (int col = 0; col < newWidth; col++) {

                // get the four corresponding source pixels
                RGBPixel p1 = pixels[2*row][2*col];
                RGBPixel p2 = pixels[2*row+1][2*col];
                RGBPixel p3 = pixels[2*row][2*col+1];
                RGBPixel p4 = pixels[2*row+1][2*col+1];

                // compute grayscale values for each
                short g1 = getGray(p1);
                short g2 = getGray(p2);
                short g3 = getGray(p3);
                short g4 = getGray(p4);

                double average = (g1 + g2 + g3 + g4) / 4.0;
                // round to nearest integer
                short gray = (short) Math.round(average);

                // create a gray pixel and place it in the new array
                newPixels[row][col] = new RGBPixel(gray, gray, gray);
            }
        }

        // replace old pixel data and update dimensions
        pixels = newPixels;
        height = newHeight;
        width  = newWidth;
    }

    /**
     * Rotates the image 90 degrees clockwise.
     */
    @Override
    public void rotateClockwise() {
        // new dimensions: swapped
        int newHeight = width;
        int newWidth  = height;
        // allocate a new array for the rotated image
        RGBPixel[][] newPixels = new RGBPixel[newHeight][newWidth];

        // map each old pixel (r,c) to its new position
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // rotation formula: (row,col) -> (col, newWidth-1-row)
                newPixels[col][newWidth - 1 - row] = pixels[row][col];
            }
        }

        // swap in the rotated data and dimensions
        pixels = newPixels;
        width  = newWidth;
        height = newHeight;
    }

    // Extra

    private short getGray(RGBPixel p) {
        // weighted sum for luminosity
        double val = p.getRed() * 0.3
                + p.getGreen() * 0.59
                + p.getBlue() * 0.11;
        // round to nearest integer
        return (short) Math.round(val);
    }
}
