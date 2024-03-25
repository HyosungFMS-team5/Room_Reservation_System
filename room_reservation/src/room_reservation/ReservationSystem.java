package room_reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

import console.print.ConsoleMethod;

import java.text.NumberFormat;
import datecalc.util.DateCalc;

public class ReservationSystem {
	private Scanner sc;
	private FileIO fileIO;
	private UserSystem userSystem;
	private DateCalc dateCalc;
	private Map<Integer, String> scoreMap;
	
	
	public ReservationSystem(Scanner sc, FileIO fileIO, UserSystem userSystem) {
		this.sc = sc;
		this.fileIO = fileIO;
		this.userSystem = userSystem;
		this.dateCalc = new DateCalc();
		this.scoreMap = Map.ofEntries(
    		    Map.entry(1, "★"),
    		    Map.entry(2, "★★"),
    		    Map.entry(3, "★★★"),
    		    Map.entry(4, "★★★★"),
    		    Map.entry(5, "★★★★★")
    		);
	}
	

	// 실행 함수
	public void run() {
		
		while (true) {
			System.out.println("1. 펜션 목록 보기 | 2. 펜션 예약하기 | 3.나의 예약 내역 | 4. 리뷰 작성하기 | 5. 내 리뷰 보기 | 6. 뒤로가기 ");
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
				checkCanReview();
				break;
			}
			case "5": {
				showAllReviewOfMine();
				break;
			}
			case "6": {
				return;
			}
			default:
				
			}
		}
	}
	
	
	//  전체 방 조회 + 상세조회 입력 or 뒤로가기
	private void ShowAllRoom() {
		Map<String, Room> roomMap = fileIO.roomLoad();
		
		TreeSet<String> sortedKeys = new TreeSet<>(roomMap.keySet());
		
		while (true) {

			System.out.println("**************** 예약 가능한 숙소(" + roomMap.size() +") ****************");
			
			for (String key : sortedKeys) {
				System.out.println();
	            roomMap.get(key).showRoomInfo();
	        }
			
			System.out.println("상세 조회 원하시는 방 번호를 입력하세요. | " + ConsoleMethod.FONT_RED + " 0. 뒤로가기" + ConsoleMethod.RESET);
					
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
		Map<String,Review> reviewMap = fileIO.reviewLoad();
		List<Review> sameRoomIdReviewList = new ArrayList<>();

		for (Map.Entry<String, Review> review : reviewMap.entrySet()) {
			if (review.getValue().getRoomId().equals(room.getRoomId())) {
				sameRoomIdReviewList.add(review.getValue());
			}
		}
		
		System.out.println(room.getRoomId() + "번 방 상세 내역");
		System.out.println("==================================================");
		System.out.println("방 이름 : " + room.getRoomName());
		System.out.println("최대 수용인원 : " + room.getCapacity());
		System.out.println("방 상세 설명 : " + room.getDescription());
		System.out.println();
		System.out.println("******************** 후기(" + sameRoomIdReviewList.size() + ") **********************"  );
		
		if (sameRoomIdReviewList.size() == 0) {
			System.out.println();
    		System.out.println("         아직 작성된 후기가 없습니다.        ");
    		System.out.println();
		} else {
			for (Review review: sameRoomIdReviewList) {
    			System.out.println();
    			System.out.println(review.getRoomId() + "번 방");
    			System.out.println(userSystem.getUserId() + "님의 리뷰 - "+ scoreMap.get(review.getScore()) );
    			System.out.println("후기 : " + review.getContent());
    			System.out.println();
    			System.out.println("----------------------------------------");
    	}
		}
		
		System.out.println("**************************************************");
		System.out.println("==================================================");
		System.out.println("0.뒤로가기");
		
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
			if (checkInDate != null && checkOutDate != null && dateDiff > 0) break;
		
			System.out.println("원하는 체크인 날짜를 입력하세요 (형식: yyyy-MM-dd): ");
	        String inputCheckInDate = sc.nextLine();
	        System.out.println("원하는 체크아웃 날짜를 입력하세요 (형식: yyyy-MM-dd): ");
	        String inputCheckOutDate = sc.nextLine();

	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        
	        try {
	        	checkInDate = dateFormat.parse(inputCheckInDate);
	        	// 시연용 리뷰 남길 때 사이 주석
	        	
//	        	LocalDate localCheckInDate = checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//	        	
//	        	if (localCheckInDate.isBefore(LocalDate.now())) {
//	        		System.out.println(LocalDate.now() + " 이후의 날짜를 입력해주세요.");
//	        		continue;
//	        	}
	      
	        	//
	        	checkOutDate = dateFormat.parse(inputCheckOutDate);
	        	// 시연용 리뷰 남길때 사이 주석
//	        	LocalDate localCheckOutDate = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//	        	if (localCheckOutDate.isBefore(localCheckInDate)) {
//	        		System.out.println(localCheckInDate + " 이후의 날짜를 입력해주세요.");
//	        		continue;
//	        	}
	        	//
	        	dateDiff = dateCalc.calcDateDiff(checkInDate,checkOutDate);
	        } catch (ParseException e) {
	            System.out.println("올바른 날짜 형식이 아닙니다.");
	        } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage()); 
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

		List<String> dateList = dateCalc.getDateRange(checkInDate, dateDiff);
		
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
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				// 예약 정보 보여주기
				System.out.println("====================================");
				pickRoom.showRoomInfo();

				System.out.println("숙박 인원 : " + checkInPerson + " / " + pickRoom.getCapacity());
				System.out.println("체크인 : " +  dateFormat.format(checkInDate) + " 15:00");
				System.out.println("체크아웃 : " + dateFormat.format(checkOutDate) + " 11:00");

				// 총 가격
				int price = pickRoom.getTotalPrice(checkInPerson);
				NumberFormat numberFormat = NumberFormat.getInstance();
				String formattedNumber = numberFormat.format(price);
				System.out.printf("총 가격 : %s원\n", formattedNumber);
				System.out.println();
				// 주의 사항
				pickRoom.showPrecaution();
				System.out.println();
				// 안내 사항
				pickRoom.showTypeInfo();
				System.out.println();
				// 예약 확인
				System.out.println("이대로 예약하시겠습니까? (y/n)");
				System.out.println("====================================");
				String yesOrNo = sc.nextLine();
				
				if (yesOrNo.equalsIgnoreCase("y")) {
					
					Map<String, Room> roomMap = fileIO.roomLoad();
					
					for (Map.Entry<String, Room> e : roomMap.entrySet()) {
						if (e.getKey().equals(pickRoom.getRoomId())) {
							for (String date: dateList) {
								e.getValue().getBookedDate().put(date, true);
							}
							break;
						}

					}

					
					fileIO.roomSave(roomMap);
					
					
					
					System.out.println(ConsoleMethod.FONT_GREEN + "정상적으로 예약되었습니다." + ConsoleMethod.RESET);
					
				
					Map<String,Reservation> reservationMap = fileIO.reservationLoad();
					Map<String,User> userMap = fileIO.userLoad();
					
					String reservationId = String.format("%04d", new Random().nextInt(10000));
					Reservation NEW_reservation = new Reservation(reservationId,
																  userSystem.getUserId(),
																  pickRoom.getRoomId(), 
																  checkInDate,
																  checkOutDate,
																  checkInPerson,
																	price,
																  dateList);
					
					
					reservationMap.put(reservationId, NEW_reservation);
					userMap.get(userSystem.getUserId()).getMyReservationMap().put(reservationId, NEW_reservation);
					userSystem.getUser().getMyReservationMap().put(reservationId, NEW_reservation);
					fileIO.reservationSave(reservationMap);
					fileIO.userSave(userMap);
					
					return;
					
				} 
			}
		}}
		

	
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
	
	
	// 나의 예약 내역
    public void showMyReservation() {
    	// TODO : resercationLoad => user의 MyreservationID 로 뽑기

    	Map<String,Reservation> myReservationMap = userSystem.getUser().getMyReservationMap();  
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	System.out.println("=============" + userSystem.getUserId() + "님의 예약 내역==============");
    	
    	for (Map.Entry<String, Reservation> reservation : myReservationMap.entrySet()) {
    		System.out.println("예약번호 : " + reservation.getKey());
			System.out.println();
			System.out.println("예약자 : " + reservation.getValue().getUserId() + "| 방번호 : " + reservation.getValue().getRoomId() + " | 숙박인원 : " + reservation.getValue().getPersonCnt());
			System.out.println("체크인 : " + dateFormat.format(reservation.getValue().getCheckInDate()) + " ~ 체크아웃 : " + dateFormat.format(reservation.getValue().getCheckOutDate()));
			System.out.println("리뷰 작성 여부 : " + (reservation.getValue().isReviewed() ? "O" : "X"));
			System.out.println("관리자 취소 여부 : " + reservation.getValue().isCanceled());
			System.out.println("----------------------------------------");
    	}

    	// 예약취소
    	while (true) {
    		System.out.println("1. 예약 취소 | 0.뒤로가기");
    		String choice = sc.nextLine();
    		
    		if (choice.equals("0")) return ;
    		
    		if (choice.equals("1")) {
    			// TODO : User의 myReservation에서도 빼줘야함
    			cancelReservation(myReservationMap);
    			return;
    		}
    		
    		System.out.println("잘못된 입력입니다.");
    		
    	}
    	
    }
	
    // 예약 취소
    public void cancelReservation(Map<String,Reservation> myReservationMap) {
    	while (true) {
    		System.out.println("취소를 원하는 예약번호를 입력해주세요. | 0. 뒤로가기");
    		String inputReservationId = sc.nextLine();
    		
    		if (inputReservationId.equals("0")) return;
    		
    		if (myReservationMap.containsKey(inputReservationId)) {
    			if (myReservationMap.get(inputReservationId).getCheckOutDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(LocalDate.now())) {
    				System.out.println("숙박 예정인 예약만 취소할 수 있습니다.");
    				return;
    			}
    			
    			deleteBookedDate(myReservationMap.get(inputReservationId));
    			
    			Map<String,Reservation> reservationMap = fileIO.reservationLoad();
    			reservationMap.remove(inputReservationId);
    			fileIO.reservationSave(reservationMap);


    			Map<String, User> userMap = fileIO.userLoad();
    			userMap.get(userSystem.getUserId()).getMyReservationMap().remove(inputReservationId);
    			fileIO.userSave(userMap);
    			
    			userSystem.getUser().getMyReservationMap().remove(inputReservationId);
    			
    			
    			System.out.println("예약이 정상적으로 취소되었습니다.");
    			return;
    		} else {
    			System.out.println("해당하는 방 번호가 없습니다. ");
    		}
    		
    	}
    	
    }
    
    // 방의 bookedDate에서 취소 날짜 지우기
    public void deleteBookedDate(Reservation reservation) {
    	Map<String, Room> roomMap = fileIO.roomLoad();
    	
    	for (String date:reservation.getReserveDateList()) {
    		roomMap.get(reservation.getRoomId()).getBookedDate().remove(date);
    		
    	}
    	
    	fileIO.roomSave(roomMap);
    }
    
    
    
    // 리뷰 작성가능한 예약 내역 판별
    public void checkCanReview() {

    	Map<String,Reservation> myReservationMap = userSystem.getUser().getMyReservationMap(); 
    	LocalDate currentDate = LocalDate.now();
    	List<Reservation> canWriteReviewReservation = new ArrayList<>(); 
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	
    	for (Map.Entry<String, Reservation> reservation: myReservationMap.entrySet()) {
    		Date checkOutDate = reservation.getValue().getCheckOutDate();
            LocalDate localCheckOutDate = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            if (!reservation.getValue().isReviewed() && localCheckOutDate.isBefore(currentDate)) {
            	canWriteReviewReservation.add(reservation.getValue());
            }
    	}
    	
    	System.out.println("=======================리뷰 작성 가능(" + canWriteReviewReservation.size() +")===================");
    	if (canWriteReviewReservation.size() == 0) {
    		System.out.println();
    		System.out.println("         리뷰 작성할 수 있는 예약내역이 없습니다.        ");
    		System.out.println();
    		System.out.println("==================================================");
    	
    	} else {
    		System.out.println();
    		for (Reservation reservation : canWriteReviewReservation) {
    				System.out.println("예약 번호 : " + reservation.getReservationId());
    				System.out.println();
        			System.out.println("방번호 : " + reservation.getRoomId() + " | 숙박인원 : " + reservation.getPersonCnt());
        			System.out.println("체크인 : " + dateFormat.format(reservation.getCheckInDate()) + " ~ 체크아웃 : " + dateFormat.format(reservation.getCheckOutDate()));
        			
        			System.out.println("----------------------------------------");
        	}
    	}
    	
    	while (true) {
    		System.out.println("리뷰를 작성할 예약 번호를 입력하세요. | 0. 뒤로가기");
    		String inputReservationId = sc.nextLine();
    		
    		if (inputReservationId.equals("0")) return ;
    		
    		Reservation pickReservation = null;
    		for (Reservation reservation : canWriteReviewReservation) {
    			if (reservation.getReservationId().equals(inputReservationId)) {
    				pickReservation = reservation;
    				break;
    			}
    		}
    		
    		if (pickReservation == null) {
    			System.out.println("해당하는 방 번호가 없습니다. ");
    		} else {
    			writeReview(pickReservation);
    			return;
    		}
    		
    	}
    	
    	
    }
    
    
    // 리뷰 작성
    private void writeReview(Reservation reservation) {
    	Map<String,Review> reviewMap = fileIO.reviewLoad();

    	while (true) {
    		
    		System.out.println("=========================================");
    		System.out.println("별점 (1 - 5) : ");
    		int inputScore = Integer.parseInt(sc.nextLine());
    		System.out.println("후기를 남겨주세요 : ");
    		String inputReviewDetail = sc.nextLine();
    		System.out.println("=========================================");
    		System.out.println(reservation.getRoomId() + "번 방 리뷰");
    		System.out.println("점수 : " + scoreMap.get(inputScore) );
    		System.out.println("후기 : " + inputReviewDetail);
    		System.out.println("====================================");
    		System.out.println("이대로 남기시겠습니까? (y/n) | 0. 뒤로가기 ");
    		String yesOrNo = sc.nextLine();
    		
    		if (yesOrNo.equals("0")) return ;
    		
    		if (yesOrNo.equalsIgnoreCase("y")) {
    			String reviewId = String.format("%04d", new Random().nextInt(10000));
    			reviewMap.put(reviewId,new Review(reservation.getReservationId(), reservation.getUserId(),reservation.getRoomId(), inputScore, inputReviewDetail));
    			fileIO.reviewSave(reviewMap);
    			
    			Map<String, Reservation> reservationMap = fileIO.reservationLoad();
 
    			reservationMap.get(reservation.getReservationId()).setReviewed(true);
    			fileIO.reservationSave(reservationMap);
    			
    			Map<String, User> userMap = fileIO.userLoad();
    			userMap.get(userSystem.getUserId()).getMyReservationMap().get(reservation.getReservationId()).setReviewed(true);
    			fileIO.userSave(userMap);
    			
    			userSystem.getUser().getMyReservationMap().get(reservation.getReservationId()).setReviewed(true);
    			
    			System.out.println("리뷰가 정상적으로 작성되었습니다.");
    			break;
    		}
    		
    		
    	}
    }
    
    
    // 내 리뷰 보기
    
    public void showAllReviewOfMine() {
    	Map<String,Review> reviewMap = fileIO.reviewLoad();
    	
    	System.out.println("=======================내가 작성한 리뷰(" + reviewMap.size() +")===================");
    	if (reviewMap.size() == 0) {
    		System.out.println();
    		System.out.println("         아직 작성한 리뷰가 없습니다...        ");
    		System.out.println();
    		System.out.println("==================================================");
    	
    	} else {
    		System.out.println();
    		
    		for (Map.Entry<String, Review> review : reviewMap.entrySet()) {
    			System.out.println(review.getValue().getReservationId());
    			System.out.println();
    			System.out.println(review.getValue().getRoomId() + "번 방");
    			System.out.println(userSystem.getUserId() + "님의 리뷰 - "+ scoreMap.get(review.getValue().getScore()) );
    			System.out.println("후기 : " + review.getValue().getContent());
    			System.out.println("----------------------------------------");
    		}
    	}
    	
    }
}
