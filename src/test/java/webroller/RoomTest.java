package webroller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import static webroller.TestConstants.ADMIN_USER;
import static webroller.TestConstants.SECRET_TEST_ROOM;
import static webroller.TestConstants.TEST_ROOM;
import static webroller.TestConstants.TEST_USER;

import org.junit.Test;

public class RoomTest {
	
	@Test
	public void nameIsUsedAsKeyForPublicRooms() {
		assertEquals(TEST_ROOM.name, TEST_ROOM.key);
	}
	
	@Test
	public void keyIsGeneratedForPrivateRooms() {
		assertNotEquals(SECRET_TEST_ROOM.name, SECRET_TEST_ROOM.key);
	}
	
	@Test
	public void userGetsAddedOnlyOnce() {
		Room testRoom = new Room("Test Room", "This is a test room.", false, ADMIN_USER);
		assertTrue("Should add user successfully", testRoom.addUser(TEST_USER));
		assertFalse("Should fail to add user a second time", testRoom.addUser(TEST_USER));
	}
	
	@Test
	public void userGetsRemoved() {
		Room testRoom = new Room("Test Room", "This is a test room.", false, ADMIN_USER);
		assertTrue("Should add user successfully", testRoom.addUser(TEST_USER));
		assertTrue("Should remove user successfully", testRoom.removeUser(TEST_USER));
	}

}
