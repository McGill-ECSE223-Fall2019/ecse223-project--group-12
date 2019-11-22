/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.to;
import java.util.List;

// line 32 "../../../../../QuoridorTO.ump"
public class PathAndMove
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PathAndMove Attributes
  private List<TileTO> whitePath;
  private List<TileTO> blackPath;
  private WallMoveTO move;
  private boolean reducesOptions;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PathAndMove(List<TileTO> aWhitePath, List<TileTO> aBlackPath, WallMoveTO aMove, boolean aReducesOptions)
  {
    whitePath = aWhitePath;
    blackPath = aBlackPath;
    move = aMove;
    reducesOptions = aReducesOptions;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setWhitePath(List<TileTO> aWhitePath)
  {
    boolean wasSet = false;
    whitePath = aWhitePath;
    wasSet = true;
    return wasSet;
  }

  public boolean setBlackPath(List<TileTO> aBlackPath)
  {
    boolean wasSet = false;
    blackPath = aBlackPath;
    wasSet = true;
    return wasSet;
  }

  public boolean setMove(WallMoveTO aMove)
  {
    boolean wasSet = false;
    move = aMove;
    wasSet = true;
    return wasSet;
  }

  public boolean setReducesOptions(boolean aReducesOptions)
  {
    boolean wasSet = false;
    reducesOptions = aReducesOptions;
    wasSet = true;
    return wasSet;
  }

  public List<TileTO> getWhitePath()
  {
    return whitePath;
  }

  public List<TileTO> getBlackPath()
  {
    return blackPath;
  }

  public WallMoveTO getMove()
  {
    return move;
  }

  public boolean getReducesOptions()
  {
    return reducesOptions;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isReducesOptions()
  {
    return reducesOptions;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "reducesOptions" + ":" + getReducesOptions()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "whitePath" + "=" + (getWhitePath() != null ? !getWhitePath().equals(this)  ? getWhitePath().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "blackPath" + "=" + (getBlackPath() != null ? !getBlackPath().equals(this)  ? getBlackPath().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "move" + "=" + (getMove() != null ? !getMove().equals(this)  ? getMove().toString().replaceAll("  ","    ") : "this" : "null");
  }
}