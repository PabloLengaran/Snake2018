package data;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class Fantasma {

	private ArrayList<Point> ghost = new ArrayList<Point>(); // ArrayList que
	// contiene las
	// partes del
	// cuerpo de la
	// serpiente
	private double fantasmaX = 0; // Posici�n en x de cada parte del cuerpo
	private double fantasmaY = 0; // Posici�n en y de cada parte del cuerpo

	// Cosntructor que crea la cabeza de la serpiente
	public Fantasma() {
		ghost.add(new Point(30, 20));
	}

	// M�todo que devuelve la longitud de la serpiente
	public ArrayList<Point> getLargo() {
		return ghost;
	}

	// M�todo que dibuja la serpiente, mediante un bucle pinta todos los
	// elementos del cuerpo
	 public double angulo() {
         if (fantasmaX == 0 && fantasmaY == -1) // ARR
                 return Math.PI;
         else if (fantasmaX == 0 && fantasmaY == 1) // AB
                 return 0;
         else if (fantasmaX == -1 && fantasmaY == 0) // IZ
                 return Math.PI/2;
         else // DER
                 return -Math.PI/2;
 }
	public void dibujoFantasma(Graphics g) {
		int contador = 0;
		for (int n = 0; n < 	ghost.size() - 1; n++) {
			contador++;
			if (contador == 1) {
				Point p = 	ghost.get(0);
				((Graphics2D) g).translate( p.x * 20, p.y * 20 );
				 
				int difAncho = (20 - p.x * 20) / 2;
				int difAlto = (20 - p.y * 20) / 2;
				
				g.drawImage(new ImageIcon(Fantasma.class.getResource("../recursos/Fantasma.png")).getImage(), 0,
						0, 20, 20, null);
			((Graphics2D) g).setTransform( new AffineTransform() );
				
				
			} 
				//g.drawImage(new ImageIcon(Serpiente.class.getResource("../recursos/Cola.png")).getImage(), p1.x * 20,
					//	p1.y * 20, 20, 20, null);
			


				Point p2 = 	ghost.get(n);
				((Graphics2D) g).translate( p2.x * 20, p2.y * 20 );
				g.drawImage(new ImageIcon(Fantasma.class.getResource("../recursos/Fantasma.png")).getImage(), p2.x*0,
						p2.y*0, 20, 20, null);
				
				((Graphics2D) g).setTransform( new AffineTransform() );
			
			}
		}
	

	// M�todo que sirve para dotar de movilidad a la serpiente, y mediante un
	// bucle establece la nueva posici�n de cada elemento como la del que le
	// precede
	public void MoveFantasma() {
		
		// El punto de la cabeza queda excluido del bucle(condición n>0) y se
		// actualiza su posición con los valores siguientes
		ghost.get(0).x += fantasmaX;
		ghost.get(0).y += fantasmaY;
	}
	public void AddPointFantasma() {
		ghost.add(new Point());
	}

	
	
	
	// M�todo que establece la direcci�n de la serpiente
	public void direccion(String d) {
		switch (d) {
		case "ARR1":
			fantasmaX = 0;
			fantasmaY = -1;
			break;
		case "ABA1":
			fantasmaX = 0;
			fantasmaY = 1;
			break;
		case "IZQ1":
			fantasmaX = -1;
			fantasmaY = 0;
			break;
		case "DER1":
			fantasmaX = 1;
			fantasmaY = 0;
			break;
		}

	}
}
