package quizduel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Duel {

  private Player _player1;
  private Player _player2;
  private Player _currentPlayer;
  private static int _uid = 0;
  private int _id;

  // Player and Score
  private Map<String, Integer> _scores = new HashMap<String, Integer>();

  private ArrayList<Question> _duelQuestions;

  private int _round; // round 0 -5
  // private int _scorePlayer1;
  // private int _scorePlayer2;

  public Duel(Player player1, Player player2, ArrayList<Question> questions) {
    _player1 = player1;
    _player2 = player2;
    _scores.put(player1.getName(), 0);
    _scores.put(player2.getName(), 0);
    _duelQuestions = questions;
    _round = 1;
    // _scorePlayer1 = 0;
    // _scorePlayer2 = 0;

    // set duel number
    _uid += 1;
    _id = _uid;
  }

  //TODO: add state, is round finished, have all players played?
  public void playRound(Player currentPlayer) {
    _currentPlayer = currentPlayer;

    Quizduel.print("Playing round " + _round);

    boolean correct = false;
    int start = Quizduel.QUEST * _round - 1;

    for (int i = start; i < start + Quizduel.QUEST; i++) {
      correct = false;

      Question currentQuestion = _duelQuestions.get(i);
      int correctAnswer = currentQuestion.getRightAnswerIndex();

      Quizduel.print(currentQuestion.toString());
      Quizduel.print("Answer: ");

      String answerString = Quizduel.readInput();
      if (answerString.matches("\\d+")) {
        int answer = Integer.parseInt(answerString);

        // System.out.println("answer: "+answer);
        // System.out.println("correctAnswer: "+correctAnswer);

        if (answer == correctAnswer) {
          correct = true;
        }
      }

      if (correct) {
        Quizduel.print("Correct Answer !!");
        updateScore(_currentPlayer);
      }
      else {
        Quizduel.print("Wrong !! The correct answer was " + correctAnswer);
      }

    }

    Quizduel.print("Your current score: " + getScore(_currentPlayer));
  }

  public void updateScore(Player player) {
    int newScore = _scores.get(player.getName()) + 1;
    _scores.put(player.getName(), newScore);
  }

  public int getScore(Player player) {
    return _scores.get(player.getName());
  }

  public String getNextPlayer(Player _currentPlayer) {
    return _currentPlayer.equals(_player1) ? 
        _player2.getName() : _player1.getName();
  }
  
  public int getId(){
    return _id;
  }

  public String toString() {
    String result = "[Duel " + _id + "]" + ": " + _player1.getName() + " vs. "
        + _player2.getName() + " | " + getScore(_player1) + " - "
        + getScore(_player2) + " | " + "Round " + _round + " | "
        + getNextPlayer(_currentPlayer);
    return result;
  }
}
