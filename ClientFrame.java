import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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

public class ClientFrame extends JFrame {

	private JPanel contentPane;
	private StringBuilder noteStringBuilder, logStringBuilder;
	private JTextArea log_text_area, textAreaNotesAdded, textAreaGameNote;
	private JScrollPane scrollPane, scrollPaneNotesAdded;
	private JLabel lblConsoleLog, lblAddGameNote, lblGameNotes;
	private JMenuBar menuBar;
	private JMenu gameMenu;
	private JMenuItem gameRulesMenuItem, seeCardDeckMenuItem;
	private JButton btnAddNote;
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
		
		//----ask if we can start
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
		
		gameRulesMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				class GameRules extends JFrame{
					public GameRules() {
						setResizable(false);
						setTitle("Clue Game Rules");
						getContentPane().setLayout(new FlowLayout());
						setBounds(6,6,329,493);
						JPanel gameRulesPanel = new JPanel();
						gameRulesPanel.setBounds(6, 6, 327, 491);
						JTextArea textArea = new JTextArea(40,30);
						textArea.setLineWrap(true);
						textArea.setEditable(false);
						JScrollPane scrollPane = new JScrollPane(textArea);
						scrollPane.setPreferredSize(new Dimension(307, 471));
						gameRulesPanel.add(scrollPane);
						getContentPane().add(gameRulesPanel);
						textArea.setText(
						"Object of the Game"+
						"Mr. Boddy is found death in one of the rooms of his mansion." +
						"The players must determine the answers to these three questions: Who killed him? Where? and with What Weapon?"+
						" "+
						"	Game Play"+
						"Miss Scarlett opens the game, the turns continue clockwise around the table."+
						"On each turn, a player try to reach a different room of the mansion to investigate."+
						"To start your turn, move your token by rolling the die or use a Secret Passage when you are in a corner room."+
						"If you roll the die, you move your token that many spaces:"+
						"Horizontally or vertically, forward or backward, but not diagonally."+
						"You are not allowed to enter the same space twice on the same turn."+
						"You may not enter on a space that is already occupied by another player."+
						"If you move through a Secret Passage, you don't need to roll and you can move immediately to the other room. This ends your movement."+
						"It is possible that your opponents might block any and all doors and trap you in a room. In that case, you have to wait for someone to move or un-block a door to leave!"+
						" "+
						" "+
						"Suggestions or Guess"+
						"When you enter a room, you can make a suggestion by naming a suspect, murder weapon and the room you just entered."+
						"e.g. 'The crime was committed by Mr. Green in the lounge with a knife'."+
						"Then your opponents (starting by the left player) must (if possible) prove that your suggestion is false by showing you one card that matches your suggestion."+
						"If the first player can't disprove, the next player must try it, etc... until all players have passed."+
						"As soon as someone shows you one of the cards, it is prooved that it can't be in the envelope and you can cross it off in your notebook as a possibility."+
						"If no one is able to prove your suggestion false, you may either end your turn or make an accusation."+
						"Note: the suggestion is always made for the room your token is at that moment."+
						" "+
						"Accusing"+
						"If you think you have solved the crime by deduction, you can end your turn by making an accusation and name any three elements."+
						"you can say: I accuse suspect of committing the crime in the room with the weapon."+
						"Then, you must look secretly at the cards in the envelope to check if your suggestion is correct."+
						"If you are correct, you can place the 3 cards face-up on the table to prove it and you won the game!"+
						" "+
						"Note: You can only make one accusation during a game. If your accusation is wrong, you lost and you must leave the game and board"
						
						);

						setLocationRelativeTo(null);
						setVisible(true);
					}
				} //end of inner game rules class
				new GameRules();
			}
		});

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