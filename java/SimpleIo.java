import java.util.Scanner;
//javac Main.java Account.java HotelOwner.java Review.java Hotel.java Administrator.java SimpleIo.java
public class SimpleIo{
	
	public static String readLine(String text) {
        Scanner scan = new Scanner(System.in);
        System.out.print(text);
        return scan.next();
    }
    
    public static String readLine() {
        Scanner scan = new Scanner(System.in);
        return scan.next();
    }
}
