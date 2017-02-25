package vue;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class SoundThread implements Runnable{

	private AudioInputStream as;
	private Clip c;
	
	public SoundThread(Niveau n) {
		// TODO Auto-generated constructor stub
	}


    private String fichier = "Sprites/Musique3.wav";

    public void play() {
        Thread t = new Thread(this);
        t.start();
    }

    public void run(){
    	playSound();
    }

    private void playSound() {
    	    try {
    	    	java.net.URL url = this.getClass().getResource(fichier);
    	        as = AudioSystem.getAudioInputStream(url);
    	        DataLine.Info info = new DataLine.Info(Clip.class, as.getFormat());
    	        c = (Clip)AudioSystem.getLine(info);
    	        c.open(as);
    	        c.loop(-1);
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	    }
        //playSound(fileName);
    }
}
