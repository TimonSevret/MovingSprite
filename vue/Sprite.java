package vue;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
    /** L'image dessinée pour ce Sprite */
    private BufferedImage image;
    
    /**
     * Crée un nouveau Sprite par rapport à une image donnée.
     * 
     * @param image L'image représentée par ce Sprite
     */
    public Sprite(BufferedImage image){
    	this.image = image;
    }
    
    public Sprite(String path){
    	try {
			image = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public BufferedImage getBufferedImage(){
    	return image;
    }
}
