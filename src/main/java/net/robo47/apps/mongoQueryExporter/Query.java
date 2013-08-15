package net.robo47.apps.mongoQueryExporter;

import java.io.Serializable;
import java.util.List;

/**
 * TODO Additional fields
 * 
 * @author robo47
 */
public class Query implements Serializable {

	private final String collectionName;
	private final String dbName;
	private final String query;
	private final String hint;
	private final String sort;
	private final Integer limit;
	private final List<String> fields;

	private static final long serialVersionUID = -8635262897777276592L;

	public Query(final String dbName, final String collectionName,
			final String query, final List<String> fields, final String hint,
			final String sort, final int limit) {
		this.dbName = dbName;
		this.collectionName = collectionName;
		this.query = query;
		this.hint = hint;
		this.limit = limit;
		this.sort = sort;
		this.fields = fields;
	}

	public String getCollectionName() {
		return this.collectionName;
	}

	public String getDbName() {
		return this.dbName;
	}

	public String getHint() {
		return this.hint;
	}

	public String getQuery() {
		return this.query;
	}

	public Integer getLimit() {
		return this.limit;
	}

	public List<String> getFields() {
		return this.fields;
	}

	public String getSort() {
		return this.sort;
	}
}