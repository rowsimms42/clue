
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ClueStartFrame extends JFrame{
	private JPanel contentPane, clue_logo_panel, button_panel; 
	private JButton btnStartGame;
	/**
	 * Create the frame.
	 */
	public ClueStartFrame(){
		
		setBounds(100, 100, 450, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		clue_logo_panel = new JPanel();
		clue_logo_panel.setBackground(Color.BLUE);
		clue_logo_panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		clue_logo_panel.setBounds(6, 6, 438, 538);
		contentPane.add(clue_logo_panel);
		
		button_panel = new JPanel();
		button_panel.setBounds(6, 556, 438, 116);
		contentPane.add(button_panel);
		button_panel.setLayout(null);
		
		btnStartGame = new JButton("Start Game");
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//keep frame in memory, do not dispose(), just don't show
				setVisible(false);
				//new CharacterSelectFrame(new ServerConnection("Billy Bob", 34212));
				new CharacterSelectFrame(new ServerConnection());
			}
		});
		btnStartGame.setBounds(149, 53, 117, 29);
		button_panel.add(btnStartGame);
		
		//pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
 
	} //end class

} // end class
