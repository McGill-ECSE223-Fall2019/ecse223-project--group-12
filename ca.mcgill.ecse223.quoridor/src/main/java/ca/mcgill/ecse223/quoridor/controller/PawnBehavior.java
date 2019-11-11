/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.controller;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import java.util.*;
import ca.mcgill.ecse223.quoridor.model.*;

// line 5 "../../../../../PawnStateMachine.ump"
public class PawnBehavior
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PawnBehavior State Machines
  public enum PawnSM { Playing, Finished }
  public enum PawnSMPlaying { Null, Setup, InMiddle, NextToEastEdge, AtEastEdge }
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

  public boolean finishGame()
  {
    boolean wasEventProcessed = false;
    
    PawnSM aPawnSM = pawnSM;
    switch (aPawnSM)
    {
      case Playing:
        exitPawnSM();
        setPawnSM(PawnSM.Finished);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean startGame()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlaying aPawnSMPlaying = pawnSMPlaying;
    switch (aPawnSMPlaying)
    {
      case Setup:
        if (getCurrentPawnColumn()==9)
        {
          exitPawnSMPlaying();
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnColumn()==8)
        {
          exitPawnSMPlaying();
          setPawnSMPlaying(PawnSMPlaying.NextToEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnColumn()<8)
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

  public boolean moveRight()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlaying aPawnSMPlaying = pawnSMPlaying;
    switch (aPawnSMPlaying)
    {
      case InMiddle:
        if (isLegalStep(MoveDirection.East)&&getCurrentPawnColumn()==6)
        {
          exitPawnSMPlaying();
        // line 24 "../../../../../PawnStateMachine.ump"
          movePawnRight();
          setPawnSMPlaying(PawnSMPlaying.NextToEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.East)&&getCurrentPawnColumn()<7)
        {
          exitPawnSMPlaying();
        // line 25 "../../../../../PawnStateMachine.ump"
          movePawnRight();
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        break;
      case NextToEastEdge:
        if (isLegalStep(MoveDirection.East)&&getCurrentPawnColumn()==7)
        {
          exitPawnSMPlaying();
        // line 28 "../../../../../PawnStateMachine.ump"
          movePawnRight();
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtEastEdge:
        exitPawnSMPlaying();
        // line 31 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
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
      case NextToEastEdge:
        setPawnSMPlaying(PawnSMPlaying.Null);
        break;
      case AtEastEdge:
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
  // line 41 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnRow(){
    Player player  = currentGame.getCurrentPosition().getPlayerToMove();
		if (player.hasGameAsWhite()) {
			return currentGame.getCurrentPosition().getWhitePosition().getTile().getRow();
		} else {
			return currentGame.getCurrentPosition().getBlackPosition().getTile().getRow();
		}
  }


  /**
   * Returns the current column number of the pawn
   */
  // line 50 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnColumn(){
    Player player  = currentGame.getCurrentPosition().getPlayerToMove();
		if (player.hasGameAsWhite()) {
			return currentGame.getCurrentPosition().getWhitePosition().getTile().getColumn();
		} else {
			return currentGame.getCurrentPosition().getBlackPosition().getTile().getColumn();
		}
  }


  /**
   * Returns if it is legal to step in the given direction
   */
  // line 59 "../../../../../PawnStateMachine.ump"
  public boolean isLegalStep(MoveDirection dir){
    GamePosition gp = currentGame.getCurrentPosition();
    	int row = 0;
    	int col = 0;
    	int newRow = 0;
    	int newCol = 0;
    	if (player.hasGameAsWhite()) {
    		row =  gp.getWhitePosition().getTile().getRow();
    		col = gp.getWhitePosition().getTile().getColumn();
    	} else {
    		row =  gp.getBlackPosition().getTile().getRow();
    		col = gp.getBlackPosition().getTile().getColumn();
    	}
    	
    	// Check direction
    	if (dir == MoveDirection.East ){
    		newCol = col +1;
    		newRow = row;
    	} else {
    		return false; //TODO: Remaining Directions
    	}
    	BoardGraph bg = new BoardGraph();
		bg.syncEdges();
    	return bg.isAdjacent(row,col,newRow,newCol);
  }


  /**
   * Returns if it is legal to jump in the given direction
   */
  // line 84 "../../../../../PawnStateMachine.ump"
  public boolean isLegalJump(MoveDirection dir){
    return false;
  }


  /**
   * Action to be called when an illegal move is attempted
   */
  // line 88 "../../../../../PawnStateMachine.ump"
  public void illegalMove(){
    //throw new InvalidMoveException("");
  }

  // line 92 "../../../../../PawnStateMachine.ump"
  public void movePawnRight(){
    GamePosition gp = currentGame.getCurrentPosition();
 	 	if (player.hasGameAsWhite()) {
			PlayerPosition old = gp.getWhitePosition();
			Tile t = old.getTile();
			int newRow = t.getRow();
			int newCol = t.getColumn() +1;
			Tile newTile = getTile(newRow, newCol);
			PlayerPosition newPos = new PlayerPosition(player, newTile);
			gp.setWhitePosition(newPos);
		} else {		
			PlayerPosition old = gp.getBlackPosition();
			Tile t = old.getTile();
			int newRow = t.getRow();
			int newCol = t.getColumn() +1;
			Tile newTile = getTile(newRow, newCol);
			PlayerPosition newPos = new PlayerPosition(player, newTile);
			gp.setBlackPosition(newPos);	
		}
  }

  // line 113 "../../../../../PawnStateMachine.ump"
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
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 125 "../../../../../PawnStateMachine.ump"
  enum MoveDirection 
  {
    East, South, West, North;
  }

  
}