package io.vertx.bigquery.impl.actions;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.JobStatus;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public abstract class AbstractBigqueryRunJobAction extends AbstractBigqueryAction<JobStatus> {

	private final JsonObject jsonObject;

	public AbstractBigqueryRunJobAction(Vertx vertx, BigQuery bigquery, JsonObject config) {
		super(vertx, bigquery);
		this.jsonObject = config;
	}

	@Override
	protected JobStatus execute() throws BigQueryException {
		Job startedJob = bigquery.create(getJobInfo());
		while (!startedJob.isDone()) {
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return startedJob.getStatus();
	}

	protected abstract JobInfo getJobInfo();

	protected JsonObject getJsonObject() {
		return this.jsonObject;
	}
}
