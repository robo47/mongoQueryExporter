package net.robo47.apps.mongoQueryExporter.GUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import net.robo47.apps.mongoQueryExporter.Exporter;
import net.robo47.apps.mongoQueryExporter.Main;
import net.robo47.apps.mongoQueryExporter.Query;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.google.common.collect.Lists;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mongodb.MongoClient;

public class GUI extends JFrame {

	private static final long serialVersionUID = 3652121405580600222L;

	private final JTextPane serverField;
	private final JTextPane databaseField;
	private final JTextPane collectionField;
	private final JTextPane queryField;
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
						ColumnSpec.decode("default:grow"),
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
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),
						RowSpec.decode("fill:min"), }));

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

		JLabel queryLabel = new JLabel("Query");
		getContentPane().add(queryLabel, "2, 17, right, default");

		JButton btnNewButton = new JButton("Export");
		btnNewButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				MongoClient mongo;
				try {

					String server = serverField.getText();
					String database = databaseField.getText();
					String collection = collectionField.getText();
					String query = queryField.getText();
					// TODO fields
					List<String> fields = Lists.newArrayList("domain", "ip",
							"crawled");

					String hint = hintField.getText();
					String filename = filenameField.getText();
					String sort = sortField.getText();

					int limit = Integer.parseInt(limitField.getText());

					mongo = new MongoClient(server);

					Query exportQuery = new Query(database, collection, query,
							fields, hint, sort, limit);

					File file = new File(filename);

					Exporter exporter = new Exporter(mongo, Main.iterationInfo,
							console);
					exporter.exportQueryToFile(exportQuery, file);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		queryField = new JTextPane();
		getContentPane().add(queryField, "4, 17, fill, fill");

		getContentPane().add(btnNewButton, "4, 21");

		addListenerForField(serverField, "server", "localhost",
				console.getPreferences());
		addListenerForField(databaseField, "database",
				"reverseIpDomainCrawler", console.getPreferences());
		addListenerForField(collectionField, "collection", "addresses",
				console.getPreferences());
		addListenerForField(queryField, "query", "", console.getPreferences());
		addListenerForField(hintField, "hint", "", console.getPreferences());
		addListenerForField(filenameField, "filename",
				"/home/robo47/export/export.csv", console.getPreferences());
		addListenerForField(limitField, "limit", "0", console.getPreferences());
		addListenerForField(sortField, "sort", "", console.getPreferences());
	}

	private void addListenerForField(JTextPane pane, String name,
			String defaultValue, PropertiesConfiguration properties) {

		pane.setText(properties.getString("form_field_" + name, defaultValue));
		pane.getDocument().addDocumentListener(new AutoValueSaveListener(name));

	}

	class AutoValueSaveListener implements DocumentListener {

		private final String fieldName;

		public AutoValueSaveListener(String fieldName) {
			this.fieldName = fieldName;
		}

		@Override
		public void changedUpdate(DocumentEvent event) {
			saveValue(event);
		}

		private void saveValue(DocumentEvent event) {
			final Document document = event.getDocument();
			try {
				// get the preferences associated with your application
				System.out.println("saving " + fieldName + " with value "
						+ document.getText(0, document.getLength()));

				// save textfield value in the preferences object
				console.getPreferences().setProperty(fieldName,
						"" + document.getText(0, document.getLength()));
				console.getPreferences().save();

			} catch (BadLocationException e) {
				e.printStackTrace();
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void insertUpdate(DocumentEvent event) {
			saveValue(event);
		}

		@Override
		public void removeUpdate(DocumentEvent event) {
			saveValue(event);
		}
	}

}
