package net.robo47.apps.mongoQueryExporter.GUI;

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

public class GUI extends JFrame {

	private static final long serialVersionUID = 3652121405580600222L;

	private final JTextField serverField;
	private final JTextField databaseField;
	private final JTextField collectionField;
	private final JTextPane hintField;
	private final JTextField limitField;
	private final JTextField filenameField;
	private final JTextPane sortField;
	private final JTextPane fieldsField;
	private final JTextPane queryField;

	public GUI(final Console console) {
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
		getContentPane().add(serverLabel, "2, 2, right, top");

		serverField = new JTextField();
		getContentPane().add(serverField, "4, 2, fill, fill");

		JLabel databaseLabel = new JLabel("Database");
		getContentPane().add(databaseLabel, "2, 4, right, top");

		databaseField = new JTextField();
		getContentPane().add(databaseField, "4, 4, fill, fill");

		JLabel collectionLabel = new JLabel("Collection");
		getContentPane().add(collectionLabel, "2, 6, right, top");

		collectionField = new JTextField();
		getContentPane().add(collectionField, "4, 6, fill, fill");

		JLabel limitLabel = new JLabel("Limit");
		getContentPane().add(limitLabel, "2, 8, right, top");

		limitField = new JTextField();
		getContentPane().add(limitField, "4, 8, fill, fill");

		JLabel filenameLabel = new JLabel("Filename");
		getContentPane().add(filenameLabel, "2, 10, right, top");

		filenameField = new JTextField();
		getContentPane().add(filenameField, "4, 10, fill, fill");

		JLabel sortLabel = new JLabel("Sort");
		getContentPane().add(sortLabel, "2, 12, right, top");

		sortField = new JTextPane();
		getContentPane().add(sortField, "4, 12, fill, fill");

		JLabel hintLabel = new JLabel("Hint");
		getContentPane().add(hintLabel, "2, 14, right, top");

		hintField = new JTextPane();
		getContentPane().add(hintField, "4, 14, fill, fill");

		JLabel label = new JLabel("Query");
		getContentPane().add(label, "2, 16, right, top");

		queryField = new JTextPane();
		getContentPane().add(queryField, "4, 16, fill, fill");

		JLabel fieldsLabel = new JLabel("Fields");
		getContentPane().add(fieldsLabel, "2, 18, right, top");

		fieldsField = new JTextPane();
		getContentPane().add(fieldsField, "4, 18, fill, fill");

		JButton exportButton = new JButton("Export");
		getContentPane().add(exportButton, "4, 20");

		exportButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				MongoClient mongo;
				try {
					final String server = serverField.getText();
					final String database = databaseField.getText();
					final String collection = collectionField.getText();
					final String filename = filenameField.getText();
					final String fields = fieldsField.getText();
					final String hint = hintField.getText();

					final String sort = sortField.getText();
					final String query = queryField.getText();

					final int limit = Integer.parseInt(limitField.getText());

					mongo = new MongoClient(server);

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
