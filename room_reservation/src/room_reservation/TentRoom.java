package room_reservation;

import java.io.Serializable;

public class TentRoom extends Room implements Serializable{

	public TentRoom(String roomId, String roomName, int capacity, String description) {
		super(roomId, roomName, capacity, description);
	}

	
}
