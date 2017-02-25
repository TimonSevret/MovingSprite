package modele;

import java.util.List;

public interface Eval {

	public int eval(Entite e,Entite [][] tiles, List<Entite> l, List<EntiteTrace> lTrace);
}
