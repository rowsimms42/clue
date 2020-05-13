import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import javax.swing.plaf.basic.BasicArrowButton;

public class BoardPanel extends JPanel {

	final ImageIcon gameboard;
    RoomClick roomClick;
    int rows = 24;
    int coloums = 25;
    String value;
    Client client;
    ClientFrame clientFrame;
    //Characters assignedCharacter;
    Player currentPlayer;
    Message messageReceived;
    final int WEST = 0, EAST = 1, NORTH = 2, SOUTH = 3;
    String btnValues;
    BasicArrowButton[] movementButtons;
    char[] cArray;

    int xC = 0; // Player class sends starting coordinates. xe=xe*21, ye=ye*20;
    int yC = 0;
    int currentXgrid = 0;
    int currentYgrid = 0;

    public BoardPanel(Client clientConnection, ClientFrame clientFrame, Player player) {
        client = clientConnection;
        this.clientFrame = clientFrame;
        currentPlayer = player;
        this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.setBackground(Color.PINK);
        this.setBounds(6, 37, 688, 535);
        //movementButtons = new JButton[4];
        JLabel lblNewLabel = new JLabel("");
        
        gameboard = new ImageIcon(getClass().getResource("resources/board.jpg"));
        int w = gameboard.getIconWidth();
        int h = gameboard.getIconHeight();
        setPreferredSize(new Dimension(w, h));
        this.add(lblNewLabel);
        lblNewLabel.setBounds(6, 6, 569, 523); 

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
        
        cArray = new char[4];

        xC = currentPlayer.getCharacter().getxStarting() * 21;
        yC = currentPlayer.getCharacter().getyStarting() * 20;

        currentXgrid = currentPlayer.getCharacter().getxStarting();
        currentYgrid = currentPlayer.getCharacter().getyStarting();

        lblNewLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int p_x = e.getX();
                int p_y = e.getY();
                int pixal_with = 20;
                int x = e.getX() - 30; // off set by 30
                int y = e.getY() - 13; // off set by 13
                int x_mod = x % 20;
                int y_mod = y % 20;
                int x_coord = x / pixal_with;
                int y_coord = y / pixal_with;
                int gx = x_coord;
                int gy = y_coord;

                if (x_mod >= 2 || x_mod <= 17 || y_mod >= 2 || y_mod <= 17) {
                    if (x_coord <= 23 || y_coord <= 24) {
                        gx = x_coord;
                        gy = y_coord;
                    }
                }
                value = RoomClick.checker(gx, gy);
                System.out.println(value);
                String xc = String.valueOf(p_x);
                String yc = String.valueOf(p_y);
                String s = (", ");
                String coordinates = xc.concat(s).concat(yc);
                clientFrame.addToLogConsole(coordinates);
                // System.out.println(coordinates);
            }
        });

        //request movement options at launch
        try {
			requestBtns(currentXgrid, currentYgrid);
		} catch (ClassNotFoundException | IOException e2) {
			e2.printStackTrace();
		}
        
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

                try {
					requestBtns(currentXgrid, currentYgrid);
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
                
                enableOrdisableBtns(movementButtons, cArray);
            }
        });

        movementButtons[NORTH].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                yC -= 20;
                currentYgrid--;
                repaint();

                try {
					requestBtns(currentXgrid, currentYgrid);
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
               
                enableOrdisableBtns(movementButtons, cArray);
            }
        });

        movementButtons[EAST].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xC += 21;
                repaint();
                currentXgrid++;
                
                try {
					requestBtns(currentXgrid, currentYgrid);
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
                
                enableOrdisableBtns(movementButtons, cArray);
            }
        });

        movementButtons[WEST].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xC -= 21;
                repaint();
                currentXgrid--;
                
                try {
					requestBtns(currentXgrid, currentYgrid);
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
               
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
 
    private Boolean determineBounds(int x, int y){
    	return (x>=23 && y>=13 && x<=536 && y<=511);
    }

    private void requestBtns(int x, int y) throws IOException, ClassNotFoundException{
    	int[] coords = {0,0};
    	coords[0] = x;
    	coords[1] = y;
    	String xc = String.valueOf(coords[0]);
                String yc = String.valueOf(coords[1]);
                String s = (", ");
                String coordinates = xc.concat(s).concat(yc);
                clientFrame.addToLogConsole(coordinates);

        client.send(new Message(ClueGameConstants.REQUEST_MOVEMENT_BUTTON_VALUES, coords));
        // Receive message with available connections from the server
        messageReceived = client.getMessage();
        // Get available connections from the message
        btnValues =  (String) messageReceived.getData();
        //put string into character array
        cArray = btnValues.toCharArray();
        //System.out.println(Collections.singletonList(buttonValues));
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
    	/*
    	if (cArray[LEFT] == '1')
    	{
    		movementButtons[LEFT].setEnabled(true);
    	}
    	else if (cArray[LEFT] == '0')
    	{
    		movementButtons[LEFT].setEnabled(false);
    	} 
    	if (cArray[RIGHT] == '1')
    	{
    		movementButtons[RIGHT].setEnabled(true);
    	}
    	else if (cArray[RIGHT] == '0')
    	{
    		movementButtons[RIGHT].setEnabled(false);
    	}
    	if (cArray[UP] == '1')
    	{
    		movementButtons[UP].setEnabled(true);
    	}
    	else if (cArray[UP] == '0')
    	{
    		movementButtons[UP].setEnabled(false);
    	}
    	if (cArray[DOWN] == '1')
    	{
    		movementButtons[DOWN].setEnabled(true);
    	}
    	else if (cArray[DOWN] == '0')
    	{
    		movementButtons[DOWN].setEnabled(false);
    	} */
    }

    /* TODO ---- 
    private void updatePlayerLocations() throws IOException, ClassNotFoundException{
    	client.send(new Message(ClueGameConstants.REQUEST_PLAYER_MAP, null));
    	messageReceived = client.getMessage();
    } */
    
    private void requestBtnsAndEnableOrDisable(int x, int y, BasicArrowButton movementButtons[], char cArray[]) {
    	
    }

} // end class


