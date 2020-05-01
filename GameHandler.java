
public class GameHandler {

    GameState gameState;

    GameHandler(GameState gameState){
        this.gameState = gameState;
    }

    public Message parseMessage(Message msgObj)
    {
    int msg = msgObj.getMessageID();
    Message returnMessage;
        switch (msg) {

            case ClueGameConstants.REQUEST_AVAILABLE_CHARACTERS:
                returnMessage = new Message(ClueGameConstants.AVAILABLE_CHARACTERS, 
                    Integer.valueOf(gameState.getAvailableCharacters()));
                    System.out.print("Client Requests available characters");    
                return returnMessage;

            case ClueGameConstants.IS_SELECTED_CHARACTER_AVAILABLE:
                int characterIndex = (Integer) msgObj.getData();
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_IS_CHARACTER_AVAILABLE,
                    Boolean.valueOf(gameState.isSpecificCharacterAvailable(characterIndex)));
                return returnMessage;

            case ClueGameConstants.REQUEST_INDEED_CHARACTER_AVAILABLE: 
                characterIndex = (Integer) msgObj.getData();
                returnMessage = new Message(ClueGameConstants.REPLY_FROM_SERVER_IS_CHARACTER_AVAILABLE,
                    Boolean.valueOf(gameState.isSpecificCharacterAvailable(characterIndex)));
                return returnMessage;

            case ClueGameConstants.MARK_CHARACTER_AS_TAKEN:
                characterIndex = (Integer) msgObj.getData();
                gameState.setSpecificCharacterToUnavailable(characterIndex);
                returnMessage = new Message(ClueGameConstants.CONFIRM_CHARACTER_SELECTED, null);
                return returnMessage;

            //case ClueGameConstants.    
            default:
                return msgObj;
        }
    }
}


