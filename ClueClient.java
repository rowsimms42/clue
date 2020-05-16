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