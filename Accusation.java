/************************************************************************
 * Accusation class is called from Board Panel class and implements two 
 * runnables for if player accuses correctly or incorrectly.
 ***********************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Accusation extends JFrame {

    JFrame f;
    JLabel l, l1, l3, l4, I5, I6;
    JComboBox c1,c2,c3;
    JPanel panel;
    JButton send;
    ClientRequestManager crm;

    public Accusation(Runnable correctRun, Runnable incorrectRun, ClientRequestManager crm){
        this.crm = crm;
        initComponents();

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] finalGuess = new String[3];
                Arrays.fill(finalGuess, null);
                finalGuess[0] = (String) c1.getSelectedItem();
                finalGuess[2] = (String) c2.getSelectedItem();
                finalGuess[1] = (String) c3.getSelectedItem();
                if(finalGuess[0] == null || finalGuess[1] == null || finalGuess[2] == null) {
                    JOptionPane.showMessageDialog(null,"Invalid selection",
                            "One or more items isnt selected.",
                            JOptionPane.WARNING_MESSAGE);
                }
                else{
                    int combinedAccusedContent = getCombinedAccusationContentNumber(finalGuess[1],finalGuess[0],finalGuess[2]);
                    crm.requestSubmitAccuseContent(combinedAccusedContent);
                    boolean isAccusationCorrect = crm.requestIsAccusationCorrect();
                    if(isAccusationCorrect)
                        correctRun.run();
                    else
                        incorrectRun.run();

                    f.setVisible(false);
                    dispose();
                }
            }
        });
    }

    private int getCombinedAccusationContentNumber(String accusedChar, String accusedWeapon, String accusedRoom){
        int accusedCharacterNum = getAccusedContentIndex(accusedChar,3);
        int accusedWeaponNum = getAccusedContentIndex(accusedWeapon,1);
        int accusedRoomNum = getAccusedContentIndex(accusedRoom,2);
        return ((accusedCharacterNum + 1) * 100) + ((accusedWeaponNum + 1) * 10) + (accusedRoomNum + 1);
    }

    private int getAccusedContentIndex(String accusedStr, int whichArray){
        if(whichArray == 1){
            String[] weaponArray = ClueGameConstants.WEAPON_NAMES_ARRAY;
            return Arrays.asList(weaponArray).indexOf(accusedStr);
        }
        else if(whichArray == 2){
            String[] roomArray = ClueGameConstants.ROOM_NAMES_ARRAY;
            return Arrays.asList(roomArray).indexOf(accusedStr);
        }
        else{
            String[] characterArray = ClueGameConstants.CHARACTER_NAMES_ARRAY;
            return Arrays.asList(characterArray).indexOf(accusedStr);
        }
    }

    private void initComponents(){
        f = new JFrame("Make An Accusation");
        // create checkbox
        c1 = new JComboBox(ClueGameConstants.WEAPON_NAMES_ARRAY);
        c2 = new JComboBox(ClueGameConstants.ROOM_NAMES_ARRAY);
        c3 = new JComboBox(ClueGameConstants.CHARACTER_NAMES_ARRAY);
        // using setSelectedIndex
        c1.setSelectedIndex(-1);
        c2.setSelectedIndex(-1);
        c3.setSelectedIndex(-1);
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
        panel = new JPanel();
        panel.add(l);
        // add combobox to panel
        panel.add(c1);
        panel.add(l1);
        panel.add(l3);
        // add combobox to panel
        panel.add(c2);
        panel.add(l4);
        // add combobox to panel
        panel.add(I5);
        panel.add(c3);
        panel.add(I6);
        send = new JButton("Send");
        panel.add(send);
        // set a layout for panel
        panel.setLayout(new FlowLayout());
        // add panel to frame
        f.add(panel);
        // set the size of frame
        f.setSize(400, 400);
        setLocationRelativeTo(null);
        f.setVisible(true);
    }
}


