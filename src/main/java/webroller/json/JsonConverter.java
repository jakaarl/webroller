package webroller.json;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import webroller.Room;
import webroller.RoomDirectory;
import webroller.User;

public class JsonConverter {
	
	public static JsonObject json(Room room) {
		JsonObject json = new JsonObject();
		json.put("name", room.name);
		json.put("description", room.description);
		json.put("userCount", room.getUsers().size() + 1); // +1 because there's always an admin
		json.put("admin", json(room.getAdmin()));
		return json;
	}
	
	public static JsonObject json(RoomDirectory roomDirectory) {
		JsonObject json = new JsonObject();
		JsonArray rooms = new JsonArray();
		for (Room room : roomDirectory.publicRooms()) {
			rooms.add(json(room));
		}
		json.put("rooms", rooms);
		return json;
	}
	
	public static JsonObject json(User user) {
		JsonObject json = new JsonObject();
		json.put("nick", user.nick);
		json.put("name", user.fullName);
		return json;
	}
	
	public static User user(JsonObject userJson) {
		return new User(userJson.getString("nick"), userJson.getString("name"));
	}
	
	public static Room room(JsonObject roomJson) {
		User admin = user(roomJson.getJsonObject("admin"));
		return new Room(
				roomJson.getString("name"),
				roomJson.getString("description"),
				roomJson.getBoolean("secret"),
				admin);
	}

}
