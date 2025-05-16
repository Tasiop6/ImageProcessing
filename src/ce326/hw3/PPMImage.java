package ce326.hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class PPMImage extends RGBImage {

    // default constructor
    public PPMImage() {
        super();
    }

    // read a P3 PPM file
    public PPMImage(File file) throws FileNotFoundException, UnsupportedFileFormatException {
        Scanner in = new Scanner(file);
        String magic = in.next();              // read "P3"
        if (!magic.equals("P3")) {
            in.close();
            throw new UnsupportedFileFormatException("Unsupported format: " + magic);
        }
        int w = in.nextInt();                  // image width
        int h = in.nextInt();                  // image height
        int maxVal = in.nextInt();             // max color depth

        // initialize inherited fields
        super.width = w;
        super.height = h;
        super.colorDepth = maxVal;
        super.pixels = new RGBPixel[h][w];

        // read pixels in row-major order
        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                short red   = (short) in.nextInt();
                short green = (short) in.nextInt();
                short blue  = (short) in.nextInt();
                pixels[r][c] = new RGBPixel(red, green, blue);
            }
        }
        in.close();
    }

    // construct from an existing RGBImage
    public PPMImage(RGBImage img) {
        super(img);
    }

    // construct from a YUVImage
    public PPMImage(YUVImage img) {
        super(img);
    }

    // return the PPM file content (P3 format)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("P3\n")
                .append(width).append(" ").append(height).append("\n")
                .append(colorDepth).append("\n");
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                RGBPixel p = pixels[r][c];
                sb.append(p.getRed()).append(" ")
                        .append(p.getGreen()).append(" ")
                        .append(p.getBlue()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // write this image out as a P3 PPM file
    public void toFile(File file) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(file)) {
            out.print(this.toString());
        }
    }
}
