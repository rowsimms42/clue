public class Player {

    private String name;
    private int[] locationArray = {0,0};
    private long Id;
    
    public Player(long Id){
        this.Id = Id;
    }

    public long getPlayerId(){
        return Id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setLocation(int[] loc){
        for (int i = 0; i <loc.length; i++){
            this.locationArray[i] = loc[i];
        }
    }

}

