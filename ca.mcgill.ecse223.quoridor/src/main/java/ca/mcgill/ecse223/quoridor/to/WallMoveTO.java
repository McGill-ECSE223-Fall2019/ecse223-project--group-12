/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.to;
import ca.mcgill.ecse223.quoridor.model.Direction;

// line 6 "../../../../../QuoridorTO.ump"
public class WallMoveTO
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WallMoveTO Attributes
  private int row;
  private int column;
  private Direction direction;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WallMoveTO(int aRow, int aColumn, Direction aDirection)
  {
    row = aRow;
    column = aColumn;
    direction = aDirection;
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

  public boolean setDirection(Direction aDirection)
  {
    boolean wasSet = false;
    direction = aDirection;
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

  public Direction getDirection()
  {
    return direction;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "row" + ":" + getRow()+ "," +
            "column" + ":" + getColumn()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "direction" + "=" + (getDirection() != null ? !getDirection().equals(this)  ? getDirection().toString().replaceAll("  ","    ") : "this" : "null");
  }
}