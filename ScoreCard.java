import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;

public class ScoreCard extends JPanel{
    JLabel lblSuspects, lblWeapons, lblRooms;
    JCheckBox chckbxMrsWhite, chckbxCandlestick, chckbxMrGreen, chckbxMissScarlett, chckbxProfessorPlum;
    JCheckBox chckbxMrsPeacock, chckbxKnife, chckbxRevolver, chckbxRope, chckbxLeadP, chckbxWrench;
    JCheckBox chckbxLibrary, chckbxKitchen, chckbxBilliardRoom, chckbxBallroom, chckbxConservatory;
    JCheckBox chckbxDiningHall, chckbxLounge, chckbxHall, chckbxStudy;

    public ScoreCard(){
        this.setBounds(704, 222, 227, 310);

        lblSuspects = new JLabel("Suspects");
	    		lblSuspects.setForeground(new Color(255, 0, 0));
	    		lblSuspects.setFont(new Font("SansSerif", Font.BOLD, 14));
	    		lblSuspects.setBounds(29, 0, 70, 18);
	    		this.add(lblSuspects);
	    		
	    		lblWeapons = new JLabel("Weapons");
	    		lblWeapons.setForeground(new Color(0, 153, 51));
	    		lblWeapons.setFont(new Font("SansSerif", Font.BOLD, 14));
	    		lblWeapons.setBounds(152, -1, 70, 21);
	    		this.add(lblWeapons);
	    		
	    		lblRooms = new JLabel("Rooms");
	    		lblRooms.setForeground(new Color(0, 51, 153));
	    		lblRooms.setFont(new Font("SansSerif", Font.BOLD, 14));
	    		lblRooms.setBounds(69, 161, 58, 21);
	    		this.add(lblRooms);
	    		
	    		JCheckBox chckbxMustard = new JCheckBox("Colonel Mustard");
	    		chckbxMustard.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxMustard.setBounds(6, 44, 121, 21);
                chckbxMustard.setFocusPainted(false);
	    		this.add(chckbxMustard);
	    		
	    		JCheckBox chckbxMrsWhite = new JCheckBox("Mrs. White");
	    		chckbxMrsWhite.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxMrsWhite.setBounds(6, 67, 93, 21);
                chckbxMrsWhite.setFocusPainted(false);
	    		this.add(chckbxMrsWhite);
	    		
	    		JCheckBox chckbxCandlestick = new JCheckBox("Candlestick");
	    		chckbxCandlestick.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxCandlestick.setBounds(133, 68, 93, 21);
                chckbxCandlestick.setFocusPainted(false);
	    		this.add(chckbxCandlestick);
	    		
	    		JCheckBox chckbxMrGreen = new JCheckBox("Mr. Green");
	    		chckbxMrGreen.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxMrGreen.setBounds(6, 90, 93, 21);
                chckbxMrGreen.setFocusPainted(false);
	    		this.add(chckbxMrGreen);
	    		
	    		JCheckBox chckbxMissScarlett = new JCheckBox("Miss Scarlett");
	    		chckbxMissScarlett.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxMissScarlett.setBounds(6, 24, 104, 18);
                chckbxMissScarlett.setFocusPainted(false);
	    		this.add(chckbxMissScarlett);
	    		
	    		JCheckBox chckbxProfessorPlum = new JCheckBox("Professor Plum");
	    		chckbxProfessorPlum.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxProfessorPlum.setBounds(6, 113, 111, 21);
                chckbxProfessorPlum.setFocusPainted(false);
	    		this.add(chckbxProfessorPlum);
	    		
	    		chckbxMrsPeacock = new JCheckBox("Mrs. Peacock");
	    		chckbxMrsPeacock.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxMrsPeacock.setBounds(6, 136, 104, 21);
                chckbxMrsPeacock.setFocusPainted(false);
	    		this.add(chckbxMrsPeacock);
	    		
	    		chckbxKnife = new JCheckBox("Knife");
	    		chckbxKnife.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxKnife.setBounds(133, 23, 93, 21);
                chckbxKnife.setFocusPainted(false);
	    		this.add(chckbxKnife);
	    		
	    		chckbxRevolver = new JCheckBox("Revolver");
	    		chckbxRevolver.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxRevolver.setBounds(133, 44, 93, 21);
                chckbxRevolver.setFocusPainted(false);
	    		this.add(chckbxRevolver);
	    		
	    		chckbxRope = new JCheckBox("Rope");
	    		chckbxRope.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxRope.setBounds(133, 91, 93, 21);
                chckbxRope.setFocusPainted(false);
	    		this.add(chckbxRope);
	    		
	    		chckbxLeadP = new JCheckBox("Lead Pipe");
	    		chckbxLeadP.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxLeadP.setBounds(133, 114, 93, 21);
                chckbxLeadP.setFocusPainted(false);
	    		this.add(chckbxLeadP);
	    		
	    		chckbxWrench = new JCheckBox("Wrench");
	    		chckbxWrench.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxWrench.setBounds(133, 137, 93, 21);
                chckbxWrench.setFocusPainted(false);
	    		this.add(chckbxWrench);
	    		
	    		chckbxLibrary = new JCheckBox("Library");
	    		chckbxLibrary.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxLibrary.setBounds(6, 188, 93, 21);
                chckbxLibrary.setFocusPainted(false);
	    		this.add(chckbxLibrary);
	    		
	    		chckbxKitchen = new JCheckBox("Kitchen");
	    		chckbxKitchen.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxKitchen.setBounds(6, 211, 93, 21);
                chckbxKitchen.setFocusPainted(false);
	    		this.add(chckbxKitchen);
	    		
	    		chckbxBilliardRoom = new JCheckBox("Billiard Room");
	    		chckbxBilliardRoom.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxBilliardRoom.setBounds(6, 234, 104, 21);
                chckbxBilliardRoom.setFocusPainted(false);
	    		this.add(chckbxBilliardRoom);
	    		
	    		chckbxBallroom = new JCheckBox("Ballroom");
	    		chckbxBallroom.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxBallroom.setBounds(6, 257, 104, 21);
                chckbxBallroom.setFocusPainted(false);
	    		this.add(chckbxBallroom);
	    		
	    		chckbxConservatory = new JCheckBox("Conservatory");
	    		chckbxConservatory.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxConservatory.setBounds(6, 283, 104, 21);
                chckbxConservatory.setFocusPainted(false);
	    		this.add(chckbxConservatory);
	    		
	    		chckbxDiningHall = new JCheckBox("Dining Hall");
	    		chckbxDiningHall.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxDiningHall.setBounds(114, 188, 93, 21);
                chckbxDiningHall.setFocusPainted(false);
	    		this.add(chckbxDiningHall);
	    		
	    		chckbxLounge = new JCheckBox("Lounge");
	    		chckbxLounge.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxLounge.setBounds(114, 212, 93, 21);
                chckbxLounge.setFocusPainted(false);
	    		this.add(chckbxLounge);
	    		
	    		chckbxHall = new JCheckBox("Hall");
	    		chckbxHall.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxHall.setBounds(114, 235, 93, 21);
                chckbxHall.setFocusPainted(false);
	    		this.add(chckbxHall);
	    		
	    		chckbxStudy = new JCheckBox("Study");
	    		chckbxStudy.setFont(new Font("SansSerif", Font.BOLD, 11));
                chckbxStudy.setBounds(114, 258, 93, 21);
                chckbxStudy.setFocusPainted(false);
	    		this.add(chckbxStudy);

    }
}