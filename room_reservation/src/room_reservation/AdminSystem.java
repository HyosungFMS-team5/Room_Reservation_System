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
   Map<String, Room> roomMap;
   
   public AdminSystem(Scanner sc, FileIO fileIO) {
      this.sc = sc;
      this.fileIO = fileIO;
      roomMap = fileIO.roomLoad();
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
         System.out.println("-------------------------------------------------------------------------------------------------------");
         String choice = sc.nextLine();
         switch (choice) {
         case "1":
            showRoom();
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
         System.out.println("-------------------------------------------------------------------------------------------------------");
      }

   }

   // 관리자 로그인
   private void login( ) {
      System.out.println("관리자 패스워드를 입력하세요");
      String inputPW = sc.nextLine();
      if (inputPW.equals(Admin.adminPW)) loggedIn = true;
      System.out.println("관리자 인증이 완료되었습니다.");
   }
   
   // 방 목록 보여주기
   private void showRoom() {
      System.out.println("-----------------------------------방 리스트-----------------------------------");
      
      for (Map.Entry<String, Room> e : roomMap.entrySet()) {
         Room room = e.getValue();
         room.showRoomInfo();
      }
   }

   // 2. 방 추가
   private void addRoom() {
      System.out.println("-----------------------------------방 추가-----------------------------------");
      // 방 정보 입력
      System.out.println("방ID를 입력해주세요. (0: 취소)");
      String roomId = sc.nextLine();
      if (roomId.equals("0")) {
         System.out.println("취소하셨습니다.");
         return;
      }
      String roomName = inputString("방 이름");
      if (roomName == null) return;
      int baseCapacity = inputNumber("기준 숙박 인원");
      if (baseCapacity == 0) return;
      int capacity = inputNumber("최대 숙박 인원");
      if (capacity == 0) return;
      String description = inputString("방 상세 설명");
      if (description == null) return;
      int price = inputNumber("기준 가격");
      if (price == 0) return;
      // 룸타입 결정
      Room room = null;
      while(room == null) {
         System.out.println("방 종류를 정해주세요.");
         System.out.println("1.텐트형 | 2.캠핑카형 | 0.취소");
         String roomType = sc.nextLine();
         switch (roomType) {
         case "1":
            room = new TentRoom(roomId, roomName, baseCapacity, capacity, description, price);
            break;
         case "2":
            room = new CampingCarRoom(roomId, roomName, baseCapacity, capacity, description, price);
            break;
         case "0":
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
   
   // 방 상세 조회
   private void showRoomDetail(Room room) {
      System.out.printf("-----------------------------------%s번 방 상세 내역-----------------------------------\n", room.getRoomId());
      room.showRoomInfo();
      System.out.println(room.getDescription());
   }
   
   
   // 방 정보 수정
   private void roomInfoChange() {
   
      String roomId = inputRoomId("수정");
      
      if(roomMap.containsKey(roomId)) {
         Room room = roomMap.get(roomId);
         if (!roomDetailInfoChange(room)) return;
         roomMap.put(roomId, room);
         fileIO.roomSave(roomMap);
      }
      else {
         System.out.println("해당하는 방 정보가 없습니다.");
      }
      
   }
   

   // 방 정보 삭제
   private void deleteRoomInfo() {
      String roomId = inputRoomId("삭제");
      
      if (roomMap.containsKey(roomId)) {
         roomMap.remove(roomId); // 방 삭제
         fileIO.roomSave(roomMap); // 변경된 방 정보를 파일에 저장
         System.out.println("방 정보가 성공적으로 삭제되었습니다.");
         showRoom(); // 변경된 방 목록 출력
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

   // 방 세부 정보 수정
   private boolean roomDetailInfoChange(Room room) {
      System.out.println("-----------------------------------방 정보 수정-----------------------------------");
      System.out.println("1.방 이름 / 2. 기준 인원 / 3. 최대 수용 인원 / 4. 방 상세 설명 / 5. 가격 / 0. 뒤로가기");

      boolean isChanged = true;

      String detailType = sc.nextLine();

      switch (detailType) {
         case "1": 
            String inputName = inputString("수정할 방 이름");
            if (inputName == null) return false;
            room.setRoomName(inputName);
            break;
         case "2":
            int inputBaseCapacity = inputNumber("수정할 기준 인원");
            if (inputBaseCapacity == 0) return false;
            room.setBaseCapacity(inputBaseCapacity);
            break;
         case "3": 
            int inputCapacity = inputNumber("수정할 최대 인원");
            if (inputCapacity == 0) return false;
            room.setCapacity(inputCapacity);
            break;
         case "4": 
            String inputDescription = inputString("수정할 방 상세 설명");
            room.setDescription(inputDescription);
            break;
         case "5": 
            int inputPrice = inputNumber("수정할 가격");
            if (inputPrice == 0) return false;
            room.setBasePrice(inputPrice);
            break;
         case "0" : 
            isChanged = false;
            System.out.println("방 정보 수정을 취소합니다.");
            break;
         default:
            System.out.println("잘못된 입력입니다.");
            break;
      }

      return isChanged;

   }

   // 선택할 방 입력
   private String inputRoomId(String content) {
      showRoom();
      System.out.println("-----------------------------------------------------------------------------------");
      
      System.out.println(content + "할 방 ID를 입력해주세요.");
      return sc.nextLine();
   }

   // 문자열 입력기
   private String inputString(String content) {
      System.out.println(content + "을/를 입력해주세요. (0: 취소)");
      String input = sc.nextLine();

      if (input.equals("0")) {
         System.out.println("입력을 취소하였습니다.");
         input = null;
      } 

      return input;
   }

   // 숫자 입력기
   private int inputNumber(String content) {
      System.out.println(content + "을/를 입력해주세요. (0: 취소)");
      int input = 0;

      while(true) {
         try {
            input = Integer.parseInt(sc.nextLine());
            break;
         } catch (Exception e){
            System.out.println("숫자를 입력해주세요.");
         }
      }

      return input;
   }
   
}