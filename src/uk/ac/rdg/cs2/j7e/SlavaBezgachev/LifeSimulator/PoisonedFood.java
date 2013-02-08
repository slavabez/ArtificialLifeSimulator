package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

import java.util.Random;

import javax.swing.JLabel;

public class PoisonedFood extends Food {

	// If lethal - kills the life form instantly
	protected boolean isLethal;
	// If non-lethal simply deals damage
	protected int damage;

	protected int isDegraded;
	protected int degradedCount = 0;
	protected static int daysToDegrade = 15;

	// Constructor with 1 variable, an integer. Creates a Poisoned Food with the
	// specified damage, non-lethal
	// Still has calories, but only half the amount of Fresh Food. Also, damages
	PoisonedFood(int d, double perishFoodCal) {
		super();

		this.isLethal = false;
		this.damage = d;
		this.calories = perishFoodCal * 0.5;
		
		this.positionAltered=true;
		
		entityCount++;
		this.uniqueID = entityCount;

		j = new JLabel(getImageIcon("poisonedMeat.png"));

		w.setEntityOnTheMap(xPos, yPos, this);
	}

	// Constructor with no parameters, creates a lethal Poisoned Food. No Damage
	// necessary, as it kills instantly
	PoisonedFood(int x, int y, World w, boolean lethal, double cal,
			boolean startItself) {
		super();

		this.xPos = x;
		this.yPos = y;
		this.w = w;
		
		this.positionAltered=true;
		
		this.calories = cal;

		Random rand = new Random();
		this.damage = rand.nextInt(10) + 5;

		j = new JLabel(getImageIcon("poisonedMeat.png"));

		this.isLethal = lethal;

		if (this.isLethal) {
			this.calories = 0.1;
		}

		entityCount++;
		this.uniqueID = entityCount;

		w.setEntityOnTheMap(xPos, yPos, this);

		if (startItself) {
			this.start();
		}

		if (w.getDEBUG()) {
			System.out
					.println("Created an instance of PoisonedFood using the main constructor...");
		}
	}

	PoisonedFood(int x, int y, World w, boolean startItself, double cal) {
		super();

		this.calories = cal / 2;
		this.isLethal = false;
		this.xPos = x;
		
		this.positionAltered=true;
		
		this.yPos = y;
		this.w = w;
		entityCount++;
		this.uniqueID = entityCount;

		Random rand = new Random();

		this.damage = rand.nextInt(10) + 5;

		j = new JLabel(getImageIcon("poisonedMeat.png"));

		if (startItself) {
			this.start();
		}
		w.setEntityOnTheMap(xPos, yPos, this);
	}

	// Constructor with one boolean parameter, creates a non-lethal Poisoned
	// Food with a random damage value (1-10)
	PoisonedFood(boolean b, double perishFoodCal) {
		super();
		
		this.positionAltered=true;
		

		if (b == true) {
			this.isLethal = true;
		} else {
			Random rand = new Random();
			this.isLethal = false;
			this.damage = rand.nextInt(10) + 1;
			this.calories = perishFoodCal * 0.5;
		}
		entityCount++;
		this.uniqueID = entityCount;

		j = new JLabel(getImageIcon("poisonedMeat.png"));

		w.setEntityOnTheMap(xPos, yPos, this);
	}

	PoisonedFood(int id, int x, int y, double cal, int dam, boolean isLethal,
			World world) {
		super();

		this.uniqueID = id;
		this.xPos = x;
		this.yPos = y;
		
		this.positionAltered=true;
		
		this.calories = cal;
		this.damage = dam;
		this.isLethal = isLethal;
		this.w = world;

		j = new JLabel(getImageIcon("poisonedMeat.png"));

		w.setEntityOnTheMap(xPos, yPos, this);

		if (w.getDEBUG()) {
			System.out
					.println("Created a new instance of PoisonedFood using the file-read constructor...");
		}
	}

	public void setIsLethal(boolean l) {
		this.isLethal = l;
	}

	public boolean getIsLethal() {
		return this.isLethal;
	}

	public void setDamage(int d) {
		this.damage = d;
	}

	public int getDamage() {
		return this.damage;
	}

	public String saveString() {
		String content = null;

		content = "poisoned" + "," + Integer.toString(uniqueID) + ","
				+ Integer.toString(xPos) + "," + Integer.toString(yPos) + ","
				+ Double.toString(calories) + "," + Integer.toString(damage)
				+ "," + Boolean.toString(isLethal);

		return content;
	}

	/**
	 * Degrades the PoisonedFood object. If not consumed within daysToDegrade
	 * days (15 by default), the PoisonedFood is removed from the map
	 */
	private void degrade() {
		this.degradedCount++;
		if (this.degradedCount >= daysToDegrade) {
			this.remove();
		}
	}

	@Override
	public void check() {
		degrade();
		if (this.calories <= 0) {
			this.remove();
		}

		if (w.getDEBUG()) {
			System.out.println("Executed PoisonedFood's check function...");
		}

	}

	@Override
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
