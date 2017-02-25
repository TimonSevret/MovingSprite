package vue;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import modele.Entite;
import modele.EntiteTrace;
import modele.GoblinStrat;
import modele.JoueurStrat;
import modele.Physique;
import modele.Strategie;
import modele.StrategieTrace;

public class Niveau extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = 1L;

	public static SpriteStocker stock = new SpriteStocker("Sprites/TableauSprite.png");

	public Entite[][] entite;
	public LinkedList<Entite> mob;
	public Entite joueur;
	public LinkedList<EntiteTrace> trace;
	
	private boolean traceAutorise = true; 
	private int positionXCLickSouris = 0;
	private int positionYCLickSouris = 0;
	private int positionXLacheSouris = 0;
	private int positionYLacheSouris = 0;
	private int tailleBlockTrace = 5;
	private int ttlTrace = 75;
	
	public Niveau(){
		entite = new Entite[50][50];
		mob = new LinkedList<Entite>();
		trace = new LinkedList<EntiteTrace>();
		joueur = new Entite(100,200,21,53,this,true,new JoueurStrat(),stock.get("gob"));
		for (int i =0;i < entite.length;i++){
			for(int j = 0 ; j < entite[0].length;j++){	
				ajouterEntite(new Entite(j*25,i*25,25,25,this,false,new Strategie(new Point(0,0)),stock.get("ciel")));
			}
		}
		for (int j = 0; j<800/25;j++){
			for (int i = 0;i<600/26;i++){
					ajouterEntite(new Entite(j*25,i*25,25,25,this,false,new Strategie(new Point(0,0)),stock.get("ciel")));
				if (i == 1 || i == 3 || i == 7 || i== 13){
					ajouterEntite(new Entite(j*25,i*25,25,25,this,false,new Strategie(new Point(0,0)),stock.get("nuage")));
				}
			}
		}
		for (int i =0; i<800/25; i++){
			ajouterEntite(new Entite(i*25,600-50,25,25,this,true,new Strategie(new Point(0,0)),stock.get("sol")));
		}
		for (int i =0; i<800/25; i++){
			ajouterEntite(new Entite(i*25,600-25,25,25,this,true,new Strategie(new Point(0,0)),stock.get("mur")));
		}
		for (int i = 0; i<800/25; i++){
			if (i == 1 || i == 3 || i == 7 || i== 13){
				ajouterEntite(new Entite(i*25,600-75,25,25,this,true,new Strategie(new Point(0,0)),stock.get("sol")));
				ajouterEntite(new Entite(i*25,600-50,25,25,this,true,new Strategie(new Point(0,0)),stock.get("mur")));
			}
		}
		
		mob.add(new Entite(300,100,21,53,this,true,new GoblinStrat(),stock.get("gob")));
		
		/*
		 * Les instructions suivantes servent à définir le comprtement du joueur
		 * en réaction à certaines touchent du clavier.
		*/		
		
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed D"), "pressed D");
		this.getActionMap().put("pressed D", 
				((JoueurStrat)(joueur.getStrat())).new ActionDeplacementPressedD("pressed D")  );
	
		this.getInputMap().put(KeyStroke.getKeyStroke("released D"), "released D");
		this.getActionMap().put("released D", 
				((JoueurStrat)(joueur.getStrat())).new ActionDeplacementReleasedD("released D")  );
	
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed Q"), "pressed Q");
		this.getActionMap().put("pressed Q", 
				((JoueurStrat)(joueur.getStrat())).new ActionDeplacementPressedQ("pressed Q")  );
	
		this.getInputMap().put(KeyStroke.getKeyStroke("released Q"), "released Q");
		this.getActionMap().put("released Q", 
				((JoueurStrat)(joueur.getStrat())).new ActionDeplacementReleasedQ("released Q")  );
	
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed Z"), "pressed Z");
		this.getActionMap().put("pressed Z", 
				((JoueurStrat)(joueur.getStrat())).new ActionDeplacementPressedZ("pressed Z")  );

		this.addMouseListener(this);
	}
	
	public void paint(Graphics g){
		for (int i =0;i < entite.length;i++){
			for(int j = 0 ; j < entite[0].length;j++){	
				Entite e = entite[i][j];
				e.rendu(g,(300 -joueur.getWidth())-joueur.getPosX(),300 -joueur.getHeight()-joueur.getPosY(),getWidth(),getHeight());
			}
		}
		
		Iterator<Entite> it = mob.iterator();
		for(int i =0;i<mob.size();i++){
			it.next().rendu(g,(300 -joueur.getWidth())-joueur.getPosX(),300 -joueur.getHeight()-joueur.getPosY(),getWidth(),getHeight());
		}
		joueur.rendu(g,(300 -joueur.getWidth())-joueur.getPosX(),
				300 -joueur.getHeight()-joueur.getPosY(),getWidth(),getHeight());
		
		Iterator<EntiteTrace> itTrace = trace.iterator();
		for(int i =0;i<trace.size();i++){
			itTrace.next().rendu(g,(300 -joueur.getWidth())-joueur.getPosX(),300 -joueur.getHeight()-joueur.getPosY(),getWidth(),getHeight());
		}
	}
	
	public void bouger(){
		Iterator<Entite> it = mob.iterator();
		for(int i =0;i<mob.size();i++){
			it.next().eval(entite, mob, trace);
		}
		joueur.eval( entite, mob, trace);
		
		ListIterator<EntiteTrace> itTrace = trace.listIterator();
		for (int i =0;i<trace.size();i++){
			if (itTrace.next().doitDeceder()){
				itTrace.previous();
				itTrace.remove();
			}
		}
	}
	
	public Entite ajouterEntite(Entite e){
		entite[e.getPosX()/25][e.getPosY()/25] = e;
		//System.out.println(" entite contenu dans la case "+ e.getPosX()/25+ ","+e.getPosY()/25 + " = " + e.getId());
		return e;
	}
	
	public void supprimerEntite(Entite e){
		if(joueur.equals(e)){
			joueur.getHitBox().x = 100;
			joueur.getHitBox().y = 400;
		}else
			if(mob.contains(e))
				mob.remove(e);
			else
				entite[e.getPosX()/25][e.getPosY()/25] = ajouterEntite(new Entite(e.getPosX(),e.getPosY(),25,25,this,false,new Strategie(new Point(0,0)),stock.get("gob")));
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent click) {
		// TODO Auto-generated method stub
		if (traceAutorise) {
			positionXCLickSouris = click.getX();
			positionYCLickSouris = click.getY();
			traceAutorise = false;
		}
	}

	public void mouseReleased(MouseEvent click) {
		// TODO Auto-generated method stub
		if (!traceAutorise){
			positionXLacheSouris = click.getX();
			positionYLacheSouris = click.getY();
			tracerLigne();
			traceAutorise = true;
		}
	}
		
	private void tracerLigne (){
		//determiner le plus grand des décalages de coordonnees
		int deltaX;
		if (positionXCLickSouris <= positionXLacheSouris){
			deltaX = positionXLacheSouris - positionXCLickSouris;
		}else{
			deltaX = positionXCLickSouris - positionXLacheSouris;
		}
		
		int deltaY;
		if (positionYCLickSouris < positionYLacheSouris){
			deltaY = positionYLacheSouris - positionYCLickSouris;
		}else{
			deltaY = positionYCLickSouris - positionYLacheSouris;
		}
		
		int curseurX = positionXCLickSouris;
		int curseurY = positionYCLickSouris;
		
		//si l'ecart d'ordonnee est plus grand, les blocks s'empilent
		//verticalement
		if (deltaX <= deltaY){
			int decalageHorizontal = deltaX / (deltaY / tailleBlockTrace);
						
			for (int i = 0; i < deltaY; i += tailleBlockTrace){
				trace.add(new EntiteTrace(curseurX, curseurY, tailleBlockTrace,
						this, new StrategieTrace(),
						new Sprite(stock.getSprite(4, 1)), ttlTrace));
				
				//il faut toujours tracer du click vers le lache
				if (positionXCLickSouris <= positionXLacheSouris){
					curseurX += decalageHorizontal;
				}else{
					curseurX -= decalageHorizontal;
				}
				
				if (positionYCLickSouris <= positionYLacheSouris){
					curseurY += tailleBlockTrace;
				}else{
					curseurY -= tailleBlockTrace;
				}
			}
		
			
		//si l'ecart d'abscisse est plus grand, les blocks s'empilent
		//horizontalement
		}else{
			int decalageVertical = deltaY / (deltaX / tailleBlockTrace);
			
			for (int i = 0; i < deltaX; i += tailleBlockTrace){
				trace.add(new EntiteTrace(curseurX, curseurY, tailleBlockTrace, 
						this, new StrategieTrace(), 
						new Sprite(stock.getSprite(4, 1)), ttlTrace));
				
				//il faut toujours tracer du click vers le lache
				if (positionXCLickSouris <= positionXLacheSouris){
					curseurX += tailleBlockTrace;
				}else{
					curseurX -= tailleBlockTrace;
				}
				
				if (positionYCLickSouris <= positionYLacheSouris){
					curseurY += decalageVertical;
				}else{
					curseurY -= decalageVertical;
				}
			}
			
		}
		
}
}
