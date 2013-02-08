package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

import java.util.Random;

import javax.swing.JLabel;

public class Meat extends PerishableFood {

	// constructor with no parameters, creates a Meat object with 10-20
	// calories, 7 days expiry time
	Meat(int x, int y, World world, boolean startItself) {
		this.activity = new Thread(this);
		Random rand = new Random();
		this.calories = rand.nextInt(10) + 10;
		this.isGoneOff = false;
		this.maxDays = 7;
		
		this.positionAltered=true;
		
		this.daysLeft = this.maxDays;
		
		j = new JLabel(getImageIcon("meat.png"));
		
		entityCount++;
		this.uniqueID = entityCount;
		this.w = world;
		this.xPos = x;
		this.yPos = y;

		w.setEntityOnTheMap(xPos, yPos, this);

		if (startItself) {
			this.start();
		}

		if (w.getDEBUG()) {
			System.out
					.println("Created a new instance of Meat using the main constructor...");
		}
	}

	Meat(int x, int y, World world, boolean startItself, double cal) {
		super();
		
		
		Random rand = new Random();
		this.maxDays=rand.nextInt(5)+25;
		this.isGoneOff = false;
		this.calories=cal;
		this.daysLeft = this.maxDays;
		entityCount++;
		this.uniqueID = entityCount;
		this.w = world;
		
		this.positionAltered=true;
		
		this.xPos = x;
		this.yPos = y;
		
		j = new JLabel(getImageIcon("meat.png"));

		w.setEntityOnTheMap(xPos, yPos, this);

		if (startItself) {
			this.start();
		}
		if (w.getDEBUG()) {
			System.out
					.println("Created a new instance of Meat using the man constructor, secifying the amount of maxDays...");
		}
	}

	Meat(int id, int x, int y, double cal, int daysleft, int maxdays,
			World world) {
		super();

		this.uniqueID = id;
		this.xPos = x;
		this.yPos = y;
		this.calories = cal;
		this.daysLeft = daysleft;
		this.maxDays = maxdays;
		
		this.positionAltered=true;
		
		this.w = world;
		
		j = new JLabel(getImageIcon("meat.png"));

		w.setEntityOnTheMap(xPos, yPos, this);

		if (w.getDEBUG()) {
			System.out.println("Created a new instance of meat using the file-read constructor...");
		}

	}

	Meat(int size) {
		super();

		Random rand = new Random();
		this.calories = rand.nextInt(10) + 10;
		this.calories = this.calories * this.foodCalorieMultiplier * size;
		entityCount++;
		this.uniqueID = entityCount;
		
		j = new JLabel(getImageIcon("meat.png"));
		
		if (w.getDEBUG()) {
		System.out.println("Created a new instace of Meat using the Meat(size) constructor...");
		}

	}

	public String saveString() {
		String content = null;

		content = "meat" + "," + Integer.toString(uniqueID) + ","
				+ Integer.toString(xPos) + "," + Integer.toString(yPos) + ","
				+ Double.toString(calories) + "," + Integer.toString(daysLeft)
				+ "," + Integer.toString(maxDays);

		return content;
	}

}
