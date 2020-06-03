import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ClueStartFrame extends JFrame {
    private JButton start_button;
    private JPanel button_panel, contentPane;
    private JLabel image_panel, dice_panel;

    /**
     * Create the frame.
     */
    public ClueStartFrame() {

        setBounds(100, 100, 450, 700);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.BLACK);

        ImageIcon image = new ImageIcon(getClass().getResource("resources/clueLogo.jpg"));
        image_panel = new JLabel(image);
        image_panel.setBounds(10, 10, 400, 260);
        image_panel.setBackground(Color.white);

        ImageIcon dice = new ImageIcon(getClass().getResource("resources/temp_dice.jpg"));
        dice_panel = new JLabel(dice);
        dice_panel.setBounds(10, 150, 430, 400);
        dice_panel.setBackground(Color.white);

        button_panel = new JPanel();
        button_panel.setBounds(123, 500, 200, 60);
        button_panel.setBackground(Color.black);
        start_button = new JButton("START GAME");
        start_button.setFocusPainted(false);
        start_button.setBackground(Color.black);
        start_button.setForeground(Color.white);
        Font b_font = new Font("Times New Roman", Font.PLAIN, 20);
        start_button.setFont(b_font);

        contentPane.add(dice_panel);
        contentPane.add(image_panel);
        contentPane.add(button_panel);
        button_panel.add(start_button);

        start_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // keep frame in memory, do not dispose(), just don't show
                setVisible(false);
                try {
                    // Server must be started seperately before this function will return anything
                    CharacterSelectFrame characterSelectFrame =  new CharacterSelectFrame(new Client());
                    characterSelectFrame.setVisible(true);
                } catch (IOException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    } //end conconstructor
} // end class

/**
 * Need another window that comes up when only first person connection
 * This starts the server, after that, there is no need to interact
 * with the server except through the Client class, which holds the
 * methods to send and receive data
 */
