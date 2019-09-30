/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.util.*;

// line 55 "../../../../../model.ump"
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
  private boolean isOnBoard;

  //Wall Associations
  private Tile currentPos;
  private List<WallMove> wallMoves;
  private PlayerEnrollment enrollment;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Wall(Orientation aOrientation, boolean aIsOnBoard, PlayerEnrollment aEnrollment)
  {
    orientation = aOrientation;
    isOnBoard = aIsOnBoard;
    wallMoves = new ArrayList<WallMove>();
    boolean didAddEnrollment = setEnrollment(aEnrollment);
    if (!didAddEnrollment)
    {
      throw new RuntimeException("Unable to create wall due to enrollment");
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

  public boolean setIsOnBoard(boolean aIsOnBoard)
  {
    boolean wasSet = false;
    isOnBoard = aIsOnBoard;
    wasSet = true;
    return wasSet;
  }

  public Orientation getOrientation()
  {
    return orientation;
  }

  public boolean getIsOnBoard()
  {
    return isOnBoard;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsOnBoard()
  {
    return isOnBoard;
  }
  /* Code from template association_GetOne */
  public Tile getCurrentPos()
  {
    return currentPos;
  }

  public boolean hasCurrentPos()
  {
    boolean has = currentPos != null;
    return has;
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
  public PlayerEnrollment getEnrollment()
  {
    return enrollment;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCurrentPos(Tile aNewCurrentPos)
  {
    boolean wasSet = false;
    currentPos = aNewCurrentPos;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWallMoves()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public WallMove addWallMove(int aTurnNumber, boolean aIsValid, boolean aConfirmed, Tile aTargetPos, PlayerEnrollment aEnrollment)
  {
    return new WallMove(aTurnNumber, aIsValid, aConfirmed, aTargetPos, aEnrollment, this);
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
  public boolean setEnrollment(PlayerEnrollment aEnrollment)
  {
    boolean wasSet = false;
    //Must provide enrollment to wall
    if (aEnrollment == null)
    {
      return wasSet;
    }

    //enrollment already at maximum (10)
    if (aEnrollment.numberOfWalls() >= PlayerEnrollment.maximumNumberOfWalls())
    {
      return wasSet;
    }
    
    PlayerEnrollment existingEnrollment = enrollment;
    enrollment = aEnrollment;
    if (existingEnrollment != null && !existingEnrollment.equals(aEnrollment))
    {
      boolean didRemove = existingEnrollment.removeWall(this);
      if (!didRemove)
      {
        enrollment = existingEnrollment;
        return wasSet;
      }
    }
    enrollment.addWall(this);
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
    PlayerEnrollment placeholderEnrollment = enrollment;
    this.enrollment = null;
    if(placeholderEnrollment != null)
    {
      placeholderEnrollment.removeWall(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isOnBoard" + ":" + getIsOnBoard()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "orientation" + "=" + (getOrientation() != null ? !getOrientation().equals(this)  ? getOrientation().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentPos = "+(getCurrentPos()!=null?Integer.toHexString(System.identityHashCode(getCurrentPos())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "enrollment = "+(getEnrollment()!=null?Integer.toHexString(System.identityHashCode(getEnrollment())):"null");
  }
}