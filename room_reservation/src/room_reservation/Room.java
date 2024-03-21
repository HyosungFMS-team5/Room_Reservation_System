package room_reservation;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public abstract class Room implements Serializable {
	private String roomId; 
	private String roomName;
	private int capacity;
	private String description;
	private Map<Date, Boolean> bookedDate;  // 날짜가 키에 존재하면 불가능한 날짜, 없으면 가능한 날짜
	
	public Room(String roomId, String roomName, int capacity, String description) {
		this.roomId = roomId;
		this.roomName = roomName;
		this.capacity = capacity;
		this.description = description;
		this.bookedDate = new HashMap<>();
	}
}
