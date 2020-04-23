import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//import com.sun.security.ntlm.Server;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class CharacterSelectFrame extends JFrame {

	private final int MAX_CHARACTERS = 6;
	private final int REQUEST_AVAILABLE_CHARACTERS = 12300;
	
	private JPanel contentPane, characterPanel;
	private JPanel[] characterPanelArray;
	private JLabel[] characterImageLabel;
	private ImageIcon characterImageArray[];
	private Timer timer;
	private JButton btnAdvance;
	//FOR Testing
	private Color[] colorArray = {new Color(0,204,102), new Color(204,0,204), Color.WHITE,
			new Color(204,204,0), new Color(204,0,0), Color.BLUE };
	//FOR Testing
	private String imageNameArray[] = {"resources\\green.png", "resources\\plum.png", 
									   "resources\\white.png", "resources\\mustard.png", 
									   "resources\\scarlett.png", "resources\\peacock.png"};
	
	ServerConnection serverConnection;
	private JTextArea textAreaCharacterFrameInfo;
	private String imageNamesArray[] = {"Mr. Green", "Professor Plum", "Mrs. White", 
			               "Colonel Mustard", "Miss Scarlett", "Mrs. Peacock"};
	private int availableCharacters = 0;
	private boolean availableCharactersArray[] = {true, true, true, true, true, true};
	private boolean isValidCharacterSelected = false;
	
	
	public CharacterSelectFrame(ServerConnection serverConnection) {
		
		setServerConnection(serverConnection);
	
		//Build the  frame and its components
		setTitle("Character Select");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnAdvance = new JButton("Advance");
		btnAdvance.setBounds(126, 571, 117, 29);
		btnAdvance.setEnabled(false);
		contentPane.add(btnAdvance);
		
		characterPanel = new JPanel();
		characterPanel.setBounds(6, 6, 388, 480);
		contentPane.add(characterPanel);
		characterPanel.setLayout(new GridLayout(3, 2));
		
		textAreaCharacterFrameInfo = new JTextArea();
		textAreaCharacterFrameInfo.setEditable(false);
		textAreaCharacterFrameInfo.setBounds(6, 498, 388, 59);
		contentPane.add(textAreaCharacterFrameInfo);
		
		//Create, assign, fill the character panels
		initCharacterPanels();
		
		//At this point we should be connected to the server
		//but if we are not then reconnect or quit the game
		if(!serverConnection.isServerConnected())
			handleNotConnected();
		
		
		//availableCharacters = serverConnection.requestNumber(REQUEST_AVAILABLE_CHARACTERS);
		availableCharacters = 54;
		if(availableCharacters > 0) {
			updatePanelArrayUsedCharacters();
		}
		
		//Mouse Listener ---------------------------------
		characterPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int x = e.getX();
				int y = e.getY();
				int characterSelected = determinePanelClickedOn(x, y);
				
				//TODO: send character selected to server
				

				//The character is not available
				if(!availableCharactersArray[characterSelected - 1]) {
					textAreaCharacterFrameInfo.setText(imageNamesArray[ characterSelected - 1 ]);
					JOptionPane.showMessageDialog(null, "The character you selected is already choosen");
					btnAdvance.setEnabled(false);
				}
				else {
					textAreaCharacterFrameInfo.setText(imageNamesArray[ characterSelected - 1 ] +
													  " is available");
					btnAdvance.setEnabled(true);
				}
				
				//TODO: send character selected to server
				
				//TODO: receive data if character is available from server
				
				//TODO: Confirm with server that character is indeed available. 
	
			}
		});
		
	
		
		//Button listener -------------------------------------------------
		
		final ServerConnection serverConnectionPassed = serverConnection;
		
		btnAdvance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO 
				
				new ClientFrame(serverConnectionPassed).setVisible(true);
			}
		});

		setVisible(true);
	} //end constructor

	
	
	/*
	 *  Method to hide the characters that have already been 
	 *  selected and determine which characters are not 
	 *  available. 
	 */
	private void updatePanelArrayUsedCharacters() {
		
		for(int i = 0; i < MAX_CHARACTERS; i++) {
			boolean isBitSet = isNthBitSet(availableCharacters, i+1);
			if(isBitSet) {
				//Set the background color to black 
				characterPanelArray[i].setBackground(Color.BLACK);
				//Mark character as unavailable in array
				availableCharactersArray[i] = false;
			}
		}
	}
	
	/*
	 * Method to determine which of the 6 panels were clicked
	 * on based on parameters x and y (int x, int y) 
	 * coordinates from the mouse event handler.
	 * Returns integer value, 1 - 6, representing the panel 
	 * that was clicked on. 
	 */
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

	private void initCharacterPanels() {
		initPanelsInArray();  // initial the panels
		addPictureToPanels(); // add character pictures to the panels
		addArrayPanels();     // add the panels from array to the main panel
	}

	private void initPanelsInArray() {
		characterPanelArray = new JPanel[MAX_CHARACTERS];
		characterImageLabel = new JLabel[MAX_CHARACTERS];
		characterImageArray = new ImageIcon[MAX_CHARACTERS];
		for(int i = 0; i < MAX_CHARACTERS; i++) {
			characterPanelArray[i] = new JPanel();
			/* TODO uncomment
			characterImageArray[i] = new ImageIcon(imageNameArray[i]);
			characterImageLabel[i] = new JLabel(characterImageArray[i]); */
		}
	}
	
	
	private void addPictureToPanels() {
		for(int i = 0; i < MAX_CHARACTERS; i++) {
			characterPanelArray[i].setBackground(colorArray[i]);
			/*TODO Uncomment
			characterPanelArray[i].add(characterImageLabel[i]); */
		}
	}
	
	private void addArrayPanels() {
		for(JPanel panel : characterPanelArray) {
			characterPanel.add(panel);
		}
	}
	
	private boolean isNthBitSet(int number, int n) {
		
		return (((number >> (n - 1)) & 1) == 1);
	}
	
	
	private void showGameQuitMessageAndExitGame() {
		JOptionPane.showMessageDialog(null, "Choosen not to reconnect. Game will now close.");
		dispose();
		System.exit(1);
	}
	
	private void handleNotConnected() {
		while(!serverConnection.isServerConnected()) {
			//Not connected to server
			
			//Show error connection message
			int ans = JOptionPane.showConfirmDialog(null, 
								  "No server connection - Attempt to reconnect?",
								  "Server Connection Error", JOptionPane.YES_NO_OPTION);
			
			switch(ans) {
				case JOptionPane.YES_OPTION: 
					//Attempt to reconnect to the server
					String portNumStr = JOptionPane.showInputDialog(null,
										"Enter port number of the server",
										"Server Reconnection Attempt",
										JOptionPane.INFORMATION_MESSAGE);
					if(portNumStr == null) {
						showGameQuitMessageAndExitGame();
					}
					int portNumber = Integer.parseInt(portNumStr);
					serverConnection.reconnectToServer( portNumber );
					//TODO remove in final, FOR TESTING
					serverConnection.setServerConnected(true);
					break;
				case JOptionPane.NO_OPTION:
					showGameQuitMessageAndExitGame();
					break;
				case JOptionPane.CLOSED_OPTION:
					showGameQuitMessageAndExitGame();
					break;
				default: break;
			}
		}
	}
	
	
	public void setServerConnection(ServerConnection serverConnection) {
		this.serverConnection = serverConnection;
	}
	

} //end class