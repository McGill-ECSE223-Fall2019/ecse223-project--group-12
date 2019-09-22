/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mgcill.ecse223.quoridor.model;

// line 29 "../../../../../model.ump"
// line 71 "../../../../../model.ump"
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
  private Tile position;
  private Board board;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Wall(Orientation aOrientation, Board aBoard)
  {
    orientation = aOrientation;
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create wall due to board");
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
  public Tile getPosition()
  {
    return position;
  }

  public boolean hasPosition()
  {
    boolean has = position != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setPosition(Tile aNewPosition)
  {
    boolean wasSet = false;
    if (aNewPosition == null)
    {
      Tile existingPosition = position;
      position = null;
      
      if (existingPosition != null && existingPosition.getWall() != null)
      {
        existingPosition.setWall(null);
      }
      wasSet = true;
      return wasSet;
    }

    Tile currentPosition = getPosition();
    if (currentPosition != null && !currentPosition.equals(aNewPosition))
    {
      currentPosition.setWall(null);
    }

    position = aNewPosition;
    Wall existingWall = aNewPosition.getWall();

    if (!equals(existingWall))
    {
      aNewPosition.setWall(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setBoard(Board aBoard)
  {
    boolean wasSet = false;
    //Must provide board to wall
    if (aBoard == null)
    {
      return wasSet;
    }

    //board already at maximum (20)
    if (aBoard.numberOfWalls() >= Board.maximumNumberOfWalls())
    {
      return wasSet;
    }
    
    Board existingBoard = board;
    board = aBoard;
    if (existingBoard != null && !existingBoard.equals(aBoard))
    {
      boolean didRemove = existingBoard.removeWall(this);
      if (!didRemove)
      {
        board = existingBoard;
        return wasSet;
      }
    }
    board.addWall(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (position != null)
    {
      position.setWall(null);
    }
    Board placeholderBoard = board;
    this.board = null;
    if(placeholderBoard != null)
    {
      placeholderBoard.removeWall(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "orientation" + "=" + (getOrientation() != null ? !getOrientation().equals(this)  ? getOrientation().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "position = "+(getPosition()!=null?Integer.toHexString(System.identityHashCode(getPosition())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "board = "+(getBoard()!=null?Integer.toHexString(System.identityHashCode(getBoard())):"null");
  }
}