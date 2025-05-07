package ce326.hw3;

public interface Image {
    /**
     * Converts the image to grayscale (black and white).
     */
    void grayscale();

    /**
     * Doubles the dimensions of the image.
     */
    void doublesize();

    /**
     * Halves the dimensions of the image.
     */
    void halfsize();

    /**
     * Rotates the image 90 degrees clockwise.
     */
    void rotateClockwise();
}
