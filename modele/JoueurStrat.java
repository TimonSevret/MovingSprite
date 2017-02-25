package modele;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;

public class JoueurStrat extends Strategie{
	
	public JoueurStrat(){
		super(new Point(0,0));
	}
	
	public int eval(Entite e,Entite [][] tiles, List<Entite> l, List<EntiteTrace> lTrace){
		return Physique.move(e, tiles, l, lTrace );
	}
	
	//Les classes suivantes heritants de AbstractAction servent à definir le 
	//comportement du gobelin en reponse au touches Z, Q et D
	
	public class ActionDeplacementPressedD extends AbstractAction {
		private static final long serialVersionUID = 1L;
		
		public ActionDeplacementPressedD (String str){
			super (str);
		}
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (deplacement.x < 1) {
				deplacement.x = deplacement.x+2;
			}
		}
		
	}
	
	public class ActionDeplacementReleasedD extends AbstractAction {
		private static final long serialVersionUID = 1L;
		
		public ActionDeplacementReleasedD (String str){
			super (str);
		}
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (deplacement.x > 0) {
				deplacement.x = deplacement.x-2;
			}
		}
		
	}
	
	public class ActionDeplacementPressedQ extends AbstractAction {
		private static final long serialVersionUID = 1L;
		
		public ActionDeplacementPressedQ (String str){
			super (str);
		}
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (deplacement.x > -1) {
				deplacement.x = deplacement.x-2;
			}
		}
		
	}
	
	public class ActionDeplacementReleasedQ extends AbstractAction {
		private static final long serialVersionUID = 1L;
		
		public ActionDeplacementReleasedQ (String str){
			super (str);
		}
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (deplacement.x < 0) {
				deplacement.x = deplacement.x+2;
			}
		}
		
	}
	
	public class ActionDeplacementPressedZ extends AbstractAction {
		private static final long serialVersionUID = 1L;
		
		public ActionDeplacementPressedZ (String str){
			super (str);
		}
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (!enVols) {
				saut();
			}
		}
		
	}
	
	public void saut(){
		deplacement.y = -25;
	}
}
