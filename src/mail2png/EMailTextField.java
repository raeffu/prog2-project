package mail2png;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.JTextField;

import mail2png.util.FontStyle;

public class EMailTextField extends JTextField {

    private static final long serialVersionUID = 1L;

    private String fontFamily;
    private int fontSize;
    private Color fontColor;
    private FontStyle fontStyle;

    public EMailTextField() {
        this.fontFamily = "Trebuchet MS";
        this.fontSize = 22;
        this.fontColor = Color.BLACK;
        this.fontStyle = FontStyle.PLAIN;
    }

    public EMailTextField(String text) {
        super(text);
    }

    public BufferedImage getImage() {
        Font font = new Font(this.fontFamily, this.fontStyle.getFontStyle(), this.fontSize);
        return String2ImageConvertor.getStringAsImage(this.getImageText(), font, this.fontColor);
    }

    public boolean isEmpty() {
        return this.getText().isEmpty();
    }

    private String getImageText() {
        return this.isEmpty() ? "" : this.getText();
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public void setFontStyle(FontStyle fontStyle) {
        this.fontStyle = fontStyle;
    }

}
