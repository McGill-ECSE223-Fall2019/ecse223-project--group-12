/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import ca.mcgill.ecse223.quoridor.model.PlayerEnrollment.Outcome;
import java.time.Duration;

// line 46 "../../../../../model.ump"
public class Pawn
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Color { WHITE, BLACK }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Pawn Attributes
  private Color color;

  //Pawn Associations
  private Tile currentPos;
  private PlayerEnrollment enrollment;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Pawn(Color aColor, Tile aCurrentPos, PlayerEnrollment aEnrollment)
  {
    color = aColor;
    if (!setCurrentPos(aCurrentPos))
    {
      throw new RuntimeException("Unable to create Pawn due to aCurrentPos");
    }
    if (aEnrollment == null || aEnrollment.getPawn() != null)
    {
      throw new RuntimeException("Unable to create Pawn due to aEnrollment");
    }
    enrollment = aEnrollment;
  }

  public Pawn(Color aColor, Tile aCurrentPos, String aIdForEnrollment, Outcome aOutcomeForEnrollment, Duration aThinkingTimeForEnrollment, Player aPlayerForEnrollment, Match aMatchForEnrollment)
  {
    color = aColor;
    boolean didAddCurrentPos = setCurrentPos(aCurrentPos);
    if (!didAddCurrentPos)
    {
      throw new RuntimeException("Unable to create pawn due to currentPos");
    }
    enrollment = new PlayerEnrollment(aIdForEnrollment, aOutcomeForEnrollment, aThinkingTimeForEnrollment, this, aPlayerForEnrollment, aMatchForEnrollment);
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
  public PlayerEnrollment getEnrollment()
  {
    return enrollment;
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
    PlayerEnrollment existingEnrollment = enrollment;
    enrollment = null;
    if (existingEnrollment != null)
    {
      existingEnrollment.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "color" + "=" + (getColor() != null ? !getColor().equals(this)  ? getColor().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentPos = "+(getCurrentPos()!=null?Integer.toHexString(System.identityHashCode(getCurrentPos())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "enrollment = "+(getEnrollment()!=null?Integer.toHexString(System.identityHashCode(getEnrollment())):"null");
  }
}