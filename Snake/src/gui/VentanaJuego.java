package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import data.Manzana;
import data.Musica;
import data.Obstaculo;
import data.Serpiente;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class VentanaJuego extends JFrame implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int windowWidth = 800;
	private int windowHeight = 600;
	private Serpiente snake;
	private Manzana manzana;
	private Obstaculo muro;
	private Obstaculo muro2;
	private Obstaculo muro3;
	private Obstaculo muro4;
	private Obstaculo muro5;
	private int score;
	private int life;
	private long goal;
	private int tiempoDemora;
	private Point punteroMouse;
	private int contador = 0;
	private String usuario;
	private float volumenEfectos = 0;
	private float volumenPartida = 0;
	private float volumenMenu = 0;
	private String fondo;

	public static void main(String args, int tiempoDemora, float volumenE, float volumenM, float volumenP, String fondo) {
		VentanaJuego v = new VentanaJuego(args, tiempoDemora, volumenE,volumenM,volumenP, fondo);
		while (true) {
			v.punteroMouse = MouseInfo.getPointerInfo().getLocation();
			v.juego();
			v.sleep(tiempoDemora);
		}
	}

	public VentanaJuego(String usuario, int tiempoDemora, float volumenE, float volumenM, float volumenP, String fondo) {
		this.usuario = usuario;
		this.tiempoDemora = tiempoDemora;
		this.volumenEfectos = volumenE;
		this.volumenMenu = volumenM;
		this.volumenPartida = volumenP;
		if(!fondo.equals("")) {
			this.fondo = fondo;
		} else {
			this.fondo =  "../recursos/Fondo.png";
		}
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaJuego.class.getResource("/recursos/SnakeIcon.png")));
		Musica.musicaFondo(volumenPartida);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(windowWidth, windowHeight);
		this.setResizable(false);
		this.setLocation(100, 100);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.createBufferStrategy(2);
		this.addKeyListener(this);
		life = 3;
		score = 0;
		inicializoObjetos();
		JOptionPane.showMessageDialog(this, "Puedes jugar con el puntero del rat�n pulsando la tecla 'R'", "Atenci�n",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void raton() {
		getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				String direccionActual = null;
				if (snake.getLargo().get(0).x < punteroMouse.x / 20) {
					snake.direccion("DER");
					direccionActual = "DER";
				} else if (snake.getLargo().get(0).x > punteroMouse.x / 20) {
					snake.direccion("IZQ");
					direccionActual = "IZQ";
				} else if (snake.getLargo().get(0).y < punteroMouse.y / 20) {
					snake.direccion("ABA");
					direccionActual = "ABA";
				} else if (snake.getLargo().get(0).y > punteroMouse.y / 20) {
					snake.direccion("ARR");
					direccionActual = "ARR";
				} else {
					snake.direccion(direccionActual);
				}
			}
		});
	}

	private void inicializoObjetos() {
		snake = new Serpiente();
		snake.AddPointSerpiente();
		manzana = new Manzana();
		manzana.newObjeto();
		muro = new Obstaculo();
		muro.newObjeto();
		muro2 = new Obstaculo();
		muro2.newObjeto();
		muro3 = new Obstaculo();
		muro3.newObjeto();
		muro4 = new Obstaculo();
		muro4.newObjeto();
		muro5 = new Obstaculo();
		muro5.newObjeto();
	}

	private void juego() {
		snake.MoveSerpiente();
		colision();
		dibujoPantalla();
	}

	private void dibujoPantalla() {
		BufferStrategy bf = this.getBufferStrategy();
		Graphics g = null;

		try {
			g = bf.getDrawGraphics();
			g.drawImage(new ImageIcon(VentanaJuego.class.getResource(fondo)).getImage(), 0, 0, windowWidth, windowHeight,null);
			snake.dibujoSnake(g);
			manzana.dibujaObjeto(g);
			muro.dibujaObjeto(g);
			muro2.dibujaObjeto(g);
			muro3.dibujaObjeto(g);
			muro4.dibujaObjeto(g);
			muro5.dibujaObjeto(g);
			puntuacion(g);
			vida(g);
		} finally {
			g.dispose();
		}

		bf.show();
		Toolkit.getDefaultToolkit().sync();
	}

	private void colision() {
		if (snake.getLargo().get(0).equals(manzana.getPunto())) {
			manzana.newObjeto();
			snake.AddPointSerpiente();
			Musica.sonidoRecompensa(volumenEfectos);
			score += 10;
			contador++;
			if (contador % 3 == 0) {
				life++;
			}
		}

		if (snake.getLargo().get(0).equals(muro.getPunto()) || snake.getLargo().get(0).equals(muro2.getPunto()) || snake.getLargo().get(0).equals(muro3.getPunto()) || snake.getLargo().get(0).equals(muro4.getPunto()) || snake.getLargo().get(0).equals(muro5.getPunto()) ) {
			if (life > 0) {
				life--;
				inicializoObjetos();
				Musica.colision(volumenEfectos);
			} else {
				this.dispose();
				new GameOver(usuario, tiempoDemora, volumenEfectos, volumenMenu, volumenPartida, fondo).main(usuario,tiempoDemora, volumenEfectos, volumenMenu, volumenPartida, fondo);
			}
		}

		if (snake.getLargo().get(0).x < 0 || snake.getLargo().get(0).x > 39 || snake.getLargo().get(0).y < 1
				|| snake.getLargo().get(0).y > 29) {
			if (life > 0) {
				life--;
				inicializoObjetos();
				Musica.colision(volumenEfectos);
			} else {
				this.dispose();
				new GameOver(usuario, tiempoDemora, volumenEfectos, volumenMenu, volumenPartida, fondo).main(usuario,tiempoDemora, volumenEfectos, volumenMenu, volumenPartida, fondo);
			}
		}

		for (int n = 1; n < snake.getLargo().size(); n++) {
			if (snake.getLargo().get(0).equals(snake.getLargo().get(n)) && snake.getLargo().size() > 2) {
				if (life > 0) {
					life--;
					inicializoObjetos();
					Musica.colision(volumenEfectos);
				} else {
					this.dispose();
					new GameOver(usuario, tiempoDemora, volumenEfectos, volumenMenu, volumenPartida, fondo).main(usuario,tiempoDemora, volumenEfectos, volumenMenu, volumenPartida, fondo);
				}
			}
		}
	}

	private void puntuacion(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Consolas", Font.BOLD, 16));
		g.drawString("Score: " + score, 20, 50);
	}

	private void vida(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Consolas", Font.BOLD, 16));
		g.drawString("Lifes: " + life, 700, 50);
	}

	private void sleep(int tiempoDemora) {
		goal = (System.currentTimeMillis() + tiempoDemora);
		while (System.currentTimeMillis() < goal) {
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int tecla = e.getKeyCode();
		switch (tecla) {
		case KeyEvent.VK_UP:
			snake.direccion("ARR");
			break;
		case KeyEvent.VK_DOWN:
			snake.direccion("ABA");
			break;
		case KeyEvent.VK_LEFT:
			snake.direccion("IZQ");
			break;
		case KeyEvent.VK_RIGHT:
			snake.direccion("DER");
			break;
		case KeyEvent.VK_ESCAPE:
			String resp = JOptionPane.showInputDialog("�Est�s seguro de que quieres abandonar la partida? (S/N)");
			if (resp.equalsIgnoreCase("S")) {
				this.dispose();
				Musica.stop(1);
				VentanaMenu v = new VentanaMenu(usuario, tiempoDemora, volumenEfectos, volumenMenu, volumenPartida, fondo);
				v.main(usuario, tiempoDemora, volumenEfectos, volumenMenu, volumenPartida, fondo);
			} else {
			}
			
			break;
		case KeyEvent.VK_R:
			raton();
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}