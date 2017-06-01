package examples;

import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.api.client.util.DateTime;

import io.vertx.bigquery.BigQueryClient;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.json.JsonObject;

public class Examples {

	public static void main(String[] args) {

		VertxOptions options = new VertxOptions();
		options.setBlockedThreadCheckInterval(10000*60*60);
		Vertx vertx = Vertx.vertx(options);


		BigQueryClient client = BigQueryClient.create(vertx, new JsonObject().put("applicationName", "test"));

		Callable callable = () -> {
			System.err.println(Thread.currentThread().getName()+ " " + Instant.now());
			client.read(v -> 
				System.out.println("done :"+ Instant.now()+" "+Thread.currentThread().getName())
			, "bigquery-public-data", "baseball.games_post_wide", "",
					"SELECT * FROM [bigquery-public-data:baseball.games_post_wide] LIMIT 20");
			return null;
		};

		ExecutorService exec = Executors.newFixedThreadPool(5);

		for (int i = 0; i < 5; i++) {
			exec.submit(callable);
		}
		System.err.println("toto");
	}
}
