package net.robo47.apps.mongoQueryExporter;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import net.robo47.apps.mongoQueryExporter.GUI.Console;
import net.robo47.apps.mongoQueryExporter.GUI.GUI;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Main {

	final public static int iterationInfo = 5000;

	public Main() {

	}

	public void run() {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				showGui();
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

	public void showGui() {
		Console console = new Console();
		console.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		console.setSize(500, 500);
		console.setMinimumSize(new Dimension(500, 500));
		console.setLocation(new Point(500, 0));

		JFrame gui = new GUI(console);
		gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		gui.setSize(500, 500);
		gui.setMinimumSize(new Dimension(500, 500));

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
