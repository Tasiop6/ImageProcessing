package ce326.hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

// PPMImageStacker loads multiple PPM images from a directory and stacks them
public class PPMImageStacker {
    private List<PPMImage> images = new ArrayList<>();  // list of input images
    private PPMImage stackedImage;                       // result after stacking

    // constructor: load all PPM files from dir
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
                // load the PPM image (may throw exceptions)
                PPMImage img = new PPMImage(f);
                images.add(img);
            }
        }
    }

    // apply photo stacking: average each pixel across all images
    public void stack() {
        if (images.isEmpty()) {
            return;  // nothing to stack
        }
        // assume all images share same dimensions and depth
        int w = images.get(0).getWidth();
        int h = images.get(0).getHeight();
        int depth = images.get(0).getColorDepth();
        // create a new PPMImage to hold the result
        RGBImage base    = new RGBImage(w, h, depth);
        stackedImage     = new PPMImage(base);

        // for each pixel position
        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                int sumR = 0, sumG = 0, sumB = 0;
                // sum channels across all images
                for (PPMImage img : images) {
                    RGBPixel p = img.getPixel(r, c);
                    sumR += p.getRed();
                    sumG += p.getGreen();
                    sumB += p.getBlue();
                }
                // compute average and round
                short avgR = (short)Math.round((double)sumR / images.size());
                short avgG = (short)Math.round((double)sumG / images.size());
                short avgB = (short)Math.round((double)sumB / images.size());
                // set pixel in stacked image
                stackedImage.setPixel(r, c, new RGBPixel(avgR, avgG, avgB));
            }
        }
    }

    // return the result of stacking
    public PPMImage getStackedImage() {
        return stackedImage;
    }
}

