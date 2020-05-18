import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


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
        System.out.println("Adding player to gamestate address: " + gameState);
    }

    //return the number of players from the game state 
    public int getNumberOfCurrentPlayers(){
        return gameState.getNumberOfPlayers();
    }

    public Message parseMessage(Message msgObj, long threadID)
    {
        Message returnMessage;
        Player tempPlayer;
        Characters tempCharacter;
        HashMap<Long, Player> playerMapTemp = new  HashMap<Long, Player>();

        int msgID = msgObj.getMessageID();
        System.out.println("Incoming to server MessageID: " + msgID);
        switch (msgID) {

            case ClueGameConstants.REQUEST_AVAILABLE_CHARACTERS:
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_AVAILABLE_CHARACTERS, 
                                Integer.valueOf(gameState.getAvailableCharacters()));
                    System.out.println("Client Requests available characters");    
                return returnMessage;

            case ClueGameConstants.REQUEST_IS_SELECTED_CHARACTER_AVAILABLE:
                characterIndex = (Integer) msgObj.getData();
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_IS_CHARACTER_AVAILABLE,
                                Boolean.valueOf(gameState.isSpecificCharacterAvailable(characterIndex)));
                System.out.println("Client Requests if character is available");    
                return returnMessage;

            case ClueGameConstants.REQUEST_INDEED_CHARACTER_AVAILABLE: 
                characterIndex = (Integer) msgObj.getData();
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_IS_CHARACTER_AVAILABLE,
                                Boolean.valueOf(gameState.isSpecificCharacterAvailable(characterIndex)));
                System.out.println("Client wants to confirm that character is indeed available"); 
                return returnMessage;
                
            case ClueGameConstants.REQUEST_MARK_CHARACTER_AS_TAKEN:
                characterIndex = (Integer) msgObj.getData();
                gameState.setSpecificCharacterToUnavailable(characterIndex);
                tempCharacter = (Characters) gameState.getCharacterMap().get(gameState.getCharacterName(characterIndex));
                tempPlayer = (Player) gameState.getPlayerMap().get(threadID);
                tempPlayer.setCharacter(tempCharacter);
                gameState.getPlayerMap().put(threadID, tempPlayer);
                gameState.addTurnOrder(tempPlayer.getCharacter().getTurnOrder());
                System.out.println("Character chosen: " + tempPlayer.getName());
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_CONFIRM_CHARACTER_SELECTED, null);
                System.out.println("Client wants server to mark character is taken");
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
                System.out.println(sb);
                System.out.println("in game handler...");
                System.out.println(Collections.singletonList(buttonValues));
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_MOVEMENT_BUTTON_VALUES, String.valueOf(sb));
                return returnMessage;
                
            case ClueGameConstants.REQUEST_PLAYER_MAP:
            	playerMapTemp.clear();
            	playerMapTemp.putAll(gameState.getPlayerMap());
            	returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_PLAYER_MAP, 
            			playerMapTemp);
            	return returnMessage;
            	
            case ClueGameConstants.REQUEST_PLAYER_OBJECT:
            	tempPlayer = (Player) gameState.getPlayerMap().get(threadID);
            	returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_PLAYER_OBJECT, tempPlayer);
            	return returnMessage;
            
            case ClueGameConstants.REQUEST_IS_CURRENT_TURN:
            	tempPlayer = (Player) gameState.getPlayerMap().get(threadID);
            	returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_IS_CURRENT_TURN, 
            								Boolean.valueOf(tempPlayer.getIsPlayerTurn()));
            	return returnMessage;
            
            case ClueGameConstants.REQUEST_MARK_PLAYER_END_TURN:
            	tempPlayer = (Player) gameState.getPlayerMap().get(threadID);
            	tempPlayer.setIsPlayerTurn(false);
            	gameState.getPlayerMap().put(threadID, tempPlayer);
            	int nextTurnOrder = gameState.getNextPlayerTurn();
            	gameState.setPlayOrderIndex(gameState.getPlayOrderIndex() + 1);
            	tempPlayer = (Player)gameState.getPlayerByTurnOrder(nextTurnOrder);
            	tempPlayer.setIsPlayerTurn(true);
            	gameState.getPlayerMap().put(threadID, tempPlayer);
            	returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_CONFIRM_PLAYER_END_TURN,null);
            	return returnMessage; 
            
            default:
                return msgObj; //returns same object sent
        }
    }
}


