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
	private int noteCounter = 0;
	private JTextArea log_text_area, textAreaNotesAdded, textAreaGameNote;
	Message messageRecieved;
	Client client;
	RoomClick roomClick;
	Characters assignedCharacter;
	int rows = 24;
	int coloums = 25;
	String value;
	private final BoardPanel gameBoardPanel;

    int xe = 0;
    int ye = 0;

	/**
	 * Create the frame.
	 */
	public ClientFrame(Client player) {
		client = player;
		
		//setServerConnection(serverConnection);

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
		
		JScrollPane scrollPane = new JScrollPane(log_text_area);
		scrollPane.setBounds(6, 613, 688, 159);
		contentPane.add(scrollPane);
		
		JLabel lblConsoleLog = new JLabel("Console Log");
		lblConsoleLog.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblConsoleLog.setBounds(6, 585, 80, 16);
		contentPane.add(lblConsoleLog);
		
		JLabel lblAddGameNote = new JLabel("Add Game Note");
		lblAddGameNote.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblAddGameNote.setBounds(774, 24, 113, 16);
		contentPane.add(lblAddGameNote);
		
		textAreaGameNote = new JTextArea();
		textAreaGameNote.setEditable(true);
		textAreaGameNote.setLineWrap(true);
		textAreaGameNote.setBounds(706, 52, 238, 98);
		contentPane.add(textAreaGameNote);
		
		JButton btnAddNote = new JButton("Add Note");
		btnAddNote.setFont(new Font("SansSerif", Font.BOLD, 12));
		
		btnAddNote.setBounds(770, 162, 117, 29);
		contentPane.add(btnAddNote);
		
		JLabel lblGameNotes = new JLabel("Game Notes");
		lblGameNotes.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblGameNotes.setBounds(784, 206, 80, 16);
		contentPane.add(lblGameNotes);
		
		textAreaNotesAdded = new JTextArea(5, 10);
		textAreaNotesAdded.setEditable(false);
		textAreaNotesAdded.setLineWrap(true);
		
		JScrollPane scrollPaneNotesAdded = new JScrollPane(textAreaNotesAdded);
		scrollPaneNotesAdded.setBounds(706, 234, 238, 111);
		contentPane.add(scrollPaneNotesAdded);
				
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Options");
		gameMenu.setFont(new Font("SansSerif", Font.BOLD, 12));
		JMenuItem gameRulesMenuItem = new JMenuItem("Game Rules");
		gameMenu.add(gameRulesMenuItem);
		menuBar.add(gameMenu);
		
		menuBar.setToolTipText("Options");
		menuBar.setBounds(6, 6, 132, 22);
		contentPane.add(menuBar);
		
		
		//request and receive the character the player has chosen
		try {
			client.send(new Message(ClueGameConstants.REQUEST_PLAYERS_CHARACTER, null));
			messageRecieved = client.getMessage();
			if(messageRecieved.getData() == null) 
				System.out.println("No data");
			
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}
		
		//display the character properties in the console log
		assignedCharacter = (Characters) messageRecieved.getData();
		addToLogConsole(assignedCharacter.getName());
		xe = assignedCharacter.getxStarting();
		ye = assignedCharacter.getyStarting();
		String startPointsStr = "Starting points: " + xe + " " + ye;
		addToLogConsole(startPointsStr);

		gameBoardPanel = new BoardPanel(player, this, assignedCharacter);
		contentPane.add(gameBoardPanel);
		gameBoardPanel.setLayout(null);
		
		//display the character name in the console log
		//addToLogConsole(String.valueOf(messageRecieved.getData()));
	
		btnAddNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addToNotebook(textAreaGameNote.getText());
			}
		}); //end button action listener

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		//setVisible(true); 
	} 

	protected void addToLogConsole(String input){
		logStringBuilder.append(input + "\n");
		log_text_area.setText(logStringBuilder.toString());
	}
	
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
		//*client manager can update console log as well
} //end class