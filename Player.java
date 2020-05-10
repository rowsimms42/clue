import java.io.Serializable;
import java.util.ArrayList;
import java.io.Serializable;

public class Player implements Serializable{

    private Characters playerCharacter;
    private int[] locationArray = {0,0};
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
        setCurrentXLocation(playerLocation[0]);
        setCurrentYLocation(playerLocation[1]);
    }

    public int[] getLocationArray(){
        return locationArray;
    }

    public ArrayList<Card> getPlayerDeck(){
        return playerCardDeck;
    }

    public void setCurrentXLocation(int value){
        locationArray[0] = currentXLocation = value;
    }

    public void setCurrentYLocation(int value){
        locationArray[1] = currentYLocation = value;
    }

    public int getCurrentXLocation(){
        return currentXLocation;
    }

    public int getCurrentYLocation(){
        return currentYLocation;
    }
} //end class

