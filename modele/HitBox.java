package modele;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

public class HitBox implements Shape{
	
	Rectangle global;
	List<Shape> list;
	
	/*public HitBox(String path){
		
	}*/
	
	public HitBox(){
		list = new LinkedList<Shape>();
		global = new Rectangle();
	}

	@Override
	public boolean contains(Point2D arg0) {
		if(global.contains(arg0)){
			Iterator<Shape> it = list.iterator();
			while(it.hasNext()){
				if(it.next().contains(arg0))
					return true;
			}
			return false;
		}else
			return false;
	}

	@Override
	public boolean contains(Rectangle2D arg0) {
		if(global.contains(arg0)){
			Iterator<Shape> it = list.iterator();
			while(it.hasNext()){
				if(it.next().contains(arg0))
					return true;
			}
			return false;
		}else
			return false;
	}

	@Override
	public boolean contains(double arg0, double arg1) {
		Point2D p = new Point2D.Double(arg0,arg1);
		if(global.contains(p)){
			Iterator<Shape> it = list.iterator();
			while(it.hasNext()){
				if(it.next().contains(p))
					return true;
			}
			return false;
		}else
			return false;
	}

	@Override
	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		Rectangle2D r = new Rectangle2D.Double(arg0,arg1,arg2,arg3);
		if(global.contains(r)){
			Iterator<Shape> it = list.iterator();
			while(it.hasNext()){
				if(it.next().contains(r))
					return true;
			}
			return false;
		}else
			return false;
	}

	@Override
	public Rectangle getBounds() {
		return global.getBounds();
	}

	@Override
	public Rectangle2D getBounds2D() {
		return global.getBounds2D();
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean intersects(Rectangle2D arg0) {
		if(global.intersects(arg0)){
			Iterator<Shape> it = list.iterator();
			while(it.hasNext()){
				if(it.next().intersects(arg0))
					return true;
			}
			return false;
		}else
			return false;
	}

	@Override
	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		Rectangle2D r = new Rectangle2D.Double(arg0,arg1,arg2,arg3);
		if(global.intersects(r)){
			Iterator<Shape> it = list.iterator();
			while(it.hasNext()){
				if(it.next().intersects(r))
					return true;
			}
			return false;
		}else
			return false;
	}
	
	
	
}
