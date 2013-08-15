package net.robo47.apps.mongoQueryExporter.GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class GUI extends JFrame {

	private static final long serialVersionUID = 3652121405580600222L;

	private final JTextPane serverField;
	private final JTextPane databaseField;
	private final JTextPane collectionField;
	private final JTextPane hintField;
	private final JTextPane limitField;
	private final JTextPane filenameField;
	private final JTextPane sortField;

	private final Console console;

	public GUI(final Console console) {
		this.console = console;

		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),
						RowSpec.decode("fill:min"),
						FormFactory.RELATED_GAP_ROWSPEC, }));

		JLabel serverLabel = new JLabel("Server");
		getContentPane().add(serverLabel, "2, 2, right, default");

		serverField = new JTextPane();
		getContentPane().add(serverField, "4, 2, fill, fill");
		serverField.setSize(300, 300);
		serverField.setSize(300, 300);
		serverField.setSize(300, 300);
		serverField.setSize(300, 300);
		serverField.setSize(300, 300);
		serverField.setSize(300, 300);
		serverField.setSize(300, 300);

		JLabel databaseLabel = new JLabel("Database");
		getContentPane().add(databaseLabel, "2, 4, right, default");

		databaseField = new JTextPane();
		getContentPane().add(databaseField, "4, 4, fill, fill");

		JLabel collectionLabel = new JLabel("Collection");
		getContentPane().add(collectionLabel, "2, 6, right, default");

		collectionField = new JTextPane();
		getContentPane().add(collectionField, "4, 6, fill, fill");

		JLabel limitLabel = new JLabel("Limit");
		getContentPane().add(limitLabel, "2, 8, right, default");

		limitField = new JTextPane();
		getContentPane().add(limitField, "4, 8, fill, fill");

		JLabel filenameLabel = new JLabel("Filename");
		getContentPane().add(filenameLabel, "2, 10, right, default");

		filenameField = new JTextPane();
		getContentPane().add(filenameField, "4, 10, fill, fill");

		JLabel sortLabel = new JLabel("Sort");
		getContentPane().add(sortLabel, "2, 12, right, default");

		sortField = new JTextPane();
		getContentPane().add(sortField, "4, 12, fill, fill");

		// TODO limit!
		JLabel hintLabel = new JLabel("Hint");
		getContentPane().add(hintLabel, "2, 14, right, default");

		hintField = new JTextPane();
		getContentPane().add(hintField, "4, 14, fill, fill");

		JLabel label = new JLabel("Query");
		getContentPane().add(label, "2, 16, right, default");

		JTextPane textPane = new JTextPane();
		getContentPane().add(textPane, "4, 16, fill, fill");

		JButton button = new JButton("Export");
		getContentPane().add(button, "4, 19");

	}

}
