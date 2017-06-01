package io.vertx.bigquery.impl.actions;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.InsertAllRequest;
import com.google.cloud.bigquery.InsertAllResponse;
import com.google.cloud.bigquery.TableId;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class BigqueryInsert extends AbstractBigqueryAction<InsertAllResponse> {

	private final JsonObject document;
	private final String project;
	private final String table;
	private final String dataset;

	public BigqueryInsert(Vertx vertx, BigQuery bigquery, String dataset, String project, String table,
			JsonObject document) {
		super(vertx, bigquery);
		this.document = document;
		this.project = project;
		this.table = table;
		this.dataset = dataset;
	}

	@Override
	protected InsertAllResponse execute() throws BigQueryException {
		InsertAllRequest insertRequest = InsertAllRequest.newBuilder(dataset, table).addRow(document.getMap()).build();
		return bigquery.insertAll(insertRequest);
	}

}
