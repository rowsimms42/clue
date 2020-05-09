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

public class BoardPanel extends JPanel {

    final ImageIcon gameboard;
    RoomClick roomClick;
    int rows = 24;
    int coloums = 25;
    String value;
    Client client;
    ClientFrame clientFrame;
    Characters assignedCharacter;
    Message messageReceived;
    final int LEFT = 0,  RIGHT = 1, UP = 2, DOWN = 3;
    //HashMap<String, Boolean> buttonValues;
    String buttonValues;
    
    boolean[] boolMovements;
    JButton[] movementButtons;
    private int[] coords;

    int xC = 0; // Player class sends starting coordinates. xe=xe*21, ye=ye*20;
    int yC = 0;
    int currentXgrid = 0;
    int currentYgrid = 0;

    public BoardPanel(Client clientConnection, ClientFrame clientFrame, Characters assignedCharacter) {
        client = clientConnection;
        this.clientFrame = clientFrame;
        this.assignedCharacter = assignedCharacter;
        this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.setBackground(Color.PINK);
        this.setBounds(6, 37, 688, 535);
        boolMovements = new boolean[4];
        movementButtons = new JButton[4];
		coords = new int[2];

        xC = assignedCharacter.getxStarting() * 21;
        yC = assignedCharacter.getyStarting() * 20;

        currentXgrid = assignedCharacter.getxStarting();
        currentYgrid = assignedCharacter.getyStarting();

        JLabel lblNewLabel = new JLabel("");

        lblNewLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int p_x = e.getX();
                int p_y = e.getY();
                int pixal_with;
                pixal_with = 20;
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

        gameboard = new ImageIcon("resources\\board.jpg");
        int w = gameboard.getIconWidth();
        int h = gameboard.getIconHeight();
        setPreferredSize(new Dimension(w, h));
        this.add(lblNewLabel);
        lblNewLabel.setBounds(6, 6, 569, 523);

        JButton[] movementButtons = new JButton[4];
        movementButtons[UP] = new JButton("Up");
        movementButtons[UP].setBounds(594, 88, 74, 21);
        this.add(movementButtons[UP]);

        movementButtons[DOWN] = new JButton("Down");
        movementButtons[DOWN].setBounds(594, 129, 74, 21);
        this.add(movementButtons[DOWN]);

        movementButtons[RIGHT] = new JButton("Right");
        movementButtons[RIGHT].setBounds(594, 167, 74, 21);
        this.add(movementButtons[RIGHT]);

        movementButtons[LEFT] = new JButton("Left");
        movementButtons[LEFT].setBounds(594, 206, 74, 21);
        this.add(movementButtons[LEFT]);

        JButton btnSuggest = new JButton("Suggest");
        btnSuggest.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnSuggest.setBounds(579, 349, 99, 30);
        this.add(btnSuggest);

        JButton btnAccuse = new JButton("Accuse");
        btnAccuse.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnAccuse.setBounds(579, 389, 99, 30);
        this.add(btnAccuse);

        JButton btnShortcut = new JButton("Shortcut");
        btnShortcut.setForeground(Color.BLACK);
        btnShortcut.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnShortcut.setBounds(579, 431, 99, 30);
        this.add(btnShortcut);

        JButton btnEndTurn = new JButton("End Turn");
        btnEndTurn.setForeground(Color.RED);
        btnEndTurn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnEndTurn.setBounds(579, 471, 99, 30);
        this.add(btnEndTurn);

        JButton btnRollDice = new JButton("Roll Dice");
        btnRollDice.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnRollDice.setBounds(579, 306, 99, 30);
        this.add(btnRollDice);

        try {
            requestBtns(currentXgrid,currentYgrid);
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        movementButtons[DOWN].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                yC += 20;
                currentYgrid++;
                repaint();
                
                
                try {
                    requestBtns(currentXgrid, currentYgrid);
                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }   
/*
                boolMovements[UP] = false;
                boolMovements[DOWN] = false;
                boolMovements[RIGHT] = true;
                boolMovements[LEFT] = true;
                movementButtons[UP].setEnabled(boolMovements[UP]);
                movementButtons[DOWN].setEnabled(boolMovements[DOWN]);
                movementButtons[LEFT].setEnabled(boolMovements[LEFT]);
                movementButtons[RIGHT].setEnabled(boolMovements[RIGHT]);
                movementButtons[RIGHT].setEnabled(false);
    */            
                
        }
    });
    
    movementButtons[UP].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            yC-=20;
            currentYgrid--;
                repaint();
                try {
                    requestBtns(currentXgrid, currentYgrid);
                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }   
                enableOrdisableBtns(boolMovements);
        }
    });
    
    movementButtons[RIGHT].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            xC+=21;
            repaint();
            currentXgrid++;
                repaint();
                try {
                    requestBtns(currentXgrid, currentYgrid);
                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }   
                enableOrdisableBtns(boolMovements);
                //movementButtons[RIGHT].setEnabled(false);
        }
    });
    
    movementButtons[LEFT].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            xC-=21;
            repaint();
            currentXgrid--;
                repaint();
                try {
                    requestBtns(currentXgrid, currentYgrid);
                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }   
                enableOrdisableBtns(boolMovements);
        }
    });
}

    //@Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(gameboard.getImage(), 0, 0, null);
        Rectangle bounds = getBounds(xC, yC);
        g.setColor(new Color(assignedCharacter.getColor()));
        g.fillRect((int)bounds.getX(), (int)bounds.getY(), (int)bounds.getHeight(), (int)bounds.getWidth());
 }
    
 private Rectangle getBounds(int x, int y)
 {
     //offset
     x=x+30;
     y=y+16;
     return new Rectangle(x,y,20,20);
 }
 
private Boolean determineBounds(int x, int y)
{
    return (x>=23 && y>=13 && x<=536 && y<=511);
}

private void requestBtns(int x, int y) throws IOException, ClassNotFoundException
{
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
    buttonValues =  (String) messageReceived.getData();
    //System.out.println(Collections.singletonList(buttonValues));
    System.out.println(buttonValues);


	/*
    for( Map.Entry<String, Boolean> v : buttonValues.entrySet() ) {
        if( v.getValue() ) {
            System.out.println(v.getKey());
        }
	 }
	 */
    

    //final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
}

private void enableOrdisableBtns(boolean boolMovements[])
{
    
    movementButtons[UP].setEnabled(boolMovements[UP]);
    movementButtons[DOWN].setEnabled(boolMovements[DOWN]);
    movementButtons[LEFT].setEnabled(boolMovements[LEFT]);
    movementButtons[RIGHT].setEnabled(boolMovements[RIGHT]);
    movementButtons[RIGHT].setEnabled(false);


                

}
} // end class


