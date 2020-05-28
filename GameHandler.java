import java.util.ArrayList;
import java.util.HashMap;


public class GameHandler {

    GameState gameState;
    //System.out.println(gameState);
    int characterIndex;
    HashMap<Long, Player> playerMapTemp;
    GameHandler(GameState gameState){
    	//System.out.println("Game state: " + gameState);
        this.gameState = gameState;
        playerMapTemp = new HashMap<Long, Player>();
    }

    public GameState getGameState() {
    	return this.gameState;
    }
    
    /* Increment the number of players in the game state class */
    public void incrementAmountOfPlayers(){
        gameState.setNumberOfPlayers( gameState.getNumberOfPlayers() + 1);
    }

    //add player to the game state
    public void addPlayerToGame(long ID, Player player){
        gameState.addPlayer(ID, player);
        //System.out.println("Adding player to gamestate address: " + gameState);
    }

    //remove player from game
    public void removePlayerFromGame(long ID, Player player){
       String name = player.getName();
       /* Character: 0 - Mr. Green, 1 - Professor Plumb, 2 - Mrs. White,
		   3 - Colonel Mustard, 4 - Miss Scarlet, 5 - Mrs. Peacock */
       switch (name) {
           case "Mr. Green": gameState.setSpecificCharacterToAvailable(0);
               break;
            case "Professor Plumb": gameState.setSpecificCharacterToAvailable(1);
                break;
            case "Mrs. White": gameState.setSpecificCharacterToAvailable(2);
                break;
            case "Colonel Mustard": gameState.setSpecificCharacterToAvailable(3);
                break;
            case "Miss Scarlet": gameState.setSpecificCharacterToAvailable(4);
                break;
            case "Mrs. Peacock": gameState.setSpecificCharacterToAvailable(5);
           default:
               break;
       }
       
        gameState.removePlayer(ID);
        //TODO make character the player was assigned to available.

    }

    //return the number of players from the game state 
    public int getNumberOfCurrentPlayers(){
        return gameState.getNumberOfPlayers();
    }

    public Message parseMessage(Message msgObj, long threadID)
    {
        Message returnMessage;
        Player tempPlayer, nextPlayer;
        Characters tempCharacter;
        HashMap<Long, Player> playerMapTemp = new  HashMap<Long, Player>();
        ArrayList<Card> playersDeck = new ArrayList<>();
        ArrayList<Characters> nonPlayingCharList; 

        int msgID = msgObj.getMessageID();
       // System.out.println("Incoming to server MessageID: " + msgID);
        switch (msgID) {

            case ClueGameConstants.REQUEST_AVAILABLE_CHARACTERS:
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_AVAILABLE_CHARACTERS, 
                                Integer.valueOf(gameState.getAvailableCharacters()));
                //System.out.println("Client Requests available characters");    
                return returnMessage;

            case ClueGameConstants.REQUEST_IS_SELECTED_CHARACTER_AVAILABLE:
                characterIndex = (Integer) msgObj.getData();
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_IS_CHARACTER_AVAILABLE,
                                Boolean.valueOf(gameState.isSpecificCharacterAvailable(characterIndex)));
                //System.out.println("Client Requests if character is available");    
                return returnMessage;

            case ClueGameConstants.REQUEST_INDEED_CHARACTER_AVAILABLE: 
                characterIndex = (Integer) msgObj.getData();
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_IS_CHARACTER_AVAILABLE,
                                Boolean.valueOf(gameState.isSpecificCharacterAvailable(characterIndex)));
                //System.out.println("Client wants to confirm that character is indeed available"); 
                return returnMessage;
                
            case ClueGameConstants.REQUEST_MARK_CHARACTER_AS_TAKEN:
            	//System.out.println("Client wants server to mark character is taken");
                characterIndex = (Integer) msgObj.getData();
                gameState.setSpecificCharacterToUnavailable(characterIndex);
                tempCharacter = (Characters) gameState.getCharacterMap().get(gameState.getCharacterName(characterIndex));
                tempPlayer = new Player((Player) gameState.getPlayerMap().get(threadID));
                tempPlayer.setCharacter(tempCharacter);
                gameState.getPlayerMap().put(threadID, tempPlayer);
                gameState.addTurnOrder(tempPlayer.getCharacter().getTurnOrder());
                //System.out.println("Character chosen: " + tempPlayer.getName());
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_CONFIRM_CHARACTER_SELECTED, null);
                return returnMessage;
                
            case ClueGameConstants.REQUEST_PLAYER_ID:
                tempPlayer = (Player) gameState.getPlayerMap().get(threadID);
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_PLAYER_ID, 
                                Long.valueOf(tempPlayer.getPlayerId()));
                return returnMessage; 
            
            case ClueGameConstants.REQUEST_DICE_ROLL:
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_DICE_ROLL, 
                                Integer.valueOf(gameState.rollDice()));
                return returnMessage;
             
            case ClueGameConstants.REQUEST_MOVEMENT_BUTTON_VALUES:
                int[] locCoords = (int[]) msgObj.getData();
                tempPlayer = new Player((Player) gameState.getPlayerMap().get(threadID));
                tempPlayer.setLocation(locCoords);
                gameState.getPlayerMap().put(threadID, tempPlayer);
                HashMap<String, Boolean> buttonValues = gameState.getAvailableMoves(tempPlayer.getLocationArray());
                StringBuilder sb = new StringBuilder();
                for (String value : buttonValues.keySet()){
                    boolean val = buttonValues.get(value);
                    int boolToIntVal = (val == false) ? 0 : 1;
                    sb.append(boolToIntVal);
                }
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_MOVEMENT_BUTTON_VALUES, String.valueOf(sb));
                return returnMessage;
                
            case ClueGameConstants.REQUEST_PLAYER_MAP:
            	playerMapTemp.clear();
            	playerMapTemp.putAll(gameState.getPlayerMap());
            	returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_PLAYER_MAP, playerMapTemp);
            	return returnMessage;
            	
            case ClueGameConstants.REQUEST_PLAYER_OBJECT:
            	tempPlayer = (Player) gameState.getPlayerMap().get(threadID);
            	returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_PLAYER_OBJECT, tempPlayer);
            	return returnMessage;
            
            case ClueGameConstants.REQUEST_IS_CURRENT_TURN:
            	//System.out.print("Requesting if current turn.");
            	tempPlayer = (Player) gameState.getPlayerMap().get(threadID);
            	returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_IS_CURRENT_TURN, 
            					Boolean.valueOf(tempPlayer.getIsPlayerTurn()));
            	return returnMessage;
            
            case ClueGameConstants.REQUEST_MARK_PLAYER_END_TURN:
            	tempPlayer = new Player((Player) gameState.getPlayerMap().get(threadID));
            	tempPlayer.setIsPlayerTurn(false);
            	gameState.getPlayerMap().put(threadID, tempPlayer);
            	int nextTurnOrder = gameState.getNextPlayerTurnNumber();
            	gameState.setPlayOrderIndex(gameState.getPlayOrderIndex() + 1);
            	nextPlayer = (Player)gameState.getPlayerByTurnOrder(nextTurnOrder);
            	if(nextPlayer == null) System.out.println("Next turn player was null"); 
            	tempPlayer = new Player((Player) gameState.getPlayerMap().get(nextPlayer.getPlayerId()));
            	tempPlayer.setIsPlayerTurn(true);
            	gameState.getPlayerMap().put(tempPlayer.getPlayerId(), tempPlayer);
            	returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_CONFIRM_PLAYER_END_TURN,null);
            	return returnMessage; 
            	
            case ClueGameConstants.REQUEST_UPDATE_PLAYER_ROOM_NUMBER:
            	int roomNumber = (int) msgObj.getData();
            	tempPlayer = new Player((Player) gameState.getPlayerMap().get(threadID));
            	tempPlayer.setRoomLocation(roomNumber);
            	gameState.getPlayerMap().put(threadID, tempPlayer);
            	returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_CONFIRM_UPDATE_PLAYER_ROOM_NUMBER, null);
            	return returnMessage; 
            	
            case ClueGameConstants.REQUEST_DOES_CURRENT_PLAYER_GO_FIRST:
            	 //int recievedTurnOrderNumber = (Integer) msgObj.getData();
            	 tempPlayer = (Player) gameState.getPlayerMap().get(threadID);
            	 int firstTurnNumberInList = gameState.getTurnOrderList().get(0);
            	 boolean doesGoFirst = (tempPlayer.getCharacter().getTurnOrder() == firstTurnNumberInList) ?
            			 true : false;
            	 if(doesGoFirst) {
            		 tempPlayer = new Player((Player) gameState.getPlayerMap().get(threadID));
            		 tempPlayer.setIsGoingFirst(doesGoFirst); //true
            		 gameState.getPlayerMap().put(threadID, tempPlayer);
            		 gameState.setPlayOrderIndex(gameState.getPlayOrderIndex() + 1);
            	 }
            	 returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_DOES_CURRENT_PLAYER_GO_FIRST,
            			 		 Boolean.valueOf(tempPlayer.getIsGoingFirst()));
            	 return returnMessage; 
            	 
            case  ClueGameConstants.REQUEST_CAN_PLAYER_START_GAME:
            	tempPlayer = (Player) gameState.getPlayerMap().get(threadID);
            	boolean isCanPlayerStartGame = gameState.findIfPlayerCanStartGame(tempPlayer);
            	if(isCanPlayerStartGame) {
            		tempPlayer = new Player((Player) gameState.getPlayerMap().get(threadID));
            		tempPlayer.setIsCanStartGame(true);
            		gameState.getPlayerMap().put(threadID, tempPlayer);
            	}
            	returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_CAN_PLAYER_START_GAME,
           			 			Boolean.valueOf(tempPlayer.getIsCanStartGame()));
           	 	return returnMessage; 
           	 	
            case ClueGameConstants.REQUEST_TO_START_GAME:
            	gameState.setIsGameStarted(true);
            	returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_CONFIRM_START_GAME, null);
            	return returnMessage; 
            	
            case ClueGameConstants.REQUEST_IF_GAME_HAS_STARTED:
            	boolean isHasGameStarted = gameState.getIsGameStarted();
            	returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_IF_GAME_HAS_STARTED,
             			 		Boolean.valueOf(isHasGameStarted));
            	return returnMessage;
            	
            case ClueGameConstants.REQUEST_DEAL_CARDS:
            	gameState.dealCardsToPlayers();
            	returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_CONFIRM_DEAL_CARDS, null);
            	return returnMessage;
            	
            case ClueGameConstants.REQUEST_PlAYERS_DECK:
            	tempPlayer = (Player) gameState.getPlayerMap().get(threadID);
            	playersDeck.clear();
            	for(int i = 0; i < tempPlayer.getPlayerDeck().size(); i++) {
            		 playersDeck.add(tempPlayer.getPlayerDeck().get(i));
            	}
            	returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_CONFIRM_PlAYERS_DECK,
            				    playersDeck);
            	return returnMessage;
            case ClueGameConstants.REQUEST_LIST_OF_NON_PLAYING_CHARACTERS:
            	nonPlayingCharList = gameState.getNonPlayingCharactersArrayList();
            	returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_CONFIRM_LIST_OF_NON_PLAYING_CHARACTERS,
            					nonPlayingCharList);
            	return returnMessage;
            	
            case ClueGameConstants.REQUEST_BUILD_NON_PLAYING_CHAR_LIST:
            	gameState.buildlistOfAllNonPlayingCharacters();
            	returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_CONFIRM_BUILD_NON_PLAYING_CHAR_LIST, null);
                return returnMessage;
                
            case ClueGameConstants.REQUEST_IF_SUGGESTION_MADE:
                returnMessage = new Message(ClueGameConstants.REPLY_IF_SUGGESTION_MADE, gameState.getIsSuggestionMade());
                return returnMessage;

            case ClueGameConstants.REQUEST_SUGGESTION_CONTENT:
                //TODO get and parse suggestion player
                returnMessage = new Message(ClueGameConstants.REPLY_SUGGESTION_CONTENTS, null);// TODO NOT NULL
                return returnMessage;

            case ClueGameConstants.REQUEST_CARD_REVEALED:
                //TODO cycle through suggestion and match with players' hands
                returnMessage = new Message(ClueGameConstants.REPLY_REVEALED_CARD, null); //TODO not null
                return returnMessage;
            default:
                return msgObj; //returns same object sent
        }
    }
}


