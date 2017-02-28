package vue;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.WindowConstants;


public class Main{
	
	public static void main (String[] args){
		Niveau n = new Niveau();
		JFrame frame = new JFrame();
		frame.setSize(600,600);
		//n.setSize(frame.getWidth(), frame.getHeight());
		frame.setContentPane(n);
		frame.setBackground(Color.black);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);		
		frame.setVisible(true);
		gamethread gt = new gamethread(n);
		gt.start();
		(new SoundThread(n)).play();
	}
}
