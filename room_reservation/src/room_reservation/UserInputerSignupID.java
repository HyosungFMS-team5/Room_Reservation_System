package room_reservation;

import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserInputerSignupID extends UserInputer{
  private Map<String,User> userMap;

  public UserInputerSignupID(Scanner sc, String guide, String cancleNum, String content, Map<String, User> userMap) {
    super(sc, guide, cancleNum, content);
    this.userMap = userMap;
  }

  @Override
  public boolean validator(String[] input) {
    boolean isValid = false;
		if (userMap.containsKey(input[0])) {
			System.out.println("이미 존재하는 ID입니다.");
		} else if (!Pattern.matches("^[a-z0-9]{6,16}+$", input[0])) {
			System.out.println("영문자 숫자 조합 6-16글자로 입력해주세요.");
		} else isValid = true;

		return isValid;
  }
  
}
