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
import java.util.Collections;
import java.awt.Graphics;
import java.awt.Rectangle;

public class ClientFrame extends JFrame {

	private JPanel contentPane;
	private StringBuilder logStringBuilder, legendStringBuilder;
	private JTextArea log_text_area, textAreaGameNote, textAreaName, textAreaLegend;
	private JScrollPane scrollPane;
	private JLabel lblConsoleLog, lblAddGameNote, lblGameNotes, lblScoreCard, lblPlayerLegend;
	private JMenuBar menuBar;
	private JMenu gameMenu;
	private JMenuItem gameRulesMenuItem, seeCardDeckMenuItem;

	//private JTextArea yourCardsFrameInfo;
	private int cardTotalInHand;
	public int [] cardIdNumber = {0,0,0,0,0,0};
	int xPieceName = 130;
	int yPieceName = 22;
	
	private JLabel[] yourPlayerCards;
	private JPanel[] yourCardPanelArray;
	private ImageIcon yourCardsImages [];
	private JPanel yourcardPanel;
	private JPanel yourCardContentPane;

	Message messageRecieved;
	Client client;
	Player currentPlayer;
	String value;
	private final BoardPanel gameBoardPanel;
	private final ScoreCard scoreCardPanel;
    int xe = 0;
	int ye = 0;
	
	private String playerCardscheaker [] = {"Pipe", "Candle Stick","Revolver","Wrench","Knife", "Rope",
		   "Conservatory", "Billiard Room", "Study Room", "Hall", "Lounge", "Dining Room", "Kitchen", 
		   "Ballroom", "Library", "Mr. Green","Professor Plum" , "Mrs. White", "Colonel Mustard", 
		   "Miss Scarlet", "Mrs. Peacock"};

	private String  pullPlayerCardsImageArray [] = { "resources/Lead_Pipe.JPG", "resources/Candlestick.JPG", 
		   "resources/Revolver.JPG", "resources/Wrench.JPG", "resources/Knife.JPG", "resources/Rope.JPG", 
		   "resources/Conservatory.JPG", "resources/Billiard_Room.JPG","resources/Study.JPG","resources/Hall.JPG",
		   "resources/Lounge.JPG", "resources/Dinning_Room.JPG","resources/Kitchen.JPG","resources/BallRoom.JPG",
		   "resources/Library.JPG", "resources/green.png", "resources/plum.png", "resources/white.png",
		   "resources/mustard.png", "resources/scarlett.png", "resources/peacock.png"}; 
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

		textAreaName.append("Character: " + currentPlayer.getCharacter().getName());
		
		gameBoardPanel = new BoardPanel(clientConnection, this, currentPlayer);
		contentPane.add(gameBoardPanel);
		gameBoardPanel.setLayout(null);

		scoreCardPanel = new ScoreCard();
		scoreCardPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(scoreCardPanel);
		scoreCardPanel.setLayout(null);

		seeCardDeckMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent g) {
				class MyCards extends JFrame{
					public MyCards(){
						setResizable (true);
						setTitle("Your Cards");
						yourCardContentPane = new JPanel();
						yourCardContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
						setContentPane(yourCardContentPane);
						yourCardContentPane.setLayout(null);
						setBounds(100, 100, 400, 650);
						yourcardPanel = new JPanel();
						yourcardPanel.setBounds(6, 6, 388, 480);
						yourCardContentPane.add(yourcardPanel);
						yourcardPanel.setLayout(new GridLayout(3, 2));
						addingYourCardsToSee();
						setLocationRelativeTo(null);
						setVisible(true); 
					}
				}
				new MyCards();
			}
		});
									
		gameRulesMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				class GameRules extends JFrame{
					public GameRules() {
						setResizable(true);
						setTitle("Clue Game Rules");
						getContentPane().setLayout(new FlowLayout());
						setBounds(6,6,494,693);
						JPanel gameRulesPanel = new JPanel();
						gameRulesPanel.setBounds(6, 6, 450, 691);
						JTextArea textArea = new JTextArea(60,50);
						textArea.setLineWrap(true);
						textArea.setEditable(false);
						JScrollPane scrollPane = new JScrollPane(textArea);
						scrollPane.setPreferredSize(new Dimension(430, 671));
						gameRulesPanel.add(scrollPane);
						getContentPane().add(gameRulesPanel);
						textArea.setText(ClueGameConstants.gameRulesString);
						setLocationRelativeTo(null);
						setVisible(true);
					}
				} //end of inner game rules class
				new GameRules();
			}
		});
	} // end constructor
	
	public void paint(Graphics g2) {
		super.paint(g2);		
		for(ClueGameConstants.LEGEND legend : ClueGameConstants.LEGEND.values()) {
		 	g2.setColor(new Color(legend.getColor()));
         	Rectangle rectBounds = getBounds1(legend.getXCoordLegend(), legend.getYCoordLegend());
         	int rectXBounds = (int) rectBounds.getX(); 
         	int rectYBounds = (int) rectBounds.getY();
         	int rectBoundsHeight = (int) rectBounds.getHeight();
         	int rectBoundsWidth  = (int) rectBounds.getWidth();
		 	g2.fillRect(rectXBounds, rectYBounds, rectBoundsHeight, rectBoundsWidth);
		}
		g2.setColor(new Color(currentPlayer.getCharacter().getColor()));
         Rectangle rectBounds = getBounds1(xPieceName, yPieceName);
         int rectXBounds = (int) rectBounds.getX(); 
         int rectYBounds = (int) rectBounds.getY();
         int rectBoundsHeight = (int) rectBounds.getHeight();
         int rectBoundsWidth  = (int) rectBounds.getWidth();
		 g2.fillRect(rectXBounds, rectYBounds, rectBoundsHeight, rectBoundsWidth);
	}
	
	private Rectangle getBounds1(int x, int y) {
        return new Rectangle(x + 30,y + 16,18,18);
    }

	private void addingYourCardsToSee() {
		cardTotalInHand = gameBoardPanel.getPlayersCards().size();
		initPanelsInArrayYourCards(); // initial the panels
		addArrayPanelsYourCards(); // add the panels from array to the main panel
	}

	private void yourcards (){
	 for(int i = 0; i < cardTotalInHand; i++){
		 String yourhandofcards = gameBoardPanel.getPlayersCards().get(i).getName();
		 	for(int j = 0; j <= 20; j++){
	 			if (yourhandofcards.equals(playerCardscheaker[j])){
					cardIdNumber [i] = j;
				} 
	 		}
		}
	}
	
	private void initPanelsInArrayYourCards() {
		yourCardPanelArray = new JPanel[ClueGameConstants.MAX_CHARACTERS];
		yourPlayerCards = new JLabel[ClueGameConstants.MAX_CHARACTERS];
		yourCardsImages = new ImageIcon[ClueGameConstants.MAX_CHARACTERS];
		yourcards();

		for(int i = 0; i < 6; i++) {
			yourCardPanelArray[i] = new JPanel();
			yourCardPanelArray[i].setBackground(Color.BLUE);
		}
		
		for (int i = 0; i < cardTotalInHand; i++) {
			int imageIndex  = cardIdNumber[i];
			String imageStr = pullPlayerCardsImageArray[imageIndex];
			yourCardsImages[i] = new ImageIcon(getClass().getResource(imageStr));
			yourPlayerCards[i] = new JLabel(yourCardsImages[i]);
			yourCardPanelArray[i].add(yourPlayerCards[i]);
		}
	}

	private void addArrayPanelsYourCards() {
		for (JPanel panel : yourCardPanelArray) {
			yourcardPanel.add(panel);
		}
	}

	//add text to the console log
	protected void addToLogConsole(String input){
		logStringBuilder.append(input + "\n");
		log_text_area.setText(logStringBuilder.toString());
	}

	protected void addToLegend(String input){
		legendStringBuilder.append(input + "\n" + "\n");
		textAreaLegend.setText(legendStringBuilder.toString());
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 800);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 204, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		logStringBuilder  = new StringBuilder();
		legendStringBuilder = new StringBuilder();
		
		log_text_area = new JTextArea(10,60);
		log_text_area.setEditable(false);
		log_text_area.setLineWrap(true);
		
		scrollPane = new JScrollPane(log_text_area);
		scrollPane.setBounds(6, 613, 688, 159);
		contentPane.add(scrollPane);
		
		lblConsoleLog = new JLabel("Console Log");
		lblConsoleLog.setFont(new Font("Arial", Font.BOLD, 12));
		lblConsoleLog.setBounds(6, 585, 80, 16);
		contentPane.add(lblConsoleLog);
		
		lblAddGameNote = new JLabel("Add Game Note");
		lblAddGameNote.setFont(new Font("Arial", Font.BOLD, 12));
		lblAddGameNote.setBounds(770, 24, 113, 16);
		contentPane.add(lblAddGameNote);
		
		textAreaGameNote = new JTextArea();
		textAreaGameNote.setEditable(true);
		textAreaGameNote.setLineWrap(true);
		textAreaGameNote.setBounds(704, 52, 227, 98);
		contentPane.add(textAreaGameNote);

		menuBar = new JMenuBar();
		gameMenu = new JMenu("Options");
		gameMenu.setFont(new Font("SansSerif", Font.BOLD, 12));
		gameRulesMenuItem = new JMenuItem("Game Rules");
		seeCardDeckMenuItem = new JMenuItem("View Cards");
		gameMenu.add(gameRulesMenuItem);
		gameMenu.add(seeCardDeckMenuItem);
		menuBar.add(gameMenu);

		textAreaName = new JTextArea();
		textAreaName.setFont(new Font("Arial", Font.BOLD, 14));
		textAreaName.setBackground(new Color(0, 204, 204));
		textAreaName.setBounds(180, 8, 290, 20);
		textAreaName.setEditable(false);
		contentPane.add(textAreaName);
		
		textAreaLegend = new JTextArea();
		textAreaLegend.setFont(new Font("Arial", Font.BOLD, 11));
		textAreaLegend.setEditable(false);
		textAreaLegend.setBounds(740, 600, 171, 200);
		textAreaLegend.setBackground(new Color(0, 204, 204));
		contentPane.add(textAreaLegend);

		lblScoreCard = new JLabel("Score Card");
	    lblScoreCard.setFont(new Font("Arial", Font.BOLD, 12));
	    lblScoreCard.setBounds(785, 200, 90, 13);
		contentPane.add(lblScoreCard);
		
		lblPlayerLegend = new JLabel("Player Legend");
		lblPlayerLegend.setFont(new Font("Arial", Font.BOLD, 12));
	    lblPlayerLegend.setBounds(774, 560, 150, 13);
		contentPane.add(lblPlayerLegend);
		
		menuBar.setToolTipText("Options");
		menuBar.setBounds(6, 6, 132, 22);
		contentPane.add(menuBar);

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
} //end class