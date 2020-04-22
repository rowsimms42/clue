//Contributors: Jonah Dubbs-Nadeau and Joseph Salazar

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/*
Main class to invoke Clue game GUI
*/
public class ClueClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                
                ClientManager cm = new ClientManager();
                cm.showStartFrame();
            	/* Build and display the main game window (JFrame) 
                ClientFrame frame = new ClientFrame();
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                frame.setVisible(true);
                
                //TODO JOptionPane - ask for port number and connect to server
                
                */
            } //end run
        });

	} //end main

} //end class
