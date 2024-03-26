package room_reservation;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import console.print.ConsoleMethod;
import datecalc.util.DateCalc;
import lombok.Getter;

@Getter
public class UserSystem {
	private Scanner sc;
	private FileIO fileIO;
	private UserInputer userInputer;
	private User user;
	private String userId;
	private Map<String, User> userMap;
	private boolean loggedIn;
	
	public UserSystem(Scanner sc, FileIO fileIO) {
		this.sc = sc;
		this.fileIO = fileIO;
		this.userMap = fileIO.userLoad();
	}
	
	// 실행 함수
	public void run() {
		boolean isEnd = false;
		while(!isEnd) {
			isEnd = isLoggedIn()? showAfterLogin() : showBeforLogin();
			System.out.println();
		}
	}

	// 로그인 전 메뉴
	private boolean showBeforLogin() {
		boolean isEnd = false;

		System.out.println("1. 로그인 | 2. 회원가입 |" + ConsoleMethod.FONT_PURPLE + " 0. 뒤로 가기" + ConsoleMethod.RESET);
		System.out.println("---------------------------------------");
		System.out.println();

		String choice = sc.nextLine();
		System.out.println();
		
		switch (choice) {
			case "1":
				login();
			break;
			case "2":
				signUp();
				break;
			case "0":
				isEnd = true;
				break;
			default:
				System.out.println(ConsoleMethod.FONT_RED + "잘못된 입력입니다." + ConsoleMethod.RESET);
				break;
		}

		System.out.println();

		return isEnd;
	}

	// 로그인 후 메뉴
	private boolean showAfterLogin() {
		boolean isEnd = false;

		System.out.println("1. 펜션 메뉴로 이동 | 2. 로그아웃 | 3. 전화번호 수정 | 4. 비밀번호 수정 | "  + ConsoleMethod.FONT_RED + "5. 회원탈퇴"  + ConsoleMethod.RESET);
		System.out.println("---------------------------------------");
		System.out.println();

		String choice = sc.nextLine();
		System.out.println();
		
		switch (choice) {
			case "1":
				isEnd = true;
				break;
			case "2":
				logout();
				break;
			case "3":
				updatePhone();
				break;
			case "4":
				updatePW();
				break;
			case "5":
				deleteInfo();
				break;
			default:
				System.out.println(ConsoleMethod.FONT_RED + "잘못된 입력입니다." + ConsoleMethod.RESET);
				break;
		}

		System.out.println();

		return isEnd;
	}

	
	// 로그인
	private void login() {
		System.out.println(ConsoleMethod.BACKGROUND_CYAN + ConsoleMethod.FONT_BLACK + "		  로그인		  " + ConsoleMethod.RESET);

		// 추가기능 - 로그인 시도 횟수 제한

		// 아이디 입력
		userInputer = new UserInputerLoginID(sc, "아이디를 입력해주세요", "0", "로그인", userMap);
		String inputID = userInputer.validatedInput();
		if (inputID == null) return;

		// 비밀번호 입력
		userInputer = new UserInputerLoginPW(sc, "비밀번호 입력해주세요", "0", "로그인");
		String inputPW = userInputer.validatedInput(userMap.get(inputID).getUserPW());
		if (inputPW == null) return;
		
		// 유저 정보 입력
		loggedIn = true;
		this.userId = inputID;  // 얘는 날리는 게 나을 수도??
		this.user = userMap.get(inputID);

		System.out.println();
		System.out.println(ConsoleMethod.FONT_GREEN + "로그인 완료" + ConsoleMethod.RESET);
	}

	// 로그아웃
	private void logout() {
		user = null;
		loggedIn = false;
		System.out.println(ConsoleMethod.BACKGROUND_CYAN + ConsoleMethod.FONT_BLACK + "		  로그아웃		  " + ConsoleMethod.RESET);
	}
	
	// 회원가입
	private void signUp() {
		System.out.println(ConsoleMethod.BACKGROUND_CYAN + ConsoleMethod.FONT_BLACK + "		  회원가입		  " + ConsoleMethod.RESET);
		// 아이디 입력
		userInputer = new UserInputerSignupID(sc, "아이디를 입력해주세요", "0", "회원가입", userMap);
		String inputID = userInputer.validatedInput();
		if (inputID == null) return;

		// 비밀번호 입력
		userInputer = new UserInputerSignupPW(sc, "비밀번호를 입력해주세요", "0", "회원가입");
		String inputPW = userInputer.validatedInput();
		if (inputPW == null) return;

		// 전화번호 입력
		userInputer = new UserInputerSignupPhone(sc, "전화번호를 입력해주세요.(OOO-OOOO-OOOO)", "0", "회원가입");
		String inputPhone = userInputer.validatedInput();
		if (inputPhone == null) return;

		// 유저정보 저장
		User inputUser = new User(inputID, inputPW, inputPhone, new HashMap<>());
		userMap.put(inputUser.getUserID(), inputUser);
		fileIO.userSave(userMap);

		System.out.println();
		System.out.println(ConsoleMethod.FONT_GREEN + "회원가입 완료" + ConsoleMethod.RESET);
	}

	// 전화번호 수정
	private void updatePhone() {
		System.out.println(ConsoleMethod.BACKGROUND_CYAN + ConsoleMethod.FONT_BLACK + "          전화 번호 수정          " + ConsoleMethod.RESET);

		System.out.println("현재 저장된 번호 : " + user.getPhone());
		// 전화번호 입력
		userInputer = new UserInputerSignupPhone(sc, "수정할 전화번호를 입력해주세요.(OOO-OOOO-OOOO)", "0", "전화번호 수정");
		String inputPhone = userInputer.validatedInput();
		if (inputPhone == null) return;
		// 정보 수정
		user.setPhone(inputPhone);
		userMap.put(user.getUserID(), user);
		fileIO.userSave(userMap);

		System.out.println("변경된 번호 : " + user.getPhone());

		System.out.println();
		System.out.println(ConsoleMethod.FONT_GREEN + "전화번호 수정 완료" + ConsoleMethod.RESET);
	}

	// 비밀번호 수정
	private void updatePW() {
		System.out.println(ConsoleMethod.BACKGROUND_CYAN + ConsoleMethod.FONT_BLACK + "		  비밀번호 수정		  " + ConsoleMethod.RESET);

		// 현재 비밀번호 입력
		userInputer = new UserInputerLoginPW(sc, "현재 비밀번호 입력해주세요", "0", "비밀번호 수정");
		String confirmPW = userInputer.validatedInput(userMap.get(user.getUserID()).getUserPW());
		if (confirmPW == null) return;
		// 바꿀 비밀번호 입력
		userInputer = new UserInputerSignupPW(sc, "변경할 비밀번호를 입력해주세요", "0", "비밀번호 수정");
		String changePW = userInputer.validatedInput();
		if (changePW == null) return;
		// 정보 수정
		user.setUserPW(changePW);
		userMap.put(user.getUserID(), user);
		fileIO.userSave(userMap);

		System.out.println();
		System.out.println(ConsoleMethod.FONT_GREEN + "비밀번호 수정 완료" + ConsoleMethod.RESET);

	}

	// 회원 정보 삭제
	private void deleteInfo() {
		System.out.println(ConsoleMethod.BACKGROUND_CYAN + ConsoleMethod.FONT_BLACK + "		  회원정보 삭제		  " + ConsoleMethod.RESET);

		System.out.println(ConsoleMethod.FONT_RED + "정말로 회원정보를 삭제하시겠습니까?(예: y)"  + ConsoleMethod.RESET);
		String choice = sc.nextLine();

		System.out.println();

		if(choice.equals("y")) {
			// 예약 데이터 삭제
			deleteReservation();
			// 유저 데이터 삭제
			userMap.remove(user.getUserID());
			fileIO.userSave(userMap);
			// 로그아웃
			logout(); 
			System.out.println(ConsoleMethod.FONT_RED + "회원정보 삭제 완료" + ConsoleMethod.RESET);
		} else {
			System.out.println(ConsoleMethod.FONT_GREEN + "회원정보 삭제 취소" + ConsoleMethod.RESET);
		}
	}

	private void deleteReservation() {
		// 데이터 불러오기
		Map<String, Reservation> myReservations = user.getMyReservationMap();
		Map<String, Reservation> reservationMap = fileIO.reservationLoad();
		Map<String, Room> roomMap = fileIO.roomLoad();
		// 예약 내역 삭제
		for (Map.Entry<String, Reservation> e : myReservations.entrySet()) {
			// reservationMap 내부 예약 데이터 삭제
			reservationMap.remove(e.getKey());
			// Room 객체 내부 예약 데이터 삭제
			Reservation reservation = e.getValue();
			Room room = roomMap.get(reservation.getRoomId());
			int dateDiff = DateCalc.calcDateDiff(reservation.getCheckInDate(), reservation.getCheckOutDate());
			for (String bookedDate : DateCalc.getDateRange(reservation.getCheckInDate(), dateDiff)) {
				room.getBookedDate().remove(bookedDate);
			}
		}
		// 변경 내용 저장
		fileIO.reservationSave(reservationMap);
		fileIO.roomSave(roomMap);

	}

}
