package webroller;

import static webroller.Constants.JSON_CONTENT_TYPE;


import io.vertx.core.AbstractVerticle;
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

public class Main extends AbstractVerticle {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	private HttpServer server;
	
	public void start() {
		server = vertx.createHttpServer();
		
		Router router = Router.router(vertx);
		router.route().handler(CookieHandler.create());
		router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
		router.route().handler(BodyHandler.create());
		context.put(RoomDirectory.CONTEXT_KEY, new RoomDirectory());

		router.get("/rooms")
			.handler(new RoomListingHandler())
			.produces(JSON_CONTENT_TYPE);
		router.post("/rooms")
			.handler(new RoomCreationHandler())
			.consumes(JSON_CONTENT_TYPE)
			.produces(JSON_CONTENT_TYPE);

		final int port = 8080;
		server.requestHandler(router::accept).listen(port);
		LOGGER.info("HTTP server started on port: " + port);
	}
	
	public void stop() {
		server.close();
		LOGGER.info("HTTP server closed.");
	}

}
