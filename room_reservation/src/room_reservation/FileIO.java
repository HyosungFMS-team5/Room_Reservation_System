package room_reservation;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


class AppendableObjectOutputStream extends ObjectOutputStream {

    // 첫 번째 객체에 대해서만 헤더를 쓰도록 오버라이드
    @Override
    protected void writeStreamHeader() throws IOException {
        // 파일의 시작이 아니라면, 헤더를 쓰지 않음
        reset();
    }

    public AppendableObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }
}


public class FileIO {

	// 회원가입시 유저 데이터 저장
	public void userSave(User user) {
		File file  = new File("UserData.txt");

		
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream out = null;
		
		try {
			// 입출력 관련 객체 초기화
			boolean fileExist = file.exists() && file.length() > 0;
			fos = new FileOutputStream(file, true);
			bos = new BufferedOutputStream(fos);
			out = fileExist ? new AppendableObjectOutputStream(bos) : new ObjectOutputStream(bos);  // 직렬화 지원 객체
			
			// 직렬화
			out.writeObject(user);

		} catch(Exception e) {
			// 예외 처리 - 나중에
			e.printStackTrace();
		} finally {
			// 파일 종료
			try {
				out.close();
				bos.close();
				fos.close();
			} catch (Exception e) {
				// 나중에
				e.printStackTrace();
			}
		}
	}
	
	// 유저 데이터 불러오기
	public Map<String, User> userLoad() {
		Map<String, User> userMap = new HashMap<>();
		
		String filename = "UserData.txt";
		
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream(filename);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);
			
			User user = null;
			while((user = (User)ois.readObject()) != null) {
				userMap.put(user.getUserID(), user);
			}
			
		} catch (FileNotFoundException e) {  // 잘못된 경로
			System.out.println("파일이 존재하지 않습니다");
		} catch (EOFException e) {  // 파일의 끝
		} catch (IOException e) {  // 입출력 에러
			e.printStackTrace();
			System.out.println("파일을 읽을 수 없습니다");
		} catch (ClassNotFoundException e) {  // 클래스 에러
			System.out.println("잘못된 객체입니다");
		} catch (Exception e) {
			System.out.println("오류가 발생하였습니다");
		}finally {
			try {
				ois.close();
				bis.close();
				fis.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}

		return userMap;
	}
	
	// 관리자 방 추가시 방 데이터 저장
	public void roomSave(Room room) {
		File file  = new File("RoomData.txt");

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream out = null;
		
		try {
			// 입출력 관렬 객체 초기화
			boolean fileExist = file.exists() && file.length() > 0;
			fos = new FileOutputStream(file, true);
			bos = new BufferedOutputStream(fos);
			out = fileExist ? new AppendableObjectOutputStream(bos) : new ObjectOutputStream(bos);  // 직렬화 지원 객체
			
			// 직렬화
			out.writeObject(room);

		} catch(Exception e) {
			// 예외 처리 - 나중에
			e.printStackTrace();
		} finally {
			// 파일 종료
			try {
				out.close();
				bos.close();
				fos.close();
			} catch (Exception e) {
				// 나중에
				e.printStackTrace();
			}
		}
	}
	
	
	// 방 데이터 불러오기
	public Map<String, Room> roomLoad() {
		Map<String, Room> roomMap = new HashMap<>();
		
		String filename = "roomData.txt";
		
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream(filename);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);
			
			Room room = null;
			while((room = (Room)ois.readObject()) != null) {
				roomMap.put(room.getRoomId(), room);
			}
			
		} catch (FileNotFoundException e) {  // 잘못된 경로
			System.out.println("파일이 존재하지 않습니다");
		} catch (EOFException e) {  // 파일의 끝
		} catch (IOException e) {  // 입출력 에러
			e.printStackTrace();
			System.out.println("파일을 읽을 수 없습니다");
		} catch (ClassNotFoundException e) {  // 클래스 에러
			System.out.println("잘못된 객체입니다");
		} catch (Exception e) {
			System.out.println("오류가 발생하였습니다");
		}finally {
			try {
				ois.close();
				bis.close();
				fis.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}

		return roomMap;
	}
	
	
	// 예약 내역 저장
	public void reservationSave(Reservation reservation) {
		File file  = new File("ReservationData.txt");

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream out = null;
		
		try {
			// 입출력 관렬 객체 초기화
			boolean fileExist = file.exists() && file.length() > 0;
			fos = new FileOutputStream(file, true);
			bos = new BufferedOutputStream(fos);
			out = fileExist ? new AppendableObjectOutputStream(bos) : new ObjectOutputStream(bos);  // 직렬화 지원 객체
			
			// 직렬화
			out.writeObject(reservation);

		} catch(Exception e) {
			// 예외 처리 - 나중에
			e.printStackTrace();
		} finally {
			// 파일 종료
			try {
				out.close();
				bos.close();
				fos.close();
			} catch (Exception e) {
				// 나중에
				e.printStackTrace();
			}
		}
	}
	
	// 예약 내역 불러오기
	public List<Reservation> reservationLoad() {
		List<Reservation> reservationList = new ArrayList<>();
		
		String filename = "ReservationData.txt";
		
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream(filename);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);
			
			Reservation reservation = null;
			while((reservation = (Reservation)ois.readObject()) != null) {
				reservationList.add(reservation);
			}
			
		} catch (FileNotFoundException e) {  // 잘못된 경로
			System.out.println("파일이 존재하지 않습니다");
		} catch (EOFException e) {  // 파일의 끝
		} catch (IOException e) {  // 입출력 에러
			e.printStackTrace();
			System.out.println("파일을 읽을 수 없습니다");
		} catch (ClassNotFoundException e) {  // 클래스 에러
			System.out.println("잘못된 객체입니다");
		} catch (Exception e) {
			System.out.println("오류가 발생하였습니다");
		}finally {
			try {
				ois.close();
				bis.close();
				fis.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}

		return reservationList;
	}
	
	// 방 정보 수정 -> 파일 저장 >>>>>>>>>>>>RoomSave는 하나씩 저장되는데, 방 정보 수정이 한꺼번에 이루어질 수 있으므로 만듦
	public void roomInfoChangeSave(Map<String, Room> roomMap) {
	    File file = new File("RoomData.txt");
	    try {
	    	FileOutputStream fos = new FileOutputStream(file, false); // false로 설정하여 파일 내용을 덮어쓰기
	         BufferedOutputStream bos = new BufferedOutputStream(fos);
	         ObjectOutputStream oos = new ObjectOutputStream(bos);
	         
	        for (Room room : roomMap.values()) {
	            oos.writeObject(room); 
	        }
	        System.out.println("수정한 방 정보를 모두 저장했습니다.");
	        
	    } catch (IOException e) {
	        System.out.println("오류가 발생하였습니다.");
	        e.printStackTrace();
	    }
	}

	

	
	// 방 정보 삭제 ->  파일 저장 
	public void deleteRoomSaveFile(Map<String, Room> roomMap) {
	    File file = new File("RoomData.txt");

	    try{
	    	FileOutputStream fos = new FileOutputStream(file);
	         BufferedOutputStream bos = new BufferedOutputStream(fos);
	         ObjectOutputStream oos = new ObjectOutputStream(bos);

	        for(Room room : roomMap.values()) {
	            oos.writeObject(room);
	        }
	        System.out.println("삭제한 방 정보가 반영되었습니다.");
	    } catch(Exception e) {
	        System.out.println("오류가 발생하였습니다.");
	        e.printStackTrace();
	    }
	}
	
}