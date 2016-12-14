/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

package worldOfZuul;

import java.util.HashMap;

public class Game {
	private Parser parser;
	private Room currentRoom;
	private HashMap<String, Direction> directions = new HashMap<String, Direction>();

	/**
	 * Create the game and initialise its internal map.
	 */
	public Game() {
		createRooms();
		parser = new Parser();
		setDirectionMap();
	}

	/**
	 * Create all the rooms and link their exits together.
	 */
	private void createRooms() {

		// create the rooms
		Room outside = new Room("outside the main entrance of the university");
		Room theater = new Room("in a lecture theater");
		Room pub = new Room("in the campus pub");
		Room lab = new Room("in a computing lab");
		Room office = new Room("in the computing admin office");
		Room tunnle = new Room("in a dirty dark tunnle");
		Room zagara = new Room("in a cold dark world named Zagara");
		Room darkHouse = new Room("in an old dark house");
		Room kitchen = new Room("in the kitchen of the dark house");
		Room livingroom = new Room("in the living room of the dark house");
		Room bedroom = new Room("in the bedroom of the dark house");
		Room bathroom = new Room("in the bathroom");
		Room basement = new Room("in the basement");

		// initialise room exits
		outside.setExit(Direction.NORTH, tunnle);
		outside.setExit(Direction.EAST, theater);
		outside.setExit(Direction.SOUTH, lab);
		outside.setExit(Direction.WEST, pub);
		theater.setExit(Direction.WEST, outside);
		pub.setExit(Direction.EAST, outside);
		lab.setExit(Direction.NORTH, outside);
		lab.setExit(Direction.EAST, office);
		office.setExit(Direction.WEST, lab);
		tunnle.setExit(Direction.NORTH, zagara);
		tunnle.setExit(Direction.SOUTH, outside);
		zagara.setExit(Direction.SOUTH, tunnle);
		zagara.setExit(Direction.WEST, darkHouse);
		darkHouse.setExit(Direction.NORTH, livingroom);
		darkHouse.setExit(Direction.EAST, zagara);
		darkHouse.setExit(Direction.SOUTH, kitchen);
		darkHouse.setExit(Direction.WEST, bedroom);
		darkHouse.setExit(Direction.DOWN, basement);
		kitchen.setExit(Direction.NORTH, darkHouse);
		bedroom.setExit(Direction.EAST, darkHouse);
		livingroom.setExit(Direction.SOUTH, darkHouse);
		livingroom.setExit(Direction.NORTH, bathroom);
		bathroom.setExit(Direction.SOUTH, livingroom);
		basement.setExit(Direction.UP, darkHouse);

		currentRoom = outside; // start game outside

	}

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void play() {
		this.printWelcome();

		// Enter the main command loop. Here we repeatedly read commands and
		// execute them until the game is over.

		boolean finished = false;
		while (!finished) {
			Command command = parser.getCommand();
			finished = processCommand(command);
		}
		System.out.println("Thank you for playing.  Good bye.");
	}

	/**
	 * Print out the opening message for the player.
	 */
	private void printWelcome() {
		System.out.println();
		System.out.println("Welcome to the World of Zuul!");
		System.out.println("World of Zuul is a new, incredibly boring adventure game.");
		System.out.println("Type 'help' if you need help.");
		System.out.println();
		this.printLocationInfo(currentRoom);
	}

	/**
	 * Given a command, process (that is: execute) the command.
	 * 
	 * @param command
	 *            The command to be processed.
	 * @return true If the command ends the game, false otherwise.
	 */
	private boolean processCommand(Command command) {
		boolean wantToQuit = false;

		if (command.isUnknown()) {
			System.out.println("I don't know what you mean...");
			return false;
		}

		String commandWord = command.getCommandWord();
		if (commandWord.equals("help")) {
			printHelp();
		} else if (commandWord.equals("go")) {
			goRoom(command);
		} else if (commandWord.equals("quit")) {
			wantToQuit = quit(command);
		}

		return wantToQuit;
	}

	// implementations of user commands:

	/**
	 * Print out some help information. Here we print some stupid, cryptic message and a list of the
	 * command words.
	 */
	private void printHelp() {
		System.out.println("You are lost. You are alone. You wander");
		System.out.println("around at the university.");
		System.out.println();
		System.out.println("Your command words are:");
		System.out.println("   go quit help");
	}

	/**
	 * Print your current location and the exits.
	 */
	private void printLocationInfo(Room currentRoom) {
		System.out.println(currentRoom.getLongDescription());
	}

	/**
	 * Try to go in one direction. If there is an exit, enter the new room, otherwise print an error
	 * message.
	 */
	private void goRoom(Command command) {
		if (!command.hasSecondWord()) {
			// if there is no second word, we don't know where to go...
			System.out.println("Go where?");
			return;
		}
		
		Direction direction = null;
		
		for (String s : directions.keySet()) {
			if (s.equalsIgnoreCase(command.getSecondWord().toLowerCase()))
				direction = directions.get(s.toLowerCase());
		}

		// Try to leave current room.
		Room nextRoom = null;
		nextRoom = currentRoom.getExit(direction);

		if (nextRoom == null) {
			System.out.println("There is no door!");
		} else {
			currentRoom = nextRoom;
			this.printLocationInfo(currentRoom);
		}
	}

	/**
	 * "Quit" was entered. Check the rest of the command to see whether we really quit the game.
	 * 
	 * @return true, if this command quits the game, false otherwise.
	 */
	private boolean quit(Command command) {
		if (command.hasSecondWord()) {
			System.out.println("Quit what?");
			return false;
		} else {
			return true; // signal that we want to quit
		}
	}
	
	private void setDirectionMap() {
		directions.put("north", Direction.NORTH);
		directions.put("east", Direction.EAST);
		directions.put("south", Direction.SOUTH);
		directions.put("west", Direction.WEST);
		directions.put("up", Direction.UP);
		directions.put("down", Direction.DOWN);
	}
}
