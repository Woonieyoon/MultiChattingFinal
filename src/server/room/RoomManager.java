package server.room;

import java.util.ArrayList;
import java.util.List;

public class RoomManager {
	
	private List<Room> roomList = new ArrayList<>();

	public RoomManager() {
		roomList = new ArrayList<Room>();
	}
	
	public List<Room> getRoomList() {
		return roomList;
	}

	public Room createRoom(String title) {
		Room room = new Room(title);
		roomList.add(room);
		System.out.println("Room Create!!");
		return room;
	}
	
	public void RemoveRoom(Room room) {
		roomList.remove(room);
		System.out.println("Room Remove");
	}
	
	

}
