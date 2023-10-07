//package test.nz.ac.wgtn.swen225.lc.recorder;

//import nz.ac.wgtn.swen225.lc.recorder.Recorder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This is a collection of tests for the Recorder class.
 * It will test the methods in the Recorder class to ensure that they are working as expected.
 * It will test the following methods:
 * TODO add methods to test
 *
 * @Author: Neeraj Patel (300604056).
 */
public class RecorderTests {
  /**
   * Test that the JUnit tests are working.
   */
  @Test
  void test_Junit_Working() {
    Assertions.assertTrue(true);
  }

  /**
   * Test the initialisation and states of startRecording() method.
   */
  @Test
  void test_startRecording() {
    Recorder test = new Recorder();
    String level = "1";

    // --- Test that the initial state is WAITING --- //
    Assertions.assertEquals("WAITING", test.getState());

    // --- Test that the state is RECORDING after startRecording() is called --- //
    test.setRecording(level);
    Assertions.assertEquals("RECORDING", test.getState());
  }

  /**
   * Test the adding elements of addToRecording() method.
   */
  @Test
  void test_addToRecording_01() {
    Recorder test = new Recorder();
    String level = "1";
    int sequenceNumber = 0;

    // --- Add elements to the recording --- //
    test.setRecording(level);
    test.addToRecording("PLAYER | MOVE_LEFT");
    test.addToRecording("ACTOR | MOVE_LEFT");
    test.addToRecording("ACTOR | MOVE_RIGHT");
    test.addToRecording("PLAYER | MOVE_RIGHT");

    // --- Test that the elements have been added to the recording --- //
    if (test.getCurrentRecording().size() != 5) {
      Assertions.fail("The size of the current recording is not 4");
    }

    // --- Test that the elements have been added to the recording in the correct order --- //
    for (int i = 0; i < test.getCurrentRecording().size(); i++) {
      Assertions.assertEquals(sequenceNumber,
              test.getCurrentRecording().get(i).getSequenceNumber());
      sequenceNumber++;
    }
  }

  /**
   * Test the adding elements of addToRecording() method.
   */
  @Test
  void test_addToRecording_02() {
    Recorder test = new Recorder();
    test.setRecording("1");

    // --- Add data to the recording --- //
    test.addToRecording("PLAYER | MOVE_LEFT");
    test.addToRecording("ACTOR | MOVE_RIGHT");

    Assertions.assertEquals(3, test.currentRecording.size());

    // --- Check the sequence numbers are correct --- //
    Assertions.assertEquals(0, test.currentRecording.get(0).sequenceNumber);
    Assertions.assertEquals(1, test.currentRecording.get(1).sequenceNumber);

    // --- Check that the data was recorded correctly --- //
    Assertions.assertEquals("PLAYER", test.currentRecording.get(1).actor);
    Assertions.assertEquals("MOVE_LEFT", test.currentRecording.get(1).other);

    Assertions.assertEquals("ACTOR", test.currentRecording.get(2).actor);
    Assertions.assertEquals("MOVE_RIGHT", test.currentRecording.get(2).other);
  }

  /**
   * Test that the stepByStepReplay() method is working as expected.
   * This will test that the stepByStepReplay() method is working as expected.
   */
  @Test
  void test_stepByStepReplay() {
    // TODO: Implement

    Recorder test = new Recorder();

  }

  /**
   * Test that the autoReplay() method is working as expected.
   * This will test that the autoReplay() method is working as expected.
   */
  @Test
  void test_autoReplay() {
    // TODO: Implement

    Recorder test = new Recorder();

  }

  /**
   * Test that the loadGame() method is working as expected.
   * This will test that the loadGame() method is working as expected.
   */
  @Test
  void test_loadGame() {
    // TODO: Implement

    Recorder test = new Recorder();

  }

  /**
   * Test that the getIsRecording() method is working as expected.
   * This will test that the getIsRecording() method is working as expected.
   */
  @Test
  void test_getIsRecording() {
    Recorder test = new Recorder();



  }

}

