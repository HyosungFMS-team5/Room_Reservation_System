package room_reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ReservationSystem {
	private Scanner sc;
	private FileIO fileIO;
	private UserSystem userSystem;
	
	
	public ReservationSystem(Scanner sc, FileIO fileIO, UserSystem userSystem) {
		this.sc = sc;
		this.fileIO = fileIO;
		this.userSystem = userSystem;
	}
	

	// 실행 함수
	public void run() {
		
		while (true) {
			System.out.println("1. 펜션 목록 보기 | 2. 펜션 예약하기 | 3.나의 예약 내역 | 4. 리뷰 작성하기 | 5. 뒤로가기 ");
			String choice = sc.nextLine();
			switch (choice) {
			case "1": {
				ShowAllRoom();
				break;
			}
			case "2": {
				reserveRoom();
				break;
			}
			case "3": {
				showMyReservation();
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
	
	
	// 방 예약하기
	private void reserveRoom() {
		System.out.println("====================================");
		Date checkInDate = null;
		Date checkOutDate = null;
		int dateDiff = 0;
		
		while (true) {
			// TODO: LocalDate보다 이전의 값을 입력하면 커트해야함
			if (checkInDate != null && checkOutDate != null && dateDiff > 0) break;
		
			System.out.println("원하는 체크인 날짜를 입력하세요 (형식: yyyy-MM-dd): ");
	        String inputCheckInDate = sc.nextLine();
	        System.out.println("원하는 체크아웃 날짜를 입력하세요 (형식: yyyy-MM-dd): ");
	        String inputCheckOutDate = sc.nextLine();

	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        
	        try {
	        	checkInDate = dateFormat.parse(inputCheckInDate);
	        	checkOutDate = dateFormat.parse(inputCheckOutDate);
	        	dateDiff = calcDateDiff(checkInDate,checkOutDate);
	        } catch (ParseException e) {
	            System.out.println("올바른 날짜 형식이 아닙니다.");
	        } 
		}
		
		int checkInPerson = 0;
		while (checkInPerson == 0) {
			System.out.println("숙박 인원을 입력하세요");
			try {
				checkInPerson = Integer.parseInt(sc.nextLine());
				
			} catch (Exception e) {
				e.getMessage();
			}
		}

		List<String> dateList = getDateRange(checkInDate, dateDiff);
		
		// 예약 가능한 방인지 체크
		List<Room> validRoomList = canReseveRoom(dateList,checkInPerson);
		
		while (true) {
			
			System.out.println("***************************************");
			System.out.println("예약가능한 방("+ validRoomList.size() + ")");
			int idx = 1;
			for (Room room : validRoomList) {
				System.out.println(idx++ + ". " + room.getRoomId() + "번 방 / " + room.getRoomName() + "/ 최대 수용인원 : " + room.getCapacity());
			}
			System.out.println("***************************************");
			System.out.println("예약 원하시는 방 번호를 입력하세요. | 0. 뒤로가기");
			String inputRoomId = sc.nextLine();
			
			if (inputRoomId.equals("0")) return ;
			
			Room pickRoom = null;
			for (Room room : validRoomList) {
				if (room.getRoomId().equals(inputRoomId)) pickRoom = room;
			}
			
			if (pickRoom == null) {
				System.out.println("일치하는 방번호가 없습니다.");
			} else {
				System.out.println("====================================");
				System.out.println("방 번호 : " + pickRoom.getRoomId());
				System.out.println("방 이름 : " + pickRoom.getRoomName());
				System.out.println("숙박 인원 : " + checkInPerson + " / " + pickRoom.getCapacity());
				System.out.println("체크인 날짜 : " + checkInDate);
				System.out.println("체크아웃 날짜 : " + checkOutDate);
				
				System.out.println("이대로 예약하시겠습니까? (y/n)");
				System.out.println("====================================");
				String yesOrNo = sc.nextLine();
				
				if (yesOrNo.equalsIgnoreCase("y")) {
					// booked에 date 키로 집어넣고 , reservation data 집어넣고 break
					
					// TODO: 이거 이렇게 넣으면 안될듯 , 데이터에 저장해줘야함 
					Map<String, Room> roomMap = fileIO.roomLoad();
					List<Room> temp = new ArrayList<>();
					
					for (Map.Entry<String, Room> e : roomMap.entrySet()) {
						if (e.getKey().equals(pickRoom.getRoomId())) {
							for (String date: dateList) {
								e.getValue().getBookedDate().put(date, true);
							}
							temp.add(e.getValue());
						} else {
							temp.add(e.getValue());
						}
					}
					
					for (Room room : temp) {
						fileIO.roomSave(room);
					}
					
					
					System.out.println(userSystem.getUserId());
					System.out.println(pickRoom.getRoomId());
					System.out.println(checkInDate);
					System.out.println(checkOutDate);
					System.out.println(checkInPerson);
					
					fileIO.reservationSave(new Reservation(userSystem.getUserId(),
															pickRoom.getRoomId(), 
															checkInDate,
															checkOutDate,
															checkInPerson));
				
					break;
					
				} 
			}
		}
		
		
		
	}
	
	// 예약 가능한 방 체크
	
	private List<Room> canReseveRoom(List<String> dateList, int checkInPerson) {
		Map<String, Room> roomMap = fileIO.roomLoad();
		List<Room> validRoomList = new ArrayList<>();
		
		for (Map.Entry<String, Room> e : roomMap.entrySet()) {
			if (checkInPerson > e.getValue().getCapacity()) continue; 
			
			boolean flag = false;
	        for (String date : dateList) {	
	        	if (e.getValue().getBookedDate().containsKey(date)) {
	        		flag = true;
	        		break;
	        	}  
	        }
	        
	        if (!flag) validRoomList.add(e.getValue());
			
		}
		
		return validRoomList;
	}
	
	// 체크할 날짜 리스트 return
    private static List<String> getDateRange(Date startDate, int dateDiff) {
        List<String> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < dateDiff; i++) {
            String dateString = dateFormat.format(calendar.getTime());
            dateList.add(dateString);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dateList;
    }
	
	// 체크인 체크아웃 날짜의 차 계산
    private static int calcDateDiff(Date startDate, Date endDate) {
        long differenceInMilliseconds = endDate.getTime() - startDate.getTime();
        long differenceInDays = differenceInMilliseconds / (1000 * 60 * 60 * 24);
        return (int) differenceInDays; 
    }
	
	
	// 나의 예약 내역
    public void showMyReservation() {
    	List<Reservation> reservationList = fileIO.reservationLoad();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	System.out.println("=============" + userSystem.getUserId() + "님의 예약 내역==============");
    	for (Reservation r : reservationList) {
    		if (r.getUserId().equals(userSystem.getUserId())) {
    			System.out.println("예약자 : " + r.getUserId() + "| 방번호 : " + r.getRoomId() + " | 숙박인원 : " + r.getPersonCnt());
    			System.out.println("체크인 : " + dateFormat.format(r.getCheckInDate()) + " ~ 체크아웃 : " + dateFormat.format(r.getCheckOutDate()));
    			System.out.println("----------------------------------------");
    		}
    	}
    	
    	
    }
	
}
