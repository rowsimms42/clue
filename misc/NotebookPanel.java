
//Contributors: Jonah Dubbs-Nadeau and Joseph Salazar
/*
import javax.swing.*;
import java.lang.*;
import java.nio.channels.NetworkChannel;
import java.awt.event.*;
import java.awt.*;



public class NotebookPanel extends JPanel {
	
	private JTextArea editable_text_area     = null, 
					  non_editable_text_area = null;
	private JScrollPane editable_scrollpane     = null,
						non_editable_scrollpane = null;
	private JButton submit_note_button    = null;
	private StringBuilder notebook_string = null;
	private JFrame joption_pane_frame     = null;
	private JLabel add_note_label = null;
	private int note_counter = 0;
	
	
    public NotebookPanel() {
        setLayout(new GridLayout(8, 1));
        setBorder(BorderFactory.createTitledBorder("Clue Notebook"));
        setPreferredSize(new Dimension(250, 800));
        setBackground(new Color(42,250,160));
        
        //Frame for JOptionpane (no text error)
        joption_pane_frame = new JFrame();
        
        //string builder for all notes added
        notebook_string = new StringBuilder();
        
        //JTextArea to type notes to be added to notebook
        editable_text_area = new JTextArea(10, 20);
        editable_text_area.setEditable(true);
        editable_text_area.setLineWrap(true);
        
        //TJTextArea to view all game notes. 
        non_editable_text_area = new JTextArea(10, 20);
        non_editable_text_area.setEditable(false);
        non_editable_text_area.setLineWrap(true);
        
        //JScrollPane's to make JTextArea's scrollable
        editable_scrollpane     = new JScrollPane(editable_text_area);
        non_editable_scrollpane = new JScrollPane(non_editable_text_area);
        editable_scrollpane.setPreferredSize(new Dimension(50, 100));
        non_editable_scrollpane.setPreferredSize(new Dimension(50,100));
        
        //JButton to submit text to non_editable_text_area
        submit_note_button = new JButton("Add Note");
        submit_note_button.setPreferredSize(new Dimension(150,300));
        
        add_note_label = new JLabel("Add note here");
        
        //add both panes and button to the panel
        add(this.add_note_label);
        add(this.editable_scrollpane);
        add(this.non_editable_scrollpane);
        add(this.submit_note_button);
        
      
        submit_note_button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String note_to_add = editable_text_area.getText();
                if(note_to_add.isEmpty()){
                    
                    JOptionPane.showMessageDialog(joption_pane_frame, "No Text Entered");
                }
                else{
                    //Assign text from editable_text_area to non_editable_text_area
                    note_counter++;
                    notebook_string.append("Note ");
                    notebook_string.append(noteCounterToString(note_counter));
                    notebook_string.append(": ");
                    notebook_string.append(note_to_add);
                    notebook_string.append("\n");
                    non_editable_text_area.setText(notebook_string.toString());
                    editable_text_area.setText("");
                }
            }
        }); //end button action listener
        
    } //end constructor
    
    
    private String noteCounterToString(int number){
    	return Integer.toString(number);
    }

} 
*/
//end class

//TODO display number of players(server connections). 

