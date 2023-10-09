//package test.nz.ac.wgtn.swen225.lc.recorder;

//import nz.ac.wgtn.swen225.lc.recorder.Recorder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This is a collection of tests for the Recorder class.
 * It will test the methods in the Recorder class to ensure that they are working as expected.
 * It will test the following methods:
 * TODO add methods to test
 *
 * @author Neeraj Patel (300604056).
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
    test.startRecording(level);
    test.addToRecording("PLAYER | MOVE_LEFT");
    test.addToRecording("MONSTER | MOVE_LEFT");
    test.addToRecording("MONSTER | MOVE_RIGHT");
    test.addToRecording("PLAYER | MOVE_RIGHT");

    // --- Test that the elements have been added to the recording --- //
    if (test.getCurrentRecording().size() != 5) {
      Assertions.fail("The size of the current recording is not 4");
    }

    // --- Test that the elements have been added to the recording in the correct order --- //
    for (int i = 0; i < test.getCurrentRecording().size(); i++) {
      assertEquals(sequenceNumber,
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
    test.startRecording("1");

    // --- Add data to the recording --- //
    test.addToRecording("PLAYER | MOVE_LEFT");
    test.addToRecording("ACTOR | MOVE_RIGHT");

    assertEquals(3, test.getCurrentRecording().size());

    // --- Check the sequence numbers are correct --- //
    assertEquals(0, test.getCurrentRecording().get(0).getSequenceNumber());
    assertEquals(1, test.getCurrentRecording().get(1).getSequenceNumber());

    // --- Check that the data was recorded correctly --- //
    assertEquals("PLAYER", test.getCurrentRecording().get(1).getActor());
    assertEquals("MOVE_LEFT", test.getCurrentRecording().get(1).getMove());

    assertEquals("ACTOR", test.getCurrentRecording().get(2).getActor());
    assertEquals("MOVE_RIGHT", test.getCurrentRecording().get(2).getMove());
  }

  /**
   * Test that the endRecording() method is working as expected.
   */
  @Test
  void test_endRecording() {
    // TODO: Implement
  }


  /**
   * Test that the loadGame() method is working as expected.
   */
  @Test
  void test_loadGame() {
    // TODO: Implement

    Recorder test = new Recorder();

  }

  /**
   * Test that the stepByStepReplay() method is working as expected.
   */
  @Test
  void test_stepByStepReplay() {
    // TODO: Implement

    Recorder test = new Recorder();

  }

  /**
   * Test that the autoReplay() method is working as expected.
   */
  @Test
  void test_autoReplay() {
    // TODO: Implement
  }

}

