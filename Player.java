import java.io.Serializable;
import java.util.ArrayList;
import java.io.Serializable;

public class Player implements Serializable{

    private Characters playerCharacter;
    private int[] locationArray = {getCurrentXLocation(),getCurrentYLocation()};
    private long Id;
    private ArrayList<Card> playerCardDeck;
    private int currentXLocation, currentYLocation;
    
    public Player(long Id){
        this.Id = Id;
        playerCardDeck = new ArrayList<Card>();
    }

    public long getPlayerId(){
        return Id;
    }

    public void setCharacter(Characters character) {
        playerCharacter = character;
        setCurrentXLocation(playerCharacter.getxStarting());
        setCurrentYLocation(playerCharacter.getyStarting());
    }
    
    public Characters getCharacter() {
    	return playerCharacter;
    }

    public String getName(){
       return playerCharacter.getName();
    }

    public void setLocation(int[] playerLocation){
        int xLoc = 0, yLoc = 1;
        setCurrentXLocation(playerLocation[xLoc]);
        setCurrentXLocation(playerLocation[yLoc]);
    }

    public int[] getLocationArray(){
        return locationArray;
    }

    public ArrayList<Card> getPlayerDeck(){
        return playerCardDeck;
    }

    public void setCurrentXLocation(int value){
        currentXLocation = value;
    }

    public void setCurrentYLocation(int value){
        currentYLocation = value;
    }

    public int getCurrentXLocation(){
        return currentXLocation;
    }

    public int getCurrentYLocation(){
        return currentYLocation;
    }
} //end class

