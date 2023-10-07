//package nz.ac.wgtn.swen225.lc.recorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Recorder.java
 *
 * <p>Recorder class is used to record the game and replay it.
 * It will record the game and save it to a JSON file.
 *
 * @author Neeraj Patel (300604056).
 */
public class Recorder {
  // ----------------------------------- VARIABLES ----------------------------------- //
  static int replaySpeed; // 1 (slowest auto replay speed) to 10 (fastest auto replay speed)

  enum RecorderState { RECORDING, MANUAL_REPLAY, AUTO_REPLAY, WAITING }

  Map<Integer, RecordItem> currentRecording;

  static int currentSequenceNumber = 0;

  static RecorderState state;


  // ----------------------------------- CONSTRUCTOR ----------------------------------- //

  /**
   * The constructor for the Recorder class.
   * Set initial values.
   */
  public Recorder() {
    state = RecorderState.WAITING;
    replaySpeed = 5;
  }

  // ----------------------------------- METHODS ----------------------------------- //

  /**
   * When called this will start recording and save the recording to a file.
   * Reset the current recording.
   *
   *
   * @param level - the level that the user is currently playing.
   */
  private void startRecording(String level) {
    // ---- Setup recording ---- //
    System.out.println("[DEBUG] Recorder: Recording has started. \n");
    currentRecording = new HashMap<>();
    currentSequenceNumber = 0;

    // ---- Record the initial game ---- //
    RecordItem currentChange = new RecordItem(currentSequenceNumber, "START", level);
    currentRecording.put(currentSequenceNumber, currentChange);
    System.out.println("[DEBUG] Recorder: Level has been added: [ " + currentChange + " ] \n");
    currentSequenceNumber++;
  }

  /**
   * When called this will add the data to the recording.
   *
   *
   * @param data - the data to added to the recording.
   */
  public void addToRecording(String data) {
    // ---- Check if recording is happening ---- //
    if (state != RecorderState.RECORDING) {
      System.out.println("[DEBUG] Recorder: Not recording. \n");
      return;
    }

    // ---- Split the data up ---- //
    String[] dataArray = data.split("\\|");
    String actor = dataArray[0].trim();

    ArrayList<String> remainingData = new ArrayList<>();
    for (int i = 1; i < dataArray.length; i++) {
      dataArray[i] = dataArray[i].trim();
      remainingData.add(dataArray[i]);
    }

    // ---- Check type of data ---- //
    if (dataArray[0].equals("END")) {
      endRecording();
      return;
    }

    // ---- Record Player movement ---- //
    RecordItem newItem = new RecordItem(currentSequenceNumber, actor, remainingData.get(0));
    System.out.println("[DEBUG] Recorder: Player has been added: [ " + newItem + " ] \n");

    // ---- Save to current recording ---- //
    currentRecording.put(currentSequenceNumber, newItem);
    currentSequenceNumber++;
  }

  /**
   * When called this will end the recording and save the recording to a file.*/
  private void endRecording() {
    // ---- Stop recording and save the current game in jason format ---- //
    /*#
    System.out.println("Recorder: Recording has stopped & Saving game. \n");
    try {
      JSONObject jsonRecording = new JSONObject(currentRecording);
      String jsonString = jsonRecording.toString();
      Files.writeString(Paths.get("gameRecording.json"), jsonString);
    } catch (IOException e) {
      System.out.println("Error: Unable to save game recording. " + e.getMessage());
    }

     */

  }



  /**
   * When called will load the game from a file.
   */
  private void loadGame() {
    System.out.println("[DEBUG] Recorder: Loading game. \n");
    // ---- load the game from json file ---- //
    HashMap<Integer, RecordItem> loadedRecording = new HashMap<>();

    // open file finder

    // parse json file



    currentRecording = loadedRecording;
  }

  /**
   * When called this will replay the recording in steps when the user presses a button.
   */
  public void stepByStepReplay() {
    System.out.println("[DEBUG] Recorder: Step by step replaying. \n");

    loadGame();



  }

  /**
   * When called will replay the recording at the speed set by the user.
   * If the user has not set a speed, it will default to the slowest speed.
   */
  private void autoReplay() {
    System.out.println("[DEBUG] Recorder: Auto replaying. \n");

    loadGame();


  }


  // ----------------------------------- GETTERS ----------------------------------- //

  /**
   * Returns the state of the recorder.
   *
   * @return the state of the recorder.
   */
  public String getState() {
    return state.toString();
  }

  /**
   * Returns the current recording.
   *
   * @return the current recording.
   */
  public Map<Integer, RecordItem> getCurrentRecording() {
    return currentRecording;
  }




  // ----------------------------------- SETTERS ----------------------------------- //

  /**
   * Will set the state of the recorder to be recording.
   */
  public void setRecording(String level) {
    state = RecorderState.RECORDING;
    startRecording(level);
  }

  /**
   * Will set the state of the recorder to be manual replay.
   */
  public void setManualReplay() {
    state = RecorderState.MANUAL_REPLAY;
    stepByStepReplay();
  }

  /**
   * Will set the state of the recorder to be auto replay.
   */
  public void setAutoReplay() {
    state = RecorderState.AUTO_REPLAY;
    autoReplay();
  }

  /**
   * This method will set the replay speed.
   *
   * @param speed - the speed to set the replay speed to.
   */
  public void setReplaySpeed(int speed) {
    replaySpeed = speed;
  }


  // todo delete once ----------------------------------- MAIN ----------------------------------- //

  /**
   * The main method for the Recorder program.
   *
   * @param args - the arguments for the main method.
   */
  public static void main(String[] args) {

    // ---- Create a new instance of the Recorder ---- //
    Recorder recorder = new Recorder();

    // ---- Start recording game ---- //
    recorder.setRecording("1"); // Send [level number]

    // ---- Record player and actor movements ---- //
    recorder.addToRecording("PLAYER | MOVE_LEFT");  // Send [currentPlayer | move]
    recorder.addToRecording("ACTOR | MOVE_RIGHT");  // Send [ACTOR | move]
    recorder.addToRecording("PLAYER | na");
    recorder.addToRecording("PLAYER | na");

    // ---- Stop recording the game and save json file---- //
    recorder.addToRecording("END");

    // ---- Auto replay game ---- //
    //recorder.setAutoReplay();

    // ---- Manual replay game ---- //
    //recorder.setManualReplay();

  }


}
