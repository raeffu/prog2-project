package quizduel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author raeffu
 * 
 * Console simulation of quizduel app
 * 
 * @member _questions
 *         List of all avaiable questions
 * @member _currentUser
 *         Person currently logged in
 * @member _players
 *         List of avaiable players
 * @member _duels
 *         List of duels
 * @member ROUNDS
 *         Number of rounds to play
 * @member QUEST
 *         Number of questions per round
 * 
 */
public class Quizduel extends Printable {

  private List<Question> _questions = new ArrayList<Question>();
  private Player _currentUser;
  private List<Player> _players = new ArrayList<Player>();
  private List<Duel> _duels = new ArrayList<Duel>();
  
  public static final int ROUNDS = 6;
  public static final int QUEST = 3;

  /**
   * 
   * Constructor
   * 
   */
  public Quizduel() {
    loadQuestions();
    generatePlayers();
    quizLoop();
  }

  /**
   * 
   * Game loop, print menu and fires actions
   * 
   */
  private void quizLoop() {

    boolean playing = true;

    while (playing) {
      print("User: ["+_currentUser+"]");
      print("Log I)n, N)ew Duel, C)ontinue Duel, D)isplay Duels, Log O)ut, Q)uit");

      String input = readInput();

      if ("I".equalsIgnoreCase(input)) {
        login();
      }
      else if ("N".equalsIgnoreCase(input)) {
        if (checkLogin())
          newDuel();
      }
      else if ("C".equalsIgnoreCase(input)) {
        if (checkLogin())
          continueDuel();
      }
      else if ("D".equalsIgnoreCase(input)) {
        displayDuels();
      }
      else if ("O".equalsIgnoreCase(input)) {
        if (checkLogin())
          logout();
      }
      else if ("Q".equalsIgnoreCase(input)) {
        playing = false;
      }
      else{
        print("Wrong input!");
      }
    }
  }

  /**
   * 
   * Create Player and set _currentUser
   * 
   */
  private void login() {
    print("Username: ");
    String name = readInput();

    for (Player player : _players) {
      if (player.getName().equals(name)) {
        print("Logged in as existing: " + name);
        _currentUser = player;
        return;
      }
    }

    print("Logged in as new: " + name);
    _currentUser = new Player(name);
    _players.add(_currentUser);
  }

  /**
   * 
   * Construct new duel and play first round
   * 
   */
  private void newDuel() {

    print("Available opponents: ");
    for (Player player : _players) {
      if (player != _currentUser)
        print(player.getName());
    }

    print("Opponent's name: ");
    String opponentName = readInput();
    Player opponent = null;
    
    for(Player player : _players){
      if(player.getName().equals(opponentName))
        opponent = player;
    }
    
    if (opponent == null) {
      print("Opponent not found, check spelling!");
      return;
    }
    
    ArrayList<Question> duelQuestions = getDuelQuestions();
    if(duelQuestions == null) return;
    
    Duel duel = new Duel(_currentUser, opponent, duelQuestions);
    _duels.add(duel);
    duel.playRound(_currentUser);
  }

  /**
   * 
   * Find duel and continue playing
   * 
   */
  private void continueDuel() {
    print("Enter duel number:");
    String duelNr = readInput();
    
    Duel duel = getDuel(duelNr);
    if(duel == null){
      print("Duel not found!");
      return;
    }
    
    duel.playRound(_currentUser);
  }

  /**
   * 
   * Print out duels
   * 
   */
  private void displayDuels() {
    for (Duel duel : _duels) {
      print(duel);
    }
  }

  /**
   * 
   * Log out current user
   * 
   */
  private void logout() {
    _currentUser = null;
  }

  /**
   * See if someone is logged in
   * 
   * @return ok
   *         return true if logged in
   * 
   */
  private boolean checkLogin() {
    boolean ok = _currentUser != null;
    if (!ok) {
      print("Not logged in");
    }
    return ok;
  }
  
  /**
   * Find duel with ID
   * 
   * @param duelNr
   *        ID of a duel
   * @return duel
   *         selected duel
   */
  private Duel getDuel(String duelNr){
    int id = duelNr.matches("\\d+") ? Integer.parseInt(duelNr) : -1;
      
    for(Duel duel : _duels){
      if (duel.getId() == id) {
        return duel;
      }
    }
    return null;
  }

  /**
   * Get set of duel questions
   * 
   * @return duelQuestions
   *         Selection of questions in random order
   */
  private ArrayList<Question> getDuelQuestions(){
    ArrayList<Question> duelQuestions = new ArrayList<Question>();
    
    if (_questions.size() < ROUNDS*QUEST) {
      print("Not enough questions for "+ROUNDS+"x"+QUEST+"-Duel");
      return null;
    }
    
    // Everyday I'm shuffling
    Collections.shuffle(_questions);
    
    // Add first 18 or needed amount of questions
    for(int i=0; i<ROUNDS*QUEST; i++){
      duelQuestions.add(_questions.get(i));
    }
    
    return duelQuestions;
  }
  
  /**
   * Start program
   * 
   * @param args
   */
  public static void main(String[] args) {
    new Quizduel();
  }

  /**
   * 
   * Generate some Players
   * 
   */
  private void generatePlayers() {
    _players.add(new Player("Hans"));
    _players.add(new Player("Peter"));
    _players.add(new Player("Urs"));
  }

  /**
   * 
   * Load questions from XML file.
   * See Question class.
   * 
   */
  private void loadQuestions() {
    _questions = Question.loadQuestions();
  }

}
