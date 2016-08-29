package webroller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import static webroller.TestConstants.ADMIN_USER;
import static webroller.TestConstants.TEST_USER;

import org.junit.Test;

public class RoomTest {
	
	@Test
	public void nameIsUsedAsKeyForPublicRooms() {
		String name = "Test Room";
		Room testRoom = new Room(name, "This is a test room.", false, ADMIN_USER);
		assertEquals(name, testRoom.key);
	}
	
	@Test
	public void keyIsGeneratedForPrivateRooms() {
		String name = "Test Room";
		Room testRoom = new Room(name, "This is a test room.", true, ADMIN_USER);
		assertNotEquals(name, testRoom.key);
	}
	
	@Test
	public void userGetsAddedOnlyOnce() {
		String name = "Test Room";
		Room testRoom = new Room(name, "This is a test room.", false, ADMIN_USER);
		assertTrue("Should add user successfully", testRoom.addUser(TEST_USER));
		assertFalse("Should fail to add user a second time", testRoom.addUser(TEST_USER));
	}
	
	@Test
	public void userGetsRemoved() {
		String name = "Test Room";
		Room testRoom = new Room(name, "This is a test room.", false, ADMIN_USER);
		assertTrue("Should add user successfully", testRoom.addUser(TEST_USER));
		assertTrue("Should remove user successfully", testRoom.removeUser(TEST_USER));
	}

}
