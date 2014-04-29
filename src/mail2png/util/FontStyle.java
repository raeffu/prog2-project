package mail2png.util;

import java.awt.Font;

public enum FontStyle {

    BOLD(Font.BOLD), ITALIC(Font.ITALIC), PLAIN(Font.PLAIN), BOLD_ITALIC(Font.BOLD + Font.ITALIC);

    private final int fontStyle;

    private FontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
    }

    public int getFontStyle() {
        return this.fontStyle;
    }

}
