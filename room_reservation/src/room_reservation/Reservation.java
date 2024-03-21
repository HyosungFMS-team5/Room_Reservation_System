package room_reservation;

import java.io.Serializable;
import java.util.Date;
import lombok.*;

@Getter
@Setter
@ToString
public class Reservation implements Serializable{
	private String userId;
	private String roomId;
	private Date checkInDate;
	private Date checkOutDate;
	private int personCnt;
	private boolean isCanceled;
	private boolean isReviewed;
	
	public Reservation(String userId, String roomId, Date checkInDate, Date checkOutDate, int personCnt) {
		this.userId = userId;
		this.roomId = roomId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.personCnt = personCnt;
		this.isCanceled = false;
		this.isReviewed = false;
	}
}
