package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

import java.util.Random;

public abstract class PerishableFood extends Food {

	// private variables
	// whether it's gone off (turned bad)
	protected boolean isGoneOff;
	// number of days before it goes off
	protected int maxDays;
	// how many days left before it goes off
	protected int daysLeft;

	PerishableFood() {
		activity = new Thread(this);
	}

	// getters and setters
	public void setIsGoneOff(boolean b) {
		this.isGoneOff = b;
	}

	public boolean getIsGoneOff() {
		return this.isGoneOff;
	}

	public void setMaxDays(int d) {
		this.maxDays = d;
	}

	public int getMaxDays() {
		return this.maxDays;
	}

	public void setDaysLeft(int d) {
		this.daysLeft = d;
	}

	public int getDaysLeft() {
		return this.daysLeft;
	}

	// METHODS

	// Checks if the food has perished. If it has, turns it into a non-lethal
	// poison
	public void check() {

		if (this.daysLeft <= 0) {
			this.stop();
			this.turnIntoPoisonedFood();
		} else if (this.calories <= 0) {
			this.stop();
			w.setEntityOnTheMap(this.xPos, this.yPos, new EmptyCell(this.xPos,
					this.yPos, w));
		} else if (this.isGoneOff == false) {
			if (this.maxDays >= this.daysLeft) {
				this.daysLeft--;
			}
		}

		if (w.getDEBUG()) {
			System.out.println("Executed Perishable food's check function");
		}

	}

	protected void turnIntoPoisonedFood() {
		w.removeEntity(this);
		this.calories = 5;
		PoisonedFood p;

		Random rand = new Random();

		// chance to determine whether the PoisonedFood is lethal or not
		boolean chance = rand.nextBoolean();

		if (chance) {
			w.setEntityOnTheMap(this.xPos, this.yPos, new PoisonedFood(
					this.xPos, this.yPos, this.w, false, this.calories / 2,
					true));
		} else {

			w.setEntityOnTheMap(this.xPos, this.yPos, new PoisonedFood(
					this.xPos, this.yPos, this.w, true, 1, true));
		}

		if (w.getDEBUG()) {
			System.out.println("Turned a PerishableFood into PoisonedFood...");
		}
	}

	public void run() {
		while (!runTerminated) {
			try {
				this.check();
				Thread.sleep(delay); // go to sleep for some time
			} catch (InterruptedException ex) {
				// wake up!
			}
		}
	}
}
