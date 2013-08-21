package net.robo47.apps.mongoQueryExporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.jongo.Find;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.ResultHandler;

import au.com.bytecode.opencsv.CSVWriter;

import com.google.common.collect.Lists;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class Exporter {

	public void exportQueryToFile(final Mongo mongo, final Query query,
			final File file, ExporterStatus status) throws IOException {

		Jongo jongo = new Jongo(mongo.getDB(query.getDbName()));
		List<String> fields = query.getFieldsAsList();

		MongoCollection collection = jongo.getCollection(query
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

		StopWatch watch = status.getWatch();
		watch.start();

		status.setStatus("Exporting data");
		CSVWriter writer = new CSVWriter(new FileWriter(file), ';', '"', '\\',
				"\n");
		writer.writeNext(fields.toArray(new String[fields.size() - 1]));
		Iterable<DBObject> iter = this.getIteratable(iterator);
		long currentRow = 1;
		long skippedRows = 0;
		long fileSizeWritten = 0;
		for (DBObject result : iter) {
			status.setCurrentRow(currentRow);

			List<String> values = Lists.newArrayList();
			for (String fieldName : fields) {
				Object value = result.get(fieldName);
				String stringValue = ExporterUtil.convertToString(value);
				if (stringValue == null) {
					writer.close();
					throw new RuntimeException(
							"Unable to convert a value for field '" + fieldName
									+ "' to string: " + value.toString()
									+ "class: " + value.getClass());
				}

				status.setFileSizeWritten(stringValue.length() + 1);
				values.add(stringValue);
			}
			if (values.size() > 0) {
				writer.writeNext(values.toArray(new String[values.size() - 1]));
				writer.flush();
			} else {
				skippedRows++;
			}
			currentRow++;
			status.setFileSizeWritten(fileSizeWritten);
			status.setSkippedRows(skippedRows);
			status.updateStatusRowFromExportStatus();
			status.setWaiting(true);
		}
		status.setStatus("Writing and closing file");
		writer.flush();
		writer.close();
		status.setStatus("Done");
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
