package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

import java.util.Random;

import uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator.World.Direction;

public abstract class LifeForm extends Entity implements LifeFormInterface {

	protected double energy;
	protected int maxHP;
	protected int HP;
	protected int size;
	protected int sensingDistance;
	protected String name;
	protected static int lifeFormCount;
	protected boolean isDead = false;

	public double getEnergy() {
		return this.energy;
	}

	public void setEnergy(double e) {
		this.energy = e;
	}

	public int getMaxHP() {
		return this.maxHP;
	}

	public void setMaxHP(int h) {
		this.maxHP = h;
	}

	public int getHP() {
		return this.HP;
	}

	public void setHP(int h) {
		this.HP = h;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int s) {
		this.size = s;
	}

	public World getWorld() {
		return this.w;
	}

	public void setWorld(World w) {
		this.w = w;
	}

	public int getSensingDist() {
		return this.sensingDistance;
	}

	public void setSensingDist(int a) {
		this.sensingDistance = a;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String s) {
		this.name = s;
	}

	// methods
	/**
	 * This function physically places the Life Form into the adjacent cell in
	 * the given direction. <br/>
	 * Be careful! Does not perform any checks whatsoever, i.e. ignores any
	 * other objects and entities. Thus prone to 'Null pointer exceptions' if
	 * used carelessly
	 * 
	 * @param d
	 *            - Direction, one of the values of the enum
	 */
	public void move(Direction d) {

		switch (d) {
		case North: {
			this.yPos = this.yPos - 1;
			w.setEntityOnTheMap(this.xPos, this.yPos, this);
			this.positionAltered = true;
			w.setEntityOnTheMap(this.xPos, this.yPos + 1, new EmptyCell(
					this.xPos, this.yPos + 1, w));
			this.energy = this.energy - 1 * energySpendMultiplier;

		}
			break;
		case South: {
			this.yPos = this.yPos + 1;
			w.setEntityOnTheMap(this.xPos, this.yPos, this);
			this.positionAltered = true;
			w.setEntityOnTheMap(this.xPos, this.yPos - 1, new EmptyCell(
					this.xPos, this.yPos - 1, w));
			this.energy = this.energy - 1 * energySpendMultiplier;

		}
			break;
		case East: {
			this.xPos = this.xPos - 1;
			w.setEntityOnTheMap(this.xPos, this.yPos, this);
			this.positionAltered = true;
			w.setEntityOnTheMap(this.xPos + 1, this.yPos, new EmptyCell(
					this.xPos + 1, this.yPos, w));
			this.energy = this.energy - 1 * energySpendMultiplier;

		}
			break;
		case West: {
			this.xPos = this.xPos + 1;
			w.setEntityOnTheMap(this.xPos, this.yPos, this);
			this.positionAltered = true;
			w.setEntityOnTheMap(this.xPos - 1, this.yPos, new EmptyCell(
					this.xPos - 1, this.yPos, w));
			this.energy = this.energy - 1 * energySpendMultiplier;

		}
			break;
		default: {

		}
			break;

		}

	}

	/**
	 * Returns a copy of the Object (Entity) that is currently in the cell that
	 * the bug smells. Performs a check to make sure there are no out of bounds
	 * errors, i.e. does NOT check if the requested cell is outside the
	 * boundaries of the map matrix
	 * 
	 * @param d
	 *            - Direction (enum)
	 * @param dist
	 *            - how many cells away from the Life Form
	 * @return - the Object that populates that cell. If null is returned, it's
	 *         either out of bounds or couldn't smell
	 */
	public Entity sense(Direction d, int dist) {

		switch (d) {
		case North: {
			if (this.sensingDistance >= dist) {
				if (this.xPos >= 0 && this.yPos - dist >= 0
						&& this.xPos < w.getMaxX()
						&& this.yPos - dist < w.getMaxY()) {
					return w.getEntityOnTheMap(xPos, yPos - dist);
				}
			}
		}
			break;
		case South: {
			if (this.sensingDistance >= dist) {
				if (this.xPos >= 0 && this.yPos + dist >= 0
						&& this.xPos < w.getMaxX()
						&& this.yPos + dist < w.getMaxY()) {
					return w.getEntityOnTheMap(xPos, yPos + dist);
				}
			}

		}
			break;
		case East: {
			if (this.sensingDistance >= dist) {
				if (this.xPos - dist >= 0 && this.yPos >= 0
						&& this.xPos - dist < w.getMaxX()
						&& this.yPos < w.getMaxY()) {
					return w.getEntityOnTheMap(xPos - dist, yPos);
				}
			}
		}
			break;
		case West: {
			if (this.sensingDistance >= dist) {
				if (this.xPos + dist >= 0 && this.yPos >= 0
						&& this.xPos + dist < w.getMaxX()
						&& this.yPos < w.getMaxY()) {
					return w.getEntityOnTheMap(xPos + dist, yPos);
				}
			}
		}
			break;
		default: {
			return null;
		}
		}
		return null;
	}

	/**
	 * Checks if the Life Form is dead (HP less than or equal to zero).<br>
	 * If it died, deletes the object and creates a Meat object in it's place
	 * 
	 */
	public void checkIfDied() {

		if (this.HP <= 0) {
			isDead = true;
		}

		if (this.energy <= 0) {
			isDead = true;
		}
		if (isDead == true) {
			this.die();

		}

	}

	/**
	 * Removes the Life Form from World's lists.
	 * 
	 */
	public void die() {
		this.stop();
		w.removeEntity(this);
		double d = this.maxHP;
		Meat m = new Meat(this.xPos, this.yPos, this.w, true, d);
		w.setEntityOnTheMap(this.xPos, this.yPos, m);

	}

	public void run() {
		while (!runTerminated) {
			try {
				this.check();
				this.move();
				Thread.sleep(delay); // go to sleep for some time
			} catch (InterruptedException ex) {
				// wake up!
			}
		}
	}

	/**
	 * A function to determine where to move if there are no other incentives to
	 * go are present. I.e. last resort
	 * 
	 * @return Returns a Direction at random. Returns null if cannot move (due
	 *         to obstacles, or lack of empty cells to be more precise)
	 */
	public Direction getRandomDirectionToMove() {

		Random rand = new Random();
		Direction directionToGoIn = null;
		boolean canGo = false;
		int i = 0;
		while (canGo == false && i != 10) {
			int decision = rand.nextInt(4);
			if (decision == 1) {
				if (this.sense(Direction.North, 1) instanceof EmptyCell) {
					canGo = true;
					directionToGoIn = Direction.North;
				} else if (this.sense(Direction.South, 1) instanceof EmptyCell) {
					canGo = true;
					directionToGoIn = Direction.South;
				} else if (this.sense(Direction.East, 1) instanceof EmptyCell) {
					canGo = true;
					directionToGoIn = Direction.East;
				} else if (this.sense((Direction.West), 1) instanceof EmptyCell) {
					canGo = true;
					directionToGoIn = Direction.West;
				}
			}
			if (decision == 2) {
				if (this.sense(Direction.South, 1) instanceof EmptyCell) {
					canGo = true;
					directionToGoIn = Direction.South;
				} else if (this.sense(Direction.North, 1) instanceof EmptyCell) {
					canGo = true;
					directionToGoIn = Direction.North;
				} else if (this.sense(Direction.East, 1) instanceof EmptyCell) {
					canGo = true;
					directionToGoIn = Direction.East;
				} else if (this.sense((Direction.West), 1) instanceof EmptyCell) {
					canGo = true;
					directionToGoIn = Direction.West;
				}
			}
			if (decision == 3) {
				if (this.sense(Direction.East, 1) instanceof EmptyCell) {
					canGo = true;
					directionToGoIn = Direction.East;
				} else if (this.sense(Direction.South, 1) instanceof EmptyCell) {
					canGo = true;
					directionToGoIn = Direction.South;
				} else if (this.sense(Direction.North, 1) instanceof EmptyCell) {
					canGo = true;
					directionToGoIn = Direction.North;
				} else if (this.sense((Direction.West), 1) instanceof EmptyCell) {
					canGo = true;
					directionToGoIn = Direction.West;
				}
			}
			if (decision == 4) {
				if (this.sense((Direction.West), 1) instanceof EmptyCell) {
					canGo = true;
					directionToGoIn = Direction.West;
				} else if (this.sense(Direction.South, 1) instanceof EmptyCell) {
					canGo = true;
					directionToGoIn = Direction.South;
				} else if (this.sense(Direction.North, 1) instanceof EmptyCell) {
					canGo = true;
					directionToGoIn = Direction.North;
				} else if (this.sense(Direction.East, 1) instanceof EmptyCell) {
					canGo = true;
					directionToGoIn = Direction.East;
				}
			}
			i++;
		}
		return directionToGoIn;
	}
}