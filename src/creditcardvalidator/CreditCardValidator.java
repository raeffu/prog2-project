package creditcardvalidator;

import javax.swing.*;

public class CreditCardValidator extends JFrame {

	private static final long serialVersionUID = 2365469391161584384L;

	private final static String NAME = "Credit Card Validator";
	public final static String NUMBER_SEPARATOR = " - ";

	public CreditCardValidator() {
		this.setTitle(CreditCardValidator.NAME);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
		this.setResizable(false);

		JPanel panel = new CreditCardValidatorPanel();
		this.setContentPane(panel);

		this.pack();
		this.setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		new CreditCardValidator();
	}
}
