package modele;

import java.awt.Point;
import java.util.List;
import vue.Niveau;


public class GoblinStrat extends Strategie {
	
	int pas;

	public GoblinStrat() {
		super(new Point(-2,0));
		pas = 0;
		// TODO Auto-generated constructor stub
	}

	public int eval(Entite e,Entite [][] tiles, List<Entite> l, List<EntiteTrace> lTrace){
		pas++;
		if(pas == 100){
			deplacement.x = deplacement.x * -1;
			pas = 0;
		}
		return Physique.move(e, tiles, l, lTrace);
	}

}
