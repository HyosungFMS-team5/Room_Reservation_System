package room_reservation;

import java.io.Serializable;

public class CampingCarRoom extends Room implements Serializable{

	public CampingCarRoom(String roomId, String roomName, int capacity, String description) {
		super(roomId, roomName, capacity, description);
	}

}
