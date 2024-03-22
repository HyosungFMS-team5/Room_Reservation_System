package room_reservation;

import java.util.Scanner;
import java.util.regex.Pattern;

public class UserInputerSignupPW extends UserInputer{

  public UserInputerSignupPW(Scanner sc, String guide, String cancleNum, String content) {
    super(sc, guide, cancleNum, content);
  }

  @Override
  public boolean validator(String[] input) {
		boolean isValid = false;
		if(!Pattern.matches("^.{8,}$", input[0])) {
			System.out.println("비밀번호는 8글자 이상 이어야합니다.");
		} else if (!Pattern.matches("^.*\\d+.*$", input[0])) {
			System.out.println("1개 이상의 숫자를 포함해주세요.");
		} else if (!Pattern.matches("^.*[a-zA-Z]+.*$", input[0])) {
			System.out.println("1개 이상의 영문자를 포함해주세요.");
		} else if (!Pattern.matches("^\\S+$", input[0])) {
			System.out.println("공백은 입력하실 수 없습니다.");
		} else isValid = true;

		return isValid;
  }
}
