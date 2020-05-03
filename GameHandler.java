public class GameHandler {

    GameState gameState;
    int characterIndex;

    GameHandler(GameState gameState){
        this.gameState = gameState;
    }

    /* Increment the number of players in the gamestate clas */
    public void incrementAmountOfPlayers(){
        gameState.setNumberOfPlayers( gameState.getNumberOfPlayers() + 1);
    }

    //add player to the gamestate
    public void addPlayerToGame(Player player, long ID){
        gameState.addPlayer(player, ID);
    }

    /* return the number of players from the gamestate */
    public int getNumberOfCurrentPlayers(){
        return gameState.getNumberOfPlayers();
    }

    public Message parseMessage(Message msgObj, long threadID)
    {
        Message returnMessage;
        Player tempPlayer;

        int msgID = msgObj.getMessageID();
        switch (msgID) {

            case ClueGameConstants.REQUEST_AVAILABLE_CHARACTERS:
                returnMessage = new Message(ClueGameConstants.AVAILABLE_CHARACTERS, 
                    Integer.valueOf(gameState.getAvailableCharacters()));
                    System.out.println("Client Requests available characters");    
                return returnMessage;

            case ClueGameConstants.IS_SELECTED_CHARACTER_AVAILABLE:
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

            case ClueGameConstants.MARK_CHARACTER_AS_TAKEN:
                characterIndex = (Integer) msgObj.getData();
                gameState.setSpecificCharacterToUnavailable(characterIndex);
                gameState.assignPlayerName(gameState.getCharacterName(characterIndex), threadID);
                returnMessage = new Message(ClueGameConstants.CONFIRM_CHARACTER_SELECTED, null);
                System.out.println("Client wants server to mark character is taken");
                return returnMessage;
            case ClueGameConstants.REQUEST_PLAYER_ID:
                tempPlayer = (Player) gameState.getPlayerMap().get(threadID);
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_PLAYER_ID, 
                                Long.valueOf(tempPlayer.getPlayerId()));
                return returnMessage;
            case ClueGameConstants.REQUEST_PLAYER_NAME:
                tempPlayer = (Player) gameState.getPlayerMap().get(threadID);
                System.out.println("Player name: " + String.valueOf(tempPlayer.getName()));
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_PLAYER_NAME, 
                                            new String(tempPlayer.getName()));
                return returnMessage;

            //case ClueGameConstants.    
            default:
                return msgObj; //returns same object sent
        }
    }
}


