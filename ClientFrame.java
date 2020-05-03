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


public class ClientFrame extends JFrame {

	private JPanel contentPane;
	private StringBuilder noteStringBuilder;
	private int noteCounter = 0;
	private JTextArea log_text_area;
	Message messageRecieved;
	Client client;
	int rows = 24;
	int coloums = 25;

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
		
		JPanel BoardPanel = new JPanel();
		BoardPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		BoardPanel.setBackground(Color.PINK);
		BoardPanel.setBounds(6, 37, 688, 535);
		contentPane.add(BoardPanel);
		BoardPanel.setLayout(null);
		
		//***** determine x and y coordinates *****//
		
		JLabel lblNewLabel = new JLabel("");
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
			    	//int tileSelected = determineTile(x,y);
			    	log_text_area.setText(coordinates);
			    }
			}
	
			});

		lblNewLabel.setIcon(new ImageIcon("resources\\board.jpg"));
		lblNewLabel.setBounds(6, 6, 569, 523);
		BoardPanel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Suggest");
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnNewButton.setBounds(579, 349, 99, 30);
		BoardPanel.add(btnNewButton);
		
		JButton btnAccuse = new JButton("Accuse");
		btnAccuse.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnAccuse.setBounds(579, 389, 99, 30);
		BoardPanel.add(btnAccuse);
		
		JButton btnShortcut = new JButton("Shortcut");
		btnShortcut.setForeground(Color.BLACK);
		btnShortcut.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnShortcut.setBounds(579, 431, 99, 30);
		BoardPanel.add(btnShortcut);
		
		JButton btnEndTurn = new JButton("End Turn");
		btnEndTurn.setForeground(Color.RED);
		btnEndTurn.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnEndTurn.setBounds(579, 471, 99, 30);
		BoardPanel.add(btnEndTurn);
		
		JButton btnRollDice = new JButton("Roll Dice");
		btnRollDice.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnRollDice.setBounds(579, 306, 99, 30);
		BoardPanel.add(btnRollDice);
		
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
		
		final JTextArea textAreaGameNote = new JTextArea();
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
		
		final JTextArea textAreaNotesAdded = new JTextArea(5, 10);
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
		
		//FOR TESTING
		//log_text_area.setText(Integer.toString(serverConnection.getPortNumber()));
		
		JButton btnShowCards = new JButton("Show Cards");
		btnShowCards.setForeground(new Color(0, 0, 255));
		btnShowCards.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnShowCards.setBounds(774, 357, 117, 29);
		contentPane.add(btnShowCards);
		
		
		try {
			client.send(new Message(ClueGameConstants.REQUEST_PLAYER_NAME, null));
			messageRecieved = client.getMessage();
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(messageRecieved.getData() == null) {
			System.out.println("Data with player name is null");
		}
		
	
		log_text_area.setText("Player character is: " + String.valueOf(messageRecieved.getData()));
		

		btnAddNote.addActionListener(new ActionListener() {
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
		}); //end button action listener
	
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		//setVisible(true); 
		
	} 

	protected int clickLocation(int x, int y) 
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
		//*client manager can update console log as well
} //end class