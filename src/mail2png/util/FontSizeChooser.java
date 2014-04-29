package mail2png.util;

import java.util.ArrayList;
import java.util.List;

public class FontSizeChooser extends Chooser<Integer> {

    private static final long serialVersionUID = 1L;

    public FontSizeChooser() {
        this.setSelectedItem(26);
    }

    @Override
    protected Integer[] getAvailableOptions() {
        List<Integer> fontSizes = new ArrayList<>();

        for (int i = 8; i <= 72; i++) {

            if (i > 28) {
                i += 3;
            } else if (i > 8) {
                i++;
            }
            fontSizes.add(i);
        }

        return fontSizes.toArray(new Integer[] {});
    }

}
