package vue;
import java.awt.Color;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;


public class Main{
	
	public static void main (String[] args){
		Niveau n = null;
		try {
			n = new Niveau("Level1");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JFrame frame = new JFrame();
		frame.setSize(600,600);
		frame.setContentPane(n);
		frame.setBackground(Color.black);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);		
		frame.setVisible(true);
		gamethread gt = new gamethread(n);
		gt.start();
		(new SoundThread(n)).play();
	}
}
