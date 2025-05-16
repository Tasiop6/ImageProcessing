package ce326.hw3;

// represents a pixel in YUV color space
public class YUVPixel {
    private short y;
    private short u;
    private short v;

    // construct from explicit Y, U, V values
    public YUVPixel(short y, short u, short v) {
        this.y = clip(y);
        this.u = clip(u);
        this.v = clip(v);
    }

    // copy constructor
    public YUVPixel(YUVPixel p) {
        this.y = p.y;
        this.u = p.u;
        this.v = p.v;
    }

    // convert from an RGBPixel using integer formulas and clamp
    public YUVPixel(RGBPixel p) {
        int R = p.getRed();
        int G = p.getGreen();
        int B = p.getBlue();
        // RGB->YUV
        int Y = (( 66 * R + 129 * G +  25 * B + 128) >> 8) + 16;
        int U = ((-38 * R -  74 * G + 112 * B + 128) >> 8) + 128;
        int V = ((112 * R -  94 * G -  18 * B + 128) >> 8) + 128;
        // clamp into [0..255] and store
        this.y = (short) clip(Y);
        this.u = (short) clip(U);
        this.v = (short) clip(V);
    }

    public short getY() {
        return y;
    }

    public short getU() {
        return u;
    }

    public short getV() {
        return v;
    }

    public void setY(short y) {
        this.y = clip(y);
    }

    public void setU(short u) {
        this.u = clip(u);
    }

    // set V component
    public void setV(short v) {
        this.v = clip(v);
    }

    // clamp an int or short to the 0-255 range
    private int clip(int val) {
        if (val < 0)   return 0;
        if (val > 255) return 255;
        return val;
    }

    // overload for convenience
    private short clip(short val) {
        return (short) clip((int) val);
    }
}
