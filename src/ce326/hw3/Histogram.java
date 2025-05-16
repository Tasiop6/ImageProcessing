package ce326.hw3;

import java.io.File;
import java.io.PrintWriter;

// creates and manages a histogram for a YUVImage
public class Histogram {
    private YUVImage img;      // source YUV image
    private int[] hist;        // raw counts of each Y intensity (0-255)
    private int[] lut;         // lookup table for equalized Y values
    private int maxY = 235;    // target maximum Y after equalization

    // build histogram and LUT for the given image
    public Histogram(YUVImage img) {
        this.img = img;
        int w = img.getWidth();
        int h = img.getHeight();
        int total = w * h;     // total pixel count (used for normalization)

        hist = new int[256];
        lut  = new int[256];

        // compute histogram of Y channel
        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                int y = img.getPixel(r, c).getY();
                hist[y]++;
            }
        }

        // build lookup table in one pass (running sum avoids separate CDF array)
        int runningSum = 0;
        for (int i = 0; i < 256; i++) {
            runningSum += hist[i];
            // scale cumulative count to [0..maxY]
            lut[i] = (runningSum * maxY) / total;
        }
    }

    // format the histogram as string according to spec
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            int count = hist[i];
            sb.append("\n")
                    .append(String.format("%3d.", i))    // intensity label
                    .append("[")
                    .append(String.format("%4d", count)) // count in brackets
                    .append("]\t");
            // decompose count digits
            int thousands = count / 1000;
            int hundreds  = (count % 1000) / 100;
            int tens      = (count % 100)  / 10;
            int ones      = count % 10;
            // append symbols for each place value
            for (int t = 0; t < thousands; t++) sb.append('#');
            for (int h2 = 0; h2 < hundreds;  h2++) sb.append('$');
            for (int t2 = 0; t2 < tens;      t2++) sb.append('@');
            for (int o = 0; o < ones;       o++) sb.append('*');
        }
        sb.append("\n");  // extra newline at end
        return sb.toString();
    }

    // write the histogram string to a file
    public void toFile(File file) throws java.io.FileNotFoundException {
        try (PrintWriter out = new PrintWriter(file)) {
            out.print(this.toString());
        }
    }

    // apply histogram equalization to the image's Y channel
    public void equalize() {
        int w = img.getWidth();
        int h = img.getHeight();
        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                int oldY = img.getPixel(r, c).getY();
                // use LUT to get new Y, then set
                short newY = (short) lut[oldY];
                img.getPixel(r, c).setY(newY);
            }
        }
    }

    // return the equalized Y for a given original intensity
    public short getEqualizedLuminosity(int luminosity) {
        return (short) lut[luminosity];
    }
}

