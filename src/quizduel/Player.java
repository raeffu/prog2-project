package quizduel;

public class Player {
  
  private String _name;
  
  public Player(String name){
    _name = name;
  }
  
  public String getName(){
    return _name;
  }
  
  public String toString(){
    return getName();
  }
}
