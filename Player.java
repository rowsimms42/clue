import java.io.Serializable;
import java.util.ArrayList;
import java.io.Serializable;

public class Player implements Serializable{

    private Characters playerCharacter;
    private int[] locationArray = {0,0};
    private long Id;
    private ArrayList<Card> playerCardDeck;
    
    public Player(long Id){
        this.Id = Id;
        playerCardDeck = new ArrayList<Card>();
    }

    public long getPlayerId(){
        return Id;
    }

    public void setCharacter(Characters character) {
    	playerCharacter = character;
    }
    
    public Characters getCharacter() {
    	return playerCharacter;
    }

    public String getName(){
       return playerCharacter.getName();
    }

    public void setLocation(int[] playerLocation){
        for (int i = 0; i <playerLocation.length; i++){
            this.locationArray[i] = playerLocation[i];
        }
    }

    public ArrayList<Card> getPlayerDeck(){
        return playerCardDeck;
    }

}

