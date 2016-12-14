/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

package worldOfZuul;

import java.util.HashMap;

public class Room {
	private String description;
	private String longDescription = "";
	private HashMap<Direction, Room> exits = new HashMap<Direction, Room>();

	/**
	 * Create a room described "description". Initially, it has no exits. "description" is something
	 * like "a kitchen" or "an open court yard".
	 * 
	 * @param description
	 *            The room's description.
	 */
	public Room(String description) {
		this.description = description;
	}

	/**
	 * Define the exits of this room. Put the room in a HashMap with his direction. Every direction
	 * either leads to another room or is null (no exit there).
	 * 
	 * @param direction
	 *            The direction where the exitroom is.
	 * @param room
	 *            The room which is in the given dircetion.
	 */
	public void setExit(Direction direction, Room room) {
		this.exits.put(direction, room);
	}

	public String getExitsAsString() {
		String rueckgabe = "";
		for (Direction dir : this.exits.keySet()) {
			rueckgabe += " " + dir.name().toLowerCase();
		}
		return rueckgabe;
	}

	/**
	 * @return The description of the room.
	 */
	public String getDescription() {
		return description;
	}

	public String getLongDescription() {
		longDescription += "You are " + this.getDescription() + "\n";
		longDescription += "Exits: ";
		longDescription += this.getExitsAsString() + "\n";
		return longDescription;
	}
	
	public Room getExit(Direction direction) {
		return this.exits.get(direction);
	}

}
