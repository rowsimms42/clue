/**********************************
 * Child class of card class.
 * Contains attributes of room card
 *********************************/

import java.io.Serializable;

public class RoomCard extends Card implements Serializable{
    
        private int type; 
        RoomCard(String name, int value, int type){
            super(name, value);
            this.type = type; 
        }

    public int getType(){return type;}
    @Override
    public int getValue(){return super.getValue();}
    @Override
    public String getName(){return super.getName();}
}