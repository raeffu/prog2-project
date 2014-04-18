package emailToPNG;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConverterFrame extends JFrame {

  private static final long serialVersionUID = 1L;

  private static final int FRAME_WIDTH = 300, FRAME_HEIGHT = 150;
  private static final int FIELD_WIDTH = 20;

  private JLabel emailLabel;
  private JTextField emailInput;
  private JButton saveButton;

  private JPanel imagePanel;

  public ConverterFrame() {
    super();

    setLayout(new FlowLayout());

    // createGUI();

    createFields();
    createPanel();

    this.setTitle("Email to PNG converter");
    this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
  }

  private void createGUI() {

  }

  private void createFields() {

    emailLabel = new JLabel("Email: ");
    emailInput = new JTextField(FIELD_WIDTH);
    emailInput.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(KeyEvent arg0) {

      }

      @Override
      public void keyReleased(KeyEvent arg0) {
        Graphics graphics = imagePanel.getGraphics();
        graphics.clearRect(0, 0, getSize().width, getSize().height);
        graphics.drawImage(getImage(), 0, 8, null);}

      @Override
      public void keyPressed(KeyEvent arg0) {
      }
    });

    saveButton = new JButton("Save");
    saveButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        // save image

        JFileChooser fileChooser = new JFileChooser(System
            .getProperty("user.dir"));
        fileChooser.setSelectedFile(new File(emailInput.getText() + ".PNG"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int returnCode = fileChooser.showSaveDialog(null);
        String filepath = "";

        if (returnCode == JFileChooser.APPROVE_OPTION) {
          filepath = fileChooser.getSelectedFile().getAbsolutePath();
          
          try {
            ImageIO.write(getImage(), "PNG", new File(filepath));
          }
          catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    });
  }

  private void createPanel() {
    JPanel mainPanel = new JPanel(new GridLayout(3, 1));

    JPanel emailPanel = new JPanel();
    emailPanel.add(emailLabel);
    emailPanel.add(emailInput);
    mainPanel.add(emailPanel);

    imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createLineBorder(Color.RED));
    imagePanel.setPreferredSize(new Dimension(200, 40));
    mainPanel.add(imagePanel);

    JPanel panel3 = new JPanel();
    panel3.add(saveButton);
    mainPanel.add(panel3);

    add(mainPanel);
  }

  private BufferedImage getImage() {
    BufferedImage image = new BufferedImage(260, 40,
        BufferedImage.TYPE_INT_ARGB);

    Graphics graph = image.createGraphics();
    graph.setFont(new Font("Arial", Font.BOLD, 24));
    graph.setColor(Color.BLACK);
    graph.drawString(emailInput.getText(), 0, 20);

    return image;
  }

}
