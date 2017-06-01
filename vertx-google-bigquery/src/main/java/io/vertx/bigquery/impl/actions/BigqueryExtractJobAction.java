package io.vertx.bigquery.impl.actions;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.ExtractJobConfiguration;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.TableId;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class BigqueryExtractJobAction extends AbstractBigqueryRunJobAction {

	public BigqueryExtractJobAction(Vertx vertx, BigQuery bigquery, JsonObject config) {
		super(vertx, bigquery, config);
	}

	@Override
	protected JobInfo getJobInfo() {

		String dataset = getJsonObject().getString("dataset");
		String table = getJsonObject().getString("table");
		String format = getJsonObject().getString("format");
		String target = getJsonObject().getString("target");
		TableId tableId = TableId.of(dataset, table);
		ExtractJobConfiguration configuration = ExtractJobConfiguration.of(tableId, target, format);
		return JobInfo.of(configuration);
	}

}
