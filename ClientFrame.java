
//Contributors: Jonah Dubbs-Nadeau and Joseph Salazar

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ClientFrame extends JFrame {
	BoardPanel board_panel = null;
    LogPanel log_panel = null;
    NotebookPanel notebook_panel = null;

    public ClientFrame(){
        board_panel = new BoardPanel();
        log_panel = new LogPanel();
        notebook_panel = new NotebookPanel();
        this.setPreferredSize(new Dimension(1250, 800));

        JPanel left_pane = new JPanel(new BorderLayout());
        left_pane.add(board_panel, BorderLayout.CENTER);
        left_pane.add(log_panel, BorderLayout.SOUTH);

        JPanel right_pane = new JPanel(new BorderLayout());
        right_pane.add(notebook_panel, BorderLayout.CENTER);

        getContentPane().add(left_pane, BorderLayout.CENTER);
        getContentPane().add(right_pane, BorderLayout.EAST);
        pack();
    } // end constructor
} // end class

//TODO Option to end game. 