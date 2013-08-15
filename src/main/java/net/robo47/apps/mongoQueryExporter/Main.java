package net.robo47.apps.mongoQueryExporter;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import net.robo47.apps.mongoQueryExporter.GUI.Console;
import net.robo47.apps.mongoQueryExporter.GUI.GUI;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Main {

	final public static int iterationInfo = 5000;
	final public static File propertiesFile = new File(
			System.getProperty("user.home") + File.separator
					+ ".mongoQueryExporter");
	PropertiesConfiguration config;

	public Main() {

	}

	public void run() {

		if (propertiesFile.exists()) {
			try {
				config = new PropertiesConfiguration(propertiesFile);
			} catch (ConfigurationException e1) {
				e1.printStackTrace();
			}
		} else {
			try {
				config = new PropertiesConfiguration(propertiesFile);
				config.save();
			} catch (ConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				showGui(config);
				System.out.println("run end");
			}
		});

	}

	// TODO fenster-position speichern!
	class Closer extends WindowAdapter implements Serializable {

		private static final long serialVersionUID = -8195889633201800566L;

		private final String name;
		private final PropertiesConfiguration preferences;

		public Closer(String name, PropertiesConfiguration preferences) {
			this.name = name;
			this.preferences = preferences;
		}

		@Override
		public void windowClosing(WindowEvent e) {
			System.out.println("Close, saving properties");
			preferences.setProperty(name + "_width", ""
					+ e.getComponent().getWidth());
			preferences.setProperty(name + "_height", ""
					+ e.getComponent().getHeight());

			try {
				preferences.save();
			} catch (ConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Close, saved properties");
			System.exit(0);
		}
	}

	public void loadPreferencesForJFrame(JFrame frame, String name,
			PropertiesConfiguration properties) {
		System.out.println("Loading prefs for " + name);
		frame.setSize(
				Integer.parseInt(properties.getString(name + "_width", "500")),
				Integer.parseInt(properties.getString(name + "_height", "500")));

	}

	public void showGui(PropertiesConfiguration preferences) {
		Console console = new Console(preferences);
		console.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		console.addWindowListener(new Closer("console", preferences));
		loadPreferencesForJFrame(console, "console", preferences);

		console.setSize(500, 500);
		console.setMinimumSize(new Dimension(500, 500));
		JFrame gui = new GUI(console);
		gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		gui.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		gui.addWindowListener(new Closer("gui", preferences));
		loadPreferencesForJFrame(gui, "gui", preferences);
		gui.setSize(500, 500);

		// Display the window.
		console.pack();
		gui.pack();
		gui.setVisible(true);
		console.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Main main = new Main();
			main.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
