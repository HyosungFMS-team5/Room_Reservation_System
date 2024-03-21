package room_reservation;

import java.util.Map;
import java.util.Scanner;

public class ReservationSystem {
	private Scanner sc;
	private FileIO fileIO;
	
	
	public ReservationSystem(Scanner sc, FileIO fileIO) {
		this.sc = sc;
		this.fileIO = fileIO;
	}
	
	
	// 실행 함수
	public void run() {
		
		while (true) {
			System.out.println("1. 펜션 목록 보기 | 2. 펜션 예약하기 | 3. 예약 내역 조회 | 4. 리뷰 작성하기 | 5. 뒤로가기 ");
			String choice = sc.nextLine();
			switch (choice) {
			case "1": {
				ShowAllRoom();
				break;
			}
			case "2": {
				break;
			}
			case "3": {
				break;
			}
			case "4": {
				break;
			}
			case "5": {
				return;
			}
			default:
				
			}
		}
	}
	
	
	//  전체 방 조회 + 상세조회 입력 or 뒤로가기
	private void ShowAllRoom() {
		Map<String, Room> roomMap = fileIO.roomLoad();
		
		while (true) {
			for (Map.Entry<String, Room> e : roomMap.entrySet()) {
				// 출력형식 수정해주세요.
				System.out.println(e.getKey() + " : " + e.getValue().toString());
			}
			
			System.out.println("상세 조회 원하시는 방 번호를 입력하세요. | 0. 뒤로가기");
			String inputRoomId = sc.nextLine();
			
			if (inputRoomId.equals("0")) return ;
			
			if (!roomMap.containsKey(inputRoomId)) {
				System.out.println("해당하는 방번호의 방은 없습니다. ");
			} else {
				showRoomDetail(roomMap.get(inputRoomId));
			}
			
		}
		
	}
	
	// 방 상세 조회 
	private void showRoomDetail(Room room) {
		System.out.println(room.getRoomId() + "번 방 상세 내역");
		System.out.println("===========================================");
		System.out.println("방 이름 : " + room.getRoomName());
		System.out.println("최대 수용인원 : " + room.getCapacity());
		System.out.println("방 상세 설명 : " + room.getDescription());
		System.out.println("===========================================");
		System.out.println("0.뒤로가기");
		// TODO: 리뷰 내역뽑혀야함
		String back = null;
		while (back == null) {
			back = sc.nextLine();
		}
	}
	
	
	
	
	
	
	
	
	
	
}
