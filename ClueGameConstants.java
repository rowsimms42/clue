public final class ClueGameConstants {
	
		public static final int PORT = 6060;
	
		public static final String IP = "localhost";
	
		public static final int MAX_CHARACTERS = 6;
		
		public static final int MIN_CHARACTERS = 3;
		
		public static final int REQUEST_AVAILABLE_CHARACTERS = 11;

		public static final int AVAILABLE_CHARACTERS = 12;
		
		public static final int CONFIRM_INDEX_AVAILABLE = 13;
	
		public static final int MARK_CHARACTER_AS_TAKEN = 14;
	
		public static final int REQUEST_INDEED_CHARACTER_AVAILABLE = 15;
	
		public static final int IS_SELECTED_CHARACTER_AVAILABLE = 16;

		public static final int REPLY_FROM_SERVER_IS_CHARACTER_AVAILABLE = 17;

		public static final int REPLY_FROM_SERVER_IS_CHARACTER_INDEED_AVAILABLE = 18;

		public static final int CONFIRM_CHARACTER_SELECTED = 19;

		public static final int REQUEST_PLAYER_ID = 20;

		public static final int REPLY_FROM_SERVER_PLAYER_ID = 21;

		public static final int REQUEST_PLAYER_NAME = 22;

		public static final int REPLY_FROM_SERVER_PLAYER_NAME = 23;

		public static final int UNSELECTABLE_TILE = -1;

		public static final int EMPTY_TILE = 0;

		public static final String[] CHARACTER_NAMES_ARRAY = {"Mr. Green", "Professor Plum", "Mrs. White", "Colonel Mustard",
										"Miss Scarlett", "Mrs. Peacock"};

		public static enum ROOMS{
			CONSERVATORY(0,5,20,23, "Conservatory", 1, 1), 
			BILLARD(0, 5, 12, 16, "Billard Room", 1, 2),
			LIBRARY(1, 5, 6, 10, "Library", 1, 3), 
			STUDY(0, 5, 0, 3, "Study", 1, 4),
			BALLROOM(8, 15, 17, 22, "Ball Room", 2, 5), 
			HALL(9, 14, 0, 6, "Hall", 2, 6),
			STAIRCASE(10,13, 8, 14, "Staircase", 2, 7), 
			LOUNGE(17, 23, 1, 5, "Lounge", 3, 8),
			KITCHEN(18, 22, 18, 23, "Kitchen", 3, 9), 
			DINNINGHALL(16,23,9,14, "Dinning Hall", 3, 10);
			
			// Rooms private variables
			private final int x_min, x_max, y_min, y_max, column, idNum;
			private final String roomNameStr;
			
			// Rooms constructor
			ROOMS(int x_min, int x_max, int y_min, int y_max, String name, int col, int id){
				this.x_min = x_min;
				this.x_max = x_max;
				this.y_min = y_min;
				this.y_max = y_max;
				this.roomNameStr = name;
				this.column = col;
				this.idNum = id;
			}
			
			public int getXMin(){ return this.x_min;}
			public int getXMax(){ return this.x_max;}
			public int getYMin(){ return this.y_min;}
			public int getYMax(){ return this.y_max;}
			public int getColumn() { return this.column;}
			public int getRoomNum() {return this.idNum;}
			public String getRoomName() { return this.roomNameStr;}
		}
		
		public static enum ROOMS_ODD{
			CONSERVATORY(0,4,19,19, "Conservatory", 1, 1), 
			LIBRARY_1(0,0,7,9, "Library", 1, 3), 
			LIBRARY_2(6,6,7,9, "Library", 1, 3), 
			STUDY(0, 6, 0, 0, "Study", 1, 4),
			BALLROOM(10,13,23,23, "Ball Room", 2, 5), 
			LOUNGE(18,23,0,0, "Lounge", 3, 8),
			KITCHEN(23, 23, 19, 23, "Kitchen", 3, 9), 
			DINNINGHALL(19,23,15,15, "Dinning Hall", 3, 10);
			
			// Rooms private variables
			private final int x_min, x_max, y_min, y_max, column, idNum;
			private final String roomNameStr;
			
			// Rooms constructor
			ROOMS_ODD(int x_min, int x_max, int y_min, int y_max, String name, int col, int id){
				this.x_min = x_min;
				this.x_max = x_max;
				this.y_min = y_min;
				this.y_max = y_max;
				this.roomNameStr = name;
				this.column = col;
				this.idNum = id;
			}
			
			public int getXMin(){ return this.x_min;}
			public int getXMax(){ return this.x_max;}
			public int getYMin(){ return this.y_min;}
			public int getYMax(){ return this.y_max;}
			public int getColumn() { return this.column;}
			public int getRoomNum() {return this.idNum;}
			public String getRoomName() { return this.roomNameStr;}
		}
		
	} //end class