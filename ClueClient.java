/*********************************************
 * This class contains the main run method 
 * to execute the client side gui
 ********************************************/

import javax.swing.SwingUtilities;

public class ClueClient {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new ClueStartFrame().setVisible(true);
			}
		});

	}

}