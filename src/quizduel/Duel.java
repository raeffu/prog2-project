package quizduel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author raeffu
 * 
 * Class representing a duel.
 * 
 * @member _player1
 * @member _player2
 * @member _currentPlayer
 *         Player who is playing
 * @member _finished
 *         true if Quizduel.ROUND reached
 * @member _questionOffset
 *         Offset for looping over _duelQuestions. Loop over next questions
 * @member _uid
 *         static. Unique number for duel ID
 * @member _id
 *         ID of duel
 * @member _scores
 *         HashMap with player's name and current score
 * @member _duelQuestions
 *         Set of questions for this duel
 * @member _round
 *         Counter for rounds
 * 
 */
public class Duel extends Printable {

  private Player _player1;
  private Player _player2;
  private Player _currentPlayer;
  private boolean _finished;
  private int _questionOffset;
  private static int _uid = 0;
  private int _id;

  // Playername and his score
  private Map<String, Integer> _scores = new HashMap<String, Integer>();

  private ArrayList<Question> _duelQuestions;

  private int _round; // round 0 -5

  /**
   * Constructor: Create new duel
   * 
   * @param player1
   *        Player who started duel
   * @param player2
   *        opponent
   * @param questions
   *        questions selected for this duel, see Quizduel.getDuelQuestions
   */
  public Duel(Player player1, Player player2, ArrayList<Question> questions) {
    _player1 = player1;
    _player2 = player2;
    _scores.put(player1.getName(), 0);
    _scores.put(player2.getName(), 0);
    _duelQuestions = questions;
    _round = 1;
    _finished = false;
    _questionOffset = 0;

    // set duel number
    _uid += 1;
    _id = _uid;
  }

  /**
   * Play a round in a duel.
   * 
   * @param newPlayer
   *        Player who wants to play a duel
   */
  public void playRound(Player newPlayer) {

    if (_finished) {
      print("Duel already finished!");
      return;
    }

    if (newPlayer.equals(_currentPlayer)) {
      print("Sorry, Player " + getNextPlayer(newPlayer) + " is up!");
      return;
    }
    else if (newPlayer.equals(_player1) || newPlayer.equals(_player2)) {
      _currentPlayer = newPlayer;
    }
    else {
      print("Sorry " + newPlayer.getName()
          + ", this is a game between " + _player1.getName() + " and "
          + _player2.getName());
      return;
    }

    print("Playing round " + _round);

    boolean correct = false;
    
    for (int i = _questionOffset; i < _questionOffset + Quizduel.QUEST; i++) {
      correct = false;

      Question currentQuestion = _duelQuestions.get(i);
      int correctAnswer = currentQuestion.getRightAnswerIndex();

      print(currentQuestion);
      print("Answer: ");

      String answerString = readInput();
      if (answerString.matches("\\d+")) {
        int answer = Integer.parseInt(answerString);

        if (answer == correctAnswer) {
          correct = true;
        }
      }

      if (correct) {
        print("Correct Answer !!");
        updateScore(_currentPlayer);
      }
      else {
        print("Wrong !! The correct answer was " + correctAnswer);
      }

    }

    print("Your current score: " + getScore(_currentPlayer));
    updateRound();

    if (_round > Quizduel.ROUNDS) {
      print(getSummary());
      _finished = true;
      return;
    }
  }

  /**
   * Update score of Player player
   * 
   * @param player
   */
  private void updateScore(Player player) {
    int newScore = _scores.get(player.getName()) + 1;
    _scores.put(player.getName(), newScore);
  }

  /**
   * Return score of a player
   * 
   * @param player
   * @return score
   *         score of player
   */
  private int getScore(Player player) {
    return _scores.get(player.getName());
  }

  /**
   * Get player whos up next
   * 
   * @param _currentPlayer
   * @return nextPlayer
   */
  private Player getNextPlayer(Player _currentPlayer) {
    return _currentPlayer.equals(_player1) ? _player2 : _player1;
  }

  /**
   * Evaluate winner.
   * 
   * @return winner
   *         Name of the winner
   */
  private String getWinner() {
    if (_scores.get(_player1.getName()) > _scores.get(_player2.getName())) {
      return "Winner: " + _player1.getName();
    }
    else if (_scores.get(_player1) < _scores.get(_player2)) {
      return "Winner: " + _player2.getName();
    }
    else {
      return "DRAW";
    }
  }

  /**
   * 
   * Update round counter and offset for questions selection
   * 
   * @var _round
   *      current round
   * @var _questionOffset
   *      begin index of questions for round (_duelQuestions)
   * 
   */
  private void updateRound() {
    if (_currentPlayer.equals(_player2)) {
      _round += 1;
      _questionOffset += Quizduel.QUEST;
    }
    return;
  }

  /**
   * Return ID of a duel
   * 
   * @return id
   *         ID of duel
   */
  public int getId() {
    return _id;
  }

  /**
   * Return summary for a duel.
   * 
   * @return duelSummary
   *         summary of a finished duel
   */
  private String getSummary() {
    String result = "[Duel " + _id + "]" + ": " + _player1.getName() + " vs. "
        + _player2.getName() + " | " + getScore(_player1) + " - "
        + getScore(_player2) + " | " + "FINISHED | " + getWinner();
    return result;
  }

  /**
   * 
   *  Print status of duel
   * 
   */
  @Override
  public String toString() {
    if(_finished) return getSummary();
    
    String result = "[Duel " + _id + "]" + ": " + _player1.getName() + " vs. "
        + _player2.getName() + " | " + getScore(_player1) + " - "
        + getScore(_player2) + " | " + "Round " + _round + " | "
        + getNextPlayer(_currentPlayer).getName();
    return result;
  }
}
