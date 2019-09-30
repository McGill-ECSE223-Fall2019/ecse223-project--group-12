/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.time.Duration;
import java.util.*;

// line 17 "../../../../../model.ump"
public class Match
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum GameState { NEW_GAME, SETUP, WHITE_PLAYER_TURN, BLACK_PLAYER_TURN, PAUSED, GAME_OVER }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Match Attributes
  private GameState state;
  private int id;

  //Match Associations
  private Board board;
  private List<PlayerEnrollment> enrolledPlayers;
  private Quoridor quoridor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Match(GameState aState, int aId, Board aBoard, Quoridor aQuoridor)
  {
    state = aState;
    id = aId;
    if (aBoard == null || aBoard.getMatch() != null)
    {
      throw new RuntimeException("Unable to create Match due to aBoard");
    }
    board = aBoard;
    enrolledPlayers = new ArrayList<PlayerEnrollment>();
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create matche due to quoridor");
    }
  }

  public Match(GameState aState, int aId, boolean aIsValidForBoard, Quoridor aQuoridor)
  {
    state = aState;
    id = aId;
    board = new Board(aIsValidForBoard, this);
    enrolledPlayers = new ArrayList<PlayerEnrollment>();
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create matche due to quoridor");
    }
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

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public GameState getState()
  {
    return state;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_GetMany */
  public PlayerEnrollment getEnrolledPlayer(int index)
  {
    PlayerEnrollment aEnrolledPlayer = enrolledPlayers.get(index);
    return aEnrolledPlayer;
  }

  public List<PlayerEnrollment> getEnrolledPlayers()
  {
    List<PlayerEnrollment> newEnrolledPlayers = Collections.unmodifiableList(enrolledPlayers);
    return newEnrolledPlayers;
  }

  public int numberOfEnrolledPlayers()
  {
    int number = enrolledPlayers.size();
    return number;
  }

  public boolean hasEnrolledPlayers()
  {
    boolean has = enrolledPlayers.size() > 0;
    return has;
  }

  public int indexOfEnrolledPlayer(PlayerEnrollment aEnrolledPlayer)
  {
    int index = enrolledPlayers.indexOf(aEnrolledPlayer);
    return index;
  }
  /* Code from template association_GetOne */
  public Quoridor getQuoridor()
  {
    return quoridor;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfEnrolledPlayersValid()
  {
    boolean isValid = numberOfEnrolledPlayers() >= minimumNumberOfEnrolledPlayers() && numberOfEnrolledPlayers() <= maximumNumberOfEnrolledPlayers();
    return isValid;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfEnrolledPlayers()
  {
    return 2;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEnrolledPlayers()
  {
    return 2;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfEnrolledPlayers()
  {
    return 2;
  }
  /* Code from template association_AddMNToOnlyOne */
  public PlayerEnrollment addEnrolledPlayer(String aId, PlayerEnrollment.Outcome aOutcome, Duration aThinkingTime, Pawn aPawn, Player aPlayer)
  {
    if (numberOfEnrolledPlayers() >= maximumNumberOfEnrolledPlayers())
    {
      return null;
    }
    else
    {
      return new PlayerEnrollment(aId, aOutcome, aThinkingTime, aPawn, aPlayer, this);
    }
  }

  public boolean addEnrolledPlayer(PlayerEnrollment aEnrolledPlayer)
  {
    boolean wasAdded = false;
    if (enrolledPlayers.contains(aEnrolledPlayer)) { return false; }
    if (numberOfEnrolledPlayers() >= maximumNumberOfEnrolledPlayers())
    {
      return wasAdded;
    }

    Match existingMatch = aEnrolledPlayer.getMatch();
    boolean isNewMatch = existingMatch != null && !this.equals(existingMatch);

    if (isNewMatch && existingMatch.numberOfEnrolledPlayers() <= minimumNumberOfEnrolledPlayers())
    {
      return wasAdded;
    }

    if (isNewMatch)
    {
      aEnrolledPlayer.setMatch(this);
    }
    else
    {
      enrolledPlayers.add(aEnrolledPlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeEnrolledPlayer(PlayerEnrollment aEnrolledPlayer)
  {
    boolean wasRemoved = false;
    //Unable to remove aEnrolledPlayer, as it must always have a match
    if (this.equals(aEnrolledPlayer.getMatch()))
    {
      return wasRemoved;
    }

    //match already at minimum (2)
    if (numberOfEnrolledPlayers() <= minimumNumberOfEnrolledPlayers())
    {
      return wasRemoved;
    }
    enrolledPlayers.remove(aEnrolledPlayer);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_SetOneToMany */
  public boolean setQuoridor(Quoridor aQuoridor)
  {
    boolean wasSet = false;
    if (aQuoridor == null)
    {
      return wasSet;
    }

    Quoridor existingQuoridor = quoridor;
    quoridor = aQuoridor;
    if (existingQuoridor != null && !existingQuoridor.equals(aQuoridor))
    {
      existingQuoridor.removeMatche(this);
    }
    quoridor.addMatche(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Board existingBoard = board;
    board = null;
    if (existingBoard != null)
    {
      existingBoard.delete();
    }
    while (enrolledPlayers.size() > 0)
    {
      PlayerEnrollment aEnrolledPlayer = enrolledPlayers.get(enrolledPlayers.size() - 1);
      aEnrolledPlayer.delete();
      enrolledPlayers.remove(aEnrolledPlayer);
    }
    
    Quoridor placeholderQuoridor = quoridor;
    this.quoridor = null;
    if(placeholderQuoridor != null)
    {
      placeholderQuoridor.removeMatche(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "state" + "=" + (getState() != null ? !getState().equals(this)  ? getState().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "board = "+(getBoard()!=null?Integer.toHexString(System.identityHashCode(getBoard())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "quoridor = "+(getQuoridor()!=null?Integer.toHexString(System.identityHashCode(getQuoridor())):"null");
  }
}