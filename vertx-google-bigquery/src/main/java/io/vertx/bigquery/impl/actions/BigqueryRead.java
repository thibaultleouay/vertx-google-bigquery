package io.vertx.bigquery.impl.actions;

import com.google.cloud.bigquery.QueryRequest;
import com.google.cloud.bigquery.QueryResponse;
import com.google.cloud.bigquery.QueryResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;

import io.vertx.core.Vertx;

public class BigqueryRead extends AbstractBigqueryAction<QueryResult> {

	private final String query;

	public BigqueryRead(Vertx vertx, BigQuery bigquery, String query) {
		super(vertx, bigquery);
		this.query = query;
	}

	@Override
	protected QueryResult execute() throws BigQueryException {
		QueryRequest queryRequest = QueryRequest
				.newBuilder(query).setMaxWaitTime(180000L)
				.setPageSize(1000L).build();
		QueryResponse queryResponse = bigquery.query(queryRequest);
		while (!queryResponse.jobCompleted()) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return queryResponse.getResult();
	}

}
