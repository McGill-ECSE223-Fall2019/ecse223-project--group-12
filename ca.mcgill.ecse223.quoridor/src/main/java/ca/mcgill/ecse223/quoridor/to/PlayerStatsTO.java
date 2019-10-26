/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.to;
import java.sql.Time;

// line 20 "../../../../../QuoridorTO.ump"
public class PlayerStatsTO
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayerStatsTO Attributes
  private String name;
  private Time remaningTime;
  private int remainingWalls;
  private String moveMode;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayerStatsTO(String aName, Time aRemaningTime, int aRemainingWalls, String aMoveMode)
  {
    name = aName;
    remaningTime = aRemaningTime;
    remainingWalls = aRemainingWalls;
    moveMode = aMoveMode;
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

  public boolean setRemaningTime(Time aRemaningTime)
  {
    boolean wasSet = false;
    remaningTime = aRemaningTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setRemainingWalls(int aRemainingWalls)
  {
    boolean wasSet = false;
    remainingWalls = aRemainingWalls;
    wasSet = true;
    return wasSet;
  }

  public boolean setMoveMode(String aMoveMode)
  {
    boolean wasSet = false;
    moveMode = aMoveMode;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public Time getRemaningTime()
  {
    return remaningTime;
  }

  public int getRemainingWalls()
  {
    return remainingWalls;
  }

  public String getMoveMode()
  {
    return moveMode;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "remainingWalls" + ":" + getRemainingWalls()+ "," +
            "moveMode" + ":" + getMoveMode()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "remaningTime" + "=" + (getRemaningTime() != null ? !getRemaningTime().equals(this)  ? getRemaningTime().toString().replaceAll("  ","    ") : "this" : "null");
  }
}