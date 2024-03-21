package room_reservation;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
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
		String filename = "UserData.txt";
		
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		AppendableObjectOutputStream out = null;
		
		try {
			// 입출력 관렬 객체 초기화
			fos = new FileOutputStream(filename, true);
			bos = new BufferedOutputStream(fos);
			out = new AppendableObjectOutputStream(bos);  // 직렬화 지원 객체
			
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
				System.out.println("저장완료");
			} catch (Exception e) {
				// 나중에
				e.printStackTrace();
			}
		}
	}
	
	// 유저 데이터 불러오기
	public Map<String, User> userLoad() {
		Map<String, User> userMap = new HashMap<String, User>();
		
		String filename = "UserData.txt";
		
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream(filename);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);
			
			Object user = null;
			int count = 1;
			while((user = ois.readObject()) != null) {
//				if (user == "\n") continue;
//				userMap.put(user.getUserID(), user);
				System.out.println(user.toString());
				System.out.println(count++);
				
			}
			
		} catch (FileNotFoundException e) {  // 잘못된 경로
			System.out.println("파일이 존재하지 않습니다");
		} catch (EOFException e) {  // 파일의 끝
			System.out.println("파일의 끝 + " + e.getMessage());
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

		
		return null;
	}
	
	
}
