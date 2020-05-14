import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
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
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Rectangle;

public class ClientFrame extends JFrame {

	private JPanel contentPane;
	private StringBuilder noteStringBuilder, logStringBuilder;
	private JTextArea log_text_area, textAreaNotesAdded, textAreaGameNote;
	private JScrollPane scrollPane, scrollPaneNotesAdded;
	private JLabel lblConsoleLog, lblAddGameNote, lblGameNotes;
	private JMenuBar menuBar;
	private JMenu gameMenu;
	private JMenuItem gameRulesMenuItem, seeCardDeckMenuItem;
	private JButton btnAddNote, startBtn;
	Message messageRecieved;
	Client client;
	Player currentPlayer;
	private int noteCounter = 0;
	int rows = 24;
	int coloums = 25;
	String value;
	private final BoardPanel gameBoardPanel;
    int xe = 0;
    int ye = 0;

	/**
	 * Create the frame.
	 */
	public ClientFrame(Client clientConnection) {
		client = clientConnection;
		
		//initialize all gui components minus the game board panel
		initComponents();
		
		//call to server asking if 

		//start button
		startBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
		
		//request and receive the player object for this player
		try {
			client.send(new Message(ClueGameConstants.REQUEST_PLAYER_OBJECT, null));
			messageRecieved = client.getMessage();
			currentPlayer = (Player) messageRecieved.getData();
			if(messageRecieved.getData() == null) 
				System.out.println("No data");
			
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}
		
		//display the character properties in the console log
		addToLogConsole(currentPlayer.getCharacter().getName());
		xe = currentPlayer.getCharacter().getxStarting();
		ye = currentPlayer.getCharacter().getyStarting();
		String startPointsStr = "Starting points: " + xe + " " + ye;
		addToLogConsole(startPointsStr);
		
		gameBoardPanel = new BoardPanel(clientConnection, this, currentPlayer);
		contentPane.add(gameBoardPanel);
		gameBoardPanel.setLayout(null);

		btnAddNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addToNotebook(textAreaGameNote.getText());
			}
		}); //end button action listener

		
		//setVisible(true); 
	} // end constructor

	//add text to the console log
	protected void addToLogConsole(String input){
		logStringBuilder.append(input + "\n");
		log_text_area.setText(logStringBuilder.toString());
	}
	
	//add notes to the notebook
	private void addToNotebook(String input){
		String noteToAdd = input;
		if(noteToAdd.isEmpty()) {
			//Display JOptionPane with error message if textAreaGameNote contained no text. 
			JOptionPane.showMessageDialog(null, "No Text Entered");
		}
		else {
			noteCounter++;
			noteStringBuilder.append("Note ");
			noteStringBuilder.append(Integer.toString(noteCounter));
			noteStringBuilder.append(": ");
			noteStringBuilder.append(noteToAdd);
			noteStringBuilder.append("\n");
			textAreaNotesAdded.setText(noteStringBuilder.toString());
			textAreaGameNote.setText("");
		}
	} 
	
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 800);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 204, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		startBtn = new JButton("Start Game");
        startBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        startBtn.setBounds(109, 579, 105, 28);
		contentPane.add(startBtn);
		
		noteStringBuilder = new StringBuilder();
		logStringBuilder  = new StringBuilder();
		
		log_text_area = new JTextArea(10,60);
		log_text_area.setEditable(false);
		log_text_area.setLineWrap(true);
		
		scrollPane = new JScrollPane(log_text_area);
		scrollPane.setBounds(6, 613, 688, 159);
		contentPane.add(scrollPane);
		
		lblConsoleLog = new JLabel("Console Log");
		lblConsoleLog.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblConsoleLog.setBounds(6, 585, 80, 16);
		contentPane.add(lblConsoleLog);
		
		lblAddGameNote = new JLabel("Add Game Note");
		lblAddGameNote.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblAddGameNote.setBounds(774, 24, 113, 16);
		contentPane.add(lblAddGameNote);
		
		textAreaGameNote = new JTextArea();
		textAreaGameNote.setEditable(true);
		textAreaGameNote.setLineWrap(true);
		textAreaGameNote.setBounds(706, 52, 238, 98);
		contentPane.add(textAreaGameNote);
		
		btnAddNote = new JButton("Add Note");
		btnAddNote.setFont(new Font("SansSerif", Font.BOLD, 12));
		
		btnAddNote.setBounds(770, 162, 117, 29);
		contentPane.add(btnAddNote);
		
		lblGameNotes = new JLabel("Game Notes");
		lblGameNotes.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblGameNotes.setBounds(784, 206, 80, 16);
		contentPane.add(lblGameNotes);
		
		textAreaNotesAdded = new JTextArea(5, 10);
		textAreaNotesAdded.setEditable(false);
		textAreaNotesAdded.setLineWrap(true);
		
		scrollPaneNotesAdded = new JScrollPane(textAreaNotesAdded);
		scrollPaneNotesAdded.setBounds(706, 234, 238, 111);
		contentPane.add(scrollPaneNotesAdded);
				
		menuBar = new JMenuBar();
		gameMenu = new JMenu("Options");
		gameMenu.setFont(new Font("SansSerif", Font.BOLD, 12));
		gameRulesMenuItem = new JMenuItem("Game Rules");
		seeCardDeckMenuItem = new JMenuItem("View Cards");
		gameMenu.add(gameRulesMenuItem);
		gameMenu.add(seeCardDeckMenuItem);
		menuBar.add(gameMenu);
		
		menuBar.setToolTipText("Options");
		menuBar.setBounds(6, 6, 132, 22);
		contentPane.add(menuBar);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
} //end class