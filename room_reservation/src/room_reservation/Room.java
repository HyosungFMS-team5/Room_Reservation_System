package room_reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Room {
	public String roomId; 
	public String roomName;
	public int capacity;
	public String description;
}
