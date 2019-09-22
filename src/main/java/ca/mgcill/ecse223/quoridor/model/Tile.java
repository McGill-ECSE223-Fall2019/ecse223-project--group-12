/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mgcill.ecse223.quoridor.model;

// line 43 "../../../../../model.ump"
// line 88 "../../../../../model.ump"
public class Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tile Attributes
  private int xPos;
  private int yPos;

  //Tile Associations
  private Pawn pawn;
  private Wall wall;
  private Board board;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tile(int aXPos, int aYPos, Board aBoard)
  {
    xPos = aXPos;
    yPos = aYPos;
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create tile due to board");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setXPos(int aXPos)
  {
    boolean wasSet = false;
    xPos = aXPos;
    wasSet = true;
    return wasSet;
  }

  public boolean setYPos(int aYPos)
  {
    boolean wasSet = false;
    yPos = aYPos;
    wasSet = true;
    return wasSet;
  }

  public int getXPos()
  {
    return xPos;
  }

  public int getYPos()
  {
    return yPos;
  }
  /* Code from template association_GetOne */
  public Pawn getPawn()
  {
    return pawn;
  }

  public boolean hasPawn()
  {
    boolean has = pawn != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Wall getWall()
  {
    return wall;
  }

  public boolean hasWall()
  {
    boolean has = wall != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setPawn(Pawn aNewPawn)
  {
    boolean wasSet = false;
    if (pawn != null && !pawn.equals(aNewPawn) && equals(pawn.getTile()))
    {
      //Unable to setPawn, as existing pawn would become an orphan
      return wasSet;
    }

    pawn = aNewPawn;
    Tile anOldTile = aNewPawn != null ? aNewPawn.getTile() : null;

    if (!this.equals(anOldTile))
    {
      if (anOldTile != null)
      {
        anOldTile.pawn = null;
      }
      if (pawn != null)
      {
        pawn.setTile(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setWall(Wall aNewWall)
  {
    boolean wasSet = false;
    if (aNewWall == null)
    {
      Wall existingWall = wall;
      wall = null;
      
      if (existingWall != null && existingWall.getPosition() != null)
      {
        existingWall.setPosition(null);
      }
      wasSet = true;
      return wasSet;
    }

    Wall currentWall = getWall();
    if (currentWall != null && !currentWall.equals(aNewWall))
    {
      currentWall.setPosition(null);
    }

    wall = aNewWall;
    Tile existingPosition = aNewWall.getPosition();

    if (!equals(existingPosition))
    {
      aNewWall.setPosition(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMandatoryMany */
  public boolean setBoard(Board aBoard)
  {
    boolean wasSet = false;
    //Must provide board to tile
    if (aBoard == null)
    {
      return wasSet;
    }

    if (board != null && board.numberOfTiles() <= Board.minimumNumberOfTiles())
    {
      return wasSet;
    }

    Board existingBoard = board;
    board = aBoard;
    if (existingBoard != null && !existingBoard.equals(aBoard))
    {
      boolean didRemove = existingBoard.removeTile(this);
      if (!didRemove)
      {
        board = existingBoard;
        return wasSet;
      }
    }
    board.addTile(this);
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
    if (wall != null)
    {
      wall.setPosition(null);
    }
    Board placeholderBoard = board;
    this.board = null;
    if(placeholderBoard != null)
    {
      placeholderBoard.removeTile(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "xPos" + ":" + getXPos()+ "," +
            "yPos" + ":" + getYPos()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "pawn = "+(getPawn()!=null?Integer.toHexString(System.identityHashCode(getPawn())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "wall = "+(getWall()!=null?Integer.toHexString(System.identityHashCode(getWall())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "board = "+(getBoard()!=null?Integer.toHexString(System.identityHashCode(getBoard())):"null");
  }
}