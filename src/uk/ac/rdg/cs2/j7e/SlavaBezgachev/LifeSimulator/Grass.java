package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

import java.util.Random;

import javax.swing.JLabel;

public class Grass extends GrownRenewableFood {

	/**
	 * Constructor with no parameters. Creates a Grass with calories of 10-25,
	 * and 5-8 Days to Grow. The grass is Grown at the start
	 */
	Grass() {
		super();
		Random rand = new Random();
		this.calories = rand.nextInt(15) + 10;
		
		this.positionAltered=true;
		
		j = new JLabel(getImageIcon("grassFull.png"));
		entityCount++;
		this.uniqueID = entityCount;

		w.setEntityOnTheMap(xPos, yPos, this);
	}

	Grass(int x, int y, World world, boolean startItself) {
		super();
		Random rand = new Random();
		this.calories = rand.nextInt(15) + 10;
		j = new JLabel(getImageIcon("grassFull.png"));
		
		this.positionAltered=true;

		this.xPos = x;
		this.yPos = y;
		this.w = world;

		entityCount++;
		this.uniqueID = entityCount;

		w.setEntityOnTheMap(xPos, yPos, this);

		if (startItself == true) {
			activity.start();
		}

		if (w.getDEBUG()) {
			System.out
					.println("Created a new instance of Grass using the main constructor...");
		}
	}

	/**
	 * A constructor with two integer parameters. Sets Grown status to true in
	 * the beginning.
	 * 
	 * @param c
	 *            - sets the amount of calories
	 * @param d
	 *            - sets the number of days required to grow back after eaten
	 */
	Grass(int c) {
		super();
		this.calories = c;
		
		this.positionAltered=true;
		
		this.uniqueID = entityCount;

		j = new JLabel(getImageIcon("grassFull.png"));
		entityCount++;
		this.uniqueID = entityCount;

		w.setEntityOnTheMap(xPos, yPos, this);
	}

	Grass(int id, int x, int y, double cal, World world, boolean startItself) {
		super();
		this.uniqueID = id;
		this.calories = cal;
		this.xPos = x;
		this.yPos = y;
		
		
		this.positionAltered = true;
		this.w = world;

		j = new JLabel(getImageIcon("grassFull.png"));

		w.setEntityOnTheMap(xPos, yPos, this);

		if (startItself == true) {
			activity.start();
		}

		if (w.getDEBUG()) {
			System.out
					.println("Created a new instance of Grass using the file-read constructor...");
		}
	}

	public String saveString() {
		String content = null;

		content = "grass" + "," + Integer.toString(uniqueID) + ","
				+ Integer.toString(xPos) + "," + Integer.toString(yPos) + ","
				+ Double.toString(calories);

		return content;
	}
}
