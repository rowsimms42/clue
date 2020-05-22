import java.io.Serializable;

public class WeaponCard extends Card implements Serializable{

private int type;
public WeaponCard(String name, int value, int type){
    super(name, value);
    this.type = type; 
}
 
public int getType() {return type;}
@Override
public int getValue() {return super.getValue();}
@Override
public String getName() {return super.getName();}
}


