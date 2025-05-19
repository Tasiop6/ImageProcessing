package ce326.hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

// Loads multiple PPM images from a directory and stacks them
public class PPMImageStacker {
    private List<PPMImage> images = new ArrayList<>();  // list of input images
    private PPMImage stackedImage;                       // result after stacking

    // Load all PPM files from dir
    public PPMImageStacker(File dir) throws FileNotFoundException, UnsupportedFileFormatException {
        if (!dir.exists()) {
            // directory does not exist
            throw new FileNotFoundException("[ERROR] Directory " + dir + " does not exist!");
        }
        if (!dir.isDirectory()) {
            // path exists but is not a directory
            throw new FileNotFoundException("[ERROR] " + dir + " is not a directory!");
        }
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (!f.isFile() || !f.getName().toLowerCase().endsWith(".ppm")) {
                    // found a non-PPM file
                    throw new UnsupportedFileFormatException("Unsupported file in directory: " + f.getName());
                }
                // load the PPM image
                PPMImage img = new PPMImage(f);
                images.add(img);
            }
        }
    }

    // Photo stacking: average each pixel across all images
    public void stack() {
        if (images.isEmpty()) {
            return;
        }
        // All images share same dimensions and depth
        int w = images.get(0).getWidth();
        int h = images.get(0).getHeight();
        int depth = images.get(0).getColorDepth();
        // New PPMImage for the result
        RGBImage base    = new RGBImage(w, h, depth);
        stackedImage     = new PPMImage(base);

        // for each pixel position
        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                int sumR = 0, sumG = 0, sumB = 0;
                // sum RGB across all images
                for (PPMImage img : images) {
                    RGBPixel p = img.getPixel(r, c);
                    sumR += p.getRed();
                    sumG += p.getGreen();
                    sumB += p.getBlue();
                }
                // Average
                short avgR = (short)((double)sumR / images.size());
                short avgG = (short)((double)sumG / images.size());
                short avgB = (short)((double)sumB / images.size());
                // set pixel in stacked image
                stackedImage.setPixel(r, c, new RGBPixel(avgR, avgG, avgB));
            }
        }
    }

    public PPMImage getStackedImage() {
        return stackedImage;
    }
}

