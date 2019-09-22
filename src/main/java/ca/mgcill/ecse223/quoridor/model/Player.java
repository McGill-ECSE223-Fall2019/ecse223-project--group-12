/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mgcill.ecse223.quoridor.model;

import ca.mgcill.ecse223.quoridor.model.Pawn.Color;

// line 17 "../../../../../model.ump"
// line 59 "../../../../../model.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private String name;

  //Player Associations
  private Pawn pawn;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(String aName, Pawn aPawn, Game aGame)
  {
    name = aName;
    if (aPawn == null || aPawn.getPlayer() != null)
    {
      throw new RuntimeException("Unable to create Player due to aPawn");
    }
    pawn = aPawn;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create player due to game");
    }
  }

  public Player(String aName, Color aColorForPawn, Tile aTileForPawn, Board aBoardForPawn, Game aGame)
  {
    name = aName;
    pawn = new Pawn(aColorForPawn, aTileForPawn, this, aBoardForPawn);
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create player due to game");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template association_GetOne */
  public Pawn getPawn()
  {
    return pawn;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_SetOneToMandatoryMany */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to player
    if (aGame == null)
    {
      return wasSet;
    }

    if (game != null && game.numberOfPlayers() <= Game.minimumNumberOfPlayers())
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removePlayer(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addPlayer(this);
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
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePlayer(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "pawn = "+(getPawn()!=null?Integer.toHexString(System.identityHashCode(getPawn())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}