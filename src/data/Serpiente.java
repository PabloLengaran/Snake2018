package data;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Serpiente {

	private ArrayList<Point> cuerpo = new ArrayList<Point>(); // ArrayList que
	// contiene las
	// partes del
	// cuerpo de la
	// serpiente
	private double snakeX = 0; // Posici�n en x de cada parte del cuerpo
	private double snakeY = 0; // Posici�n en y de cada parte del cuerpo

	// Cosntructor que crea la cabeza de la serpiente
	public Serpiente() {
		cuerpo.add(new Point(20, 15));
	}

	// M�todo que devuelve la longitud de la serpiente
	public ArrayList<Point> getLargo() {
		return cuerpo;
	}

	// M�todo que dibuja la serpiente, mediante un bucle pinta todos los
	// elementos del cuerpo
	 public double angulo() {
         if (snakeX == 0 && snakeY == -1) // ARR
                 return Math.PI;
         else if (snakeX == 0 && snakeY == 1) // AB
                 return 0;
         else if (snakeX == -1 && snakeY == 0) // IZ
                 return Math.PI/2;
         else // DER
                 return -Math.PI/2;
 }
	public void dibujoSnake(Graphics g) {
		int contador = 0;
		for (int n = 0; n < cuerpo.size() - 1; n++) {
			contador++;
			if (contador == 1) {
				Point p = cuerpo.get(0);
				((Graphics2D) g).translate( p.x * 20, p.y * 20 );
				((Graphics2D) g).rotate(angulo(), 20/2, 20/2 );
				 
				int difAncho = (20 - p.x * 20) / 2;
				int difAlto = (20 - p.y * 20) / 2;
				
				g.drawImage(new ImageIcon(Serpiente.class.getResource("../recursos/Head.png")).getImage(), 0,
						0, 20, 20, null);
			((Graphics2D) g).setTransform( new AffineTransform() );
				
				
			} else {
				int last = cuerpo.size() - 1;
				Point p1 = cuerpo.get(last);
				((Graphics2D) g).translate( p1.x * 20, p1.y * 20 );
				((Graphics2D) g).rotate(angulo(), 20/2, 20/2 );
				g.drawImage(new ImageIcon(Serpiente.class.getResource("../recursos/Cola.png")).getImage(), p1.x*0 ,
						p1.y*0, 20, 20, null);
				
				((Graphics2D) g).setTransform( new AffineTransform() );
				//g.drawImage(new ImageIcon(Serpiente.class.getResource("../recursos/Cola.png")).getImage(), p1.x * 20,
					//	p1.y * 20, 20, 20, null);
			


				Point p2 = cuerpo.get(n);
				((Graphics2D) g).translate( p2.x * 20, p2.y * 20 );
				((Graphics2D) g).rotate(angulo(), 20/2, 20/2 );
				g.drawImage(new ImageIcon(Serpiente.class.getResource("../recursos/Cuerpo.png")).getImage(), p2.x*0,
						p2.y*0, 20, 20, null);
				
				((Graphics2D) g).setTransform( new AffineTransform() );
			
			}
		}
	}

	// M�todo que sirve para dotar de movilidad a la serpiente, y mediante un
	// bucle establece la nueva posici�n de cada elemento como la del que le
	// precede
	public void MoveSerpiente() {
		for (int n = cuerpo.size() - 1; n > 0; n--) {
			cuerpo.get(n).setLocation(cuerpo.get(n - 1)); // Cada punto ocupa la posici�n del punto que al que sigue
		}

		// El punto de la cabeza queda excluido del bucle(condición n>0) y se
		// actualiza su posición con los valores siguientes
		cuerpo.get(0).x += snakeX;
		cuerpo.get(0).y += snakeY;
	}
	
	// M�todo que a�ade un elemento al cuerpo de la serpiente, al ArrayList
	public void AddPointSerpiente() {
		cuerpo.add(new Point());
	}

	// M�todo que establece la direcci�n de la serpiente
	public void direccion(String d) {
		switch (d) {
		case "ARR":
			snakeX = 0;
			snakeY = -1;
			break;
		case "ABA":
			snakeX = 0;
			snakeY = 1;
			break;
		case "IZQ":
			snakeX = -1;
			snakeY = 0;
			break;
		case "DER":
			snakeX = 1;
			snakeY = 0;
			break;
		}

	}

}