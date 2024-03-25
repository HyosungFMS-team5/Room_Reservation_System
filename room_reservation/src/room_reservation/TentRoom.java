package room_reservation;

import java.io.Serializable;

public class TentRoom extends Room implements Serializable{

	public TentRoom(String roomId, String roomName, int baseCapacity, int capacity,  String description, int basePrice) {
		super(roomId, roomName, baseCapacity, capacity, description, basePrice);
		super.setAdditionalPrice(20000);
	}

	public void showRoomInfo() {
		System.out.print("텐트형 / ");
		super.showRoomInfo();
	}

	@Override
	public void showPrecaution() {
		super.showPrecaution();
		System.out.println("4. 10시 이후에는 소음 발생에 주의해주세요.");
		System.out.println("5. 쓰레기 처리는 분리수거장을 이용해주세요.");
		System.out.println("6. 가스 난로를 사용 중이므로 환기에 주의해주시길 바랍니다.");
	};

	// @Override
	// static public void showTypeInfo(){
	// 	super.showTypeInfo();
	// 	System.out.println("3. 반려동물 동반 불가");
	// 	System.out.println("4. 공용 샤워실, 공용 화장실 및 공유 주방 이용");
	// };
	
}
