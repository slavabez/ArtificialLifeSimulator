package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

import java.util.Random;

import javax.swing.JLabel;

/**
 * Class Bush extends class GrownRenewableFood. <br>
 * Bush is a food item that can be eaten by herbivores. As the name Renewable
 * Food suggests, Bush grows back to being a Bush after it's been eaten. So upon
 * consumption Bush becomes an object of type Eaten Renewable Food.
 * 
 * @author Slava
 * 
 */
public class Bush extends GrownRenewableFood {

	/**
	 * Constructor for Bush Class. Used to create new instances of Bush
	 * on-the-go (as opposed to creating one from a file).
	 * 
	 * @param x
	 *            - position on the X axis
	 * @param y
	 *            - position on the Y axis
	 * @param world
	 *            - the world the Bush is in
	 * @param startItself
	 *            - whether yu want the Bush's thread to start automatically. If
	 *            you're placing it before running the map, use false. If you're
	 *            placing it after the map has been initiated, use true.
	 */
	Bush(int x, int y, World world, boolean startItself) {
		super();
		Random rand = new Random();

		this.calories = rand.nextInt(10) + 3;
		this.calories = foodCalorieMultiplier * this.calories;
		this.w = world;
		this.xPos = x;
		this.yPos = y;

		this.positionAltered = true;

		j = new JLabel(getImageIcon("bushFull.png"));
		entityCount++;
		this.uniqueID = entityCount;

		w.setEntityOnTheMap(xPos, yPos, this);
		if (startItself == true) {
			activity.start();
		}
		if (w.getDEBUG()) {
			System.out
					.println("Created a new instance of Bush using the main constructor...");
		}
	}

	/**
	 * Constructor for Bush that is used for loading a Bush object from a file
	 * 
	 * @param id
	 *            - Bush'es Entity id
	 * @param x
	 *            - position on the X axis
	 * @param y
	 *            - position on the Y axis
	 * @param cal
	 *            - amount of calorioes the Bush has
	 * @param world
	 *            - the World the bush is in
	 */
	Bush(int id, int x, int y, double cal, World world) {
		super();

		this.uniqueID = id;
		this.calories = cal;
		this.xPos = x;
		this.yPos = y;
		this.positionAltered = true;
		j = new JLabel(getImageIcon("bushFull.png"));
		this.w = world;

		this.positionAltered = true;

		w.setEntityOnTheMap(xPos, yPos, this);

		if (w.getDEBUG()) {
			System.out
					.println("Created a new instance of bush using the file-read constructor...");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator.Entity#saveString()
	 */
	public String saveString() {
		String content = null;

		content = "bush" + "," + Integer.toString(uniqueID) + ","
				+ Integer.toString(xPos) + "," + Integer.toString(yPos) + ","
				+ Double.toString(calories);

		return content;

	}
}
