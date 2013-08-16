package net.robo47.apps.mongoQueryExporter.GUI;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import net.robo47.apps.mongoQueryExporter.Exporter;
import net.robo47.apps.mongoQueryExporter.Main;
import net.robo47.apps.mongoQueryExporter.Query;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;

public class GUI extends JFrame {

	private static final long	serialVersionUID	= 3652121405580600222L;

	private final JTextField	serverField;
	private final JTextField	databaseField;
	private final JTextField	collectionField;
	private final JTextPane		hintField;
	private final JTextField	limitField;
	private final JTextField	filenameField;
	private final JTextPane		sortField;
	private final JTextPane		fieldsField;
	private final JTextPane		queryField;

	public GUI(final Console console) {
		this.getContentPane().setLayout(
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
						RowSpec.decode("fill:default"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("fill:default"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("fill:default"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("fill:default"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("fill:default"),
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JLabel serverLabel = new JLabel("Server");
		this.getContentPane().add(serverLabel, "2, 2, right, top");

		this.serverField = new JTextField();
		this.getContentPane().add(this.serverField, "4, 2, fill, fill");

		JLabel databaseLabel = new JLabel("Database");
		this.getContentPane().add(databaseLabel, "2, 4, right, top");

		this.databaseField = new JTextField();
		this.getContentPane().add(this.databaseField, "4, 4, fill, fill");

		JLabel collectionLabel = new JLabel("Collection");
		this.getContentPane().add(collectionLabel, "2, 6, right, top");

		this.collectionField = new JTextField();
		this.getContentPane().add(this.collectionField, "4, 6, fill, fill");

		JLabel limitLabel = new JLabel("Limit");
		this.getContentPane().add(limitLabel, "2, 8, right, top");

		this.limitField = new JTextField();
		this.getContentPane().add(this.limitField, "4, 8, fill, fill");

		JLabel filenameLabel = new JLabel("Filename");
		this.getContentPane().add(filenameLabel, "2, 10, right, top");

		this.filenameField = new JTextField();
		this.getContentPane().add(this.filenameField, "4, 10, fill, fill");

		JLabel sortLabel = new JLabel("Sort");
		this.getContentPane().add(sortLabel, "2, 12, right, top");

		this.sortField = new JTextPane();
		this.getContentPane().add(this.sortField, "4, 12, fill, fill");

		JLabel hintLabel = new JLabel("Hint");
		this.getContentPane().add(hintLabel, "2, 14, right, top");

		this.hintField = new JTextPane();
		this.getContentPane().add(this.hintField, "4, 14, fill, fill");

		JLabel label = new JLabel("Query");
		this.getContentPane().add(label, "2, 16, right, top");

		this.queryField = new JTextPane();
		this.getContentPane().add(this.queryField, "4, 16, fill, fill");

		JLabel fieldsLabel = new JLabel("Fields");
		this.getContentPane().add(fieldsLabel, "2, 18, right, top");

		this.fieldsField = new JTextPane();
		this.getContentPane().add(this.fieldsField, "4, 18, fill, fill");
		this.fieldsField.setMinimumSize(new Dimension(0, 100));

		JButton exportButton = new JButton("Export");
		this.getContentPane().add(exportButton, "4, 20");

		exportButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Start");
				MongoClient mongo;
				MongoClientOptions mongoOptions = new MongoClientOptions.Builder()
						.autoConnectRetry(true)
						.readPreference(ReadPreference.secondaryPreferred())
						.build();

				try {
					final String server = GUI.this.serverField.getText();
					final String database = GUI.this.databaseField.getText();
					final String collection = GUI.this.collectionField
							.getText();
					final String filename = GUI.this.filenameField.getText();
					final String fields = GUI.this.fieldsField.getText();
					final String hint = GUI.this.hintField.getText();

					final String sort = GUI.this.sortField.getText();
					final String query = GUI.this.queryField.getText();

					final int limit = Integer.parseInt(GUI.this.limitField
							.getText());

					mongo = new MongoClient(server, mongoOptions);

					final Query exportQuery = new Query(database, collection,
							query, fields, hint, sort, limit);

					final File file = new File(filename);

					final Exporter exporter = new Exporter(mongo,
							Main.iterationInfo, console);
					exporter.exportQueryToFile(exportQuery, file);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		// test data
		// serverField.setText("localhost");
		// databaseField.setText("reverseIpDomainCrawler");
		// collectionField.setText("addresses");
		// filenameField.setText("/home/robo47/export/export2.csv");
		// fieldsField.setText("{ domain: 1, ip: 1, crawled: 1 }");
		// limitField.setText("0");

	}

}
