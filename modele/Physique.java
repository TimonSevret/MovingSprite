package modele;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Physique {
	
	public static int move(Entite e, Entite [][] tiles, List<Entite> l, List<EntiteTrace> lTrace) {
		if(e.getStrat().enVols)
			e.getStrat().deplacement.y += 2;
		return colision(e,tiles,l, lTrace);
	}
	
	private static int colision(Entite e, Entite [][] tiles, List<Entite> l, List<EntiteTrace> lTrace) {
		Point mouve = new Point(e.getStrat().deplacement.x,e.getStrat().deplacement.y);
		if(e.getStrat().enVols)
			mouve.x=mouve.x*2;
		Rectangle r = new Rectangle(0,0,0,0);
		if(mouve.x < 0){
			r.x = e.getPosX()+mouve.x;
			r.width = mouve.x*-1;
		}else{
			r.x = e.getPosX();
			r.width = mouve.x+e.getWidth();
		}
		if(mouve.y < 0){
			r.y = e.getPosY()+mouve.y;
			r.height = mouve.y*-1; 
		}else{
			r.y = e.getPosY();
			r.height = mouve.y+e.getHeight(); 
		}
		List<Entite> lent = new LinkedList<Entite>();
		Iterator<Entite> it = l.iterator();
		for(int i=0; i < l.size();i++){
			Entite ent = it.next();
			if(r.intersects(ent.getHitBox()) && !ent.equals(e)){
				lent.add(ent);
			}
		}
		
		List<EntiteTrace> listeTrace = new LinkedList<EntiteTrace>();
		Iterator<EntiteTrace> traceIterator = lTrace.iterator();
		for(int i=0; i < lTrace.size();i++){
			EntiteTrace ent = traceIterator.next();
			if(r.intersects(ent.getHitBox()) && !ent.equals(e)){
				listeTrace.add(ent);
			}
		}
		
		boolean possibleX = true;
		boolean possibleY = true;
		
		while(possibleX || possibleY){
			if(mouve.x != 0)
				if(mouve.x > 0)//mouv vers droite 
					if( (e.getPosX()+e.getWidth()+1)/25 < tiles.length){
						boolean b=true;
						for(int i=0; i<(e.getHeight());i++)
							if(tiles[(e.getPosX()+e.getWidth()+1)/25][(e.getPosY()+i)/25].isSolid()){
								b =false;
								break;
							}
						if(b){
							e.setPosition(e.getPosX()+1, e.getPosY());
							possibleX = true;
							mouve.x += -1;
						}else
							possibleX = false;
					}else{
						possibleX = false;
					}
				else//mouv vers gauche
					if((e.getPosX()-1)/25 > -1){
						boolean b=true;
						for(int i=0; i<(e.getHeight());i++)
							if(tiles[(e.getPosX()-1)/25][(e.getPosY()+i)/25].isSolid()){
								b =false;
								break;
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
			else
				possibleX = false;
			if(mouve.y != 0)
				if(mouve.y >0)//mouv vers bas
					if((e.getPosY()+e.getHeight()+1)/25 < tiles[0].length){
						boolean b=true;
						for(int i=0; i<(e.getWidth());i++)
							if(tiles[(e.getPosX()+i)/25][(e.getPosY()+e.getHeight()+1)/25].isSolid()){
								b =false;
								break;
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
				else//mouv vers haut
					if( (e.getPosY()-1)/25 > -1){
						boolean b=true;
						for(int i=0; i<(e.getWidth());i++)
							if(tiles[(e.getPosX()+i)/25][(e.getPosY()-1)/25].isSolid()){
								b =false;
								break;
							}
						if(b){
							e.setPosition(e.getPosX(), e.getPosY()-1);
							possibleY = true;
							mouve.y += 1;
						}else
							possibleY = false;
					}else
						possibleY = false;
			else
				possibleY = false;
			it = lent.iterator();
			for(int i=0; i < lent.size();i++){
				Entite ent = it.next();
				if(e.getHitBox().intersects(ent.getHitBox())){
					e.deces();
				}
			}
			
			traceIterator = listeTrace.iterator();
			for(int i=0; i < listeTrace.size();i++){
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
		for(int i=0; i<(e.getWidth());i++)
			if(((e.getPosX()+i)/25<tiles.length && (e.getPosY()+e.getHeight()+1)/25<tiles[0].length)  && tiles[(e.getPosX()+i)/25][(e.getPosY()+e.getHeight()+1)/25].isSolid()){
				e.getStrat().enVols =false;
				break;
			}
		return 1;
	}

}
