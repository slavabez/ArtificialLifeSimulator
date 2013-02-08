package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

import java.util.Random;

import javax.swing.JLabel;

import uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator.World.Direction;

public class Herbivore extends LifeForm implements HerbivoreInterface {

	boolean createdHerd = false;

	Herbivore(int x, int y, World world, boolean startItself) {
		super();
		Random rand = new Random();

		this.energy = rand.nextInt(20) + 30;
		// this.energy=5;
		this.maxHP = rand.nextInt(50) + 50;

		this.positionAltered = true;

		this.HP = this.maxHP;
		this.name = "Random Herbivore";
		this.size = rand.nextInt(3) + 7;
		boolean chance = rand.nextBoolean();
		if (chance) {
			j = new JLabel(getImageIcon("bugHerb1.png"));
		} else {
			j = new JLabel(getImageIcon("bugHerb2.png"));
		}
		this.sensingDistance = rand.nextInt(2) + 4;
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
					.println("Created a new instance of Herbivore using the main constructor...");
		}
	}

	Herbivore(String name, double energy, int hp, int maxHP) {
		super();

		Random rand = new Random();
		this.energy = energy;

		this.positionAltered = true;

		this.maxHP = hp;
		this.HP = hp;
		this.name = name;
		this.maxHP = maxHP;
		boolean chance = rand.nextBoolean();
		if (chance) {
			j = new JLabel(getImageIcon("bugHerb1.png"));
		} else {
			j = new JLabel(getImageIcon("bugHerb2.png"));
		}
		this.sensingDistance = rand.nextInt(2) + 4;

		entityCount++;
		this.uniqueID = entityCount;

		w.setEntityOnTheMap(xPos, yPos, this);

		if (w.getDEBUG()) {
			System.out.println("Created a new instance of Herbivore");
		}
	}

	Herbivore(int id, int xpos, int ypos, double energy, int maxhp, int hp,
			int size, int sensingdist, String name, World world) {
		super();

		this.uniqueID = id;
		this.xPos = xpos;
		this.yPos = ypos;
		this.energy = energy;

		this.positionAltered = true;

		this.maxHP = maxhp;
		this.HP = hp;
		this.size = size;
		this.sensingDistance = sensingdist;
		this.name = name;
		this.positionAltered = true;
		
		Random rand = new Random();
		
		boolean chance = rand.nextBoolean();
		if (chance) {
			j = new JLabel(getImageIcon("bugHerb1.png"));
		} else {
			j = new JLabel(getImageIcon("bugHerb2.png"));
		}
		this.w = world;

		w.setEntityOnTheMap(xPos, yPos, this);

		if (w.getDEBUG()) {
			System.out
					.println("Created a new instance of Herbivore using the file-read constructor...");
		}
	}

	/**
	 * Use this function to run away from a hostile life form. moves in the
	 * opposite direction of what is specified. Checks for empty cell, no need
	 * for another check.</br> To use, simply use the function and provide the
	 * direction of the hostile life form you're running away from.</br> If all
	 * three directions are non-passable, does nothing
	 * 
	 * @param d
	 *            - direction of where the hostile life form is. Moves away from
	 *            it by one square
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
			System.out.println("Executed Herbivore's (id: " + this.uniqueID
					+ ") runAway() function...");
		}
	}

	/**
	 * Thinking logic and execution of the Herbivore. Determines where to move
	 * and what to do, depending on the results it gets from smelling in all
	 * four directions. Priorities: 1) Run awa from a predator. 2)
	 */
	@Override
	public void move() {

		boolean hasMoved = false;

		// booleans that determine if there is an obstacle in that
		// direction.
		// Herbivores cannot sense through obstacles, therefore, if there is
		// an obstacle,
		// further sensing in that direction will be cancelled

		boolean obstacleNorth = false, obstacleSouth = false, obstacleEast = false, obstacleWest = false;

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
				if (n instanceof Meat) {
					// once an obstacle is marked, e.g. obstacleNorth is true,
					// then
					// all future sensing behind that square will not take place
					obstacleNorth = true;
				}
				if (s instanceof Meat) {
					obstacleSouth = true;
				}
				if (e instanceof Meat) {
					obstacleEast = true;
				}
				if (w instanceof Meat) {
					obstacleWest = true;
				}
				if (n instanceof PoisonedFood) {
					// once an obstacle is marked, e.g. obstacleNorth is true,
					// then
					// all future sensing behind that square will not take place
					obstacleNorth = true;
				}
				if (s instanceof PoisonedFood) {
					obstacleSouth = true;
				}
				if (e instanceof PoisonedFood) {
					obstacleEast = true;
				}
				if (w instanceof PoisonedFood) {
					obstacleWest = true;
				}

				if (n instanceof Carnivore && obstacleNorth == false) {
					this.runAway(Direction.North);
					hasMoved = true;
				} else if (s instanceof Carnivore && obstacleSouth == false) {
					this.runAway(Direction.South);
					hasMoved = true;
				} else if (e instanceof Carnivore && obstacleEast == false) {
					this.runAway(Direction.East);
					hasMoved = true;
				} else if (w instanceof Carnivore && obstacleWest == false) {
					this.runAway(Direction.West);
					hasMoved = true;
				} else if (n instanceof GrownRenewableFood
						&& obstacleNorth == false) {
					this.goToFood(Direction.North);
					hasMoved = true;
				} else if (s instanceof GrownRenewableFood
						&& obstacleNorth == false) {
					this.goToFood(Direction.South);
					hasMoved = true;
				} else if (e instanceof GrownRenewableFood
						&& obstacleEast == false) {
					this.goToFood(Direction.East);
					hasMoved = true;
				} else if (w instanceof GrownRenewableFood
						&& obstacleWest == false) {
					this.goToFood(Direction.West);
					hasMoved = true;
				} else if (n instanceof Herd && obstacleNorth == false) {
					this.follow(Direction.North);
					hasMoved = true;
				} else if (s instanceof Herd && obstacleNorth == false) {
					this.follow(Direction.South);
					hasMoved = true;
				} else if (e instanceof Herd && obstacleEast == false) {
					this.follow(Direction.East);
					hasMoved = true;
				} else if (w instanceof Herd && obstacleWest == false) {
					this.follow(Direction.West);
					hasMoved = true;
				} else if (n instanceof Herbivore && obstacleNorth == false) {
					this.follow(Direction.North);
					hasMoved = true;
				} else if (s instanceof Herbivore && obstacleNorth == false) {
					this.follow(Direction.South);
					hasMoved = true;
				} else if (e instanceof Herbivore && obstacleEast == false) {
					this.follow(Direction.East);
					hasMoved = true;
				} else if (w instanceof Herbivore && obstacleWest == false) {
					this.follow(Direction.West);
					hasMoved = true;
				}
			}

		}
		if (hasMoved == false) {

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

		if (w.getDEBUG()) {
			System.out.println("Executed Herbivore's (id: " + this.uniqueID
					+ ") move() function...");
		}
	}

	public void createHerd(Herbivore h) {

		
			Herd herd = new Herd(this, h, this.xPos, this.yPos);
			int oldX = this.xPos;
			int oldY = this.yPos;

			this.remove();

			w.setEntityOnTheMap(oldX, oldY, herd);
			herd.start();
			this.createdHerd = true;
			
			h.remove();


		if (w.getDEBUG()) {
			System.out.println("Created a new Herd using Herbivore id: "
					+ this.getUniqueId() + " (main Herbivore) and id: "
					+ h.getUniqueId() + "'s Herbivore");
		}

	}

	public void joinHerd(Herd h) {
		
		
		if (w.getDEBUG()) {
			System.out.println("A Herbivore (id: " + this.getUniqueId()
					+ ") joined a Herd (id: " + h.getUniqueId() + ")...");
		}

	}

	/**
	 * This function decreases Food's calories, increasing the Herbivore's
	 * energy levels. Amount of the bite depends on the size of the bug. Formula
	 * is 0.7 times Size.
	 * 
	 * @param f
	 *            - the food being bitten
	 */
	public void nibble(Food f) {
		double calories = f.getCalories();
		double bite = this.size * 0.7 * energyGainMultiplier;
		this.energy = this.energy + bite;
		f.setCalories(calories - bite);
		f.check();

		if (w.getDEBUG()) {
			System.out.println("Executed Herbivore's (id: " + this.uniqueID
					+ ") nibble(Food f) function on id: " + f.getUniqueId());
		}

	}

	/**
	 * This function follows another Herbivore or a Herd, and creates a new Herd
	 * or joins an existing Herd when it's close.
	 */
	@Override
	public void follow(Direction d) {
		// if empty, move
		if (this.sense(d, 1) instanceof EmptyCell) {
			super.move(d);
		} else if (this.sense(d, 1) instanceof Herbivore) {

			// if the other herbivore is larger, simply wait
			// else, create a Herd with the other Herbivore
			if (((Herbivore) this.sense(d, 1)).size > this.size) {

			} else {
				this.createHerd(((Herbivore) this.sense(d, 1)));
			}

		} else if (this.sense(d, 1) instanceof Herd) {
			this.joinHerd((Herd) this.sense(d, 1));
		}

		if (w.getDEBUG()) {
			System.out.println("Executed Herbivore's (id: " + this.uniqueID
					+ ") follow() function...");
		}
	}

	public void goToFood(Direction d) {

		if (this.sense(d, 1) instanceof EmptyCell) {
			super.move(d);
		} else if (this.sense(d, 1) instanceof GrownRenewableFood) {
			this.nibble((Food) this.sense(d, 1));
		}

		if (w.getDEBUG()) {
			System.out.println("Executed Herbivore's (id: " + this.uniqueID
					+ ") goToFood() function...");
		}

	}

	public String saveString() {
		String content = null;

		content = "herbivore" + "," + Integer.toString(uniqueID) + ","
				+ Integer.toString(xPos) + "," + Integer.toString(yPos) + ","
				+ Double.toString(energy) + "," + Integer.toString(maxHP) + ","
				+ Integer.toString(HP) + "," + Integer.toString(size) + ","
				+ Integer.toString(sensingDistance) + "," + this.name;

		return content;
	}

	@Override
	public void check() {
		this.checkIfDied();
		if (isDead == false) {
			this.w.setEntityOnTheMap(xPos, yPos, this);
		}

		if (w.getDEBUG()) {
			System.out.println("Executed Herbivore's (id: " + this.uniqueID
					+ ") check() function...");
		}

	}

	@Override
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
