package room_reservation.user.inputer;

import java.util.Scanner;

import console.ConsoleMethod;

public class UserInputerLoginPW extends UserInputer{

  public UserInputerLoginPW(Scanner sc, String guide, String cancleNum, String content) {
    super(sc, guide, cancleNum, content);
  }

  @Override
  public boolean validator(String[] input) {
    boolean isValid = false;
		if (!input[0].equals(input[1])) {
			System.out.println(ConsoleMethod.FONT_RED + "비밀번호가 일치하지 않습니다." + ConsoleMethod.RESET + ConsoleMethod.RESET);
		} else isValid = true;

		return isValid;
  }
  
}
