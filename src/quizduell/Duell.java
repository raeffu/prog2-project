package quizduell;

import java.util.HashMap;
import java.util.Map;

public class Duell {

  private final int roundSize;
  private final int numberOfRounds;

  private Player p1;
  private Player p2;

  private Map<Question, AnswerSet> questions;

  public Duell(Player p1, Player p2, int numberOfRounds, int roundSize) {
    this.numberOfRounds = numberOfRounds;
    this.roundSize = roundSize;

    this.p1 = p1;
    this.p2 = p2;

    this.questions = new HashMap<>();
  }

  // TODO: How do we want to add questions??
  // public void addQuestions() {
  //
  // }

}
