package webroller;

public class TestConstants {
	
	public static final User ADMIN_USER = new User("keeper", "The Keeper of Arcane Lore");
	public static final User TEST_USER = new User("test", "Test User");
	public static final Room TEST_ROOM = new Room("testroom", "Test Room", false, ADMIN_USER);
	public static final Room SECRET_TEST_ROOM = new Room("secrettest", "Secret Test Room", true, ADMIN_USER);
	
	private TestConstants() {}

}
