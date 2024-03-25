package room_reservation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import lombok.*;

@Getter
@Setter
@ToString(exclude = {"checkInDate", "checkOutDate"})
public class Reservation implements Serializable{
	private String reservationId;
	private String userId;
	private String roomId;
	private Date checkInDate;
	private Date checkOutDate;
	private int personCnt;
	private int price;
	private boolean isCanceled;
	private boolean isReviewed;
	private List<String> reserveDateList;
	
	public Reservation(String reservationId,String userId, String roomId, Date checkInDate, Date checkOutDate, int personCnt, int price, List<String> reserveDateList) {
		this.reservationId = reservationId;
		this.userId = userId;
		this.roomId = roomId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.personCnt = personCnt;
		this.isCanceled = false;
		this.isReviewed = false;
		this.price = price;
		this.reserveDateList = reserveDateList;
	}
}
