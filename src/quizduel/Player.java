package quizduel;

/**
 * @author raeffu
 * 
 * Class representing a Player.
 * 
 * @member _name
 *         Name of player
 * 
 */
public class Player {
  
  private String _name;
  
  public Player(String name){
    _name = name;
  }
  
  public String getName(){
    return _name;
  }
  
  @Override
  public String toString(){
    return getName();
  }
}
