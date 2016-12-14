package worldOfZuul;

public enum Direction {
	NORTH, EAST, SOUTH, WEST, UP, DOWN;
	
	public static Direction getDirection(String direction) {
		Direction dir = null;
		
		if(direction.equalsIgnoreCase("north"))
			dir = NORTH;
		if (direction.equalsIgnoreCase("east"))
			dir = EAST;
		if (direction.equalsIgnoreCase("south"))
			dir = SOUTH;
		if (direction.equalsIgnoreCase("west"))
			dir = WEST;
		if (direction.equalsIgnoreCase("up"))
			dir = UP;
		if (direction.equalsIgnoreCase("down"))
			dir = DOWN;
		
		return dir;
	}
}
