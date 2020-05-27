package imagecompression;

/**
 * @author Jakub Gurgacz
 */
public class ClusterCenter extends BasePixel {
    
    private int oldAlpha;
    private int oldRed;
    private int oldGreen;
    private int oldBlue;

    public ClusterCenter(int alpha, int red, int green, int blue) {
        super(alpha, red, green, blue);
        this.oldAlpha = 0;
        this.oldRed = 0;
        this.oldGreen = 0;
        this.oldBlue = 0;
    }
    
    public void setValues(int red, int green, int blue){
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }

    public int getOldAlpha() {
        return oldAlpha;
    }

    public void setOldAlpha(int oldAlpha) {
        this.oldAlpha = oldAlpha;
    }

    public int getOldRed() {
        return oldRed;
    }

    public void setOldRed(int oldRed) {
        this.oldRed = oldRed;
    }

    public int getOldGreen() {
        return oldGreen;
    }

    public void setOldGreen(int oldGreen) {
        this.oldGreen = oldGreen;
    }

    public int getOldBlue() {
        return oldBlue;
    }

    public void setOldBlue(int oldBlue) {
        this.oldBlue = oldBlue;
    }

    @Override
    public void setAlpha(int alpha) {
        setOldAlpha(getAlpha());
        super.setAlpha(alpha);
    }

    @Override
    public void setRed(int red) {
        setOldRed(getRed());
        super.setRed(red);
    }

    @Override
    public void setGreen(int green) {
        setOldGreen(getGreen());
        super.setGreen(green);
    }

    @Override
    public void setBlue(int blue) {
        setOldBlue(getBlue());
        super.setBlue(blue);
    }
}
