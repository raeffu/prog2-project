package creditcard;

import java.util.Scanner;

public class CreditcardChecker {

  public static final String[] MaestroIIN = { "5018", "5020", "5038", "5612",
      "5893", "6304", "6759", "6761", "6762", "6763", "0604", "6390" };

  public static void main(String[] args) {
    loop();
  }

  public static void loop() {
    boolean running = true;

    while (running) {
      print("Enter credit card number to check! Q to quit");

      String input = readInput();

      if ("Q".equalsIgnoreCase(input))
        running = false;
      else {
        boolean valid = isValidCreditcard(input);

        if (valid) {
          print("Credit card number is valid!");
          String issuer = getIssuer(input);
          print("Issued by: "+issuer);
        }
        else {
          print("Credit card number not valid!");
        }
      }
    }
  }

  public static boolean isValidCreditcard(String input) {

    if (!input.matches("\\d+")) {
      print("Only numbers allowed!");
      return false;
    }

    if (input.length() < 8 || input.length() > 19) {
      print("Number must be between 8 and 19 digits!");
      return false;
    }
    
    //Get all numbers for checksum validation, ignore check digit, e.g. last digit
    int[] digits = getDigits(input.substring(0, input.length()-1));
    //Get last digit of creditcard number
    int checkDigit = Integer.parseInt(input.substring(input.length()-1, input.length()));
    
    if(!validateCheckSum(digits, checkDigit)){
      return false;
    }

    return true;
  }

  public static boolean validateCheckSum(int[] digits, int checkDigit) {
    int checksum = 0;

    for (int i = 0; i < digits.length; i++) {

      // forward loop but start with rightmost digit
      int digit = digits[digits.length - 1 - i];

      if (i % 2 == 0) {
        digit *= 2;
      }

      checksum += digitSum(digit);

    }
    
    //finally add checkDigit to get correct checksum
    checksum += checkDigit;
    return checksum % 10 == 0;
  }

  public static int digitSum(int digit) {
    if (digit > 9) {
      return digit - 9;
    }

    return digit;
  }

  public static int[] getDigits(String number) {
    int[] digits = new int[number.length()];

    for (int i = 0; i < digits.length; i++) {
      digits[i] = Integer.parseInt(number.substring(i,i+1));
    }

    return digits;
  }

  public static String getIssuer(String number) {
    
    //Visa has all 4XXXXXX...
    String first1 = number.substring(0,1);
    if (first1.equals("4")) {
      return "Visa";
    }
    
    //Mastercard has all 50-55XXXXXXX...
    int first2 = Integer.parseInt(number.substring(0,2));
    if (first2 >= 50 && first2 <= 55) {
      return "Mastercard";
    }
    
    //Maestro's first 4 digits are given
    String first4 = number.substring(0,4);
    for (int i = 0; i < MaestroIIN.length; i++) {
      if(MaestroIIN[i].equals(first4))
        return "Maestro";
    }
    
    
    
    return "Unknown";
  }

  public static void print(String string) {
    System.out.println(string);
  }

  public static String readInput() {
    Scanner in = new Scanner(System.in);
    return in.nextLine();
  }

}
