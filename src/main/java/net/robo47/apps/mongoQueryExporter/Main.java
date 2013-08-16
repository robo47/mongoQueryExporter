package net.robo47.apps.mongoQueryExporter;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import net.robo47.apps.mongoQueryExporter.GUI.Console;
import net.robo47.apps.mongoQueryExporter.GUI.GUI;

public class Main {

	/**
	 * Each how many export rows it should show the info in the console window
	 */
	final public static int iterationInfo = 5000;

	public Main() {

	}

	public void run() {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				showGui();
			}
		});

	}

	public void showGui() {
		Console console = new Console();
		console.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		console.setMinimumSize(new Dimension(500, 500));
		console.setLocation(new Point(505, 0));

		JFrame gui = new GUI(console);
		gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		gui.setMinimumSize(new Dimension(500, 500));

		gui.pack();
		console.pack();
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
