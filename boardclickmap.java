import javax.swing.JFrame;
import javax.swing.SwingUtilities;public class ClueClient {    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new ClientFrame().setVisible(true);            }
        });    }}
8:21
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;import java.awt.BorderLayout;
import java.awt.EventQueue;import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import java.lang.StringBuilder;
import java.util.Arrays;public class ClientFrame extends JFrame {    private JPanel contentPane;
    private StringBuilder noteStringBuilder;
    private int noteCounter = 0;
    private JTextArea log_text_area;
    //private ClientManager cm;
    //private ServerConnection serverConnection;    int rows = 24;
    int coloums = 25;    /**
     * Create the frame.
     */
    public ClientFrame() {        //setServerConnection(serverConnection);        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 950, 800);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 204, 204));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);        noteStringBuilder = new StringBuilder();        JPanel BoardPanel = new JPanel();
        BoardPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        BoardPanel.setBackground(Color.PINK);
        BoardPanel.setBounds(6, 37, 688, 535);
        contentPane.add(BoardPanel);
        BoardPanel.setLayout(null);        //***** determine x and y coordinates *****//        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int tile = clickLocation(x,y);
                if (tile == 1)
                {
                    log_text_area.setText("Conservatory");
                }
                else if (tile == 2)
                {
                    log_text_area.setText("Ballroom");
                }
                else if (tile == 3)
                {
                    log_text_area.setText("Kitchen");
                }
                else if (tile == 4)
                {
                    log_text_area.setText("Billiard Room");
                }
                else if (tile == 5)
                {
                    log_text_area.setText("Library");
                }
                else if (tile == 6)
                {
                    log_text_area.setText("Study");
                }
                else if (tile == 7)
                {
                    log_text_area.setText("Hall");
                }
                else if (tile == 8)
                {
                    log_text_area.setText("Lounge");
                }
                else if (tile == 9)
                {
                    log_text_area.setText("Dining Room");
                }
                else if (tile == 10)
                {
                    log_text_area.setText("Kitchen");
                }
                else if (tile == 11)
                {
                    log_text_area.setText("Staircase");
                }
                else

                {
                    String xc=String.valueOf(x); 
                    String yc=String.valueOf(y);  
                    String s = (", ");
                    String coordinates = xc.concat(s).concat(yc);
                    //int tileSelected = determineTile(x,y);                    log_text_area.setText(coordinates);
                }
            }            });        lblNewLabel.setIcon(new ImageIcon("resources\\board.jpg")); //** save image of board from git to your computer and add relative path
        lblNewLabel.setBounds(6, 6, 569, 523);
        BoardPanel.add(lblNewLabel);        JButton btnNewButton = new JButton("Suggest");
        btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnNewButton.setBounds(579, 349, 99, 30);
        BoardPanel.add(btnNewButton);        JButton btnAccuse = new JButton("Accuse");
        btnAccuse.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnAccuse.setBounds(579, 389, 99, 30);
        BoardPanel.add(btnAccuse);        JButton btnShortcut = new JButton("Shortcut");
        btnShortcut.setForeground(Color.BLACK);
        btnShortcut.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnShortcut.setBounds(579, 431, 99, 30);
        BoardPanel.add(btnShortcut);        JButton btnEndTurn = new JButton("End Turn");
        btnEndTurn.setForeground(Color.RED);
        btnEndTurn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnEndTurn.setBounds(579, 471, 99, 30);
        BoardPanel.add(btnEndTurn);        JButton btnRollDice = new JButton("Roll Dice");
        btnRollDice.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnRollDice.setBounds(579, 306, 99, 30);
        BoardPanel.add(btnRollDice);        log_text_area = new JTextArea(10,60);
        log_text_area.setEditable(false);
        log_text_area.setLineWrap(true);        JScrollPane scrollPane = new JScrollPane(log_text_area);
        scrollPane.setBounds(6, 613, 688, 159);
        contentPane.add(scrollPane);        JLabel lblConsoleLog = new JLabel("Console Log");
        lblConsoleLog.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblConsoleLog.setBounds(6, 585, 80, 16);
        contentPane.add(lblConsoleLog);        JLabel lblAddGameNote = new JLabel("Add Game Note");
        lblAddGameNote.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblAddGameNote.setBounds(774, 24, 113, 16);
        contentPane.add(lblAddGameNote);        final JTextArea textAreaGameNote = new JTextArea();
        textAreaGameNote.setEditable(true);
        textAreaGameNote.setLineWrap(true);
        textAreaGameNote.setBounds(706, 52, 238, 98);
        contentPane.add(textAreaGameNote);        JButton btnAddNote = new JButton("Add Note");
        btnAddNote.setFont(new Font("SansSerif", Font.BOLD, 12));        btnAddNote.setBounds(770, 162, 117, 29);
        contentPane.add(btnAddNote);        JLabel lblGameNotes = new JLabel("Game Notes");
        lblGameNotes.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblGameNotes.setBounds(784, 206, 80, 16);
        contentPane.add(lblGameNotes);        final JTextArea textAreaNotesAdded = new JTextArea(5, 10);
        textAreaNotesAdded.setEditable(false);
        textAreaNotesAdded.setLineWrap(true);        JScrollPane scrollPaneNotesAdded = new JScrollPane(textAreaNotesAdded);
        scrollPaneNotesAdded.setBounds(706, 234, 238, 111);
        contentPane.add(scrollPaneNotesAdded);        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Options");
        gameMenu.setFont(new Font("SansSerif", Font.BOLD, 12));
        JMenuItem gameRulesMenuItem = new JMenuItem("Game Rules");
        gameMenu.add(gameRulesMenuItem);
        menuBar.add(gameMenu);        menuBar.setToolTipText("Options");
        menuBar.setBounds(6, 6, 132, 22);
        contentPane.add(menuBar);        //FOR TESTING
        //log_text_area.setText(Integer.toString(serverConnection.getPortNumber()));        JButton btnShowCards = new JButton("Show Cards");
        btnShowCards.setForeground(new Color(0, 0, 255));
        btnShowCards.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnShowCards.setBounds(774, 357, 117, 29);
        contentPane.add(btnShowCards);        btnAddNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String note_to_add = textAreaGameNote.getText();
                if(note_to_add.isEmpty()) {
                    /*
                    Display JOptionPane with error message if textAreaGameNote
                    contained no text. 
                    */
                    JOptionPane.showMessageDialog(null, "No Text Entered");
                }
                else {
                    noteCounter++;
                    noteStringBuilder.append("Note ");
                    noteStringBuilder.append(Integer.toString(noteCounter));
                    noteStringBuilder.append(": ");
                    noteStringBuilder.append(note_to_add);
                    noteStringBuilder.append("\n");
                    textAreaNotesAdded.setText(noteStringBuilder.toString());
                    textAreaGameNote.setText("");
                }
            }
            }); //end button action listener        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        //setVisible(true);     }     protected int clickLocation(int x, int y) 
        {
            if ((y <= 493 && y >= 397 && x >= 26 && x <=135) || (x >= 135 && x <= 151 && y >= 415 && y <= 493))
            {
                return 1; //convervatory
            }
            else if (y<=465 && y>= 355 && x <= 359 && x>=202)
            {
                return 2; //ballroom
            }
            else if (y<=493 && y>= 377 && x >= 412 && x <= 440)
            {
                return 3; //kitchen
            }
            else if (x >= 26 && x <= 151 && y >= 255 && y <= 345)
            {
                return 4; //billiard room
            }
            else if ((x>=26 && x<= 151 && y >= 136 && y <= 227) || (x >=136 && x <=172 && y >= 157 && y <= 205))
            {
                return 5; //library
            }
            else if (x >=26 && x <= 172 && y >= 14 && y <= 89)
            {
                return 6; //study
            }
            else if (y >= 16 && y <= 149 && x >= 223 && x <= 339)
            {
                return 7; //hall
            }
            else if (x >= 390 && x <= 535 && y >= 13 && y <= 129)
            {
                return 8; //lounge
            }
            else if ((y >= 195 && y <=305 && x >= 369 && x <= 537) || (y>=305 && y <= 328 && x >= 432 && x <= 537))
            {
                return 9; // dining room
            }
            else if (x >= 411 && x <= 537 && y >= 374 && y <= 493)
            {
                return 10; //kitchen
            }
            else if (x >= 219 && x <= 321 && y >= 172 && y < 309)
            {
                return 11; //staircase
            }
            else
            {
                return 0;
            }
        }
    } //end class


    public static void main(String[ ] args) {



board_locations = study;



        // change this to a class for the location data so we can have the data stored
        // there and changed all of the location information we need
        // who is here, and other inportand location data needed
    String  [][] grid_location = new String [24][25];

        
    int pixal_with;
    pixal_with = 20;

    int x = e.getX() - 30; // off set by 30
    int y = e.getY() - 13; // off set by 13

    int x_mod = x%20;
    int y_mod = y%20;

    int x_cowordanit = x/pixal_with;
    int y_cowordanit = y/pixal_with;

    if(x_mod >= 2 || x_mod <= 17  || y_mod >= 2 || y_mod <= 17){
        if(x_cowordanit <= 23 || y_cowordanit <= 24)
        {
        return x_cowordanit, y_cowordanit;
        }
        else{
            return bad click try again;
        }
    }
    else{
        return bad click try again;
    }

    public class board_locations_path{
        String name;
        int grid_location [] = new int [2];
        };
        public class board_locations_Study{
            String name = Study;
            int [][] grid_location_s =  {{0,0},{0,1},{0,2},{0,3},{0,4},{0,5},
                                         {1,0},{1,1},{1,2},{1,3},{1,4},{1,5},{1,6},
                                         {2,0},{2,1},{2,2},{2,3},{2,4},{2,5},{2,6},
                                         {3,0},{3,1},{3,2},{3,3},{3,4},{3,5},{3,6}
                                        };

    };
        public class board_locations_library{
            String name = library;
            int [][] grid_location_s =  {      {6,1},{6,2},{6,3},{6,4},{6,5},
                                         {7,0},{7,1},{7,2},{7,3},{7,4},{7,5},{7,6},
                                         {8,0},{8,1},{8,2},{8,3},{8,4},{8,5},{8,6},
                                         {9,0},{9,1},{9,2},{9,3},{9,4},{9,5},{9,6},
                                               {10,1},{10,2},{10,3},{10,4},{10,5},
                                         
                                         
                                        };
        };
        public class board_locations_billiard_room{
            String name = billiard_room;
            int [][] grid_location_s =  {{12,0},{12,1},{12,2},{12,3},{12,4},{12,5},
                                         {13,0},{13,1},{13,2},{13,3},{13,4},{13,5},
                                         {14,0},{14,1},{14,2},{14,3},{14,4},{14,5},
                                         {15,0},{15,1},{15,2},{15,3},{15,4},{15,5},
                                         {16,0},{16,1},{16,2},{16,3},{16,4},{16,5},
                                         {17,0},{17,1},{17,2},{17,3},{17,4},{17,5},
                                        };
        };
        public class board_locations_convervatory{
            String name = convervatory;
            int [][] grid_location_s =  {       {20,1},{20,2},{20,3},{20,4},
                                         {21,0},{21,1},{21,2},{21,2},{21,4},{21,5},
                                         {22,0},{22,1},{22,2},{22,3},{22,4},{22,5},
                                         {23,0},{23,1},{23,2},{23,3},{23,4},{23,5},
                                         {24,0},{24,1},{24,2},{24,3},{24,4},{24,5}
                                                
                                        };
        };
        public class board_locations_hall{
            String name = hall;
            int [][] grid_location_s =  {{0,9},{0,10},{0,11},{0,12},{0,13},{0,14},
                                         {1,9},{1,10},{1,11},{1,12},{1,13},{1,14},
                                         {2,9},{2,10},{2,11},{2,12},{2,13},{2,14},
                                         {3,9},{3,10},{3,11},{3,12},{3,13},{3,14},
                                         {4,9},{4,10},{4,11},{4,12},{4,13},{4,14},
                                         {5,9},{5,10},{5,11},{5,12},{5,13},{5,14},
                                         {6,9},{6,10},{6,11},{6,12},{6,13},{6,14},
                                        };
        };
        public class board_locations_staircase{
            String name = staircase;
            int [][] grid_location_s =  {{8,9},{8,10},{8,11},{8,12},{8,13},
                                        {9,9},{9,10},{9,11},{9,12},{9,13},
                                        {10,9},{10,10},{10,11},{10,12},{10,13},
                                        {11,9},{11,10},{11,11},{11,12},{11,13},
                                        {12,9},{12,10},{12,11},{12,12},{12,13},
                                        {13,9},{13,10},{13,11},{13,12},{13,13},
                                        {14,9},{14,10},{14,11},{14,12},{14,13},
                                        };
        };
        public class board_locations_ballroom{
            String name = ballroom;
            int [][] grid_location_s =  {{17,8},{17,9},{17,10},{17,11},{17,12},{17,13},{17,14},{17,15},
                                         {18,8},{18,9},{18,10},{18,11},{18,12},{18,13},{18,14},{18,15},
                                         {19,8},{19,9},{19,10},{19,11},{19,12},{19,13},{19,14},{19,15},
                                         {20,8},{20,9},{20,10},{20,11},{20,12},{20,13},{20,14},{20,15},
                                         {21,8},{21,9},{21,10},{21,11},{21,12},{21,13},{21,14},{21,15},
                                         {22,8},{22,9},{22,10},{22,11},{22,12},{22,13},{22,14},{22,15},
                                                       {23,10},{23,11},{23,12},{23,13},
                                                       {24,10},{24,11},{24,12},{24,13},
                                        };
        };
        public class board_locations_lounge{
            String name = lounge;
            int [][] grid_location_s =  {      {0,19},{0,20},{0,21},{0,22},{0,23},
                                         {1,18},{1,19},{1,20},{1,21},{1,22},{1,23},
                                         {2,18},{2,19},{2,20},{2,21},{2,22},{2,23},
                                         {3,18},{3,19},{3,20},{3,21},{3,22},{3,23},
                                         {4,18},{4,19},{4,20},{4,21},{4,22},{4,23},
                                         {5,18},{5,19},{5,20},{5,21},{5,22},{5,23},
                                         {6,18},{6,19},{6,20},{6,21},{6,22},{6,23},

                                        };
        };
        public class board_locations_dining_room {
            String name = dining_room;
            int [][] grid_location_s =  { {9,17},{9,18},{9,19},{9,20},{9,21},{9,22},{9,23},
                                          {10,17},{10,18},{10,19},{10,20},{10,21},{10,22},{10,23},
                                          {11,17},{11,18},{11,19},{11,20},{11,21},{11,22},{11,23},
                                          {12,17},{12,18},{12,19},{12,20},{12,21},{12,22},{12,23},
                                          {13,17},{13,18},{13,19},{13,20},{13,21},{13,22},{13,23},
                                          {14,17},{14,18},{14,19},{14,20},{14,21},{14,22},{14,23},
                                                                  {15,20},{15,21},{15,22},{15,23},
                                        };
        };
        public class board_locations_kitchen{
            String name = kitchen;
            int [][] grid_location_s =  {{18,18},{18,19},{18,20},{18,21},{18,22},{18,23},
                                         {19,18},{19,19},{19,20},{19,21},{19,22},{19,23},
                                         {20,18},{20,19},{20,20},{20,21},{20,22},{20,23},
                                         {21,18},{21,19},{21,20},{21,21},{21,22},{21,23},
                                         {22,18},{22,19},{22,20},{22,21},{22,22},{22,23},
                                         {23,18},{23,19},{23,20},{23,21},{23,22},{23,23},
                                        };
        };
        //class for all of the tiles that are not rooms
public class board_locations_tiles extends board_locations_tile{
    //build a class array that has the information on the tiles

};
        // base class that we can add to at tiles class 
        public class board_locations_tile{ 
            //is this space occupied
            boolean occupied = false;
            //to indicate the player based off there player number from 1-6
            int player = 0;
        };

        List<int[]> intArrays=new ArrayList<>();
        int anExample[]={1,2,3};

        for(int[] anIntArray:intArrays) {
            //iterate the retrieved array an print the individual elements
            for (int aNumber : anIntArray) {
                System.out.println("Arraylist contains:" + aNumber);
            }


        // player class that has a grid location saved and any otherinformation needed
        // 

    }
