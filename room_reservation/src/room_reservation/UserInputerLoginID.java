package room_reservation;

import java.util.Map;
import java.util.Scanner;

public class UserInputerLoginID extends UserInputer{
  private Map<String,User> userMap;

  public UserInputerLoginID(Scanner sc, String guide, String cancleNum, String content, Map<String, User> userMap) {
    super(sc, guide, cancleNum, content);
    this.userMap = userMap;
  }

  @Override
  public boolean validator(String[] input) {
		boolean isValid = false;
		if (!userMap.containsKey(input[0])) {
			System.out.println("존재하지 않는 ID입니다.");
		} else isValid = true;

		return isValid;
  }
  
}
