package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class MainMenu contains the GUI for the application. GUI is built from a
 * collection of Swing elements, such as JPanel, JFrame, etc. <br>
 * Main starts the GUI using an Event Dispatch thread, making use of the JVM
 * multi-threading capabilities
 * 
 * @author Slava Bezgachev
 * 
 */
public class MainPanel extends JPanel implements ActionListener, Runnable {

	/**
	 * 
	 */

	private final static String RELPATHICONS = "icons" + File.separator; // relative
																			// path
																			// to
																			// icons
																			// folder
	private static final long serialVersionUID = 1L;
	private Thread activity;
	private boolean runTerminated;
	private int delay = 16;
	private JPanel gridMapPanel;// the panel containing the map
	private JPanel mapCells[][];// the grid that will represent the map
	private Timer timer;// Time, will be used for executing simulation
	private int speed;// amount of simulations/second
	// Declaring two bars. One for the Top menu (file, edit, view, etc), and one
	// for the bottom bar (simulation control buttons, speed spinner)
	private JMenuBar topMenuBar, simulationBar;
	// buttons to go to the simulation control bar
	private JButton jb_startMap, jb_startWorld,
			jb_stopWorld, jb_stopMap,jb_resumeWorld;
	// submenus to go to the top manu bar
	private JMenu subMenuFile, subMenuView, subMenuEdit, subMenuHelp;
	// Menu items to go inside those sub menus
	private JMenuItem itemFile1, itemFile2, itemFile3, itemFile4, itemFile5,
			itemView1, itemView2, itemView3, itemView4, itemEdit1, itemEdit2,
			itemEdit3, itemHelp1, itemHelp2;
	// Spinner that will regulate the speed of the simulation
	private JSpinner jspin_speed;
	// labels that will be used for representing items on the map
	private World myWorld;
	private ConfigWriterReader fileOp = new ConfigWriterReader();

	/**
	 * <h1>Constructor</h1>
	 * <p>
	 * Creates a new Mainmenu (gui JPanel) and puts a World into it. Extends the
	 * JPanel element. <br>
	 * Consists of Jpanels, JButtons, JToolbars, etc.
	 * </p>
	 * 
	 * @param w
	 *            - parameter of class AWorld. Uses the map from it (2D char
	 *            array) to populate the GridLayout
	 */
	MainPanel() {
		this.activity = new Thread(this);

		// creating a new option pane with some user input
		// to determine what size of the map to create
		JFrame aFrame = new JFrame();

		String[] choices = { "Create a new World", "Load a World",
				"Load the last World", "Quit" };

		int response = JOptionPane.showOptionDialog(null,
				"Hello and Welcome to ALS! What would you like to do?",
				"Please make a selection", JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, choices,
				"Nothing, just passing by...");

		switch (response) {
		case 0: {
			// create a new world
			//textfields
			JTextField xField = new JTextField(2);
			JTextField yField = new JTextField(2);

			int x, y;
			JPanel question = new JPanel();
			question.setLayout(new BoxLayout(question, BoxLayout.PAGE_AXIS));
			question.add(new JLabel("Height: "));
			question.add(xField);
			question.add(Box.createHorizontalStrut(15)); // just a spacer
			question.add(new JLabel("Width: "));
			question.add(yField);
			question.add(new JLabel("Recommended sizes:"));
			question.add(new JLabel("800x600 - 15 by 15"));
			question.add(new JLabel("1366x768 - 20 by 30"));
			question.add(new JLabel("1600x900 - 25 by 35"));
			question.add(new JLabel("1920x1080 - 30 by 40"));
			
			
			int result = JOptionPane.showConfirmDialog(null, question,
					"Please enter integer values for maps' height and width",
					JOptionPane.OK_CANCEL_OPTION);
			
			if (result==JOptionPane.CANCEL_OPTION){
				System.exit(0);
			}
			
			x = Integer.parseInt(xField.getText());
			y = Integer.parseInt(yField.getText());

			World w = new World(x, y);

			myWorld = w.createARandomWorld(x, y);

			// GridLayout(x,y)
			gridMapPanel = new JPanel(new GridLayout(x, y, 0, 0));
			mapCells = new JPanel[x][y];

			for (int i = 0; i < x; i++) {

				for (int j = 0; j < y; j++) {

					mapCells[i][j] = new JPanel();
					JLabel label = new JLabel((getImageIcon("null.png")));
					// label.setSize(20, 20);

					// mapCells[i][j].setPreferredSize(new Dimension(25,25));
					mapCells[i][j].setBackground(new Color(185, 122, 87));
					mapCells[i][j].add(label);

					// mapCells[i][j].prepareImage(getImageIcon("null.png"),
					// mapCells[i][j].getHeight(), mapCells[i][j].getWidth(),
					// observer)

					// mapCells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));

					gridMapPanel.add(mapCells[i][j]);
				}

			}
		}

			break;
		case 1: {
			// load a user selected World

			try {
				myWorld = new World(fileOp.loadConfigFile());
			} catch (IOException e) {
				e.printStackTrace();
			}

			// GridLayout(x,y)
			gridMapPanel = new JPanel(new GridLayout(myWorld.getMaxX(),
					myWorld.getMaxY(), 0, 0));
			mapCells = new JPanel[myWorld.getMaxX()][myWorld.getMaxY()];

			for (int i = 0; i < myWorld.getMaxX(); i++) {

				for (int j = 0; j < myWorld.getMaxY(); j++) {

					mapCells[i][j] = new JPanel();
					JLabel label = new JLabel((getImageIcon("null.png")));

					mapCells[i][j].setBackground(new Color(185, 122, 87));
					mapCells[i][j].add(label);

					gridMapPanel.add(mapCells[i][j]);
				}

			}
		}
			break;
		case 2: {
			// load the default config file
			myWorld = new World(fileOp.loadDefalutConfig());

			// GridLayout(x,y)
			gridMapPanel = new JPanel(new GridLayout(myWorld.getMaxX(),
					myWorld.getMaxY(), 0, 0));
			mapCells = new JPanel[myWorld.getMaxX()][myWorld.getMaxY()];

			for (int i = 0; i < myWorld.getMaxX(); i++) {

				for (int j = 0; j < myWorld.getMaxY(); j++) {

					mapCells[i][j] = new JPanel();
					JLabel label = new JLabel((getImageIcon("null.png")));

					mapCells[i][j].setBackground(new Color(185, 122, 87));
					mapCells[i][j].add(label);

					gridMapPanel.add(mapCells[i][j]);
				}

			}

		}
			break;
		case 3:
		case -1:
			System.exit(0);
			break;
		default:
			System.out
					.println("Something ain't right in the initial selection...");

		}

		// Toolbars, one for holding the menu items, one for holding control
		// buttons for simulations
		// JMenuBar topMenuBar, simulationBar;
		// buttons for simulation control
		// JButton simButton1, simButton2, simButton3, simButton4;
		// menu options
		// JMenu subMenuFile, subMenuView, subMenuEdit, subMenuHelp;
		// File options
		// JMenuItem itemFile1, itemFile2, itemFile3, itemFile4, itemFile5;
		// View options
		// JMenuItem itemView1, itemView2, itemView3, itemView4;
		// Edit options
		// JMenuItem itemEdit1, itemEdit2, itemEdit3;
		// Help options
		// JMenuItem itemHelp1, itemHelp2;

		// initialising File items
		itemFile3 = new JMenuItem("Save");
		itemFile4 = new JMenuItem("Save As");
		itemFile5 = new JMenuItem("Exit");
		// initialising View items
		itemView1 = new JMenuItem("Display Configuration");
		itemView2 = new JMenuItem("Edit Configuration");
		itemView3 = new JMenuItem("Display Info About Life Forms");
		itemView4 = new JMenuItem("Display Info About Map");
		// initialising Edit items
		itemEdit1 = new JMenuItem("Modify Current Life Forms");
		itemEdit2 = new JMenuItem("Remove Current Life Form");
		itemEdit3 = new JMenuItem("Add a New Life Form");
		// initialising Help items
		itemHelp1 = new JMenuItem("Display Info About Application");
		itemHelp2 = new JMenuItem("Display Info About Author");
		// initialising File menu and adding submenus
		subMenuFile = new JMenu("File");
		subMenuFile.add(itemFile3);
		subMenuFile.add(itemFile4);
		subMenuFile.add(itemFile5);
		// initialising View menu and adding submenus
		subMenuView = new JMenu("View");
		subMenuView.add(itemView1);
		subMenuView.add(itemView2);
		subMenuView.add(itemView3);
		subMenuView.add(itemView4);
		// initialising Edit menu and adding submenus
		subMenuEdit = new JMenu("Edit");
		subMenuEdit.add(itemEdit1);
		subMenuEdit.add(itemEdit2);
		subMenuEdit.add(itemEdit3);
		// initialising Help menu and adding submenus
		subMenuHelp = new JMenu("Help");
		subMenuHelp.add(itemHelp1);
		subMenuHelp.add(itemHelp2);
		// initialising the menu Bar and populating it with main menus
		topMenuBar = new JMenuBar();
		topMenuBar.add(subMenuFile);
		topMenuBar.add(subMenuView);
		topMenuBar.add(subMenuEdit);
		topMenuBar.add(subMenuHelp);
		// initialising simulation menu buttons and populating the menu with
		// those buttons
		simulationBar = new JMenuBar();
		simulationBar.setLayout(new GridLayout());
		jb_startMap = new JButton("Start Map Refreshing");
		// jb_pause = new JButton("play");
		// jb_stop = new JButton("pause");
		jb_startWorld = new JButton("Start World");
		jb_resumeWorld = new JButton("Resume World");
		jb_stopWorld = new JButton("Stop World");
		jb_stopMap = new JButton("Stop Map");

		// speed = 1;
		// Number Spinner to control the speed of the simulation, goes from 1 to
		// 10
		// SpinnerNumberModel jsm = new SpinnerNumberModel(speed, 1, 10, 1);
		// Creating a spinner to keep that Number Spinner inside of it
		// jspin_speed = new JSpinner(jsm);
		// Buttons for the simulation control
		simulationBar.add(jb_startMap);
		// simulationBar.add(jb_pause);
		// simulationBar.add(jb_stop);
		simulationBar.add(jb_startWorld);
		simulationBar.add(jb_stopWorld);
		simulationBar.add(jb_stopMap);
		simulationBar.add(jb_resumeWorld);

		// Creating a label for the spinner, to go at the beginning
		// JLabel jlb_speed = new JLabel("Speed of Simulation", JLabel.CENTER);
		// simulationBar.add(jspin_speed, BorderLayout.CENTER);
		// simulationBar.add(jlb_speed, BorderLayout.CENTER);
		// adding another label at the end
		// simulationBar.add(new JLabel("(Steps per second)", JLabel.CENTER),
		// 1 BorderLayout.SOUTH);
		// creating Bug Info panel and putting a JeDITORpANE into it
		JPanel bugInfoPanel = new JPanel();
		JEditorPane bugInfoPane = new JEditorPane();
		bugInfoPanel.add(bugInfoPane);
		// creating the main panel and adding stuff to it
		this.setLayout(new BorderLayout());
		add(topMenuBar, BorderLayout.NORTH);
		add(gridMapPanel, BorderLayout.CENTER);
		add(simulationBar, BorderLayout.SOUTH);

		// labels for entities
		// adding Action Listeners to all of the items
		itemFile3.addActionListener(this);
		itemFile4.addActionListener(this);
		itemFile5.addActionListener(this);
		itemView1.addActionListener(this);
		itemView2.addActionListener(this);
		itemView3.addActionListener(this);
		itemView4.addActionListener(this);
		itemEdit1.addActionListener(this);
		itemEdit2.addActionListener(this);
		itemEdit3.addActionListener(this);
		itemHelp1.addActionListener(this);
		itemHelp2.addActionListener(this);
		jb_startMap.addActionListener(this);
		// jb_pause.addActionListener(this);
		// jb_stop.addActionListener(this);
		jb_startWorld.addActionListener(this);
		jb_stopWorld.addActionListener(this);
		jb_stopMap.addActionListener(this);
		jb_resumeWorld.addActionListener(this);

		// jspin_speed.addChangeListener(this);

		// creating a new Timer for an asynchronous animation
		// timer = new Timer(1000 / speed, this);
		// delay in ms
		// timer.setInitialDelay(500);

	}

	private static ImageIcon getImageIcon(String iconFilename) {
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

	/**
	 * Update Map. Populates the map with Obstacles, then populates it with
	 * Food. <br>
	 * Then goes through each cell in the map and puts one of the three labels
	 * inside; Food, Obstacle, Empty
	 * 
	 * @param w
	 *            - taking a AWorld variable (uses the map (2D char array))
	 */
	private void updateMap() {

		for (int i = 0; i < myWorld.getMaxX(); i++) {

			for (int j = 0; j < myWorld.getMaxY(); j++) {
				// if (myWorld.getEntityOnTheMap(i, j).getAlteredState()) {

				// Re-paint a cell only if it's positionAltered is true
				// Then set it's positionAltered to false, so that it repaints
				// only if the object changed or moved

				if (myWorld.getEntityOnTheMap(i, j).getAlteredState()) {
					mapCells[i][j].removeAll();
					JLabel label = myWorld.getEntityOnTheMap(i, j).getJLabel();

					mapCells[i][j].add(label);
					mapCells[i][j].repaint();
					myWorld.getEntityOnTheMap(i, j).setAlteredState(false);

				}

				// }
			}

		}
		this.validate();

		/*
		 * for (int i = 0; i < myWorld.getX(); i++) { for (int j = 0; j <
		 * myWorld.getY(); j++) { if (myWorld.map[i][j] == myWorld.getObsSymb())
		 * { mapCells[i][j].add(obstacle); // puts an obstacle Label } else if
		 * (myWorld.map[i][j] == myWorld.getFoodSymb()) {
		 * mapCells[i][j].add(food); // puts a Food Label } else if
		 * (myWorld.map[i][j] == myWorld.getEmptySymb()) {
		 * mapCells[i][j].add(empty); // puts an Empty label } } }
		 * this.validate();
		 */
	}

	public void displayConfig() {
		JOptionPane
				.showMessageDialog(
						null,
						"To view the config simply open the config file you saved with any text editor.\nI recommend you to use Notepad or Notepad++ if you are running Windows.");
	}

	public void editConfig() {
		JOptionPane
				.showMessageDialog(null,
						"To edit the config file, simply edit it using any text editor.");
	}

	/**
	 * Function that responds to Actions links them to the functions that need
	 * to be executed <br>
	 * E.g. pressing the 'Start' button starts the simulation
	 */
	public void actionPerformed(ActionEvent e) {
		System.out.println("Event on a  " + e.getSource().getClass().toString()
				+ ": " + e.getSource().getClass().getCanonicalName());
		System.out.println(e.getID());

		if (e.getSource() == itemFile3) {

			fileOp.writeDefaultConfig(myWorld);

		} else if (e.getSource() == itemFile4) {

			fileOp.writeConfigAs(myWorld);

		} else if (e.getSource() == itemFile5) {
			// fileOp.writeConfig(myWorld);
			int option = JOptionPane
					.showConfirmDialog(
							null,
							"Are you sure you want to Exit?\nMap configuration is saved to default, which will\noverwrite the existing default configuration.\nUse 'Save As' to save the configuration to any file.",
							"Are you sure?", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				fileOp.writeDefaultConfig(myWorld);
				System.exit(0);
			}
		} else if (e.getSource() == itemView1) {
			this.displayConfig();

		} else if (e.getSource() == itemView2) {
			this.editConfig();
		} else if (e.getSource() == itemView3) {
			String content = "Here is a short summary about the Life Forms:\nNumber of Life Forms: "
					+ myWorld.lifeFormCount()
					+ "\nNumber of Carnivores: "
					+ myWorld.carnivoreCount()
					+ "\nNumber of Herbivores: "
					+ myWorld.herbivoreCount()
					+ "\nNumber of Herds: "
					+ myWorld.herdCount();

			JOptionPane.showMessageDialog(null, content);
		} else if (e.getSource() == itemView4) {
			String content = "Here is a short summary about the World:\nNumber of Life Forms: "
					+ myWorld.lifeFormCount()
					+ "\nNumber of Food items: "
					+ myWorld.foodCount()
					+ "\nFor info about Life Forms choose the other option please... ";

			JOptionPane.showMessageDialog(null, content);

		} else if (e.getSource() == itemEdit1) {
			// modify current life form
		} else if (e.getSource() == itemEdit2) {
			// remove current entity
		} else if (e.getSource() == itemEdit3) {
			// add a new Entity
		} else if (e.getSource() == itemHelp1) {
			// myWorld.displayHelp1();

		} else if (e.getSource() == itemHelp2) {
			// myWorld.displayHelp2();

		} else if (e.getSource() == jb_startMap) {
			runSimulation();
		} else if (e.getSource() == jb_startWorld) {
			myWorld.runWorld();
		} else if (e.getSource() == jb_stopWorld) {
			myWorld.stopAllEntities();
		} else if (e.getSource() == jb_stopMap) {
			this.stop();
		} else if (e.getSource()==jb_resumeWorld){
			myWorld.resumeAllEntities();
		}

	}

	public void showWorld(World w) {

		for (int i = 0; i < w.getMaxX(); i++) {
			for (int j = 0; j < w.getMaxY(); j++) {

				mapCells[i][j].removeAll();

				JLabel label = myWorld.getEntityOnTheMap(i, j).getJLabel();

				mapCells[i][j].add(label);

			}

		}

		this.validate();

	}

	public void runSimulation() {

		if (!activity.isAlive()) {
			activity.start();
		}
	}

	/**
	 * Uses the constructor (MainMenu()) to create a GUI with a default world.
	 * The world is empty at the start
	 */

	private static void createAndShowGUI() {
		JFrame simulationFrame = new JFrame("Bug Life Simulation");
		// set the default closing operation to pressing the EXIt button
		simulationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// creating a local 'AWorld' variable for the default map
		// using the constructor to create the GUI
		MainPanel simulationPanel = new MainPanel();
		simulationFrame.add(simulationPanel);
		// pack everything within the main Frame
		simulationFrame.pack();
		simulationFrame.setLocation(10, 10);
		// making the GUi visible to the user
		simulationFrame.setVisible(true);
	}

	@Override
	public void run() {
		while (!runTerminated) {
			try {
				this.updateMap();
				Thread.sleep(delay); // go to sleep for some time
			} catch (InterruptedException ex) {
				// wake up!
			}
		}
	}

	public void start() {
		runTerminated = false;
		if (!activity.isAlive()) {
			activity.start();
		}
	}

	public void stop() {
		runTerminated = true;
	}
	
	public void resume(){
		runTerminated = false;
	}

	/**
	 * Main method. Starts the GUI in a separate thread.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

}
