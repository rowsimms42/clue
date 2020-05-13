import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import javax.swing.plaf.basic.BasicArrowButton;

public class BoardPanel extends JPanel {

	final ImageIcon gameboard;
    String value;
    Client client;
    ClientFrame clientFrame;
    Player currentPlayer; //Characters now in Player class. Access specific character by currentPlayer.getCharacter(). ...
    Message messageReceived;
    final int WEST = 0, EAST = 1, NORTH = 2, SOUTH = 3;
    String btnValues;
    BasicArrowButton[] movementButtons;
    JLabel boardLabel;
    char[] cArray;
    int xC = 0; //x coordinate for drawing on board
    int yC = 0; //y coordinate for drawing on board
    int currentXgrid = 0; //x coordinate location for tile grid. this x coord is sent to server
    int currentYgrid = 0; //y coordinate location for tile grid. this y coord is sent to server

    public BoardPanel(Client clientConnection, ClientFrame clientFrame, Player player) {
        client = clientConnection;
        this.clientFrame = clientFrame;
        currentPlayer = player;
        
        initComponents(); //init all but board 

        boardLabel = new JLabel("");
        gameboard = new ImageIcon(getClass().getResource("resources/board.jpg"));
        int w = gameboard.getIconWidth();
        int h = gameboard.getIconHeight();
        setPreferredSize(new Dimension(w, h));
        this.add(boardLabel);
        boardLabel.setBounds(6, 6, 569, 523); 

        cArray = new char[4];

        xC = currentPlayer.getCharacter().getxStarting() * 21;
        yC = currentPlayer.getCharacter().getyStarting() * 20;

        currentXgrid = currentPlayer.getCharacter().getxStarting();
        currentYgrid = currentPlayer.getCharacter().getyStarting();

        //request movement options at launch
        requestBtnsCall(currentXgrid, currentYgrid);
        enableOrdisableBtns(movementButtons, cArray);
        
        /*TODO ----- TESTING IF WE CAN RECIEVE PLAYER MAP
        try {
			updatePlayerLocations();
		} catch (ClassNotFoundException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} */

        movementButtons[SOUTH].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                yC += 20;
                currentYgrid++;
                repaint();
                requestBtnsCall(currentXgrid, currentYgrid);
                enableOrdisableBtns(movementButtons, cArray);
            }
        });

        movementButtons[NORTH].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                yC -= 20;
                currentYgrid--;
                repaint();
                requestBtnsCall(currentXgrid, currentYgrid);
                enableOrdisableBtns(movementButtons, cArray);
            }
        });

        movementButtons[EAST].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xC += 21;
                repaint();
                currentXgrid++;
                requestBtnsCall(currentXgrid, currentYgrid);
                enableOrdisableBtns(movementButtons, cArray);
            }
        });

        movementButtons[WEST].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xC -= 21;
                repaint();
                currentXgrid--;
                requestBtnsCall(currentXgrid, currentYgrid);
                enableOrdisableBtns(movementButtons, cArray);
            }
        });
    } //end constructor

    // @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(gameboard.getImage(), 0, 0, null);
        Rectangle bounds = getBounds(xC, yC);
        g.setColor(new Color(currentPlayer.getCharacter().getColor()));
        g.fillRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getHeight(), (int) bounds.getWidth());
    }

    private Rectangle getBounds(int x, int y) {
        //offset
        //x=x+30;
        //y=y+16;
        return new Rectangle(x + 30,y + 16,20,20);
    }

    private void requestBtns(int x, int y) throws IOException, ClassNotFoundException{
    	int[] coords = {0,0};
    	coords[0] = x;
    	coords[1] = y;
    	String xc = String.valueOf(coords[0]);
                String yc = String.valueOf(coords[1]);
                String s = (", ");
                String coordinates = xc.concat(s).concat(yc);
                clientFrame.addToLogConsole(coordinates); //adds player location to console

        client.send(new Message(ClueGameConstants.REQUEST_MOVEMENT_BUTTON_VALUES, coords));
        messageReceived = client.getMessage();
        btnValues =  (String) messageReceived.getData();
        cArray = btnValues.toCharArray();
        //for debugging purposes, output string to console log
        clientFrame.addToLogConsole(btnValues); 
    }

    private void enableOrdisableBtns(JButton movementButtons[], char cArray[]){
    	//WEST = 0, EAST = 1, NORTH = 2, SOUTH = 3;
		boolean []moveOptions = {false,false,false,false};
    	for(int i = 0; i < 4; i++) {
    		if(cArray[i] == '1')
    			moveOptions[i] = true;
    		else
    			moveOptions[i] = false;
    	}
    	movementButtons[WEST].setEnabled(moveOptions[WEST]);
    	movementButtons[EAST].setEnabled(moveOptions[EAST]);
    	movementButtons[NORTH].setEnabled(moveOptions[NORTH]);
    	movementButtons[SOUTH].setEnabled(moveOptions[SOUTH]); 
    }

    public void requestBtnsCall(int currentXgrid, int currentYgrid)
    {
        try {
            requestBtns(currentXgrid, currentYgrid);
        } catch (ClassNotFoundException | IOException e1) {
            e1.printStackTrace();
        }
    }

     /* TODO ---- 
    private void updatePlayerLocations() throws IOException, ClassNotFoundException{
    	client.send(new Message(ClueGameConstants.REQUEST_PLAYER_MAP, null));
    	messageReceived = client.getMessage();
    } */

    private void initComponents()
    {
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
        
        JButton btnEnterRoom = new JButton("Enter Room");
        btnEnterRoom.setForeground(new Color(128, 0, 128));
        btnEnterRoom.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnEnterRoom.setBounds(579, 340, 99, 23);
        this.add(btnEnterRoom);
        
        JButton btnExitRoom = new JButton("Exit Room");
        btnExitRoom.setForeground(new Color(128, 0, 128));
        btnExitRoom.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnExitRoom.setBounds(579, 375, 99, 23);
        this.add(btnExitRoom);

        JButton btnSuggest = new JButton("Suggest");
        btnSuggest.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnSuggest.setBounds(579, 410, 99, 23);
        this.add(btnSuggest);

        JButton btnAccuse = new JButton("Accuse");
        btnAccuse.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnAccuse.setBounds(579, 445, 99, 23);
        this.add(btnAccuse);

        JButton btnShortcut = new JButton("Shortcut");
        btnShortcut.setForeground(Color.BLACK);
        btnShortcut.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnShortcut.setBounds(579, 480, 99, 23);
        this.add(btnShortcut);

        JButton btnEndTurn = new JButton("End Turn");
        btnEndTurn.setForeground(Color.RED);
        btnEndTurn.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnEndTurn.setBounds(579, 243, 99, 23);
        this.add(btnEndTurn);

        JButton btnRollDice = new JButton("Roll Dice");
        btnRollDice.setForeground(new Color(0,128,0));
        btnRollDice.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnRollDice.setBounds(579, 208, 99, 23);
        this.add(btnRollDice);
    }

} // end class