/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;

// line 42 "../../../../../model.ump"
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
  private Tile currentPos;
  private PlayerEnrollment playerEnrollment;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Pawn(Color aColor, Tile aCurrentPos, PlayerEnrollment aPlayerEnrollment)
  {
    color = aColor;
    if (!setCurrentPos(aCurrentPos))
    {
      throw new RuntimeException("Unable to create Pawn due to aCurrentPos");
    }
    if (aPlayerEnrollment == null || aPlayerEnrollment.getPawn() != null)
    {
      throw new RuntimeException("Unable to create Pawn due to aPlayerEnrollment");
    }
    playerEnrollment = aPlayerEnrollment;
  }

  public Pawn(Color aColor, Tile aCurrentPos, Player aPlayerForPlayerEnrollment, Match aMatchForPlayerEnrollment)
  {
    color = aColor;
    boolean didAddCurrentPos = setCurrentPos(aCurrentPos);
    if (!didAddCurrentPos)
    {
      throw new RuntimeException("Unable to create pawn due to currentPos");
    }
    playerEnrollment = new PlayerEnrollment(this, aPlayerForPlayerEnrollment, aMatchForPlayerEnrollment);
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
  public Tile getCurrentPos()
  {
    return currentPos;
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

  public void delete()
  {
    currentPos = null;
    PlayerEnrollment existingPlayerEnrollment = playerEnrollment;
    playerEnrollment = null;
    if (existingPlayerEnrollment != null)
    {
      existingPlayerEnrollment.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "color" + "=" + (getColor() != null ? !getColor().equals(this)  ? getColor().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentPos = "+(getCurrentPos()!=null?Integer.toHexString(System.identityHashCode(getCurrentPos())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "playerEnrollment = "+(getPlayerEnrollment()!=null?Integer.toHexString(System.identityHashCode(getPlayerEnrollment())):"null");
  }
}