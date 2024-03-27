package fileio;

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
import java.util.HashMap;
import java.util.Map;

import room_reservation.reservation.Reservation;
import room_reservation.reservation.Review;
import room_reservation.room.Room;
import room_reservation.user.User;


public class FileIO {

	// 회원가입시 유저 데이터 저장
	public void userSave(Map<String, User> userMap) {
		File file  = new File("UserData.txt");

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream out = null;
		
		try {
			// 입출력 관련 객체 초기화
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			out =  new ObjectOutputStream(bos);  // 직렬화 지원 객체
			
			// 직렬화
			out.writeObject(userMap);

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
		Map<String, User> userMap = new HashMap<String, User>();
		
		File file  = new File("UserData.txt");
		
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;

		if (file.exists()) {
			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				ois = new ObjectInputStream(bis);
	
				userMap = (Map<String, User>)ois.readObject();
				
			} catch (FileNotFoundException e) {  // 잘못된 경로
			} catch (EOFException e) {  // 파일의 끝
			} catch (IOException e) {  // 입출력 에러
			} catch (ClassNotFoundException e) {  // 클래스 에러
			} catch (Exception e) {
			}finally {
				try {
					ois.close();
					bis.close();
					fis.close();
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
			}
		}
		
		return userMap;
	}
	
	// 관리자 방 추가시 방 데이터 저장
	public void roomSave(Map<String, Room> roomMap) {
		File file  = new File("RoomData.txt");

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream out = null;
		
		try {
			// 입출력 관렬 객체 초기화
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			out = new ObjectOutputStream(bos);  // 직렬화 지원 객체
			
			// 직렬화
			out.writeObject(roomMap);

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
		
		File file = new File("roomData.txt");
		
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		
		if (file.exists()) {
			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				ois = new ObjectInputStream(bis);
				
				roomMap = (Map<String, Room>) ois.readObject();
				
			} catch (FileNotFoundException e) {  // 잘못된 경로
			} catch (EOFException e) {  // 파일의 끝
			} catch (IOException e) {  // 입출력 에러
			} catch (ClassNotFoundException e) {  // 클래스 에러
			} catch (Exception e) {
			}finally {
				try {
					ois.close();
					bis.close();
					fis.close();
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
			}
		}

		return roomMap;
	}
	
	
	// 예약 내역 저장
	
	public void reservationSave(Map<String,Reservation> reservationMap) {
		File file  = new File("ReservationData.txt");

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream out = null;
		
		try {
			// 입출력 관렬 객체 초기화
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			out = new ObjectOutputStream(bos);  // 직렬화 지원 객체
			
			// 직렬화
			out.writeObject(reservationMap);

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

	public Map<String,Reservation> reservationLoad() {
		Map<String,Reservation> reservationMap = new HashMap<>();
		
		File file = new File("ReservationData.txt");

		if(file.exists()) {
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			ObjectInputStream ois = null;
			
			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				ois = new ObjectInputStream(bis);
				
				reservationMap = (Map<String,Reservation>) ois.readObject();
				
			} catch (FileNotFoundException e) {  // 잘못된 경로
			} catch (EOFException e) {  // 파일의 끝
			} catch (IOException e) {  // 입출력 에러
			} catch (ClassNotFoundException e) {  // 클래스 에러
			} catch (Exception e) {
			}finally {
				try {
					if (ois != null)
											ois.close();
									if (bis != null)
											bis.close();
									if (fis != null)
											fis.close();
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
			}
		}
		


		return reservationMap;
	}

	
	// 리뷰내역 저장 
	public void reviewSave(Map<String,Review> reviewMap) {
		File file  = new File("ReviewData.txt");

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream out = null;
		
		try {
			// 입출력 관렬 객체 초기화
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			out = new ObjectOutputStream(bos);  // 직렬화 지원 객체
			
			// 직렬화
			out.writeObject(reviewMap);

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
	
	
	// 리뷰 불러오기

	public Map<String,Review> reviewLoad() {
		Map<String,Review> reviewMap = new HashMap<>();
		
		File file = new File("ReviewData.txt");
		
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		
		if (file.exists()) {
			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				ois = new ObjectInputStream(bis);
				
				reviewMap = (Map<String,Review>) ois.readObject();
				
			} catch (FileNotFoundException e) {  // 잘못된 경로
			} catch (EOFException e) {  // 파일의 끝
			} catch (IOException e) {  // 입출력 에러
			} catch (ClassNotFoundException e) {  // 클래스 에러
			} catch (Exception e) {
			}finally {
				try {
					if (ois != null)
											ois.close();
									if (bis != null)
											bis.close();
									if (fis != null)
											fis.close();
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
			}
		}

		return reviewMap;
	}
}
