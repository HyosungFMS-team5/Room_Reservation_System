package room_reservation;

import java.util.Scanner;

public class UserInputerLoginPW extends UserInputer{

  public UserInputerLoginPW(Scanner sc, String guide, String cancleNum, String content) {
    super(sc, guide, cancleNum, content);
  }

  @Override
  public boolean validator(String[] input) {
    boolean isValid = false;
		if (!input[0].equals(input[1])) {
			System.out.println("P비밀번호가 일치하지 않습니다.");
		} else isValid = true;

		return isValid;
  }
  
}
