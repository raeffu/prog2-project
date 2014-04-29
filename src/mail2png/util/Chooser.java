package mail2png.util;

import javax.swing.JComboBox;

public abstract class Chooser<T> extends JComboBox<T> {

    private static final long serialVersionUID = 1L;

    public Chooser() {

        for (T fontName : this.getAvailableOptions()) {
            this.addItem(fontName);
        }

    }

    public T getChosenOption() {
        return this.getItemAt(this.getSelectedIndex());
    }

    protected abstract T[] getAvailableOptions();

}
