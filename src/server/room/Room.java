package server.room;

import java.util.ArrayList;
import java.util.List;

import server.ServerClient;

public class Room {

	private List<ServerClient> userList;
	private String roomTitle;
	
	public Room(String roomTitle) {
		this.roomTitle = roomTitle;
		userList = new ArrayList<>();
	}
	
	public List<ServerClient> getUserList() {
		return userList;
	}

	public void setUserList(List<ServerClient> userList) {
		this.userList = userList;
	}

	public String getRoomTitle() {
		return roomTitle;
	}
	
	public void EnterRoom(ServerClient user) {
		System.out.println("user입장");
		userList.add(user);
	}
	
	public void ExitRoom(ServerClient user) {
		if (userList.size() > 0)
			userList.remove(user);
	}
	

}
