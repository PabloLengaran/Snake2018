package data;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public abstract class Objeto {
	
	protected Random random;
	protected Point punto;
	
	public Objeto() {
	}
	
	public Point getPunto() {
		return punto;
	}

	public void setPunto(Point punto) {
		this.punto = punto;
	}
	
	public abstract void newObjeto();
	
	public abstract void  dibujaObjeto(Graphics g);
}
