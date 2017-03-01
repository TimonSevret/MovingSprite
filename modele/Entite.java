package modele;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import vue.Niveau;
import vue.Sprite;


public class Entite {

    private Rectangle hitBox;
    //private Point position;
    private Sprite sprite;
	private Niveau niveau;
    private boolean solid;
    private Strategie strat;

    @SuppressWarnings("resource")
	public Entite(String path, Niveau niveau, int posX, int posY) throws IllegalArgumentException, FileNotFoundException{
    	this.niveau = niveau;
    	//position = new Point(posX,posY);
    	BufferedReader fichier=new BufferedReader(new FileReader(new File(path)));
    	String ligne;
    	try{
    		ligne = fichier.readLine();
    		if(!ligne.equals("Entite :"))
    			throw new IllegalArgumentException("Entite:\n fichier au mauvais format");
    		/*ligne = fichier.readLine();
    		if(!ligne.equals("sprite :"))
    			throw new IllegalArgumentException("Entite:\n sprite manquant");
    		sprite = new Sprite(fichier);*/
    		hitBox = new Rectangle(posX,posY,21,53);
    		sprite = new Sprite("Sprites/goblin.png");
    		ligne = fichier.readLine();
    		if(!ligne.equals("solid :"))
    			throw new IllegalArgumentException("Entite:\n solid manquant");
    		solid = fichier.readLine().equals("true");
    		ligne = fichier.readLine();
    		if(!ligne.equals("strategie :"))
    			throw new IllegalArgumentException("Entite:\n strategie manquant");
    		strat = new GoblinStrat(fichier);
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
    }
    
    public Entite(int posX, int posY,int tailleX,int tailleY,  Niveau niveau,boolean solid,Strategie strat, Sprite sprite){
    	hitBox = new Rectangle(posX,posY,tailleX,tailleY);
    	//position = new Point(posX,posY);
    	this.strat = strat;
		this.niveau = niveau;
		this.solid = solid;
		this.sprite= sprite;
    }
    
    public Rectangle getHitBox(){
    	return hitBox;
    }
    
    public int getPosX(){
    	return (int) hitBox.getX();
    }
    public int getPosY(){
    	return (int) hitBox.getY();
    }
    /*
     * \pre posX positifs
     * \pre posY positifs
     */
    public void setPosition(int posX, int posY){
    	if(posX >= 0 && posY >= 0 )
    		hitBox.setLocation(posX, posY);
    	else
    		throw new IllegalArgumentException("coordonne negative");
    }
    
    public int getWidth(){
    	return (int) hitBox.getWidth();
    }
    
    public int getHeight(){
    	return (int) hitBox.getHeight();
    }
    
    public boolean isSolid(){
    	return solid;
    }
    
    /*
     * permet de dessiner l'entité
     * \param g le Graphics utilisé pour dessiner l'entite
     * \param deltaX le decalage horizontal avec lequel afficher l'entité
     * \param deltaY le decalage verticlal avec lequel afficher l'entité
     * \param screenWidth la taille horizontal de l'ecran sur laquel l'entité s'affiche
     * \param screenHeight la taille vertical de l'ecran sur laquel l'entité s'affiche
     * \pre tout les arguments non null
     * \pre screenWidth positifs
     * \pre screenHeight positifs
     */
    public void rendu(Graphics g,int deltaX,int deltaY,int screenWidth,int screenHeight){
    	g.drawImage(sprite.getBufferedImage(), ((getPosX()+deltaX)*screenWidth)/600, ((getPosY()+deltaY)*screenHeight)/600, (getWidth()*screenWidth)/600+1, (getHeight()*screenHeight)/600+1,null);
    }

    public Sprite getSprite() {
		return sprite;
	}

	public Niveau getNiveau() {
		return niveau;
	}
	
	public Strategie getStrat(){
		return strat;
	}
	
	/*
	 * permet a l'entite de metre a jours sa position
	 * \param tiles tableau representant le decors du niveau
	 * \param l liste des ennemis du niveau
	 * \param lTrace liste des boucliers du niveau
	 */
	public int eval(Entite [][] tiles, List<Entite> l, List<EntiteTrace> lTrace){
		return strat.eval(this, tiles, l, lTrace);
	}
	
	/*
	 * premet de supprimer l'entité du niveau aauquel elle est rattacher
	 */
	public void deces(){
		niveau.supprimerEntite(this);
	}
	
}