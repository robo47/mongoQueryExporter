package net.robo47.apps.mongoQueryExporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.robo47.apps.mongoQueryExporter.GUI.Console;

import org.apache.commons.lang.time.StopWatch;
import org.bson.types.ObjectId;
import org.jongo.Find;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.ResultHandler;

import au.com.bytecode.opencsv.CSVWriter;

import com.google.common.collect.Lists;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class Exporter {

	private CSVWriter		writer;
	final private Mongo		mongo;
	private Jongo			jongo;
	final private int		iterationInfo;
	final private Console	console;

	public Exporter(final Mongo mongo, int iterationInfo, Console console) {
		this.mongo = mongo;
		this.iterationInfo = iterationInfo;
		this.console = console;
	}

	public void exportQueryToFile(final Query query, final File file)
			throws IOException {
		this.writer = new CSVWriter(new FileWriter(file), ';', '"', '\\', "\n");
		this.jongo = new Jongo(this.mongo.getDB(query.getDbName()));
		List<String> fields = query.getFieldsAsList();

		MongoCollection collection = this.jongo.getCollection(query
				.getCollectionName());

		Find find = collection.find(query.getQuery());

		find.hint(query.getHint());

		find.projection(query.getFields());
		if (!"0".equals(query.getLimit()) && query.getLimit() != null) {
			find.limit(query.getLimit());
		}

		System.out.println("Generating query");
		@SuppressWarnings("unchecked")
		Iterator<DBObject> iterator = (Iterator<DBObject>) find
				.map(new ResultHandler<DBObject>() {
					@Override
					public DBObject map(DBObject result) {
						return result;
					}
				});
		StopWatch watch = new StopWatch();
		watch.start();
		System.out.println("Exporting data");
		int i = 1;
		this.writer.writeNext(fields.toArray(new String[fields.size() - 1]));
		Iterable<DBObject> iter = this.getIteratable(iterator);
		for (DBObject result : iter) {
			if (i % this.iterationInfo == 0) {
				this.console.appendRow(watch.getTime() + "] Dumping Row " + i);
				this.writer.flush();
				System.out.println("flush");
				Runtime runtime = Runtime.getRuntime();

				NumberFormat format = NumberFormat.getInstance();

				StringBuilder sb = new StringBuilder();
				long maxMemory = runtime.maxMemory();
				long allocatedMemory = runtime.totalMemory();
				long freeMemory = runtime.freeMemory();

				sb.append("free memory: " + format.format(freeMemory / 1024)
						+ "\n");
				sb.append("allocated memory: "
						+ format.format(allocatedMemory / 1024) + "\n");
				sb.append("max memory: " + format.format(maxMemory / 1024)
						+ "\n");
				sb.append("total free memory: "
						+ format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024)
						+ "\n");
				System.out.println(sb.toString());
			}
			if (i == 100) {
				System.out.println("100");
			}
			System.out.println(watch.getTime() + "] line: " + i);

			List<String> values = Lists.newArrayList();
			for (String fieldName : fields) {
				Object value = result.get(fieldName);
				String stringValue = null;
				if (value == null) {
					stringValue = "";
				} else {

					// TODO other datatypes ?
					if (value instanceof String) {
						stringValue = (String) value;
					} else if (value instanceof Integer) {
						stringValue = ((Integer) value).toString();
					} else if (value instanceof Float) {
						stringValue = ((Float) value).toString();
					} else if (value instanceof Boolean) {
						stringValue = ((Boolean) value) ? "true" : "false";
					} else if (value instanceof Date) {
						SimpleDateFormat dt = new SimpleDateFormat(
								"dd.MM.yyyy HH:mm:ss");
						stringValue = dt.format((Date) value);
					} else if (value instanceof ObjectId) {
						ObjectId id = ((ObjectId) value);
						stringValue = id.toStringMongod();
					}

					if (stringValue == null) {
						throw new RuntimeException(
								"Unable to convert a value for field '"
										+ fieldName + "' to string: "
										+ value.toString() + "class: "
										+ value.getClass());
					}
				}

				values.add(stringValue);
			}
			if (values.size() > 0) {
				this.writer
						.writeNext(values.toArray(new String[values.size() - 1]));
				this.writer.flush();
			} else {
				// this.console.appendRow("empty row skipped");
			}
			i++;
		}
		System.out.println(watch.getTime() + "Writing File flush");
		this.writer.flush();
		System.out.println(watch.getTime() + "Writing File close");
		this.writer.close();

		// this.console
		// .appendRow("Done wrote into file " + file.getAbsoluteFile());
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
