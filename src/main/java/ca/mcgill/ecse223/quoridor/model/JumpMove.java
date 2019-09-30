/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;

// line 91 "../../../../../model.ump"
public class JumpMove extends PawnMove
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public JumpMove(int aTurnNumber, boolean aIsValid, boolean aConfirmed, Tile aTargetPos, PlayerEnrollment aEnrollment)
  {
    super(aTurnNumber, aIsValid, aConfirmed, aTargetPos, aEnrollment);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}