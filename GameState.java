import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class GameState {
	
	private int availableCharacters;
	private Boolean availableCharactersArray[];
	private int numberOfPlayers;
	private HashMap<Long, Player> playerMap;
	private HashMap<String, Characters> characterMap;
	private int[][] boardcoords = new int[ClueGameConstants.BOARD_ROWS][ClueGameConstants.BOARD_COLS];
	
	
	private ArrayList<Card> weaponCardDeck, suspectCardDeck, 
							roomCardDeck, envelopeDeck, combinedDeck;
	
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

		initBoardCoords();
	
		availableCharacters = 0;

		numberOfPlayers = 0;
		
		availableCharactersArray = new Boolean[ ClueGameConstants.MAX_CHARACTERS ];
		Arrays.fill(availableCharactersArray, true);
		
		playerMap = new HashMap<Long, Player>();
		characterMap    = createAndbuildCharacterMap();
		weaponCardDeck  = createAndFillWeaponCardDeck();
		roomCardDeck    = createAndFillRoomCardDeck();
		suspectCardDeck = createAndFillSuspectCardDeck();

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

	public HashMap<Long, Player> getPlayerMap(){
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
	
	private HashMap<String, Characters> createAndbuildCharacterMap() {
		HashMap<String, Characters> characterMap = new HashMap<String, Characters>();
		for(int i = 0; i < ClueGameConstants.MAX_CHARACTERS;i++) {
			String charName = ClueGameConstants.CHARACTER_NAMES_ARRAY[i];
			switch(i) {
				case 0 : characterMap.put(charName, new Characters(charName, 261123, 111,222)); break;
				case 1 : characterMap.put(charName, new Characters(charName, 10290172,333,444)); break;
				case 2 : characterMap.put(charName, new Characters(charName, 16777212,555,600)); break;
				case 3 : characterMap.put(charName, new Characters(charName, 16576515,245,345)); break;
				case 4 : characterMap.put(charName, new Characters(charName, 16515918,400,500)); break;
				case 5 : characterMap.put(charName, new Characters(charName, 234748, 341,410)); break;
				default: //nothing		
			}
		}
		return characterMap;
	}
	

	private HashMap<String, Boolean> setMovesU() {
		HashMap <String, Boolean> returnMap = new HashMap<>();
		returnMap.put("left", false);
		returnMap.put("right", false);
		returnMap.put("up", true);
		returnMap.put("down", false);
		return returnMap;
	}

	private HashMap<String, Boolean> setMovesR() {
		HashMap <String, Boolean> returnMap = new HashMap<>();
		returnMap.put("left", false);
		returnMap.put("right", true);
		returnMap.put("up", false);
		returnMap.put("down", false);
		return returnMap;
	}

	private HashMap<String, Boolean> setMovesD() {
		HashMap <String, Boolean> returnMap = new HashMap<>();
		returnMap.put("left", false);
		returnMap.put("right", false);
		returnMap.put("up", false);
		returnMap.put("down", true);
		return returnMap;
	}

	private HashMap<String, Boolean> setMovesL() {
		HashMap <String, Boolean> returnMap = new HashMap<>();
		returnMap.put("left", true);
		returnMap.put("right", false);
		returnMap.put("up", false);
		returnMap.put("down", false);
		return returnMap;
	}

	private HashMap<String, Boolean> setMovesUR() {
		HashMap <String, Boolean> returnMap = new HashMap<>();
		returnMap.put("left", false);
		returnMap.put("right", true);
		returnMap.put("up", true);
		returnMap.put("down", false);
		return returnMap;
	}

	private HashMap<String, Boolean> setMovesUD() {
		HashMap <String, Boolean> returnMap = new HashMap<>();
		returnMap.put("left", false);
		returnMap.put("right", false);
		returnMap.put("up", true);
		returnMap.put("down", true);
		return returnMap;
	}

	private HashMap<String, Boolean> setMovesUL() {
		HashMap <String, Boolean> returnMap = new HashMap<>();
		returnMap.put("left", true);
		returnMap.put("right", false);
		returnMap.put("up", true);
		returnMap.put("down", false);
		return returnMap;
	}
	
	private HashMap<String, Boolean> setMovesRD() {
		HashMap <String, Boolean> returnMap = new HashMap<>();
		returnMap.put("left", false);
		returnMap.put("right", true);
		returnMap.put("up", false);
		returnMap.put("down", true);
		return returnMap;
	}

	private HashMap<String, Boolean> setMovesLR() {
		HashMap <String, Boolean> returnMap = new HashMap<>();
		returnMap.put("left", true);
		returnMap.put("right", true);
		returnMap.put("up", false);
		returnMap.put("down", false);
		return returnMap;
	}

	private HashMap<String, Boolean> setMovesLD() {
		HashMap <String, Boolean> returnMap = new HashMap<>();
		returnMap.put("left", true);
		returnMap.put("right", false);
		returnMap.put("up", false);
		returnMap.put("down", true);
		return returnMap;
	}

	private HashMap<String, Boolean> setMovesURD() {
		HashMap <String, Boolean> returnMap = new HashMap<>();
		returnMap.put("left", false);
		returnMap.put("right", true);
		returnMap.put("up", true);
		returnMap.put("down", true);
		return returnMap;
	}

	private HashMap<String, Boolean> setMovesUDL() {
		HashMap <String, Boolean> returnMap = new HashMap<>();
		returnMap.put("left", true);
		returnMap.put("right", false);
		returnMap.put("up", true);
		returnMap.put("down", true);
		return returnMap;
	}

	private HashMap<String, Boolean> setMovesRLD() {
		HashMap <String, Boolean> returnMap = new HashMap<>();
		returnMap.put("left", true);
		returnMap.put("right", true);
		returnMap.put("up", false);
		returnMap.put("down", true);
		return returnMap;
	}

	private HashMap<String, Boolean> setMovesULR() {
		HashMap <String, Boolean> returnMap = new HashMap<>();
		returnMap.put("left", true);
		returnMap.put("right", true);
		returnMap.put("up", true);
		returnMap.put("down", false);
		return returnMap;
	}

	private HashMap<String, Boolean> setMovesLRUD() {
		HashMap <String, Boolean> returnMap = new HashMap<>();
		returnMap.put("left", true);
		returnMap.put("right", true);
		returnMap.put("up", true);
		returnMap.put("down", true);
		return returnMap;
	}

	private HashMap<String, Boolean> setMovesNone() {
		HashMap <String, Boolean> returnMap = new HashMap<>();
		returnMap.put("left", false);
		returnMap.put("right", false);
		returnMap.put("up", false);
		returnMap.put("down", false);
		return returnMap;
	}


	public HashMap<String, Boolean> getNextMoves(int[] location){
		
		HashMap<String, Boolean> availableMoves = null;
		
		if(location.length > 2){
			System.out.println("Error in passing location x,y");
			return null;
		}
		int a = location[0];
		int b = location [1];
		int loc = boardcoords[a][b];
		switch (loc) {
			case 1:
				availableMoves = setMovesR();
				break;
			case 2:
				availableMoves = setMovesL();
				break;
			case 3:
				availableMoves = setMovesD();
				break;
			case 4:
				availableMoves = setMovesU();
				break;
			case 5:
				availableMoves = setMovesRD();
				break;
			case 6:
				availableMoves = setMovesLD();
				break;
			case 7:
				availableMoves = setMovesUR();
				break;
			case 8:
				availableMoves = setMovesUL();
				break;
			case 9: 
				availableMoves = setMovesRLD();
				break;
			case 10:
				availableMoves = setMovesULR();
				break;
			case 11:
				availableMoves = setMovesURD();
				break;
			case 12:
				availableMoves = setMovesUDL();
				break;
			case 13:
				availableMoves = setMovesLRUD();
				break;
			case 14:
				availableMoves = setMovesLR();
				break;
			default:
				availableMoves = setMovesNone();
				System.out.println("Error in getNextMoves switch statement");
				break;
		}
		return availableMoves;
	}

	private void initBoardCoords(){

		//Right only
		boardcoords[5][0] = 1;
		boardcoords[11][1] = 1;
		boardcoords[18][0] = 1;
		
		//left only
		boardcoords[7][23] = 2;
		boardcoords[17][23] = 2;

		//down only
		boardcoords[0][7] = 3;
		boardcoords[0][16] = 3;
		
		//up only
		boardcoords[24][9] = 4;
		boardcoords[24][14] = 4;

		//right and down
		boardcoords[4][1] = 5;
		boardcoords[17][1] = 5;
		boardcoords[1][15] = 5;
		boardcoords[10][6] = 5;
		boardcoords[23][14] = 5;
		
		//left and down
		boardcoords[1][8] = 6;
		boardcoords[23][9] = 6;
		boardcoords[15][18] = 6;
		boardcoords[6][22] = 6;
		
		//right and up
		boardcoords[6][6] = 7;
		boardcoords[19][5] = 7;
		boardcoords[22][6] = 7;
		boardcoords[23][7] = 7;
		
		//left and up
		boardcoords[23][16] = 8;
		boardcoords[8][22] = 8;
		boardcoords[22][17] = 8;
		
		//left, right, down
		boardcoords[4][2] = 9;
		boardcoords[4][3] = 9;
		boardcoords[4][4] = 9;
		boardcoords[4][5] = 9;
		boardcoords[4][6] = 9;

		boardcoords[6][17] = 9;
		boardcoords[6][18] = 9;
		boardcoords[6][19] = 9;
		boardcoords[6][20] = 9;
		boardcoords[6][21] = 9;

		boardcoords[7][14] = 9;

		boardcoords[15][9] = 9;
		boardcoords[15][10] = 9;
		boardcoords[15][11] = 9;
		boardcoords[15][12] = 9;
		boardcoords[15][13] = 9;

		boardcoords[15][16] = 9;
		boardcoords[15][17] = 9;

		boardcoords[17][2] = 9;
		boardcoords[17][3] = 9;
		boardcoords[17][4] = 9;
		boardcoords[17][5] = 9;

		boardcoords[16][19] = 9;
		boardcoords[16][20] = 9;
		boardcoords[16][21] = 9;

		//left right up
		boardcoords[5][1] = 10;
		boardcoords[5][2] = 10;
		boardcoords[5][3] = 10;
		boardcoords[5][4] = 10;
		boardcoords[5][5] = 10;

		boardcoords[8][16] = 10;
		boardcoords[8][17] = 10;
		boardcoords[8][18] = 10;
		boardcoords[8][19] = 10;
		boardcoords[8][20] = 10;
		boardcoords[8][21] = 10;

		boardcoords[16][8] = 10;
		boardcoords[16][9] = 10;
		boardcoords[16][10] = 10;
		boardcoords[16][11] = 10;
		boardcoords[16][12] = 10;
		boardcoords[16][13] = 10;
		boardcoords[16][14] = 10;
		boardcoords[16][15] = 10;
		boardcoords[16][15] = 10;

		boardcoords[17][18] = 10;
		boardcoords[17][19] = 10;
		boardcoords[17][20] = 10;
		boardcoords[17][21] = 10;
		boardcoords[17][22] = 10;

		boardcoords[18][1] = 10;
		boardcoords[18][2] = 10;
		boardcoords[18][3] = 10;
		boardcoords[18][4] = 10;

		
		//up down right
		boardcoords[1][7] = 11;
		boardcoords[2][7] = 11;
		boardcoords[3][7] = 11;

		boardcoords[7][7] = 11;
		boardcoords[8][7] = 11;
		boardcoords[9][7] = 11;

		boardcoords[12][6] = 11;
		boardcoords[13][6] = 11;
		boardcoords[14][6] = 11;
		boardcoords[15][6] = 11;
		boardcoords[16][6] = 11;

		boardcoords[20][6] = 11;
		boardcoords[21][6] = 11;

		boardcoords[2][15] = 11;
		boardcoords[3][15] = 11;
		boardcoords[4][15] = 11;
		boardcoords[5][15] = 11;
		boardcoords[6][15] = 11;

		boardcoords[8][14] = 11;
		boardcoords[9][14] = 11;
		boardcoords[10][14] = 11;
		boardcoords[11][14] = 11; 
		boardcoords[12][14] = 11;
		boardcoords[13][14] = 11;
		boardcoords[14][14] = 11;

		boardcoords[17][16] = 11;
		boardcoords[18][16] = 11;
		boardcoords[19][16] = 11;
		boardcoords[20][16] = 11;
		boardcoords[21][16] = 11;
		boardcoords[22][16] = 11;

		//up down left
		boardcoords[2][8] = 12;
		boardcoords[3][8] = 12;
		boardcoords[4][8] = 12;
		boardcoords[5][8] = 12;
		boardcoords[6][8] = 12;

		boardcoords[8][8] = 12;
		boardcoords[9][8] = 12;
		boardcoords[10][8] = 12;
		boardcoords[11][8] = 12;
		boardcoords[12][8] = 12;
		boardcoords[13][8] = 12;
		boardcoords[14][8] = 12;

		boardcoords[17][7] = 12;
		boardcoords[18][7] = 12;
		boardcoords[19][7] = 12;
		boardcoords[20][7] = 12;
		boardcoords[21][7] = 12;
		boardcoords[22][7] = 12;

		boardcoords[1][16] = 12;
		boardcoords[2][16] = 12;
		boardcoords[3][16] = 12;
		boardcoords[4][16] = 12;
		boardcoords[5][16] = 12;

		boardcoords[9][15] = 12;
		boardcoords[10][15] = 12;
		boardcoords[11][15] = 12;
		boardcoords[12][15] = 12;
		boardcoords[13][15] = 12;
		boardcoords[14][15] = 12;

		boardcoords[18][17] = 12;
		boardcoords[19][17] = 12;
		boardcoords[20][17] = 12;
		boardcoords[21][17] = 12;


		//left right up down
		boardcoords[4][7] = 13;
		boardcoords[5][6] = 13;
		boardcoords[5][7] = 13;
		boardcoords[6][7] = 13;
		boardcoords[7][8] = 13;

		boardcoords[10][7] = 13;
		boardcoords[11][6] = 13;
		boardcoords[11][7] = 13;
		boardcoords[12][7] = 13;
		boardcoords[13][7] = 13;
		boardcoords[14][7] = 13;
		boardcoords[15][7] = 13;
		boardcoords[16][7] = 13;

		boardcoords[17][6] = 13;
		boardcoords[18][6] = 13;
		boardcoords[18][5] = 13;
		boardcoords[19][6] = 13;

		boardcoords[16][16] = 13;
		boardcoords[16][17] = 13;
		boardcoords[16][18] = 13;

		boardcoords[15][14] = 13;
		boardcoords[15][15] = 13;

		boardcoords[6][16] = 13;

		boardcoords[7][15] = 13;
		boardcoords[7][16] = 13;
		boardcoords[7][17] = 13;
		boardcoords[7][18] = 13;
		boardcoords[7][19] = 13;
		boardcoords[7][20] = 13;
		boardcoords[7][21] = 13;
		boardcoords[7][22] = 13;

		boardcoords[8][15] = 13;
	
		//left right
		boardcoords[7][9] = 14;
		boardcoords[7][10] = 14;
		boardcoords[7][11] = 14;
		boardcoords[7][12] = 14;
		boardcoords[7][13] = 14;

		boardcoords[11][2] = 14;
		boardcoords[11][3] = 14;
		boardcoords[11][4] = 14;
		boardcoords[11][5] = 14;

		boardcoords[23][8] = 14;
		boardcoords[23][15] = 14;
	}


	public static void main(String[] args) {
		GameState gameState = new GameState();

		gameState.initBoardCoords();

		int[] testlocation = new int[2];
		testlocation[0] = 11;
		testlocation[1] = 4;

		HashMap<String, Boolean> test = gameState.getNextMoves(testlocation);

		System.out.println(Collections.singletonList(test));
	}
} //end class
