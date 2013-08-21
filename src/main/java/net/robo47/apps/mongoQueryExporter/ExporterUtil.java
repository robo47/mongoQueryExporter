package net.robo47.apps.mongoQueryExporter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.types.ObjectId;

public class ExporterUtil {

	public static String humanReadableByteCount(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if (bytes < unit)
			return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1)
				+ (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

	public static String humanReadableByteCount(long bytes) {
		return humanReadableByteCount(bytes, false);
	}

	public static String convertToString(final Object value) {
		String stringValue = "";
		// TODO other datatypes ?
		if (value == null) {
			stringValue = "";
		} else if (value instanceof String) {
			stringValue = (String) value;
		} else if (value instanceof Integer) {
			stringValue = ((Integer) value).toString();
		} else if (value instanceof Float) {
			stringValue = ((Float) value).toString();
		} else if (value instanceof Boolean) {
			stringValue = ((Boolean) value) ? "true" : "false";
		} else if (value instanceof Date) {
			SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			stringValue = dt.format((Date) value);
		} else if (value instanceof ObjectId) {
			ObjectId id = ((ObjectId) value);
			stringValue = id.toStringMongod();
		}
		return stringValue;
	}
}
