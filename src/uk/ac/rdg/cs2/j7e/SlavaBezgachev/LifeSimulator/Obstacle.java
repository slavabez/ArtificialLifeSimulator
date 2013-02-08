package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

import javax.swing.JLabel;

public class Obstacle extends Entity {
	

	
	Obstacle(int x, int y, World world){
		
		this.xPos=x;
		this.yPos=y;
		this.w=world;
		j = new JLabel(getImageIcon("obstacle.png"));
		
		entityCount++;
		this.uniqueID=entityCount;
		
		this.positionAltered=true;
		
		w.setEntityOnTheMap(xPos, yPos, this);
		
		
		if (w.getDEBUG()) {
		System.out.println("Created a new instance of Obstacle using the main constructor...");
		}
	}
	
	Obstacle (int id, int x, int y, World world){
		
		this.uniqueID=id;
		this.xPos=x;
		
		this.positionAltered=true;
		
		this.yPos=y;
		j = new JLabel(getImageIcon("obstacle.png"));
		this.w=world;
		
		w.setEntityOnTheMap(xPos, yPos, this);
		
		
		if (w.getDEBUG()) {
		System.out.println("Created a new instance of Meat using the file-read constructor...");
		}
		
		
	}
	
	
	public String saveString() {
		String content = null;
		// type id, unique id, x position, y position
		content = "obstacle" + "," + Integer.toString(uniqueID) + ","
				+ Integer.toString(xPos) + "," + Integer.toString(yPos);

		return content;
	}

	@Override
	public void check() {
		
		if (w.getDEBUG()) {
		System.out.println("Executed this Obstacle's check function... (which it shouldn't)...");
		}
	}

	@Override
	public void run() {
		check();
		
	}
}
