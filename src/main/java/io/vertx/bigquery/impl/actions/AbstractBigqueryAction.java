package io.vertx.bigquery.impl.actions;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

public abstract class AbstractBigqueryAction<T> {

	final Vertx vertx;
	final BigQuery bigquery;

	public AbstractBigqueryAction(Vertx vertx, BigQuery bigquery) {
		this.vertx = vertx;
		this.bigquery = bigquery;
	}

	private void handle(Future<T> future) {
		try {
			T result = execute();
			future.complete(result);
		} catch (BigQueryException e) {
			future.fail(e);
		}
	}

	public void execute(Handler<AsyncResult<T>> resultHandler) {
		vertx.executeBlocking(this::handle, resultHandler);
	}

	protected abstract T execute() throws BigQueryException;
}
