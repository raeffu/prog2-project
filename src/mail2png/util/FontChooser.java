package mail2png.util;

import java.awt.GraphicsEnvironment;

public class FontChooser extends Chooser<String> {

    private static final long serialVersionUID = 1L;

    @Override
    protected String[] getAvailableOptions() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    }
}
