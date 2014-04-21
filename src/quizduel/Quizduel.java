package quizduel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
public class Quizduel {

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
  public void login() {
    print("Username: ");
    String name = readInput();

    for (Player player : _players) {
      if (player.getName().equals(name)) {
        print("Logged in as existing:" + name);
        _currentUser = player;
        return;
      }
    }

    print("Logged in as new:" + name);
    _currentUser = new Player(name);
    _players.add(_currentUser);
  }

  /**
   * 
   * Construct new duel and play first round
   * 
   */
  public void newDuel() {

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
  public void continueDuel() {
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
  public void displayDuels() {
    for (Duel duel : _duels) {
      print(duel.toString());
    }
  }

  /**
   * 
   * Log out current user
   * 
   */
  public void logout() {
    _currentUser = null;
  }

  /**
   * See if someone is logged in
   * 
   * @return ok
   *         return true if logged in
   * 
   */
  public boolean checkLogin() {
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
  public Duel getDuel(String duelNr){
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
  public ArrayList<Question> getDuelQuestions(){
    ArrayList<Question> duelQuestions = new ArrayList<Question>();
    
    if (_questions.size() < ROUNDS*QUEST) {
      print("Not enough questions for "+ROUNDS+"x"+QUEST+"-Duel");
      return null;
    }
    
    // Randomize order of questions
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
   * Print something to the UI (console)
   * 
   * @param string
   */
  public static void print(String string) {
    System.out.println(string);
  }

  /**
   * Read from console
   * 
   * @return input
   */
  public static String readInput() {
    Scanner in = new Scanner(System.in);
    return in.nextLine();
  }

  /**
   * 
   * Generate some Players
   * 
   */
  public void generatePlayers() {
    _players.add(new Player("Hans"));
    _players.add(new Player("Peter"));
    _players.add(new Player("Urs"));
  }

  /**
   * 
   * Load questions from XML file.
   * Answers are randomized here.
   * 
   */
  private void loadQuestions() {
    File xml = new File("questions.xml");
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    try {
      DocumentBuilder builder = factory.newDocumentBuilder();

      Document doc = builder.parse(xml);

      NodeList questionList = doc.getElementsByTagName("question");

      for (int i = 0; i < questionList.getLength(); i++) {
        Element questionElement = (Element) questionList.item(i); // <question>
                                                                  // element

        // Get the child elements <text> of <question>, only one
        NodeList textNodes = questionElement.getElementsByTagName("text");
        Element textElement = (Element) textNodes.item(0);

        ArrayList<Answer> answers = new ArrayList<Answer>();

        // Get the child elements <answer> of <question>, one or more
        NodeList answerNodes = questionElement.getElementsByTagName("answer");
        for (int j = 0; j < answerNodes.getLength(); j++) {
          Element answerElement = (Element) answerNodes.item(j);
          boolean isCorrect = answerElement.getAttribute("correct").equals(
              "true") ? true : false;

          answers.add(new Answer(answerElement.getTextContent(), isCorrect));
        }
        
        // Randomize order of answers
        Collections.shuffle(answers);
        _questions.add(new Question(textElement.getTextContent(), answers));
      }

    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
