package io.vertx.bigquery.impl.actions;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.FormatOptions;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.LoadJobConfiguration;
import com.google.cloud.bigquery.TableId;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class BigqueryLoadJobAction extends AbstractBigqueryRunJobAction {

	public BigqueryLoadJobAction(Vertx vertx, BigQuery bigquery, JsonObject config) {
		super(vertx, bigquery, config);
	}

	@Override
	protected JobInfo getJobInfo() {

		String dataset = getJsonObject().getString("dataset");
		String table = getJsonObject().getString("table");
		String format = getJsonObject().getString("format");
		String source = getJsonObject().getString("source");
		TableId tableId = TableId.of(dataset, table);
		LoadJobConfiguration configuration = LoadJobConfiguration.of(tableId, source, FormatOptions.of(format));
		return JobInfo.of(configuration);
	}

}
