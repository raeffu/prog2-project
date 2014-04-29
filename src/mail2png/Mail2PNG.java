package mail2png;

import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Mail2PNG extends JFrame {

    private static final long serialVersionUID = 1L;

    private final static String NAME = "mail2png";
    private final static Image ICON = String2ImageConvertor.getStringAsImage("@");

    public Mail2PNG() {
        this.setTitle(Mail2PNG.NAME);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(Mail2PNG.ICON);
        this.setResizable(false);

        JPanel panel = new EMailImagePanel();
        this.setContentPane(panel);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Mail2PNG();
    }

}
