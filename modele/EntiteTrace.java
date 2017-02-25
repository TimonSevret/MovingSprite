package modele;

import vue.Niveau;
import vue.Sprite;

public class EntiteTrace extends Entite{

	private int ttl;
	
	public EntiteTrace(int posX, int posY, int taille, Niveau niveau, 
			Strategie strat, Sprite sprite, int ttl) {
		super(posX, posY, taille, taille, niveau, true, strat, sprite);
		// TODO Auto-generated constructor stub
		this.ttl = ttl;
	}

	public boolean doitDeceder (){
		if (ttl < 1){
			return true;
		}
		ttl--;
		return false;
	}
	
}
