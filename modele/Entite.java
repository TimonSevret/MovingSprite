package modele;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import vue.Niveau;
import vue.Sprite;


public class Entite {

    private Rectangle hitBox;
    private Sprite sprite;
	private Niveau niveau;
    private boolean solid;
    private Strategie strat;

    
    public Entite(int posX, int posY, int tailleX, int tailleY, Niveau niveau,boolean solid,Strategie strat, Sprite sprite){
    	hitBox = new Rectangle(posX,posY,tailleX,tailleY);
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
    
    public void setPosition(int posX, int posY){
    	hitBox.setLocation(posX, posY);
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
	
	public int eval(Entite [][] tiles, List<Entite> l, List<EntiteTrace> lTrace){
		return strat.eval(this, tiles, l, lTrace);
	}
	
	public void deces(){
		niveau.supprimerEntite(this);
	}
}