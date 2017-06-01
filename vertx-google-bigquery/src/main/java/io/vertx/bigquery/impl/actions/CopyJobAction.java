package io.vertx.bigquery.impl.actions;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.CopyJobConfiguration;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.TableId;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class CopyJobAction extends AbstractBigqueryRunJobAction {

	public CopyJobAction(Vertx vertx, BigQuery bigquery, JsonObject config) {
		super(vertx, bigquery, config);
	}

	@Override
	protected JobInfo getJobInfo() {
		String datasetTarget = getJsonObject().getString("dataset-target");
		String tableTarget = getJsonObject().getString("table-target");
		String datasetSource = getJsonObject().getString("dataset-source");
		String tableSource = getJsonObject().getString("table-source");
		TableId sourceTable = TableId.of(datasetSource, tableSource);
		TableId targetTable = TableId.of(datasetTarget, tableTarget);
		CopyJobConfiguration configuration = CopyJobConfiguration.of(targetTable, sourceTable);
		return JobInfo.of(configuration);
	}

}
