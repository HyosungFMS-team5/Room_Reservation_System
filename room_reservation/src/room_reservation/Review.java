package room_reservation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {
	private String userId;
	private String roomId;
	private double score;
	private String content;
	
	public Review(String userId, String roomId, String content) {
		this.userId = userId;
		this.roomId = roomId;
		this.content = content;
		this.score = 0;
	}
	
}
