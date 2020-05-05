import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import javax.smartcardio.Card;

import javax.smartcardio.Card;

import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

public class GameState {
	
	private int availableCharacters;
	private Boolean availableCharactersArray[];
	private int numberOfPlayers;
	private HashMap<Long, Player> playerMap;
	
	ArrayList<Cards> weaponCardDeck, suspectCardDeck, roomCardDeck, envelopeDeck; 
	


	public GameState(){
		
		initializeVariables();
	}
	
	
	/*
	 * Need to set up call to new Game state and
	 * Initialize all data that need to be set
	 * from a call at the same time that the server
	 * starts functioning. There is only 1 GameState
	 * and it is accessed through the GameHandler
	 * so the data can be undated and shared with 
	 * the clients 
	 */

	public void initializeVariables(){
	
		availableCharacters = 0;

		numberOfPlayers = 0;
		
		availableCharactersArray = new Boolean[ ClueGameConstants.MAX_CHARACTERS ];
		for(int i = 0; i < ClueGameConstants.MAX_CHARACTERS; i++) {
			availableCharactersArray[i] = true;
		}
		
		playerMap = new HashMap<Long, Player>();

		//TODO initialize more variables as needed
		
		weaponCardDeck = new ArrayList<Cards>();
		weaponCardDeck.add(new WeaponCard("Pipe", 1, 1));
		weaponCardDeck.add(new WeaponCard("Candle Stick", 2, 1));
		weaponCardDeck.add(new WeaponCard("Revolver", 3, 1));
		weaponCardDeck.add(new WeaponCard("Wrench", 4, 1));
		weaponCardDeck.add(new WeaponCard("Knife", 5, 1));
		weaponCardDeck.add(new WeaponCard("Rope", 6, 1));
		Collections.shuffle(weaponCardDeck);


		//evenlop set up for weapon
		Random random = new Random();
		int rand = random.nextInt(weaponCardDeck.size()); 
		envelopeDeck.add(weaponCardDeck.get(rand));
		weaponCardDeck.remove(rand); 

	// ArrayList<Cards> weaponCardDeck, suspectCardDeck, roomCardDeck, envelopeDeck; 

		suspectCardDeck = new ArrayList<Cards>();
		suspectCardDeck.add(new SuspectCard("Colonel Mustard", 1, 2));
		suspectCardDeck.add(new SuspectCard("Miss Scarlet", 2, 2));
		suspectCardDeck.add(new SuspectCard("Professor Plum", 3, 2));
		suspectCardDeck.add(new SuspectCard("Mr. Green", 4, 2));
		suspectCardDeck.add(new SuspectCard("Mrs. White", 5, 2));
		suspectCardDeck.add(new SuspectCard("Mrs. Peacock", 6, 2));
		Collections.shuffle(suspectCardDeck);

			//evenlop set up for suspects
		//Random random = new Random();
		int srand = random.nextInt(suspectCardDeck.size()); 
		envelopeDeck.add(suspectCardDeck.get(srand));
		suspectCardDeck.remove(srand); 

		roomCardDeck = new ArrayList<Cards>();
		roomCardDeck.add(new RoomCard("Conservatory", 1, 3));
		roomCardDeck.add(new RoomCard("Billiard Room", 2, 3));
		roomCardDeck.add(new RoomCard("Study Room", 3, 3));
		roomCardDeck.add(new RoomCard("Hall", 4, 3));
		roomCardDeck.add(new RoomCard("Lounge", 5, 3));
		roomCardDeck.add(new RoomCard("Dining Room", 6, 3));
		roomCardDeck.add(new RoomCard("Kitchen", 7, 3));
		roomCardDeck.add(new RoomCard("Ballroom", 8, 3));
		roomCardDeck.add(new RoomCard("Library", 9, 3));
		Collections.shuffle(roomCardDeck);


		// final card for evenlop, adds the room card
		int	Rrand = random.nextInt(roomCardDeck.size());
		envelopeDeck.add(roomCardDeck.get(Rrand));
		roomCardDeck.remove(Rrand); 


	}

	public int getAvailableCharacters() {
		return availableCharacters;
	}

	public boolean isSpecificCharacterAvailable(int index) {
		return (availableCharactersArray[index - 1] == true);
	}
	
	public void setSpecificCharacterToUnavailable(int index ) {
		/* Character: 0 - Mr. Green, 1 - Professor Plumb, 2 - Mrs. White,
		   3 - Colonel Mustard, 4 - Miss Scarlet, 5 - Mrs. Peacock */
		availableCharacters = setNthBit(availableCharacters, index - 1);
		
		availableCharactersArray[index - 1] = false;
	}
	
	private int setNthBit(int number, int n) {
		return ((1 << n) | number);
	}
	
	public  void setNumberOfPlayers(int n){
		numberOfPlayers = n;
	}

	public int getNumberOfPlayers(){
		return numberOfPlayers;
	}
	
	public void addPlayer(Player player, long threadID){
		playerMap.put(threadID, player);
	}

	public HashMap getPlayerMap(){
		return playerMap;
	} 

	public void assignPlayerName(String name, long threadID){
		playerMap.get(threadID).setName(name);
	}

	public String getCharacterName(int index){
		return ClueGameConstants.CHARACTER_NAMES_ARRAY[index - 1];
	}

	public int rollDice(){
		Random rand = new Random();

		int dice_1 = rand.nextInt(7);
		int dice_2 = rand.nextInt(7);

		return dice_1 + dice_2;
	}
	
} //end class
