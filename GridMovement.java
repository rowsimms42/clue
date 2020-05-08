
public class GridMovement {
	
	private int columns = 24;
	private int rows = 25;
	private int directions = 4;
	private int[][] gameGrid;
	private int[][][] movementGrid;
	private final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	
	public GridMovement(int[][] gameGrid) {
		this.gameGrid = gameGrid;
		movementGrid = new int[rows][columns][directions];
		buildMovementGrid(movementGrid, this.gameGrid, 24, 23);
	}
	
	public void buildMovementGrid(int[][][] movementMap, int[][] grid, int maxRow, int maxColumn) {
		for(int row = 0; row <= maxRow; row++) {
			for(int col = 0; col <= maxColumn; col++) {
				if(grid[row][col] == 25) {
					if(row - 1 < 0) //row is off top grid
						movementMap[row][col][UP] = 0;
					else 
						movementMap[row][col][UP] = (grid[row-1][col] == 25) ? 1 : 0;
	
					if(row + 1 > maxRow) //row is off bottom grid
						movementMap[row][col][DOWN] = 0;
					else
						movementMap[row][col][DOWN] = (grid[row+1][col] == 25) ? 1 : 0;
				
					if(col - 1 < 0) //column is off left side of grid
						movementMap[row][col][LEFT] = 0;
					else 
						movementMap[row][col][LEFT]  = (grid[row][col-1] == 25) ? 1 : 0;
				
					if(col + 1 > maxColumn) ////column is off right side of grid
						movementMap[row][col][RIGHT] = 0;
					else 
						movementMap[row][col][RIGHT] = (grid[row][col+1] == 25) ? 1 : 0;
				}
			}
		}
	}
	
	//Check if the title in a given direction is a door. 
	private int investigateIndexes(int row, int col, int direction) {
		for(ClueGameConstants.DOORS door : ClueGameConstants.DOORS.values()) {
			if(door.getRow() == row && door.getCol() == col && door.getDirection() == direction) 
				return door.getRoomNumber();
		}
		return 0; //not a door
	}
		
	public int[] movementOptions(int xLocation, int yLocation) {
		/* if we got a 0, that means we couldn't go in that direction, so lets see if that direction is a doorway.
		 * if its not a doorway, then must be a wall or another player at that location.
		 */
		int[] movement = new int[4];
		movement[UP]    = (movementGrid[yLocation][xLocation][UP]    != 0) ? 1 : investigateIndexes(yLocation-1,xLocation,UP);
		movement[DOWN]  = (movementGrid[yLocation][xLocation][DOWN]  != 0) ? 1 : investigateIndexes(yLocation+1,xLocation,DOWN);
		movement[LEFT]  = (movementGrid[yLocation][xLocation][LEFT]  != 0) ? 1 : investigateIndexes(yLocation,xLocation-1,LEFT); 
		movement[RIGHT] = (movementGrid[yLocation][xLocation][RIGHT] != 0) ? 1 : investigateIndexes(yLocation,xLocation+1,RIGHT); 
		return movement;
	}

}
