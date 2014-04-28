package creditcardvalidator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class CreditCardValidatorPanel extends JPanel implements KeyListener,
		ActionListener {

	private static final long serialVersionUID = -3801293499665399966L;

	private final static int P_WIDTH = 400;
	private final static int P_HEIGHT = 200;
	private final static int P_MARGIN = 10;

	private CardNumberField numberInput;
	private JButton submitButton;

	private String message = "Please enter your card number.";
	private boolean isErrorMessage = false;
	private Font font = new Font("Trebuchet MS", Font.BOLD, 18);
	private Color normalColor = new Color(50,205,50);
	private Color errorColor = new Color (220,20,60);

	public CreditCardValidatorPanel() {
		this.setLayout(null);
		this.setPreferredSize(new Dimension(P_WIDTH, P_HEIGHT));
		this.setSize(this.getPreferredSize());

		int inputHeight = (P_HEIGHT / 3) - (4 * P_MARGIN);
		int inputWidth = P_WIDTH - (2 * P_MARGIN);

		// Add input for card number
		this.numberInput = new CardNumberField();
		this.numberInput.addKeyListener(this);
		this.numberInput.setBounds(P_MARGIN, P_MARGIN, inputWidth, inputHeight);
		this.numberInput.setHorizontalAlignment(JTextField.CENTER);
		this.add(this.numberInput);

		// Add submit button
		this.submitButton = new JButton("Check");
		this.submitButton.addActionListener(this);
		this.submitButton.setBounds(P_MARGIN,
				P_HEIGHT - P_MARGIN - inputHeight, inputWidth, inputHeight);
		this.add(this.submitButton);
	}

	// the parameter newCharacter is false if text was deleted (delete button)
	// and true for other keys (new text)
	private void testInputValidity() {

		// check if a number was entered or input length is too long
		if (!this.numberInput.hasValidContent()) {
			this.numberInput.removeLastNumber();
			return;
		}

	}

	private void addSeparatorIfNecessary(boolean isNewCharacter) {
		// insert the number separator if necessary
		if (isNewCharacter && this.numberInput.getNumberLength() > 1
				&& this.numberInput.getNumberLength() % 4 == 0) {
			this.numberInput.setText(this.numberInput.getText()
					+ CreditCardValidator.NUMBER_SEPARATOR);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setFont(this.font);

		if (this.isErrorMessage) {
			g.setColor(errorColor);
		} else {
			g.setColor(normalColor);
		}

		g.drawString(this.message, 20, 80);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		boolean isNewCharacter = true;
		if (e.getKeyCode() == 8) {
			isNewCharacter = false;
		} else {
			this.addSeparatorIfNecessary(isNewCharacter);
		}

		// Remove the error message, if one is displayed
		this.message = "Geben Sie ihre Kreditkartennr. ein!";
		this.isErrorMessage = false;
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// test whether the separator is in front upon pressing delete, so that
		// it is removed
		if (e.getKeyCode() == 8) {
			if (numberInput.isSeparatorAhead()) {
				numberInput.removeSeparatorAhead();
			}
		} else {
			this.testInputValidity();
		}
		this.repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent a) {
		if (LuhnAlgorithm.isValidCreditCard(this.numberInput.getNumber())) {
			this.message = "Valid: " + LuhnAlgorithm.getIssuer(this.numberInput
					.getNumber());
			this.isErrorMessage = false;
		} else {
			this.message = "Number is not valid!";
			this.isErrorMessage = true;
		}

		this.repaint();
	}

}
