/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mgcill.ecse223.quoridor.model;
import java.util.*;

// line 9 "../../../../../model.ump"
// line 53 "../../../../../model.ump"
public class Game
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum GameState { SETUP, IN_PROGRESS, GAME_OVER }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  private GameState state;
  private boolean isNew;

  //Game Associations
  private List<Player> players;
  private Board board;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(GameState aState, boolean aIsNew, Board aBoard)
  {
    state = aState;
    isNew = aIsNew;
    players = new ArrayList<Player>();
    if (aBoard == null || aBoard.getGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aBoard");
    }
    board = aBoard;
  }

  public Game(GameState aState, boolean aIsNew, boolean aIsValidForBoard)
  {
    state = aState;
    isNew = aIsNew;
    players = new ArrayList<Player>();
    board = new Board(aIsValidForBoard, this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setState(GameState aState)
  {
    boolean wasSet = false;
    state = aState;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsNew(boolean aIsNew)
  {
    boolean wasSet = false;
    isNew = aIsNew;
    wasSet = true;
    return wasSet;
  }

  public GameState getState()
  {
    return state;
  }

  public boolean getIsNew()
  {
    return isNew;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsNew()
  {
    return isNew;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfPlayersValid()
  {
    boolean isValid = numberOfPlayers() >= minimumNumberOfPlayers();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 1;
  }
  /* Code from template association_AddMandatoryManyToOne */
  public Player addPlayer(String aName, Pawn aPawn)
  {
    Player aNewPlayer = new Player(aName, aPawn, this);
    return aNewPlayer;
  }

  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    Game existingGame = aPlayer.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);

    if (isNewGame && existingGame.numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasAdded;
    }
    if (isNewGame)
    {
      aPlayer.setGame(this);
    }
    else
    {
      players.add(aPlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayer, as it must always have a game
    if (this.equals(aPlayer.getGame()))
    {
      return wasRemoved;
    }

    //game already at minimum (1)
    if (numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasRemoved;
    }

    players.remove(aPlayer);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addPlayer(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=players.size(); i > 0; i--)
    {
      Player aPlayer = players.get(i - 1);
      aPlayer.delete();
    }
    Board existingBoard = board;
    board = null;
    if (existingBoard != null)
    {
      existingBoard.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isNew" + ":" + getIsNew()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "state" + "=" + (getState() != null ? !getState().equals(this)  ? getState().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "board = "+(getBoard()!=null?Integer.toHexString(System.identityHashCode(getBoard())):"null");
  }
}