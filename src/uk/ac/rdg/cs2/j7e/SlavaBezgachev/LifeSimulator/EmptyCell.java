package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

import javax.swing.JLabel;

public class EmptyCell extends Entity {

	EmptyCell(int x, int y, World world) {

		this.xPos = x;
		this.yPos = y;
		
		this.positionAltered=true;
		
		this.w = world;
		j = new JLabel(getImageIcon("null.png"));
		entityCount++;
		this.uniqueID = entityCount;

		w.setEntityOnTheMap(xPos, yPos, this);

		if (w.getDEBUG()) {
			System.out
					.println("Created a new instance of EmptyCell using the main constructor...");
		}
	}

	EmptyCell(int id, int x, int y, World world) {
		this.uniqueID = id;
		this.xPos = x;
		
		this.positionAltered=true;
		
		this.yPos = y;
		j = new JLabel(getImageIcon("null.png"));
		this.w = world;

		w.setEntityOnTheMap(xPos, yPos, this);

		if (w.getDEBUG()) {
			System.out
					.println("Created a new instance of EmptyCell using the file-read constructor...");
		}
	}

	public String saveString() {
		String content = null;
		// type id, unique id, x position, y position
		content = "empty" + "," + Integer.toString(uniqueID) + ","
				+ Integer.toString(xPos) + "," + Integer.toString(yPos);

		return content;
	}

	@Override
	public void check() {
		
		if (w.getDEBUG()) {
		System.out.println("");
		}
	}

	@Override
	public void run() {
		System.out.println("");
	}

}
