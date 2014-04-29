package mail2png;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class EMailFileChooser extends JFileChooser {

    private static final long serialVersionUID = 1L;

    public EMailFileChooser() {
        this.setFileSelectionMode(JFileChooser.FILES_ONLY);
        this.setAcceptAllFileFilterUsed(true);

        this.setFileFilter(new FileFilter() {

            @Override
            public String getDescription() {
                return "PNG-File (.png)";
            }

            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().contains(".png");
            }
        });
    }

}
