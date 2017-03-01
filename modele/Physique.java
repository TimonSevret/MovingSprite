package modele;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Physique {
	
	/*
	 * permet a une entite de ce deplacer
	 * \param e l'entité a deplacer
	 * \param tiles decors du niveau dans lequel evolue e
	 * \param l liste des entités existant dans le meme niveau que e
	 * \param lTrace liste des boucliers existant dans le meme niveau que e
	 * \pre e non null
	 */
	public static int move(Entite e, Entite [][] tiles, List<Entite> l, List<EntiteTrace> lTrace) {
		if(e.getStrat().enVols)
			e.getStrat().deplacement.y += 2;
		return colision(e,tiles,l, lTrace);
	}
	
	/*
	 * fait bouger l'entité e en prenant en compte les collisions
	 * \param e l'entité a deplacer
	 * \param tiles decors du niveau dans lequel evolue e
	 * \param l liste des entités existant dans le meme niveau que e
	 * \param lTrace liste des boucliers existant dans le meme niveau que e
	 * \pre e non null
	 * \pre tiles non null
	 * \pre l non null
	 * \pre lTrace non null
	 */
	private static int colision(Entite e, Entite [][] tiles, List<Entite> l, List<EntiteTrace> lTrace) {
		
		// mouve est une copie du deplacement de e
		// ce vecteur servira a effectuer ds operation sur le mouvemant de e sans influer sur ce dernier
		Point mouve = new Point(e.getStrat().deplacement.x,e.getStrat().deplacement.y);
		
		// si e est en l'aire on augmente sa vitesse afin de simuler un saut "realiste"
		if(e.getStrat().enVols)
			mouve.x=mouve.x*2;
		
		// afin de ne pas tester la colision de e avec toute les entités du niveau on construie un rectangle
		// comprenent e et sa position final theorique
		Rectangle r = new Rectangle(0,0,0,0);
		if(mouve.x < 0){
			r.x = e.getPosX()+mouve.x;
			r.width = mouve.x*-1 + e.getWidth();
		}else{
			r.x = e.getPosX();
			r.width = mouve.x+e.getWidth();
		}
		if(mouve.y < 0){
			r.y = e.getPosY()+mouve.y;
			r.height = mouve.y*-1+e.getHeight(); 
		}else{
			r.y = e.getPosY();
			r.height = mouve.y+e.getHeight(); 
		}
		
		//listeEntite servira a stocker les entités avec lesquel il faudra tester la collision avec e
		List<Entite> listeEntite = new LinkedList<Entite>();
		Iterator<Entite> it = l.iterator();
		for(int i=0; i < l.size();i++){
			Entite entite = it.next();
			// si entite intersect r alors il est dans le chemin de e
			if(r.intersects(entite.getHitBox()) && !entite.equals(e)){
				listeEntite.add(entite);
			}
		}
		
		//meme explication que listeEntite  -> fusion peut etre possible
		List<EntiteTrace> listeTrace = new LinkedList<EntiteTrace>();
		Iterator<EntiteTrace> traceIterator = lTrace.iterator();
		for(int i=0; i < lTrace.size();i++){
			EntiteTrace ent = traceIterator.next();
			if(r.intersects(ent.getHitBox()) && !ent.equals(e)){
				listeTrace.add(ent);
			}
		}
		
		//on bouge e unité par unité tant que cela est possible
		boolean possibleX = true;
		boolean possibleY = true;
		// ce i sert de compteur pour toutes les boucles 
		int i = 0;
		while(possibleX || possibleY){
			// si il n'y a aucun deplacement en x inutils de fair les calcules pour x
			if(mouve.x != 0)
				if(mouve.x > 0){
					//calcule pour le cas ou le deplacment en x est positifs donc vers la droite
					
					//test si il y a un risque que e sort du niveau
					if( (e.getPosX()+e.getWidth()+1)/25 < tiles.length){
						boolean b=true;
						i=0; 
						//test sur toute la taille de e, voirs si il peut bouger de la tete au pied
						// pour autoriser le deplacement de 1 unité
						while( i<e.getHeight() && b){
							if(tiles[(e.getPosX()+e.getWidth()+1)/25][(e.getPosY()+i)/25].isSolid()){
								b =false;
							}
							//les case du niveau ayant une taille de 25 il suffit de tester touts les 25 unités
							if(i < e.getHeight()-26)
								i+=25;
							else
								if(i == e.getHeight()-1 )
									i++;
								else
									//si e n'a pas une taille en multiple de 25 ce cas permet de forcer le test sur le reste
									i = e.getHeight()-1;
						}
						if(b){
							// si le deplacement est autoriser on actualise la position de e
							e.setPosition(e.getPosX()+1, e.getPosY());
							possibleX = true;
							mouve.x += -1;
						}else
							possibleX = false;
					}else{
						possibleX = false;
					}
				}else{
					//calcule pour le cas ou le deplacment en x est negatifs donc vers la gauche
					// voirs explication pour droite
					if((e.getPosX()-1)/25 > -1){
						boolean b=true;
						i=0; 
						while( i<e.getHeight() && b){
							if(tiles[(e.getPosX()-1)/25][(e.getPosY()+i)/25].isSolid()){
								b =false;
							}
							if(i < e.getHeight()-26)
								i+=25;
							else
								if(i == e.getHeight()-1 )
									i++;
								else
									i = e.getHeight()-1;
						}
						if(b){
							e.setPosition(e.getPosX()-1, e.getPosY());
							possibleX = true;
							mouve.x += 1;
						}else
							possibleX = false;
					}else{
						possibleX = false;
					}
				}
			else
				possibleX = false;
			
			// si il n'y a aucun deplacement en y inutils de fair les calcules pour y
			if(mouve.y != 0)
				if(mouve.y >0)
					//calcule pour le cas ou le deplacment en y est positfs donc vers le bas
					// voirs explication pour droite
					if((e.getPosY()+e.getHeight()+1)/25 < tiles[0].length){
						boolean b=true;
						i = 0;
						while( i < e.getWidth() && b){
							if(tiles[(e.getPosX()+i)/25][(e.getPosY()+e.getHeight()+1)/25].isSolid()){
								b =false;
							}
							if(i < e.getWidth()-26)
								i+=25;
							else
								if(i == e.getWidth()-1 )
									i++;
								else
									i = e.getWidth()-1;
						}
						if(b){
							e.setPosition(e.getPosX(), e.getPosY()+1);
							possibleY = true;
							mouve.y += -1;
						}else
							possibleY = false;
					}else{
						e.getStrat().deplacement.y = 0;
						possibleY = false;
					}
				else
					//calcule pour le cas ou le deplacment en y est negatifs donc vers le haut
					// voirs explication pour droite
					if( (e.getPosY()-1)/25 > -1){
						boolean b=true;
						i = 0;
						while( i < e.getWidth() && b){
							if(tiles[(e.getPosX()+i)/25][(e.getPosY()-1)/25].isSolid()){
								e.getStrat().deplacement.y = 0;
								b =false;
							}
							if(i < e.getWidth()-26)
								i+=25;
							else
								if(i == e.getWidth()-1 )
									i++;
								else
									i = e.getWidth()-1;
						}
						if(b){
							e.setPosition(e.getPosX(), e.getPosY()-1);
							possibleY = true;
							mouve.y += 1;
						}else
							possibleY = false;
					}else{
						e.getStrat().deplacement.y = 0;
						possibleY = false;
					}
			else
				possibleY = false;
			
			//apres avoirs fais un deplacement de e on test si il n'est pas en colision avec une autre entité 
			it = listeEntite.iterator();
			for(int j=0; j < listeEntite.size();j++){
				Entite ent = it.next();
				if(e.getHitBox().intersects(ent.getHitBox())){
					//si il est en colision on appele la methode pour gerer cette colison
					e.deces();//methode temporaire le temps de fair des methodes specifique
				}
			}
			
			//voirs pour les entités
			traceIterator = listeTrace.iterator();
			for(int j=0; j < listeTrace.size();j++){
				EntiteTrace ent = traceIterator.next();
				if(e.getHitBox().intersects(ent.getHitBox())){
					//flag
					//e est l'entite qui se déplace
					//c'est deces qui représente le comportement a adopter lorsque 
					//e va rencontrer le shield
					
					//e.refractionBoulette #le nom du truc qui va calculer l'angle 
					//de rebond
				}
			}
		}
		
		
		e.getStrat().enVols=true;
		i = 0;
		// on test si e possede du sols sous ses pieds
		while(i<e.getWidth()){
			if(((e.getPosX()+i)/25<tiles.length && (e.getPosY()+e.getHeight()+1)/25<tiles[0].length)  && tiles[(e.getPosX()+i)/25][(e.getPosY()+e.getHeight()+1)/25].isSolid()){
				e.getStrat().enVols =false;
				break;
			}
			
			//voir explication dans deplacment vers la droite
			if(i < e.getHeight()-26)
				i+=25;
			else
				if(i == e.getHeight()-1 )
					i++;
				else
					i = e.getHeight()-1;
		}
		//retour d'un int pour le moment inutils mais poura etre utiliser plus tard pour definirs l'animation a utiliser
		return 1;
	}

}
