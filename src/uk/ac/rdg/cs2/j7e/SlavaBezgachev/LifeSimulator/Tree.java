package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

import java.util.Random;

import javax.swing.JLabel;

public class Tree extends GrownRenewableFood {

	//random constructor
	
	Tree(){
		super();
		
		Random rand = new Random();
		
		this.positionAltered=true;
		
		this.calories=rand.nextInt(5)+10;
		this.uniqueID=entityCount;
		j = new JLabel(getImageIcon("treeFull.png"));
		
		entityCount++;
		this.uniqueID=entityCount;
		
		w.setEntityOnTheMap(xPos, yPos, this);
	}
	
	
	Tree(int x, int y, World world, boolean startItself){
		super();
		
		Random rand = new Random();
		this.calories=rand.nextInt(5)+10;
		this.uniqueID=entityCount;
		j = new JLabel(getImageIcon("treeFull.png"));
		
		this.positionAltered=true;
		
		this.xPos=x;
		this.yPos=y;
		this.w=world;
		
		entityCount++;
		this.uniqueID=entityCount;
		
		w.setEntityOnTheMap(xPos, yPos, this);
		
		if (startItself) {
			activity.start();
		}
		
		if (w.getDEBUG()) {
			System.out.println("Created a new instance of Tree using the main constructor...");
		}
	}
	
	Tree(int id,int x, int y, double cal, World world){
		super();
		
		this.uniqueID=id;
		this.calories=cal;
		
		this.positionAltered=true;
		
		this.xPos=x;
		this.yPos=y;
		this.positionAltered=true;
		j = new JLabel(getImageIcon("treeFull.png"));
		this.w=world;
		
		w.setEntityOnTheMap(xPos, yPos, this);
		
		if (w.getDEBUG()) {
			System.out.println("Created a new instance of Tree using the file-read constructor...");
		}
	}
	
	public String saveString() {
		String content = null;

		content = "tree" + "," + Integer.toString(uniqueID) + ","
				+ Integer.toString(xPos) + "," + Integer.toString(yPos) + ","
				+ Double.toString(calories);

		return content;
	}
}
