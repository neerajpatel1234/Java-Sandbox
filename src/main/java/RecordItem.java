//package nz.ac.wgtn.swen225.lc.recorder;

/**
 * RecordItem.java.
 *
 * <p>RecordItem class is used to store information about the activity of the game.
 *
 * @author Neeraj Patel (300604056).
 */
public class RecordItem {
  // ----------------------------------- VARIABLES ----------------------------------- //
  int sequenceNumber; // the turn number that the action was taken on
  String actor; // the actor that took the action
  String other; // any other information that is needed to be stored

  /**
   * Constructor for RecordItem class.
   *
   * @param seqNumber - the sequence number of the action.
   * @param actor     - the actor that took the action.
   * @param other     - any other information that is needed to be stored.
   */
  public RecordItem(int seqNumber, String actor, String other) {
    this.sequenceNumber = seqNumber;
    this.actor = actor;
    this.other = other;
  }

  /**
   * Returns the sequence number of the action.
   *
   * @return sequenceNumber - the sequence number of the action.
   */
  public int getSequenceNumber() {
    return sequenceNumber;
  }

  /**
   * Returns the actor that took the action.
   *
   * @return actor - the actor that took the action.
   */
  public String getActor() {
    return actor;
  }


  /**
   * Returns any other information that is needed to be stored.
   *
   * @return other - any other information that is needed to be stored.
   */
  public String getOther() {
    return other;
  }

  /**
   * Returns a string representation of the RecordItem.
   *
   * @return string - a string representation of the RecordItem.
   */
  @Override
  public String toString() {
    return "RecordItem["
            + "sequenceNumber="
            + sequenceNumber
            + ", actor='"
            + actor
            + '\''
            + ", other='"
            + other
            + '\''
            + ']';
  }

}
