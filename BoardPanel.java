import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.*;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.plaf.basic.BasicArrowButton;

public class BoardPanel extends JPanel {

    ImageIcon gameboard;
    Timer startGameTimer, currentTurnTimer;
    String value;
    Client client;
    ClientFrame clientFrame;
    Player currentPlayer; //Characters now in Player class. Access specific character by currentPlayer.getCharacter(). ...
    Player tempPlayer;
    Message messageReceived;
    final int WEST = 0, EAST = 1, NORTH = 2, SOUTH = 3;
    final int ENTER_ROOM = 0;
    String btnValues;
    BasicArrowButton[] movementButtons;
    JLabel boardLabel;
    JButton[] enterButton, roomButtons; //to hold buttons in room
    private JTextArea textAreaDice;
    int diceRollValue = 1, movementAmount = 0 ,previousMovement = 0;
    char[] cArray_movement_enter;
    int xC = 0; //x coordinate for drawing on board
    int yC = 0; //y coordinate for drawing on board
    int currentXgrid = 0; //x coordinate location for tile grid. this x coord is sent to server
    int currentYgrid = 0; //y coordinate location for tile grid. this y coord is sent to server
    HashMap<Long, Player> playerMap;
    boolean isPlayerCurrentTurn = false;
    boolean isGameStarted = false, isCurrentPlayerGoFirst = false;
    private JButton btnExitRoom, btnSuggest, btnAccuse, btnShortcut, btnEndTurn, btnRollDice, btnStartGame;
    int turnTimerUpdate = 1, startTimerUpdate=1, playerNumberUpdate = 1, tempNum = 0;//testing for timer
    int numOfPlayers = 0, tempNumPlayers = 0, buttonRollLimit = 1, currentTurnCount = 0;
    int currentInRoomNumber = 0;
    ArrayList<Card> playersDeck;
    ArrayList<Characters> nonPlayingCharList;
    ArrayList<String> legendList;
    ClientRequestManager crm;
    BoardPanelHelper bph;
    String[] reqBtnArray;
    String[] roomExitPictureStrs = {"resources/conservatoryExit.png", "resources/billardExit.png",
            "resources/libraryExit.png", "resources/studyExit.png", "resources/ballroomExit.png",
            "resources/hallExit.png", "resources/loungeExit.png", "resources/kitchenExit.png",
            "resources/diningroomExit.png"};

    //Hashtable roomExitPictures;
    int counterForShortCut = 0, enterRoomCounter = 0;
    boolean inShortcutRoom = false, inRoom = false;

    public BoardPanel(Client clientConnection, ClientFrame clientFrame, Player player) {
        crm = new ClientRequestManager(clientConnection);
        bph = new BoardPanelHelper();
        this.clientFrame = clientFrame;
        currentPlayer = player;

        initComponents(); //initiate all but board

        JLabel lblNewLabel = new JLabel("");
        gameboard = new ImageIcon(getClass().getResource("resources/board.jpg"));
        int w = gameboard.getIconWidth();
        int h = gameboard.getIconHeight();
        setPreferredSize(new Dimension(w, h));
        this.add(lblNewLabel);
        lblNewLabel.setBounds(6, 6, 569, 523);

        xC = currentPlayer.getCharacter().getxStarting() * 21;
        yC = currentPlayer.getCharacter().getyStarting() * 20;

        currentXgrid = currentPlayer.getCharacter().getxStarting();
        currentYgrid = currentPlayer.getCharacter().getyStarting();
        repaint();

        isGameStarted = false;
        disableButtons(movementButtons);
        btnStartGame.setEnabled(false);
        btnRollDice.setEnabled(false);
        btnEndTurn.setEnabled(false);
        btnSuggest.setEnabled(false);
        btnAccuse.setEnabled(false);
        btnShortcut.setEnabled(false);
        btnExitRoom.setEnabled(false);

        //Start game timer
        clientFrame.addToLogConsole("Waiting for other players to join the game.........");
        //if (!isGameStarted) {
        //startGameTimer.start();
        startGameTimer =  new Timer(2000,new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (startTimerUpdate==1) {
                    clientFrame.addToLogConsole("Start timer running..."); //for testing
                    startTimerUpdate--;
                }

                boolean canStart = crm.requestIfPlayerCanStartGame();

                if (numOfPlayers >= 3 && playerNumberUpdate == 1 && canStart) {
                    clientFrame.addToLogConsole("UPDATE: enough players have joined to start game");
                    playerNumberUpdate--;
                    btnStartGame.setEnabled(true);
                }
                else if(numOfPlayers >= 3 && playerNumberUpdate == 1 && !canStart) {
                    clientFrame.addToLogConsole("UPDATE: enough players have joined to start game and for player 1 to start it");
                    playerNumberUpdate--;
                }

                tempNumPlayers = playerMap.size();
                if (tempNumPlayers > numOfPlayers) {
                    clientFrame.addToLogConsole("Number of players: " + tempNumPlayers);
                    numOfPlayers = tempNumPlayers;
                }

                playerMap = crm.requestPlayerMap();
                repaint();

                boolean isHasGameStarted = crm.requestIfGameHasStarted();
                clientFrame.addToLogConsole("Has the game started: " + isHasGameStarted);
                if(isHasGameStarted) {
                    isGameStarted = true;
                    nonPlayingCharList = crm.requestListNonPlayingChars();
                    buildNamesForLegend(nonPlayingCharList);
                    playersDeck = crm.requestPlayersCardDeck();
                    printCardsInPlayersDeck(playersDeck);
                    startGameTimer.stop();
                    currentTurnTimer.start();
                }
            }
        });
        startGameTimer.setRepeats(true); //timer repeats every 2 seconds
        startGameTimer.start();

        //request movement options at launch
        reqBtnArray = crm.requestBtns(currentXgrid, currentYgrid);
        printAndAssignBtnsArray(reqBtnArray);
        //enabled or disable movement buttons at launch
        enableOrdisableBtns(movementButtons);
        //movement buttons disabled
        disableButtons(movementButtons);
        //request initial player map (for drawing)
        playerMap = crm.requestPlayerMap();
        repaint();

        //current turn timer
        currentTurnTimer =  new Timer(2000,new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (turnTimerUpdate!=0) {
                    clientFrame.addToLogConsole("Waiting for your turn..."); //for testing
                    turnTimerUpdate--;
                }

                if(currentTurnCount==0) {
                    isCurrentPlayerGoFirst = crm.requestDoesCurrentPlayerGoFirst();
                    playerMap = crm.requestPlayerMap();
                    repaint();

                    if(isCurrentPlayerGoFirst) {
                        currentTurnTimer.stop();
                        clientFrame.addToLogConsole("UPDATE - It's now your turn.");
                        turnTimerUpdate = 1;
                        reqBtnArray = crm.requestBtns(currentXgrid, currentYgrid);
                        printAndAssignBtnsArray(reqBtnArray);
                        enableOrdisableBtns(movementButtons);
                        disableButtons(movementButtons);
                        btnRollDice.setEnabled(true);
                        buttonRollLimit = 1;
                    }
                    currentTurnCount++;
                }

                //--------all below will execute at every cycle
                isPlayerCurrentTurn = crm.requestIsCurrentTurn();
                playerMap = crm.requestPlayerMap();
                repaint();

                //boolean isSuggestion = requestHasSuggestionBeenMade();
                //if(isSuggestion)
                //  requestWhatCardsIHad()


                if(isPlayerCurrentTurn) {
                    counterForShortCut++;
                    currentTurnTimer.stop();
                    clientFrame.addToLogConsole("UPDATE - It's now your turn.");
                    turnTimerUpdate = 1;
                    reqBtnArray = crm.requestBtns(currentXgrid, currentYgrid);
                    printAndAssignBtnsArray(reqBtnArray);
                    enableOrdisableBtns(movementButtons);
                    disableButtons(movementButtons);
                    btnRollDice.setEnabled(true);
                    buttonRollLimit = 1;
                    //if(currentInRoomNumber > 0)
                      //  btnSuggest.setEnabled(true);
                }
            }
        });
        currentTurnTimer.setRepeats(true); //timer repeats every 2 seconds

        movementButtons[SOUTH].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                diceRollValue--;
                if(diceRollValue >= 0){
                    addToDiceLog(Integer.toString(diceRollValue));
                }
                yC += 20;
                currentYgrid++;
                repaint();
                reqBtnArray = crm.requestBtns(currentXgrid, currentYgrid);
                printAndAssignBtnsArray(reqBtnArray);
                enableOrdisableBtns(movementButtons);
            }
        });

        movementButtons[NORTH].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                diceRollValue--;
                if(diceRollValue >= 0){
                    addToDiceLog(Integer.toString(diceRollValue));
                }
                yC -= 20;
                currentYgrid--;
                repaint();
                reqBtnArray = crm.requestBtns(currentXgrid, currentYgrid);
                printAndAssignBtnsArray(reqBtnArray);
                enableOrdisableBtns(movementButtons);
            }
        });

        movementButtons[EAST].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                diceRollValue--;
                if(diceRollValue >= 0){
                    addToDiceLog(Integer.toString(diceRollValue));
                }
                xC += 21;
                repaint();
                currentXgrid++;
                reqBtnArray = crm.requestBtns(currentXgrid, currentYgrid);
                printAndAssignBtnsArray(reqBtnArray);
                enableOrdisableBtns(movementButtons);
                movementAmount++;
            }
        });

        movementButtons[WEST].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                diceRollValue--;
                if(diceRollValue >= 0){
                    addToDiceLog(Integer.toString(diceRollValue));
                }
                xC -= 21;
                repaint();
                currentXgrid--;
                reqBtnArray = crm.requestBtns(currentXgrid, currentYgrid);
                printAndAssignBtnsArray(reqBtnArray);
                enableOrdisableBtns(movementButtons);
            }
        });

        btnAccuse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               new Accusation();
            }
         });

        enterButton[ENTER_ROOM].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //enterRoomCounter = counterForShortCut;
                diceRollValue = 0;
                addToDiceLog(Integer.toString(diceRollValue));
                currentInRoomNumber = getDoorId(currentXgrid, currentYgrid);
                int roomDirection = getDirection(currentXgrid, currentYgrid);
                crm.requestUpdatePlayerRoomLocation(currentInRoomNumber);
                drawInRoom(currentInRoomNumber, roomDirection);
                String enterRoomStr = "room number: "+currentInRoomNumber+" room direction: "+roomDirection;
                clientFrame.addToLogConsole(enterRoomStr);
                reqBtnArray = crm.requestBtns(currentXgrid, currentYgrid);
                printAndAssignBtnsArray(reqBtnArray);
                enableOrdisableBtns(movementButtons);
                enterButton[ENTER_ROOM].setEnabled(false);
                if(bph.isRoomAShortCutRoom(currentInRoomNumber)) {
                    inShortcutRoom = true;
                }
                inRoom = true;
                repaint();
                //TODO --- > enable the suggestion button
                //TODO --- > enable the accuse button
                //TODO ----> test appropriate room and enable shortcut button
            }
        });

        btnShortcut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counterForShortCut = 0;
                btnShortcut.setEnabled(false);
                btnExitRoom.setEnabled(false);
                //shortUsedAmount++;
                //if(counterForShortCut % 2 != 0 && shortUsedAmount == 1){

                //}
                if(currentInRoomNumber == 1) {
                    currentInRoomNumber = 7;
                    crm.requestUpdatePlayerRoomLocation(currentInRoomNumber);
                    currentXgrid = 17;
                    currentYgrid = 6;
                    xC = currentXgrid * 21;
                    yC = currentYgrid * 20;
                    drawInRoom(currentInRoomNumber, 0);
                    reqBtnArray = crm.requestBtns(currentXgrid, currentYgrid);
                    printAndAssignBtnsArray(reqBtnArray);
                    repaint();
                }
            }
        });

        btnEndTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                diceRollValue = 0;
                addToDiceLog(Integer.toString(diceRollValue));
                crm.requestEndOfTurn();
                disableButtons(movementButtons);
                playerMap = crm.requestPlayerMap();
                repaint();
                if(!currentTurnTimer.isRunning()) {
                    currentTurnTimer.start();
                    btnEndTurn.setEnabled(false);
                }
            }
        });

        btnRollDice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                diceRollValue = crm.requestDiceRoll();
                addToDiceLog(Integer.toString(diceRollValue));
                reqBtnArray = crm.requestBtns(currentXgrid, currentYgrid);
                printAndAssignBtnsArray(reqBtnArray);
                enableOrdisableBtns(movementButtons);
                buttonRollLimit--;
                enableOrdisableBtns(movementButtons);
                btnEndTurn.setEnabled(true);
            }
        });

        btnStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crm.requestToStartGame();
                crm.requestDealCards();
                crm.requestBuildListOfNonPlayingChars();
                btnStartGame.setEnabled(false);
            }
        });

        btnExitRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitRoom(currentInRoomNumber);
                if (currentInRoomNumber == 0){
                    inShortcutRoom = false;
                    inRoom = false;
                    btnExitRoom.setEnabled(false);
                    btnSuggest.setEnabled(false);
                }
            }
        });

        btnSuggest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               makeSuggestion();
               btnSuggest.setEnabled(false);
            }
        });
    } //end constructor

    public ArrayList<Card> getPlayersCards(){
        return playersDeck;
    }

    private void printAndAssignBtnsArray(String[] btsStrArray){
        clientFrame.addToLogConsole(btsStrArray[0]);
        cArray_movement_enter = btsStrArray[1].toCharArray();
    }

    public void buildNamesForLegend(ArrayList<Characters> nonPlayingList){
        playerMap = crm.requestPlayerMap();
        String playerName;
        for(Entry<Long, Player> p : playerMap.entrySet()) {
            long idNum = (long)p.getKey();
            Player player = p.getValue();
            playerName = player.getCharacter().getName() + " - Active";
            legendList.add(playerName);
        }

        for(Characters c : nonPlayingList){
            legendList.add(c.getName());
        }
        Collections.sort(legendList);
        for (String characterName : legendList){
            clientFrame.addToLegend(characterName);
        }
    }

    public Player getCurrentPlayerFromMap(Player currentPlayer, HashMap<Long, Player> map) {
        return (Player) map.get(currentPlayer.getPlayerId());
    }

    private int getDoorId(int row, int col) {
        for(ClueGameConstants.DOORS door : ClueGameConstants.DOORS.values()) {
            if(door.getRow() == row && door.getCol() == col)
                return door.getRoomNumber();
        }
        return 0;
    }

    private int getDirection(int row, int col) {
        for(ClueGameConstants.DOORS door : ClueGameConstants.DOORS.values()) {
            if(door.getRow() == row && door.getCol() == col)
                return door.getDirection();
        }
        return 0;
    }

    private void printCardsInPlayersDeck(ArrayList<Card> deck) {
        clientFrame.addToLogConsole("------------------------");
        clientFrame.addToLogConsole("Your cards are: ");
        clientFrame.addToLogConsole("Deck size: " + deck.size());
        for(Card card : deck) {
            clientFrame.addToLogConsole(card.getName());
        }
        clientFrame.addToLogConsole("------------------------");
    }

    private void printNonPlayingCharacters(ArrayList<Characters> charList) {
        clientFrame.addToLogConsole("------------------------");
        clientFrame.addToLogConsole("Non playing characters are: ");
        if(charList.isEmpty())
            clientFrame.addToLogConsole("None");
        else {
            for(Characters c : charList)
                clientFrame.addToLogConsole(c.getName());
        }
        clientFrame.addToLogConsole("------------------------");
    }

    public void paint(Graphics g) {
        //clientFrame.addToLogConsole("Paint start.....")
        super.paint(g);
        g.drawImage(gameboard.getImage(), 0, 0, null);
        g.setColor(new Color(currentPlayer.getCharacter().getColor()));
        Rectangle rectBounds = bph.getBounds(xC, yC);
        int rectXBounds = (int) rectBounds.getX();
        int rectYBounds = (int) rectBounds.getY();
        int rectBoundsHeight = (int) rectBounds.getHeight();
        int rectBoundsWidth  = (int) rectBounds.getWidth();
        g.fillRect(rectXBounds, rectYBounds, rectBoundsHeight, rectBoundsWidth);

        for(Entry<Long, Player> p : playerMap.entrySet()) {
            long id = (long)p.getKey();
            Player player = p.getValue();
            if(player.getCharacter().getName().equals(currentPlayer.getCharacter().getName()))
                continue;
            int playerInMapXBound = player.getCurrentXLocation() * 21;
            int playerInMapYBound = player.getCurrentYLocation() * 20;
            rectBounds  = bph.getBounds(playerInMapXBound, playerInMapYBound);
            rectXBounds = (int) rectBounds.getX();
            rectYBounds = (int) rectBounds.getY();
            rectBoundsHeight = (int) rectBounds.getHeight();
            rectBoundsWidth  = (int) rectBounds.getWidth();
            g.setColor(new Color(player.getCharacter().getColor()));
            g.fillRect(rectXBounds, rectYBounds, rectBoundsHeight, rectBoundsWidth);
        }
        if (isGameStarted){
            for(Characters character : nonPlayingCharList) {
                int xBound = character.getxStarting() * 21;
                int yBound = character.getyStarting() * 20;
                rectBounds  = bph.getBounds(xBound, yBound);
                rectXBounds = (int) rectBounds.getX();
                rectYBounds = (int) rectBounds.getY();
                rectBoundsHeight = (int) rectBounds.getHeight();
                rectBoundsWidth  = (int) rectBounds.getWidth();
                g.setColor(new Color(character.getColor()));
                g.fillRect(rectXBounds, rectYBounds, rectBoundsHeight, rectBoundsWidth);
            }
        }
    }

    private void enableOrdisableBtns(JButton[] movementButtons) {
        // WEST = 0, EAST = 1, NORTH = 2, SOUTH = 3;
        if (buttonRollLimit == 0){
            btnRollDice.setEnabled(false);
        }
        if (diceRollValue == 0) {
            disableButtons(movementButtons);
        } else {
            enableOrDisableMovementAndEnterButtons(movementButtons);
        }
    }

    private void enableOrDisableMovementAndEnterButtons(JButton[] movementButtons) {
        boolean[] moveOptions = { false, false, false, false };
        boolean roomOptions = false;
        int i = 1;
        if (inShortcutRoom)
            btnShortcut.setEnabled(true);

        roomOptions = cArray_movement_enter[0] == '1';

        for (int j = 0; j < moveOptions.length; j++) {
            moveOptions[j] = cArray_movement_enter[i] == '1';
            i++;
            movementButtons[j].setEnabled(moveOptions[j]);
        }
        enterButton[ENTER_ROOM].setEnabled(roomOptions);
        if (inRoom) {
            btnExitRoom.setEnabled(true);
            btnSuggest.setEnabled(true);
        }
    }

    public void disableButtons(JButton[] movementButtons) {
        for(int i = 0; i < 4; i++) {
            movementButtons[i].setEnabled(false);
        }
        enterButton[ENTER_ROOM].setEnabled(false);
        btnShortcut.setEnabled(false);
        btnExitRoom.setEnabled(false);
        btnSuggest.setEnabled(false);
    }

    protected void addToDiceLog(String input){
        textAreaDice.setText(input.toString());
    }

    private void exitRoom(int roomNumber){
        ArrayList<Boolean[]> attemptedDoorsListArray = bph.getAttemptedDoorsList();
        boolean isDoorBlocked = false;
        int attemptedDoorsAmt = 0;

        do {
            String roomName = ClueGameConstants.ROOM_NAMES_ARRAY[roomNumber - 1];
            String[] exitOptions = bph.assignExitOptions(roomNumber);
            String roomPictureStrResource = roomExitPictureStrs[roomNumber - 1];
            ImageIcon roomImageIcon  = new ImageIcon(getClass().getResource(roomPictureStrResource));
            String doorSelection = (String)JOptionPane.showInputDialog(null,
                    "Please Select Exiting Door", "Select Door", JOptionPane.PLAIN_MESSAGE,
                    roomImageIcon, exitOptions, exitOptions[0]);
            ClueGameConstants.DOORS doorEnumValue = bph.findSpecificDoor(roomNumber, Integer.parseInt(doorSelection));
            assert doorEnumValue != null;
            int tempCurrentXgrid = doorEnumValue.getRow();
            int tempCurrentYgrid = doorEnumValue.getCol();
            isDoorBlocked = bph.determineIfSelectedDoorIsBlocked(tempCurrentXgrid, tempCurrentYgrid, playerMap);
            if(isDoorBlocked){
                attemptedDoorsListArray.get(roomNumber - 1)[doorEnumValue.getDoorID() - 1] = true;
                attemptedDoorsAmt = bph.determineTrueAmountInDoorArray(attemptedDoorsListArray.get(roomNumber - 1));
                if(attemptedDoorsAmt == attemptedDoorsListArray.get(roomNumber - 1).length){
                    String allDoorsBlockedStr = "All door(s) are blocked.";
                    clientFrame.addToLogConsole("Could not exit " + roomName + ". Door(s) blocked");
                    JOptionPane.showMessageDialog(null, allDoorsBlockedStr, "No Exit",
                            JOptionPane.WARNING_MESSAGE);
                    break;
                }
                else{
                    String blockedDoorStr = "Selected door is blocked. Choose another door";
                    JOptionPane.showMessageDialog(null, blockedDoorStr, "Blocked Door",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
            else{
                currentXgrid = doorEnumValue.getRow();
                currentYgrid = doorEnumValue.getCol();
                xC = currentXgrid * 21;
                yC = currentYgrid * 20;
                currentInRoomNumber = 0;
                inShortcutRoom = false;
                playerMap = crm.requestPlayerMap();
                repaint();
                reqBtnArray = crm.requestBtns(currentXgrid,currentYgrid);
                crm.requestUpdatePlayerRoomLocation(currentInRoomNumber);
                clientFrame.addToLogConsole("Exited the " + roomName);
                btnExitRoom.setEnabled(false);
                btnShortcut.setEnabled(false);
            }
        } while (isDoorBlocked);
    }

    private void makeSuggestion(){
        //get the character being suggested
        String[] characterNamesArr = ClueGameConstants.CHARACTER_NAMES_ARRAY;
        ImageIcon allCharsImageIcon  = new ImageIcon(getClass().getResource("resources/allCharacters.png"));
        String suggestedChar = (String)JOptionPane.showInputDialog(null,
                "Please Select Suggested Character", "Select Suggested Character",JOptionPane.PLAIN_MESSAGE,
                allCharsImageIcon, characterNamesArr, characterNamesArr[0]);

        //get the weapon being suggested
        String[] weaponNamesArr = ClueGameConstants.WEAPON_NAMES_ARRAY;
        ImageIcon allWeaponsImageIcon  = new ImageIcon(getClass().getResource("resources/allWeapons.png"));
        String suggestedWeapon = (String)JOptionPane.showInputDialog(null,
                "Please Select Suggested Character", "Select Suggested Weapon", JOptionPane.PLAIN_MESSAGE,
                allWeaponsImageIcon, weaponNamesArr, weaponNamesArr[0]);

        //convert character name, weapon name, and room number into single int number
        int contentSuggestionNum = bph.getCombinedSuggestionContentNumber(suggestedChar,suggestedWeapon,currentInRoomNumber);

        //submit contentSuggestionNum to server. submission will also trigger isSuggestionMade = true on gamestate
        //crm.requestSubmitSuggestionContentNum(contentSuggestionNum);

        //
    }

    public void drawInRoom(int roomNumber, int roomDirection)
    {
        //cons:1 ; billiard:2; lib:3; study:4; ball: 5; hall:6; lounge:8; kitchen:9; dining: 10
        //room direction 0=up; 1=down; 2=left; 3=right
        int multiplier = currentPlayer.getCharacter().getTurnOrder();
        if (roomNumber == 1) { //conservatory down 2, left turn over num
            yC += 20*2;
            xC += 21;
            currentXgrid++;
            xC -= 21 * multiplier;
            currentYgrid = currentYgrid+2;
            currentXgrid = currentXgrid - multiplier;
        }
        else if (roomNumber == 9) { //dining room
            if (roomDirection == 1) {
                yC += 20*2;
                currentYgrid = currentYgrid+2;
            }
            xC += 21 * multiplier;
            currentXgrid = currentXgrid + multiplier;
        }
        else if (roomNumber == 4) { //study //up 2, left multiplier
            yC-=20*2;
            xC-= 21* multiplier;
            currentYgrid = currentYgrid-2;
            currentXgrid = currentXgrid - multiplier;
        }
        else if (roomNumber == 7) { //lounge //right 2, up mult
            yC -= 20*multiplier;
            xC += 21*2;
            currentYgrid = currentYgrid - multiplier;
            currentXgrid = currentXgrid + 2;
        }
        else if (roomNumber == 3){ //library
            if (roomDirection == 0) {
                yC-= 20*2; //up 2
                xC+= 21*3; //right 3
                currentXgrid = currentXgrid+3;
                xC-= 21*multiplier; //left by 6
                currentYgrid = currentYgrid-2;
            }
            else {
                xC-= 21*multiplier;
            }
            currentXgrid = currentXgrid - multiplier;
        }
        else if (roomNumber == 8){ //kitchen
            yC+= 20* multiplier;
            xC+= 21;
            currentYgrid = currentYgrid + multiplier;
            currentXgrid++;
        }
        else if (roomNumber == 6){ //hall
            if (roomDirection == 0){ //up
                yC-= 20* multiplier; //up multiplier
                currentYgrid = currentYgrid - multiplier;
            }
            else{ //right
                yC+= 20*2;
                currentYgrid = currentYgrid + 2;
                xC+= 21; //right one
                yC-= 20* multiplier;
                currentYgrid = currentYgrid - multiplier;
                currentXgrid++;
            }
        }
        else if (roomNumber == 2){ //biilard
            if (roomDirection == 2){ //left
                yC+= 20;
                xC-= 21*multiplier;
                currentXgrid = currentXgrid - multiplier;
            }
            else{ //down
                xC-= 21*2;
                currentXgrid= currentXgrid - 2;
                yC+= 20;
                xC+= 21*multiplier;
                currentXgrid = currentXgrid + multiplier;
            }
            currentYgrid++;
        }
        else if (roomNumber == 5){ //ballrom
            if (roomDirection == 1) {
                yC+= 20*multiplier;
            }
            else {
                yC-= 20*3; //up 3
                currentYgrid = currentYgrid - 3;
                if (roomDirection == 3){ //right
                    xC+= 21;
                    currentXgrid++;
                }
                else {
                    xC-= 21;
                    currentXgrid--;
                }
                yC+= 20*multiplier;
            }
            currentYgrid = currentYgrid + multiplier;
        }
        else
            System.out.println("error in draw in room.");
    }

    private void initComponents(){
        this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.setBackground(Color.PINK);
        this.setBounds(6, 37, 688, 535);

        movementButtons = new BasicArrowButton[4];
        movementButtons[NORTH] = new BasicArrowButton(BasicArrowButton.NORTH);
        movementButtons[NORTH].setBounds(613, 74, 26, 23);
        this.add(movementButtons[NORTH]);

        movementButtons[SOUTH] = new BasicArrowButton(BasicArrowButton.SOUTH);
        movementButtons[SOUTH].setBounds(613, 136, 26, 23);
        this.add(movementButtons[SOUTH]);

        movementButtons[EAST] = new BasicArrowButton(BasicArrowButton.EAST);
        movementButtons[EAST].setBounds(639, 106, 26, 23);
        this.add(movementButtons[EAST]);

        movementButtons[WEST] = new BasicArrowButton(BasicArrowButton.WEST);
        movementButtons[WEST].setBounds(587, 106, 26, 23);
        this.add(movementButtons[WEST]);

        btnEndTurn = new JButton("End Turn");
        btnEndTurn.setForeground(Color.RED);
        btnEndTurn.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnEndTurn.setBounds(579, 337, 99, 23);
        this.add(btnEndTurn);

        enterButton = new JButton[1];
        enterButton[ENTER_ROOM] = new JButton("Enter Room");
        enterButton[ENTER_ROOM] .setForeground(new Color(128, 0, 128));
        enterButton[ENTER_ROOM] .setFont(new Font("SansSerif", Font.BOLD, 10));
        enterButton[ENTER_ROOM] .setBounds(579, 370, 99, 23);
        this.add(enterButton[ENTER_ROOM]);

        btnExitRoom = new JButton("Exit Room");
        btnExitRoom.setForeground(new Color(128, 0, 128));
        btnExitRoom.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnExitRoom.setBounds(579, 403, 99, 23);
        this.add(btnExitRoom);

        btnSuggest = new JButton("Suggest");
        btnSuggest.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnSuggest.setBounds(579, 436, 99, 23);
        this.add(btnSuggest);

        btnAccuse = new JButton("Accuse");
        btnAccuse.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnAccuse.setBounds(579, 469, 99, 23);
        this.add(btnAccuse);

        btnShortcut = new JButton("Shortcut");
        btnShortcut.setForeground(Color.BLACK);
        btnShortcut.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnShortcut.setBounds(579, 502, 99, 23);
        this.add(btnShortcut);

        btnRollDice = new JButton("Roll Dice");
        btnRollDice.setForeground(new Color(0,128,0));
        btnRollDice.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnRollDice.setBounds(579, 208, 99, 23);
        this.add(btnRollDice);

        btnStartGame = new JButton("Start Game");
        btnStartGame.setForeground(new Color(0, 128, 0));
        btnStartGame.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnStartGame.setBounds(579, 6, 99, 23);
        this.add(btnStartGame);

        textAreaDice = new JTextArea();
        textAreaDice.setBounds(604, 241, 74, 76);
        textAreaDice.setEditable(false);
        textAreaDice.setFont(new Font("Microsoft Tai Le", Font.BOLD, 60));
        textAreaDice.setBackground(Color.PINK);
        this.add(textAreaDice);

        cArray_movement_enter = new char[5];
        playersDeck = new ArrayList<>();
        legendList = new ArrayList<>();
    }
} // end class