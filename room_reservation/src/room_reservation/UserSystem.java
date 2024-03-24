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
		while(true) {
			System.out.println("---------------------------------------");
			System.out.println("1. 회원가입 | 2. 로그인 | 3. 뒤로 가기");
			String choice = sc.nextLine();
			
			switch (choice) {
				case "1":
					signUp();
					break;
				case "2":
					login();
					if (!loggedIn) break;
				case "3":
					return;
				default:
					System.out.println("잘못된 입력입니다.");
					break;
			}
		}
		
	}
	
	// 로그인
	public void login() {
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
	
	// 회원가입
	public void signUp() {
		System.out.println("회원가입을 진행합니다.");
		// 아이디 입력
		userInputer = new UserInputerSignupID(sc, "아이디를 입력해주세요", "0", "회원가입", userMap);
		String inputID = userInputer.validatedInput();
		if (inputID == null) return;

		// 비밀번호 입력
		userInputer = new UserInputerSignupPW(sc, "비밀번호 입력해주세요", "0", "회원가입");
		String inputPW = userInputer.validatedInput();
		if (inputPW == null) return;

		// 전화번호 입력
		userInputer = new UserInputerSignupPhone(sc, "휴대전화 번호 입력해주세요.(OOO-OOOO-OOOO)", "0", "회원가입");
		String inputPhone = userInputer.validatedInput();
		if (inputPhone == null) return;

		// 유저정보 저장
		User user = new User(inputID, inputPW, inputPhone);
		userMap.put(user.getUserID(), user);
		fileIO.userSave(userMap);

		System.out.println("회원 가입을 완료하였습니다!");
	}

	public void updatePW() {

	}

	public void updatePhone() {

	}

	public void deleteInfo() {

	}
}
