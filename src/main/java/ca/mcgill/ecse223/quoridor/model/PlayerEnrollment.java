/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import ca.mcgill.ecse223.quoridor.model.Pawn.Color;
import java.util.*;

// line 35 "../../../../../model.ump"
public class PlayerEnrollment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayerEnrollment Associations
  private Pawn pawn;
  private List<Wall> walls;
  private List<Move> moves;
  private Player player;
  private Match match;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayerEnrollment(Pawn aPawn, Player aPlayer, Match aMatch)
  {
    if (aPawn == null || aPawn.getPlayerEnrollment() != null)
    {
      throw new RuntimeException("Unable to create PlayerEnrollment due to aPawn");
    }
    pawn = aPawn;
    walls = new ArrayList<Wall>();
    moves = new ArrayList<Move>();
    boolean didAddPlayer = setPlayer(aPlayer);
    if (!didAddPlayer)
    {
      throw new RuntimeException("Unable to create enrolledMatche due to player");
    }
    boolean didAddMatch = setMatch(aMatch);
    if (!didAddMatch)
    {
      throw new RuntimeException("Unable to create enrolledPlayer due to match");
    }
  }

  public PlayerEnrollment(Color aColorForPawn, Tile aCurrentPosForPawn, Player aPlayer, Match aMatch)
  {
    pawn = new Pawn(aColorForPawn, aCurrentPosForPawn, this);
    walls = new ArrayList<Wall>();
    moves = new ArrayList<Move>();
    boolean didAddPlayer = setPlayer(aPlayer);
    if (!didAddPlayer)
    {
      throw new RuntimeException("Unable to create enrolledMatche due to player");
    }
    boolean didAddMatch = setMatch(aMatch);
    if (!didAddMatch)
    {
      throw new RuntimeException("Unable to create enrolledPlayer due to match");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Pawn getPawn()
  {
    return pawn;
  }
  /* Code from template association_GetMany */
  public Wall getWall(int index)
  {
    Wall aWall = walls.get(index);
    return aWall;
  }

  public List<Wall> getWalls()
  {
    List<Wall> newWalls = Collections.unmodifiableList(walls);
    return newWalls;
  }

  public int numberOfWalls()
  {
    int number = walls.size();
    return number;
  }

  public boolean hasWalls()
  {
    boolean has = walls.size() > 0;
    return has;
  }

  public int indexOfWall(Wall aWall)
  {
    int index = walls.indexOf(aWall);
    return index;
  }
  /* Code from template association_GetMany */
  public Move getMove(int index)
  {
    Move aMove = moves.get(index);
    return aMove;
  }

  public List<Move> getMoves()
  {
    List<Move> newMoves = Collections.unmodifiableList(moves);
    return newMoves;
  }

  public int numberOfMoves()
  {
    int number = moves.size();
    return number;
  }

  public boolean hasMoves()
  {
    boolean has = moves.size() > 0;
    return has;
  }

  public int indexOfMove(Move aMove)
  {
    int index = moves.indexOf(aMove);
    return index;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }
  /* Code from template association_GetOne */
  public Match getMatch()
  {
    return match;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfWallsValid()
  {
    boolean isValid = numberOfWalls() >= minimumNumberOfWalls() && numberOfWalls() <= maximumNumberOfWalls();
    return isValid;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfWalls()
  {
    return 10;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWalls()
  {
    return 10;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfWalls()
  {
    return 10;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Wall addWall(Wall.Orientation aOrientation, Tile aCurrentPos)
  {
    if (numberOfWalls() >= maximumNumberOfWalls())
    {
      return null;
    }
    else
    {
      return new Wall(aOrientation, aCurrentPos, this);
    }
  }

  public boolean addWall(Wall aWall)
  {
    boolean wasAdded = false;
    if (walls.contains(aWall)) { return false; }
    if (numberOfWalls() >= maximumNumberOfWalls())
    {
      return wasAdded;
    }

    PlayerEnrollment existingPlayerEnrollment = aWall.getPlayerEnrollment();
    boolean isNewPlayerEnrollment = existingPlayerEnrollment != null && !this.equals(existingPlayerEnrollment);

    if (isNewPlayerEnrollment && existingPlayerEnrollment.numberOfWalls() <= minimumNumberOfWalls())
    {
      return wasAdded;
    }

    if (isNewPlayerEnrollment)
    {
      aWall.setPlayerEnrollment(this);
    }
    else
    {
      walls.add(aWall);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWall(Wall aWall)
  {
    boolean wasRemoved = false;
    //Unable to remove aWall, as it must always have a playerEnrollment
    if (this.equals(aWall.getPlayerEnrollment()))
    {
      return wasRemoved;
    }

    //playerEnrollment already at minimum (10)
    if (numberOfWalls() <= minimumNumberOfWalls())
    {
      return wasRemoved;
    }
    walls.remove(aWall);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMoves()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Move addMove(Tile aTile)
  {
    return new Move(aTile, this);
  }

  public boolean addMove(Move aMove)
  {
    boolean wasAdded = false;
    if (moves.contains(aMove)) { return false; }
    PlayerEnrollment existingPlayerEnrollment = aMove.getPlayerEnrollment();
    boolean isNewPlayerEnrollment = existingPlayerEnrollment != null && !this.equals(existingPlayerEnrollment);
    if (isNewPlayerEnrollment)
    {
      aMove.setPlayerEnrollment(this);
    }
    else
    {
      moves.add(aMove);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMove(Move aMove)
  {
    boolean wasRemoved = false;
    //Unable to remove aMove, as it must always have a playerEnrollment
    if (!this.equals(aMove.getPlayerEnrollment()))
    {
      moves.remove(aMove);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMoveAt(Move aMove, int index)
  {  
    boolean wasAdded = false;
    if(addMove(aMove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMoves()) { index = numberOfMoves() - 1; }
      moves.remove(aMove);
      moves.add(index, aMove);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMoveAt(Move aMove, int index)
  {
    boolean wasAdded = false;
    if(moves.contains(aMove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMoves()) { index = numberOfMoves() - 1; }
      moves.remove(aMove);
      moves.add(index, aMove);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMoveAt(aMove, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setPlayer(Player aPlayer)
  {
    boolean wasSet = false;
    if (aPlayer == null)
    {
      return wasSet;
    }

    Player existingPlayer = player;
    player = aPlayer;
    if (existingPlayer != null && !existingPlayer.equals(aPlayer))
    {
      existingPlayer.removeEnrolledMatche(this);
    }
    player.addEnrolledMatche(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setMatch(Match aMatch)
  {
    boolean wasSet = false;
    //Must provide match to enrolledPlayer
    if (aMatch == null)
    {
      return wasSet;
    }

    //match already at maximum (2)
    if (aMatch.numberOfEnrolledPlayers() >= Match.maximumNumberOfEnrolledPlayers())
    {
      return wasSet;
    }
    
    Match existingMatch = match;
    match = aMatch;
    if (existingMatch != null && !existingMatch.equals(aMatch))
    {
      boolean didRemove = existingMatch.removeEnrolledPlayer(this);
      if (!didRemove)
      {
        match = existingMatch;
        return wasSet;
      }
    }
    match.addEnrolledPlayer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Pawn existingPawn = pawn;
    pawn = null;
    if (existingPawn != null)
    {
      existingPawn.delete();
    }
    while (walls.size() > 0)
    {
      Wall aWall = walls.get(walls.size() - 1);
      aWall.delete();
      walls.remove(aWall);
    }
    
    for(int i=moves.size(); i > 0; i--)
    {
      Move aMove = moves.get(i - 1);
      aMove.delete();
    }
    Player placeholderPlayer = player;
    this.player = null;
    if(placeholderPlayer != null)
    {
      placeholderPlayer.removeEnrolledMatche(this);
    }
    Match placeholderMatch = match;
    this.match = null;
    if(placeholderMatch != null)
    {
      placeholderMatch.removeEnrolledPlayer(this);
    }
  }

}