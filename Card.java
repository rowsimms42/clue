/********************************************************
 * Super Class that encapsulates a card of the clue game
 *******************************************************/

import java.io.Serializable;

public class Card implements Serializable{

int value; 
String cardName;

public Card(String name, int value){
    cardName = name;
    this.value = value; 
}

public int getValue() { return value;}
public int getType() {return 0; }
public String getName() {return cardName;}

}


 

