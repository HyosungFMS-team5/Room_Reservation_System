package room_reservation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {
	String userId;
	String roomId;
	double score;
	String content;
	
	public Review(String userId, String roomId, String content) {
		this.userId = userId;
		this.roomId = roomId;
		this.content = content;
		this.score = 0;
	}
	
}
