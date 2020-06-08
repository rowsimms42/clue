/******************************************************
 * This class is the base class for the client side gui
 ******************************************************/

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
import java.util.Arrays;

public class ClientFrame extends JFrame {

    private JPanel contentPane;
    private StringBuilder logStringBuilder, legendStringBuilder;
    private JTextArea log_text_area, textAreaGameNote, textAreaName, textAreaLegend;
    private JScrollPane scrollPane;
    private JLabel lblConsoleLog, lblAddGameNote, lblGameNotes, lblScoreCard, lblPlayerLegend;
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem gameRulesMenuItem, seeCardDeckMenuItem;
    private int cardTotalInHand;
    public int [] cardIdNumberArray = {-1,-1,-1,-1,-1,-1};
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

    ArrayList<String> playerCardsCheakerList;

    private final String[] pullPlayerCardsImageArray = { "resources/Lead_Pipe.JPG", "resources/Candlestick.JPG",
            "resources/Revolver.JPG", "resources/Wrench.JPG", "resources/Knife.JPG", "resources/Rope.JPG",
            "resources/Conservatory.JPG", "resources/Billiard_Room.JPG","resources/Library.JPG","resources/Study.JPG",
            "resources/Ballroom.JPG", "resources/Hall.JPG","resources/Lounge.JPG","resources/Kitchen.JPG",
            "resources/Dinning_room.JPG", "resources/green.png", "resources/plum.png", "resources/white.png",
            "resources/mustard.png", "resources/scarlett.png", "resources/peacock.png"};
    /**
     * Create the frame.
     */
    public ClientFrame(Client clientConnection) {
        client = clientConnection;
        playerCardsCheakerList = getCardsList();

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
                        getContentPane().setBackground(new Color(255, 255, 255));
		                setResizable(true);
		                setTitle("Clue Game Rules");
		                setBounds(6,6,494,693);
		                getContentPane().setLayout(null);
		                JPanel gameRulesPanel = new JPanel();
		                gameRulesPanel.setBounds(239, 5, 1, 1);
		                gameRulesPanel.setLayout(null);
		                getContentPane().add(gameRulesPanel);
		                JLabel lblRulesObjective = new JLabel("Objective");
		                lblRulesObjective.setFont(new Font("Bookman Old Style", Font.BOLD, 16));
		                lblRulesObjective.setBounds(187, 16, 105, 29);
		                getContentPane().add(lblRulesObjective);
		                JTextArea rulesObjectiveTxtArea = new JTextArea();
		                rulesObjectiveTxtArea.setEditable(false);
		                rulesObjectiveTxtArea.setFont(new Font("Arial", Font.PLAIN, 12));
		                rulesObjectiveTxtArea.setLineWrap(true);
		                rulesObjectiveTxtArea.setText(ClueGameConstants.gameRulesObjective);
		                rulesObjectiveTxtArea.setBackground(new Color(255, 255, 255));
		                rulesObjectiveTxtArea.setBounds(10, 44, 460, 57);
		                getContentPane().add(rulesObjectiveTxtArea);
		                JLabel lblRulesGamePlay = new JLabel("Game Play");
		                lblRulesGamePlay.setFont(new Font("Bookman Old Style", Font.BOLD, 16));
		                lblRulesGamePlay.setBounds(181, 95, 129, 24);
		                getContentPane().add(lblRulesGamePlay);	
		                JTextArea rulesGamePlayTxtArea = new JTextArea();
		                rulesGamePlayTxtArea.setText(ClueGameConstants.gameRulesGamePlay);
		                rulesGamePlayTxtArea.setFont(new Font("Arial", Font.PLAIN, 12));
		                rulesGamePlayTxtArea.setEditable(false);
		                rulesGamePlayTxtArea.setBackground(new Color(255, 255, 255));
		                rulesGamePlayTxtArea.setBounds(10, 122, 460, 137);
		                getContentPane().add(rulesGamePlayTxtArea);
		                JLabel lblRulesSuggestion = new JLabel("Suggestion");
		                lblRulesSuggestion.setFont(new Font("Bookman Old Style", Font.BOLD, 16));
		                lblRulesSuggestion.setBounds(181, 269, 129, 24);
		                getContentPane().add(lblRulesSuggestion);
		                JTextArea rulesSuggestionTxtArea = new JTextArea();
		                rulesSuggestionTxtArea.setEditable(false);
		                rulesSuggestionTxtArea.setFont(new Font("Arial", Font.PLAIN, 12));
		                rulesSuggestionTxtArea.setText(ClueGameConstants.gameRulesSuggestion);
		                rulesSuggestionTxtArea.setBounds(10, 295, 460, 152);
		                getContentPane().add(rulesSuggestionTxtArea);
		                JTextArea rulesAccusationTxtArea = new JTextArea();
		                rulesAccusationTxtArea.setText(ClueGameConstants.gameRulesAccusing);
		                rulesAccusationTxtArea.setEditable(false);
		                rulesAccusationTxtArea.setFont(new Font("Arial", Font.PLAIN, 12));
		                rulesAccusationTxtArea.setBounds(10, 481, 460, 125);
		                getContentPane().add(rulesAccusationTxtArea);
		                JLabel lblRulesAccusation = new JLabel("Accusation");
		                lblRulesAccusation.setFont(new Font("Bookman Old Style", Font.BOLD, 16));
		                lblRulesAccusation.setBounds(181, 457, 129, 24);
		                getContentPane().add(lblRulesAccusation);
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
            cardIdNumberArray[i] = findCardInArray(yourhandofcards);;
        }
    }

    public int findCardInArray(String cardToFind){
        for(int i = 0; i < playerCardsCheakerList.size(); i++){
            String cardInList = playerCardsCheakerList.get(i);
            if(cardInList.equals(cardToFind))
                return i;
        }
        return -1;
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
            int imageIndex  = cardIdNumberArray[i];
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
        logStringBuilder.append(input).append("\n");
        log_text_area.setText(logStringBuilder.toString());
    }

    protected void addToLegend(String input){
        legendStringBuilder.append(input).append("\n").append("\n");
        textAreaLegend.setText(legendStringBuilder.toString());
    }

    private ArrayList<String> getCardsList(){
        ArrayList<String> allCardsList = new ArrayList<>();
        String[] weaponCards = ClueGameConstants.WEAPON_NAMES_ARRAY;
        String[] characterCards = ClueGameConstants.CHARACTER_NAMES_ARRAY;
        String[] roomCards = ClueGameConstants.ROOM_NAMES_ARRAY;
        allCardsList.addAll(Arrays.asList(weaponCards));
        allCardsList.addAll(Arrays.asList(roomCards));
        allCardsList.addAll(Arrays.asList(characterCards));
        return allCardsList;
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
