
//Contributors: Jonah Dubbs-Nadeau and Joseph Salazar

import javax.swing.*;
import java.awt.*;
import java.lang.StringBuilder;

/*
 * Class to display the console log in a JPanel 
 */

public class LogPanel extends JPanel{
	private JScrollPane log_scroll_pane = null;
    private JTextArea log_text_area     = null;
    private StringBuilder log_text      = null;

    /*
    Default constructor
    Assembles all Swing components
    */
    public LogPanel() {
        setPreferredSize(new Dimension(950, 200));
        setBorder(BorderFactory.createTitledBorder("Log"));

        // JTextArea to display the log.
        log_text_area = new JTextArea(10, 80);
        log_text_area.setEditable(false);
        log_text_area.setLineWrap(true);

        // JScrollPane to make the log scrollable.
        log_scroll_pane = new JScrollPane(log_text_area);

        // Fill the log with example text (to be replaced later)
        log_text = new StringBuilder();
        log_text.append("Sample Text:\n");
        log_text.append("Colonel Mustard joined.\n");
        log_text.append("Miss Scarlet joined.\n");
        log_text.append("Professor Plum joined.\n");
        log_text.append("Colonel Mustard's turn.\n");
        log_text.append("Colonel Mustard rolled a 12.\n");
        log_text.append("Colonel Mustard moved to the Lounge.\n");
        log_text.append("Colonel Mustard suggests that Professor Plum did it in the Lounge with the Knife.\n");
        log_text.append("Miss Scarlet showed a card to Colonel Mustard.\n");
        log_text.append("Miss Scarlet's turn.\n");
        log_text.append("Miss Scarlet rolled a 12.\n");
        log_text.append("Miss Scarlet moved to the Lounge.\n");
        log_text_area.setText(log_text.toString());

        // Add the scroll pane to the panel
        add(log_scroll_pane);
    } // end constructor


    /*
    So client manager can update the console log
    */
    public JTextArea getConsoleTextArea(){
        return log_text_area;
    }
}
