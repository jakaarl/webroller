package webroller.handlers;

import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import webroller.RoomDirectory;
import webroller.json.JsonConverter;

public class RoomListingHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext context) {
		Context vertxContext = context.vertx().getOrCreateContext();
		RoomDirectory directory = vertxContext.get(RoomDirectory.CONTEXT_KEY);
		JsonObject listing = JsonConverter.json(directory);
		context.response().end(listing.encode());
	}
	
}
