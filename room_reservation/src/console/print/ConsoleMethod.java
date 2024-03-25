package console.print;

public class ConsoleMethod {

	public static void StartSystem1() {
		String asciiArt = """ 
				-----------------------------------------------------------
				|⢌⢢⠱⡨⢢⠱⡨⢢⠱⡘⢔⢅⠕⢜⠌⢆⢣⢑⢕⠸⡨⢪⠨⡪⡘⡌⡪⡸⢨⠪⡘⡌⢎⢜⢌⢎⢜⢌⢎⢪⢸⢨⠪⡊⡎⡪⡪⢪⠪⡪|
				|⡐⡑⡌⡌⢆⢕⠸⡐⢅⠣⡑⢔⢍⠢⢣⢑⠱⡨⡂⢇⢪⠢⢣⠱⡘⡌⣪⣘⣔⢕⡕⣕⡅⢇⡪⣢⢱⡘⣴⡱⡡⣃⣇⣣⡣⣱⣜⢜⠜⡜|
				|⢊⠢⣳⡎⣦⣾⠞⣨⣾⠿⢾⣶⣂⢣⣷⠿⢷⠪⡨⢪⣾⣮⠢⠣⡑⢜⣼⣭⣿⢺⣯⣿⢜⠸⣿⡫⣿⡋⣾⡇⡇⢏⣿⣏⢝⢼⣷⡥⢣⠱|
				|⠡⡑⣽⣿⣿⡐⢅⣿⡧⡑⢅⢻⣧⢙⢿⢾⣦⡱⢨⣿⢣⢿⣇⠣⡑⢕⡴⡟⢕⢹⠧⢿⢊⢺⠿⡾⠿⡞⢾⡇⠧⡿⡋⣛⢷⢹⡗⢜⢸⢘|
				|⢑⠌⣾⡇⢝⢿⣦⠹⣷⣮⣶⡿⠣⣪⣦⣵⣿⠇⣿⡟⡛⠻⣿⡜⡨⡂⡪⣿⣛⢛⡛⣿⢅⢕⢱⣟⢛⢛⣻⡎⡜⢴⣟⡛⡝⣻⡮⡊⢆⢣|
				|⠢⡑⠌⢜⠐⢅⠅⢕⢐⠅⢕⠨⡊⠔⢌⠪⡐⢅⠕⡰⡘⡌⡊⡢⠢⡑⢔⠫⡛⢝⠫⡛⢅⠆⡍⢝⢛⢛⠍⡕⢌⠪⡙⡝⡻⡙⢕⠸⡨⢢|
				|⠨⡐⢅⠅⢕⠡⢊⠢⠢⡡⢑⠌⡢⢑⠅⡪⢐⠅⣪⢎⠝⢜⢾⢴⣕⡌⡢⢑⠌⡢⡑⢌⠢⡑⢌⠢⡑⢔⢑⠌⢆⠣⡑⢌⠢⡊⢆⠣⡊⡪|
				|⢌⢂⠅⡪⢐⠡⡑⠌⢌⠢⠡⡊⢄⠕⡨⢐⢡⢮⢫⠊⠈⡐⠌⠕⢝⢟⣿⣶⣵⣰⠨⠢⡑⢌⠢⡃⢅⠕⢌⢊⠢⡱⢘⢌⠪⡨⡂⡣⡑⡌|
				|⡐⠔⡁⡢⠡⢊⢐⠅⡑⢌⠌⡂⢅⠢⢊⢔⡯⠕⢁⠐⠀⠂⠠⢁⠑⠌⢆⠟⣞⣿⣿⢿⣮⣦⣑⢌⠢⡡⡑⢌⢌⠢⡑⡐⠅⣆⣊⠢⡊⢌|
				|⠢⢑⢐⠌⢌⢂⠢⢑⠨⢂⠌⡢⢑⢨⢮⡳⢉⠐⢀⠐⢈⠈⡀⢂⠂⡁⢂⠑⢌⠲⡹⣻⣽⣟⣿⢿⣷⣶⣬⣂⡢⢑⢌⠢⢑⠢⡅⠕⢌⠢|
				|⠕⡐⡐⢌⢐⠄⡑⡐⡡⢂⠅⢢⢱⢝⢜⠀⢂⠐⠀⢂⠀⡂⢐⢐⠐⡀⢂⠐⡀⢑⠨⠢⡓⡟⣿⢿⣷⢿⣻⣟⣿⣷⣳⣼⣵⡫⡎⢜⢐⢅|
				|⡐⡐⠨⢐⢐⠨⡐⡐⡐⠔⡨⡞⡭⠃⡂⠐⢠⡢⠥⠦⢲⠺⡘⠪⢐⢀⢂⠀⡂⠐⠄⠡⢈⢪⣻⣟⣿⢿⣿⣻⣿⣾⣾⣟⣿⢿⣾⣕⢯⣞|
				|⢐⠨⢈⢂⠂⠅⡂⡂⡊⣼⢹⠪⠈⠔⡀⠌⢰⡹⡨⠨⠠⡑⠨⢈⢐⠠⠠⠐⡀⠅⠅⠨⢀⢸⣺⢯⡫⡟⡷⡿⣷⢿⣽⢿⣻⣿⣯⣿⣗⣗|
				|⠐⡈⡐⠠⠡⡁⡂⡂⡳⡕⢁⠂⠡⡁⠄⢐⠰⡹⡠⠡⢁⢂⠡⠐⡐⡈⠄⠂⡐⠨⡀⠅⢂⢰⣻⢮⢺⡸⡪⡺⡸⡹⡹⡻⡻⡿⣾⣟⣿⣿|
				|⣰⣄⠄⠡⢁⢐⢀⠂⢅⠐⠠⠨⠐⢄⠡⠐⢨⡪⡂⠅⡂⠢⠀⠅⡐⠠⠂⢁⢐⠐⠄⢊⠠⠰⣽⡳⡕⡵⡱⣝⢪⡣⣫⢪⢺⡪⡺⣪⣻⢹|
				|⢼⣺⣌⠨⢀⠂⠄⠅⡂⠄⡁⡂⠅⡂⡂⠌⢰⢱⠨⢐⠠⠡⢈⠐⠨⠠⠡⠐⠠⠨⠐⡐⠠⠱⣽⡺⡜⡎⡮⡪⡎⡮⡪⡎⡧⡳⡹⣜⢮⡪|
				|⣳⡯⣿⣳⣆⠡⠁⢅⣢⠂⠄⠂⡂⡂⡂⠨⢘⠼⠨⡐⠄⠕⠐⠌⡊⠄⠅⠨⠈⠄⠅⡂⠅⢸⢽⣺⡱⡕⡇⡗⣕⢵⢹⢜⢮⡪⡳⣕⢗⣕|
				|⠿⡽⡿⣽⣯⣷⢕⡞⠌⡐⠈⠔⢐⢐⠄⡁⡂⢂⠂⡂⠨⠠⠁⠅⠢⠈⠔⢁⠑⠨⢐⠠⠑⢨⣻⢮⢎⢮⢪⡣⡇⣇⢯⢪⢮⡪⡫⡮⣳⢱|
				|⠢⡊⢔⠰⣐⢼⠍⠌⠀⠠⠁⠅⡂⠢⢂⠐⡨⠀⡂⢂⠁⠅⠨⠨⠨⠨⡈⠠⠨⠨⢀⢂⢑⠨⣾⡳⣣⢳⡱⣣⢳⢱⢝⡜⡮⡪⣏⢞⡵⣣|
				|⢐⠌⡢⢱⢪⠑⠀⢀⠀⠡⠁⠅⡂⠕⡁⡐⠄⠡⢐⠐⡈⢌⢈⡂⡅⡅⣂⡡⢑⠨⢐⠰⠐⢌⢷⠯⡺⢜⠼⡜⣎⢧⢳⡱⣝⢞⢎⣗⢽⡪|
				|⠡⡊⢔⠡⠁⠀⢀⠀⢲⠅⠁⠑⠀⠑⢴⡲⢯⠈⠀⠡⡹⡽⡫⡯⣻⠩⠩⡁⢀⠀⢀⠀⢀⠐⣽⠱⡨⡂⡣⡑⡢⡱⡑⡍⢎⠭⡳⡕⣿⣼|
				|⡂⢎⠢⡑⡁⠀⡀⠀⣪⠃⠀⠁⠀⠂⢕⣝⢎⠀⠠⠀⡗⡯⣫⡚⣾⠈⢔⠐⠀⢀⠀⠄⠀⠨⣺⢱⢐⠕⢌⡊⢆⢎⢢⠣⡣⡣⡫⣚⢞⣗|
				|⢊⢢⠱⡑⡀⠀⡀⠀⢜⡅⠈⠀⠁⢀⢣⢇⢗⠀⠠⠀⡇⣟⢮⡪⣺⠌⡌⡊⠀⡀⠠⠀⠂⠡⢽⢪⢐⢕⢑⠜⡌⡦⡣⡣⡣⢮⢮⢳⢽⣳|
				|⡸⡪⣎⢮⠀⢀⠀⠀⢵⠅⢀⠈⢀⠀⢎⢗⢕⠀⠠⠀⡏⡮⣗⡕⣽⠨⡨⡢⠀⠀⠄⠀⠄⢘⢺⢕⠌⡆⡕⢕⢕⢕⡇⣏⢮⢣⡳⣝⢾⣻|
				|⣪⢯⡪⣞⠀⡀⠀⠂⡸⡅⠀⢀⠀⠠⢹⠪⠣⠐⠀⢀⢯⠫⡺⡘⢎⠎⡔⡢⠀⠁⡀⠐⠀⢐⢽⢅⢣⢪⢸⢸⢸⡱⣝⢼⡸⣕⢯⣞⢽⣻|
				|⣾⣟⢞⣞⠄⠀⠀⠂⣸⡂⠄⠀⠄⠂⠀⠠⠐⠀⠐⠀⠀⡀⠀⠠⠀⠄⢀⠠⠐⠀⠀⠄⠈⠠⣻⡱⡑⡜⡜⡜⡜⣞⢮⣣⣻⣪⢷⣝⢮⡯|
				|⣟⢷⠻⢞⠖⠖⠜⢔⠜⡂⡤⢤⢄⡤⣐⣠⢠⣀⣂⣀⣁⣀⣐⢀⡠⣀⢀⡀⣀⢀⢁⠀⠄⢑⢺⣪⣸⣸⣸⣸⣪⣷⣽⣞⣾⣮⣷⣟⣮⢟|
				|⢀⢂⠑⡀⢂⢑⢨⢐⣐⣘⣘⡑⣃⠫⡊⡎⡓⡑⡣⠳⠱⠣⡳⠝⠞⠮⠫⢞⠵⡫⢏⢯⢻⢽⡿⡿⡷⡿⣻⢟⡿⣻⣻⣻⣟⣽⢯⡿⡾⣿|
				|⠠⢂⠡⠂⢅⠂⠅⡡⢂⢂⠂⠅⢅⠩⡈⡊⡘⡘⠌⡙⡉⢋⠚⡘⡃⠫⠚⡂⠳⢘⢒⠲⠰⠲⠰⡢⠺⡪⠎⢇⠗⠕⠜⠔⠕⠕⠝⢜⠩⠪|
				|⡪⢐⠨⠨⡂⢌⢂⠢⢂⢂⢊⠌⡐⡐⡐⠔⡐⠄⠕⡐⢌⢐⠌⡐⠌⢌⢂⢊⠌⡂⡂⡢⠡⠡⡑⡐⡁⡂⡑⢔⠨⠨⡨⢊⢘⠌⡊⠔⡡⠅|
				|⠪⡐⡡⡑⢌⢂⠪⡈⡂⡊⠔⡨⢐⠌⠔⡡⢊⠌⡪⢐⠔⡡⢊⠌⡊⡢⠑⢄⠕⡐⠔⡐⡡⡑⢌⢂⠅⡪⢈⠢⠡⡃⠪⡐⢅⠪⠨⡊⢔⠡|""";
		
		System.out.println(asciiArt);
	}
	
	public static void StartSystem2() {
		String asciiArt = """
|				⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀ |⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
|⠀⠀⠀⠀⡇⠉⠈⠁⠉⠈⠁⠉⠈⠁⠉⠈⠁⠉⠈⠁⠉⡆⠀⠀⠀⠀⠀⠀⢕⠉⠈⠁⠉⠈⠁⠉⠈⠁⠉⠈⠁⠉⠈⠁⠉⡆⠀⠀⠀⠀|""";
		
		System.out.println(asciiArt);
	}
	
	public static void StartSystem3() {
		String asciiArt = """
|⠀⠀⠀⠀⢇⢄⢄⢄⢄⢄⢄⢄⢄⢄⢄⢄⢄⢄⢄⢄⢄⠇⠀⠀⠀⠀⠀⠀⢵⢠⢠⢠⢠⢠⢠⢠⢠⢠⢠⢠⢠⢠⢠⢠⢀⠇⠀⠀⠀⠀|
| ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀                                       |
-----------------------------------------------------------⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀	
				""";
		
		System.out.println(asciiArt);
	}
}