import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//import javax.smartcardio.Card; 
  
public class Cards {

//these can be added to the gameconstants file
 

public static final int TYPE_SUPSECTS = 0;
public static final int TYPE_WEAPONS = 1;
public static final int TYPE_ROOMS = 2;

 
public static final int ROOM_CONSERVATORY = 0;
public static final int ROOM_BILLIARD = 1;
public static final int ROOM_STUDY = 2;
public static final int ROOM_LIBRARY = 3;
public static final int ROOM_HALL = 4;
public static final int ROOM_LOUNGE = 5;
public static final int ROOM_DINING = 6;
public static final int ROOM_KITCHEN = 7;
public static final int ROOM_BALLROOM = 8;


public static final int WEAPON_PIPE = 0;
public static final int WEAPON_CANDLE = 1;
public static final int WEAPON_REVOLVER = 2;
public static final int WEAPON_WRENCH = 3;
public static final int WEAPON_KNIFE = 4;
public static final int WEAPON_ROPE = 5;
 
public static final int NUM_SUSPECTS = 6; 
public static final int NUM_WEAPONS = 6; 
public static final int NUM_ROOMS = 9; 
public static final int TOTAL = NUM_SUSPECTS + NUM_WEAPONS + NUM_ROOMS; 
private final static List<Cards> shuffled = new ArrayList<>(TOTAL);
   


private final int type;
private final int value;

  //to add the three cards in the envelope
  private final static List<Cards> envelope = new ArrayList<>(3);

  // cards:
  // put three cards - 1 from each category - in seperate location, "Case
  // Confidential File"
  // deal remainding cards to every player
  // if cards left over, show to all players.

  public Cards(final int type, final int value) {
      this.type = type;
      this.value = value;
  }

  public String tyString() {

      String defineSt = null;

      switch (type) {
          case TYPE_SUPSECTS:
              // define = find
              break;
          case TYPE_WEAPONS:
              switch (value) {
                  case WEAPON_PIPE:
                      defineSt = "Pipe";
                      break;
                  case WEAPON_CANDLE:
                      defineSt = "Candle";
                      break;
                  case WEAPON_REVOLVER:
                      defineSt = "Revoler";
                      break;
                  case WEAPON_WRENCH:
                      defineSt = "Wrench";
                      break;
                  case WEAPON_KNIFE:
                      defineSt = "Knife";
                      break;
                  case WEAPON_ROPE:
                      defineSt = "Rope";
                      break;
              }
              break;

          case TYPE_ROOMS:
              switch (value) {
                  case ROOM_CONSERVATORY:
                      defineSt = "Conservatory";
                      break;
                  case ROOM_BILLIARD:
                      defineSt = "Billiard Room";
                      break;
                  case ROOM_STUDY:
                      defineSt = "Study Room";
                      break;
                  case ROOM_HALL:
                      defineSt = "Hall";
                      break;
                  case ROOM_LOUNGE:
                      defineSt = "Lounge";
                      break;
                  case ROOM_DINING:
                      defineSt = "Dining Room";
                      break;
                  case ROOM_KITCHEN:
                      defineSt = "Kitchen";
                      break;
                  case ROOM_BALLROOM:
                      defineSt = "Ballroom";
                      break;

              }
break;

      }
return defineSt; 
  }

  public int getValue() {
      return value;
  }

  public int getType() {
      return type;
  }

  // to determine the type and which particular object we are working with.
  // add type and values
  public int typeSpecifier() {
      return type + value;
  }

  // create the deck
  public static void deckCreator() {

      final ArrayList<Cards> deck = new ArrayList<>(TOTAL);

      // create the deck of cards using int values
      for (int i = 0; i < NUM_SUSPECTS; i++) {
          deck.add(new Cards(TYPE_SUPSECTS, i));
      }
      for (int i = 0; i < NUM_WEAPONS; i++) {
          deck.add(new Cards(TYPE_WEAPONS, i));
      }
      for (int i = 0; i < NUM_ROOMS; i++) {
          deck.add(new Cards(TYPE_ROOMS, i));

      }

      // shuffle the cards added to the deck
      // create class called rand
      final Random rand = new Random();
      for (int i = 0; i < TOTAL; i++) {

          final int random = rand.nextInt(deck.size());

          Cards card = deck.remove(random);
          shuffled.add(card);

      }

      // pull one from each type
      int oneRoom = rand.nextInt(NUM_ROOMS);
      int oneWeapon = rand.nextInt(NUM_WEAPONS);
      int oneSuspect = rand.nextInt(NUM_SUSPECTS);

      Cards theRoom = new Cards(TYPE_ROOMS, oneRoom);
      Cards theWeapon = new Cards(TYPE_WEAPONS, oneWeapon);
      Cards thesupspect = new Cards(TYPE_SUPSECTS, oneSuspect);

      // remove this set of cards from the shuffled liste of cards
      shuffled.remove(theRoom);
      shuffled.remove(theWeapon);
      shuffled.remove(thesupspect);

      // add them to the array list created
        envelope.add(theRoom);
        envelope.add(theWeapon);
        envelope.add(thesupspect);


}
 
// static void envelope () {


// }

// once player class created, deal the deck to the players 
public void dealDeck(){

    
}




public static void main( final String args[]) 
    { 


      // deckCreator(); 
        
      //rollDice(); 
 } 


}




