package creditcardvalidator;

public class LuhnAlgorithm {

	public static final String[] MaestroIIN = { "5018", "5020", "5038", "5612",
			"5893", "6304", "6759", "6761", "6762", "6763", "0604", "6390" };


	public static boolean isValidCreditCard(String input) {

		if (!input.matches("\\d+")) {
			return false;
		}

		if (input.length() < 8 || input.length() > 19) {
			return false;
		}

		// Get all numbers for checksum validation, ignore check digit, i.e. the last digit
		int[] digits = getDigits(input.substring(0, input.length() - 1));
		
		// Get last digit of creditcard number
		int checkDigit = Integer.parseInt(input.substring(input.length() - 1,
				input.length()));

		if (!isCheckSumValid(digits, checkDigit)) {
			return false;
		}

		return true;
	}

	private static boolean isCheckSumValid(int[] digits, int checkDigit) {
		int checksum = 0;

		for (int i = 0; i < digits.length; i++) {

			// forward loop but start with rightmost digit
			int digit = digits[digits.length - 1 - i];

			if (i % 2 == 0) {
				digit *= 2;
			}

			checksum += digitSum(digit);

		}

		// finally add checkDigit to get correct checksum
		checksum += checkDigit;
		return checksum % 10 == 0;
	}

	private static int digitSum(int digit) {
		if (digit > 9) {
			return digit - 9;
		}

		return digit;
	}

	private static int[] getDigits(String number) {
		int[] digits = new int[number.length()];

		for (int i = 0; i < digits.length; i++) {
			digits[i] = Integer.parseInt(number.substring(i, i + 1));
		}

		return digits;
	}

	public static String getIssuer(String number) {

		// Visa has all 4XXXXXX...
		String first1 = number.substring(0, 1);
		if (first1.equals("4")) {
			return "Visa";
		}

		// Mastercard has all 50-55XXXXXXX...
		int first2 = Integer.parseInt(number.substring(0, 2));
		if (first2 >= 50 && first2 <= 55) {
			return "Mastercard";
		}

		// Maestro's first 4 digits are given
		String first4 = number.substring(0, 4);
		for (int i = 0; i < MaestroIIN.length; i++) {
			if (MaestroIIN[i].equals(first4))
				return "Maestro";
		}

		return "Unknown issuer";
	}

}
