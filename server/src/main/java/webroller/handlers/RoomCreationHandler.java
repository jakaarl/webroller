package webroller.handlers;

import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import webroller.Room;
import webroller.RoomDirectory;
import webroller.User;
import webroller.json.JsonConverter;

public class RoomCreationHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext context) {
		JsonObject json = context.getBodyAsJson();
		JsonObject roomJson = json.getJsonObject("room");
		JsonObject adminJson = roomJson.getJsonObject("admin");
		Room room = new Room(
				roomJson.getString("name"),
				roomJson.getString("description"),
				roomJson.getBoolean("secret", false),
				new User(adminJson.getString("nick"), adminJson.getString("name")));
		Context vertxContext = context.vertx().getOrCreateContext();
		RoomDirectory directory = vertxContext.get(RoomDirectory.CONTEXT_KEY);
		directory.addRoom(room);
		context.response().end(JsonConverter.json(room).encode());
	}

}
