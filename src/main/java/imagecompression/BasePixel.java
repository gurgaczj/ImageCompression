package imagecompression;

import java.util.Objects;

/**
 * @author Jakub Gurgacz
 */
public class BasePixel {

    private int alpha;
    private int red;
    private int green;
    private int blue;

    public BasePixel(int alpha, int red, int green, int blue) {
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasePixel)) return false;
        BasePixel basePixel = (BasePixel) o;
        return alpha == basePixel.alpha &&
                red == basePixel.red &&
                green == basePixel.green &&
                blue == basePixel.blue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(alpha, red, green, blue);
    }
}
