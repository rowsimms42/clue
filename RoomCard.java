public class RoomCard extends Cards {
    
int type; 

        RoomCard(String name, int value, int type){

            super(name, value);
            this.type = type; 

        }

    public int getType(){return type;}
    public int getValue(){return super.getValue();}
    public String getName(){return super.getName();}

}