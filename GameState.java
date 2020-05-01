import java.net.Socket;

public class GameState {
	
	private int availableCharacters;
	private Boolean availableCharactersArray[];
	private int numberOfPlayers;

	private static enum Weapon{
		CandleStick,
		Dagger,
		LeadPipe,
		Revolver,
		Rope,
		Wrench
	}
	
	public GameState(){

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
	
		setAvailableCharacters(0);
		
		availableCharactersArray = new Boolean[ ClueGameConstants.MAX_CHARACTERS ];
		for(int i = 0; i < ClueGameConstants.MAX_CHARACTERS; i++) {
			availableCharactersArray[i] = true;
		}
		
		//TODO initialize more variables as needed
		
	}


	private void setAvailableCharacters(int avChrts) {
		availableCharacters = avChrts;
	}
	
	public int getAvailableCharacters() {
		return availableCharacters;
	}

	
	//Index should be between 0, 5
	public boolean isSpecificCharacterAvailable(int index) {
		if(availableCharactersArray[index] == true){
			return true;
		} else{
			return false;
		}
	}
	
	
	/*
	 * Character:
	 * 0: Mr. Green
	 * 1: Professor Plumb
	 * 2: Mrs. White
	 * 3: Colonel Mustard
	 * 4: Miss Scarlet 
	 * 5: Mrs. Peacock
	 */
	public void setSpecificCharacterToUnavailable(int index ) {
		
		availableCharacters = setNthBit(availableCharacters, index - 1);
		
		availableCharactersArray[index - 1] = false;
		
	}
	
	private int setNthBit(int number, int n) {
		
		return ((1 << n) | number);
	}
	
	public  void setNumberOfCharacters(){
		for(Socket s : Server.clientSocketList){
			numberOfPlayers++;
		}
	}

	private int getNumberOfPlayers(){
		for(Socket s : Server.clientSocketList){
			numberOfPlayers++;
		}
		return numberOfPlayers;
	}


	
} //end class
