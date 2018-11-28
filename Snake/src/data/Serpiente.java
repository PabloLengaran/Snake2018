package data;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
	public void dibujoSnake(Graphics g) {
		int contador = 0;
		for (int n = 0; n < cuerpo.size() - 1; n++) {
			contador++;
			if (contador == 1) {
				Point p = cuerpo.get(0);
				//Graphics2D g2 = (Graphics2D) g;
				//g2.rotate(Math.PI/2, 10,10);
				g.drawImage(new ImageIcon(Serpiente.class.getResource("../recursos/Head.png")).getImage(), p.x * 20,
						p.y * 20, 20, 20, null);
			} else {
				int last = cuerpo.size() - 1;
				Point p1 = cuerpo.get(last);
				g.drawImage(new ImageIcon(Serpiente.class.getResource("../recursos/Cola.png")).getImage(), p1.x * 20,
						p1.y * 20, 20, 20, null);

				Point p2 = cuerpo.get(n);
				g.drawImage(new ImageIcon(Serpiente.class.getResource("../recursos/Cuerpo.png")).getImage(), p2.x * 20,
						p2.y * 20, 20, 20, null);
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