package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;

import uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator.World.Direction;

/**
 * A herd is a an array of Herbivore life forms. When two herbivore life forms
 * meet, they are joined together in a "Herd". The Herd has same stats and
 * variables a
 * 
 * @author Slava
 * 
 */
public class Herd extends LifeForm {
	// Herd is a collection of Life Forms that grouped together and share a
	// single cell. They move and behave as a single unit, but preserve the
	// attributes of individuals
	private List<LifeForm> herd = new ArrayList<LifeForm>();
	private boolean consumedAHerd = false;

	/**
	 * Constructor used to create a new Herd. Joins the two herbivores,
	 * assigning the Herd the neccessary properties. The Larger herbivore
	 * (bigger size) will determine the stats for the herd.
	 * 
	 * @param a
	 *            - first Herbivore
	 * @param b
	 *            - second Herbivore
	 */
	Herd(Herbivore a, Herbivore b, int x, int y) {
		super();
		// boolean to determine who is larger
		boolean aIsLarger = true;

		this.positionAltered = true;

		if (a.getSize() < b.getSize()) {
			aIsLarger = false;
		}

		// adding both Herbivores to the List
		this.herd.add(a);
		this.herd.add(b);

		// if a is larger, assign all values of a
		if (aIsLarger == true) {
			this.size = a.getSize();
			this.w = a.getWorld();
			this.sensingDistance = a.getSensingDist();

		}
		// if b is larger, assign all values of b
		if (aIsLarger == false) {
			this.size = b.getSize();
			this.w = b.getWorld();
			this.sensingDistance = b.getSensingDist();
		}
		this.maxHP = a.maxHP + b.maxHP;
		this.HP = this.maxHP;
		j = new JLabel(getImageIcon("herd.png"));

		entityCount++;
		this.uniqueID = entityCount;

		this.energy = (a.getEnergy() + b.getEnergy()) / 2;

		this.xPos = x;
		this.yPos = y;

		if (w.getDEBUG()) {
			System.out
					.println("Created a new instance of Herd using the Herd(herb a, herb b) constructor...");
		}
	}

	Herd(int x, int y, World w, int numOfHerbInside) {
		this.positionAltered = true;
	}

	Herd(int id, int x, int y, int num, double energy, int maxhp, int hp,
			int size, int smelldist, String name, World world) {
		super();

		this.uniqueID = id;

		this.positionAltered = true;

		this.xPos = x;
		this.yPos = y;
		this.energy = energy;
		this.maxHP = maxhp;
		this.HP = hp;
		this.size = size;
		this.sensingDistance = smelldist;
		this.name = name;
		this.w = world;

		for (int i = 0; i < num; i++) {

			this.herd.add(new Herbivore("herbivore", this.energy / num, this.HP
					/ num, this.maxHP / num));

		}
		j = new JLabel(getImageIcon("herd.png"));

		w.setEntityOnTheMap(xPos, yPos, this);

		if (w.getDEBUG()) {
			System.out.println("");
		}
	}

	/**
	 * This function is for adding an additional bug into the Herd. If the new
	 * bug is larger, it becomes the "leader", i.e. rewrites the values of the
	 * Herd.
	 * 
	 * @param h
	 *            - the new Herbivore being added to the Herd
	 */
	public void addHerbivore(Herbivore h) {
		this.maxHP = this.maxHP + h.getMaxHP();
		this.HP = this.HP + h.getHP();

		this.herd.add(h);

		// if the new Herbivore has a larger Size, assign new values
		if (h.size > this.size) {
			this.sensingDistance = h.getSensingDist();
			this.size = h.getSize();
		}

		h.remove();

		if (w.getDEBUG()) {
			System.out.println("");
		}
	}

	public void joinHerds(Herd h) {

		this.maxHP = this.maxHP + h.maxHP;
		this.HP = this.HP + h.HP;

		this.energy = this.energy + h.energy;

		if (this.size < h.size) {
			this.size = h.size;
		}
		consumedAHerd = true;

		if (h.consumedAHerd) {
			h.remove();
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
					this.goToOtherHerdOrHerbivore(Direction.North);
					hasMoved = true;
				} else if (s instanceof Herd && obstacleNorth == false) {
					this.goToOtherHerdOrHerbivore(Direction.South);
					hasMoved = true;
				} else if (e instanceof Herd && obstacleEast == false) {
					this.goToOtherHerdOrHerbivore(Direction.East);
					hasMoved = true;
				} else if (w instanceof Herd && obstacleWest == false) {
					this.goToOtherHerdOrHerbivore(Direction.West);
					hasMoved = true;
				} else if (n instanceof Herbivore && obstacleNorth == false) {
					this.goToOtherHerdOrHerbivore(Direction.North);
					hasMoved = true;
				} else if (s instanceof Herbivore && obstacleNorth == false) {
					this.goToOtherHerdOrHerbivore(Direction.South);
					hasMoved = true;
				} else if (e instanceof Herbivore && obstacleEast == false) {
					this.goToOtherHerdOrHerbivore(Direction.East);
					hasMoved = true;
				} else if (w instanceof Herbivore && obstacleWest == false) {
					this.goToOtherHerdOrHerbivore(Direction.West);
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
			System.out.println("Executed Herd's (id: " + this.uniqueID
					+ ") move() function...");
		}
	}

	public void addLifeFormToList(LifeForm l) {
		herd.add(l);
	}

	public void removeAllLifeFormsFromList() {
		herd.removeAll(herd);
	}

	public void removeALifeFormFromList(int index) {
		if (index < herd.size()) {
			herd.remove(index);
		} else {

			if (w.getDEBUG()) {

				System.out
						.println("Tried to remove a non-existent life form, out of bounds");
			}
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
			System.out.println("Executed Herd's runAway function...");
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
		double bite = this.size * 0.7;
		this.energy = this.energy + bite;
		f.setCalories(calories - bite);
		f.check();

		if (w.getDEBUG()) {
			System.out.println("Executed Herd's nibble function");
		}

	}

	public void goToFood(Direction d) {

		if (this.sense(d, 1) instanceof EmptyCell) {
			super.move(d);
		} else if (this.sense(d, 1) instanceof GrownRenewableFood) {
			this.nibble((Food) this.sense(d, 1));
		}
		if (w.getDEBUG()) {
			System.out.println("Executed Herd's goToFood fnction...");
		}

	}

	public void goToOtherHerdOrHerbivore(Direction d) {

		if (this.sense(d, 1) instanceof EmptyCell) {
			super.move(d);
		} else if (this.sense(d, 1) instanceof Herd) {
			joinHerds((Herd) this.sense(d, 1));
		} else if (this.sense(d, 1) instanceof Herbivore) {
			addHerbivore((Herbivore) this.sense(d, 1));
		}

	}

	public String saveString() {
		String content = null;
		// Herd saving layout:
		// number of herbivores, stats

		content = "herd" + "," + Integer.toString(uniqueID) + ","
				+ Integer.toString(xPos) + "," + Integer.toString(yPos) + ","
				+ Integer.toString(herd.size()) + "," + Double.toString(energy)
				+ "," + Integer.toString(maxHP) + "," + Integer.toString(HP)
				+ "," + Integer.toString(size) + ","
				+ Integer.toString(sensingDistance) + "," + this.name;

		return content;
	}

	@Override
	public void check() {
		this.checkIfDied();
		if (isDead == false) {
			// this.w.setEntityOnTheMap(xPos, yPos, this);
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
