
public class GameState {
	
	private int availableCharacters;
	private Boolean availableCharactersArray[];
	
	
	public GameState() {
		
		initializeVariables();
		
		
	} //end constructor

	private void initializeVariables(){
	
		setAvailableCharacters(0);
		
		availableCharactersArray = new Boolean[ ClueGameConstants.MAX_CHARACTERS ];
		for(int i = 0; i < ClueGameConstants.MAX_CHARACTERS; i++) {
			availableCharactersArray[i] = true;
		}
		
		//TODO initialize more variables as needed
		
	}

	private void setAvailableCharacters(int availableCharacters) {
		this.availableCharacters = availableCharacters;
	}
	
	public int getAvailableCharacters() {
		return availableCharacters;
	}

	
	//Index should be between 1 - 6
	public boolean isSpecificCharacterAvailable(int index) {
		return (availableCharactersArray[index - 1] == true) ;
	}
	
	
	private void setSpecificCharacterToUnavailable(int index ) {
		
		availableCharacters = setNthBit(availableCharacters, index - 1);
		
		availableCharactersArray[index - 1] = false;
		
	}
	
	private int setNthBit(int number, int n) {
		
		return ((1 << n) | number);
	}
	
	
} //end class
