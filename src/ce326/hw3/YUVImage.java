package ce326.hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class YUVImage {
    private int width;
    private int height;
    private YUVPixel[][] pixels;

    // Empty image with Y=16, U=128, V=128
    public YUVImage(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new YUVPixel[height][width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                pixels[r][c] = new YUVPixel((short) 16, (short) 128, (short) 128);
            }
        }
    }

    // Copy constructor
    public YUVImage(YUVImage img) {
        this.width = img.width;
        this.height = img.height;
        pixels = new YUVPixel[height][width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                pixels[r][c] = new YUVPixel(img.pixels[r][c]);
            }
        }
    }

    // RGBImage to YUVImage
    public YUVImage(RGBImage rgb) {
        this.width = rgb.getWidth();
        this.height = rgb.getHeight();
        pixels = new YUVPixel[height][width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                pixels[r][c] = new YUVPixel(rgb.getPixel(r, c));
            }
        }
    }

    // Read from a .yuv file
    public YUVImage(File file) throws FileNotFoundException, UnsupportedFileFormatException {
        Scanner in = new Scanner(file);
        String header = in.next();
        if (!header.equals("YUV3")) {
            in.close();
            throw new UnsupportedFileFormatException("Unsupported format: " + header);
        }
        width = in.nextInt();                          // read width
        height = in.nextInt();                          // read height
        pixels = new YUVPixel[height][width];
        // read Y U V for each pixel
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                short Y = (short) in.nextInt();
                short U = (short) in.nextInt();
                short V = (short) in.nextInt();
                pixels[r][c] = new YUVPixel(Y, U, V);
            }
        }
        in.close();
    }

    // YUV3  format String
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("YUV3\n");
        sb.append(width).append(" ").append(height).append("\n");
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                YUVPixel p = pixels[r][c];
                sb.append(p.getY()).append(" ")
                        .append(p.getU()).append(" ")
                        .append(p.getV()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // image to a .yuv file
    public void toFile(File file) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(file)) {
            out.print(this.toString());
        }
    }

    // Equalize histogram in the Y* channel (max Y = 235) (*Y controls brightness)
    public void equalize() {
        int[] hist = new int[256];
        int total = width * height;

        // Step 1: Histogram of Y values
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                hist[pixels[r][c].getY()]++;
            }
        }

        // Step 2: Cumulative distribution function (CDF) from histogram
        int[] cdf = new int[256];
        cdf[0] = hist[0];
        for (int i = 1; i < 256; i++) {
            cdf[i] = cdf[i - 1] + hist[i];
        }

        // Step 3: Lookup table (LUT) to map old Y to new Y
        int maxY = 235;
        int[] lut = new int[256];
        for (int i = 0; i < 256; i++) {
            // integer division drops fractional part
            lut[i] = (cdf[i] * maxY) / total;
        }

        // Step 4: Apply the LUT to each pixel's Y value
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                short newY = (short) lut[pixels[r][c].getY()];
                pixels[r][c].setY(newY);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public YUVPixel getPixel(int row, int col) {
        return pixels[row][col];
    }
}