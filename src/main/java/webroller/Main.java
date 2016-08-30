package webroller;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class Main extends AbstractVerticle {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	private Webroller webroller;
	
	public void start() {
		LOGGER.info("Starting Webroller verticle...");
		webroller = new Webroller(vertx);
		webroller.start();
		LOGGER.info("Webroller started!");
	}
	
	public void stop() {
		LOGGER.info("Stopping Webroller verticle...");
		webroller.stop();
		LOGGER.info("Webroller stopped!");
	}

}
