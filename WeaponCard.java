
public class WeaponCard extends Card{

private int type;
public WeaponCard(String name, int value, int type){
    super(name, value);
    this.type = type; 
}
 
public int getType() {return type;}
public int getValue() {return value;}
public String getName() {return getName();}
}


