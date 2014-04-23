package quizduel;

import java.util.Scanner;

public abstract class Printable {
  
  /**
   * Print something to the UI (console)
   * 
   * @param string
   */
  public static void print(Object object) {
    System.out.println(object);
  }
  
  /**
   * Read from console
   * 
   * @return input
   */
  public static String readInput() {
    Scanner in = new Scanner(System.in);
    String result = in.nextLine();
    return result;
  }
}
