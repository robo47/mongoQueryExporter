package net.robo47.apps.mongoQueryExporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import net.robo47.apps.mongoQueryExporter.GUI.Console;

import org.jongo.Jongo;
import org.jongo.ResultHandler;

import au.com.bytecode.opencsv.CSVWriter;

import com.google.common.collect.Lists;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class Exporter {

	private CSVWriter writer;
	final private Mongo mongo;
	private Jongo jongo;
	final private int iterationInfo;
	final private Console console;

	public Exporter(final Mongo mongo, int iterationInfo, Console console) {
		this.mongo = mongo;
		this.iterationInfo = iterationInfo;
		this.console = console;
	}

	public void exportQueryToFile(final Query query, final File file)
			throws IOException {
		writer = new CSVWriter(new FileWriter(file), ';', '"', '\\', "\n");
		jongo = new Jongo(mongo.getDB(query.getDbName()));
		List<String> fields = query.getFields();

		@SuppressWarnings("unchecked")
		Iterator<DBObject> iterator = (Iterator<DBObject>) jongo
				.getCollection(query.getCollectionName())
				.find(query.getQuery()).hint(query.getHint())
				.limit(query.getLimit()).map(new ResultHandler<DBObject>() {
					@Override
					public DBObject map(DBObject result) {
						return result;
					}
				});

		int i = 1;
		for (DBObject result : getIteratable(iterator)) {
			if (i % iterationInfo == 0) {
				console.appendRow("Dumping Row " + i);
			}

			List<String> values = Lists.newArrayList();
			for (String fieldName : fields) {
				Object value = result.get(fieldName);
				String stringValue = null;
				if (value == null) {
					stringValue = "";
				} else {

					if (value instanceof String) {
						stringValue = (String) value;
					} else if (value instanceof Integer) {
						stringValue = ((Integer) value).toString();
					} else if (value instanceof Float) {
						stringValue = ((Float) value).toString();
					} else if (value instanceof Boolean) {
						stringValue = ((Boolean) value) ? "true" : "false";
					}

					if (stringValue == null) {
						throw new RuntimeException(
								"Unable to convert a value for field '"
										+ fieldName + "' to string: "
										+ value.toString());
					}
				}

				values.add(stringValue);
			}
			writer.writeNext(values.toArray(new String[values.size() - 1]));
			i++;
		}
		writer.close();
		FileInputStream stream = new FileInputStream(file);
		console.appendRow("Done wrote " + stream.getChannel().size()
				+ " bytes into file " + file.getAbsoluteFile());
		stream.close();
	}

	private Iterable<DBObject> getIteratable(final Iterator<DBObject> iterator) {
		return new Iterable<DBObject>() {
			@Override
			public Iterator<DBObject> iterator() {
				return iterator;
			}
		};
	}

}
