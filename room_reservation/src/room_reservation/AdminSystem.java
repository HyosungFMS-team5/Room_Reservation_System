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
			System.out.println("1.방 목록 보기 | 2.방 추가 | 3.방 정보 수정 | 4.방 삭제 | 5.예약 내역 조회 | 6. 예약 내역 취소 | 0. 뒤로 가기");
			String choice = sc.nextLine();
			switch (choice) {
			case "1":
				showAllRooms();
				break;
			case "2":
				addRoom();
				break;
			case "3":
				roomInfoChange();
				break;
			case "4":
				deleteRoomInfo();
				break;
			case "5":
				showReservation();
				break;
			case "6":
				cancelReservation();
				break;
			case "0":
				return;
			default:
				System.out.println("잘못된 입력입니다.");
				break;
			}
		}

	}
	
	// 방 세부 정보 수정
	private void roomDetailInfoChange(Room room) {
		System.out.println("수정할 사항 1. 최대 수용 인원 2. 방 상세 설명  3. 뒤로가기");
		String detailType = sc.nextLine();
		switch (detailType) {
		
		case "1": {
			System.out.println("변경 최대 수용 인원을 입력해주세요.");
			int changeCapacity = Integer.parseInt(sc.nextLine());
			room.setCapacity(changeCapacity);
			break;

		}
		case "2": {
			System.out.println("수정할 방 상세 설명에 대해 말해주세요.");
			String description = sc.nextLine();
			room.setDescription(description);
				}
		case "3": {
			return;
		}
		default:
			System.out.println("잘못된 입력입니다.");
			break;
		}
	}
	
	// 방 정보 수정
	private void roomInfoChange() {
	
		System.out.println("수정할 방 타입을 선택해주세요");
		System.out.println("1. TentRoom 2. CampingCarRoom");
		
		Map<String, Room> roomMap = fileIO.roomLoad();
		FileIO fileIO = new FileIO();
		
		// 방 타입 선택 roomType으로 받음
		String roomType = sc.nextLine();
		
		// 각 방을 순회하며 타입에 맞는 방의 세부 정보 수정
        for (Room room : roomMap.values()) {
            switch (roomType) {
                case "1":
                    if (room instanceof TentRoom) {
                        roomDetailInfoChange(room); // 세부 정보 수정 메서드 호출
                        fileIO.roomInfoChangeSave(roomMap); // 파일에 수정한 정보들 덮어써서 저장
                    }
                    break;
                case "2":
                    if (room instanceof CampingCarRoom) {
                        roomDetailInfoChange(room); // 세부 정보 수정 메서드 호출
                        fileIO.roomInfoChangeSave(roomMap); // 파일에 수정한 정보들 덮어써서 저장
                    }
                    break;
                default:
                    System.out.println("잘못된 입력입니다.");
                    return;
            }
        }
}

	// 삭제할 방 ID를 토대로 삭제하기
	private void deleteroomId(Map<String, Room> roomMap) {
		FileIO fileio = new FileIO();

		System.out.println("삭제할 룸 ID를 알려주세요.");
		String roomId = sc.nextLine();
		
		if(roomMap.containsKey(roomId)) {
			roomMap.remove(roomId);
			System.out.println("방 정보가 성공적으로 삭제되었습니다.");
			// 삭제한 것 파일에 반영 
			fileio.deleteRoomSaveFile(roomMap);
		}
		else {
			System.out.println("해당하는 방 정보가 없습니다.");
		}
		return;
	}
	
	// 방 정보 삭제
	private void deleteRoomInfo() {
		System.out.println("삭제할 방 타입을 선택해주세요");
		System.out.println("1. TentRoom 2. CampingCarRoom");
		
		// 삭제할 방 타입
		String roomType = sc.nextLine();
		
		Map<String, Room> roomMap = fileIO.roomLoad();
		
		switch (roomType) {
		case "1": {
			System.out.println("TentRoom 타입에 대한 삭제가 이루어집니다.");
			deleteroomId(roomMap);

		}
		case "2": {
			System.out.println("CampingCarRoom 타입에 대한 삭제가 이루어집니다.");
			deleteroomId(roomMap);
		}
		default:
			System.out.println("잘못된 입력입니다.");
			break;
		}

	}
	


	// 예약 내역 조회
	private void showReservation() {
		
	}
	
	// 예약 취소
	private void cancelReservation() {
		
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
