package quizduel;

import java.util.ArrayList;

public class Question {
  
  private String _text;
  private ArrayList<Answer> _answers;
  
  public Question(String text, ArrayList<Answer> answers){
    _text = text;
    _answers = answers;
  }
  
//  public Answer getRightAnswer(){
//    for(Answer answer : _answers){
//      if (answer.isCorrect()) {
//        return answer;
//      }
//    }
//    
//    return null;
//  }
  
  public int getRightAnswerIndex() {
    for (int i=0; i<_answers.size(); i++) {
      if (_answers.get(i).isCorrect()) {
        return i;
      }
    }

    return -1;
  }
  
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
