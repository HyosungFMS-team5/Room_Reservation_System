package room_reservation;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class User implements Serializable{
	private String userID; // pk
	private String userPW;
	private String phone;
	private Map<String,Reservation> myReservationMap;
}
