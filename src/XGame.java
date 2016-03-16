import javax.swing.JFrame;
import java.io.FileNotFoundException;

public class XGame extends JFrame{

	public XGame() throws FileNotFoundException{
		super("XGame");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		XGame2 XGame2 = new XGame2();
		add(XGame2);
		setVisible(true);
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		new XGame();
		XGame2.timer.start();
	}
}