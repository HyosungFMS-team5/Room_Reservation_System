package room_reservation;

import java.util.Scanner;

import lombok.AllArgsConstructor;

@AllArgsConstructor
abstract public class UserInputer {
	private Scanner sc;
	private String guide;
	private String cancleNum;
	private String content;

  // 비교값이 없는 유효값 입력기
  public String validatedInput() {
		String input = null;
    while(input == null) {
			System.out.println(guide + " | " + cancleNum + ". 취소");
			input = sc.nextLine();
			// 취소시 null값 반환
			if (input.equals(cancleNum)) {
				System.out.println(content + "을 취소하였습니다.");
				input = null;
				break;
			}
			// 유효성 검증 결과에 따라 input 초기화
			if (!validator(new String[] {input})) input = null;
		}
		return input;
  }

	// 비교값이 있는 유효값 입력기
	public String validatedInput(String compare) {
		String input = null;
		while(input == null) {
			System.out.println(guide + " | " + cancleNum + ". 취소");
			input = sc.nextLine();
			// 취소시 null값 반환
			if (input.equals(cancleNum)) {
				System.out.println(content + "을 취소하였습니다.");
				input = null;
				break;
			}
			// 유효성 검증 결과에 따라 input 초기화
			if (!validator(new String[] {input, compare})) input = null;
		}
		return input;
	}

  // 유효성 검증기 - 구상 클래스에서 재정의
  abstract public boolean validator(String[] input);
}
