/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;

// line 75 "../../../../../model.ump"
public class WallMove extends Move
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WallMove Associations
  private Wall wall;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WallMove(Tile aTile, PlayerEnrollment aPlayerEnrollment, Wall aWall)
  {
    super(aTile, aPlayerEnrollment);
    boolean didAddWall = setWall(aWall);
    if (!didAddWall)
    {
      throw new RuntimeException("Unable to create wallMove due to wall");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Wall getWall()
  {
    return wall;
  }
  /* Code from template association_SetOneToMany */
  public boolean setWall(Wall aWall)
  {
    boolean wasSet = false;
    if (aWall == null)
    {
      return wasSet;
    }

    Wall existingWall = wall;
    wall = aWall;
    if (existingWall != null && !existingWall.equals(aWall))
    {
      existingWall.removeWallMove(this);
    }
    wall.addWallMove(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Wall placeholderWall = wall;
    this.wall = null;
    if(placeholderWall != null)
    {
      placeholderWall.removeWallMove(this);
    }
    super.delete();
  }

}