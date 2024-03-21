package room_reservation;

import java.security.KeyStore.Entry;
import java.util.Map;
import java.util.Scanner;

public class AdminSystem {
	Scanner sc;
	FileIO fileIO;
	boolean loggedIn;
	
	public AdminSystem(Scanner sc, FileIO fileIO) {
		this.sc = sc;
		this.fileIO = fileIO;
	}
	
	// 실행 함수
	public void run() {
		while(true) {
			if (!loggedIn) {
				login();
			} else {
				showMenu();
				break;
			}
		}
	}
	
	// 메뉴 보여주기
	private void showMenu() {
		while(true) {
			System.out.println("1.방 목록 보기 | 2.방 추가 | 3.방 정보 수정 | 4.방 정보 삭제 | 5.예약 내역 조회 | 6. 예약 내역 취소 | 0. 뒤로 가기");
			String choice = sc.nextLine();
			switch (choice) {
			case "1":
				showAllRooms();
				break;
			case "2":
				addRoom();
				break;
			case "3":
				
				break;
			case "4":
				
				break;
			case "5":
				
				break;
			case "6":
				
				break;
			case "0":
				return;
			default:
				System.out.println("잘못된 입력입니다.");
				break;
			}
		}

	}
	
	// 관리자 로그인
	private void login( ) {
		System.out.println("관리자 패스워드를 입력하세요");
		String inputPW = sc.nextLine();
		if (inputPW.equals(Admin.adminPW)) loggedIn = true;
		System.out.println("관리자 인증이 완료되었습니다.");
	}
	
	// 방 추가
	private void addRoom() {
		// 얘는 자동 생성으로 바꿔주세요
		System.out.println("방ID를 입력해주세요");
		String roomId = sc.nextLine();
		System.out.println("방 이름을 입력해주세요");
		String roomName = sc.nextLine();
		System.out.println("최대 숙박 정원 입력해주세요");
		int capacity = Integer.parseInt(sc.nextLine());
		// 숙박인원 예외 처리 추가해주세요.
		System.out.println("방 상세 설명을 입력해주세요");
		String description = sc.nextLine();
		// 룸타입 결정
		Room room = null;
		while(room == null) {
			System.out.println("방 종류를 정해주세요.");
			System.out.println("1.텐트형 | 2.캠핑카형 | 3.취소");
			String roomType = sc.nextLine();
			switch (roomType) {
			case "1": 
				room = new TentRoom(roomId, roomName, capacity, description);
				break;
			case "2": 
				room = new CampingCarRoom(roomId, roomName, capacity, description);
				break;
			case "3": 
				System.out.println("입력을 취소하셨습니다.");
				return;
			default:
				System.out.println("잘못된 입력입니다. 다시 시도해주십시오");
				break;
			}
		}

		
		fileIO.roomSave(room);
	}
	
	// 방 목록 조회
	private void showAllRooms() {
		Map<String, Room> roomMap = fileIO.roomLoad();
		for (Map.Entry<String, Room> e : roomMap.entrySet()) {
			// 출력형식 수정해주세요.
			System.out.println(e.getValue().toString());
		}
	}
	
	
}
