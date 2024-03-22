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
	// AdminSystem 방 정보 수정에서 (직렬화된 객체를 역직렬화할 때)클래스 버전 일치하지 않는 문제 발생으로 인한 버전 맞춰주는 코드
	private static final long serialVersionUID = 5332156671298028215L;

	private String roomId; 
	private String roomName;
	private int capacity;
	private String description;
//	private Map<Date, Boolean> bookedDate;  // 날짜가 키에 존재하면 불가능한 날짜, 없으면 가능한 날짜
	private Map<String, Boolean> bookedDate;
	private int getCapacity;
	private String getDescription;
	
	public Room(String roomId, String roomName, int capacity, String description) {
		this.roomId = roomId;
		this.roomName = roomName;
		this.capacity = capacity;
		this.description = description;
		this.bookedDate = new HashMap<>();
	}
}
