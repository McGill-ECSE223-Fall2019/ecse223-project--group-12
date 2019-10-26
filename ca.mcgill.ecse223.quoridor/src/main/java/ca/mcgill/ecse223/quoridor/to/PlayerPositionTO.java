/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.to;

// line 13 "../../../../../QuoridorTO.ump"
public class PlayerPositionTO
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum PlayerColor { White, Black }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayerPositionTO Attributes
  private int row;
  private int column;
  private PlayerColor playerColor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayerPositionTO(int aRow, int aColumn, PlayerColor aPlayerColor)
  {
    row = aRow;
    column = aColumn;
    playerColor = aPlayerColor;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRow(int aRow)
  {
    boolean wasSet = false;
    row = aRow;
    wasSet = true;
    return wasSet;
  }

  public boolean setColumn(int aColumn)
  {
    boolean wasSet = false;
    column = aColumn;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlayerColor(PlayerColor aPlayerColor)
  {
    boolean wasSet = false;
    playerColor = aPlayerColor;
    wasSet = true;
    return wasSet;
  }

  public int getRow()
  {
    return row;
  }

  public int getColumn()
  {
    return column;
  }

  public PlayerColor getPlayerColor()
  {
    return playerColor;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "row" + ":" + getRow()+ "," +
            "column" + ":" + getColumn()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "playerColor" + "=" + (getPlayerColor() != null ? !getPlayerColor().equals(this)  ? getPlayerColor().toString().replaceAll("  ","    ") : "this" : "null");
  }
}