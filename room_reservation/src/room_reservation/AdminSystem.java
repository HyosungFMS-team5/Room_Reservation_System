package room_reservation;

import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AdminSystem {
   
   Scanner sc;
   FileIO fileIO;
   boolean loggedIn;
   
   public AdminSystem(Scanner sc, FileIO fileIO) {
      this.sc = sc;
      this.fileIO = fileIO;
   }
   
   // 실행 함수
   public void run() {
      while(true) {
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
      while(true) {
         System.out.println("1.방 목록 보기 | 2.방 추가 | 3.방 정보 수정 | 4.방 삭제 | 5.예약 내역 조회 | 6. 예약 내역 취소 | 0. 뒤로 가기");
         String choice = sc.nextLine();
         switch (choice) {
         case "1":
            showAllRooms();
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
            System.out.println("잘못된 입력입니다.");
            break;
         }
      }

   }
   
   // 방 목록 보여주기
   private void ShowRoom() {
      Map<String, Room> roomMap = fileIO.roomLoad();
      
      System.out.println("==========================================");
      System.out.println("                      방                   ");
      System.out.println("==========================================");
      for (Map.Entry<String, Room> e : roomMap.entrySet()) {
         // 출력형식 수정해주세요.
         //System.out.println(e.getKey() + " : " + e.getValue().toString());
         Room room = e.getValue();
         System.out.println("방 ID : " + e.getKey() + ", 방 이름: " + room.getRoomName() + ", 최대 수용 인원: " + room.getCapacity());
         
      }
      System.out.println("==========================================");
      
   }
   
   // 방 상세 조회
   private void showRoomDetail(Room room) {
      System.out.println(room.getRoomId() + "번 방 상세 내역");
      System.out.println("===========================================");
      System.out.println("방 이름 : " + room.getRoomName());
      System.out.println("최대 수용인원 : " + room.getCapacity());
      System.out.println("방 설명 : " + room.getDescription());
      System.out.println("===========================================");
   }
   
   // 방 세부 정보 수정
   private void roomDetailInfoChange(Room room) {
      System.out.println("수정할 사항 1. 최대 수용 인원 2. 방 상세 설명  3. 뒤로가기");
      String detailType = sc.nextLine();
      switch (detailType) {
      
      case "1": {
         System.out.println("변경 최대 수용 인원을 입력해주세요.");
         int changeCapacity = Integer.parseInt(sc.nextLine());
         room.setCapacity(changeCapacity);
         break;

      }
      case "2": {
         System.out.println("수정할 방 상세 설명에 대해 말해주세요.");
         String description = sc.nextLine();
         room.setDescription(description);
            }
      case "3": {
         return;
      }
      default:
         System.out.println("잘못된 입력입니다.");
         break;
      }
   }
   
   public void displayRoomInfo(Map<String, Room> roomMap) {
        if (roomMap.isEmpty()) {
            System.out.println("방이 없습니다.");
        } else {
            for (Room room : roomMap.values()) {
                System.out.println(room);
            }
        }
    }
   
   // 방 정보 수정
   private void roomInfoChange() {
   
      System.out.println("------------------------------현재 방 목록입니다------------------------------");
      ShowRoom();
      System.out.println("-----------------------------------------------------------------------------------");
      
      //System.out.println("수정할 방 타입을 선택해주세요. 1. TentRoom 2. CampingRoom");
      
      System.out.println("수정할 방 ID를 입력해주세요.");
      // 방 타입 선택 roomId으로 받음
      String roomId = sc.nextLine();
      
      Map<String, Room> roomMap = fileIO.roomLoad();
      FileIO fileIO = new FileIO();
      
      if(roomMap.containsKey(roomId)) {
          for (Room room : roomMap.values()) {
            roomDetailInfoChange(room);
            fileIO.roomSave(roomMap);
            displayRoomInfo(roomMap);
            System.out.println("방 정보가 성공적으로 수정되었습니다.");
          }
      }
      else {
         System.out.println("해당하는 방 정보가 없습니다.");
      }
      
      
}
   

   
   private void deleteRoomInfo() {
       Map<String, Room> roomMap = fileIO.roomLoad(); // 방 정보 로드

       System.out.println("------------------------------현재 방 목록입니다------------------------------");
       showAllRooms();
       System.out.println("-----------------------------------------------------------------------------------");

       System.out.println("삭제할 방 ID를 입력해주세요.");
       String roomId = sc.nextLine();
       
       if (roomMap.containsKey(roomId)) {
           roomMap.remove(roomId); // 방 삭제
           fileIO.roomSave(roomMap); // 변경된 방 정보를 파일에 저장
           System.out.println("방 정보가 성공적으로 삭제되었습니다.");
           showAllRooms(); // 변경된 방 목록 출력
       } else {
           System.out.println("해당하는 방 정보가 없습니다.");
       }
   }


   // 예약 내역 조회
   private void showReservation() {
      // 에약내역 파일 불러오면 예약 내역 조회가 된다.
      List<Reservation> reservationList = fileIO.reservationLoad();
      
      for(Reservation reservation : reservationList) {
         System.out.println("예약 ID: " + reservation.getReservationId() +
                    ", 사용자 ID: " + reservation.getUserId() +
                    ", 방 ID: " + reservation.getRoomId() +
                    ", 체크인 날짜: " + reservation.getCheckInDate() +
                    ", 체크아웃 날짜: " + reservation.getCheckOutDate() +
                    ", 인원 수: " + reservation.getPersonCnt());
      }
   }

   
    // 예약 취소
    public void cancelReservation() {
       List<Reservation> reservationList = (List<Reservation>) fileIO.reservationLoad();
       
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
    
   
   // 관리자 로그인
   private void login( ) {
      System.out.println("관리자 패스워드를 입력하세요");
      String inputPW = sc.nextLine();
      if (inputPW.equals(Admin.adminPW)) loggedIn = true;
      System.out.println("관리자 인증이 완료되었습니다.");
   }
   
   // 방 추가
   private void addRoom() {
      Map<String, Room> roomMap = fileIO.roomLoad();
      // 얘는 자동 생성으로 바꿔주세요
      System.out.println("방ID를 입력해주세요");
      String roomId = sc.nextLine();
      System.out.println("방 이름을 입력해주세요");
      String roomName = sc.nextLine();
      System.out.println("최대 숙박 정원 입력해주세요");
      int capacity = Integer.parseInt(sc.nextLine());
      // 숙박인원 예외 처리 추가해주세요.
      System.out.println("방 상세 설명을 입력해주세요");
      String description = sc.nextLine();
      // 룸타입 결정
      Room room = null;
      while(room == null) {
         System.out.println("방 종류를 정해주세요.");
         System.out.println("1.텐트형 | 2.캠핑카형 | 3.취소");
         String roomType = sc.nextLine();
         switch (roomType) {
         case "1":
            room = new TentRoom(roomId, roomName, capacity, description);
            break;
         case "2":
            room = new CampingCarRoom(roomId, roomName, capacity, description);
            break;
         case "3":
            System.out.println("입력을 취소하셨습니다.");
            return;
         default:
            System.out.println("잘못된 입력입니다. 다시 시도해주십시오");
            break;
         }
      }

      roomMap.put(roomId, room);
      fileIO.roomSave(roomMap);
   }
   
   // 방 목록 조회
   private void showAllRooms() {
      Map<String, Room> roomMap = fileIO.roomLoad();
      for (Map.Entry<String, Room> e : roomMap.entrySet()) {
         System.out.println("==========================================");
         System.out.println("                    방 목록                 ");
         Room room = e.getValue();
         System.out.println("방 이름: " + room.getRoomName() + ", 최대 수용 인원: " + room.getCapacity() + ", 방 설명: " + room.getDescription());
         System.out.println("==========================================");
      }
   }
   
   
}