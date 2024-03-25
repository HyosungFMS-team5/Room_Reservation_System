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
public abstract class Room implements Serializable {
	private String roomId; 
	private String roomName;
	private int baseCapacity;
	private int capacity;
	private String description;
	private int basePrice;
	private int additionalPrice;
	private Map<String, Boolean> bookedDate;
	
	public Room(String roomId, String roomName, int baseCapacity, int capacity,  String description, int basePrice) {
		this.roomId = roomId;
		this.roomName = roomName;
		this.baseCapacity = baseCapacity;
		this.capacity = capacity;
		this.description = description;
		this.basePrice = basePrice;
		this.bookedDate = new HashMap<>();
	}

	// 추가요금 합산 총요금
	public int getTotalPrice(int personNumber) {
		System.out.printf("추가 1인당 %d원의 요금이 추가됩니다.\n", additionalPrice);

		int totalAdditionalPrcie = personNumber > baseCapacity ? additionalPrice * (personNumber - baseCapacity) : 0;
			
		return basePrice + totalAdditionalPrcie;
	};

	// 방정보 보여주기
	public void showRoomInfo() {
		System.out.printf("%s번 방 / %s\n", roomId, roomName);
		System.out.printf("%d원 / 기준 %d인(최대 : %d인)\n", basePrice, baseCapacity, capacity);
	}

	// 시설 이용 시 주의사항
	public void showPrecaution() {
		System.out.println("----------숙소 이용 시 주의 사항----------");
		System.out.println("1. 체크인 시간은 15시, 체크아웃 시간은 11시입니다. 체크인 시간과 체크아웃 시간을 지켜주세요.");
		System.out.println("2. 실내 흡연은 금지입니다. 흡연장을 이용해주세요.");
		System.out.println("3. 내부 시설은 저희의 자산입니다. 깨끗하게 사용해주세요.");
	};

	public void showTypeInfo(){
		System.out.println("----------시설 이용 시 안내사항----------");
		System.out.println("1. 바베큐 시설 이용 가능(이용시 50,000원 추가)");
		System.out.println("2. 침구류 더 필요할 시 관리실에 문의");
	};
}
