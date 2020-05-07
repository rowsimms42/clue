public final class ClueGameConstants {
	
		public static final int PORT = 6060;
	
		public static final String IP = "localhost";
	
		public static final int MAX_CHARACTERS = 6;
		
		public static final int MIN_CHARACTERS = 3;
		
		public static final int REQUEST_AVAILABLE_CHARACTERS = 11;

		public static final int REPLY_FROM_SERVER_AVAILABLE_CHARACTERS = 12;
		
		public static final int CONFIRM_INDEX_AVAILABLE = 13;
	
		public static final int REQUEST_MARK_CHARACTER_AS_TAKEN = 14;
	
		public static final int REQUEST_INDEED_CHARACTER_AVAILABLE = 15;
	
		public static final int REQUEST_IS_SELECTED_CHARACTER_AVAILABLE = 16;

		public static final int REPLY_FROM_SERVER_IS_CHARACTER_AVAILABLE = 17;

		public static final int REPLY_FROM_SERVER_IS_CHARACTER_INDEED_AVAILABLE = 18;

		public static final int REPLY_FROM_SERVER_CONFIRM_CHARACTER_SELECTED = 19;

		public static final int REQUEST_PLAYER_ID = 20;

		public static final int REPLY_FROM_SERVER_PLAYER_ID = 21;

		public static final int REQUEST_PLAYER_NAME = 22;

		public static final int REPLY_FROM_SERVER_PLAYER_NAME = 23;

		public static final int ERROR_CODE = 24;

		public static final int EMPTY_TILE = 25;

		public static final int REQUEST_DICE_ROLL = 26;

		public static final int REPLY_FROM_SERVER_DICE_ROLL= 27;
		
		public static final int REQUEST_PLAYERS_CHARACTER = 28;
		
		public static final int REPLY_FROM_SERVER_PLAYERS_CHARACTER = 29;

		
		public static final int UNSELECTABLE_TILE = -1;


		public static final String[] CHARACTER_NAMES_ARRAY = {"Mr. Green", "Professor Plum", "Mrs. White", "Colonel Mustard",
										"Miss Scarlett", "Mrs. Peacock"};

		public static enum ROOMS{
			Conservatory(new Rect(0,5,20,23), new Rect(0,4,19,19)),
			Billard(new Rect(0, 5, 12, 16), new Rect(0, 5, 12, 16)),
			Library(new Rect(1,5,6,10), new Rect(0,6,7,9)),
			Study(new Rect(0, 5, 0, 3), new Rect(0, 6, 0, 0)),
			Ballroom(new Rect(8, 15, 17, 22), new Rect(10,13,23,23)),
			Hall(new Rect(9, 14, 0, 6), new Rect(9, 14, 0, 6)),
			StairCase(new Rect(10,13, 8, 14), new Rect(10,13, 8, 14)),
			Lounge(new Rect(17, 23, 1, 5), new Rect(18,23,0,0)),
			Kitchen(new Rect(18, 22, 18, 23), new Rect(23, 23, 19, 23)),
			DinningHall(new Rect(16,23,9,14), new Rect(19,23,15,15));

			private Rect rect1, rect2;

			ROOMS(Rect rect1, Rect rect2){
				this.rect1 = rect1;
				this.rect2 = rect2;
			}

			public Boolean isInRoom(int x, int y){
				return rect1.isWithIn(x, y) || rect2.isWithIn(x,y);
			}
		};
		
	} //end class