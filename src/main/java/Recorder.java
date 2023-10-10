//package nz.ac.wgtn.swen225.lc.recorder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
  private int replaySpeed; // 1 (fastest auto replay speed) to 10 (slowest auto replay speed)

  private Map<Integer, RecordItem> currentRecording;

  private int currentSequenceNumber = 0;
  
  private JFileChooser fileFinder;

  // ----------------------------------- CONSTRUCTOR ----------------------------------- //

  /**
   * The constructor for the Recorder class.
   * Set initial values.
   */
  public Recorder() {
    replaySpeed = 5;

    // todo: delete after using newGame method in app
    currentRecording = new HashMap<>();
  }

  // ----------------------------------- METHODS ----------------------------------- //

  /**
   * When called this will start recording and save the recording to a file.
   * Reset the current recording.
   *
   *
   * @param level - the level that the user is currently playing.
   */
  public void startRecording(String level) {
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

    // ---- Check type of data ---- //
    if (dataArray[0].equals("END")) {
      endRecording();
      return;
    }

    // ---- Record Player movement ---- //
    String move = dataArray[1].trim();
    RecordItem newItem = new RecordItem(currentSequenceNumber, actor, move);
    System.out.println("[DEBUG] Recorder: Player has been added: [ " + newItem + " ]");
    currentRecording.put(currentSequenceNumber, newItem);
    currentSequenceNumber++;
  }

  /**
   * When called this will end the recording and save the recording to a file.*/
  private void endRecording() {
    // ---- Ask user the location and file name ---- //
    fileFinder = new JFileChooser();
    fileFinder.setDialogTitle("Select file to save recording to:");

    // ---- Validate the file ---- //
    int userAction = fileFinder.showSaveDialog(null);
    if (userAction == JFileChooser.APPROVE_OPTION) {
      String filePath = fileFinder.getSelectedFile().getAbsolutePath();

      // ---- Save the current game in JSON format ---- //
      System.out.println("[DEBUG] Recorder: saved json; c.r. size: " + currentRecording.size());
      try {
        JSONObject gameJson = new JSONObject(currentRecording);
        Files.writeString(Paths.get(filePath), gameJson.toString());
      } catch (IOException e) {
        System.out.println("Error: Saving Json file: " + e.getMessage());
      }
    }
  }


  /**
   * When called will load the game from a json file.
   */
  private void loadGame() {
    System.out.println("[DEBUG] Recorder: Loading game.");

    // ---- Ask user for the file to load ---- //
    fileFinder = new JFileChooser();
    fileFinder.setDialogTitle("Select file to load recording from:");
    int userAction = fileFinder.showOpenDialog(null);
    Map<Integer, RecordItem> loadedRecording = new HashMap<>();
    currentRecording = new HashMap<>();

    // ---- Validate the file ---- //
    if (userAction == JFileChooser.APPROVE_OPTION) {
      try {
        // ---- Read the json file ---- //
        String filePath = fileFinder.getSelectedFile().getAbsolutePath();
        String jsonData = Files.readString(Paths.get(filePath));
        JSONObject json = new JSONObject(jsonData);

        // -- Break down the json data -- //
        for (String key : json.keySet()) {
          int sequenceNumber = Integer.parseInt(key);
          JSONObject recordData = json.getJSONObject(key);
          String move = recordData.getString("move");
          String actor = recordData.getString("actor");

          // -- Check if the game has ended -- //
          if (actor.equals("END")) {
            break;
          }

          // -- Add the data -- //
          RecordItem newRecordItem = new RecordItem(sequenceNumber, actor, move);
          loadedRecording.put(sequenceNumber, newRecordItem);
        }
      } catch (IOException | JSONException e) {
        System.out.println("Error: Loading Json file: " + e.getMessage());
      }
    }

    // ---- Verify selected file ---- //
    if (loadedRecording.isEmpty()) {
      System.out.println("Error: No recording loaded.");
      loadGame();
    } else {
      currentRecording = loadedRecording;
    }
  }

  /**
   * When called this will replay the recording in steps when the user presses a button.
   */
  public void manualReplay() {
    // ---- Load the game ---- //
    loadGame();

    // ---- Display the loaded game ---- //
    int currentSequenceNumber = 0;
    String level = currentRecording.get(currentSequenceNumber).getMove();
    System.out.println("[DEBUG] Recorder to App: Level [ " + level + " ] ");
    // todo: tell app to load level
    currentSequenceNumber++;

    // ---- Wait for user input  ---- //
    try {
      while (currentSequenceNumber < currentRecording.size()) {
        System.out.println("Press the enter key to step through the replay.");
        System.in.read();  // Wait for user input

        // -- Do next move when button pressed-- //
        sendToApp(currentSequenceNumber);
        currentSequenceNumber++;
      }
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  /**
   * When called will replay the recording at the speed set by the user.
   * If the user has not set a speed, it will default to the slowest speed.
   */
  public void autoReplay() {
    // ---- Load the game ---- //
    loadGame();

    // ---- Ask user for replay speed ---- //
    askReplaySpeed();

    // ---- Replay the game ---- //
    int currentSequenceNumber = 0;
    while (currentSequenceNumber < currentRecording.size()) {
      RecordItem currentRecord = currentRecording.get(currentSequenceNumber);

      // -- Load the level -- //
      if (currentRecord.getActor().equals("START")) {
        // todo: tell app to load level
        System.out.println("[DEBUG] Recorder to App: Level [ " + currentRecord.getMove() + " ] ");
        currentSequenceNumber++;
      }

      // -- Wait before doing next move -- //
      try {
        Thread.sleep(400L * replaySpeed);
      } catch (InterruptedException e) {
        System.out.println("Error: " + e.getMessage());
      }

      // -- Do next move -- //
      sendToApp(currentSequenceNumber);
      currentSequenceNumber++;
    }
  }

  /**
   * When called this will send the data to App for which action to take.
   */
  private void sendToApp(int sequenceNumber) {
    RecordItem currentRecord = currentRecording.get(sequenceNumber);

    // ---- Extract the data ---- //
    String actor = currentRecord.getActor();
    String move = currentRecord.getMove();

    // ---- Send the data to App ---- //
    // todo: tell app which move to make and on who
    System.out.println("[DEBUG] Recorder to App: " + actor + " | " + move);
  }

  /**
   * When called this will ask the user for the replay speed.
   */
  private void askReplaySpeed() {
    String userSpeed = JOptionPane.showInputDialog("Enter replay speed (1 to 10):");

    // Validate the input speed
    int newSpeed = 5; // Default speed
    try {
      newSpeed = Integer.parseInt(userSpeed);
      if (newSpeed < 1 || newSpeed > 10) {
        System.out.println("Error: Invalid input select a number from 1 and 10.");
        askReplaySpeed();
      }
    } catch (NumberFormatException e) {
      System.out.println("Error: Invalid input select a number from 1 and 10.");
      askReplaySpeed();
    }

    replaySpeed = newSpeed;
  }


  // ----------------------------------- GETTERS ----------------------------------- //

  /**
   * Returns the current recording.
   *
   * @return the current recording.
   */
  public Map<Integer, RecordItem> getCurrentRecording() {
    return Collections.unmodifiableMap(currentRecording);
  }


  // todo: delete at the end ---------------------------- MAIN ---------------------------- //

  /**
   * The main method for the Recorder program.
   *
   * @param args - the arguments for the main method.
   */
  public static void main(String[] args) {

    // ---- Create a new instance of the Recorder ---- //
    Recorder recorder = new Recorder();

    // ---- Start recording game ---- //
    recorder.startRecording("1"); // Send [level number]

    // ---- Record player and actor movements ---- //
    recorder.addToRecording("PLAYER | MOVE_LEFT");  // Send [currentPlayer | move]
    recorder.addToRecording("ACTOR | MOVE_RIGHT");
    recorder.addToRecording("MONSTER | MOVE_UP");
    recorder.addToRecording("MONSTER | MOVE_DOWN");

    // ---- Stop recording the game and save json file---- //
    recorder.addToRecording("END");

    // ---- Auto replay game ---- //
    System.out.println(" \n ---------- Auto Replay ----------");
    recorder.autoReplay();

    // ---- Manual replay game ---- //
    System.out.println(" \n ---------- Manual Replay ----------");
    recorder.manualReplay();
  }


}
