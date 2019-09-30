/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.util.*;

// line 3 "../../../../../model.ump"
public class Quoridor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Quoridor Associations
  private List<Player> players;
  private List<Match> matches;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Quoridor()
  {
    players = new ArrayList<Player>();
    matches = new ArrayList<Match>();
  }

  //------------------------
  // INTERFACE
  //------------------------
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
  /* Code from template association_GetMany */
  public Match getMatche(int index)
  {
    Match aMatche = matches.get(index);
    return aMatche;
  }

  public List<Match> getMatches()
  {
    List<Match> newMatches = Collections.unmodifiableList(matches);
    return newMatches;
  }

  public int numberOfMatches()
  {
    int number = matches.size();
    return number;
  }

  public boolean hasMatches()
  {
    boolean has = matches.size() > 0;
    return has;
  }

  public int indexOfMatche(Match aMatche)
  {
    int index = matches.indexOf(aMatche);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Player addPlayer(String aName, int aElo)
  {
    return new Player(aName, aElo, this);
  }

  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    Quoridor existingQuoridor = aPlayer.getQuoridor();
    boolean isNewQuoridor = existingQuoridor != null && !this.equals(existingQuoridor);
    if (isNewQuoridor)
    {
      aPlayer.setQuoridor(this);
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
    //Unable to remove aPlayer, as it must always have a quoridor
    if (!this.equals(aPlayer.getQuoridor()))
    {
      players.remove(aPlayer);
      wasRemoved = true;
    }
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMatches()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Match addMatche(Match.GameState aState, int aId, Board aBoard)
  {
    return new Match(aState, aId, aBoard, this);
  }

  public boolean addMatche(Match aMatche)
  {
    boolean wasAdded = false;
    if (matches.contains(aMatche)) { return false; }
    Quoridor existingQuoridor = aMatche.getQuoridor();
    boolean isNewQuoridor = existingQuoridor != null && !this.equals(existingQuoridor);
    if (isNewQuoridor)
    {
      aMatche.setQuoridor(this);
    }
    else
    {
      matches.add(aMatche);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMatche(Match aMatche)
  {
    boolean wasRemoved = false;
    //Unable to remove aMatche, as it must always have a quoridor
    if (!this.equals(aMatche.getQuoridor()))
    {
      matches.remove(aMatche);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMatcheAt(Match aMatche, int index)
  {  
    boolean wasAdded = false;
    if(addMatche(aMatche))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMatches()) { index = numberOfMatches() - 1; }
      matches.remove(aMatche);
      matches.add(index, aMatche);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMatcheAt(Match aMatche, int index)
  {
    boolean wasAdded = false;
    if(matches.contains(aMatche))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMatches()) { index = numberOfMatches() - 1; }
      matches.remove(aMatche);
      matches.add(index, aMatche);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMatcheAt(aMatche, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (players.size() > 0)
    {
      Player aPlayer = players.get(players.size() - 1);
      aPlayer.delete();
      players.remove(aPlayer);
    }
    
    while (matches.size() > 0)
    {
      Match aMatche = matches.get(matches.size() - 1);
      aMatche.delete();
      matches.remove(aMatche);
    }
    
  }

}