/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mgcill.ecse223.quoridor.model;

// line 23 "../../../../../model.ump"
// line 64 "../../../../../model.ump"
public class Pawn
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Color { RED, BLACK }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Pawn Attributes
  private Color color;

  //Pawn Associations
  private Tile tile;
  private Player player;
  private Board board;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Pawn(Color aColor, Tile aTile, Player aPlayer, Board aBoard)
  {
    color = aColor;
    boolean didAddTile = setTile(aTile);
    if (!didAddTile)
    {
      throw new RuntimeException("Unable to create pawn due to tile");
    }
    if (aPlayer == null || aPlayer.getPawn() != null)
    {
      throw new RuntimeException("Unable to create Pawn due to aPlayer");
    }
    player = aPlayer;
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create pawn due to board");
    }
  }

  public Pawn(Color aColor, Tile aTile, String aNameForPlayer, Game aGameForPlayer, Board aBoard)
  {
    color = aColor;
    boolean didAddTile = setTile(aTile);
    if (!didAddTile)
    {
      throw new RuntimeException("Unable to create pawn due to tile");
    }
    player = new Player(aNameForPlayer, this, aGameForPlayer);
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create pawn due to board");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setColor(Color aColor)
  {
    boolean wasSet = false;
    color = aColor;
    wasSet = true;
    return wasSet;
  }

  public Color getColor()
  {
    return color;
  }
  /* Code from template association_GetOne */
  public Tile getTile()
  {
    return tile;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setTile(Tile aNewTile)
  {
    boolean wasSet = false;
    if (aNewTile == null)
    {
      //Unable to setTile to null, as pawn must always be associated to a tile
      return wasSet;
    }
    
    Pawn existingPawn = aNewTile.getPawn();
    if (existingPawn != null && !equals(existingPawn))
    {
      //Unable to setTile, the current tile already has a pawn, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Tile anOldTile = tile;
    tile = aNewTile;
    tile.setPawn(this);

    if (anOldTile != null)
    {
      anOldTile.setPawn(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBoard(Board aBoard)
  {
    boolean wasSet = false;
    if (aBoard == null)
    {
      return wasSet;
    }

    Board existingBoard = board;
    board = aBoard;
    if (existingBoard != null && !existingBoard.equals(aBoard))
    {
      existingBoard.removePawn(this);
    }
    board.addPawn(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Tile existingTile = tile;
    tile = null;
    if (existingTile != null)
    {
      existingTile.setPawn(null);
    }
    Player existingPlayer = player;
    player = null;
    if (existingPlayer != null)
    {
      existingPlayer.delete();
    }
    Board placeholderBoard = board;
    this.board = null;
    if(placeholderBoard != null)
    {
      placeholderBoard.removePawn(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "color" + "=" + (getColor() != null ? !getColor().equals(this)  ? getColor().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "tile = "+(getTile()!=null?Integer.toHexString(System.identityHashCode(getTile())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "board = "+(getBoard()!=null?Integer.toHexString(System.identityHashCode(getBoard())):"null");
  }
}