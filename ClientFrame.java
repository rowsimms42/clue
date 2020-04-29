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
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ClientFrame extends JFrame {

	private JPanel contentPane;
	private StringBuilder noteStringBuilder;
	private int noteCounter = 0;
	private JTextArea log_text_area;
	//private ClientManager cm;
	//private ServerConnection serverConnection;

	/**
	 * Create the frame.
	 */
	public ClientFrame(Client player) {
		
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
		
		JLabel lblNewLabel = new JLabel("");
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
		btnShortcut.setFont(new Font("SansSerif", Font.BOLD, 13));
		btnShortcut.setBounds(579, 431, 99, 30);
		BoardPanel.add(btnShortcut);
		
		JButton btnEndTurn = new JButton("End Turn");
		btnEndTurn.setForeground(Color.RED);
		btnEndTurn.setFont(new Font("SansSerif", Font.BOLD, 13));
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
		
		
	} //end constructor
		
	
	
	
	/*
	 * Client Manager can update the console log as well 
	 */
	
	
	
} //end class