package net.robo47.apps.mongoQueryExporter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

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
	private final String fields;
	private final List<String> fieldsAsList;

	private static final long serialVersionUID = -8635262897777276592L;

	public Query(final String dbName, final String collectionName,
			final String query, final String fields, final String hint,
			final String sort, final int limit) {
		this.dbName = dbName;
		this.collectionName = collectionName;
		this.query = query;
		this.hint = hint;
		this.limit = limit;
		this.sort = sort;
		this.fields = fields;
		this.fieldsAsList = fieldsToList(this.fields);
	}

	private List<String> fieldsToList(String fields) {
		List<String> fieldsList = Lists.newArrayList();
		Gson gson = new Gson();
		Map<String, Double> map = new HashMap<String, Double>();
		map = gson.fromJson(fields, map.getClass());
		for (String key : map.keySet()) {

			if (map.get(key).equals(new Double(1.0))) {
				fieldsList.add(key.replaceAll("^\"|\"$", ""));
			}
		}
		return fieldsList;
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

	public String getFields() {
		return this.fields;
	}

	public String getSort() {
		return this.sort;
	}

	public List<String> getFieldsAsList() {
		return this.fieldsAsList;
	}
}