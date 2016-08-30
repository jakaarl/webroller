package webroller;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public abstract class AbstractVertxTestCase {
	
	@Rule
	public final RunTestOnContext runtime = new RunTestOnContext();
	
	protected TestLauncher launcher;
	protected HttpClient client;
	
	@Before
	public void setup(TestContext testContext) {
		Vertx vertx = runtime.vertx();
		launcher = createLauncher(vertx);
		client = vertx.createHttpClient();
		Async async = testContext.async();
		launcher.start(result -> {
			testContext.assertTrue(result.succeeded());
			async.complete();
		});
	}
	
	protected TestLauncher createLauncher(Vertx vertx) {
		return new TestLauncher(vertx);
	}
	
	@After
	public void tearDown(TestContext testContext) {
		launcher.stop();
	}

}
