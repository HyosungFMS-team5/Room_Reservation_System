package room_reservation;

import java.util.Scanner;

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
		System.out.println("예약시스템을 종료합니다.");
	}
	
	// 초기 메뉴 조회 - 관리자, 유저 선택
	public void displayDefault() {
		while (true) {
			System.out.println("1. 관리자, 2. 유저");
			String choice = sc.nextLine();
			
			switch (choice) {
			case "1": isUser = false;
				System.out.println("..");
				return;
			case "2": isUser = true;
				return;
			default:
				System.out.println("잘못된 입력입니다.");
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
