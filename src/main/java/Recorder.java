//package nz.ac.wgtn.swen225.lc.recorder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import org.json.JSONException;
import org.json.JSONObject;

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
  private static int replaySpeed; // 1 (slowest auto replay speed) to 10 (fastest auto replay speed)

  private enum RecorderState { RECORDING, MANUAL_REPLAY, AUTO_REPLAY, WAITING }

  private Map<Integer, RecordItem> currentRecording;

  private static int currentSequenceNumber = 0;

  private static RecorderState state;


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
    System.out.println("[DEBUG] Recorder: Recording has started.");
    currentRecording = new HashMap<>();
    currentSequenceNumber = 0;

    // ---- Record the initial game ---- //
    RecordItem currentChange = new RecordItem(currentSequenceNumber, "START", level);
    currentRecording.put(currentSequenceNumber, currentChange);
    System.out.println("[DEBUG] Recorder: Level has been added: [ " + currentChange + " ] ");
    currentSequenceNumber++;
  }

  /**
   * When called this will add the data to the recording.
   *
   *
   * @param data - the data to added to the recording.
   */
  public void addToRecording(String data) {
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
    System.out.println("[DEBUG] Recorder: Player has been added: [ " + newItem + " ]");
    currentRecording.put(currentSequenceNumber, newItem);
    currentSequenceNumber++;
  }

  /**
   * When called this will end the recording and save the recording to a file.*/
  private void endRecording() {
    // ---- Ask user the location and file name ---- //
    JFileChooser fileFinder = new JFileChooser();
    fileFinder.setDialogTitle("Select file to save recording to:");

    // ---- Validate the file ---- //
    int userAction = fileFinder.showSaveDialog(null);
    if (userAction == JFileChooser.APPROVE_OPTION) {
      String filePath = fileFinder.getSelectedFile().getAbsolutePath();

      // ---- Save the current game in JSON format ---- //
      System.out.println("[DEBUG] Recorder: saved json (cr size: " + currentRecording.size() + ")");
      try {
        JSONObject gameJson = new JSONObject(currentRecording);
        Files.writeString(Paths.get(filePath), gameJson.toString());
      } catch (IOException e) {
        System.out.println("Error: Saving Json file: " + e.getMessage());
      }
    }
  }


  /**
   * When called will load the game from a file.
   */
  private void loadGame() {
    System.out.println("[DEBUG] Recorder: Loading game.");

    // ---- Ask user for the file to load ---- //
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Select file to load recording from:");
    int userAction = fileChooser.showOpenDialog(null);
    currentRecording = new HashMap<>();

    // ---- Validate the file ---- //
    if (userAction == JFileChooser.APPROVE_OPTION) {
      try {
        // ---- Read the json file ---- //
        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        String jsonData = Files.readString(Paths.get(filePath));
        JSONObject json = new JSONObject(jsonData);

        for (String key : json.keySet()) {
          int sequenceNumber = Integer.parseInt(key);
          JSONObject recordData = json.getJSONObject(key);
          String other = recordData.getString("other");
          String actor = recordData.getString("actor");

          if (actor.equals("END")) {
            break;
          }

          RecordItem newRecordItem = new RecordItem(sequenceNumber, actor, other);
          currentRecording.put(sequenceNumber, newRecordItem);
        }
        System.out.println("[DEBUG] Recorder: Game loaded successfully.");
      } catch (IOException | JSONException e) {
        System.out.println("Error: Loading Json file: " + e.getMessage());
      }
    }
  }

  /**
   * When called this will replay the recording in steps when the user presses a button.
   */
  private void stepByStepReplay() {
    // ---- Load the game ---- //
    loadGame();

    // ---- Replay the game ---- //
    System.out.println("[DEBUG] Recorder: Step by step replaying.");




  }

  /**
   * When called will replay the recording at the speed set by the user.
   * If the user has not set a speed, it will default to the slowest speed.
   */
  private void autoReplay() {
    // ---- Load the game ---- //
    loadGame();

    // ---- Replay the game ---- //
    System.out.println("[DEBUG] Recorder: Auto replaying.");
    int currentSequenceNumber = 0;
    while (currentSequenceNumber < currentRecording.size()) {
      RecordItem currentRecord = currentRecording.get(currentSequenceNumber);
      System.out.println("[DEBUG] Recorder: " + currentRecord);

      // -- Wait before doing next move -- //
      try {
        Thread.sleep(400L * replaySpeed);
      } catch (InterruptedException e) {
        System.out.println("Error: " + e.getMessage());
      }

      // -- Check the type of item -- //
      if (currentRecord.actor.equals("START")) {
        // todo: tell app to load level
      } else {
        // todo: tell app to move actor
      }
      currentSequenceNumber++;
    }
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
   *
   *
   * @param level - the level that the user is currently playing.
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


  // todo delete once ---------------------------- MAIN ---------------------------- //

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
    recorder.setReplaySpeed(5);
    recorder.setAutoReplay();

    // ---- Manual replay game ---- //
    //recorder.setManualReplay();

  }


}
