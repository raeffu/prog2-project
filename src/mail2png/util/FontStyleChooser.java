package mail2png.util;

public class FontStyleChooser extends Chooser<FontStyle> {

    private static final long serialVersionUID = 1L;

    public FontStyleChooser() {
        this.setSelectedItem(FontStyle.PLAIN);
    }

    @Override
    protected FontStyle[] getAvailableOptions() {
        return FontStyle.values();
    }
}
