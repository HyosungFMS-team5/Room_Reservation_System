package room_reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class Room {
	private String roomId; 
	private String roomName;
	private int capacity;
	private String description;
}
