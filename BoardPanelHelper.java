/*******************************************************************
 * Contains various helper functions that assist Board Panel class.
 * Functions are called from Board Panel class.
 ******************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoardPanelHelper {

    public BoardPanelHelper(){
        //empty default constructor
    }

    public String[] assignExitOptions(int roomNum){
        String[] options = null;
        if(roomNum == 1 || roomNum == 4 || roomNum == 7 || roomNum == 8)
            options = new String[]{"1"};
        else if(roomNum == 2 || roomNum == 3 || roomNum == 9)
            options = new String[]{"1","2"};
        else if(roomNum == 6)
            options = new String[]{"1","2","3"};
        else
            options = new String[]{"1","2","3","4"};
        return options;
    }

    public ClueGameConstants.DOORS findSpecificDoor(int roomNumber, int doorSelection){
        for(ClueGameConstants.DOORS doors : ClueGameConstants.DOORS.values()){
            if(doors.getRoomNumber() == roomNumber)
                if(doors.getDoorID() == doorSelection)
                    return doors;
        }
        return null;
    }

    public boolean determineIfSelectedDoorIsBlocked(int xSpace, int ySpace, HashMap<Long, Player> playerMap){
        for(Map.Entry<Long, Player> player : playerMap.entrySet()) {
            Player p = player.getValue();
            if(p.getCurrentXLocation() == xSpace && p.getCurrentYLocation() == ySpace)
                return true;
        }
        return false;
    }


    public ArrayList<Boolean[]> getAttemptedDoorsList(){
        ArrayList<Boolean[]> attemptedDoorsListArray = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            int roomNumber = i + 1;
            switch (roomNumber){
                case 1: attemptedDoorsListArray.add(new Boolean[]{false}); break;
                case 2: attemptedDoorsListArray.add(new Boolean[]{false,false}); break;
                case 3: attemptedDoorsListArray.add(new Boolean[]{false,false}); break;
                case 4: attemptedDoorsListArray.add(new Boolean[]{false}); break;
                case 5: attemptedDoorsListArray.add(new Boolean[]{false,false,false,false,false}); break;
                case 6: attemptedDoorsListArray.add(new Boolean[]{false,false, false}); break;
                case 7: attemptedDoorsListArray.add(new Boolean[]{false}); break;
                case 8: attemptedDoorsListArray.add(new Boolean[]{false}); break;
                case 9: attemptedDoorsListArray.add(new Boolean[]{false,false}); break;
                default: break;
            }
        }
        return attemptedDoorsListArray;
    }

    public int determineTrueAmountInDoorArray(Boolean[] doorArray){
        int count = 0;
        for(boolean bValue : doorArray){
            if(bValue) count++;
        }
        return count;
    }

    public boolean isRoomAShortCutRoom(int roomNumber){
        return roomNumber == 1 || roomNumber == 8 || roomNumber == 4 || roomNumber == 7;
    }

    public Rectangle getBounds(int x, int y) {
        //increase x offset by 30
        //increase y offset by 16
        return new Rectangle(x + 30,y + 16,20,20);
    }

    public int getCombinedSuggestionContentNumber(String charName, String weaponName, int roomNumber){
        int characterSuggestionValue = getSuggestionNumValue(charName, ClueGameConstants.CHARACTER_NAMES_ARRAY);
        int weaponSuggestionValue    = getSuggestionNumValue(weaponName, ClueGameConstants.WEAPON_NAMES_ARRAY);
        return (characterSuggestionValue * 100) + (weaponSuggestionValue * 10) + roomNumber;
    }

    private int getSuggestionNumValue(String inputString, String[] arrayToSearch){
        int i = 0;
        for(String strToFind : arrayToSearch){
            if(strToFind.equals(inputString))
                return i+1;
            i++;
        }
        return i;
    }

    public int getDoorId(int row, int col) {
        for(ClueGameConstants.DOORS door : ClueGameConstants.DOORS.values()) {
            if(door.getRow() == row && door.getCol() == col)
                return door.getRoomNumber();
        }
        return 0;
    }

    public int getDirection(int row, int col) {
        for(ClueGameConstants.DOORS door : ClueGameConstants.DOORS.values()) {
            if(door.getRow() == row && door.getCol() == col)
                return door.getDirection();
        }
        return 0;
    }

    /*
    public Player getPlayerFromMap(String characterName, HashMap<Long, Player> map) {
        for()
        return map.get(player.getPlayerId());
    } */

    public String getNameFromAccusingString(String inputStr){
        int index = inputStr.indexOf("-");
        return inputStr.substring(0, index);
    }

    public String getNameFromSuggestedString(String inputStr){
        int colonIndex = inputStr.indexOf(":");
        int commaIndex = inputStr.indexOf(",");
        return inputStr.substring(colonIndex+2, commaIndex);
    }
}
