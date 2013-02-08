package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

import javax.swing.JLabel;

/**
 * class EatenRenewableFood is an extension of class Food. Eaten Renewable Food
 * is what GrownRenewableFood turns into after it's been eaten.
 * 
 * @author Slava
 * 
 */
public class EatenRenewableFood extends Food {
	// variables

	protected int daysToGrow;
	protected int daysLeft;
	protected boolean isGrown;
	private String kindOfFood;

	/**
	 * Constructor for EatenRenewableFood used for creating new instances on the
	 * map on-the-go.
	 * 
	 * @param x
	 *            - position on the X axis
	 * @param y
	 *            - position on the Y axis
	 * @param world
	 *            - the World it's in
	 * @param startItself
	 *            - whether to start the Thread during the constructor. True
	 *            will start it automatically, flase will not
	 * @param kind
	 *            - what kind of food the food was before being eaten. Bush or
	 *            Tree or Grass
	 */
	EatenRenewableFood(int x, int y, World world, boolean startItself,
			String kind) {
		super();

		this.kindOfFood = kind;
		// int decision = rand.nextInt(3);

		// giving the object an icon depending on what it looked like
		if (kind == "bush") {
			this.j = new JLabel(this.getImageIcon("bushEmpty.png"));
		} else if (kind == "tree") {
			this.j = new JLabel(this.getImageIcon("treeEmpty.png"));
		} else if (kind == "grass") {
			this.j = new JLabel(this.getImageIcon("grassEmpty.png"));
		}

		// setting days to grow
		this.daysToGrow = 12;

		// setting days Left To Grow
		this.daysLeft = daysToGrow;

		// setting to NOT grown
		this.isGrown = false;

		// position altered to true, to make the map display it in the next
		// refresh cycle
		this.positionAltered = true;

		this.w = world;
		this.xPos = x;
		this.yPos = y;

		// Incrementing the Entity count
		entityCount++;
		this.uniqueID = entityCount;

		// if specified, start the Thread
		if (startItself) {
			activity.start();
		}

	}

	// getters and setters
	/**
	 * Setter for daysToGrow
	 * 
	 * @param d
	 *            - sets DaysToGrow
	 */
	public void setDaysToGrow(int d) {
		this.daysToGrow = d;
	}

	/**
	 * getter for daysToGrow
	 * 
	 * @return - daysToGrow
	 */
	public int getDaysToGrow() {
		return this.daysToGrow;
	}
	/**
	 *  setter for daysLeft
	 * @param d - daysLeft
	 */
	public void setDaysLeft(int d) {
		this.daysLeft = d;
	}
	
	/**
	 * getter for daysLeft
	 * @return
	 */
	public int getDaysLeft() {
		return this.daysLeft;
	}

	/**
	 * Setter for isGrown
	 * @param b - set isGrown to b
	 */
	public void setIsGrown(boolean b) {
		this.isGrown = b;
	}
	
	/**
	 * Getter for isGrown
	 * @return isGrown
	 */
	public boolean getIsGrown() {
		return this.isGrown;
	}

	// methods
	
	

	public void growIntoGrownFood() {
		// if the food is NOT grown, decrease the number of days left till
		// growth by one.

		if (this.kindOfFood == "bush") {
			w.setEntityOnTheMap(this.xPos, this.yPos, new Bush(this.xPos,
					this.yPos, w, true));
		} else if (this.kindOfFood == "grass") {
			w.setEntityOnTheMap(this.xPos, this.yPos, new Grass(this.xPos,
					this.yPos, w, true));
		} else if (this.kindOfFood == "tree") {
			Tree b = new Tree(this.xPos, this.yPos, w, true);
			w.setEntityOnTheMap(this.xPos, this.yPos, b);
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

	@Override
	public void check() {

		if (this.daysLeft <= 0) {
			this.isGrown = true;
		}
		if (this.isGrown) {
			this.stop();
			this.growIntoGrownFood();
		} else {
			this.daysLeft--;
		}
	}

}
