
import java.util.*;

public class RoomClick {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char answer;
		Scanner scanner = new Scanner(System.in);
		HashMap<Integer, ArrayList<Integer>> map = fillMapBadTiles();
		do{
			System.out.println("Enter x value: ");
			int x = scanner.nextInt();
			System.out.println("Enter y value: ");
			int y = scanner.nextInt();
		
			System.out.println();
			System.out.println(determineTileType(x,y,map));
			System.out.println("Continue(Y/N)");
			answer = scanner.next().charAt(0);
		}while(answer=='Y'|| answer=='y');
		scanner.close();
	}
	
	public static String determineTileType(int x, int y, HashMap<Integer, ArrayList<Integer>> map){
		//see if the tile is in a room
		String roomName = findInRoom(x,y);
		if(roomName != null)
			return roomName;
		//see if the tile is an invalid tile
		String invalidTile = findInvalidTiles(map,x,y);
		if(invalidTile != null)
			return invalidTile;
		//tile is not invalid and not in a room so must be empty tile
		return "Empty tile";
	}

    //test if the tile is in a room
	public static String findInRoom(int x, int y){
		//See if the tile was found in an nxn portion of a room 
		String roomName = null;
		roomName = squareRooms(x, y);
		if(roomName != null)
			return roomName;
		//See if title was in an odd peice of the room
		roomName = oddRooms(x,y);
		if(roomName != null)	
			return roomName;

		return null; //tile was not found in a room
	}

	public static String findInvalidTiles(HashMap<Integer, ArrayList<Integer>> map, int x, int y){
		if(map.containsKey(y)){
			List<Integer> badColumsList = map.get(y);
			for(int i = 0; i < badColumsList.size();i++){
				if(badColumsList.get(i) == x)
					return "invald tile";
			}
		}
		return null; //not found in the map
	}

	public static String squareRooms(int x, int y){
		for(ClueGameConstants.ROOMS room : ClueGameConstants.ROOMS.values()){
			if(x >= room.getXMin() && x <= room.getXMax()){
				if(y >= room.getYMin() && y <= room.getYMax())
					return room.getRoomName();
			}
		}
		return null;
	}
	
	public static String oddRooms(int x, int y){
		for(ClueGameConstants.ROOMS_ODD room : ClueGameConstants.ROOMS_ODD.values()){
			if(x >= room.getXMin() && x <= room.getXMax()){
				if(y >= room.getYMin() && y <= room.getYMax())
					return room.getRoomName();
			}
		}
		return null;
	}

	//HashMap contains invalid tiles. 
	public static HashMap<Integer, ArrayList<Integer>> fillMapBadTiles(){
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
		map.put(0, new ArrayList<Integer>(Arrays.asList(6,8,15,17)));
		map.put(4, new ArrayList<Integer>(Arrays.asList(0)));
		map.put(6, new ArrayList<Integer>(Arrays.asList(0,24)));
		map.put(10, new ArrayList<Integer>(Arrays.asList(0)));
		map.put(11, new ArrayList<Integer>(Arrays.asList(0)));
		map.put(17, new ArrayList<Integer>(Arrays.asList(0)));
		map.put(19, new ArrayList<Integer>(Arrays.asList(0)));
		map.put(23, new ArrayList<Integer>(Arrays.asList(6,17)));
		map.put(8, new ArrayList<Integer>(Arrays.asList(23)));
		map.put(16, new ArrayList<Integer>(Arrays.asList(23)));
		map.put(18, new ArrayList<Integer>(Arrays.asList(23)));
		map.put(24, new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8,10,11,
						12,13,15,16,17,18,19,20,21,22,23)));

		return map;
	}
}
