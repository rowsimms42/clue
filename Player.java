import java.util.ArrayList;

import javax.smartcardio.Card;

public class Player {

    private String name;
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

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
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

