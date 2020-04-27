
public class GameHandler {

    public static void parseMessage(Message msgObj)
    {
    int msg = msgObj.getMessageID();
        switch (msg) {
            case 1:
                msgObj.setData("Hello from case 1");
                break;
            case 2:
                msgObj.setData("Hello from case 2");
            default:
                break;
        }
    }
}