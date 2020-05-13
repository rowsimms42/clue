import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.UnknownHostException;

//import com.sun.security.ntlm.Server;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

//import old server classes.ServerConnection;

public class CharacterSelectFrame extends JFrame {

	private JPanel contentPane, characterPanel;
	private JPanel[] characterPanelArray;
	private JLabel[] characterImageLabel;
	private ImageIcon characterImageArray[];
	private Timer timer;
	private JButton btnAdvance;
	// FOR Testing
	private Color[] colorArray = { new Color(0, 204, 102), new Color(204, 0, 204), Color.WHITE, new Color(204, 204, 0),
			new Color(204, 0, 0), Color.BLUE };
	// FOR Testing
	private String imageNameArray[] = { "resources/green.png", "resources/plum.png", "resources/white.png",
			"resources/mustard.png", "resources/scarlett.png", "resources/peacock.png" };

	Client clientConnection;
	Message messageReceived = null;;
	private JTextArea textAreaCharacterFrameInfo;
	private int characterSelected = 0;
	private int availableCharacters = 0;
	private boolean availableCharactersArray[] = { true, true, true, true, true, true };
	private boolean isAvailableCharacter = false, isCharacterIndeedAvailable = false;
	

	public CharacterSelectFrame(Client clientConnection) throws IOException, ClassNotFoundException {
		// the player is the new Thread that connects with the server
		this.clientConnection = clientConnection; 

		//initialize all the gui components
		initComponents();
		
		// At this point we should be connected to the server
		// but if we are not then reconnect or quit the game
		if (!clientConnection.isPlayerConnected()) {
			handleNotConnected();
		}

		// Request available characters from the server
		requestAvailableCharactersAndUpdate();

		// handle if not all characters are available for selection
		if (availableCharacters > 0) {
			updatePanelArrayUsedCharacters();
		}

		//Request the player id from the server and display in text box
		clientConnection.send(new Message(ClueGameConstants.REQUEST_PLAYER_ID, null));
		messageReceived = clientConnection.getMessage();
		textAreaCharacterFrameInfo.setText("Player ID is: " + messageReceived.getData().toString());
		
		// Mouse Listener ---------------------------------
		characterPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//determine which character was selected based on which panel was clicked on.
				//characterSelected will be values 1 - 6.
				int x_coordinate = e.getX();
				int y_coordinate = e.getY();
				characterSelected = determinePanelClickedOn(x_coordinate, y_coordinate);

				// send character selected to server for availability check
				try {
					clientConnection.send(new Message(ClueGameConstants.REQUEST_IS_SELECTED_CHARACTER_AVAILABLE,
							          Integer.valueOf(characterSelected)));
					
					messageReceived = clientConnection.getMessage();
					isAvailableCharacter = (Boolean) messageReceived.getData();
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
				
				
				if (!isAvailableCharacter) {
					// ----Character became unavailable-----
					try {
						showCharacterUsedandNewAvailableRequest();
					} catch (ClassNotFoundException | IOException e2) {
						e2.printStackTrace();
					}
				} else {
					// -----Character is still available -------
					textAreaCharacterFrameInfo.setText(
							ClueGameConstants.CHARACTER_NAMES_ARRAY[characterSelected - 1] + " is available");
					btnAdvance.setEnabled(true);
				}
			}
		}); // end Panel mouse listener

		// Button listener -------------------------------------------------
		btnAdvance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// send message to re-confirm with the server that the selected character is
				// indeed available
				try {
					clientConnection.send(new Message(ClueGameConstants.REQUEST_INDEED_CHARACTER_AVAILABLE,
								Integer.valueOf(characterSelected)));	
					// receive message from server with character availability confirmation
					messageReceived = clientConnection.getMessage();
					isCharacterIndeedAvailable = (Boolean) messageReceived.getData();
				} catch (ClassNotFoundException | IOException e2) {
					e2.printStackTrace();
				}
				
				if (isCharacterIndeedAvailable) {
					// ---selected character confirmed to be available-----
					// character will be chosen, tell server to mark this character as used
					try {
						clientConnection.send( new Message(ClueGameConstants.REQUEST_MARK_CHARACTER_AS_TAKEN,
												Integer.valueOf(characterSelected)));
						messageReceived = clientConnection.getMessage();
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}
					
					final Client clientConnectionPassed = clientConnection;
					setVisible(false);
					// transition to the next frame
					new ClientFrame(clientConnectionPassed);
				} else {
					// ----selected character was confirmed not to be available---
					// Show JOptionpane message window that character is already chosen
					String characterAlreadySelectedStr = 
							ClueGameConstants.CHARACTER_NAMES_ARRAY[characterSelected - 1]
							+ " has already been selected by another character. Choose another"
							+ " available character.";
					JOptionPane.showMessageDialog(null, characterAlreadySelectedStr);

					// request again all available/unavailable characters
					try {
						requestAvailableCharactersAndUpdate();
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}
					// update panel with available/unavailable characters
					updatePanelArrayUsedCharacters();
					// disable this button
					btnAdvance.setEnabled(false);
				}
			}
		}); // end button listener
	} // end constructor

	private void requestAvailableCharactersAndUpdate() throws IOException, ClassNotFoundException {
		// request again all available/unavailable characters
		clientConnection.send(new Message(ClueGameConstants.REQUEST_AVAILABLE_CHARACTERS, null));
		// Receive message with available connections from the server
		messageReceived = clientConnection.getMessage();
		// Get available connections from the message
		availableCharacters = (Integer) messageReceived.getData();
	}

	/*
	 * Method to hide the characters that have already been selected and determine
	 * which characters are not available.
	 */
	private void updatePanelArrayUsedCharacters() {

		for (int i = 0; i < ClueGameConstants.MAX_CHARACTERS; i++) {
			boolean isBitSet = isNthBitSet(availableCharacters, i + 1);
			if (isBitSet) {
				ImageIcon unavail = new ImageIcon(getClass().getResource("resources/unavailable.png"));
				JLabel unavailLabel = new JLabel(unavail);
				characterPanelArray[i].remove(characterImageLabel[i]);
				characterPanelArray[i].add(unavailLabel);
				// Mark character as unavailable in array
				availableCharactersArray[i] = false;
			}
		}
	}

	/*
	 * Method to determine which of the 6 panels were clicked on based on parameters
	 * x and y (int x, int y) coordinates from the mouse event handler. Returns
	 * integer value, 1 - 6, representing the panel that was clicked on.
	 */
	private int determinePanelClickedOn(int x, int y) {

		if (x <= 193 && y <= 160) // test for the top left panel
			return 1;
		else if (x >= 194 && y <= 160) // test for top right panel
			return 2;
		else if (x <= 193 && (y >= 160 && y <= 320)) // test for middle left panel
			return 3;
		else if (x >= 194 && (y >= 160 && y <= 320)) // test for middle right panel
			return 4;
		else if (x <= 193 && y >= 321) // test for bottom left panel
			return 5;
		else // bottom right panel
			return 6;
	}

	private void initCharacterPanels() {
		initPanelsInArray(); // initial the panels
		addPictureToPanels(); // add character pictures to the panels
		addArrayPanels(); // add the panels from array to the main panel
	}

	private void initPanelsInArray() {
		characterPanelArray = new JPanel[ClueGameConstants.MAX_CHARACTERS];
		characterImageLabel = new JLabel[ClueGameConstants.MAX_CHARACTERS];
		characterImageArray = new ImageIcon[ClueGameConstants.MAX_CHARACTERS];
		for (int i = 0; i < ClueGameConstants.MAX_CHARACTERS; i++) {
			characterPanelArray[i] = new JPanel();
			characterImageArray[i] = new ImageIcon(getClass().getResource(imageNameArray[i]));
			//characterImageLabel[i] = new JLabel(ClueGameConstants.CHARACTER_NAMES_ARRAY[i]);
			characterImageLabel[i] = new JLabel(characterImageArray[i]);

		}
	}

	private void addPictureToPanels() {
		for (int i = 0; i < ClueGameConstants.MAX_CHARACTERS; i++) {
			characterPanelArray[i].setBackground(colorArray[i]);
			characterPanelArray[i].add(characterImageLabel[i]);
		}
	}

	private void addArrayPanels() {
		for (JPanel panel : characterPanelArray) {
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

	private void handleNotConnected() throws UnknownHostException, ClassNotFoundException {
		while (!clientConnection.isPlayerConnected()) {
			// ----Not connected to server --------
			// Show error connection message
			int ans = JOptionPane.showConfirmDialog(null, "No server connection - Attempt to reconnect?",
					"Server Connection Error", JOptionPane.YES_NO_OPTION);

			switch (ans) {
				case JOptionPane.YES_OPTION:
					// Attempt to reconnect to the server
					String portNumStr = JOptionPane.showInputDialog(null, "Enter port number of the server", // Don't
																												// need
																												// port
																												// number
																												// anymore,
																												// it is
																												// in
																					// ClueGameConstants
							"Server Reconnection Attempt", JOptionPane.INFORMATION_MESSAGE);
					if (portNumStr == null) {
						showGameQuitMessageAndExitGame();
					}

					setServerConnection(startNewConnection());
					break;
				case JOptionPane.NO_OPTION:
					showGameQuitMessageAndExitGame();
					break;
				case JOptionPane.CLOSED_OPTION:
					showGameQuitMessageAndExitGame();
					break;
				default:
					break;
			}
		}
	}

	private void showCharacterUsedandNewAvailableRequest() throws ClassNotFoundException, IOException {

		textAreaCharacterFrameInfo.setText(ClueGameConstants.CHARACTER_NAMES_ARRAY[characterSelected - 1]);
		JOptionPane.showMessageDialog(null, "The character you selected is already choosen");
		btnAdvance.setEnabled(false);
		// request again all available/unavailable characters
		requestAvailableCharactersAndUpdate();
		// update panel with available/unavailable characters
		updatePanelArrayUsedCharacters();
	}

	public void setServerConnection(Client client) {
		this.clientConnection = client;
	}

	public Client startNewConnection() throws UnknownHostException, ClassNotFoundException {
		Client player = new Client();
		return player;
	}
	
	private void initComponents() {
		// Build the frame and its components
		setTitle("Character Select");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//create advance button and set properties
		btnAdvance = new JButton("Advance");
		btnAdvance.setBounds(126, 571, 117, 29);
		btnAdvance.setEnabled(false);
		contentPane.add(btnAdvance);
		//create character panel and set properties
		characterPanel = new JPanel();
		characterPanel.setBounds(6, 6, 388, 480);
		contentPane.add(characterPanel);
		characterPanel.setLayout(new GridLayout(3, 2));
		//create the character text area and set properties
		textAreaCharacterFrameInfo = new JTextArea();
		textAreaCharacterFrameInfo.setEditable(false);
		textAreaCharacterFrameInfo.setBounds(6, 498, 388, 59);
		contentPane.add(textAreaCharacterFrameInfo);
		// Create, assign, fill the character panels
		initCharacterPanels();
		// set the character select frame to visibility to true
		setVisible(true); 
	}

} //end class