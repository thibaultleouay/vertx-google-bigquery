package io.vertx.bigquery;

import com.google.cloud.bigquery.InsertAllResponse;
import com.google.cloud.bigquery.JobStatus;
import com.google.cloud.bigquery.QueryResult;

import io.vertx.bigquery.impl.BigQueryClientImpl;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public interface BigQueryClient {

	static BigQueryClient create(Vertx vertx, JsonObject config) {
		return new BigQueryClientImpl(vertx, config);
	}

	@Fluent
	public BigQueryClient insert(Handler<AsyncResult<InsertAllResponse>> resultHandler, String dataset,
			String projectId, String tableId, JsonObject document);

	@Fluent
	public BigQueryClient read(Handler<AsyncResult<QueryResult>> resultHandler, String dataset, String projectId,
			String tableId, String query);
	
	@Fluent
	public BigQueryClient extractJob(Handler<AsyncResult<JobStatus>> resultHandler,JsonObject config);
	
	@Fluent
	public BigQueryClient loadJob(Handler<AsyncResult<JobStatus>> resultHandler,JsonObject config);
	
	@Fluent 
	public BigQueryClient copyJob(Handler<AsyncResult<JobStatus>> resultHandler,JsonObject config);
}
