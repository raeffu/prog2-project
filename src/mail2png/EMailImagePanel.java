package mail2png;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mail2png.util.FontChooser;
import mail2png.util.FontSizeChooser;
import mail2png.util.FontStyleChooser;

public class EMailImagePanel extends JPanel implements KeyListener, ActionListener {

    private static final long serialVersionUID = 1L;

    private final static int P_WIDTH = 400;
    private final static int P_HEIGHT = 200;
    private final static int P_MARGIN = 10;
    private final static int INPUTHEIGHT = 26;
    private final static int INPUTWIDTH = P_WIDTH - (2 * P_MARGIN);

    private EMailTextField addressInput;
    private JButton saveButton;
    private FontChooser fontChooser;
    private FontSizeChooser fontSizeChooser;
    private FontStyleChooser fontStyleChooser;

    public EMailImagePanel() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(P_WIDTH, P_HEIGHT));
        this.setBackground(Color.WHITE);

        this.initAddressInput();
        this.initSaveButton();
        this.initFontChooser();
        this.initFontSizeChooser();
        this.initFontStyleChooser();

        this.refreshSelectedFontSettings();
    }

    private void initSaveButton() {
        this.saveButton = new JButton("Save PNG");
        this.saveButton.addActionListener(this);
        this.saveButton.setBounds(P_MARGIN, P_HEIGHT - P_MARGIN - INPUTHEIGHT, INPUTWIDTH, INPUTHEIGHT);
        this.add(this.saveButton);
    }

    private void initAddressInput() {
        this.addressInput = new EMailTextField("your.mail@host.com");
        this.addressInput.addKeyListener(this);
        this.addressInput.setBounds(P_MARGIN, P_MARGIN, INPUTWIDTH, INPUTHEIGHT);
        this.addressInput.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(this.addressInput);
    }

    private void initFontChooser() {
        this.fontChooser = new FontChooser();
        this.fontChooser.setBounds(P_MARGIN, P_HEIGHT - 2 * (P_MARGIN + INPUTHEIGHT), (INPUTWIDTH - 2 * P_MARGIN) / 2, INPUTHEIGHT);
        this.fontChooser.addActionListener(this);
        this.add(this.fontChooser);
    }

    private void initFontSizeChooser() {
        this.fontSizeChooser = new FontSizeChooser();
        this.fontSizeChooser.setBounds(2 * P_MARGIN + this.fontChooser.getWidth(), P_HEIGHT - 2 * (P_MARGIN + INPUTHEIGHT), (INPUTWIDTH - 2 * P_MARGIN) / 4,
                INPUTHEIGHT);
        this.fontSizeChooser.addActionListener(this);
        this.add(this.fontSizeChooser);
    }

    private void initFontStyleChooser() {
        this.fontStyleChooser = new FontStyleChooser();
        Rectangle bounds = new Rectangle(this.fontSizeChooser.getBounds());
        bounds.translate(P_MARGIN + this.fontSizeChooser.getWidth(), 0);
        this.fontStyleChooser.setBounds(bounds);
        this.fontStyleChooser.addActionListener(this);
        this.add(this.fontStyleChooser);
    }

    private void refreshSelectedFontSettings() {
        this.addressInput.setFontFamily(this.fontChooser.getChosenOption());
        this.addressInput.setFontColor(Color.BLACK);
        this.addressInput.setFontSize(this.fontSizeChooser.getChosenOption());
        this.addressInput.setFontStyle(this.fontStyleChooser.getChosenOption());
        this.repaint();
    }

    private void save() {
        if (this.addressInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You need to input an email address", "Address Missing", JOptionPane.ERROR_MESSAGE);
        } else {
            JFileChooser fc = new EMailFileChooser();
            fc.setSelectedFile(new File(this.addressInput.getText() + ".png"));

            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();

                if (!file.getName().toLowerCase().endsWith(".png")) {
                    file = new File(file + ".png");
                }

                if (!file.exists() || this.confirmOverride(file) == JOptionPane.YES_OPTION) {
                    this.saveAs(file);
                }
            }

        }
    }

    private int confirmOverride(File file) {
        String title = "Confirm Save As";
        String msg = file.getName() + " allready exists.\nDo you want to replace it?";

        return JOptionPane.showConfirmDialog(this, msg, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    private void saveAs(File file) {
        try {
            ImageIO.write(this.addressInput.getImage(), "PNG", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(this, "File saved as: " + file.getName(), "File saved!", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        BufferedImage mailImage = this.addressInput.getImage();
        Rectangle border = new Rectangle(P_MARGIN, 2 * P_MARGIN + INPUTHEIGHT, INPUTWIDTH, P_HEIGHT - 3 * INPUTHEIGHT - 5 * P_MARGIN);

        g2.drawImage(mailImage, border.x + border.width / 2 - mailImage.getWidth() / 2, border.y + border.height / 2 - mailImage.getHeight() / 2, null);

        // Clear the image outside of the border
        g2.fillRect(0, 0, border.x, P_HEIGHT);
        g2.fillRect(0, 0, P_WIDTH, border.y);
        g2.fillRect(0, (int) border.getMaxY(), P_WIDTH, (int) (P_HEIGHT - border.getMaxY()));
        g2.fillRect((int) border.getMaxX(), 0, (int) (P_WIDTH - border.getMaxX()), P_HEIGHT);
        g2.draw(border);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent key) {
        if (key.getKeyCode() == KeyEvent.VK_ENTER) {
            this.save();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(this.saveButton)) {
            this.save();
        } else if (source.equals(this.fontChooser) || source.equals(this.fontSizeChooser) || source.equals(this.fontStyleChooser)) {
            this.refreshSelectedFontSettings();
        }
    }
}
