package vue;

public class gamethread extends Thread {
	
	Niveau n;
	
	public gamethread(Niveau n){
		this.n= n;
	}
	
	public void run(){
		while(true){
			n.repaint();
			n.bouger();
			try {
				sleep(15);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
