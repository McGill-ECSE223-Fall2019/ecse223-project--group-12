/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mgcill.ecse223.quoridor.model;
import java.util.*;

import ca.mgcill.ecse223.quoridor.model.Game.GameState;

// line 35 "../../../../../model.ump"
// line 77 "../../../../../model.ump"
public class Board
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Orientation { VERTICAL, HORIZONTAL }
  public enum Color { RED, BLACK }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Attributes
  private boolean isValid;

  //Board Associations
  private List<Tile> tiles;
  private List<Wall> walls;
  private List<Pawn> pawns;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(boolean aIsValid, Game aGame)
  {
    isValid = aIsValid;
    tiles = new ArrayList<Tile>();
    walls = new ArrayList<Wall>();
    pawns = new ArrayList<Pawn>();
    if (aGame == null || aGame.getBoard() != null)
    {
      throw new RuntimeException("Unable to create Board due to aGame");
    }
    game = aGame;
  }

  public Board(boolean aIsValid, GameState aStateForGame, boolean aIsNewForGame)
  {
    isValid = aIsValid;
    tiles = new ArrayList<Tile>();
    walls = new ArrayList<Wall>();
    pawns = new ArrayList<Pawn>();
    game = new Game(aStateForGame, aIsNewForGame, this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsValid(boolean aIsValid)
  {
    boolean wasSet = false;
    isValid = aIsValid;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsValid()
  {
    return isValid;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsValid()
  {
    return isValid;
  }
  /* Code from template association_GetMany */
  public Tile getTile(int index)
  {
    Tile aTile = tiles.get(index);
    return aTile;
  }

  public List<Tile> getTiles()
  {
    List<Tile> newTiles = Collections.unmodifiableList(tiles);
    return newTiles;
  }

  public int numberOfTiles()
  {
    int number = tiles.size();
    return number;
  }

  public boolean hasTiles()
  {
    boolean has = tiles.size() > 0;
    return has;
  }

  public int indexOfTile(Tile aTile)
  {
    int index = tiles.indexOf(aTile);
    return index;
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
  public Pawn getPawn(int index)
  {
    Pawn aPawn = pawns.get(index);
    return aPawn;
  }

  public List<Pawn> getPawns()
  {
    List<Pawn> newPawns = Collections.unmodifiableList(pawns);
    return newPawns;
  }

  public int numberOfPawns()
  {
    int number = pawns.size();
    return number;
  }

  public boolean hasPawns()
  {
    boolean has = pawns.size() > 0;
    return has;
  }

  public int indexOfPawn(Pawn aPawn)
  {
    int index = pawns.indexOf(aPawn);
    return index;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfTilesValid()
  {
    boolean isValid = numberOfTiles() >= minimumNumberOfTiles();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTiles()
  {
    return 1;
  }
  /* Code from template association_AddMandatoryManyToOne */
  public Tile addTile(int aXPos, int aYPos)
  {
    Tile aNewTile = new Tile(aXPos, aYPos, this);
    return aNewTile;
  }

  public boolean addTile(Tile aTile)
  {
    boolean wasAdded = false;
    if (tiles.contains(aTile)) { return false; }
    Board existingBoard = aTile.getBoard();
    boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);

    if (isNewBoard && existingBoard.numberOfTiles() <= minimumNumberOfTiles())
    {
      return wasAdded;
    }
    if (isNewBoard)
    {
      aTile.setBoard(this);
    }
    else
    {
      tiles.add(aTile);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTile(Tile aTile)
  {
    boolean wasRemoved = false;
    //Unable to remove aTile, as it must always have a board
    if (this.equals(aTile.getBoard()))
    {
      return wasRemoved;
    }

    //board already at minimum (1)
    if (numberOfTiles() <= minimumNumberOfTiles())
    {
      return wasRemoved;
    }

    tiles.remove(aTile);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTileAt(Tile aTile, int index)
  {  
    boolean wasAdded = false;
    if(addTile(aTile))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTiles()) { index = numberOfTiles() - 1; }
      tiles.remove(aTile);
      tiles.add(index, aTile);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTileAt(Tile aTile, int index)
  {
    boolean wasAdded = false;
    if(tiles.contains(aTile))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTiles()) { index = numberOfTiles() - 1; }
      tiles.remove(aTile);
      tiles.add(index, aTile);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTileAt(aTile, index);
    }
    return wasAdded;
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
    return 20;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWalls()
  {
    return 20;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfWalls()
  {
    return 20;
  }
  /* Code from template association_AddMNToOnlyOne */
//  public Wall addWall(Orientation aOrientation)
//  {
//    if (numberOfWalls() >= maximumNumberOfWalls())
//    {
//      return null;
//    }
//    else
//    {
//      return new Wall(aOrientation, this);
//    }
//  }

  public boolean addWall(Wall aWall)
  {
    boolean wasAdded = false;
    if (walls.contains(aWall)) { return false; }
    if (numberOfWalls() >= maximumNumberOfWalls())
    {
      return wasAdded;
    }

    Board existingBoard = aWall.getBoard();
    boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);

    if (isNewBoard && existingBoard.numberOfWalls() <= minimumNumberOfWalls())
    {
      return wasAdded;
    }

    if (isNewBoard)
    {
      aWall.setBoard(this);
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
    //Unable to remove aWall, as it must always have a board
    if (this.equals(aWall.getBoard()))
    {
      return wasRemoved;
    }

    //board already at minimum (20)
    if (numberOfWalls() <= minimumNumberOfWalls())
    {
      return wasRemoved;
    }
    walls.remove(aWall);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPawns()
  {
    return 0;
  }
//  /* Code from template association_AddManyToOne */
//  public Pawn addPawn(Color aColor, Tile aTile, Player aPlayer)
//  {
//    return new Pawn(aColor, aTile, aPlayer, this);
//  }

  public boolean addPawn(Pawn aPawn)
  {
    boolean wasAdded = false;
    if (pawns.contains(aPawn)) { return false; }
    Board existingBoard = aPawn.getBoard();
    boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);
    if (isNewBoard)
    {
      aPawn.setBoard(this);
    }
    else
    {
      pawns.add(aPawn);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePawn(Pawn aPawn)
  {
    boolean wasRemoved = false;
    //Unable to remove aPawn, as it must always have a board
    if (!this.equals(aPawn.getBoard()))
    {
      pawns.remove(aPawn);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPawnAt(Pawn aPawn, int index)
  {  
    boolean wasAdded = false;
    if(addPawn(aPawn))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPawns()) { index = numberOfPawns() - 1; }
      pawns.remove(aPawn);
      pawns.add(index, aPawn);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePawnAt(Pawn aPawn, int index)
  {
    boolean wasAdded = false;
    if(pawns.contains(aPawn))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPawns()) { index = numberOfPawns() - 1; }
      pawns.remove(aPawn);
      pawns.add(index, aPawn);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPawnAt(aPawn, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=tiles.size(); i > 0; i--)
    {
      Tile aTile = tiles.get(i - 1);
      aTile.delete();
    }
    for(int i=walls.size(); i > 0; i--)
    {
      Wall aWall = walls.get(i - 1);
      aWall.delete();
    }
    for(int i=pawns.size(); i > 0; i--)
    {
      Pawn aPawn = pawns.get(i - 1);
      aPawn.delete();
    }
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isValid" + ":" + getIsValid()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}