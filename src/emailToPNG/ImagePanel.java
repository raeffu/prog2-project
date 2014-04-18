package emailToPNG;

import java.awt.Graphics;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

  private static final long serialVersionUID = 1L;

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    getGraphics().clearRect(1, 1, getSize().width - 2, getSize().height - 2);
//    getGraphics().drawImage(imageObject , 0, 0, null);

  }

}
