package net.robo47.apps.mongoQueryExporter.GUI;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.configuration.PropertiesConfiguration;

public class Console extends JFrame {

	private static final long serialVersionUID = -7688944729863910388L;

	private final JTextArea consoleWindow;
	private final PropertiesConfiguration preferences;

	public Console(PropertiesConfiguration preferences) {
		this.preferences = preferences;
		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);
		consoleWindow = new JTextArea();
		scrollPane.setViewportView(consoleWindow);
		consoleWindow.setSize(800, 600);
		consoleWindow.setFont(Font.getFont("Courier"));
	}

	public void appendRow(String row) {
		consoleWindow.append(row + "\n");
		System.out.println(row);
		consoleWindow.setCaretPosition(consoleWindow.getText().length() - 1);
		consoleWindow.update(consoleWindow.getGraphics());
	}

	public PropertiesConfiguration getPreferences() {
		return this.preferences;
	}

}
