package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public abstract class Entity implements Runnable {

	protected JLabel j;
	protected Thread activity;
	protected static long delay = 16;
	protected boolean runTerminated;
	protected long speed;
	protected static long entitySpeed = 1;

	protected static float foodCalorieMultiplier = 1;
	protected static float energyGainMultiplier = 1;
	protected static float energySpendMultiplier = 1;
	protected static int entityCount = 0;
	protected int xPos;
	protected int yPos;
	protected int uniqueID;
	protected boolean positionAltered;
	protected final static String RELPATHICONS = "icons" + File.separator;
	protected World w;

	Entity() {
		activity = new Thread(this);

	}

	public void start() {
		runTerminated = false;
		if (!activity.isAlive()) {
			activity.start();
		}
		// System.out.println("Started id: " + this.uniqueID + "'s Thread...");

	}

	public void stop() {
		runTerminated = true;
	}

	public void resume() {
		runTerminated = false;
	}

	public int getXPos() {
		return this.xPos;
	}

	public void setXPos(int x) {
		this.xPos = x;
	}

	public int getYPos() {
		return this.yPos;
	}

	public void setYPos(int y) {
		this.yPos = y;
	}

	public boolean getAlteredState() {
		return this.positionAltered;
	}

	public void setAlteredState(boolean b) {
		this.positionAltered = b;
	}

	public int getUniqueId() {
		return this.uniqueID;
	}

	public void setUniqueId(int id) {
		this.uniqueID = id;
	}

	public JLabel getJLabel() {
		return this.j;
	}

	public void setJLabel(JLabel l) {
		this.j = l;
	}

	public void setWorld(World w) {
		this.w = w;
	}

	public World getWorld() {
		return this.w;
	}

	public abstract void run();

	public abstract void check();

	/** Returns Entity's String with it's attributes, in order to be saved in a file
	 * @return the String containing entity's info
	 */
	public String saveString() {
		String s = null;
		return s;
	}

	/**
	 * This function stops the Thread that was executing this Entity, and puts a
	 * new EmptyCell in it's location on the map
	 */
	public void remove() {
		this.stop();
		new EmptyCell(this.getXPos(), this.getYPos(), w);
	}

	/**
	 * @param iconFilename
	 * @return
	 */
	protected ImageIcon getImageIcon(String iconFilename) {
		ImageIcon theIcon;
		File theImage = new File(RELPATHICONS + iconFilename);
		if (theImage.isFile()) {
			theIcon = new ImageIcon(theImage.getAbsolutePath());

		} else {
			// show an error message and quit
			theIcon = null;
			JOptionPane.showMessageDialog(null, "Error - file not found: "
					+ iconFilename);
			System.exit(-1);
		}

		return (theIcon);
	}

}
