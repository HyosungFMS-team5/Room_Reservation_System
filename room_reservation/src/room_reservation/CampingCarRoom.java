package room_reservation;

import java.io.Serializable;

public class CampingCarRoom extends Room implements Serializable{

	public CampingCarRoom(String roomId, String roomName, int baseCapacity, int capacity,  String description, int basePrice) {
		super(roomId, roomName, baseCapacity, capacity, description, basePrice);
		super.setAdditionalPrice(10000);
	}

	// 방정보 보여주기
	public void showRoomInfo() {
		System.out.print("캠핑카형 / ");
		super.showRoomInfo();
	}
	
	@Override
	public void showPrecaution() {
		System.out.println("4. 현관문이 열려있으면 소음으로 인한 민원이 발생할 수 있습니다.");
		System.out.println("5. 내부 쓰레기통에 정확히 분리수거해주시길 바랍니다.");
	};

	// @Override
	// public static void showTypeInfo(){
	// 	System.out.println("3. 반려동물 동반 가능");
	// 	System.out.println("4. 내부 샤워실, 화장실 및 주방 보유");
	// };

}
