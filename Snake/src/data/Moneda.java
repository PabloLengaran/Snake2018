package data;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import javax.swing.ImageIcon;

public class Moneda extends Objeto{
	public Moneda() {
		super.random = new Random();
		super.punto = new Point();
	}

	public void newObjeto() {
		super.punto.x = random.nextInt(39);
		super.punto.y = random.nextInt(28) + 1;
	}

	
	public void dibujaObjeto(Graphics g) {
		g.drawImage(new ImageIcon(Manzana.class.getResource("../recursos/Moneda.png")).getImage(), super.punto.x * 20, super.punto.y * 20, 20, 20,null);
	}
}
