import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;

public class Player implements Serializable{

    private Characters playerCharacter;
    private int[] locationArray = {0,0};
    private long Id;
    private ArrayList<Card> playerCardDeck;
    private int currentXLocation, currentYLocation;
    /*Rooms in the ROOM enum have a id value of 1 - 10.
    If player is in a room, then roomNumber will have a 
    value between 1 - 10. If not in a room, then value is 0*/
    private int roomNumber = 0; 
    private boolean isPlayerTurn = false;
    private boolean isBeingSuggested = false;
    private boolean isBeingAccused = false;
    private boolean isGoingFirst = false;
    private boolean isCanStartgame = false;
	
    
    public Player(long Id){
        this.Id = Id;
        playerCardDeck = new ArrayList<Card>();
    }

    //Copy constructor
    public Player(Player oldPlayer) {
    	this.playerCardDeck =  new ArrayList<Card>();
    	this.playerCharacter = oldPlayer.playerCharacter;
    	this.locationArray = oldPlayer.locationArray;
    	this.Id = oldPlayer.Id;
    	this.playerCardDeck = oldPlayer.playerCardDeck;
    	//Collections.copy(this.playerCardDeck,oldPlayer.playerCardDeck);
    	this.currentXLocation = oldPlayer.currentXLocation;
    	this.currentYLocation = oldPlayer.currentYLocation;
    	this.roomNumber = oldPlayer.roomNumber;
    	this.isPlayerTurn = oldPlayer.isPlayerTurn;
    	this.isBeingSuggested = oldPlayer.isBeingSuggested;
    	this.isBeingAccused = oldPlayer.isBeingAccused;
    	this.isGoingFirst = oldPlayer.isGoingFirst;
    	this.isCanStartgame = oldPlayer.isCanStartgame;
    }
    
    public long getPlayerId(){
        return Id;
    }
    
    public void setIsCanStartGame(boolean value) {
    	isCanStartgame = value;
    }
    
    public boolean getIsCanStartGame() {
    	return isCanStartgame;
    }
    
    public void setIsGoingFirst(boolean value) {
    	isGoingFirst = value;
    }
    
    public boolean getIsGoingFirst() {
    	return isGoingFirst;
    }
    
    public void setIsBeingSuggest(boolean value) {
    	isBeingSuggested = value;
    }
    
    public boolean getIsBeingSuggested() {
    	return isBeingSuggested;
    }
    
    public void setIsBeingAccused(boolean value) {
    	isBeingAccused = value;
    }
    
    public boolean getIsBeingAccused() {
    	return isBeingAccused;
    }
    
    public void setRoomLocation(int roomNumber){
        this.roomNumber = roomNumber;
    }

    public int getRoomLocation(){
        return roomNumber;
    }
    
    public void setIsPlayerTurn(boolean value) {
    	isPlayerTurn = value;
    }
    
    public boolean getIsPlayerTurn() {
    	return isPlayerTurn;
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

