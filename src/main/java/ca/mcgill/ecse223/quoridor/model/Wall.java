/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.util.*;

// line 49 "../../../../../model.ump"
public class Wall
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Orientation { VERTICAL, HORIZONTAL }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Wall Attributes
  private Orientation orientation;

  //Wall Associations
  private Tile currentPos;
  private List<WallMove> wallMoves;
  private PlayerEnrollment playerEnrollment;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Wall(Orientation aOrientation, Tile aCurrentPos, PlayerEnrollment aPlayerEnrollment)
  {
    orientation = aOrientation;
    if (!setCurrentPos(aCurrentPos))
    {
      throw new RuntimeException("Unable to create Wall due to aCurrentPos");
    }
    wallMoves = new ArrayList<WallMove>();
    boolean didAddPlayerEnrollment = setPlayerEnrollment(aPlayerEnrollment);
    if (!didAddPlayerEnrollment)
    {
      throw new RuntimeException("Unable to create wall due to playerEnrollment");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOrientation(Orientation aOrientation)
  {
    boolean wasSet = false;
    orientation = aOrientation;
    wasSet = true;
    return wasSet;
  }

  public Orientation getOrientation()
  {
    return orientation;
  }
  /* Code from template association_GetOne */
  public Tile getCurrentPos()
  {
    return currentPos;
  }
  /* Code from template association_GetMany */
  public WallMove getWallMove(int index)
  {
    WallMove aWallMove = wallMoves.get(index);
    return aWallMove;
  }

  public List<WallMove> getWallMoves()
  {
    List<WallMove> newWallMoves = Collections.unmodifiableList(wallMoves);
    return newWallMoves;
  }

  public int numberOfWallMoves()
  {
    int number = wallMoves.size();
    return number;
  }

  public boolean hasWallMoves()
  {
    boolean has = wallMoves.size() > 0;
    return has;
  }

  public int indexOfWallMove(WallMove aWallMove)
  {
    int index = wallMoves.indexOf(aWallMove);
    return index;
  }
  /* Code from template association_GetOne */
  public PlayerEnrollment getPlayerEnrollment()
  {
    return playerEnrollment;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCurrentPos(Tile aNewCurrentPos)
  {
    boolean wasSet = false;
    if (aNewCurrentPos != null)
    {
      currentPos = aNewCurrentPos;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWallMoves()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public WallMove addWallMove(Tile aTile, PlayerEnrollment aPlayerEnrollment)
  {
    return new WallMove(aTile, aPlayerEnrollment, this);
  }

  public boolean addWallMove(WallMove aWallMove)
  {
    boolean wasAdded = false;
    if (wallMoves.contains(aWallMove)) { return false; }
    Wall existingWall = aWallMove.getWall();
    boolean isNewWall = existingWall != null && !this.equals(existingWall);
    if (isNewWall)
    {
      aWallMove.setWall(this);
    }
    else
    {
      wallMoves.add(aWallMove);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWallMove(WallMove aWallMove)
  {
    boolean wasRemoved = false;
    //Unable to remove aWallMove, as it must always have a wall
    if (!this.equals(aWallMove.getWall()))
    {
      wallMoves.remove(aWallMove);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWallMoveAt(WallMove aWallMove, int index)
  {  
    boolean wasAdded = false;
    if(addWallMove(aWallMove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWallMoves()) { index = numberOfWallMoves() - 1; }
      wallMoves.remove(aWallMove);
      wallMoves.add(index, aWallMove);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWallMoveAt(WallMove aWallMove, int index)
  {
    boolean wasAdded = false;
    if(wallMoves.contains(aWallMove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWallMoves()) { index = numberOfWallMoves() - 1; }
      wallMoves.remove(aWallMove);
      wallMoves.add(index, aWallMove);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWallMoveAt(aWallMove, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setPlayerEnrollment(PlayerEnrollment aPlayerEnrollment)
  {
    boolean wasSet = false;
    //Must provide playerEnrollment to wall
    if (aPlayerEnrollment == null)
    {
      return wasSet;
    }

    //playerEnrollment already at maximum (10)
    if (aPlayerEnrollment.numberOfWalls() >= PlayerEnrollment.maximumNumberOfWalls())
    {
      return wasSet;
    }
    
    PlayerEnrollment existingPlayerEnrollment = playerEnrollment;
    playerEnrollment = aPlayerEnrollment;
    if (existingPlayerEnrollment != null && !existingPlayerEnrollment.equals(aPlayerEnrollment))
    {
      boolean didRemove = existingPlayerEnrollment.removeWall(this);
      if (!didRemove)
      {
        playerEnrollment = existingPlayerEnrollment;
        return wasSet;
      }
    }
    playerEnrollment.addWall(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    currentPos = null;
    for(int i=wallMoves.size(); i > 0; i--)
    {
      WallMove aWallMove = wallMoves.get(i - 1);
      aWallMove.delete();
    }
    PlayerEnrollment placeholderPlayerEnrollment = playerEnrollment;
    this.playerEnrollment = null;
    if(placeholderPlayerEnrollment != null)
    {
      placeholderPlayerEnrollment.removeWall(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "orientation" + "=" + (getOrientation() != null ? !getOrientation().equals(this)  ? getOrientation().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentPos = "+(getCurrentPos()!=null?Integer.toHexString(System.identityHashCode(getCurrentPos())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "playerEnrollment = "+(getPlayerEnrollment()!=null?Integer.toHexString(System.identityHashCode(getPlayerEnrollment())):"null");
  }
}