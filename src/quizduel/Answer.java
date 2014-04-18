package quizduel;

public class Answer {
  
  private String _text;
  private boolean _isCorrect;
  
  public Answer(String text, boolean isCorrect){
    _text = text;
    _isCorrect = isCorrect;
  }
  
  public String getText(){
    return _text;
  }
  
  public boolean isCorrect(){
    return _isCorrect;
  }
  
}
