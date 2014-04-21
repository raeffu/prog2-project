package quizduel;

import java.util.ArrayList;

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
