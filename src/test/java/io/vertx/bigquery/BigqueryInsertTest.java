package io.vertx.bigquery;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import io.vertx.core.json.JsonObject;
import io.vertx.test.core.VertxTestBase;

public class BigqueryInsertTest extends VertxTestBase {

	BigQueryClient bigqueryClient;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		bigqueryClient = BigQueryClient.create(vertx, config());

	}

	@Test
	public void readTest() {

		bigqueryClient.read(v -> {
			assertTrue(v.succeeded());
			System.out.println(v.result().getValues());
			testComplete();
		}, "bigquery-public-data", "baseball.games_post_wide", "",
				"SELECT * FROM [bigquery-public-data:baseball.games_post_wide] LIMIT 20");
		await();
	}

	protected static JsonObject config() {
		return new JsonObject().put("applicationName", "test");
	}
}
