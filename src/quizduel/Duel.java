package quizduel;

import java.util.ArrayList;

public class Duel {
  
  private Player _player1;
  private Player _player2;
  
  private ArrayList<Question> _duelQuestions;
  
  private int _round; // round 0 -5
  private int _scorePlayer1;
  private int _scorePlayer2;
  
  public Duel(Player player1, Player player2, ArrayList<Question> questions){
    _player1 = player1;
    _player2 = player2;
    _duelQuestions = questions;
    _round = 0;
    _scorePlayer1 = 0;
    _scorePlayer2 = 0;
  }
  
  public void playRound(Player currentPlayer){
    Quizduel.print("Playing round " + _round+1);
    
    boolean correct = false;
    int start = Quizduel.QUEST * _round;
    
    for(int i=start; i<start+Quizduel.QUEST; i++){
      correct = false;
      
      Question currentQuestion = _duelQuestions.get(i);
      int correctAnswer = currentQuestion.getRightAnswerIndex();
      
      Quizduel.print(currentQuestion.toString());
      Quizduel.print("Answer: ");
      
      String answerString = Quizduel.readInput();
      if(answerString.matches("\\d+")){
        int answer = Integer.parseInt(answerString);
        
//        System.out.println("answer: "+answer);
//        System.out.println("correctAnswer: "+correctAnswer);
        
        if(answer == correctAnswer){
          correct = true;
        }
      }
      
      if(correct){
        Quizduel.print("Correct Answer !!");
        if(currentPlayer == _player1){
          _scorePlayer1 += 1;
        }
        else{
          _scorePlayer2 += 1;
        }
      }
      else{
        Quizduel.print("Wrong !! The correct answer was "+ correctAnswer);
      }
      
    }
    
//    Quizduel.print("Your current score: " + );
  }
  
  public String toString(){
    return "";
  }
}
