
public class GameHandler {

    public static Object parseMessage(Message msgObj)
    {
    int msg = msgObj.getMessageID();
        switch (msg) {

            case ClueGameConstants.REQUEST_AVAILABLE_CHARACTERS:
                msgObj.setData(GameState.getAvailableCharacters());
                msgObj.setMessageID(ClueGameConstants.AVAILABLE_CHARACTER_BOOL_ARRAY);
                return msgObj;

            case ClueGameConstants.IS_SELECTED_CHARACTER_AVAILABLE:
                int c0 = (int) msgObj.getData();
                msgObj.setData(GameState.isSpecificCharacterAvailable(c0));
                return msgObj;

            case ClueGameConstants.REQUEST_INDEED_CHARACTER_AVAILABLE: 
                int c1 = (int) msgObj.getData();
                msgObj.setData(GameState.isSpecificCharacterAvailable(c1));
                return msgObj;

            case ClueGameConstants.MARK_CHARACTER_AS_TAKEN:
                int c2 = (int) msgObj.getData();
                GameState.setSpecificCharacterToUnavailable(c2);
                msgObj.setData(null);
                return msgObj;

            //case ClueGameConstants.    
            default:
                return msgObj;
        }
    }
}