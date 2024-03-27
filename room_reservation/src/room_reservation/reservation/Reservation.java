package room_reservation.reservation;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import consolemethod.ConsoleMethod;
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

	public void showInfo() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if(isCanceled) {
				System.out.println(ConsoleMethod.FONT_RED + "예약번호 : " + reservationId + " (관리자에 의해 취소된 예약입니다.)" + ConsoleMethod.RESET);
			} else {
				System.out.println("예약번호 : " + reservationId);
    	}
		System.out.println();

		System.out.printf("예약자 : %s | 방번호 : %s | 숙박인원 : %d\n", userId, roomId, personCnt);
		System.out.printf("체크인 : %s | 체크아웃 : %s\n",dateFormat.format(checkInDate), dateFormat.format(checkOutDate));
		System.out.printf("리뷰 작성 여부 : %s\n", isReviewed() ? "O" : "X");
		System.out.println("----------------------------------------");
	}

}
