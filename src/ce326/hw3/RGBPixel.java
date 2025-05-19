package ce326.hw3;

public class RGBPixel {
    // [0..255]
    private short red;
    private short green;
    private short blue;

    public RGBPixel(short red, short green, short blue) {
        setRGB(red, green, blue);
    }

    // Copy constructor
    public RGBPixel(RGBPixel pixel) {
        this(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
    }

    // An RGBPixel from a YUVPixel
    public RGBPixel(YUVPixel yp) {
        // read YUV
        int Y = yp.getY();
        int U = yp.getU();
        int V = yp.getV();

        // Conversion
        int C = Y - 16;
        int D = U - 128;
        int E = V - 128;
        int rInt = (298*C + 409*E + 128) >> 8;
        int gInt = (298*C - 100*D - 208*E + 128) >> 8;
        int bInt = (298*C + 516*D + 128) >> 8;

        // clamp 0-255
        if      (rInt < 0)   rInt = 0;  else if (rInt > 255) rInt = 255;
        if      (gInt < 0)   gInt = 0;  else if (gInt > 255) gInt = 255;
        if      (bInt < 0)   bInt = 0;  else if (bInt > 255) bInt = 255;

        this.red   = (short) rInt;
        this.green = (short) gInt;
        this.blue  = (short) bInt;
    }

    public short getRed() {
        return red;
    }

    public short getGreen() {
        return green;
    }

    public short getBlue() {
        return blue;
    }

    public void setRed(short red) {
        this.red = red;
    }

    public void setGreen(short green) {
        this.green = green;
    }

    public void setBlue(short blue) {
        this.blue = blue;
    }

    // Returns int containing RGB
    public int getRGB() {
        int r = red;
        int g = green;
        int b = blue;
        return r * 256 * 256 + g * 256 + b;
    }

    // Sets RGB  from int
    public void setRGB(int value) {
        short r = (short)(value / (256 * 256));
        short g = (short)((value / 256) % 256);
        short b = (short)(value % 256);
        setRGB(r, g, b);
    }

    // Sets RGB components from individual values
    public final void setRGB(short red, short green, short blue) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }

    // String in the form "R G B"
    @Override
    public String toString() {
        return getRed() + " " + getGreen() + " " + getBlue();
    }
}