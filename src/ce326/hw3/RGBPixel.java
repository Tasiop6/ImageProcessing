package ce326.hw3;

public class RGBPixel {
    // each component in [0..255]
    private short red;
    private short green;
    private short blue;

    /**
     * Constructs a pixel with the given red, green, blue values.
     * @param red   red component [0..255]
     * @param green green component [0..255]
     * @param blue  blue component [0..255]
     */
    public RGBPixel(short red, short green, short blue) {
        setRGB(red, green, blue);
    }

    /**
     * Copy constructor.
     * @param pixel existing RGBPixel to copy
     */
    public RGBPixel(RGBPixel pixel) {
        this(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
    }

    /**
     * Constructs an RGBPixel from a YUVPixel.
     * Implementation to be added later.
     * @param pixel source YUVPixel
     */
    public RGBPixel(YUVPixel pixel) {
        // TODO: implement YUV to RGB conversion
    }

    /** @return red component [0..255] */
    public short getRed() {
        return red;
    }

    /** @return green component [0..255] */
    public short getGreen() {
        return green;
    }

    /** @return blue component [0..255] */
    public short getBlue() {
        return blue;
    }

    /** @param red new red component [0..255] */
    public void setRed(short red) {
        this.red = red;
    }

    /** @param green new green component [0..255] */
    public void setGreen(short green) {
        this.green = green;
    }

    /** @param blue new blue component [0..255] */
    public void setBlue(short blue) {
        this.blue = blue;
    }

    /**
     * @return packed int 0xRRGGBB using arithmetic (no bit shifts).
     */
    public int getRGB() {
        int r = red;
        int g = green;
        int b = blue;
        return r * 256 * 256 + g * 256 + b;
    }

    /**
     * Sets RGB components from packed int 0xRRGGBB using arithmetic (no bit shifts).
     * @param value packed RGB value
     */
    public void setRGB(int value) {
        short r = (short)(value / (256 * 256));
        short g = (short)((value / 256) % 256);
        short b = (short)(value % 256);
        setRGB(r, g, b);
    }

    /**
     * Sets RGB components from individual values.
     * @param red   red component [0..255]
     * @param green green component [0..255]
     * @param blue  blue component [0..255]
     */
    public final void setRGB(short red, short green, short blue) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }

    /**
     * Returns a string in the form "R G B".
     */
    @Override
    public String toString() {
        return getRed() + " " + getGreen() + " " + getBlue();
    }
}
