package room_reservation;

import java.util.Scanner;
import java.util.regex.Pattern;

public class UserInputerSignupPhone extends UserInputer{

  public UserInputerSignupPhone(Scanner sc, String guide, String cancleNum, String content) {
    super(sc, guide, cancleNum, content);
  }

  @Override
  public boolean validator(String[] input) {
		boolean isValid = false;
		if(!Pattern.matches("^\\d{3}-\\d{4}-\\d{4}$", input[0])) {
			System.out.println("형식(OOO-OOOO-OOOO)에 맞게 입력해주세요.");
		} else isValid = true;

		return isValid;
  }
}
