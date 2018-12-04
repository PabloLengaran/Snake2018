package data;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Obstaculo extends Objeto{

		public Obstaculo() {
			super.random = new Random();
			super.punto = new Point();
		}

		@Override
		public void newObjeto() {
			super.punto.x = random.nextInt(39);
			super.punto.y = random.nextInt(28) + 1;
		}

		@Override
		public void dibujaObjeto(Graphics g) {
			g.setColor(new Color(115, 74, 18));
			g.fillRect(super.punto.x * 20, super.punto.y * 20, 20, 20);
		}
}
