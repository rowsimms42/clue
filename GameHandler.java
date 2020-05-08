import java.util.HashMap;

public class GameHandler {

    GameState gameState;
    int characterIndex;

    GameHandler(GameState gameState){
        this.gameState = gameState;
    }

    /* Increment the number of players in the game state class */
    public void incrementAmountOfPlayers(){
        gameState.setNumberOfPlayers( gameState.getNumberOfPlayers() + 1);
    }

    //add player to the game state
    public void addPlayerToGame(Player player, long ID){
        gameState.addPlayer(player, ID);
    }

    /* return the number of players from the game state */
    public int getNumberOfCurrentPlayers(){
        return gameState.getNumberOfPlayers();
    }

    public Message parseMessage(Message msgObj, long threadID)
    {
        Message returnMessage;
        Player tempPlayer;
        Characters tempCharacter;

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
                System.out.println("Character chosen: " + tempPlayer.getName());
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_CONFIRM_CHARACTER_SELECTED, null);
                System.out.println("Client wants server to mark character is taken");
                return returnMessage;

            case ClueGameConstants.REQUEST_PLAYER_ID:
                tempPlayer = (Player) gameState.getPlayerMap().get(threadID);
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_PLAYER_ID, 
                                            Long.valueOf(tempPlayer.getPlayerId()));
                return returnMessage;

            case ClueGameConstants.REQUEST_PLAYER_NAME:
                tempPlayer = (Player) gameState.getPlayerMap().get(threadID);
                System.out.println("Player name: " + tempPlayer.getName());
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_PLAYER_NAME, 
                                            tempPlayer.getName());
                return returnMessage;

            case ClueGameConstants.REQUEST_DICE_ROLL:
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_DICE_ROLL, 
                                            Integer.valueOf(gameState.rollDice()));
                return returnMessage;
                
            case ClueGameConstants.REQUEST_PLAYERS_CHARACTER:
            	tempPlayer = (Player) gameState.getPlayerMap().get(threadID);
            	tempCharacter = tempPlayer.getCharacter();
            	System.out.println("Character to be sent: " + tempCharacter.getName());
            	returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_PLAYERS_CHARACTER, 
            								(Characters)tempCharacter);
            	return returnMessage;
            
            /**
             * Needs to be tested with GUI implementation. Test of gamestate function in main of GameState
             */
            case ClueGameConstants.REQUEST_MOVEMENT_BUTTON_VALUES:
                int[] locCoords = (int[]) msgObj.getData();
                HashMap<String, Boolean> buttonValues = gameState.getNextMoves(locCoords);
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_MOVEMENT_BUTTON_VALUES, buttonValues);
            default:
                return msgObj; //returns same object sent
        }
    }
}


