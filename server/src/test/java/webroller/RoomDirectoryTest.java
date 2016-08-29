package webroller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static webroller.TestConstants.ADMIN_USER;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RoomDirectoryTest {
	
	@Rule
	public final ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void publicRoomsExcludesSecretRooms() {
		RoomDirectory directory = new RoomDirectory();
		Room room = new Room("test", "test", true, ADMIN_USER);
		directory.addRoom(room);
		assertFalse(directory.publicRooms().contains(room));
	}
	
	public void publicRoomsIncludesNonSecretRooms() {
		RoomDirectory directory = new RoomDirectory();
		Room room = new Room("test", "test", false, ADMIN_USER);
		directory.addRoom(room);
		assertTrue(directory.publicRooms().contains(room));
	}
	
	@Test
	public void getRoomThrowsOnInvalidKey() {
		RoomDirectory directory = new RoomDirectory();
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("No room by key");
		directory.getRoom("non-existent room");
	}
	
	@Test
	public void addRoomThrowsWhenMaximumReached() {
		RoomDirectory directory = new RoomDirectory(0);
		Room room = new Room("test", "test", true, ADMIN_USER);
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("Maximum number of rooms reached");
		directory.addRoom(room);
	}

}
