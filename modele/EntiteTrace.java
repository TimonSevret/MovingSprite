package modele;

import vue.Niveau;
import vue.Sprite;

public class EntiteTrace extends Entite{

	private int ttl = 75;
	public static int tailleBlockTrace = 5;
	
	public EntiteTrace(int posX, int posY, Niveau niveau, 
			Strategie strat, Sprite sprite) {
		super(posX, posY, tailleBlockTrace, tailleBlockTrace, niveau, true, strat, sprite);
		// TODO Auto-generated constructor stub
	}

	public boolean doitDeceder (){
		if (ttl < 1){
			return true;
		}
		ttl--;
		return false;
	}
	
}
