/*
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class TravelGrid {

	public static void main(String[] args) {
		
		int columns = 24;
		int rows = 25;
		int[][] gameGrid = new int[rows][columns];
		HashMap<Integer, ArrayList<Integer>> map = fillMapBadTiles();
		fillGameGrid(gameGrid, map);
		GridMovement movement = new GridMovement(gameGrid);
		

		int startingCol = 9, startingRow = 23;
		int[] options = movement.movementOptions(startingCol,startingRow);
		System.out.println(Arrays.toString(options));
	}
	
	public static int titleType(int x, int y, HashMap<Integer, ArrayList<Integer>> map){
		
		return determineTileType(x, y, map);
	}
	
	public static int determineTileType(int x, int y, HashMap<Integer, ArrayList<Integer>> map){
		//see if the tile is in a room
		int roomName = findInRoom(x,y);
		if(roomName != -2)
			return roomName;
		//see if the tile is an invalid tile
		int invalidTile = findInvalidTiles(map,x,y);
		if(invalidTile != -2)
			return invalidTile;
		//tile is not invalid and not in a room so must be empty tile
		return ClueGameConstants.EMPTY_TILE;
	}
	
	public static int findInRoom(int x, int y){
		for(ClueGameConstants.ROOMS room : ClueGameConstants.ROOMS.values()){
			if(room.isInRoom(x, y))
				return room.getID();
		}
		return -2;
	}
	
	public static int findInvalidTiles(HashMap<Integer, ArrayList<Integer>> map, int x, int y){
		if(map.containsKey(y)){
			List<Integer> badColumsList = map.get(y);
			for(int i = 0; i < badColumsList.size();i++){
				if(badColumsList.get(i) == x)
					return -1;
			}
		}
		return -2; //not found in the map
	}
	
	//HashMap contains invalid tiles. 
	public static HashMap<Integer, ArrayList<Integer>> fillMapBadTiles(){
			HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
			map.put(0, new ArrayList<Integer>(Arrays.asList(6,8,9,10,11,12,13,14,15,17)));
			map.put(4, new ArrayList<Integer>(Arrays.asList(0)));
			map.put(6, new ArrayList<Integer>(Arrays.asList(0,23)));
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
	
	public static void fillGameGrid(int[][] grid, HashMap<Integer, ArrayList<Integer>> map) {
		for(int row = 0; row < 25; row++) {
			for(int col = 0; col < 24; col++) {
				grid[row][col] = determineTileType(col, row, map);
			}
		}
	}

}
*/