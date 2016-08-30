package webroller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static webroller.TestConstants.SECRET_TEST_ROOM;
import static webroller.TestConstants.TEST_ROOM;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RoomDirectoryTest {
	
	@Rule
	public final ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void publicRoomsExcludesSecretRooms() {
		RoomDirectory directory = new RoomDirectory();
		directory.addRoom(SECRET_TEST_ROOM);
		assertFalse(directory.publicRooms().contains(SECRET_TEST_ROOM));
	}
	
	public void publicRoomsIncludesNonSecretRooms() {
		RoomDirectory directory = new RoomDirectory();
		directory.addRoom(TEST_ROOM);
		assertTrue(directory.publicRooms().contains(TEST_ROOM));
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
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("Maximum number of rooms reached");
		directory.addRoom(TEST_ROOM);
	}
	
	@Test
	public void removeRoomThrowsWhenNoSuchRoom() {
		RoomDirectory directory = new RoomDirectory();
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("No room by key");
		directory.removeRoom(TEST_ROOM);
	}

}
