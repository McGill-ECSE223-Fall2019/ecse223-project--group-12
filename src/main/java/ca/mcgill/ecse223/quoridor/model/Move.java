/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;

// line 64 "../../../../../model.ump"
public class Move
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Move Associations
  private Move previousMove;
  private Tile tile;
  private PlayerEnrollment playerEnrollment;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Move(Tile aTile, PlayerEnrollment aPlayerEnrollment)
  {
    if (!setTile(aTile))
    {
      throw new RuntimeException("Unable to create Move due to aTile");
    }
    boolean didAddPlayerEnrollment = setPlayerEnrollment(aPlayerEnrollment);
    if (!didAddPlayerEnrollment)
    {
      throw new RuntimeException("Unable to create move due to playerEnrollment");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Move getPreviousMove()
  {
    return previousMove;
  }

  public boolean hasPreviousMove()
  {
    boolean has = previousMove != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Tile getTile()
  {
    return tile;
  }
  /* Code from template association_GetOne */
  public PlayerEnrollment getPlayerEnrollment()
  {
    return playerEnrollment;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setPreviousMove(Move aNewPreviousMove)
  {
    boolean wasSet = false;
    previousMove = aNewPreviousMove;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setTile(Tile aNewTile)
  {
    boolean wasSet = false;
    if (aNewTile != null)
    {
      tile = aNewTile;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setPlayerEnrollment(PlayerEnrollment aPlayerEnrollment)
  {
    boolean wasSet = false;
    if (aPlayerEnrollment == null)
    {
      return wasSet;
    }

    PlayerEnrollment existingPlayerEnrollment = playerEnrollment;
    playerEnrollment = aPlayerEnrollment;
    if (existingPlayerEnrollment != null && !existingPlayerEnrollment.equals(aPlayerEnrollment))
    {
      existingPlayerEnrollment.removeMove(this);
    }
    playerEnrollment.addMove(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    previousMove = null;
    tile = null;
    PlayerEnrollment placeholderPlayerEnrollment = playerEnrollment;
    this.playerEnrollment = null;
    if(placeholderPlayerEnrollment != null)
    {
      placeholderPlayerEnrollment.removeMove(this);
    }
  }

}