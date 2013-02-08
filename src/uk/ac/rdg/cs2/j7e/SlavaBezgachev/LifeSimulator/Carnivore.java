package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

import java.util.Random;

import javax.swing.JLabel;

import uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator.World.Direction;

/**
 * Carnivore is an extension of class Life Form. Carnivores are hostile Life
 * Forms, they feed on Meat, which is what Life Froms become after death.
 * Carnivores attack other Life Forms: Herbivores and Herds regardless of size
 * and other Carnivores, but only if they are smaller in size. If the other
 * Carnivore is larger, it runs away.
 * 
 * @author Slava
 * 
 */

public class Carnivore extends LifeForm implements CarnivoreInterface {

	/**
	 * Constructor used to create a new random Carnivore. Energy - 30-50, HP -
	 * 50-100, size - 7-10, sensing distance - 4-6
	 * 
	 * @param x
	 *            - position on the X axis
	 * @param y
	 *            - position on the Y axis
	 * @param world
	 *            - the world Carnivore wants to be in
	 * @param startItself
	 *            - whether to start Carnivore's Thread automatically. true will
	 *            start, false will not.
	 */
	Carnivore(int x, int y, World world, boolean startItself) {
		super();

		Random rand = new Random();

		this.energy = rand.nextInt(20) + 30;
		// this.energy = 5;
		this.maxHP = rand.nextInt(50) + 50;
		this.HP = this.maxHP;
		this.name = "Random Carnivore";
		this.size = rand.nextInt(3) + 7;
		boolean chance = rand.nextBoolean();
		if (chance) {
			j = new JLabel(getImageIcon("bugCarn1.png"));
		} else {
			j = new JLabel(getImageIcon("bugCarn2.png"));
		}
		this.sensingDistance = rand.nextInt(2) + 4;
		this.w = world;
		this.xPos = x;
		this.yPos = y;

		this.positionAltered = true;

		entityCount++;
		this.uniqueID = entityCount;

		if (startItself == true) {
			activity.start();
		}
		// temporarily get rid of this
		// w.setEntityOnTheMap(xPos, yPos, this);

		if (w.getDEBUG()) {
			System.out
					.println("Created a new instance of Carnivore using the main constructor...");
		}
	}

	/**
	 * Constructor used to create a new random Carnivore. Energy - 30-50, HP -
	 * 50-100, sensing distance - 4-6. Size is specified
	 * 
	 * @param x
	 *            - position on the X axis
	 * @param y
	 *            - position on the Y axis
	 * @param world
	 *            - the world Carnivore wants to be in
	 * @param startItself
	 *            - whether to start Carnivore's Thread automatically. true will
	 *            start, false will not.
	 * @param size
	 *            - specifying the size of the Carnivore
	 */
	Carnivore(int x, int y, World world, boolean startItself, int size) {
		super();

		Random rand = new Random();

		this.positionAltered = true;

		this.energy = rand.nextInt(20) + 30;
		// this.energy=5;
		this.maxHP = rand.nextInt(50) + 50;
		this.HP = this.maxHP;
		this.name = "Random Carnivore";
		this.size = size;
		boolean chance = rand.nextBoolean();
		if (chance) {
			j = new JLabel(getImageIcon("bugCarn1.png"));
		} else {
			j = new JLabel(getImageIcon("bugCarn2.png"));
		}
		this.sensingDistance = rand.nextInt(2) + 4;
		this.w = world;
		this.xPos = x;
		this.yPos = y;

		entityCount++;
		this.uniqueID = entityCount;

		if (startItself == true) {
			activity.start();
		}
		// temporarily get rid of this
		// w.setEntityOnTheMap(xPos, yPos, this);

		if (w.getDEBUG()) {
			System.out
					.println("Created a new instance of Carnivore using the main constructor...");
		}
	}

	/**
	 * A constructor used to initiate the object from a file String
	 * 
	 * @param name
	 *            - Carnivore's name
	 * @param energy
	 *            - Carnivore's energy
	 * @param hp
	 *            - Carnivore's Hit points
	 * @param size
	 *            - Carnivore's size
	 * @param startItself
	 *            - whether to start the Thread for this Carnivore
	 */
	Carnivore(String name, double energy, int hp, int size, boolean startItself) {
		super();
		Random rand = new Random();
		this.energy = energy;
		this.maxHP = hp;
		this.HP = hp;

		this.positionAltered = true;

		this.name = name;
		this.size = size;
		boolean chance = rand.nextBoolean();
		if (chance) {
			j = new JLabel(getImageIcon("bugCarn1.png"));
		} else {
			j = new JLabel(getImageIcon("bugCarn2.png"));
		}
		this.sensingDistance = rand.nextInt(2) + 4;

		entityCount++;
		this.uniqueID = entityCount;

		w.setEntityOnTheMap(xPos, yPos, this);

		if (startItself == true) {
			activity.start();
		}

		if (w.getDEBUG()) {
			System.out
					.println("Created a new instance of Carnivore using the random constructor without coordinates...");
		}
	}

	/**
	 * A constructor used to initiate the object from a file String
	 * 
	 * @param id
	 *            - id value
	 * @param xpos
	 *            - position on the X axis
	 * @param ypos
	 *            - position on the Y axis
	 * @param energy
	 *            - amount of Energy
	 * @param maxhp
	 *            - value for maximum amount of hit Points
	 * @param hp
	 *            - current amount of Hit Points
	 * @param size
	 *            - size
	 * @param sensingdist
	 *            - sensing distance
	 * @param name
	 *            - Carnivore's name
	 * @param world
	 *            - the world Carnivore is in
	 */
	Carnivore(int id, int xpos, int ypos, double energy, int maxhp, int hp,
			int size, int sensingdist, String name, World world) {
		super();

		Random rand = new Random();

		this.positionAltered = true;

		this.uniqueID = id;
		this.xPos = xpos;
		this.yPos = ypos;
		this.energy = energy;
		this.maxHP = maxhp;
		this.HP = hp;
		this.size = size;
		this.sensingDistance = sensingdist;
		this.name = name;
		this.positionAltered = true;
		this.w = world;
		boolean chance = rand.nextBoolean();
		if (chance) {
			j = new JLabel(getImageIcon("bugCarn1.png"));
		} else {
			j = new JLabel(getImageIcon("bugCarn2.png"));
		}

		w.setEntityOnTheMap(xPos, yPos, this);

		if (w.getDEBUG()) {
			System.out
					.println("Created a new instance of Carnivore using the file-read constructor...");
		}
	}

	/**
	 * getter for energySpeedMultiplier
	 * 
	 * @return the value for the energySpendMultiplier
	 */
	public float getEnergySpendMultiplier() {
		return energySpendMultiplier;
	}

	/**
	 * setter for energySpeedMultiplier
	 * 
	 * @param f
	 *            - sets energySpeedMultiplier
	 */
	public void setEnergySpendMultiplier(float f) {
		energySpendMultiplier = f;
	}

	/**
	 * Getter for energyGainMultiplier
	 * 
	 * @return returns energyGainMultiplier
	 */
	public float getEnergyGainMultiplier() {
		return energyGainMultiplier;
	}

	/**
	 * setter for energyGainMultiplier
	 * 
	 * @param f
	 *            - sets energyGainMultiplier
	 */
	public void setEnergyGainMultiplier(float f) {
		energyGainMultiplier = f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator.CarnivoreInterface#attack
	 * (uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator.LifeForm)
	 */
	public void attack(LifeForm l) {
		// calculating damage dealt and assigning it to an integer
		int damageDealt = this.size * 4;
		// setting target's Hit Points to a new updated value
		l.setHP(l.getHP() - damageDealt);
		// deducting this carnivore's energy for the attack
		this.energy = this.energy - 1 * energySpendMultiplier;
		// debugging info
		if (w.getDEBUG()) {
			System.out
					.println("A Carnivore is attacking a life form (hopefully not itself...). Id:"
							+ l.getUniqueId());
		}
	}

	/**
	 * Carnivore's move function handles a lot of tasks. It analyses the objects
	 * in all four directions, decides what it wants to do and does it. Look
	 * inside of details of how it is execited.
	 */
	@Override
	public void move() {
		// booleans represent presence of an obstacle in a certain direction.

		boolean obstacleNorth = false, obstacleSouth = false, obstacleEast = false, obstacleWest = false;

		// hasMoved boolean represents whether this carnivore has or hasn't
		// moved.
		// used for the loop
		boolean hasMoved = false;

		for (int i = 0; i < this.sensingDistance; i++) {
			if (hasMoved == false) {

				Object n = this.sense(Direction.North, i + 1);
				Object s = this.sense(Direction.South, i + 1);
				Object e = this.sense(Direction.East, i + 1);
				Object w = this.sense(Direction.West, i + 1);

				// smells and marks path as obstacle if there's an obstacle in
				// the
				// way
				if (n instanceof Obstacle) {
					// once an obstacle is marked, e.g. obstacleNorth is true,
					// then
					// all future sensing behind that square will not take place
					obstacleNorth = true;
				}
				if (s instanceof Obstacle) {
					obstacleSouth = true;
				}
				if (e instanceof Obstacle) {
					obstacleEast = true;
				}
				if (w instanceof Obstacle) {
					obstacleWest = true;
				}
				if (n instanceof EatenRenewableFood) {
					// once an obstacle is marked, e.g. obstacleNorth is true,
					// then
					// all future sensing behind that square will not take place
					obstacleNorth = true;
				}
				if (s instanceof EatenRenewableFood) {
					obstacleSouth = true;
				}
				if (e instanceof EatenRenewableFood) {
					obstacleEast = true;
				}
				if (w instanceof EatenRenewableFood) {
					obstacleWest = true;
				}

				// if they see another Carnivore, they will interact with it
				// (either
				// attack or run away)
				if (n instanceof Carnivore && obstacleNorth == false) {
					this.interactWithOtherCarnivore(
							(Carnivore) this.sense(Direction.North, i + 1),
							Direction.North);
					hasMoved = true;
				} else if (s instanceof Carnivore && obstacleSouth == false) {
					this.interactWithOtherCarnivore(
							(Carnivore) this.sense(Direction.South, i + 1),
							Direction.South);
					hasMoved = true;
				} else if (e instanceof Carnivore && obstacleEast == false) {
					this.interactWithOtherCarnivore(
							(Carnivore) this.sense(Direction.East, i + 1),
							Direction.East);
					hasMoved = true;
				} else if (w instanceof Carnivore && obstacleWest == false) {
					this.interactWithOtherCarnivore(
							(Carnivore) this.sense(Direction.West, i + 1),
							Direction.West);
					hasMoved = true;
				} else if (n instanceof Meat && obstacleNorth == false) {
					// If it sees Meat, it will execute the goToFood function,
					// which deals with food interactions
					this.goToFood(Direction.North);
					hasMoved = true;
				} else if (s instanceof Meat && obstacleNorth == false) {
					this.goToFood(Direction.South);
					hasMoved = true;
				} else if (e instanceof Meat && obstacleEast == false) {
					this.goToFood(Direction.East);
					hasMoved = true;
				} else if (w instanceof Meat && obstacleWest == false) {
					this.goToFood(Direction.West);
					hasMoved = true;
				} else if (n instanceof Herbivore && obstacleNorth == false) {
					// if a Herbivore or a Herd is seen, the 'chase' function is
					// executed, which deals with Herbivore and Herd
					// interactions
					this.chase(Direction.North);
					hasMoved = true;
				} else if (s instanceof Herbivore && obstacleNorth == false) {
					this.chase(Direction.South);
					hasMoved = true;
				} else if (e instanceof Herbivore && obstacleEast == false) {
					this.chase(Direction.East);
					hasMoved = true;
				} else if (w instanceof Herbivore && obstacleWest == false) {
					this.chase(Direction.West);
					hasMoved = true;
				} else if (n instanceof Herd && obstacleNorth == false) {
					// Same as if he smelled Herbivore
					this.chase(Direction.North);
					hasMoved = true;
				} else if (s instanceof Herd && obstacleNorth == false) {
					this.chase(Direction.South);
					hasMoved = true;
				} else if (e instanceof Herd && obstacleEast == false) {
					this.chase(Direction.East);
					hasMoved = true;
				} else if (w instanceof Herd && obstacleWest == false) {
					this.chase(Direction.West);
					hasMoved = true;
				} else if (n instanceof PoisonedFood && obstacleNorth == false) {
					// Last priority is PoisonedFood. If there's nothing else to
					// do, the Carnivore will try to go to the Poisoned Food and
					// eat it.
					this.goToFood(Direction.North);
					hasMoved = true;
				} else if (s instanceof PoisonedFood && obstacleNorth == false) {
					this.goToFood(Direction.South);
					hasMoved = true;
				} else if (e instanceof PoisonedFood && obstacleEast == false) {
					this.goToFood(Direction.East);
					hasMoved = true;
				} else if (w instanceof PoisonedFood && obstacleWest == false) {
					this.goToFood(Direction.West);
					hasMoved = true;
				}
			}

		}
		// if nothing happened up to this point, i.e. the Carnivore still hasn't
		// moved, then a random direction to move in is selected
		if (hasMoved == false) {
			// random number to decide the direction. Each if statement consists
			// of directions in a different order, which makes the Direction
			// selection process more random than simply going in one direction.
			Random rand = new Random();
			int decision = rand.nextInt(4);

			if (decision == 0) {

				if (this.sense(Direction.North, 1) instanceof EmptyCell) {
					super.move(Direction.North);
				} else if (this.sense(Direction.South, 1) instanceof EmptyCell) {
					super.move(Direction.South);
				} else if (this.sense(Direction.West, 1) instanceof EmptyCell) {
					super.move(Direction.West);
				} else if (this.sense(Direction.East, 1) instanceof EmptyCell) {
					super.move(Direction.East);
				}
			} else if (decision == 1) {

				if (this.sense(Direction.South, 1) instanceof EmptyCell) {
					super.move(Direction.South);
				} else if (this.sense(Direction.North, 1) instanceof EmptyCell) {
					super.move(Direction.North);
				} else if (this.sense(Direction.West, 1) instanceof EmptyCell) {
					super.move(Direction.West);
				} else if (this.sense(Direction.East, 1) instanceof EmptyCell) {
					super.move(Direction.East);
				}
			} else if (decision == 2) {

				if (this.sense(Direction.West, 1) instanceof EmptyCell) {
					super.move(Direction.West);
				} else if (this.sense(Direction.East, 1) instanceof EmptyCell) {
					super.move(Direction.East);
				} else if (this.sense(Direction.South, 1) instanceof EmptyCell) {
					super.move(Direction.South);
				} else if (this.sense(Direction.North, 1) instanceof EmptyCell) {
					super.move(Direction.North);
				}
			} else if (decision == 3) {

				if (this.sense(Direction.East, 1) instanceof EmptyCell) {
					super.move(Direction.East);
				} else if (this.sense(Direction.South, 1) instanceof EmptyCell) {
					super.move(Direction.South);
				} else if (this.sense(Direction.West, 1) instanceof EmptyCell) {
					super.move(Direction.West);
				} else if (this.sense(Direction.North, 1) instanceof EmptyCell) {
					super.move(Direction.North);
				}
			}

		}
		// debugging string
		if (w.getDEBUG()) {
			System.out.println("Executed Carnivore's (id: " + this.uniqueID
					+ ") move() function...");
		}

	}

	/**
	 * the chase function is responsible for dealing with interactions with
	 * Herbivores and Herds and smaller Carnivores. The function will check if
	 * the Herbivore/Herd/smaller Carnivore is in the adjacent cell. <br>
	 * If it is, then the Carnivore attacks it. Otherwise he moves towards it in
	 * this direction.
	 * 
	 * @param d
	 *            - Direction in which the other Life Forms is sensed
	 */
	public void chase(Direction d) {
		if (this.sense(d, 1) instanceof EmptyCell) {
			super.move(d);
		} else if (this.sense(d, 1) instanceof Carnivore) {
			this.attack((LifeForm) this.sense(d, 1));
		} else if (this.sense(d, 1) instanceof Herbivore) {
			this.attack((LifeForm) this.sense(d, 1));
		} else if (this.sense(d, 1) instanceof Herd) {
			this.attack((LifeForm) this.sense(d, 1));
		}
		if (w.getDEBUG()) {
			System.out.println("Executed Carnivore's (id: " + this.uniqueID
					+ ") chase() function...");
		}
	}

	/**
	 * This function is called when the Carnivore needs to run away from a
	 * larger Carnivore. Runs away in the direction opposite of the Direction
	 * the larger Carnivore. If it is unaccessible, runs away in a perpendicular
	 * direction. It it's also unaccessible, runs away in the other
	 * perpendicular direction. If no directions are accessible, does nothing.
	 * 
	 * @param d
	 *            - the Direction of the larger Carnivore
	 */
	public void runAway(Direction d) {
		Direction r; // r - direction to run away in
		// d is kept to know the original Direction

		// first, we invert the direction
		r = this.w.invertDirection(d);

		if (this.sense(r, 1) instanceof EmptyCell) { // if the cell is empty,
														// move there
			super.move(r);
		} else if (this.sense(w.getPerpendicularDirection1(r), 1) instanceof EmptyCell) { // otherwise
																							// take
																							// the
																							// perpendicular
																							// direction
																							// and
																							// move
																							// there
																							// if
																							// it's
																							// empty
			super.move(w.getPerpendicularDirection1(r));
		} else if (this.sense(w.getPerpendicularDirection2(r), 1) instanceof EmptyCell) { // otherwise
																							// take
																							// the
																							// other
																							// perpendicular
																							// direction
																							// and
																							// move
																							// there
																							// if
																							// it's
																							// empty
			super.move(w.getPerpendicularDirection2(r));
		}
		if (w.getDEBUG()) {

			System.out.println("Executed Carnivore's (id: " + this.uniqueID
					+ ") runAway() function...");
		}
	}

	/**
	 * This function is to determine whether the other carnivore is larger or
	 * smaller, and then call the neccessary functions to deal with it.
	 * 
	 * @param c
	 *            - the other Carnivore
	 * @param d
	 *            - the direction of the other Carnivore
	 */
	public void interactWithOtherCarnivore(Carnivore c, Direction d) {
		if (c.getSize() > this.getSize()) {
			// if the other carnivore is larger, calls the runAway function IN
			// the direction of the carnivore
			this.runAway(d);
		} else {
			// otherwise calls the chase function in the direction of the other
			// carnivore
			this.chase(d);
		}
		// debugging string
		if (w.getDEBUG()) {
			System.out.println("Executed Carnivore's (id: " + this.uniqueID
					+ ") interactWithAnotherCarnivore() function...");
		}
	}

	/**
	 * Takes a bite of the specified Food object. Increases the energy and Hit
	 * Points of the carnivore, while decreasing the Food's caliroes
	 * 
	 * @param f
	 *            - the Food that the Carnivore wants to nibble on
	 */
	public void nibble(Food f) {
		// getting the food's calories
		double calories = f.getCalories();

		// calculating the bite
		double bite = this.size * 0.7;

		// adding energy to this carnivore
		this.energy = this.energy + bite * energyGainMultiplier;

		// adding some HP
		this.HP = this.HP + 5;

		// checking to make sure we do not exceed maximum hit Points
		if (this.HP > this.maxHP) {
			this.HP = this.maxHP;
		}

		// updating Food's calories
		f.setCalories(calories - bite);

		// if the Food consumed was a object of type PoisonedFood, then we're
		// checking if it was lethal (it's boolean value). If it was lethal, we
		// die :(. If it wasn't , we simply deduct Hit Points equal to amount of
		// Damage (PoisonedFood's attribute)
		if (f instanceof PoisonedFood) {
			if (((PoisonedFood) f).getIsLethal() == true) {
				this.die();
				f.remove();
			} else {
				this.HP = this.HP - ((PoisonedFood) f).getDamage();
				// checking if the carnivore is still alive after death
				this.checkIfDied();
			}
		}

		// force checking the Food, to make sure it behaves properly
		f.check();

		// debugging string
		if (w.getDEBUG()) {
			System.out.println("Executed Carnivore's (id: " + this.uniqueID
					+ ") nibble() function. Nibbled on (id: " + f.getUniqueId()
					+ ")...");
		}
	}

	/**
	 * the function that deals with Food interactions. Nibbles on it if it's
	 * close, otherwise goes towards it
	 * 
	 * @param d
	 *            - direction of the food
	 */
	public void goToFood(Direction d) {
		// if nothing, move
		if (this.sense(d, 1) instanceof EmptyCell) {
			super.move(d);
		} else if (this.sense(d, 1) instanceof Meat) {
			// if Meat, take a bite
			this.nibble((Food) this.sense(d, 1));
		} else if (this.sense(d, 1) instanceof PoisonedFood) {
			// if PosionedFood, also take a bite
			this.nibble((Food) this.sense(d, 1));
		}

		// debugging string
		if (w.getDEBUG()) {
			System.out.println("");
		}
	}

	/**
	 * Produces a String that can then be saved to the file.
	 * 
	 * @return String content - String of a defined format, look inside for details
	 * 
	 * @see uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator.Entity#saveString()
	 */
	public String saveString() {
		String content = null;

		content = "carnivore" + "," + Integer.toString(uniqueID) + ","
				+ Integer.toString(xPos) + "," + Integer.toString(yPos) + ","
				+ Double.toString(energy) + "," + Integer.toString(maxHP) + ","
				+ Integer.toString(HP) + "," + Integer.toString(size) + ","
				+ Integer.toString(sensingDistance) + "," + this.name;

		if (w.getDEBUG()) {
			System.out.println("");
		}
		return content;
	}

	/** Carnivore's check function which is executed in the Thread's run method
	 * @see uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator.Entity#check()
	 */
	public void check() {
		this.checkIfDied();
		
		//debugging String
		 System.out.println("Executed Carnivore's (id: " + this.uniqueID
		 + ") check() function...");
	}

	/** Carnivore's run function, overrides Thread's run function. Run consists of: <br>1) move<br>2) check<br>3) sleep for a bit
	 * @see uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator.LifeForm#run()
	 */
	public void run() {
		while (!runTerminated) {
			try {
				this.move();
				this.check();
				Thread.sleep(delay); // go to sleep for some time
			} catch (InterruptedException ex) {
				// wake up!
			}
		}
	}
}
