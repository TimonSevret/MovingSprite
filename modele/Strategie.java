package modele;

import java.awt.Point;
import java.util.List;

public class Strategie implements Eval{
	
	public Point deplacement;
	public Boolean enVols;
	
	public 	Strategie(Point deplacement){
		this.deplacement = deplacement;
		enVols = true;	
	}
	
	public int eval(Entite e,Entite [][] tiles, List<Entite> l, List<EntiteTrace> lTrace){
		return Physique.move(e, tiles, l, lTrace);
	}

}
