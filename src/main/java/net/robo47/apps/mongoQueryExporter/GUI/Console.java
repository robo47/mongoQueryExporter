package net.robo47.apps.mongoQueryExporter.GUI;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Console extends JFrame {

	private static final long serialVersionUID = -7688944729863910388L;

	private final JTextArea consoleWindow;

	public Console() {
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
		consoleWindow.setCaretPosition(consoleWindow.getText().length() - 1);
		consoleWindow.update(consoleWindow.getGraphics());
	}
}
