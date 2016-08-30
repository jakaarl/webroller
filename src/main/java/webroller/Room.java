package webroller;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Room {
	
	public final String name;
	public final String description;
	public final boolean secret;
	public final String key;
	
	private final Set<User> users = new HashSet<>();
	private User admin;
	
	public Room(String name, String description, boolean secret, User admin) {
		this.name = name;
		this.description = description;
		this.secret = secret;
		this.admin = admin;
		if (secret) {
			key = UUID.randomUUID().toString();
		} else {
			key = name;
		}
	}
	
	public User getAdmin() {
		return admin;
	}
	
	public void setAdmin(User admin) {
		this.admin = admin;
	}
	
	public Set<User> getUsers() {
		return users;
	}
	
	public boolean addUser(User user) {
		return users.add(user);
	}
	
	public boolean removeUser(User user) {
		return users.remove(user);
	}

}
