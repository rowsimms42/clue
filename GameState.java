import java.util.HashMap;

public class GameState {
	
	private int availableCharacters;
	private Boolean availableCharactersArray[];
	private int numberOfPlayers;
	private HashMap<Long, Player> playerMap;

	private String[] characterNames = {
		"0",
		"1",
		"2",
		"3",
		"4",
		"5"
	};

	private static enum Weapon{
		CandleStick,
		Dagger,
		LeadPipe,
		Revolver,
		Rope,
		Wrench
	}
	


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
		
		//playerMap = new HashMap<Long, Player>();

		//TODO initialize more variables as needed
		
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


	public String getCharacterName(int index){
		return characterNames[index - 1];
	}


	public void setPlayerName(int characterIndex, long ID) {
		playerMap.get(ID).setName(getCharacterName(characterIndex));
	}

	public void setPlayerLocation(int[] loc,  long ID){
		playerMap.get(ID).setLocation(loc);
	}
	
} //end class
