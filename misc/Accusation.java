import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*; 



    public class Accusation extends JFrame {
      boolean WasICoreect = false;
       JFrame f; 
    // label 
     JLabel l, l1, l3, l4, I5, I6; 
    // combobox 
    public  JComboBox c1,c2,c3;

      public Accusation(){

         // create a new frame 
        f = new JFrame("frame"); 
        // create a object 
        //ItemListener s = (ItemListener) new solve();
        // array of string contating cities 
        String s1[] = { "---", "Pipe", "Candle Stick","Revolver","Wrench","Knife", "Rope"}; 
        String s2[] = { "---", "Conservatory", "Billiard Room", "Study Room", "Hall", "Lounge", "Dining Room", "Kitchen", "Ballroom", "Library" }; 
        String s3[] = { "---", "Mr. Green","Professor Plum" , "Mrs. White", "Colonel Mustard", "Miss Scarlet", "Mrs. Peacock" }; 
        // create checkbox 
        c1 = new JComboBox(s1); 
        c2 = new JComboBox(s2); 
        c3 = new JComboBox(s3); 
        // set Kolakata and male as selected items 
        // using setSelectedIndex 
        c1.setSelectedIndex(0); 
        c2.setSelectedIndex(0); 
        c3.setSelectedIndex(0); 
        // add ItemListener 
        c1.addItemListener(s); 
       // c2.addItemListener(s); 
       // c3.addItemListener(s); 
        // set the checkbox as editable 
        c1.setEditable(true); 
        c2.setEditable(true); 
        c3.setEditable(true); 
        // create labels 
        l = new JLabel("select your weapon"); 
        l1 = new JLabel("weapon selected"); 
        l3 = new JLabel("select your Room "); 
        l4 = new JLabel("room selected"); 
        I5 = new JLabel("select your suspect");
        I6 = new JLabel("person selected");
        // set color of text 
        l.setForeground(Color.red); 
        l1.setForeground(Color.blue); 
        l3.setForeground(Color.red); 
        l4.setForeground(Color.blue); 
        I5.setForeground(Color.red); 
        I6.setForeground(Color.blue); 
        // create a new panel 
        JPanel p = new JPanel(); 
        p.add(l); 
        // add combobox to panel 
        p.add(c1); 
        p.add(l1); 
        p.add(l3); 
        // add combobox to panel 
        p.add(c2); 
        p.add(l4); 
        // add combobox to panel 
        p.add(I5);
        p.add(c3);
        p.add(I6);
        JButton send = new JButton("Send");
        p.add(send);
      // set a layout for panel 
        p.setLayout(new FlowLayout()); 
        // add panel to frame 
        f.add(p); 
          // set the size of frame 
        f.setSize(400, 400); 
           // new JButton
           // panel.add(send);
        f.show(); 

        send.addActionListener(new ActionListener() {

          public void actionPerformed(ActionEvent e) {
            setVisible(false);
            String GuessFinal [] = {"---","---","---"};
            GuessFinal[0] = (String) c1.getSelectedItem();
            GuessFinal[1] = (String) c2.getSelectedItem();
            GuessFinal[2] = (String) c3.getSelectedItem();
            //try {
             // send string to server
             int numberCorrect = 0;
             ArrayList<Card> envlopeDeck = BoardPanel.crm.requestEnvelopeCardDeck();
             for(int i = 0; i < 3;i++){
              if(GuessFinal[i].equals((envlopeDeck.get(i).getName()))){
                 numberCorrect++;
                 }
          }
          if(numberCorrect == 3){
            WasICoreect = true;
          }
             //WasICoreect = 

              if(WasICoreect == true){

              }
             // run you win code
              else{
                
              }
             // run you loss code
            //}
          }
        });
      }
    public void itemStateChanged(ItemEvent e)  
    {   // if the state combobox 1is changed 
        if (e.getSource() == c1) { 
            l1.setText(c1.getSelectedItem() + " selected"); 
        } 
        // if state of combobox 2 is changed 
       if(e.getSource() == c2){
            l4.setText(c2.getSelectedItem() + " selected"); }
       else
       I6.setText(c3.getSelectedItem() + " selected"); 
    } 
  
} 

/*
public boolean DidIWin(String cheaker[]){
    boolean wasicorrect = false;
    int numberCorrect = 0;
    ArrayList<Card> temp;
    temp = GameState.getEnvelopeDeck();
     for(int i = 0; i < 3;i++){
         if(cheaker[i].equals((temp.get(i).getName()))){
            numberCorrect++;
            }
     }
     
     if(numberCorrect > 2){
         wasicorrect = true;
     }
    return wasicorrect;
}
*/
///ArrayList<card> envlopeDeck = crm.requestEnvelopeCardDeck();
