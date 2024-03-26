package room_reservation;

import java.util.Scanner;

import console.print.ConsoleMethod;
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
		// 취소 검증 - 취소 시 종료
		if ((input = inputer()) == null) break;
		// 유효성 검증 - 통과 못 하면 루프
		if (!validator(new String[] {input})) input = null;
	}

	System.out.println("-----------------------------------");

	return input;
  }

	// 비교값이 있는 유효값 입력기
	public String validatedInput(String compare) {

		String input = null;

		while(input == null) {
				// 취소 검증 - 취소 시 종료
				if ((input = inputer()) == null) break;
			// 유효성 검증 - 통과 못 하면 루프
			if (!validator(new String[] {input, compare})) input = null;
		}

		System.out.println("-----------------------------------");

		return input;
	}

	// 문자열 입력기
	private String inputer() {
		System.out.print(guide + ConsoleMethod.FONT_PURPLE + "(" + cancleNum + ". 취소) : " + ConsoleMethod.RESET);
		String input = sc.nextLine();
		// 취소시 null값 반환
		if (input.equals(cancleNum)) {
			System.out.println(ConsoleMethod.FONT_RED + content + "을 취소하였습니다." + ConsoleMethod.RESET);
			input = null;
		}
		return input;
	}

  // 유효성 검증기 - 구상 클래스에서 재정의
  abstract public boolean validator(String[] input);
}
