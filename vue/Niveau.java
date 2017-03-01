package vue;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.KeyStroke;

import modele.Entite;
import modele.EntiteTrace;
import modele.JoueurStrat;
import modele.Strategie;
import modele.StrategieTrace;

public class Niveau extends JPanel implements MouseListener, Serializable {
	
	private static final long serialVersionUID = 1L;

	public SpriteStocker stock;

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

	@SuppressWarnings("resource")
	public Niveau(String path) throws FileNotFoundException{
		BufferedReader fichier = new BufferedReader(new FileReader(new File(path)));
    	String ligne;
    	try{
    		ligne = fichier.readLine();
    		if(!ligne.equals("Niveau:"))
    			throw new IllegalArgumentException("Niveau:\n fichier au mauvais format");
    		ligne = fichier.readLine();
    		StringTokenizer st;
    		if(ligne != null)
    			st = new StringTokenizer(ligne," ,");
    		else
    			throw new IllegalArgumentException("Niveau:\n description taille monde manquant");
    		ligne = st.nextToken();
    		if(!ligne.equals("Taille:"))
    			throw new IllegalArgumentException("Niveau:\n description Taille : monde manquant");
    		int x = 0;
    		int y = 0;
    		if(st.hasMoreTokens())
    			try{
    				x = Integer.parseInt(st.nextToken());
    			}catch(NumberFormatException e){
    				System.out.println("Niveau:\n erreur de taille x");
    				throw e;
    			}
    		else
    			throw new IllegalArgumentException("Niveau:\n taille du monde manquant");
    		if(st.hasMoreTokens())
    			try{
    				y = Integer.parseInt(st.nextToken());
    			}catch(NumberFormatException e){
    				System.out.println("Niveau:\n erreur de taille y");
    				throw e;
    			}
    		else
    			throw new IllegalArgumentException("Niveau:\n taille du monde manquant");
    		entite = new Entite[x][y];
    		ligne = fichier.readLine();
    		if(ligne == null)
    			throw new IllegalArgumentException("Niveau:\n tileSet manquant");
    		st = new StringTokenizer(ligne);
    		ligne = st.nextToken();
    		if(!st.hasMoreTokens())
    			throw new IllegalArgumentException("Niveau:\n taille tileSet manquant");
    		try{
    			x = Integer.parseInt(st.nextToken());
    		}catch(NumberFormatException e){
				System.out.println("Niveau:\n erreur de taille tileSet");
				throw e;
			}
    		try{
    			stock = new SpriteStocker(ligne,x);
    		}catch(Exception e){
    			throw new IllegalArgumentException("Niveau:\n tileSet manquant ou eroner");
    		}
    		List<Point> l = new ArrayList<Point>();
    		ligne = fichier.readLine();
    		if(!ligne.equals("solidité sprite"))
    			throw new IllegalArgumentException("Niveau:\n legende code solidité sprite manquant ");
    		while(!ligne.equals("Monde:")){
    			ligne = fichier.readLine();
    			if(ligne == null)
    				throw new IllegalArgumentException("Niveau:\n fin du fichier dans la descrition du decors ");
    			else{
    				if(!ligne.equals("Monde:")){
    					st = new StringTokenizer(ligne.trim());
    					try{
    						l.add(new Point(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())));
    					}catch(NumberFormatException e){
    						System.out.println("Niveau:\n erreur dans le code du decors");
    						throw e;
    					}
    				}
    			}
    		}
    		for(int j = 0; j < entite[0].length;j++){
    			ligne = fichier.readLine();
        		if(ligne != null)
        			st = new StringTokenizer(ligne);
        		else
        			throw new IllegalArgumentException("Niveau:\n ligne du monde manquant");
        		for(int i = 0;i <entite.length;i++){
        			if(!st.hasMoreTokens())
        				throw new IllegalArgumentException("Niveau:\n ligne du monde manquant");
        			ligne = st.nextToken();
        			int code = 0;
        			try{
        				code = Integer.parseInt(ligne);
        			}catch(NumberFormatException e){
        				System.out.println("Niveau:\n erreur dans le code du monde");
        				throw e;
        			}
        			entite[i][j] = new Entite(i*25,j*25,25,25,this,l.get(code).x == 1,null,stock.get(""+l.get(code).y));
        		}
    		}
    		mob = new LinkedList<Entite>();
    		ligne = fichier.readLine();
    		if(ligne.equals("Ennemis:")){
    			ligne = fichier.readLine();
    			while(!ligne.equals("Joueur:")){
    				st = new StringTokenizer(ligne," ,");
    				try{
    					mob.add(new Entite(st.nextToken(),this,Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())));
    				}catch(NoSuchElementException e){
    					System.out.println("Niveau:\n position d'un ennemis manquante ou erroner");
    					throw e;
    				}catch(NumberFormatException e){
        				System.out.println("Niveau:\n erreur dans la position d'un ennemis");
        				throw e;
    				}
    				ligne = fichier.readLine();
    			}
    		}else
    			ligne = fichier.readLine();
    		if(!ligne.equals("Joueur:"))
    			throw new IllegalArgumentException("Niveau:\n Joueur: manquant");
    		ligne = fichier.readLine();
    		if(ligne == null)
    			throw new IllegalArgumentException("Niveau:\n Joueur manquant");
    		try{
    			st = new StringTokenizer(ligne,",");
    			joueur = new Entite(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()),21,53,this,true,new JoueurStrat(),stock.get("gob"));
    		}catch(NoSuchElementException e){
				System.out.println("Niveau:\n position du joueur manquante ou erroner");
				throw e;
			}catch(NumberFormatException e){
				System.out.println("Niveau:\n erreur dans la position du joueur");
				throw e;
			}
    		trace = new LinkedList<EntiteTrace>();
    	}catch(IOException e){
    		e.printStackTrace();
    	}catch(IllegalArgumentException e){
    		System.out.println(e.getMessage());
    		try {
    			fichier.close();
    		} catch (IOException ee) {
    			// TODO Auto-generated catch block
    			ee.printStackTrace();
    		}
    		throw e;
    	}
    	try {
			fichier.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	/*
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
		BufferedReader fichier = null;
		try {
			fichier = new BufferedReader(new FileReader(new File("gob.strat")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("fichier non trouver");
		}
		mob.add(new Entite(300,100,21,53,this,true,new GoblinStrat(fichier),stock.get("gob")));
		
		/*
		 * Les instructions suivantes servent à définir le comprtement du joueur
		 * en réaction à certaines touchent du clavier.
		*/		
		/*
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
	*/
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
			itTrace.next().rendu(g,
					(300 -joueur.getWidth())-joueur.getPosX(),
					300 -joueur.getHeight()-joueur.getPosY(),
					getWidth(),
					getHeight());
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
		
		int curseurX =positionXCLickSouris - (300 - joueur.getWidth() - joueur.getPosX());
		int curseurY =positionYCLickSouris - (300 - joueur.getHeight() - joueur.getPosY());
		
		//si l'ecart d'ordonnee est plus grand, les blocks s'empilent
		//verticalement
		if (deltaX <= deltaY){
			int decalageHorizontal = deltaX / (deltaY / tailleBlockTrace);
						
			for (int i = 0; i < deltaY; i += tailleBlockTrace){
				trace.add(new EntiteTrace(curseurX, curseurY, tailleBlockTrace,
						this, new StrategieTrace(),
						stock.get("4"), ttlTrace));
				
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
						stock.get("4"), ttlTrace));
				
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
