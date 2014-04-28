package creditcardvalidator;

import javax.swing.JTextField;

public class CardNumberField extends JTextField {
	
	private static final long serialVersionUID = 5270689486452729949L;

	public int getNumberLength() {
		int length=0;		
		for (int i=0; i<this.getNumber().length(); i++) {
			if (Character.isDigit(this.getNumber().charAt(i))) length++;
		}
		
		return length;
	}
	
	public String getNumber() {
		String[] contents = this.getText().split(CreditCardValidator.NUMBER_SEPARATOR);
		String number = "";
		
		for (int i=0; i<contents.length; i++) {
			number += contents[i];
		}

		return number;
	}
	
	public boolean hasValidContent() {
		if (this.getNumberLength()>19) return false;
		
		if (this.getText().length()>0) return this.getNumber().matches("\\d+");
		else return true;
	}

	public boolean isEmpty() {
	    return this.getText().isEmpty();
	}
	
	public void removeLastNumber() {
		
		if (!this.isEmpty() && !this.isSeparatorAhead()) {
			this.setText(this.getText().substring(0,this.getText().length()-1));
			this.removeSeparatorAhead();
		} else {
			this.removeSeparatorAhead();
		}

	}
	
	//tests whether the separaot is in the front of the text
	public boolean isSeparatorAhead() {
		int textLength = this.getText().length();
		int separatorLength = CreditCardValidator.NUMBER_SEPARATOR.length();
		
		return (textLength>=separatorLength && this.getText().substring(textLength-separatorLength).equals(CreditCardValidator.NUMBER_SEPARATOR));

	}
	
	public void removeSeparatorAhead() {
		if (this.isSeparatorAhead()) {
			this.setText(this.getText().substring(0,this.getText().length()-CreditCardValidator.NUMBER_SEPARATOR.length()));
		}
	}
		
}
