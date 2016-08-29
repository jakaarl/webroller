package webroller.json;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import webroller.Room;
import webroller.RoomDirectory;
import webroller.User;

public class JsonConverter {
	
	public static JsonObject toJson(Room room) {
		JsonObject json = new JsonObject();
		json.put("name", room.name);
		json.put("description", room.description);
		json.put("userCount", room.getUsers().size() + 1); // +1 because there's always an admin
		json.put("admin", toJson(room.getAdmin()));
		return json;
	}
	
	public static JsonObject toJson(RoomDirectory roomDirectory) {
		JsonObject json = new JsonObject();
		JsonArray rooms = new JsonArray();
		for (Room room : roomDirectory.publicRooms()) {
			rooms.add(toJson(room));
		}
		json.put("rooms", rooms);
		return json;
	}
	
	public static JsonObject toJson(User user) {
		JsonObject json = new JsonObject();
		json.put("nick", user.nick);
		json.put("name", user.fullName);
		return json;
	}
	
	public static User fromJson(JsonObject userJson) {
		return new User(userJson.getString("nick"), userJson.getString("name"));
	}
	
	public static Room fromJson(JsonObject roomJson, JsonObject adminJson) {
		User admin = fromJson(adminJson);
		return new Room(
				roomJson.getString("name"),
				roomJson.getString("description"),
				roomJson.getBoolean("secret", false),
				admin);
	}

}
