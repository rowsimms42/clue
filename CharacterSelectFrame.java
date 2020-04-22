import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class CharacterSelectFrame extends JFrame  {

	private JPanel contentPane, characterPanel;
	private JPanel[] characterPanelArray;
	private final int MAX_CHARACTERS = 6;
	private JLabel[] label;
	private Color[] colorArray = {new Color(0,204,102), new Color(204,0,204), Color.WHITE,
		new Color(204,204,0), new Color(204,0,0), Color.BLUE };
	private String imageName[] = {"Mr. Green", "Professor Plum", "Mrs. White", "Colonel Mustard", "Miss Scarlett", "Mrs. Peacock"};
	private String imageNameArray[] = {"resources\\green.png", "resources\\plum.png", "resources\\white.png", "resources\\mustard.png", "resources\\scarlett.png", "resources\\peacock.png"};
	private ImageIcon[] characterImageArray;
	ServerConnection serverConnection;
	private JTextArea textAreaCharacterFrameInfo;


	/**
	 * Create the frame.
	 */
	public CharacterSelectFrame(ServerConnection serverConnection) {
		//assign the server object
		setServerConnection(serverConnection);
		
		//check if the server was connected
		//if(!serverConnection.isClientConnectedToServer()) {
			//TODO
		

	//	}
		
		setTitle("Character Select");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAdvance = new JButton("Advance");
		
		btnAdvance.setBounds(126, 571, 117, 29);
		contentPane.add(btnAdvance);
		
		characterPanel = new JPanel();
		characterPanel.setBounds(6, 6, 388, 480);
		contentPane.add(characterPanel);
		characterPanel.setLayout(new GridLayout(3, 2));
		
		textAreaCharacterFrameInfo = new JTextArea();
		textAreaCharacterFrameInfo.setEditable(false);
		textAreaCharacterFrameInfo.setBounds(6, 498, 388, 59);
		contentPane.add(textAreaCharacterFrameInfo);
		//textAreaCharacterFrameInfo.setText(serverConnection.getServerName());		
		
		initCharacterPanels();
		
		characterPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int x = e.getX();
				int y = e.getY();
				int panelSelected = determinePanelClickedOn(x, y);
				textAreaCharacterFrameInfo.setText(imageName[ panelSelected - 1 ]);
			}
		});
		
		
		setVisible(true);
		
	}
	

	private int determinePanelClickedOn(int x, int y) {
		
		if(x <= 193 && y <= 160) //test for the top left panel
			return 1;
		else if(x >= 194 && y <= 160) //test for top right panel
			return 2;
		else if(x <= 193 && (y >= 160 && y <= 320)) //test for middle left panel
			return 3;
		else if(x >= 194 && (y >= 160 && y <= 320)) //test for middle right panel
			return 4;
		else if(x <= 193 && y >= 321) //test for bottom left panel
			return 5;
		else //bottom right panel
			return 6; 
	}

	public void setServerConnection(ServerConnection serverConnection) {
		this.serverConnection = serverConnection;
	}

	private void initCharacterPanels() {
		initPanelsInArray();  // initial the panels
		//initImageArray();
		addPictureToPanels(); // add character pictures to the panels
		addArrayPanels();     // add the panels from array to the main panel
	}

	private void initPanelsInArray() {
		characterPanelArray = new JPanel[6];
		label = new JLabel[6];
		characterImageArray = new ImageIcon[6];
		for(int i = 0; i < MAX_CHARACTERS; i++) {
			characterPanelArray[i] = new JPanel();
			characterImageArray[i] = new ImageIcon(imageNameArray[i]);
			label[i] = new JLabel(characterImageArray[i]);
		}
	}
	
	private void addPictureToPanels() {
		for(int i = 0; i < MAX_CHARACTERS; i++) {
			characterPanelArray[i].setBackground(colorArray[i]);
			characterPanelArray[i].add(label[i]);
		}
		/*
		characterPanelArray[0].setBackground(Color.CYAN);
		characterPanelArray[1].setBackground(Color.RED);
		characterPanelArray[2].setBackground(Color.YELLOW);
		characterPanelArray[3].setBackground(Color.ORANGE);
		characterPanelArray[4].setBackground(Color.PINK);
		characterPanelArray[5].setBackground(Color.BLACK); */
		
		/*
		for(JPanel panel : characterPanelArray) {
			//set the panels background
		} */
	}
	
	private void addArrayPanels() {
		for(JPanel panel : characterPanelArray) {
			characterPanel.add(panel);
		}
	}

} //end class
