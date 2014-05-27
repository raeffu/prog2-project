package quizduel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
 * Class representing a question.
 * 
 * @member _text
 *         Question text
 * @member _answers
 *         List of answers for this question.
 * 
 */
public class Question {
  
  private String _text;
  private ArrayList<Answer> _answers;
  
  /**
   * Constructor
   * 
   * @param text
   * @param answers
   */
  public Question(String text, ArrayList<Answer> answers){
    _text = text;
    _answers = answers;
  }
  
  /**
   * Return index of right answer. Needed for score evaluation. See quizduel.Duel
   * 
   * @return index
   *         index of right answer
   */
  public int getRightAnswerIndex() {
    for (int i=0; i<_answers.size(); i++) {
      if (_answers.get(i).isCorrect()) {
        return i;
      }
    }

    return -1;
  }
  
  /**
   * 
   * Load questions from XML file.
   * Answers are randomized here.
   * 
   */
  public static ArrayList<Question> loadQuestions(){
    ArrayList<Question> questions = new ArrayList<Question>();
    
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
        
        // Everyday I'm shuffling
        Collections.shuffle(answers);
        questions.add(new Question(textElement.getTextContent(), answers));
      }
      
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return questions;
  }
  
  /**
   * Print out a question. index is used for selecting a answer.
   * 
   */
  @Override
  public String toString(){
    String result = "*** Question ***\n"; 
    result += _text + "\n";
    
    int index = 0;
    for(Answer answer : _answers){
      result += index +" - "+ answer.getText() + "\n";
      index +=1;
    }   
    return result;
  }
}
