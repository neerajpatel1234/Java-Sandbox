
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class SwingSandbox extends JFrame implements ActionListener {
    // -------------------------------------- VARIABLES --------------------------------------
    static JMenuBar menuBar;
    static JMenu menuContainer1;
    static JMenuItem menuItem1, menuItem2, menuItem3;
    static JFrame frame;
    static JLabel label;

    // -------------------------------------- MAIN --------------------------------------
    public static void main(String[] args) {
        setUpGUI();
    }

    // -------------------------------------- OTHER METHODS --------------------------------------
    /**
     * set up the GUI
     */
    public static void setUpGUI(){
        SwingSandbox display = new SwingSandbox();

        frame = new JFrame("Testing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLayout(new GridLayout(10, 10, 5, 5));

        JPanel panel = new JPanel();

        panel.setBorder(BorderFactory.createTitledBorder("This is a title"));

        frame.add(panel);


        menuBar = new JMenuBar();

        JMenuItem menuItemStart = new JMenuItem("Start");
        menuItemStart.addActionListener(display);
        menuBar.add(menuItemStart);

        JMenuItem menuItemQuit = new JMenuItem("Quit");
        menuItemQuit.addActionListener(display);
        menuBar.add(menuItemQuit);


        frame.setJMenuBar(menuBar);
        frame.setVisible(true);


    }



    /**
     * @param e event performed my user
     */
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        switch (s) {
            case "Start" -> System.out.println("Button pressed: Start");
            case "Quit" -> {
                System.out.println("Button pressed: Exit");
                System.exit(0);
            }
            case "menuItem3" -> System.out.println("menuItem3 clicked");

        }
    }

}
