package io.vertx.bigquery.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.bigquery.BigqueryScopes;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.InsertAllResponse;
import com.google.cloud.bigquery.JobStatus;
import com.google.cloud.bigquery.QueryResult;

import io.vertx.bigquery.BigQueryClient;
import io.vertx.bigquery.impl.actions.BigqueryExtractJobAction;
import io.vertx.bigquery.impl.actions.BigqueryInsert;
import io.vertx.bigquery.impl.actions.BigqueryLoadJobAction;
import io.vertx.bigquery.impl.actions.BigqueryRead;
import io.vertx.bigquery.impl.actions.CopyJobAction;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class BigQueryClientImpl implements BigQueryClient {

	private final Vertx vertx;
	private final JsonObject config;
	private final BigQuery bigquery;

	public BigQueryClientImpl(Vertx vertx, JsonObject config) {
		Objects.requireNonNull(vertx);
		Objects.requireNonNull(config);
		this.vertx = vertx;
		this.config = config;
		this.bigquery = getBigqueryConnection(config);

	}

	private BigQuery getBigqueryConnection(JsonObject config) {
		GoogleCredential credential = null;
		HttpTransport transport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		Collection<String> bigqueryScopes = BigqueryScopes.all();

		try {
			credential = GoogleCredential.getApplicationDefault(transport, jsonFactory);
		} catch (IOException e) {
		//FIXME 
		}
		if (credential.createScopedRequired()) {
			credential = credential.createScoped(bigqueryScopes);
		}

		return BigQueryOptions.getDefaultInstance().getService();
	}

	@Override
	public BigQueryClient insert(Handler<AsyncResult<InsertAllResponse>> resultHandler, String dataset,
			String projectId, String tableId, JsonObject document) {
		new BigqueryInsert(vertx, bigquery, dataset, projectId, tableId, document).execute(resultHandler);
		return this;
	}

	@Override
	public BigQueryClient read(Handler<AsyncResult<QueryResult>> resultHandler, String dataset, String projectId,
			String tableId, String query) {
		new BigqueryRead(vertx, bigquery, query).execute(resultHandler);
		return this;
	}
	
	@Override
	public BigQueryClient extractJob(Handler<AsyncResult<JobStatus>> resultHandler,JsonObject config ) {
		new BigqueryExtractJobAction(vertx, bigquery, config).execute(resultHandler);
		return this;
	}
	
	@Override
	public BigQueryClient loadJob(Handler<AsyncResult<JobStatus>> resultHandler,JsonObject config) {
		new BigqueryLoadJobAction(vertx, bigquery, config).execute(resultHandler);
		return this;
	}
	
	@Override
	public BigQueryClient copyJob(Handler<AsyncResult<JobStatus>> resultHandler,JsonObject config) {
		new CopyJobAction(vertx, bigquery, config).execute(resultHandler);
		return this;
	}
	
}
