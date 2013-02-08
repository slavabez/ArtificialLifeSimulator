package uk.ac.rdg.cs2.j7e.SlavaBezgachev.LifeSimulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * the ConfigWriterReader class is developed for writing World's configuration
 * files. It also supports reading the files but not loading them, i.e.
 * converting a file to a string, but not interpreting it
 * 
 * @author Slava
 * 
 */
public class ConfigWriterReader {
	/**
	 * Writes the configuration file for the World specified into the default
	 * location (the root folder of the application)
	 * 
	 * @param w
	 *            - the World that we're dealing with.
	 */
	public void writeDefaultConfig(World w) {
		FileWriter writer;
		try {
			writer = new FileWriter("config.als");
			PrintWriter cWriter = new PrintWriter(writer);

			// and let's begin writing the config file
			// we'll start with making a single String object that will
			// essentially be the config file

			String content;
			content = Integer.toString(w.getMaxX()) + ","
					+ Integer.toString(w.getMaxY());
			// Now we have a string with Map's size X and Y

			// now we add a separator $ that will be used for splitting the file
			// when we want to read and load it
			content = content + "$";

			// now we will write each object one by one, separating objects with
			// '&'
			// the first letter will determine the type of the object, which
			// will determine how the object is saved in the file
			// each entity has it's own saveString function that deals with how
			// it is saved in the file

			for (int i = 0; i < w.getMaxX(); i++) {
				for (int j = 0; j < w.getMaxY(); j++) {
					content = content + w.getEntityOnTheMap(i, j).saveString()
							+ "&";
				}
			}
			// closing the writers
			cWriter.print(content);
			cWriter.flush();
			cWriter.close();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Similar to writing the config to the defalut location, but instead writes
	 * it to a location specified by the user. Opens a JFileChooser dialog,
	 * prompting the user about the saving directory
	 * 
	 * @param w
	 *            - the World we want to write
	 */
	public void writeConfigAs(World w) {
		// boolean to determine whether to execute the writing or not
		boolean execute = true;
		File selectedFile = null;
		JFileChooser fileChooser = new JFileChooser();
		int status = fileChooser.showSaveDialog(null);

		if (status == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
		} else if (status == JFileChooser.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(null,
					"No File selected, please try again");
			execute = false;
		}
		FileWriter writer;
		if (execute == true) {

			try {
				writer = new FileWriter(selectedFile);
				PrintWriter cWriter = new PrintWriter(writer);

				// and let's begin writing the config file
				// we'll start with making a single String object that will
				// essentially be the config file

				String content;
				content = Integer.toString(w.getMaxX()) + ","
						+ Integer.toString(w.getMaxY());
				// Now we have a string with Map's size X and Y

				// now we add a separator $ that will be used for splitting the
				// file
				// when we want to read and load it
				content = content + "$";

				// now we will write each object one by one, separating objects
				// with
				// '&'
				// the first letter will determine the type of the object, which
				// will determine how the object is saved in the file
				// each entity has it's own saveString function that deals with
				// how
				// it is saved in the file

				for (int i = 0; i < w.getMaxX(); i++) {
					for (int j = 0; j < w.getMaxY(); j++) {
						content = content
								+ w.getEntityOnTheMap(i, j).saveString() + "&";
					}
				}
				cWriter.print(content);

				cWriter.flush();
				cWriter.close();
				writer.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * This function reads the config file from the default directory, puts the
	 * content in a String and returns it. Default directory is the apps root
	 * directory, default name of the config file is 'config.als'
	 * 
	 * @return
	 */
	public String loadDefalutConfig() {
		File namePath;
		namePath = new File("config.als");
		String fileContents = "";

		try {
			fileContents = new Scanner(namePath).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return fileContents;
	}
	/**
	 * Very similar to the loadDefaultConfig function, except it promts the user for which file to read. Then returns the contents of that file in a String
	 * @return - the String with the content
	 * @throws IOException
	 */
	public String loadConfigFile() throws IOException {
		//FileChooser to ask the user what to open
		final JFileChooser fc = new JFileChooser();
		
		File configFile = new File("C:/");
		
		//suppressing compilation warnings 
		@SuppressWarnings("unused")
		File nameDir, namePath;
		namePath = new File("C:/config.als");
		int checker;
		fc.setDialogTitle("Please choose the config file");
		fc.setCurrentDirectory(configFile);
		checker = fc.showOpenDialog(null);
		if (checker == JFileChooser.APPROVE_OPTION) {
			nameDir = fc.getCurrentDirectory();
			namePath = fc.getSelectedFile();
		} else
			JOptionPane.showMessageDialog(null, "You have clicked 'Cancel'",
					"Cancel Dialog", JOptionPane.WARNING_MESSAGE);
		@SuppressWarnings("resource")
		String fileContents = new Scanner(namePath).useDelimiter("\\Z").next();
		// HAHAHA, we now have the contents of the file in a string

		return fileContents;
	}

}
