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
import java.util.Scanner;

public class ReservationSystem {
	private Scanner sc;
	private FileIO fileIO;
	private UserSystem userSystem;
	private Map<Integer, String> scoreMap;
	
	
	public ReservationSystem(Scanner sc, FileIO fileIO, UserSystem userSystem) {
		this.sc = sc;
		this.fileIO = fileIO;
		this.userSystem = userSystem;
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
		List<Review> reviewList = fileIO.reviewLoad();
		List<Review> sameRoomIdReviewList = new ArrayList<>();
		
		for (Review review : reviewList) {
			if (review.getRoomId().equals(room.getRoomId())) {
				sameRoomIdReviewList.add(review);
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
			
			// TODO: LocalDate보다 이전의 값을 입력하면 커트해야함
			// TODO: checkOutDate가 checkInDate보다 이전이면 커트 
			if (checkInDate != null && checkOutDate != null && dateDiff > 0) break;
		
			System.out.println("원하는 체크인 날짜를 입력하세요 (형식: yyyy-MM-dd): ");
	        String inputCheckInDate = sc.nextLine();
	        System.out.println("원하는 체크아웃 날짜를 입력하세요 (형식: yyyy-MM-dd): ");
	        String inputCheckOutDate = sc.nextLine();

	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        
	        try {
	        	checkInDate = dateFormat.parse(inputCheckInDate);
	        	// 시연용 리뷰 남길 때 사이 주석
	        	
	        	LocalDate localCheckInDate = checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	        	
	        	if (localCheckInDate.isBefore(LocalDate.now())) {
	        		System.out.println(LocalDate.now() + " 이후의 날짜를 입력해주세요.");
	        		continue;
	        	}
	      
	        	//
	        	checkOutDate = dateFormat.parse(inputCheckOutDate);
	        	// 시연용 리뷰 남길때 사이 주석
	        	LocalDate localCheckOutDate = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	        	if (localCheckOutDate.isBefore(localCheckInDate)) {
	        		System.out.println(localCheckInDate + " 이후의 날짜를 입력해주세요.");
	        		continue;
	        	}
	        	//
	        	dateDiff = calcDateDiff(checkInDate,checkOutDate);
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
					
					
					
					System.out.println("정상적으로 예약되었습니다.");
					
					List<Reservation> reservationList = fileIO.reservationLoad();
					reservationList.add(new Reservation(userSystem.getUserId(),
														pickRoom.getRoomId(), 
														checkInDate,
														checkOutDate,
														checkInPerson,
														dateList));
					
					fileIO.reservationSave(reservationList);
					
					return;

					
				} 
			}
		}}
		
		
		
//	}
	
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
    	for (Reservation reservation : reservationList) {
    		if (reservation.getUserId().equals(userSystem.getUserId())) {
    			System.out.println("예약번호 : " + reservation.getReservationId());
    			System.out.println();
    			System.out.println("예약자 : " + reservation.getUserId() + "| 방번호 : " + reservation.getRoomId() + " | 숙박인원 : " + reservation.getPersonCnt());
    			System.out.println("체크인 : " + dateFormat.format(reservation.getCheckInDate()) + " ~ 체크아웃 : " + dateFormat.format(reservation.getCheckOutDate()));
    			System.out.println("리뷰 작성 여부 :" + reservation.isReviewed() );
    			System.out.println("----------------------------------------");
    		}
    	}
    	// 예약취소
    	while (true) {
    		System.out.println("1. 예약 취소 | 0.뒤로가기");
    		String choice = sc.nextLine();
    		
    		if (choice.equals("0")) return ;
    		
    		if (choice.equals("1")) {
    			cancelReservation(reservationList);
    			return;
    		}
    		
    		System.out.println("잘못된 입력입니다.");
    		
    	}
    	
    }
	
    // 예약 취소
    public void cancelReservation(List<Reservation> reservationList) {
    	while (true) {
    		System.out.println("취소를 원하는 예약번호를 입력해주세요. | 0. 뒤로가기");
    		String inputReservationId = sc.nextLine();
    		
    		if (inputReservationId.equals("0")) return;
    		
    		List<Reservation> tempReservationList = new ArrayList<>();
    		Reservation pickReservation = null;
    		
    		for (Reservation reservation : reservationList) {
    			if (reservation.getReservationId().equals(inputReservationId)) {
    				pickReservation = reservation;
    				continue;
    			}
    			
    			tempReservationList.add(reservation);
    		}
    		
    		
    		if (pickReservation == null) {
    			System.out.println("해당하는 방 번호가 없습니다. ");
    		} else {
    			fileIO.reservationSave(tempReservationList);
    			deleteBookedDate(pickReservation);
    			System.out.println("예약이 정상적으로 취소되었습니다.");
    			return;
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
    	List<Reservation> reservationList = fileIO.reservationLoad();
    	LocalDate currentDate = LocalDate.now();
    	List<Reservation> canWriteReviewReservation = new ArrayList<>(); 
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	
    	for (Reservation reservation : reservationList) {
    		if (reservation.getUserId().equals(userSystem.getUserId())) {
    			
    			Date checkOutDate = reservation.getCheckOutDate();
                LocalDate localCheckOutDate = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                
                if (!reservation.isReviewed() && localCheckOutDate.isBefore(currentDate)) {
                	canWriteReviewReservation.add(reservation);
                }
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
        			System.out.println("방번호 : " + reservation.getRoomId() + " | 숙박인원 : " + reservation.getPersonCnt());
        			System.out.println("체크인 : " + dateFormat.format(reservation.getCheckInDate()) + " ~ 체크아웃 : " + dateFormat.format(reservation.getCheckOutDate()));
        			
        			System.out.println("----------------------------------------");
        	}
    	}
    	
    	while (true) {
    		System.out.println("리뷰를 작성할 방 번호를 입력하세요. | 0. 뒤로가기");
    		String inputRoomId = sc.nextLine();
    		
    		if (inputRoomId.equals("0")) return ;
    		
    		boolean isInputRoomIdValid = false;
    		for (Reservation reservation : canWriteReviewReservation) {
    			if (reservation.getRoomId().equals(inputRoomId)) {
    				isInputRoomIdValid = true;
    				break;
    			}
    		}
    		
    		if (!isInputRoomIdValid) {
    			System.out.println("해당하는 방 번호가 없습니다. ");
    		} else {
    			writeReview(inputRoomId);
    			return;
    		}
    		
    	}
    	
    	
    }
    
    
    // 리뷰 작성
    private void writeReview(String roomId) {
    	List<Review> reviewList = fileIO.reviewLoad();

    	while (true) {
    		
    		System.out.println("=========================================");
    		System.out.println("별점 (1 - 5) : ");
    		int inputScore = Integer.parseInt(sc.nextLine());
    		System.out.println("후기를 남겨주세요 : ");
    		String inputReviewDetail = sc.nextLine();
    		System.out.println("=========================================");
    		System.out.println(roomId + "번 방 리뷰");
    		System.out.println("점수 : " + scoreMap.get(inputScore) );
    		System.out.println("후기 : " + inputReviewDetail);
    		System.out.println("====================================");
    		System.out.println("이대로 남기시겠습니까? (y/n) | 0. 뒤로가기 ");
    		String yesOrNo = sc.nextLine();
    		
    		if (yesOrNo.equals("0")) return ;
    		
    		if (yesOrNo.equalsIgnoreCase("y")) {
    			reviewList.add(new Review(userSystem.getUserId(), roomId, inputScore, inputReviewDetail));
    			fileIO.reviewSave(reviewList);
    			
    			// 리뷰를 남긴 reservation 이니까 isReviewed true로! 
    			
    			List<Reservation> reservationList = fileIO.reservationLoad();
    			List<Reservation> tempReservationList = new ArrayList<>();
    			
    			for (Reservation reservation: reservationList) {
    				if (reservation.getRoomId().equals(roomId)) {
    					reservation.setReviewed(true);
    				}
    				tempReservationList.add(reservation);
    			}
    			
    			fileIO.reservationSave(tempReservationList);
    			
    			System.out.println("리뷰가 정상적으로 작성되었습니다.");
    			break;
    		}
    		
    		
    	}
    }
    
    
    // 내 리뷰 보기
    
    public void showAllReviewOfMine() {
    	List<Review> reviewList = fileIO.reviewLoad();
    	
    	System.out.println("=======================내가 작성한 리뷰(" + reviewList.size() +")===================");
    	if (reviewList.size() == 0) {
    		System.out.println();
    		System.out.println("         아직 작성한 리뷰가 없습니다...        ");
    		System.out.println();
    		System.out.println("==================================================");
    	
    	} else {
    		System.out.println();
    		for (Review review: reviewList) {
        			System.out.println();
        			System.out.println(review.getRoomId() + "번 방");
        			System.out.println(userSystem.getUserId() + "님의 리뷰 - "+ scoreMap.get(review.getScore()) );
        			System.out.println("후기 : " + review.getContent());
        			System.out.println("----------------------------------------");
        	}
    	}
    	
    }
}
