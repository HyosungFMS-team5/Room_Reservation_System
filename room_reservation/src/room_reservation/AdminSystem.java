package room_reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

import consolemethod.ConsoleMethod;
import fileio.FileIO;
import room_reservation.admin.Admin;
import room_reservation.reservation.Reservation;
import room_reservation.reservation.Review;
import room_reservation.room.CampingCarRoom;
import room_reservation.room.Room;
import room_reservation.room.TentRoom;
import room_reservation.user.User;

import java.time.LocalDate;
import java.time.ZoneId;

public class AdminSystem {

	Scanner sc;
	FileIO fileIO;
	boolean loggedIn;
	Map<String, Room> roomMap;
	private Map<Integer, String> scoreMap;

	public AdminSystem(Scanner sc, FileIO fileIO) {
		this.sc = sc;
		this.fileIO = fileIO;
		roomMap = fileIO.roomLoad();
		this.scoreMap = Map.ofEntries(Map.entry(1, ConsoleMethod.FONT_YELLOW + "★" + ConsoleMethod.RESET),
				Map.entry(2, ConsoleMethod.FONT_YELLOW + "★ ★" + ConsoleMethod.RESET),
				Map.entry(3, ConsoleMethod.FONT_YELLOW + "★ ★ ★" + ConsoleMethod.RESET),
				Map.entry(4, ConsoleMethod.FONT_YELLOW + "★ ★ ★ ★" + ConsoleMethod.RESET),
				Map.entry(5, ConsoleMethod.FONT_YELLOW + "★ ★ ★ ★ ★" + ConsoleMethod.RESET));
	}

	// 실행 함수
	public void run() {
		while (true) {
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
		while (true) {
			System.out.println("1.방 목록 보기 | 2.방 추가 | 3.방 정보 수정 | 4.방 삭제 | 5.예약 내역 조회 | 6. 예약 내역 취소 | "
					+ ConsoleMethod.FONT_PURPLE + "0. 뒤로 가기" + ConsoleMethod.RESET);
			System.out.println(
					"-----------------------------------------------------------------------------------------------");

			String choice = sc.nextLine();
			switch (choice) {
			case "1":
				showAllRoom();
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
				System.out.println(ConsoleMethod.FONT_RED + "잘못된 입력입니다." + ConsoleMethod.RESET);
				break;
			}
			System.out.println(
					"=================================================================================================");
			System.out.println();
		}

	}

	// 관리자 로그인
	private void login() {
		System.out.println("관리자 패스워드를 입력하세요");
		String inputPW = sc.nextLine();
		if (inputPW.equals(Admin.adminPW)) {
			loggedIn = true;
			System.out.println();
			System.out.println(ConsoleMethod.FONT_GREEN + "관리자 인증이 완료되었습니다." + ConsoleMethod.RESET);
			System.out.println();
		} else {
			System.out.println(ConsoleMethod.FONT_RED + "관리자 로그인 실패 : 패스워드를 정확하게 입력해주세요." + ConsoleMethod.RESET);
			System.out.println();
		}

	}

//	 방 목록 보여주기
	private void showList() {
		System.out.println(ConsoleMethod.BACKGROUND_CYAN + ConsoleMethod.FONT_BLACK
				+ "                    방 리스트                    " + ConsoleMethod.RESET);
		System.out.println();

		for (Map.Entry<String, Room> e : roomMap.entrySet()) {
			// 출력형식 수정해주세요.
			e.getValue().showRoomInfo();
			System.out.println();

		}
	}

	private void showAllRoom() {
		Map<String, Room> roomMap = fileIO.roomLoad();

		TreeSet<String> sortedKeys = new TreeSet<>(roomMap.keySet());

		while (true) {

			System.out.println(ConsoleMethod.BACKGROUND_CYAN + "                    전체 숙소 목록(" + roomMap.size()
					+ ")                    " + ConsoleMethod.RESET);
			System.out.println();
			for (String key : sortedKeys) {
				System.out.println();
				roomMap.get(key).showRoomInfo();
			}

			System.out.println(
					"상세 조회 원하시는 방 번호를 입력하세요. | " + ConsoleMethod.FONT_PURPLE + " 0. 뒤로가기" + ConsoleMethod.RESET);

			String inputRoomId = sc.nextLine();

			if (inputRoomId.equals("0"))
				return;

			if (!roomMap.containsKey(inputRoomId)) {
				System.out.println(ConsoleMethod.FONT_RED + "해당하는 방번호의 방은 없습니다. " + ConsoleMethod.RESET);
			} else {
				showRoomDetail(roomMap.get(inputRoomId));
				return;
			}

		}

	}

	// 방 상세 조회
	private void showRoomDetail(Room room) {
		Map<String, Review> reviewMap = fileIO.reviewLoad();
		List<Review> sameRoomIdReviewList = new ArrayList<>();

		for (Map.Entry<String, Review> review : reviewMap.entrySet()) {
			if (review.getValue().getRoomId().equals(room.getRoomId())) {
				sameRoomIdReviewList.add(review.getValue());
			}
		}

		System.out.println(ConsoleMethod.BACKGROUND_CYAN + "                    " + room.getRoomId()
				+ "번 방 상세 내역                    " + ConsoleMethod.RESET);
		ConsoleMethod.roomDetail(Integer.parseInt(room.getRoomId()));
		System.out.println();
		System.out.println("방 이름 : " + room.getRoomName());
		System.out.println("최대 수용인원 : " + room.getCapacity());
		System.out.println("방 상세 설명 : " + room.getDescription());
		System.out.println();
		System.out.println("******************** 후기(" + sameRoomIdReviewList.size() + ") ********************");

		if (sameRoomIdReviewList.size() == 0) {
			System.out.println();
			System.out.println("                   아직 작성된 후기가 없습니다.        ");
			System.out.println();
		} else {
			for (Review review : sameRoomIdReviewList) {
				System.out.println();
				System.out.println(review.getRoomId() + "번 방");
				System.out.println(review.getUserId() + "님의 리뷰 - " + scoreMap.get(review.getScore()));
				System.out.println("후기 : " + review.getContent());
				System.out.println();
				System.out.println("----------------------------------------");
			}
		}

		System.out.println("**************************************************");
		System.out.println("==================================================");
		System.out.println(ConsoleMethod.FONT_PURPLE + "0. 뒤로가기" + ConsoleMethod.RESET);

		String back = null;
		while (back == null) {
			back = sc.nextLine();
		}
	}

	// 2. 방 추가
	private void addRoom() {
		System.out.println(ConsoleMethod.BACKGROUND_CYAN + ConsoleMethod.FONT_BLACK
				+ "                    방 추가                    " + ConsoleMethod.RESET);
		// 방 정보 입력
		System.out.println("방ID를 입력해주세요." + ConsoleMethod.FONT_PURPLE + "(0: 취소)" + ConsoleMethod.RESET);

		String roomId = null;
		while (true) {
			roomId = sc.nextLine();
			if (!roomMap.containsKey(roomId))
				break;
			System.out.println(ConsoleMethod.FONT_RED + "이미 존재하는 방 ID입니다." + ConsoleMethod.RESET);
		}

		if (roomId.equals("0")) {
			System.out.println(ConsoleMethod.FONT_PURPLE + "취소하셨습니다." + ConsoleMethod.RESET);
			return;
		}

		String roomName = inputString("방 이름");
		if (roomName == null)
			return;
		int baseCapacity = inputNumber("기준 숙박 인원");
		if (baseCapacity == 0)
			return;
		int capacity = inputNumber("최대 숙박 인원");
		if (capacity == 0)
			return;
		String description = inputString("방 상세 설명");
		if (description == null)
			return;
		int price = inputNumber("기준 가격");
		if (price == 0)
			return;
		// 룸타입 결정
		Room room = null;
		while (room == null) {
			System.out.println("방 종류를 정해주세요.");
			System.out.println("1.텐트형 | 2.캠핑카형 | " + ConsoleMethod.FONT_PURPLE + "0.취소" + ConsoleMethod.RESET);
			String roomType = sc.nextLine();
			switch (roomType) {
			case "1":
				room = new TentRoom(roomId, roomName, baseCapacity, capacity, description, price);
				break;
			case "2":
				room = new CampingCarRoom(roomId, roomName, baseCapacity, capacity, description, price);
				break;
			case "0":
				System.out.println(ConsoleMethod.FONT_RED + "입력을 취소하셨습니다." + ConsoleMethod.RESET);
				return;
			default:
				System.out.println(ConsoleMethod.FONT_RED + "잘못된 입력입니다. 다시 시도해주십시오" + ConsoleMethod.RESET);
				break;
			}
		}

		roomMap.put(roomId, room);
		fileIO.roomSave(roomMap);
	}

	// 방 상세 조회
//	private void showRoomDetail(Room room) {
//		System.out.println(ConsoleMethod.BACKGROUND_CYAN + ConsoleMethod.FONT_BLACK + "                   "
//				+ room.getRoomId() + "번 방 상세 내역                    " + ConsoleMethod.RESET);
//		System.out.println();
//		room.showRoomInfo();
//
//		System.out.println(room.getDescription());
//
//	}

	// 방 정보 수정
	private void roomInfoChange() {
		showList();

		String roomId = inputRoomId("수정");
		
		if (roomId == null) return;

		if (roomMap.containsKey(roomId)) {
			Room room = roomMap.get(roomId);
			if (!roomDetailInfoChange(room))
				return;
			roomMap.put(roomId, room);
			fileIO.roomSave(roomMap);
		} else {
			System.out.println(ConsoleMethod.FONT_RED + "해당하는 방 정보가 없습니다." + ConsoleMethod.RESET);
		}

	}

	// 방 정보 삭제
	private void deleteRoomInfo() {
		showList();
		
		String roomId = inputRoomId("삭제");
		
		if (roomId == null) return;
		
		String deleteYN;

		if (roomMap.containsKey(roomId)) {
			System.out.println("정말로 방 정보를 삭제하시겠습니까? (y/n)");
			deleteYN = sc.nextLine();

			if (deleteYN.equals("y")) {
				roomMap.remove(roomId); // 방 삭제
				fileIO.roomSave(roomMap); // 변경된 방 정보를 파일에 저장
				System.out.println(ConsoleMethod.FONT_GREEN + "방 정보가 성공적으로 삭제되었습니다." + ConsoleMethod.RESET);
				showList(); // 변경된 방 목록 출력
			} else if (deleteYN.equals("n")) {
				return;
			} else {
				System.out.println(ConsoleMethod.FONT_RED + "잘못된 입력입니다." + ConsoleMethod.RESET);
			}

		} else {
			System.out.println(ConsoleMethod.FONT_RED + "해당하는 방 정보가 없습니다." + ConsoleMethod.RESET);
		}
	}

	// 예약 내역 조회
	private void showReservation() {
		System.out.println(ConsoleMethod.BACKGROUND_CYAN + ConsoleMethod.FONT_BLACK
				+ "                    예약 내역 조회                    " + ConsoleMethod.RESET);

		// 에약내역 파일 불러오면 예약 내역 조회가 된다.
		Map<String, Reservation> reservationMap = fileIO.reservationLoad();

		for (Map.Entry<String, Reservation> entry : reservationMap.entrySet()) {
			entry.getValue().showInfo();
		}
	}

	// 예약 취소
	public void cancelReservation() {

		showReservation();

		System.out.println(ConsoleMethod.BACKGROUND_CYAN + ConsoleMethod.FONT_BLACK
				+ "                    예약 내역 취소                    " + ConsoleMethod.RESET);

		Map<String, Reservation> reservationMap = fileIO.reservationLoad();

		while (true) {
			System.out
					.println("취소를 원하는 예약번호를 입력해주세요. |  " + ConsoleMethod.FONT_PURPLE + "0. 뒤로가기" + ConsoleMethod.RESET);
			String inputReservationId = sc.nextLine();

			if (inputReservationId.equals("0"))
				return;

			if (reservationMap.containsKey(inputReservationId)) {
				Reservation reservation = reservationMap.get(inputReservationId);
				// 날짜 지난 예약은 취소 불가

				if (reservation.getCheckOutDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
						.isBefore(LocalDate.now())) {
					System.out.println(
							ConsoleMethod.FONT_RED + "숙박 예정인 예약만 취소할 수 있습니다. 다시 선택해주세요." + ConsoleMethod.RESET);
					continue;
				}

				// 이미 취소 됐는지 확인
				if (reservation.isCanceled()) {
					System.out.println(ConsoleMethod.FONT_RED + "이미 취소된 예약입니다. 다시 선택해주세요." + ConsoleMethod.RESET);
					continue;
				}

				reservation.setCanceled(true);

				// User 클래스 내부 예약 내역 삭제
				String userId = reservationMap.get(inputReservationId).getUserId();
				Map<String, User> userMap = fileIO.userLoad();
				userMap.get(userId).getMyReservationMap().put(inputReservationId, reservation);
				fileIO.userSave(userMap);

				// Room 클래스 내부 예약 내역 삭제
				deleteBookedDate(reservationMap.get(inputReservationId));

				// 예약 내역 삭제
				fileIO.reservationSave(reservationMap);

				System.out.println(ConsoleMethod.FONT_GREEN + "예약이 정상적으로 취소되었습니다." + ConsoleMethod.RESET);
				return;
			} else {
				System.out.println(ConsoleMethod.FONT_RED + "해당하는 방 번호가 없습니다. " + ConsoleMethod.RESET);
			}

		}

	}

	// 방의 bookedDate에서 취소 날짜 지우기
	public void deleteBookedDate(Reservation reservation) {
		Map<String, Room> roomMap = fileIO.roomLoad();

		for (String date : reservation.getReserveDateList()) {
			roomMap.get(reservation.getRoomId()).getBookedDate().remove(date);
		}

		fileIO.roomSave(roomMap);
	}

	// 방 세부 정보 수정
	private boolean roomDetailInfoChange(Room room) {
		System.out.println(ConsoleMethod.BACKGROUND_CYAN + ConsoleMethod.FONT_BLACK
				+ "                    방 정보 수정                    " + ConsoleMethod.RESET);
		System.out.println("1.방 이름 / 2. 기준 인원 / 3. 최대 수용 인원 / 4. 방 상세 설명 / 5. 가격 / " + ConsoleMethod.FONT_PURPLE
				+ "0. 뒤로가기" + ConsoleMethod.RESET);

		boolean isChanged = true;

		String detailType = sc.nextLine();

		switch (detailType) {
		case "1":
			String inputName = inputString("수정할 방 이름");
			if (inputName == null)
				return false;
			room.setRoomName(inputName);
			break;
		case "2":
			int inputBaseCapacity = inputNumber("수정할 기준 인원");
			if (inputBaseCapacity == 0)
				return false;
			room.setBaseCapacity(inputBaseCapacity);
			break;
		case "3":
			int inputCapacity = inputNumber("수정할 최대 인원");
			if (inputCapacity == 0)
				return false;
			room.setCapacity(inputCapacity);
			break;
		case "4":
			String inputDescription = inputString("수정할 방 상세 설명");
			room.setDescription(inputDescription);
			break;
		case "5":
			int inputPrice = inputNumber("수정할 가격");
			if (inputPrice == 0)
				return false;
			room.setBasePrice(inputPrice);
			break;
		case "0":
			isChanged = false;
			System.out.println(ConsoleMethod.FONT_RED + "방 정보 수정을 취소합니다." + ConsoleMethod.RESET);
			break;
		default:
			System.out.println(ConsoleMethod.FONT_RED + "잘못된 입력입니다." + ConsoleMethod.RESET);
			break;
		}

		return isChanged;

	}

	// 선택할 방 입력
	private String inputRoomId(String content) {
		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println(content + "할 방 ID를 입력해주세요." + ConsoleMethod.FONT_PURPLE + "(0: 취소)" + ConsoleMethod.RESET);
		String input = sc.nextLine();
		
		if (input.equals("0")) {
			System.out.println(ConsoleMethod.FONT_GREEN + "입력을 취소하였습니다." + ConsoleMethod.RESET);
			input = null;
		}

		return input;
	}

	// 문자열 입력기
	private String inputString(String content) {
		System.out.println(content + "을/를 입력해주세요. " + ConsoleMethod.FONT_PURPLE + "(0: 취소)" + ConsoleMethod.RESET);
		String input = sc.nextLine();

		if (input.equals("0")) {
			System.out.println(ConsoleMethod.FONT_GREEN + "입력을 취소하였습니다." + ConsoleMethod.RESET);
			input = null;
		}

		return input;
	}

	// 숫자 입력기
	private int inputNumber(String content) {
		System.out.println(content + "을/를 입력해주세요. " + ConsoleMethod.FONT_PURPLE + "(0: 취소)" + ConsoleMethod.RESET);
		int input = 0;

		while (true) {
			try {
				input = Integer.parseInt(sc.nextLine());
				break;
			} catch (Exception e) {
				System.out.println("숫자를 입력해주세요.");
			}
		}

		return input;
	}

}