package room_reservation;

import java.util.Map;
import java.util.Scanner;

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
			System.out.println("---------------------------------------");
		}
	}

	// 로그인 전 메뉴
	private boolean showBeforLogin() {
		boolean isEnd = false;

		System.out.println("1. 회원가입 | 2. 로그인 | 3. 뒤로 가기");
		String choice = sc.nextLine();
		
		switch (choice) {
			case "1":
				signUp();
				break;
			case "2":
				login();
				break;
			case "3":
				isEnd = true;
				break;
			default:
				System.out.println("잘못된 입력입니다.");
				break;
		}

		return isEnd;
	}

	// 로그인 후 메뉴
	private boolean showAfterLogin() {
		boolean isEnd = false;

		System.out.println("1. 펜션 예약하기 | 2. 로그아웃 | 3. 전화번호 수정 | 4. 비밀번호 수정 | 5. 회원탈퇴");
		String choice = sc.nextLine();
		
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
				System.out.println("잘못된 입력입니다.");
				break;
		}

		return isEnd;
	}

	
	// 로그인
	private void login() {
		System.out.println("로그인을 진행합니다.");
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

		System.out.println("로그인에 성공하였습니다.");
	}

	// 로그아웃
	private void logout() {
		user = null;
		loggedIn = false;
	}
	
	// 회원가입
	private void signUp() {
		System.out.println("회원가입을 진행합니다.");
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
		User inputUser = new User(inputID, inputPW, inputPhone);
		userMap.put(inputUser.getUserID(), inputUser);
		fileIO.userSave(userMap);

		System.out.println("회원 가입을 완료하였습니다.");
	}

	// 전화번호 수정
	private void updatePhone() {
		System.out.println("등록된 전화번호를 수정합니다.현재 저장된 번호 : ");
		System.out.println("현재 저장된 번호 : " + user.getPhone());
		// 전화번호 입력
		userInputer = new UserInputerSignupPhone(sc, "전화번호를 입력해주세요.(OOO-OOOO-OOOO)", "0", "전화번호 수정");
		String inputPhone = userInputer.validatedInput();
		if (inputPhone == null) return;
		// 정보 수정
		user.setPhone(inputPhone);
		userMap.put(user.getUserID(), user);
		fileIO.userSave(userMap);

		System.out.println("전화번호 수정이 완료되었습니다.");
		System.out.println("변경된 번호 : " + user.getPhone());
	}

	// 비밀번호 수정
	private void updatePW() {
		System.out.println("비밀번호를 수정합니다.");
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

		System.out.println("비밀번호 수정이 완료되었습니다.");
	}

	// 회원 정보 삭제
	private void deleteInfo() {
		System.out.println("정말로 회원정보를 삭제하시겠습니까?(예: y)");
		String choice = sc.nextLine();

		if(choice.equals("y")) {
			userMap.remove(user.getUserID());
			fileIO.userSave(userMap);
			logout();
			System.out.println("회원정보 삭제가 완료되었습니다.");
		} 

		System.out.println("회원정보 삭제를 종료합니다.");
	}

}
