package room_reservation;

import java.util.Scanner;

import consolemethod.ConsoleMethod;
import fileio.FileIO;

public class Menu {
	private Scanner sc;
	private FileIO fileIO;
	private boolean LoggedIn;
	private boolean isUser;
	private UserSystem userSystem;
	private AdminSystem adminSystem;
	private ReservationSystem reservationSystem;
	
	// 생성자
	public Menu( ) {
		sc = new Scanner(System.in);
		fileIO = new FileIO();
	}
	
	// 실행 함수
	public void run() {
		while (!LoggedIn) {
			displayDefault();
			if (isUser) displayUserSystem();
			else displayAdminSystem();
		}
		if (isUser) displayReservation();
		System.out.println(ConsoleMethod.FONT_GREEN + "예약시스템을 종료합니다." + ConsoleMethod.RESET);
	}
	
	// 초기 메뉴 조회 - 관리자, 유저 선택
	public void displayDefault() {
		while (true) {
			ConsoleMethod.StartSystem1();
			ConsoleMethod.StartSystem2();
			System.out.println("|⠀⠀⠀⠀⡇⠀⠀1. 관리자 로그인⠀⠀⠀⡇⠀⠀⠀⠀⠀⠀⢕⠀⠀2. 사용자 로그인⠀ ⠀⡇⠀⠀⠀⠀|");
			ConsoleMethod.StartSystem3();
			
			String choice = sc.nextLine();
			
			switch (choice) {
			case "1": isUser = false;
				return;
			case "2": isUser = true;
				return;
			default:
				System.out.println(ConsoleMethod.FONT_RED + "잘못된 입력입니다." + ConsoleMethod.RESET);
				break;
			}
		}
	}
	
	// 유저 시스템 조회 - 회원 기능 담당
	public void displayUserSystem() {
		userSystem = new UserSystem(sc, fileIO);
		userSystem.run();
		this.LoggedIn = userSystem.isLoggedIn();
	}
	
	// 관리자 시스템 조회 - 관리자 관련 기능 모두
	public void displayAdminSystem() {
		adminSystem = new AdminSystem(sc, fileIO);
		adminSystem.run();
	}
	
	// 예약시스템 조회 - 예약 관련 서비스 모두
	public void displayReservation() {
		reservationSystem = new ReservationSystem(sc, fileIO, userSystem);
		reservationSystem.run();
	}
	

}
