package net.robo47.apps.mongoQueryExporter;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import net.robo47.apps.mongoQueryExporter.GUI.GUI;

public class Main {
	public Main() {

	}

	public void run() {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Main.this.showGui();
			}
		});

	}

	public void showGui() {
		JFrame gui = new GUI();
		gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		gui.setMinimumSize(new Dimension(500, 500));

		gui.pack();
		gui.setVisible(true);

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
