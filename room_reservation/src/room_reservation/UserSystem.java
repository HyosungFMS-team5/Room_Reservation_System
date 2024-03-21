package room_reservation;

import java.util.Map;
import java.util.Scanner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSystem {
	private Scanner sc;
	private FileIO fileIO;
	private boolean loggedIn;
	private User user;
	
	public UserSystem(Scanner sc, FileIO fileIO) {
		this.sc = sc;
		this.fileIO = fileIO;
	}
	
	// 실행 함수
	public void run() {
		while(true) {
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
		System.out.print("아이디 입력 : ");
		String inputID = sc.nextLine();
		System.out.print("비밀번호 입력 : ");
		String inputPW = sc.nextLine();
		
		Map<String, User> userData = fileIO.userLoad();
		if (!userData.containsKey(inputID)) {
			System.out.println("존재하지 않는 ID입니다.");
		} else if (!inputPW.equals(userData.get(inputID).getUserPW())) {
			System.out.println("비밀번호가 일치하지 않습니다.");
		} else {
			System.out.println("로그인에 성공하였습니다.");
			loggedIn = true;
		}
	}
	
	// 회원가입
	public void signUp() {
		System.out.println("회원가입을 진행합니다.");
		System.out.print("아이디 입력 : ");
		String inputID = sc.nextLine();
		// 아이디 중복 로직 추가 요망
		System.out.print("비밀번호 입력 : ");
		String inputPW = sc.nextLine();
		// 비밀번호 확인 로직 추가 요망
		// 휴대폰 번호 입력 로직 추가 요망
		fileIO.userSave(new User(inputID, inputPW, ""));
	}
	
	
}
