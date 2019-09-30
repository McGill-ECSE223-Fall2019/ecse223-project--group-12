/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;

// line 71 "../../../../../model.ump"
public class Move
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Move Attributes
  private int turnNumber;
  private boolean isValid;
  private boolean confirmed;

  //Move Associations
  private Move previousMove;
  private Move nextMove;
  private Tile targetPos;
  private PlayerEnrollment enrollment;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Move(int aTurnNumber, boolean aIsValid, boolean aConfirmed, Tile aTargetPos, PlayerEnrollment aEnrollment)
  {
    turnNumber = aTurnNumber;
    isValid = aIsValid;
    confirmed = aConfirmed;
    if (!setTargetPos(aTargetPos))
    {
      throw new RuntimeException("Unable to create Move due to aTargetPos");
    }
    boolean didAddEnrollment = setEnrollment(aEnrollment);
    if (!didAddEnrollment)
    {
      throw new RuntimeException("Unable to create move due to enrollment");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTurnNumber(int aTurnNumber)
  {
    boolean wasSet = false;
    turnNumber = aTurnNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsValid(boolean aIsValid)
  {
    boolean wasSet = false;
    isValid = aIsValid;
    wasSet = true;
    return wasSet;
  }

  public boolean setConfirmed(boolean aConfirmed)
  {
    boolean wasSet = false;
    confirmed = aConfirmed;
    wasSet = true;
    return wasSet;
  }

  public int getTurnNumber()
  {
    return turnNumber;
  }

  public boolean getIsValid()
  {
    return isValid;
  }

  public boolean getConfirmed()
  {
    return confirmed;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsValid()
  {
    return isValid;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isConfirmed()
  {
    return confirmed;
  }
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
  public Move getNextMove()
  {
    return nextMove;
  }

  public boolean hasNextMove()
  {
    boolean has = nextMove != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Tile getTargetPos()
  {
    return targetPos;
  }
  /* Code from template association_GetOne */
  public PlayerEnrollment getEnrollment()
  {
    return enrollment;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setPreviousMove(Move aNewPreviousMove)
  {
    boolean wasSet = false;
    previousMove = aNewPreviousMove;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setNextMove(Move aNewNextMove)
  {
    boolean wasSet = false;
    nextMove = aNewNextMove;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setTargetPos(Tile aNewTargetPos)
  {
    boolean wasSet = false;
    if (aNewTargetPos != null)
    {
      targetPos = aNewTargetPos;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setEnrollment(PlayerEnrollment aEnrollment)
  {
    boolean wasSet = false;
    if (aEnrollment == null)
    {
      return wasSet;
    }

    PlayerEnrollment existingEnrollment = enrollment;
    enrollment = aEnrollment;
    if (existingEnrollment != null && !existingEnrollment.equals(aEnrollment))
    {
      existingEnrollment.removeMove(this);
    }
    enrollment.addMove(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    previousMove = null;
    nextMove = null;
    targetPos = null;
    PlayerEnrollment placeholderEnrollment = enrollment;
    this.enrollment = null;
    if(placeholderEnrollment != null)
    {
      placeholderEnrollment.removeMove(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "turnNumber" + ":" + getTurnNumber()+ "," +
            "isValid" + ":" + getIsValid()+ "," +
            "confirmed" + ":" + getConfirmed()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "targetPos = "+(getTargetPos()!=null?Integer.toHexString(System.identityHashCode(getTargetPos())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "enrollment = "+(getEnrollment()!=null?Integer.toHexString(System.identityHashCode(getEnrollment())):"null");
  }
}