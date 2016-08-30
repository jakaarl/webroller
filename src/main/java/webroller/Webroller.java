package webroller;

import static webroller.Constants.JSON_CONTENT_TYPE;

import io.vertx.core.AsyncResult;
import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import webroller.handlers.RoomCreationHandler;
import webroller.handlers.RoomListingHandler;

public class Webroller {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Webroller.class);
	private static final String PORT_ENV_KEY = "PORT";
	private static final String PORT_PROPERTY_KEY = "webroller.port";
	private static final int DEFAULT_PORT = 8080;
	
	private final Vertx vertx;
	private final int port;
	private final Router router;
	private final RoomDirectory roomDirectory;
	
	private HttpServer server;
	
	public Webroller(Vertx vertx) {
		this(vertx, configuredPort(), new RoomDirectory());
	}
	
	Webroller(Vertx vertx, int port, RoomDirectory roomDirectory) {
		this.vertx = vertx;
		this.port = port;
		this.router = Router.router(vertx);
		this.roomDirectory = roomDirectory;
	}
	
	private void initialize() {
		initializeContext();
		initializeRouting();
	}
	
	private void initializeContext() {
		Context context = vertx.getOrCreateContext();
		context.put(RoomDirectory.CONTEXT_KEY, roomDirectory);
	}
	
	private void initializeRouting() {
		router.route().handler(CookieHandler.create());
		router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
		router.route().handler(BodyHandler.create());

		router.get("/rooms")
			.handler(new RoomListingHandler())
			.produces(JSON_CONTENT_TYPE);
		router.post("/rooms")
			.handler(new RoomCreationHandler())
			.consumes(JSON_CONTENT_TYPE)
			.produces(JSON_CONTENT_TYPE);
	}
	
	protected void start(Handler<AsyncResult<HttpServer>> callback) {
		initialize();
		server = vertx.createHttpServer();
		server.requestHandler(router::accept).listen(port, callback);
	}
	
	public void start() {
		start(result -> {
			if (result.succeeded()) {
				LOGGER.info("HTTP server started on port: " + result.result().actualPort());
			} else {
				throw new IllegalStateException("Failed to start HTTP server", result.cause());
			}
		});
	}
	
	public void stop() {
		server.close();
		LOGGER.info("HTTP server stopped.");
	}
	
	public int getServerPort() {
		return server.actualPort();
	}
	
	static int configuredPort() {
		String portVar = System.getenv(PORT_ENV_KEY);
		if (portVar == null) {
			portVar = System.getProperty(PORT_PROPERTY_KEY, String.valueOf(DEFAULT_PORT));
		}
		return Integer.parseInt(portVar);
	}

}
