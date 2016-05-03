import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

public class Odczyt{
	public static String t;
	public static String Wczytaj() throws FileNotFoundException{
		File file = new File("bin/img/HighScore.txt");
		Scanner in = new Scanner(file);

		String zdanie = in.nextLine();
		return zdanie;
	}
	
	public static void Zapisz() throws FileNotFoundException{
	      PrintWriter zapis = new PrintWriter("bin/img/HighScore.txt");
	      zapis.print(Integer.toString(Player.score));
	      zapis.close();
	  }
}
