package webroller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class RoomDirectory {
	
	public static final String CONTEXT_KEY = "ROOM_DIRECTORY";
	private static final int MAX_ROOMS_ALLOWED = 255;
	
	private final int maxRoomsAllowed;
	private final Map<String, Room> rooms;
	
	public RoomDirectory() {
		this(MAX_ROOMS_ALLOWED);
	}
	
	protected RoomDirectory(int maxRoomsAllowed) {
		this.maxRoomsAllowed = maxRoomsAllowed;
		this.rooms = new TreeMap<>();
	}
	
	public Collection<Room> getRooms() {
		return rooms.values();
	}
	
	public List<Room> publicRooms() {
		return rooms.values().stream().filter(room -> !room.secret).collect(Collectors.toList());
	}
	
	public Room getRoom(String key) {
		Room room = rooms.get(key);
		if (room == null) {
			throw new IllegalArgumentException("No room by key: " + key);
		}
		return room;
	}
	
	public void addRoom(Room room) {
		if (rooms.size() >= maxRoomsAllowed) {
			throw new IllegalStateException("Maximum number of rooms reached");
		}
		rooms.put(room.key, room);
	}
	
	public void removeRoom(Room room) {
		if (!rooms.containsKey(room.key)) {
			throw new IllegalArgumentException("No room by key: " + room.key);
		}
		rooms.remove(room.key);
	}

}
