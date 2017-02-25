package vue;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class SpriteStocker {
	
	private HashMap<String,Sprite> stock;
	private BufferedImage table;
	
	public SpriteStocker(String path){
		stock = new HashMap<String, Sprite>();
		try {
			table = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		add(new Sprite("Sprites/goblin.png"),"gob");
		add(new Sprite(getSprite(1,1)),"sol");
		add(new Sprite(getSprite(2,1)),"ciel");
		add(new Sprite(getSprite(3,1)),"mur");
		add(new Sprite(getSprite(4,1)),"nuage");
	}
	
	public void add(Sprite s,String nom){
		if(!stock.containsKey(nom))
			stock.put(nom,s);
	}

	public Sprite get(String nom){
		if(stock.containsKey(nom))
			return stock.get(nom);
		return null;
	}
	

	
	public BufferedImage getSprite(int x, int y){
		return table.getSubimage(x*25-25, y*25-25, 25, 25);
	}
}
