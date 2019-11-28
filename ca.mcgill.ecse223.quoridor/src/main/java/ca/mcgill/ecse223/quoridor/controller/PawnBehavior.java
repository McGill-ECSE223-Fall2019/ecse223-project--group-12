/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.controller;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import java.util.*;
import ca.mcgill.ecse223.quoridor.model.*;

// line 3 "../../../../../PawnStateMachine.ump"
public class PawnBehavior
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PawnBehavior State Machines
  public enum PawnSM { Playing, GameOver }
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

  public boolean endGame()
  {
    boolean wasEventProcessed = false;
    
    PawnSM aPawnSM = pawnSM;
    switch (aPawnSM)
    {
      case Playing:
        exitPawnSM();
        setPawnSM(PawnSM.GameOver);
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
        if (getCurrentPawnRow()==1&&getCurrentGame().getGameStatus().equals(GameStatus.Running))
        {
          exitPawnSMPlaying();
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnColumn()==9&&getCurrentGame().getGameStatus().equals(GameStatus.Running)&&getCurrentPawnRow()!=1&&getCurrentPawnRow()!=9)
        {
          exitPawnSMPlaying();
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnRow()==9&&getCurrentGame().getGameStatus().equals(GameStatus.Running))
        {
          exitPawnSMPlaying();
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnColumn()==1&&getCurrentGame().getGameStatus().equals(GameStatus.Running)&&getCurrentPawnRow()!=1&&getCurrentPawnRow()!=9)
        {
          exitPawnSMPlaying();
          setPawnSMPlaying(PawnSMPlaying.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentPawnColumn()<=8&&getCurrentPawnColumn()>=2&&getCurrentPawnRow()<=8&&getCurrentPawnRow()>=2&&getCurrentGame().getGameStatus().equals(GameStatus.Running))
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
        if (isLegalStep(MoveDirection.North)&&getCurrentPawnRow()<=2)
        {
          exitPawnSMPlaying();
        // line 25 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.North)&&getCurrentPawnRow()>2)
        {
          exitPawnSMPlaying();
        // line 26 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.North))&&isLegalJump(MoveDirection.North)&&getCurrentPawnRow()>3)
        {
          exitPawnSMPlaying();
        // line 27 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.North))&&isLegalJump(MoveDirection.North)&&getCurrentPawnRow()<=3)
        {
          exitPawnSMPlaying();
        // line 28 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtNorthEdge:
        exitPawnSMPlaying();
        // line 75 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
        wasEventProcessed = true;
        break;
      case AtEastEdge:
        if (isLegalStep(MoveDirection.North)&&getCurrentPawnRow()<=2)
        {
          exitPawnSMPlaying();
        // line 111 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.North)&&getCurrentPawnRow()>2)
        {
          exitPawnSMPlaying();
        // line 112 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.North))&&isLegalJump(MoveDirection.North)&&getCurrentPawnRow()>3)
        {
          exitPawnSMPlaying();
        // line 113 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.North))&&isLegalJump(MoveDirection.North)&&getCurrentPawnRow()<=2)
        {
          exitPawnSMPlaying();
        // line 114 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthEdge:
        if (isLegalStep(MoveDirection.North))
        {
          exitPawnSMPlaying();
        // line 139 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.North))&&isLegalJump(MoveDirection.North))
        {
          exitPawnSMPlaying();
        // line 140 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestEdge:
        if (isLegalStep(MoveDirection.North)&&getCurrentPawnRow()<=2)
        {
          exitPawnSMPlaying();
        // line 169 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.North)&&getCurrentPawnRow()>2)
        {
          exitPawnSMPlaying();
        // line 170 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.North))&&isLegalJump(MoveDirection.North)&&getCurrentPawnRow()<=3)
        {
          exitPawnSMPlaying();
        // line 171 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.North);
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.North))&&isLegalJump(MoveDirection.North)&&getCurrentPawnRow()>3)
        {
          exitPawnSMPlaying();
        // line 172 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.North);
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
        if (isLegalStep(MoveDirection.East)&&getCurrentPawnColumn()>=8)
        {
          exitPawnSMPlaying();
        // line 32 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.East);
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.East)&&getCurrentPawnColumn()<8)
        {
          exitPawnSMPlaying();
        // line 33 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.East);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.East))&&isLegalJump(MoveDirection.East)&&getCurrentPawnColumn()<7)
        {
          exitPawnSMPlaying();
        // line 34 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.East);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.East))&&isLegalJump(MoveDirection.East)&&getCurrentPawnColumn()>=7)
        {
          exitPawnSMPlaying();
        // line 35 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.East);
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtNorthEdge:
        if (isLegalStep(MoveDirection.East)&&getCurrentPawnColumn()>=8)
        {
          exitPawnSMPlaying();
        // line 84 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.East);
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.East)&&getCurrentPawnColumn()<8)
        {
          exitPawnSMPlaying();
        // line 85 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.East);
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.East))&&isLegalJump(MoveDirection.East))
        {
          exitPawnSMPlaying();
        // line 86 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.East);
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtEastEdge:
        exitPawnSMPlaying();
        // line 102 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
        wasEventProcessed = true;
        break;
      case AtSouthEdge:
        if (isLegalStep(MoveDirection.East)&&getCurrentPawnColumn()>=8)
        {
          exitPawnSMPlaying();
        // line 143 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.East);
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.East)&&getCurrentPawnColumn()<8)
        {
          exitPawnSMPlaying();
        // line 144 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.East);
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.East))&&isLegalJump(MoveDirection.East))
        {
          exitPawnSMPlaying();
        // line 145 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.East);
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestEdge:
        if (isLegalStep(MoveDirection.East))
        {
          exitPawnSMPlaying();
        // line 166 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.East);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.East))&&isLegalJump(MoveDirection.East))
        {
          exitPawnSMPlaying();
        // line 167 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.East);
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
        if (isLegalStep(MoveDirection.South)&&getCurrentPawnRow()>=8)
        {
          exitPawnSMPlaying();
        // line 38 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.South)&&getCurrentPawnRow()<8)
        {
          exitPawnSMPlaying();
        // line 39 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.South))&&isLegalJump(MoveDirection.South)&&getCurrentPawnRow()<7)
        {
          exitPawnSMPlaying();
        // line 40 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.South))&&isLegalJump(MoveDirection.South)&&getCurrentPawnRow()>=7)
        {
          exitPawnSMPlaying();
        // line 41 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtNorthEdge:
        if (isLegalStep(MoveDirection.South))
        {
          exitPawnSMPlaying();
        // line 80 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.South))&&isLegalJump(MoveDirection.South))
        {
          exitPawnSMPlaying();
        // line 81 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtEastEdge:
        if (isLegalStep(MoveDirection.South)&&getCurrentPawnRow()>=8)
        {
          exitPawnSMPlaying();
        // line 116 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.South)&&getCurrentPawnRow()<8)
        {
          exitPawnSMPlaying();
        // line 117 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.South))&&isLegalJump(MoveDirection.South)&&getCurrentPawnRow()>=7)
        {
          exitPawnSMPlaying();
        // line 118 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.South))&&isLegalJump(MoveDirection.South)&&getCurrentPawnRow()<7)
        {
          exitPawnSMPlaying();
        // line 119 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthEdge:
        exitPawnSMPlaying();
        // line 134 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
        wasEventProcessed = true;
        break;
      case AtWestEdge:
        if (isLegalStep(MoveDirection.South)&&getCurrentPawnRow()>=8)
        {
          exitPawnSMPlaying();
        // line 174 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.South)&&getCurrentPawnRow()<8)
        {
          exitPawnSMPlaying();
        // line 175 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.South))&&isLegalJump(MoveDirection.South)&&getCurrentPawnRow()>=7)
        {
          exitPawnSMPlaying();
        // line 176 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.South);
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.South))&&isLegalJump(MoveDirection.South)&&getCurrentPawnRow()<8)
        {
          exitPawnSMPlaying();
        // line 177 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.South);
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
        if (isLegalStep(MoveDirection.West)&&getCurrentPawnColumn()<=2)
        {
          exitPawnSMPlaying();
        // line 44 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.West);
          setPawnSMPlaying(PawnSMPlaying.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.West)&&getCurrentPawnColumn()>2)
        {
          exitPawnSMPlaying();
        // line 45 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.West);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.West))&&isLegalJump(MoveDirection.West)&&getCurrentPawnColumn()>3)
        {
          exitPawnSMPlaying();
        // line 46 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.West);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.West))&&isLegalJump(MoveDirection.West)&&getCurrentPawnColumn()<=3)
        {
          exitPawnSMPlaying();
        // line 47 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.West);
          setPawnSMPlaying(PawnSMPlaying.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtNorthEdge:
        if (isLegalStep(MoveDirection.West)&&getCurrentPawnColumn()<=2)
        {
          exitPawnSMPlaying();
        // line 88 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.West);
          setPawnSMPlaying(PawnSMPlaying.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.West)&&getCurrentPawnColumn()>2)
        {
          exitPawnSMPlaying();
        // line 89 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.West);
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.West))&&isLegalJump(MoveDirection.West))
        {
          exitPawnSMPlaying();
        // line 90 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.West);
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtEastEdge:
        if (isLegalStep(MoveDirection.West))
        {
          exitPawnSMPlaying();
        // line 107 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.West);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.West))&&isLegalJump(MoveDirection.West))
        {
          exitPawnSMPlaying();
        // line 108 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.West);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthEdge:
        if (isLegalStep(MoveDirection.West)&&getCurrentPawnColumn()<=2)
        {
          exitPawnSMPlaying();
        // line 147 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.West);
          setPawnSMPlaying(PawnSMPlaying.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(MoveDirection.West)&&getCurrentPawnColumn()>2)
        {
          exitPawnSMPlaying();
        // line 148 "../../../../../PawnStateMachine.ump"
          movePawn(MoveDirection.West);
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(MoveDirection.West))&&isLegalJump(MoveDirection.West))
        {
          exitPawnSMPlaying();
        // line 149 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.West);
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestEdge:
        exitPawnSMPlaying();
        // line 161 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlaying(PawnSMPlaying.AtWestEdge);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveDownLeft()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlaying aPawnSMPlaying = pawnSMPlaying;
    switch (aPawnSMPlaying)
    {
      case InMiddle:
        if (isLegalJump(MoveDirection.SouthWest)&&getCurrentPawnRow()<8&&getCurrentPawnColumn()>2)
        {
          exitPawnSMPlaying();
        // line 50 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.SouthWest);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.SouthWest)&&getCurrentPawnRow()>=8)
        {
          exitPawnSMPlaying();
        // line 51 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.SouthWest);
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.SouthWest)&&getCurrentPawnColumn()<=2&&getCurrentPawnRow()!=8)
        {
          exitPawnSMPlaying();
        // line 52 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.SouthWest);
          setPawnSMPlaying(PawnSMPlaying.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtNorthEdge:
        if (isLegalJump(MoveDirection.SouthWest)&&getCurrentPawnColumn()<=2)
        {
          exitPawnSMPlaying();
        // line 93 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.SouthWest);
          setPawnSMPlaying(PawnSMPlaying.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.SouthWest)&&getCurrentPawnColumn()>2)
        {
          exitPawnSMPlaying();
        // line 94 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.SouthWest);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtEastEdge:
        if (isLegalJump(MoveDirection.SouthWest)&&getCurrentPawnRow()>=8)
        {
          exitPawnSMPlaying();
        // line 122 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.SouthWest);
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.SouthWest)&&getCurrentPawnRow()<8)
        {
          exitPawnSMPlaying();
        // line 123 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.SouthWest);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthEdge:
        exitPawnSMPlaying();
        // line 135 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
        wasEventProcessed = true;
        break;
      case AtWestEdge:
        exitPawnSMPlaying();
        // line 163 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveDownRight()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlaying aPawnSMPlaying = pawnSMPlaying;
    switch (aPawnSMPlaying)
    {
      case InMiddle:
        if (isLegalJump(MoveDirection.SouthEast)&&getCurrentPawnRow()<8&&getCurrentPawnColumn()<8)
        {
          exitPawnSMPlaying();
        // line 55 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.SouthEast);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.SouthEast)&&getCurrentPawnRow()>=8)
        {
          exitPawnSMPlaying();
        // line 56 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.SouthEast);
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.SouthEast)&&getCurrentPawnColumn()>=8&&getCurrentPawnRow()!=8)
        {
          exitPawnSMPlaying();
        // line 57 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.SouthEast);
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtNorthEdge:
        if (isLegalJump(MoveDirection.SouthEast)&&getCurrentPawnColumn()>=8)
        {
          exitPawnSMPlaying();
        // line 96 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.SouthEast);
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.SouthEast)&&getCurrentPawnColumn()<8)
        {
          exitPawnSMPlaying();
        // line 97 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.SouthEast);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtEastEdge:
        exitPawnSMPlaying();
        // line 103 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
        wasEventProcessed = true;
        break;
      case AtSouthEdge:
        exitPawnSMPlaying();
        // line 136 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
        wasEventProcessed = true;
        break;
      case AtWestEdge:
        if (isLegalJump(MoveDirection.SouthEast)&&getCurrentPawnRow()>=8)
        {
          exitPawnSMPlaying();
        // line 183 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.SouthEast);
          setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.SouthEast)&&getCurrentPawnRow()<8)
        {
          exitPawnSMPlaying();
        // line 184 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.SouthEast);
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

  public boolean moveUpRight()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlaying aPawnSMPlaying = pawnSMPlaying;
    switch (aPawnSMPlaying)
    {
      case InMiddle:
        if (isLegalJump(MoveDirection.NorthEast)&&getCurrentPawnRow()>2&&getCurrentPawnColumn()<8)
        {
          exitPawnSMPlaying();
        // line 60 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.NorthEast);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.NorthEast)&&getCurrentPawnRow()<=2)
        {
          exitPawnSMPlaying();
        // line 61 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.NorthEast);
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.NorthEast)&&getCurrentPawnColumn()>=8&&getCurrentPawnRow()!=2)
        {
          exitPawnSMPlaying();
        // line 62 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.NorthEast);
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtNorthEdge:
        exitPawnSMPlaying();
        // line 76 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
        wasEventProcessed = true;
        break;
      case AtEastEdge:
        exitPawnSMPlaying();
        // line 104 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
        wasEventProcessed = true;
        break;
      case AtSouthEdge:
        if (isLegalJump(MoveDirection.NorthEast)&&getCurrentPawnColumn()>=8)
        {
          exitPawnSMPlaying();
        // line 155 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.NorthEast);
          setPawnSMPlaying(PawnSMPlaying.AtEastEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.NorthEast)&&getCurrentPawnColumn()<8)
        {
          exitPawnSMPlaying();
        // line 156 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.NorthEast);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestEdge:
        if (isLegalJump(MoveDirection.NorthEast)&&getCurrentPawnRow()<=2)
        {
          exitPawnSMPlaying();
        // line 180 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.NorthEast);
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.NorthEast)&&getCurrentPawnRow()>2)
        {
          exitPawnSMPlaying();
        // line 181 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.NorthEast);
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

  public boolean moveUpLeft()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlaying aPawnSMPlaying = pawnSMPlaying;
    switch (aPawnSMPlaying)
    {
      case InMiddle:
        if (isLegalJump(MoveDirection.NorthWest)&&getCurrentPawnRow()>2&&getCurrentPawnColumn()>2)
        {
          exitPawnSMPlaying();
        // line 65 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.NorthWest);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.NorthWest)&&getCurrentPawnRow()<=2)
        {
          exitPawnSMPlaying();
        // line 66 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.NorthWest);
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.NorthWest)&&getCurrentPawnColumn()<=2&&getCurrentPawnRow()!=2)
        {
          exitPawnSMPlaying();
        // line 67 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.NorthWest);
          setPawnSMPlaying(PawnSMPlaying.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtNorthEdge:
        exitPawnSMPlaying();
        // line 77 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
        wasEventProcessed = true;
        break;
      case AtEastEdge:
        if (isLegalJump(MoveDirection.NorthWest)&&getCurrentPawnRow()<=2)
        {
          exitPawnSMPlaying();
        // line 125 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.NorthWest);
          setPawnSMPlaying(PawnSMPlaying.AtNorthEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.NorthWest)&&getCurrentPawnRow()>2)
        {
          exitPawnSMPlaying();
        // line 126 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.NorthWest);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtSouthEdge:
        if (isLegalJump(MoveDirection.NorthWest)&&getCurrentPawnColumn()<=2)
        {
          exitPawnSMPlaying();
        // line 152 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.NorthWest);
          setPawnSMPlaying(PawnSMPlaying.AtWestEdge);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.NorthWest)&&getCurrentPawnColumn()>2)
        {
          exitPawnSMPlaying();
        // line 153 "../../../../../PawnStateMachine.ump"
          jumpPawn(MoveDirection.NorthWest);
          setPawnSMPlaying(PawnSMPlaying.InMiddle);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtWestEdge:
        exitPawnSMPlaying();
        // line 162 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMPlaying(PawnSMPlaying.AtSouthEdge);
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
      case GameOver:
        // line 188 "../../../../../PawnStateMachine.ump"
        setWinner();
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

    // entry actions and do activities
    switch(pawnSMPlaying)
    {
      case AtNorthEdge:
        // line 72 "../../../../../PawnStateMachine.ump"
        checkWinningMove();
        break;
      case AtSouthEdge:
        // line 131 "../../../../../PawnStateMachine.ump"
        checkWinningMove();
        break;
    }
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
   * 
   * 
   * Changes the game status of the game when a player is in a winning position (Will be modified in future deliverables)
   * 
   * @author Remi Carriere
   * 
   * 
   */
  // line 201 "../../../../../PawnStateMachine.ump"
  public void checkWinningMove(){
    if (player.hasGameAsWhite() && currentGame.getCurrentPosition().getWhitePosition().getTile().getRow() == 1) {
			currentGame.setGameStatus(GameStatus.WhiteWon);
		} else  if (player.hasGameAsBlack() && currentGame.getCurrentPosition().getBlackPosition().getTile().getRow() == 9) {
			currentGame.setGameStatus(GameStatus.BlackWon);
		}
  }


  /**
   * 
   * 
   * Verifies if white won the game
   * 
   * @author Remi Carriere
   * 
   * @return
   * True if white won, false otherwise
   * 
   */
  // line 218 "../../../../../PawnStateMachine.ump"
  public boolean isWhiteWinningMove(){
    System.out.println("white");
		if (player.hasGameAsWhite()) {
		System.out.println("white");
			return true;
		} 
		return false;
  }


  /**
   * 
   * 
   * Gets the row of the player
   * 
   * @author Remi Carriere
   * 
   * @return
   * The row of the player
   */
  // line 236 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnRow(){
    if (player.hasGameAsWhite()) {
			return currentGame.getCurrentPosition().getWhitePosition().getTile().getRow();
		} else {
			return currentGame.getCurrentPosition().getBlackPosition().getTile().getRow();
		}
  }


  /**
   * 
   * 
   * Gets the column of the player
   * 
   * @author Remi Carriere
   * 
   * @return
   * The column of the player
   */
  // line 253 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnColumn(){
    if (player.hasGameAsWhite()) {
			return currentGame.getCurrentPosition().getWhitePosition().getTile().getColumn();
		} else {
			return currentGame.getCurrentPosition().getBlackPosition().getTile().getColumn();
		}
  }


  /**
   * 
   * 
   * Verifies if black won the game
   * 
   * @author Remi Carriere
   * 
   * @return
   * True if black won, false otherwise
   * 
   */
  // line 271 "../../../../../PawnStateMachine.ump"
  public boolean isBlackWinningMove(){
    if (player.hasGameAsBlack()) {
		System.out.println("black");
			return true;
		} 
		return false;
  }


  /**
   * 
   * 
   * Verifies if a step move in the given direction is legal
   * 
   * @author Remi Carriere
   * 
   * @param dir
   * Direction to move
   * 
   * @return
   * True if the move is legal, false otherwise
   * 
   */
  // line 292 "../../../../../PawnStateMachine.ump"
  public boolean isLegalStep(MoveDirection dir){
    int row = getCurrentPawnRow();
    	int col = getCurrentPawnColumn();
    	int[] tileIndex = getStepTargetTileIndex(dir);
 	  	int newRow = tileIndex[0];
 	  	int newCol =  tileIndex[1];
    	BoardGraph bg = new BoardGraph();
		bg.syncWallEdges();
		bg.syncStepMoves();
		if(bg.isAdjacent(row,col,newRow,newCol)){
			return true;
		}else{
			return false;
		}
  }


  /**
   * 
   * 
   * Verifies if a jump move in the given direction is legal
   * 
   * @author Remi Carriere
   * 
   * @param dir
   * Direction to move
   * 
   * @return
   * True if the move is legal, false otherwise
   * 
   */
  // line 322 "../../../../../PawnStateMachine.ump"
  public boolean isLegalJump(MoveDirection dir){
    int row = getCurrentPawnRow();
    	int col = getCurrentPawnColumn();
    	int[] tileIndex = getJumpTargetTileIndex(dir);
 	  	int newRow = tileIndex[0];
 	  	int newCol =  tileIndex[1];
		
    	BoardGraph bg = new BoardGraph();
		bg.syncJumpMoves();
		if(bg.isAdjacent(row,col,newRow,newCol)){
			return true;
		}else{
			return false;
		}
  }


  /**
   * 
   * 
   * Performs a jump move for the current player in specified direction
   * 
   * @author Remi Carriere
   * 
   * @param dir
   * Direction to move
   * 
   */
  // line 348 "../../../../../PawnStateMachine.ump"
  public void jumpPawn(MoveDirection dir){
    GamePosition gp = currentGame.getCurrentPosition();
 	  	
 	  	//Get the target tile
 	  	int[] tileIndex = getJumpTargetTileIndex(dir);
 	  	int newRow = tileIndex[0];
 	  	int newCol =  tileIndex[1];
		Tile targetTile = QuoridorController.getTile(newRow, newCol);
		
		// Update position of player
 	 	if (player.hasGameAsWhite()) {
			PlayerPosition newPos = new PlayerPosition(player, targetTile);
			gp.setWhitePosition(newPos);
		} else {		
			PlayerPosition newPos = new PlayerPosition(player, targetTile);
			gp.setBlackPosition(newPos);	
		}
		
		// Add the move to the game
		JumpMove move = new JumpMove(0, 0, player, targetTile, currentGame);
		QuoridorController.addMoveToGameHistory(move);
		// Confirm the move
		QuoridorController.confirmMove();
  }


  /**
   * 
   * 
   * Performs a step move for the current player in specified direction
   * 
   * @author Remi Carriere
   * 
   * @param dir
   * Direction to move
   * 
   */
  // line 383 "../../../../../PawnStateMachine.ump"
  public void movePawn(MoveDirection dir){
    GamePosition gp = currentGame.getCurrentPosition();
 	 	//Get the target tile
 	  	int[] tileIndex = getStepTargetTileIndex(dir);
 	  	int newRow = tileIndex[0];
 	  	int newCol =  tileIndex[1];
		Tile targetTile = QuoridorController.getTile(newRow, newCol);
 	 	// Update the player position
 	 	if (player.hasGameAsWhite()) {
			PlayerPosition newPos = new PlayerPosition(player, targetTile);
			gp.setWhitePosition(newPos);;
		} else {		
			PlayerPosition newPos = new PlayerPosition(player, targetTile);
			gp.setBlackPosition(newPos);	
		}
		// Add the move to the game
		StepMove move = new StepMove(0, 0, player, targetTile, currentGame);
		QuoridorController.addMoveToGameHistory(move);
		// Confirm the move
		QuoridorController.confirmMove();
  }


  /**
   * 
   * 
   * Gets the target tile row and col for jump moves based on move direction
   * 
   * @author Remi Carriere
   * @param dir
   * Direction to move
   * 
   * @return
   * an array containing [targetRow, targetCol]
   * 
   */
  // line 417 "../../../../../PawnStateMachine.ump"
  public int[] getJumpTargetTileIndex(MoveDirection dir){
    int row = getCurrentPawnRow();
    	int col = getCurrentPawnColumn();
    	int newRow = row; // initial values to be updated
    	int newCol = col;
    	// Check direction
    	if (dir == MoveDirection.East ){
    		newRow = row;
    		newCol = col + 2;
    	} else if (dir == MoveDirection.West){
			newRow = row;
			newCol = col - 2;
		} else if (dir == MoveDirection.South){
			newRow = row + 2;
			newCol = col;
		} else if (dir == MoveDirection.North){
			newRow = row - 2;
			newCol = col;
		} else if (dir == MoveDirection.SouthWest){
			newRow = row + 1;
			newCol = col - 1;
		}else if (dir == MoveDirection.SouthEast){
			newRow = row + 1;
			newCol = col + 1;
		}
		else if (dir == MoveDirection.NorthWest){
			newRow = row - 1;
			newCol = col - 1;
		}else if (dir == MoveDirection.NorthEast){
			newRow = row - 1;
			newCol = col + 1;
		}
		int[] tileIndex = {newRow, newCol};
		return tileIndex;
  }


  /**
   * 
   * 
   * Gets the target tile row and cole for step moves based on move direction
   * 
   * @author Remi Carriere
   * @param dir
   * Direction to move
   * 
   * @return
   * an array containing [targetRow, targetCol]
   * 
   */
  // line 465 "../../../../../PawnStateMachine.ump"
  public int[] getStepTargetTileIndex(MoveDirection dir){
    int row = getCurrentPawnRow();
    	int col = getCurrentPawnColumn();
    	int newRow = row; // initial values to be updated
    	int newCol = col;
    	// Check direction
    	if (dir == MoveDirection.East ){
    		newRow = row;
    		newCol = col + 1;
    	} else if (dir == MoveDirection.West){
			newRow = row;
			newCol = col - 1;
		} else if (dir == MoveDirection.South){
			newRow = row + 1;
			newCol = col;
		} else if (dir == MoveDirection.North){
			newRow = row - 1;
			newCol = col;
		}
		int[] tileIndex = {newRow, newCol};
		return tileIndex;
  }


  /**
   * 
   * 
   * For future deliverable
   * 
   */
  // line 493 "../../../../../PawnStateMachine.ump"
  public void setWinner(){
    
  }


  /**
   * 
   * 
   * Action to be called when an illegal move is attempted
   * 
   * @author Remi Carriere
   * 
   */
  // line 503 "../../../../../PawnStateMachine.ump"
  public void illegalMove(){
    // We cant throw an exception here since doing so would set the current state to null and crash the state machine. Alse, the UI does not allow a user to
    	// input a legal move, so users don't need to be warned.
    	// throw new InvalidMoveException("Illegal move!");
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 511 "../../../../../PawnStateMachine.ump"
  enum MoveDirection 
  {
    East, South, West, North, SouthWest, SouthEast, NorthWest, NorthEast;
  }

  
}