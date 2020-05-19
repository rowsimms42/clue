import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class GameState {
	
	private int availableCharacters;
	private Boolean availableCharactersArray[];
	private int numberOfPlayers;
	private final ConcurrentHashMap<Long, Player> playerMap ;
	private HashMap<String, Characters> characterMap;
	private MovementOptions movementOptions;
	private ArrayList<Card> weaponCardDeck, suspectCardDeck, 
							roomCardDeck, envelopeDeck, combinedDeck;
	private ArrayList<Integer> playerTurnOrderArrayList;
	private int playOrderIndex;
	
	public GameState(){
		playerMap = new ConcurrentHashMap<Long, Player>(); //<- has to be here
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

		movementOptions = new MovementOptions();
		playerTurnOrderArrayList = new ArrayList<>();
		
		availableCharacters = 0;
		numberOfPlayers = 0;
		playOrderIndex = 0;
		
		availableCharactersArray = new Boolean[ ClueGameConstants.MAX_CHARACTERS ];
		Arrays.fill(availableCharactersArray, true);
		
		characterMap    = createAndbuildCharacterMap();
		weaponCardDeck  = createAndFillWeaponCardDeck();
		roomCardDeck    = createAndFillRoomCardDeck();
		suspectCardDeck = createAndFillSuspectCardDeck();
		envelopeDeck    = createAndFillenvelopedeck(); 
		combinedDeck    = createAndFillCombinedDeck();
	}

	public void addTurnOrder(int n) {
		playerTurnOrderArrayList.add(n);
		Collections.sort(playerTurnOrderArrayList);
	}
	
	public ArrayList<Integer> getTurnOrderList(){
		return playerTurnOrderArrayList;
	}
	
	public int getPlayOrderIndex() {
		return playOrderIndex;
	}
	
	public void setPlayOrderIndex(int value) {
		playOrderIndex = value;
	}
	
	public int getNextPlayerTurnNumber() {
		if(playOrderIndex < playerTurnOrderArrayList.size()) {
			return playerTurnOrderArrayList.get(playOrderIndex);
		}
		else {
			setPlayOrderIndex(0);
			return playerTurnOrderArrayList.get(playOrderIndex);
		}
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
	
	public void addPlayer(long threadID, Player player){
		playerMap.put(threadID, player);
	}

	public ConcurrentHashMap<Long, Player> getPlayerMap() {
		return playerMap;
	}
	
	public HashMap<String, Characters> getCharacterMap() {
		return characterMap;
	}

	public void assignPlayerCharacter(Characters character, long threadID){
		playerMap.get(threadID).setCharacter(character);
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

	public HashMap<String, Boolean> getAvailableMoves(int[] locations){
		return movementOptions.getNextMoves(locations, this);
	}
	
	public Player getPlayerByTurnOrder(int turnOrder) {
		for(Entry<Long, Player> p : playerMap.entrySet()) {
			Player tempPlayer = p.getValue();
			if(tempPlayer.getCharacter().getTurnOrder() == turnOrder)
				return tempPlayer;
		}
		return null;
	}
	
	private ArrayList<Card> createAndFillWeaponCardDeck(){
		ArrayList<Card> weaponsList = new ArrayList<Card>();
		weaponsList.add(new WeaponCard("Pipe", 1, 1));
		weaponsList.add(new WeaponCard("Candle Stick", 2, 1));
		weaponsList.add(new WeaponCard("Revolver", 3, 1));
		weaponsList.add(new WeaponCard("Wrench", 4, 1));
		weaponsList.add(new WeaponCard("Knife", 5, 1));
		weaponsList.add(new WeaponCard("Rope", 6, 1));
		Collections.shuffle(weaponsList);
		return weaponsList;
	}
	
	private ArrayList<Card> createAndFillRoomCardDeck(){
		ArrayList<Card> roomList = new ArrayList<Card>();
		roomList.add(new RoomCard("Conservatory", 1, 3));
		roomList.add(new RoomCard("Billiard Room", 2, 3));
		roomList.add(new RoomCard("Study Room", 3, 3));
		roomList.add(new RoomCard("Hall", 4, 3));
		roomList.add(new RoomCard("Lounge", 5, 3));
		roomList.add(new RoomCard("Dining Room", 6, 3));
		roomList.add(new RoomCard("Kitchen", 7, 3));
		roomList.add(new RoomCard("Ballroom", 8, 3));
		roomList.add(new RoomCard("Library", 9, 3));
		Collections.shuffle(roomList);
		return roomList;	
	}
	
	private ArrayList<Card> createAndFillSuspectCardDeck(){
		ArrayList<Card> suspectList = new ArrayList<Card>();
		suspectList.add(new SuspectCard("Colonel Mustard", 1, 2));
		suspectList.add(new SuspectCard("Miss Scarlet", 2, 2));
		suspectList.add(new SuspectCard("Professor Plum", 3, 2));
		suspectList.add(new SuspectCard("Mr. Green", 4, 2));
		suspectList.add(new SuspectCard("Mrs. White", 5, 2));
		suspectList.add(new SuspectCard("Mrs. Peacock", 6, 2));
		Collections.shuffle(suspectList);
		return suspectList;
	}
	
	private ArrayList<Card> createAndFillenvelopedeck(){
		ArrayList<Card> envelopeList = new ArrayList<Card>();
		//add the top card from the weapon card deck to the envelope
		envelopeList.add(weaponCardDeck.get(0)); 
		weaponCardDeck.remove(0); 
		//add the top card from the suspect card deck to the envelope
		envelopeList.add(suspectCardDeck.get(0)); 
		suspectCardDeck.remove(0);
		//add the top card from the room card deck to the envelope
		envelopeList.add(roomCardDeck.get(0)); 
		roomCardDeck.remove(0);
		return envelopeList;
	}
	
	private ArrayList<Card> createAndFillCombinedDeck(){
		ArrayList<Card> combinedList = new ArrayList<Card>();
		//transfer all cards from the weapon card desk to the combined deck
		for(Card card: weaponCardDeck ){ 
			combinedList.add(card);
		}
		weaponCardDeck.clear();
		//transfer all cards from the suspect card desk to the combined deck
		for(Card card: suspectCardDeck ){ 
			combinedList.add(card);
		}
		suspectCardDeck.clear();
		//transfer all cards from the room card desk to the combined deck
		for(Card card: roomCardDeck ){ 
			combinedList.add(card);
		}
		roomCardDeck.clear();
		//shuffle the combined deck and return it
		Collections.shuffle(combinedList);
		return combinedList; 
	}
	
	private HashMap<String, Characters> createAndbuildCharacterMap() {
		HashMap<String, Characters> characterMap = new HashMap<String, Characters>();
		for(int i = 0; i < ClueGameConstants.MAX_CHARACTERS;i++) {
			String charName = ClueGameConstants.CHARACTER_NAMES_ARRAY[i];
			switch(i) {
				case 0 : characterMap.put(charName, new Characters(charName, 261123, 9, 24, 4)); break; //green
				case 1 : characterMap.put(charName, new Characters(charName, 10290172, 0, 5, 6)); break; //plum
				case 2 : characterMap.put(charName, new Characters(charName, 16777212, 14, 24, 3)); break; //white
				case 3 : characterMap.put(charName, new Characters(charName, 16576515, 23, 7, 2)); break; //mustard
				case 4 : characterMap.put(charName, new Characters(charName, 16515918, 16, 0, 1)); break; //scarlett
				case 5 : characterMap.put(charName, new Characters(charName, 234748, 0, 18, 5)); break; //peacock
				default: //nothing
			}
		}
		return characterMap;
	}
	
	public void dealCardsToPlayers() {	
		
		final int TOP_OF_DECK = 0; 
		while(!combinedDeck.isEmpty()){	
			for(long id: playerMap.keySet()){
				if(combinedDeck.size() > 0 ){
					Player tempPlayer = playerMap.get(id);	
					tempPlayer.getPlayerDeck().add(combinedDeck.get(TOP_OF_DECK));					
					combinedDeck.remove(TOP_OF_DECK);	 		
    			}	
			}
		}
	 }
	
} //end class
