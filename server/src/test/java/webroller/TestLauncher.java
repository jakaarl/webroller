package webroller;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class TestLauncher {
	
	private final Webroller webroller;
	
	public TestLauncher(Vertx vertx) {
		this(vertx, new RoomDirectory());
	}
	
	public TestLauncher(Vertx vertx, RoomDirectory roomDirectory) {
		this.webroller = new Webroller(vertx, 0, roomDirectory);
	}
	
	public void start(Handler<AsyncResult<HttpServer>> callback) {
		webroller.start(callback);
	}
	
	public int getPort() {
		return webroller.getServerPort();
	}
	
	public void stop() {
		webroller.stop();
	}

}
