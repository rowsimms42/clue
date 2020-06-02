import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class GameState {

    private int availableCharacters;
    private Boolean[] availableCharactersArray;
    private int numberOfPlayers;
    private final ConcurrentHashMap<Long, Player> playerMap ;
    private HashMap<String, Characters> characterMap;
    private MovementOptions movementOptions;
    private ArrayList<Card> weaponCardDeck, suspectCardDeck,
            roomCardDeck, envelopeDeck, combinedDeck;
    private ArrayList<Integer> playerTurnOrderArrayList;
    private ArrayList<Characters> nonPlayingCharactersArrayList;
    private int playOrderIndex;
    private boolean isGameStarted;
    private boolean isSuggestionMade;
    private StringBuilder stringBuilder;
    private String suggestedCharacterStr, suggestedWeaponStr, suggestedRoomStr;
	private ArrayList<String[]> revealedCardsList;
	private int suggestionCount;

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
        nonPlayingCharactersArrayList = new ArrayList<>();
        revealedCardsList = new ArrayList<>();

        availableCharacters = 0;
        numberOfPlayers = 0;
        playOrderIndex = 0;
        isGameStarted = false;
		isSuggestionMade = false;
		suggestionCount = 0;

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

    public void removeFromTurnOrder(Player currentPlayer){
        int currentPlayerTurnOrderNumber = currentPlayer.getCharacter().getTurnOrder();
        playerTurnOrderArrayList.remove((Integer) currentPlayerTurnOrderNumber);
    }

    public ArrayList<Integer> getTurnOrderList(){
        return playerTurnOrderArrayList;
    }

    public ArrayList<Card> getEnvelopeDeck(){
        return envelopeDeck;
    }

    public ArrayList<Characters> getNonPlayingCharactersArrayList(){
        return nonPlayingCharactersArrayList;
    }

    public ArrayList<String[]> getRevealedCardsList(){return revealedCardsList;}

    public int getPlayOrderIndex() {
        return playOrderIndex;
    }

    public void setPlayOrderIndex(int value) {
        playOrderIndex = value;
    }

    public void setIsGameStarted(boolean value) { isGameStarted = value; }

    public boolean getIsGameStarted() {
        return isGameStarted;
    }

    public boolean getIsSuggestionMade(){
        return isSuggestionMade;
    }

    public void setIsSuggestionMade(boolean value){
        isSuggestionMade = value;
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
        return (availableCharactersArray[index - 1]);
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

    public void setSpecificCharacterToAvailable(int index){
        //TODO use setNthBit to reverse character availability
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

    public void removePlayer(long threadID){
        playerMap.remove(threadID);
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

    public String getSuggestionContentString(){ return stringBuilder.toString(); }

	/**
	 * 
	 */
	public void incrementSuggestionCount(){
		suggestionCount++;
		if (suggestionCount == (numberOfPlayers - 1)){
			suggestionCount = 0;
			isSuggestionMade = false;
		}
	}


    public int rollDice(){
        Random rand = new Random();
        int dice_1 = rand.nextInt(6) + 1;
        int dice_2 = rand.nextInt(6) + 1;
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
        for(int i = 0; i < 6; i++) {
            String weaponName = ClueGameConstants.WEAPON_NAMES_ARRAY[i];
            weaponsList.add(new WeaponCard(weaponName, i+1, 1));
        }
        Collections.shuffle(weaponsList);
        return weaponsList;
    }

    private ArrayList<Card> createAndFillRoomCardDeck(){
        ArrayList<Card> roomList = new ArrayList<Card>();
        for(int i = 0; i < 9; i++) {
            String roomName = ClueGameConstants.ROOM_NAMES_ARRAY[i];
            roomList.add(new RoomCard(roomName, i+1, 3));
        }
        Collections.shuffle(roomList);
        return roomList;
    }

    private ArrayList<Card> createAndFillSuspectCardDeck(){
        ArrayList<Card> suspectList = new ArrayList<Card>();
        for(int i = 0; i < 6; i++){
            String characterName = ClueGameConstants.CHARACTER_NAMES_ARRAY[i];
            suspectList.add(new SuspectCard(characterName,i+1, 2));
        }
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
        //transfer all cards from the weapon card desk to the combined deck
        ArrayList<Card> combinedList = new ArrayList<Card>(weaponCardDeck);
        weaponCardDeck.clear();
        //transfer all cards from the suspect card desk to the combined deck
        combinedList.addAll(suspectCardDeck);
        suspectCardDeck.clear();
        //transfer all cards from the room card desk to the combined deck
        combinedList.addAll(roomCardDeck);
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
        Player currentPlayer, newPlayer;
        final int TOP_OF_DECK = 0;
        while(!combinedDeck.isEmpty()){
            for(long id : playerMap.keySet()){
                if(combinedDeck.size() > 0 ){
                    currentPlayer = (Player) playerMap.get(id);
                    currentPlayer.getPlayerDeck().add(combinedDeck.get(TOP_OF_DECK));
                    newPlayer = new Player(currentPlayer);
                    playerMap.put(newPlayer.getPlayerId(), newPlayer);
                    combinedDeck.remove(TOP_OF_DECK);
                }
            }
        }
    }

    public boolean findIfPlayerCanStartGame(Player player) {
        long lowestIDNumber = Collections.min(playerMap.keySet());
        return player.getPlayerId() == lowestIDNumber;
    }

    public void buildlistOfAllNonPlayingCharacters(){
        nonPlayingCharactersArrayList.clear();
        for(int i = 0; i < ClueGameConstants.MAX_CHARACTERS; i++) {
            String characterName = ClueGameConstants.CHARACTER_NAMES_ARRAY[i];
            if(!isCharacterInMap(characterName)) {
                //this character was not assigned to a player in the player map
                Characters character = characterMap.get(characterName);
                nonPlayingCharactersArrayList.add(character);
            }
        }
    }

    public boolean isCharacterInMap(String name) {
        for(long id : playerMap.keySet()){
            Characters character = playerMap.get(id).getCharacter();
            if(character.getName().equals(name))
                return true;
        }
        return false;
    }

    public Player getPlayerFromMap(String characterName){
        for(long id : playerMap.keySet()) {
            Player player = playerMap.get(id);
            if(player.getName().equals(characterName))
                return player;
        }
        return null;
    }

    public void buildSuggestionString(int suggestedCharacter, int suggestedWeapon, int suggestedRoom, Player currentPlayer){
        stringBuilder = new StringBuilder();
        String suggestingCharacter = currentPlayer.getName();
        suggestedCharacterStr = ClueGameConstants.CHARACTER_NAMES_ARRAY[suggestedCharacter - 1];
        suggestedWeaponStr = ClueGameConstants.WEAPON_NAMES_ARRAY[suggestedWeapon - 1];
        suggestedRoomStr = ClueGameConstants.ROOM_NAMES_ARRAY[suggestedRoom - 1];
        stringBuilder.append(suggestingCharacter);
        stringBuilder.append("---> I suggest: ").append(suggestedCharacterStr);
        stringBuilder.append(", with the ").append(suggestedWeaponStr);
        stringBuilder.append(", in the ").append(suggestedRoomStr);
    }

    public void buildRevealedCardsList(){
        Player newPlayer;
        if(!revealedCardsList.isEmpty()) revealedCardsList.clear();
        String characterName, cardName;
        ArrayList<String> tempListCardsFoundInPlayerDeck = new ArrayList<>();
        for(long id : playerMap.keySet()){
            Player player = playerMap.get(id);
            characterName = player.getName();
            for(int i = 0; i < player.getPlayerDeck().size(); i++){
                Card tempCard = player.getPlayerDeck().get(i);
                fillTempListCardsFound(tempCard, tempListCardsFoundInPlayerDeck);
            }
            if(tempListCardsFoundInPlayerDeck.size() >= 2) {
                cardName = getRandomCardToReveal(tempListCardsFoundInPlayerDeck);
                player.setCardSelectedToReveal(cardName);
                player.setAmountOfSuggestedCardsInDeck(tempListCardsFoundInPlayerDeck.size());
                newPlayer = new Player(player);
                playerMap.put(newPlayer.getPlayerId(), newPlayer);
            }
            else if(tempListCardsFoundInPlayerDeck.size() == 1) {
                cardName = tempListCardsFoundInPlayerDeck.get(0);
                player.setCardSelectedToReveal(cardName);
                player.setAmountOfSuggestedCardsInDeck(tempListCardsFoundInPlayerDeck.size());
                newPlayer = new Player(player);
                playerMap.put(newPlayer.getPlayerId(), newPlayer);
            }
            else {
                cardName = "no cards";
                player.setCardSelectedToReveal(cardName);
                player.setAmountOfSuggestedCardsInDeck(tempListCardsFoundInPlayerDeck.size());
                newPlayer = new Player(player);
                playerMap.put(newPlayer.getPlayerId(), newPlayer);
            }
            revealedCardsList.add(new String[]{characterName,cardName});
            tempListCardsFoundInPlayerDeck.clear();
        }
    }

    private void fillTempListCardsFound(Card card, ArrayList<String> tempCardList){
        if(card.getName().equals(suggestedCharacterStr))
            tempCardList.add(card.getName());
        else if(card.getName().equals(suggestedWeaponStr))
            tempCardList.add(card.getName());
        else if(card.getName().equals(suggestedRoomStr))
            tempCardList.add(card.getName());
    }

    private String getRandomCardToReveal(ArrayList<String> cardList){
        Random random = new Random();
        int max = cardList.size() - 1;
        int min = 1;
        int randomNumber = random.nextInt((max-min) + 1) + min;
        int randomIndex = randomNumber - 1;
        return cardList.get(randomIndex);
    }

} //end class
