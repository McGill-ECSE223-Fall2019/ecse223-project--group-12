/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.util.*;

// line 9 "../../../../../model.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private String name;
  private int elo;

  //Player Associations
  private List<PlayerEnrollment> enrolledMatches;
  private Quoridor quoridor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(String aName, int aElo, Quoridor aQuoridor)
  {
    name = aName;
    elo = aElo;
    enrolledMatches = new ArrayList<PlayerEnrollment>();
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create player due to quoridor");
    }
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

  public boolean setElo(int aElo)
  {
    boolean wasSet = false;
    elo = aElo;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getElo()
  {
    return elo;
  }
  /* Code from template association_GetMany */
  public PlayerEnrollment getEnrolledMatche(int index)
  {
    PlayerEnrollment aEnrolledMatche = enrolledMatches.get(index);
    return aEnrolledMatche;
  }

  public List<PlayerEnrollment> getEnrolledMatches()
  {
    List<PlayerEnrollment> newEnrolledMatches = Collections.unmodifiableList(enrolledMatches);
    return newEnrolledMatches;
  }

  public int numberOfEnrolledMatches()
  {
    int number = enrolledMatches.size();
    return number;
  }

  public boolean hasEnrolledMatches()
  {
    boolean has = enrolledMatches.size() > 0;
    return has;
  }

  public int indexOfEnrolledMatche(PlayerEnrollment aEnrolledMatche)
  {
    int index = enrolledMatches.indexOf(aEnrolledMatche);
    return index;
  }
  /* Code from template association_GetOne */
  public Quoridor getQuoridor()
  {
    return quoridor;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEnrolledMatches()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public PlayerEnrollment addEnrolledMatche(String aId, PlayerEnrollment.Outcome aOutcome, Pawn aPawn, Match aMatch)
  {
    return new PlayerEnrollment(aId, aOutcome, aPawn, this, aMatch);
  }

  public boolean addEnrolledMatche(PlayerEnrollment aEnrolledMatche)
  {
    boolean wasAdded = false;
    if (enrolledMatches.contains(aEnrolledMatche)) { return false; }
    Player existingPlayer = aEnrolledMatche.getPlayer();
    boolean isNewPlayer = existingPlayer != null && !this.equals(existingPlayer);
    if (isNewPlayer)
    {
      aEnrolledMatche.setPlayer(this);
    }
    else
    {
      enrolledMatches.add(aEnrolledMatche);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeEnrolledMatche(PlayerEnrollment aEnrolledMatche)
  {
    boolean wasRemoved = false;
    //Unable to remove aEnrolledMatche, as it must always have a player
    if (!this.equals(aEnrolledMatche.getPlayer()))
    {
      enrolledMatches.remove(aEnrolledMatche);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addEnrolledMatcheAt(PlayerEnrollment aEnrolledMatche, int index)
  {  
    boolean wasAdded = false;
    if(addEnrolledMatche(aEnrolledMatche))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEnrolledMatches()) { index = numberOfEnrolledMatches() - 1; }
      enrolledMatches.remove(aEnrolledMatche);
      enrolledMatches.add(index, aEnrolledMatche);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveEnrolledMatcheAt(PlayerEnrollment aEnrolledMatche, int index)
  {
    boolean wasAdded = false;
    if(enrolledMatches.contains(aEnrolledMatche))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEnrolledMatches()) { index = numberOfEnrolledMatches() - 1; }
      enrolledMatches.remove(aEnrolledMatche);
      enrolledMatches.add(index, aEnrolledMatche);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addEnrolledMatcheAt(aEnrolledMatche, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setQuoridor(Quoridor aQuoridor)
  {
    boolean wasSet = false;
    if (aQuoridor == null)
    {
      return wasSet;
    }

    Quoridor existingQuoridor = quoridor;
    quoridor = aQuoridor;
    if (existingQuoridor != null && !existingQuoridor.equals(aQuoridor))
    {
      existingQuoridor.removePlayer(this);
    }
    quoridor.addPlayer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=enrolledMatches.size(); i > 0; i--)
    {
      PlayerEnrollment aEnrolledMatche = enrolledMatches.get(i - 1);
      aEnrolledMatche.delete();
    }
    Quoridor placeholderQuoridor = quoridor;
    this.quoridor = null;
    if(placeholderQuoridor != null)
    {
      placeholderQuoridor.removePlayer(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "elo" + ":" + getElo()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "quoridor = "+(getQuoridor()!=null?Integer.toHexString(System.identityHashCode(getQuoridor())):"null");
  }
}