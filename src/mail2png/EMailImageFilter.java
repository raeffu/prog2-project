package mail2png;

import java.awt.Color;
import java.awt.image.RGBImageFilter;

public class EMailImageFilter extends RGBImageFilter {

    private int markerColor;

    public EMailImageFilter(Color color) {
        this.markerColor = color.getRGB() | 0xFF000000;
    }

    @Override
    public int filterRGB(int x, int y, int rgb) {
        if ((rgb | 0xFF000000) != this.markerColor) {
            return 0x00FFFFFF & rgb;
        } else {
            return rgb;
        }
    }

}
