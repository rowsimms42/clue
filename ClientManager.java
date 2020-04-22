
//Contributors: Jonah Dubbs-Nadeau and Joseph Salazar

/*
 * Class to manage the client side of the Clue game. 
 * Sends/Receives data from the server via ServerConnection class. 
 * Receives data from ClientFrame. 
 * Updates console log. 
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*; 
import java.net.*; 

public class ClientManager {

    private ClueStartFrame start_frame;
    private ClientFrame frame; // = new ClientFrame();
    public ClientManager(){

                start_frame = new ClueStartFrame();
                /*
                start_frame.setLocationRelativeTo(null);
        start_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start_frame.setResizable(false);
        start_frame.setVisible(true); */

                frame = new ClientFrame();
                

    }

    public void showStartFrame(){
        start_frame.setLocationRelativeTo(null);
        start_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start_frame.setResizable(false);
        start_frame.setVisible(true);
    }

    public void showGuiFrame(){
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
} //end class
	
	