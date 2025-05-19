package ce326.hw3;

public class RGBImage implements Image {

    public static final int MAX_COLORDEPTH = 255;

    int width;
    int height;
    int colorDepth;
    RGBPixel[][] pixels;

    public RGBImage() {}

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

    //Copy constructor
    public RGBImage(RGBImage copyImg) {
        this(copyImg.width, copyImg.height, copyImg.colorDepth);
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                this.pixels[r][c] = new RGBPixel(copyImg.pixels[r][c]);
            }
        }
    }

    // construct an RGBImage from a YUVImage by converting each pixel
    public RGBImage(YUVImage yuvImg) {
        // copy dimensions
        this.width = yuvImg.getWidth();
        this.height = yuvImg.getHeight();
        this.colorDepth = MAX_COLORDEPTH;

        this.pixels = new RGBPixel[height][width];

        // for each pixel: grab YUV, wrap in RGBPixel(YUVPixel), store
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                YUVPixel yp = yuvImg.getPixel(r, c);
                pixels[r][c] = new RGBPixel(yp);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getColorDepth() {
        return colorDepth;
    }

    public RGBPixel getPixel(int row, int col) {
        return pixels[row][col];
    }

    public void setPixel(int row, int col, RGBPixel pixel) {
        pixels[row][col] = pixel;
    }


    //Image to grayscale using Gray = 0.3*R + 0.59*G + 0.11*B
    @Override
    public void grayscale() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                RGBPixel p = pixels[row][col];                                                    // For each pixel
                double grayValue  = p.getRed()   * 0.3 + p.getGreen() * 0.59 + p.getBlue()  * 0.11; //double value
                int gray = (int)grayValue;                                                          //trunc decimals
                p.setRGB((short)gray, (short)gray, (short)gray);                                  //save short
            }
        }
    }


    //Double the image size by replicating each pixel in a 2x2 block.
    @Override
    public void doublesize() {
        // New dimensions as twice the original
        int newHeight = height * 2;
        int newWidth  = width * 2;
        RGBPixel[][] newPixels = new RGBPixel[newHeight][newWidth];

        // Each pixel into four positions
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                RGBPixel p = pixels[r][c];
                newPixels[2*r][2*c]     = new RGBPixel(p); // top-left
                newPixels[2*r+1][2*c]   = new RGBPixel(p); // bottom-left
                newPixels[2*r][2*c+1]   = new RGBPixel(p); // top-right
                newPixels[2*r+1][2*c+1] = new RGBPixel(p); // bottom-right
            }
        }

        // Update
        pixels = newPixels;
        height = newHeight;
        width  = newWidth;
    }


    //Halves the image size by averaging each 2x2 pixel blocks.
    @Override
    public void halfsize() {
        // new dimensions
        int newHeight = height / 2;
        int newWidth = width  / 2;
        RGBPixel[][] newPixels = new RGBPixel[newHeight][newWidth];

        for (int row = 0; row < newHeight; row++) {
            for (int col = 0; col < newWidth; col++) {
                // 2x2 block
                RGBPixel p1 = pixels[2 * row][2 * col];
                RGBPixel p2 = pixels[2 * row + 1][2 * col];
                RGBPixel p3 = pixels[2 * row][2 * col + 1];
                RGBPixel p4 = pixels[2 * row + 1][2 * col + 1];

                // Sum
                int sumR = p1.getRed()   + p2.getRed() + p3.getRed()   + p4.getRed();
                int sumG = p1.getGreen() + p2.getGreen() + p3.getGreen() + p4.getGreen();
                int sumB = p1.getBlue()  + p2.getBlue() + p3.getBlue()  + p4.getBlue();

                // Trunc decimal
                short avgR = (short)(sumR / 4.0);
                short avgG = (short)(sumG / 4.0);
                short avgB = (short)(sumB / 4.0);

                // create the new colored pixel
                newPixels[row][col] = new RGBPixel(avgR, avgG, avgB);
            }
        }

        // Update
        pixels = newPixels;
        height = newHeight;
        width  = newWidth;
    }

    //Rotates the image 90 degrees clockwise.
    @Override
    public void rotateClockwise() {
        // new dimensions: swapped
        int newHeight = width;
        int newWidth  = height;
        RGBPixel[][] newPixels = new RGBPixel[newHeight][newWidth];

        // map each old pixel (r,c) to its new position
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // rotation : (row,col) -> (col, newWidth-1-row)
                newPixels[col][newWidth - 1 - row] = pixels[row][col];
            }
        }

        // update
        pixels = newPixels;
        width  = newWidth;
        height = newHeight;
    }
}
