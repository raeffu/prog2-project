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

public class Quizduel {

  private List<Question> _questions = new ArrayList<Question>();
  private Player _currentPlayer;
  private List<Player> _players = new ArrayList<Player>();
  private List<Duel> _duels = new ArrayList<Duel>();
  
  public static final int ROUNDS = 6;
  public static final int QUEST = 3;

  public Quizduel() {
    loadQuestions();
    generatePlayers();
    quizLoop();
  }

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
    }
  }

  public void login() {
    print("Username: ");
    String name = readInput();

    for (Player player : _players) {
      if (player.getName().equals(name)) {
        print("Logged in as existing:" + name);
        _currentPlayer = player;
        return;
      }
    }

    print("Logged in as new:" + name);
    _currentPlayer = new Player(name);
    _players.add(_currentPlayer);
  }

  public void newDuel() {

    print("Available opponents: ");
    for (Player player : _players) {
      if (player != _currentPlayer)
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
    
    Duel duel = new Duel(_currentPlayer, opponent, duelQuestions);
    _duels.add(duel);
    duel.playRound(_currentPlayer);
  }

  public void continueDuel() {

  }

  public void displayDuels() {
    for (Duel duel : _duels) {
      print(duel.toString());
    }
  }

  public void logout() {
    _currentPlayer = null;
  }

  public boolean checkLogin() {
    boolean ok = _currentPlayer != null;
    if (!ok) {
      print("Not logged in");
    }
    return ok;
  }

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
  
  public static void main(String[] args) {
    new Quizduel();
  }

  public static void print(String string) {
    System.out.println(string);
  }

  public static String readInput() {
    Scanner in = new Scanner(System.in);
    return in.nextLine();
  }

  public void generatePlayers() {
    _players.add(new Player("Hans"));
    _players.add(new Player("Peter"));
    _players.add(new Player("Urs"));
  }

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

        // System.out.println("Question " + (i + 1));
        // Question question = new Question(textElement.getTextContent(),
        // answers);
        // System.out.println(question);
        
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
