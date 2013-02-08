package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class World implements Runnable {

	private static boolean DEBUG = true;
	private int maxXCoor, maxYCoor;
	private static Thread drawThread;
	private boolean stopDrawing;
	private Entity[][] theMap;
	private List<LifeForm> lifeFormList = new ArrayList<LifeForm>();
	private List<Food> foodList = new ArrayList<Food>();
	private List<Obstacle> obstacleList = new ArrayList<Obstacle>();
	private List<Entity> entityList = new ArrayList<Entity>();

	public enum Direction {
		North, South, East, West
	};

	World(int x, int y) {
		this.maxXCoor = x;
		this.maxYCoor = y;
		this.theMap = new Entity[this.maxXCoor][this.maxYCoor];

	}
	World(String s){
		
		String[] splittedStrings;
		String delimer1 = "\\$";
		String delimer2 = "\\&";
		String delimer3 = ",";

		splittedStrings = s.split(delimer1);
		String maxXandY = splittedStrings[0];

		String[] xAndY = maxXandY.split(delimer3);
		int maxXVal = Integer.parseInt(xAndY[0]);
		int maxYVal = Integer.parseInt(xAndY[1]);

		this.maxXCoor=maxXVal;
		this.maxYCoor=maxYVal;
		this.theMap = new Entity[maxXVal][maxYVal];

		

		String allEntities = splittedStrings[1];

		String[] separateEntities = allEntities.split(delimer2);
		this.fillTheMapWithEmpty();
		for (int i = 0; i < maxXVal; i++) {

			for (int j = 0; j < maxYVal; j++) {
				// breaking every object into separate attributes.
				String[] entityVariables = separateEntities[j * maxYVal + i]
						.split(delimer3);

				if (entityVariables[0].equals("bush")) {

					Bush b = new Bush(Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]),
							Double.parseDouble(entityVariables[4]), this);

					//world.setEntityOnTheMap(b.getXPos(), b.getYPos(), b);
					//this.setEntityOnTheMap(b.getXPos(), b.getYPos(), b);

				} else if (entityVariables[0].equals("carnivore")) {

					Carnivore c = new Carnivore(
							Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]),
							Double.parseDouble(entityVariables[4]),
							Integer.parseInt(entityVariables[5]),
							Integer.parseInt(entityVariables[6]),
							Integer.parseInt(entityVariables[7]),
							Integer.parseInt(entityVariables[8]),
							entityVariables[9], this);

					//world.setEntityOnTheMap(c.getXPos(), c.getYPos(), c);
					//this.setEntityOnTheMap(c.getXPos(), c.getYPos(), c);

				} else if (entityVariables[0].equals("empty")) {

					EmptyCell e = new EmptyCell(
							Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]), this);

					//world.setEntityOnTheMap(e.getXPos(), e.getYPos(), e);
					//this.setEntityOnTheMap(e.getXPos(), e.getYPos(), e);

				} else if (entityVariables[0].equals("grass")) {

					Grass g = new Grass(Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]),
							Double.parseDouble(entityVariables[4]), this, true);

					//world.setEntityOnTheMap(g.getXPos(), g.getYPos(), g);
					//this.setEntityOnTheMap(g.getXPos(), g.getYPos(), g);

				} else if (entityVariables[0].equals("herbivore")) {

					Herbivore h = new Herbivore(
							Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]),
							Double.parseDouble(entityVariables[4]),
							Integer.parseInt(entityVariables[5]),
							Integer.parseInt(entityVariables[6]),
							Integer.parseInt(entityVariables[7]),
							Integer.parseInt(entityVariables[8]),
							entityVariables[9], this);
					
					//this.setEntityOnTheMap(h.getXPos(), h.getYPos(), h);

				} else if (entityVariables[0].equals("herd")) {

					Herd h = new Herd(Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]),
							Integer.parseInt(entityVariables[4]),
							Double.parseDouble(entityVariables[5]),
							Integer.parseInt(entityVariables[6]),
							Integer.parseInt(entityVariables[7]),
							Integer.parseInt(entityVariables[8]),
							Integer.parseInt(entityVariables[9]),
							entityVariables[10], this);
					
					//this.setEntityOnTheMap(h.getXPos(), h.getYPos(), h);


				} else if (entityVariables[0].equals("meat")) {

					Meat m = new Meat(Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]),
							Double.parseDouble(entityVariables[4]),
							Integer.parseInt(entityVariables[5]),
							Integer.parseInt(entityVariables[6]), this);
					
					//this.setEntityOnTheMap(m.getXPos(), m.getYPos(), m);


				} else if (entityVariables[0].equals("obstacle")) {

					Obstacle o = new Obstacle(
							Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]), this);
					
					//this.setEntityOnTheMap(o.getXPos(), o.getYPos(), o);


				} else if (entityVariables[0].equals("poisoned")) {

					PoisonedFood p = new PoisonedFood(
							Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]),
							Double.parseDouble(entityVariables[4]),
							Integer.parseInt(entityVariables[5]),
							Boolean.parseBoolean(entityVariables[6]), this);
					
					//this.setEntityOnTheMap(p.getXPos(), p.getYPos(), p);


				} else if (entityVariables[0].equals("tree")) {

					Tree t = new Tree(Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]),
							Double.parseDouble(entityVariables[4]), this);
					
					//this.setEntityOnTheMap(t.getXPos(), t.getYPos(), t);


				}

			}

		}
	}

	public void setMaxX(int x) {
		this.maxXCoor = x;
	}

	public void setMaxY(int y) {
		this.maxYCoor = y;
	}

	public int getMaxX() {
		return this.maxXCoor;
	}

	public int getMaxY() {
		return this.maxYCoor;
	}

	public void setDEBUG(boolean d) {
		DEBUG = d;
	}

	public boolean getDEBUG() {
		return DEBUG;
	}

	/**
	 * This method returns the Object from the specified coordinates from the
	 * Map of the World
	 * 
	 * @param x
	 *            - horizontal position of the Map matrix
	 * @param y
	 *            - vertical position of the Map Matrix
	 * @return - returns the Object that is in that cell
	 */
	public Entity getEntityOnTheMap(int x, int y) {
		if (x >= 0 && y >= 0 && x < this.maxXCoor && y < this.maxYCoor) {
			return this.theMap[x][y];
		} else {
			JOptionPane.showMessageDialog(null,
					"Error! Tried to read a non-existent map coordinate");
			return null;
		}
	}

	/**
	 * This method sets the Object you want to the Map of the certain
	 * coordinates.
	 * 
	 * @param x
	 *            - the horizontal position of the Map Matrix
	 * @param y
	 *            - the vertical position of the Map Matrix
	 * @param o
	 *            - the Object that will be placed into the Map Matrix
	 */
	public void setEntityOnTheMap(int x, int y, Entity o) {
		if (x >= 0 && y >= 0 && x < this.maxXCoor && y < this.maxYCoor) {
			theMap[x][y] = null;
			this.theMap[x][y] = o;
			// theMap[x][y].setXPos(x);
			// theMap[x][y].setYPos(y);
		}
	}

	public void removeLifeFormFromList(LifeForm l) {

		if (lifeFormList.contains(l)) {
			this.lifeFormList.remove(l);
		}
	}

	public void addFoodToList(Food f) {
		if (foodList.contains(f) != true) {
			this.foodList.add(f);
		}
	}

	public void removeFoodFromList(Food f) {

		if (foodList.contains(f)) {
			this.foodList.remove(f);
		}
	}

	public void addObstacleToList(Obstacle o) {
		if (this.obstacleList.contains(o) != true) {
			this.obstacleList.add(o);
		}
	}

	public void removeObstacleFromList(Obstacle o) {

		if (obstacleList.contains(o)) {
			this.obstacleList.remove(o);
		}
	}

	/**
	 * Returns a Direction that is opposite to the one in input, e.g. North will
	 * return South, etc.
	 * 
	 * @param d
	 * @return
	 */

	public Direction invertDirection(Direction d) {

		if (d == Direction.East) {
			return Direction.West;
		} else if (d == Direction.North) {
			return Direction.South;
		} else if (d == Direction.West) {
			return Direction.East;
		} else if (d == Direction.South) {
			return Direction.North;
		}

		return d;

	}

	/**
	 * Returns a Direction 90 degrees clockwise from the Direction in input,
	 * e.g. North will return East, East will return South, etc
	 * 
	 * @param d
	 * @return
	 */
	public Direction getPerpendicularDirection1(Direction d) {

		if (d == Direction.East) {
			return Direction.South;
		} else if (d == Direction.North) {
			return Direction.East;
		} else if (d == Direction.West) {
			return Direction.North;
		} else if (d == Direction.South) {
			return Direction.West;
		}

		return d;

	}

	/**
	 * Returns a Direction 90 counter-clockwise from the Direction in input,
	 * e.g. North will return West
	 * 
	 * @param d
	 * @return
	 */
	public Direction getPerpendicularDirection2(Direction d) {

		if (d == Direction.East) {
			return Direction.North;
		} else if (d == Direction.North) {
			return Direction.West;
		} else if (d == Direction.West) {
			return Direction.South;
		} else if (d == Direction.South) {
			return Direction.East;
		}

		return d;

	}

	/**
	 * Returns the number of Food objects on the map
	 * 
	 * @return
	 */
	public int foodCount() {
		int count = 0;

		for (int i = 0; i < this.maxXCoor; i++) {
			for (int j = 0; j < this.maxYCoor; j++) {
				if (theMap[i][j] instanceof Food) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Returns the number of LifeForm objects on the map
	 * 
	 * @return
	 */
	public int lifeFormCount() {
		int count = 0;
		for (int i = 0; i < this.maxXCoor; i++) {
			for (int j = 0; j < this.maxYCoor; j++) {
				if (theMap[i][j] instanceof LifeForm) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Returns the number of Herbivore objects on the map
	 * 
	 * @return
	 */
	public int herbivoreCount() {
		int count = 0;
		for (int i = 0; i < this.maxXCoor; i++) {
			for (int j = 0; j < this.maxYCoor; j++) {
				if (theMap[i][j] instanceof Herbivore) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Returns the number of Carnivore objects on the map
	 * 
	 * @return
	 */
	public int carnivoreCount() {
		int count = 0;
		for (int i = 0; i < this.maxXCoor; i++) {
			for (int j = 0; j < this.maxYCoor; j++) {
				if (theMap[i][j] instanceof Carnivore) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Returns the number of Herd objects on the map
	 * 
	 * @return
	 */
	public int herdCount() {
		int count = 0;
		for (int i = 0; i < this.maxXCoor; i++) {
			for (int j = 0; j < this.maxYCoor; j++) {
				if (theMap[i][j] instanceof Herd) {
					count++;
				}
			}
		}
		return count;
	}

	public void addEntity(Entity e) {
		if (this.entityList.contains(e) == false) {
			entityList.add(e);
		} else {
			System.out
					.println("Tried to add an entity to the World's Entity list that is already in the list.");
		}
	}

	public void removeEntity(Entity e) {
		if (this.entityList.contains(e)) {
			this.entityList.remove(e);
		} else {
			if (DEBUG) {
				System.out
						.println("Tried to remove an Entity from a list that was not in the list.");
			}
		}
	}

	public void loadAConfigFile(String s) {
		String[] splittedStrings;
		String delimer1 = "\\$";
		String delimer2 = "\\&";
		String delimer3 = ",";

		splittedStrings = s.split(delimer1);
		String maxXandY = splittedStrings[0];

		String[] xAndY = maxXandY.split(delimer3);
		int maxXVal = Integer.parseInt(xAndY[0]);
		int maxYVal = Integer.parseInt(xAndY[1]);

		World world = new World(maxXVal, maxYVal);

		

		String allEntities = splittedStrings[1];

		String[] separateEntities = allEntities.split(delimer2);
		world.fillTheMapWithEmpty();
		for (int i = 0; i < maxXVal; i++) {

			for (int j = 0; j < maxYVal; j++) {
				// breaking every object into separate attributes.
				String[] entityVariables = separateEntities[j * maxYVal + i]
						.split(delimer3);

				if (entityVariables[0] == "bush") {

					Bush b = new Bush(Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]),
							Double.parseDouble(entityVariables[4]), world);

					//world.setEntityOnTheMap(b.getXPos(), b.getYPos(), b);

				} else if (entityVariables[0] == "carnivore") {

					Carnivore c = new Carnivore(
							Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]),
							Double.parseDouble(entityVariables[4]),
							Integer.parseInt(entityVariables[5]),
							Integer.parseInt(entityVariables[6]),
							Integer.parseInt(entityVariables[7]),
							Integer.parseInt(entityVariables[8]),
							entityVariables[9], world);

					//world.setEntityOnTheMap(c.getXPos(), c.getYPos(), c);

				} else if (entityVariables[0] == "empty") {

					EmptyCell e = new EmptyCell(
							Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]), world);

					//world.setEntityOnTheMap(e.getXPos(), e.getYPos(), e);

				} else if (entityVariables[0] == "grass") {

					Grass g = new Grass(Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]),
							Double.parseDouble(entityVariables[4]), world, true);

					//world.setEntityOnTheMap(g.getXPos(), g.getYPos(), g);

				} else if (entityVariables[0] == "herbivore") {

					Herbivore h = new Herbivore(
							Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]),
							Double.parseDouble(entityVariables[4]),
							Integer.parseInt(entityVariables[5]),
							Integer.parseInt(entityVariables[6]),
							Integer.parseInt(entityVariables[7]),
							Integer.parseInt(entityVariables[8]),
							entityVariables[9], world);

					world.entityList.add(h);
					world.lifeFormList.add(h);

				} else if (entityVariables[0] == "herd") {

					Herd h = new Herd(Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]),
							Integer.parseInt(entityVariables[4]),
							Double.parseDouble(entityVariables[5]),
							Integer.parseInt(entityVariables[6]),
							Integer.parseInt(entityVariables[7]),
							Integer.parseInt(entityVariables[8]),
							Integer.parseInt(entityVariables[9]),
							entityVariables[10], world);

					world.entityList.add(h);
					world.lifeFormList.add(h);

				} else if (entityVariables[0] == "meat") {

					Meat m = new Meat(Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]),
							Double.parseDouble(entityVariables[4]),
							Integer.parseInt(entityVariables[5]),
							Integer.parseInt(entityVariables[6]), world);

					world.entityList.add(m);
					world.foodList.add(m);

				} else if (entityVariables[0] == "obstacle") {

					Obstacle o = new Obstacle(
							Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]), world);

					world.entityList.add(o);
					world.obstacleList.add(o);

				} else if (entityVariables[0] == "poisoned") {

					PoisonedFood p = new PoisonedFood(
							Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]),
							Double.parseDouble(entityVariables[4]),
							Integer.parseInt(entityVariables[5]),
							Boolean.parseBoolean(entityVariables[6]), world);

					world.entityList.add(p);
					world.foodList.add(p);

				} else if (entityVariables[0] == "tree") {

					Tree t = new Tree(Integer.parseInt(entityVariables[1]),
							Integer.parseInt(entityVariables[2]),
							Integer.parseInt(entityVariables[3]),
							Double.parseDouble(entityVariables[4]), world);

					world.entityList.add(t);
					world.foodList.add(t);

				}

			}

		}
		

	}

	/**
	 * Sets every Object of theMap[][] to null.
	 */
	public void resetMap() {
		for (int i = 0; i < this.maxXCoor; i++) {
			for (int j = 0; j < this.maxYCoor; j++) {
				this.theMap[i][j] = null;
			}
		}
	}

	public void fillTheMapWithEmpty() {
		for (int i = 0; i < this.maxXCoor; i++) {
			for (int j = 0; j < this.maxYCoor; j++) {
				EmptyCell e = new EmptyCell(i, j, this);
				this.theMap[i][j] = e;
				this.entityList.add(e);

			}
		}
	}

	public void placeObstacles() {
		for (int i = 0; i < this.obstacleList.size(); i++) {
			this.theMap[obstacleList.get(i).getXPos()][obstacleList.get(i)
					.getYPos()] = obstacleList.get(i);
		}
	}

	public void placeFoodObjects() {
		for (int i = 0; i < this.foodList.size(); i++) {
			this.theMap[foodList.get(i).getXPos()][foodList.get(i).getYPos()] = foodList
					.get(i);
		}
	}

	public void placeLifeForms() {
		for (int i = 0; i < this.lifeFormList.size(); i++) {
			this.theMap[lifeFormList.get(i).getXPos()][lifeFormList.get(i)
					.getYPos()] = lifeFormList.get(i);
		}
	}

	public void putEntitiesOnTheMap() {

		for (int i = 0; i < this.entityList.size(); i++) {
			this.theMap[entityList.get(i).getXPos()][entityList.get(i)
					.getYPos()] = entityList.get(i);
		}

	}

	public void stopAllEntities() {

		for (int i = 0; i < this.maxXCoor; i++) {
			for (int j = 0; j < this.maxYCoor; j++) {
				
					theMap[i][j].stop();
				
			}
		}

	}

	public void startAllEntities() {

		for (int i = 0; i < this.maxXCoor; i++) {
			for (int j = 0; j < this.maxYCoor; j++) {
				while (!theMap[i][j].activity.isAlive()) {
					theMap[i][j].start();
				}
			}
		}

	}
	
	public void resumeAllEntities(){
		for (int i = 0; i < this.maxXCoor; i++) {
			for (int j = 0; j < this.maxYCoor; j++) {
				
					theMap[i][j].resume();
				
			}
		}
	}

	public void runWorld() {

		// a check to make sure there are no null objects
		// replaces any nulls with a new instance of EmptyCell
		for (int i = 0; i < this.maxXCoor; i++) {

			for (int j = 0; j < this.maxYCoor; j++) {

				if (this.theMap[i][j] == null) {
					this.theMap[i][j] = new EmptyCell(i, j, this);
				}

			}

		}

		for (int i = 0; i < this.maxXCoor; i++) {

			for (int j = 0; j < this.maxYCoor; j++) {

				if (this.theMap[i][j] instanceof EmptyCell == false
						&& this.theMap[i][j] instanceof Obstacle == false) {
					this.theMap[i][j].stop();
					this.theMap[i][j].start();
				}
			}

		}

	}
	
	public void resumeWorld(){
		for (int i = 0; i < this.maxXCoor; i++) {

			for (int j = 0; j < this.maxYCoor; j++) {

				if (this.theMap[i][j] instanceof EmptyCell == false
						&& this.theMap[i][j] instanceof Obstacle == false) {
					this.theMap[i][j].resume();
				}
			}

		}
	}

	/**
	 * This function is designed for quick testing. To be used by the
	 * developer(s)
	 */
	public World createTestWorld() {
		World w = new World(10, 10);
		w.fillTheMapWithEmpty();
		w.theMap[6][6] = new Herbivore(6, 6, w, false);
		// w.theMap[3][6] = new Obstacle(2, 6, w);
		w.theMap[2][6] = new Herbivore(2, 6, w, false);
		return w;
	}

	public World createARandomWorld(int x, int y) {
		World w = new World(x, y);
		w.fillTheMapWithEmpty();
		
		
		JTextField carnNum = new JTextField(2);
		JTextField herbNum = new JTextField(2);
		JTextField obstNum = new JTextField(2);
		JTextField foodNum = new JTextField(2);
		//Panel to hold everything
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
		panel.add(new JLabel("How many carnivores do you wish to add? "));
		panel.add(carnNum);
		
		panel.add(new JLabel("How many herbivores do you wish to add? "));
		panel.add(herbNum);
		
		
		panel.add(new JLabel("How many obstacles do you wish to add? "));
		panel.add(obstNum);
		
		
		panel.add(new JLabel("How many food items do you wish to add? "));
		panel.add(foodNum);
		
		int response = JOptionPane.showConfirmDialog(null, panel,"Please fill in",JOptionPane.CANCEL_OPTION);
		
		if (response==JOptionPane.CANCEL_OPTION){
			System.exit(0);
		}
		
		int carnivores = Integer.parseInt(carnNum.getText());

		int herb = Integer.parseInt(herbNum.getText());

		int obst = Integer.parseInt(obstNum.getText());

		int food = Integer.parseInt(foodNum.getText());

		w.putSomeCarnivores(carnivores);
		w.putSomeHerbivores(herb);
		w.putSomeObstacles(obst);
		w.putSomeRenewableFood(food);

		return w;
	}

	public void putSomeCarnivores(int c) {

		Random rand = new Random();

		for (int i = 0; i < c; i++) {
			boolean placed = false;

			while (!placed) {
				int x = rand.nextInt(this.getMaxX());
				int y = rand.nextInt(this.getMaxY());

				if (this.theMap[x][y] instanceof EmptyCell) {
					this.setEntityOnTheMap(x, y, new Carnivore(x, y, this,
							false));
					placed = true;
				}
			}

		}

	}

	public void putSomeRenewableFood(int f) {

		Random rand = new Random();

		for (int i = 0; i < f; i++) {
			boolean placed = false;

			while (!placed) {
				int x = rand.nextInt(this.getMaxX());
				int y = rand.nextInt(this.getMaxY());

				if (this.theMap[x][y] instanceof EmptyCell) {

					int decision = rand.nextInt(3);
					if (decision == 0) {
						this.setEntityOnTheMap(x, y,
								new Tree(x, y, this, false));
						placed = true;
					} else if (decision == 1) {
						this.setEntityOnTheMap(x, y,
								new Bush(x, y, this, false));
						placed = true;
					} else if (decision == 2) {
						this.setEntityOnTheMap(x, y, new Grass(x, y, this,
								false));
						placed = true;
					}

				}
			}

		}

	}

	public void putSomeHerbivores(int c) {

		Random rand = new Random();

		for (int i = 0; i < c; i++) {
			boolean placed = false;

			while (!placed) {
				int x = rand.nextInt(this.getMaxX());
				int y = rand.nextInt(this.getMaxY());

				if (this.theMap[x][y] instanceof EmptyCell) {
					this.setEntityOnTheMap(x, y, new Herbivore(x, y, this,
							false));
					placed = true;
				}
			}

		}

	}

	public void putSomeObstacles(int c) {

		Random rand = new Random();

		for (int i = 0; i < c; i++) {
			boolean placed = false;

			while (!placed) {
				int x = rand.nextInt(this.getMaxX());
				int y = rand.nextInt(this.getMaxY());

				if (this.theMap[x][y] instanceof EmptyCell) {
					this.setEntityOnTheMap(x, y, new Obstacle(x, y, this));
					placed = true;
				}
			}

		}

	}

	/**
	 * Drawing a quick test map, no GUI
	 */
	public void drawTestMap() {

		for (int i = 0; i < this.maxYCoor; i++) {

			for (int j = 0; j < this.maxXCoor; j++) {
				if (this.theMap[i][j] instanceof Carnivore) {
					System.out.print("c ");
				} else if (this.theMap[i][j] instanceof Herbivore) {
					System.out.print("h ");
				} else if (this.theMap[i][j] instanceof Herd) {
					System.out.print("H ");
				} else if (this.theMap[i][j] instanceof Obstacle) {
					System.out.print("o ");
				} else if (this.theMap[i][j] instanceof Tree) {
					System.out.print("t ");
				} else if (this.theMap[i][j] instanceof Bush) {
					System.out.print("b ");
				} else if (this.theMap[i][j] instanceof Grass) {
					System.out.print("g ");
				} else if (this.theMap[i][j] instanceof PoisonedFood) {
					System.out.print("p ");
				} else if (this.theMap[i][j] instanceof EmptyCell) {
					System.out.print("_ ");
				} else if (this.theMap[i][j] instanceof Meat) {
					System.out.print("m ");
				} else if (this.theMap[i][j] instanceof EatenRenewableFood) {
					System.out.print("E ");
				} else {
					System.out.print("n ");
				}

			}
			System.out.println("");
		}
		System.out
				.println("_______________________________________________________");
	}

	public void run() {

		while (!stopDrawing) {
			try {
				drawTestMap();
				Thread.sleep(1000); // go to sleep for some time
			} catch (InterruptedException ex) {
				// wake up!
			}
		}

	}

	public void start() {
		stopDrawing = false;
		if (!drawThread.isAlive()) {
			drawThread.start();
		}
	}

	public void stop() {
		stopDrawing = true;
	}

	public static void main(String[] args) {

		// TODO debug this!
		// objects don't move
		World w = new World(30, 30);
		w.fillTheMapWithEmpty();
		w = w.createARandomWorld(30, 30);
		w.runWorld();
		drawThread = new Thread(w);

		w.start();
		System.out.println("");

	}

}
