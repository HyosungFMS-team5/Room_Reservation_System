package room_reservation;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Review implements Serializable{
	private String reservationId;
	private String userId;
	private String roomId;
	private int score;
	private String content;
	
}
