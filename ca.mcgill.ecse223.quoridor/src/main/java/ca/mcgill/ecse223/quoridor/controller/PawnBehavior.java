/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.controller;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import java.util.*;
import ca.mcgill.ecse223.quoridor.model.*;

// line 3 "../../../../../PawnStateMachine.ump"
public class PawnBehavior
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PawnBehavior State Machines
  public enum PawnSM { Playing }
  public enum PawnSMPlaying { Null, Setup, InMiddle, AtNorthEdge, AtEastEdge, AtSouthEdge, AtWestEdge }
  private PawnSM pawnSM;
  private PawnSMPlaying pawnSMPlaying;

  //PawnBehavior Associations
  private Game currentGame;
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PawnBehavior()
  {
    setPawnSMPlaying(PawnSMPlaying.Null);
    setPawnSM(PawnSM.Playing);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getPawnSMFullName()
  {
    String answer = pawnSM.toString();
    if (pawnSMPlaying != PawnSMPlaying.Null) { answer += "." + pawnSMPlaying.toString(); }
    return answer;
  }

  public PawnSM getPawnSM()
  {
    return pawnSM;
  }

  public PawnSMPlaying getPawnSMPlaying()
  {
    return pawnSMPlaying;
  }

  public boolean startGame()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlaying aPawnSMPlaying = pawnSMPlaying;
    switch (aPawnSMPlaying)
    {
      case Setup:
        if (getCurrentPawnRow()==1)
        {
          exitPawnSMPlaying();
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnColumn()==9)
        {
          exitPawnSMPlaying();
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnRow()==9)
        {
          exitPawnSMPlaying();
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnColumn()<=8&&getCurrentPawnColumn()>=1&&getCurrentPawnRow()<=8&&getCurrentPawnRow()>=1)
        {
          exitPawnSMPlaying();
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveUp()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlaying aPawnSMPlaying = pawnSMPlaying;
    switch (aPawnSMPlaying)
    {
      case InMiddle:
        if (isLegalStep(MoveDirection.North)&&getCurrentPawnRow()==2)
        {
          exitPawnSMPlaying();
        // line 20 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.North)&&getCurrentPawnRow()>2)
        {
          exitPawnSMPlaying();
        // line 21 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtNorthEdge:
        exitPawnSMPlaying();
        // line 34 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
        wasEventProcessed = true;
        break;
      case AtEastEdge:
        if (isLegalStep(MoveDirection.North)&&getCurrentPawnRow()==2)
        {
          exitPawnSMPlaying();
        // line 50 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.North)&&getCurrentPawnRow()>2)
        {
          exitPawnSMPlaying();
        // line 51 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthEdge:
        if (isLegalStep(MoveDirection.North))
        {
          exitPawnSMPlaying();
        // line 60 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestEdge:
        if (isLegalStep(MoveDirection.North)&&getCurrentPawnRow()==2)
        {
          exitPawnSMPlaying();
        // line 74 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.North)&&getCurrentPawnRow()>2)
        {
          exitPawnSMPlaying();
        // line 75 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveRight()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlaying aPawnSMPlaying = pawnSMPlaying;
    switch (aPawnSMPlaying)
    {
      case InMiddle:
        if (isLegalStep(MoveDirection.East)&&getCurrentPawnColumn()==8)
        {
          exitPawnSMPlaying();
        // line 23 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.East);
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.East)&&getCurrentPawnColumn()<8)
        {
          exitPawnSMPlaying();
        // line 24 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.East);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtNorthEdge:
        if (isLegalStep(MoveDirection.East)&&getCurrentPawnColumn()==8)
        {
          exitPawnSMPlaying();
        // line 36 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.East);
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.East)&&getCurrentPawnColumn()<8)
        {
          exitPawnSMPlaying();
        // line 37 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.East);
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtEastEdge:
        exitPawnSMPlaying();
        // line 46 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
        wasEventProcessed = true;
        break;
      case AtSouthEdge:
        if (isLegalStep(MoveDirection.East)&&getCurrentPawnColumn()==8)
        {
          exitPawnSMPlaying();
        // line 62 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.East);
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.East)&&getCurrentPawnColumn()<8)
        {
          exitPawnSMPlaying();
        // line 63 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.East);
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestEdge:
        if (isLegalStep(MoveDirection.East))
        {
          exitPawnSMPlaying();
        // line 72 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.East);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveDown()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlaying aPawnSMPlaying = pawnSMPlaying;
    switch (aPawnSMPlaying)
    {
      case InMiddle:
        if (isLegalStep(MoveDirection.South)&&getCurrentPawnRow()==8)
        {
          exitPawnSMPlaying();
        // line 26 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.South)&&getCurrentPawnRow()<8)
        {
          exitPawnSMPlaying();
        // line 27 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtNorthEdge:
        if (isLegalStep(MoveDirection.South))
        {
          exitPawnSMPlaying();
        // line 39 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtEastEdge:
        if (isLegalStep(MoveDirection.South)&&getCurrentPawnRow()==8)
        {
          exitPawnSMPlaying();
        // line 53 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.South)&&getCurrentPawnRow()<8)
        {
          exitPawnSMPlaying();
        // line 54 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthEdge:
        exitPawnSMPlaying();
        // line 58 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
        wasEventProcessed = true;
        break;
      case AtWestEdge:
        if (isLegalStep(MoveDirection.South)&&getCurrentPawnRow()==8)
        {
          exitPawnSMPlaying();
        // line 77 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.South)&&getCurrentPawnRow()<8)
        {
          exitPawnSMPlaying();
        // line 78 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveLeft()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlaying aPawnSMPlaying = pawnSMPlaying;
    switch (aPawnSMPlaying)
    {
      case InMiddle:
        if (isLegalStep(MoveDirection.West)&&getCurrentPawnColumn()==2)
        {
          exitPawnSMPlaying();
        // line 29 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.West);
          setPawnSMPlaying(PawnSMPlaying.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.West)&&getCurrentPawnColumn()>2)
        {
          exitPawnSMPlaying();
        // line 30 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.West);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtNorthEdge:
        if (isLegalStep(MoveDirection.West)&&getCurrentPawnColumn()==2)
        {
          exitPawnSMPlaying();
        // line 41 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.West);
          setPawnSMPlaying(PawnSMPlaying.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.West)&&getCurrentPawnColumn()>2)
        {
          exitPawnSMPlaying();
        // line 42 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.West);
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtEastEdge:
        if (isLegalStep(MoveDirection.West))
        {
          exitPawnSMPlaying();
        // line 48 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.West);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthEdge:
        if (isLegalStep(MoveDirection.West)&&getCurrentPawnColumn()==2)
        {
          exitPawnSMPlaying();
        // line 65 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.West);
          setPawnSMPlaying(PawnSMPlaying.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.West)&&getCurrentPawnColumn()>2)
        {
          exitPawnSMPlaying();
        // line 66 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.West);
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestEdge:
        exitPawnSMPlaying();
        // line 70 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlaying(PawnSMPlaying.AtWestEdge);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void exitPawnSM()
  {
    switch(pawnSM)
    {
      case Playing:
        exitPawnSMPlaying();
        break;
    }
  }

  private void setPawnSM(PawnSM aPawnSM)
  {
    pawnSM = aPawnSM;

    // entry actions and do activities
    switch(pawnSM)
    {
      case Playing:
        if (pawnSMPlaying == PawnSMPlaying.Null) { setPawnSMPlaying(PawnSMPlaying.Setup); }
        break;
    }
  }

  private void exitPawnSMPlaying()
  {
    switch(pawnSMPlaying)
    {
      case Setup:
        setPawnSMPlaying(PawnSMPlaying.Null);
        break;
      case InMiddle:
        setPawnSMPlaying(PawnSMPlaying.Null);
        break;
      case AtNorthEdge:
        setPawnSMPlaying(PawnSMPlaying.Null);
        break;
      case AtEastEdge:
        setPawnSMPlaying(PawnSMPlaying.Null);
        break;
      case AtSouthEdge:
        setPawnSMPlaying(PawnSMPlaying.Null);
        break;
      case AtWestEdge:
        setPawnSMPlaying(PawnSMPlaying.Null);
        break;
    }
  }

  private void setPawnSMPlaying(PawnSMPlaying aPawnSMPlaying)
  {
    pawnSMPlaying = aPawnSMPlaying;
    if (pawnSM != PawnSM.Playing && aPawnSMPlaying != PawnSMPlaying.Null) { setPawnSM(PawnSM.Playing); }
  }
  /* Code from template association_GetOne */
  public Game getCurrentGame()
  {
    return currentGame;
  }

  public boolean hasCurrentGame()
  {
    boolean has = currentGame != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }

  public boolean hasPlayer()
  {
    boolean has = player != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCurrentGame(Game aNewCurrentGame)
  {
    boolean wasSet = false;
    currentGame = aNewCurrentGame;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setPlayer(Player aNewPlayer)
  {
    boolean wasSet = false;
    player = aNewPlayer;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    currentGame = null;
    player = null;
  }


  /**
   * Returns the current row number of the pawn
   */
  // line 85 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnRow(){
    if (player.hasGameAsWhite()) {
			return currentGame.getCurrentPosition().getWhitePosition().getTile().getRow();
		} else {
			return currentGame.getCurrentPosition().getBlackPosition().getTile().getRow();
		}
  }


  /**
   * Returns the current column number of the pawn
   */
  // line 93 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnColumn(){
    if (player.hasGameAsWhite()) {
			return currentGame.getCurrentPosition().getWhitePosition().getTile().getColumn();
		} else {
			return currentGame.getCurrentPosition().getBlackPosition().getTile().getColumn();
		}
  }


  /**
   * Returns if it is legal to step in the given direction
   */
  // line 101 "../../../../../PawnStateMachine.ump"
  public boolean isLegalStep(MoveDirection dir){
    int row = getCurrentPawnRow();
    	int col = getCurrentPawnColumn();
    	int newRow = row; // initial values to be updated
    	int newCol = col;
    	// Check direction
    	if (dir == MoveDirection.East ){
    		newRow = row;
    		newCol = col +1;
    	} else if (dir == MoveDirection.West){
			newRow = row;
			newCol = col -1;
		} else if (dir == MoveDirection.South){
			newRow = row + 1;
			newCol = col;
		} else if (dir == MoveDirection.North){
			newRow = row - 1;
			newCol = col;
		}
    	BoardGraph bg = new BoardGraph();
		bg.syncWallEdges();
    	return bg.isAdjacent(row,col,newRow,newCol);
  }


  /**
   * Returns if it is legal to jump in the given direction
   */
  // line 124 "../../../../../PawnStateMachine.ump"
  public boolean isLegalJump(MoveDirection dir){
    return false;
  }


  /**
   * Action to be called when an illegal move is attempted
   */
  // line 127 "../../../../../PawnStateMachine.ump"
  public void illegalMove(){
    //throw new InvalidMoveException("");
  }

  // line 130 "../../../../../PawnStateMachine.ump"
  public void movePawn(MoveDirection dir){
    GamePosition gp = currentGame.getCurrentPosition();
 	 	
 	 	if (player.hasGameAsWhite()) {
			PlayerPosition currentPosition = gp.getWhitePosition();
			Tile currentTile = currentPosition.getTile();
			Tile newTile = getTargetTile(currentTile, dir);
			PlayerPosition newPos = new PlayerPosition(player, newTile);
			gp.setWhitePosition(newPos);
		} else {		
			PlayerPosition currentPosition = gp.getBlackPosition();
			Tile currentTile = currentPosition.getTile();
			Tile newTile = getTargetTile(currentTile, dir);
			PlayerPosition newPos = new PlayerPosition(player, newTile);
			gp.setBlackPosition(newPos);	
		}
  }

  // line 147 "../../../../../PawnStateMachine.ump"
  public Tile getTile(int row, int col){
    Iterator<Tile> itr = currentGame.getQuoridor().getBoard().getTiles().iterator();
		while (itr.hasNext()) {
			Tile t = itr.next();
			if (t.getRow() == row && t.getColumn() == col) {
				return t;
			}
		}
		return null;
  }

  // line 157 "../../../../../PawnStateMachine.ump"
  public Tile getTargetTile(Tile currentTile, MoveDirection dir){
    if (dir == MoveDirection.East){
			int newRow = currentTile.getRow();
			int newCol = currentTile.getColumn() +1;
			return getTile(newRow, newCol);
		} else if (dir == MoveDirection.West){
			int newRow = currentTile.getRow();
			int newCol = currentTile.getColumn() -1;
			return getTile(newRow, newCol);
		} else if (dir == MoveDirection.South){
			int newRow = currentTile.getRow() + 1;
			int newCol = currentTile.getColumn();
			return getTile(newRow, newCol);
		} else if (dir == MoveDirection.North){
			int newRow = currentTile.getRow() - 1;
			int newCol = currentTile.getColumn();
			return getTile(newRow, newCol);
		}
		return null;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 178 "../../../../../PawnStateMachine.ump"
  enum MoveDirection 
  {
    East, South, West, North;
  }

  
}