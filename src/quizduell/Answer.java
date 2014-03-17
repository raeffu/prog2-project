package quizduell;

public class Answer {

  private final String txt;
  private final boolean correct;

  public Answer(String txt, boolean correct) {
    this.txt = txt;
    this.correct = correct;
  }

  public String getTxt() {
    return txt;
  }

  public boolean isCorrect() {
    return correct;
  }

}
