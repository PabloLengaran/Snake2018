package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.sql.Connection;
import java.sql.Statement;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import baseDeDatos.BD;
import data.Fantasma;
import data.Manzana;
import data.Moneda;
import data.Musica;
import data.Obstaculo;
import data.Platano;
import data.Serpiente;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class VentanaJuegoFantasma extends JFrame implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int windowWidth = 800;
	private int windowHeight = 600;
	private Serpiente snake;
	private Fantasma ghost;
	private Manzana manzana;
	private Platano platano;
	private Moneda moneda;
	private Obstaculo muro;
	private Obstaculo muro2;
	private Obstaculo muro3;
	private Obstaculo muro4;
	private Obstaculo muro5;
	private Obstaculo muro6;
	private Obstaculo muro7;
	private Obstaculo muro8;
	private Obstaculo muro9;
	private Obstaculo muro10;
	private int score;
	private int creditos;
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
	private Connection con;
	private Statement st;
	private int snakeSelected;
	private int partidasAbandonadas;
	private int partidasTerminadas;

	public static void main(String args, int tiempoDemora, float volumenE, float volumenM, float volumenP, String fondo, int serpienteSeleccionada) {
		VentanaJuegoFantasma v = new VentanaJuegoFantasma(args, tiempoDemora, volumenE,volumenM,volumenP, fondo, serpienteSeleccionada);
		while (true) {
			v.punteroMouse = MouseInfo.getPointerInfo().getLocation();
			v.juego();
			v.sleep(tiempoDemora);
			
		}
	}

	public VentanaJuegoFantasma(String usuario, int tiempoDemora, float volumenE, float volumenM, float volumenP, String fondo, int serpienteSeleccionada) {
		this.usuario = usuario;
		this.tiempoDemora = tiempoDemora;
		this.volumenEfectos = volumenE;
		this.volumenMenu = volumenM;
		this.volumenPartida = volumenP;
		this.snakeSelected = serpienteSeleccionada;
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
		life = 4;
		score = 0;
		creditos = 0;
		con = BD.initBD("Usuarios");
		st = BD.usarCrearTablasBD(con);
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
		snake = new Serpiente(snakeSelected);
		snake.AddPointSerpiente();
		ghost =new Fantasma();
		ghost.AddPointFantasma();
		manzana = new Manzana();
		manzana.newObjeto();
		platano = new Platano();
		platano.newObjeto();
		moneda = new Moneda();
		moneda.newObjeto();
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
		muro6 = new Obstaculo();
		muro6.newObjeto();
		muro7 = new Obstaculo();
		muro7.newObjeto();
		muro8 = new Obstaculo();
		muro8.newObjeto();
		muro9 = new Obstaculo();
		muro9.newObjeto();
		muro10 = new Obstaculo();
		muro10.newObjeto();
		
	}

	private void juego() {
		snake.MoveSerpiente();
		ghost.MoveFantasma();
		colision();
		dibujoPantalla();
	}

	private void dibujoPantalla() {
		BufferStrategy bf = this.getBufferStrategy();
		Graphics g = null;

		try {
			g = bf.getDrawGraphics();
			g.drawImage(new ImageIcon(VentanaJuegoFantasma.class.getResource(fondo)).getImage(), 0, 0, windowWidth, windowHeight,null);
			snake.dibujoSnake(g);
			ghost.dibujoFantasma(g);
			manzana.dibujaObjeto(g);
			platano.dibujaObjeto(g);
			moneda.dibujaObjeto(g);
			muro.dibujaObjeto(g);
			muro2.dibujaObjeto(g);
			muro3.dibujaObjeto(g);
			muro4.dibujaObjeto(g);
			muro5.dibujaObjeto(g);
			muro6.dibujaObjeto(g);
			muro7.dibujaObjeto(g);
			muro8.dibujaObjeto(g);
			muro9.dibujaObjeto(g);
			muro10.dibujaObjeto(g);
			
			puntuacion(g);
			creditos(g);
			vida(g);
		} finally {
			g.dispose();
		}

		bf.show();
		Toolkit.getDefaultToolkit().sync();
	}

	private void colision() {
		//Caso de la Manzana
		if (snake.getLargo().get(0).equals(manzana.getPunto())) {
			manzana.newObjeto();
			snake.AddPointSerpiente();
			Musica.sonidoRecompensa(volumenEfectos);	//Suena el sonido de obtencion de un objeto
			contador++; //Incrementamos el contador, el contador nos sirve para ver cuantas veces seguidas se han obtenido frutas seguidas
			if (contador % 5 != 0) { 	//Si el contador es diferente a 5 sumamos los puntos de 10 en 10
				score += 10; 	//Incrementamos los puntos (10 puntos)
			} else if (contador % 5 >= 0) {		//Si el contador es igual o mayor que 5 sumamos los puntos de 15 en 15 
				score += 15;	//Incrementamos los puntos (15 puntos)
			}
			if (contador % 5 == 0) {	//Cuando se obtienen 5 frutas de forma consecutiva se incrementa una vida a la partida
				life++;
			}
		}
		//Caso de la Moneda
		if (snake.getLargo().get(0).equals(moneda.getPunto())) {
			moneda.newObjeto();
			Musica.sonidoRecompensa(volumenEfectos);	//Suena el sonido de obtencion de un objeto
			creditos += 1;		//Sumamos los creditos de uno en uno
		}
		//Caso del Platano
		if (snake.getLargo().get(0).equals(platano.getPunto())) {
			platano.newObjeto();
			Musica.sonidoRecompensa(volumenEfectos);	//Suena el sonido de obtencion de un objeto
			contador++;		//Incrementamos el contador, el contador nos sirve para ver cuantas veces seguidas se han obtenido frutas seguidas
			if (contador % 5 != 0) {	//Si el contador es diferente a 5 sumamos los puntos de 5 en 5
				score += 5;
			} else if (contador % 5 >= 0) {	//Si el contador es igual o mayor que 5 sumamos los puntos de 10 en 10
				score += 10;
			}
			if (contador % 5 == 0) {	//Cuando se obtienen 5 frutas de forma consecutiva se incrementa una vida a la partida
				life++;
			}
			
		}

		if (snake.getLargo().get(0).equals(muro.getPunto()) || snake.getLargo().get(0).equals(muro2.getPunto()) || snake.getLargo().get(0).equals(muro3.getPunto()) || snake.getLargo().get(0).equals(muro4.getPunto()) || snake.getLargo().get(0).equals(muro5.getPunto())|| snake.getLargo().get(0).equals(muro6.getPunto())|| snake.getLargo().get(0).equals(muro7.getPunto())|| snake.getLargo().get(0).equals(muro8.getPunto())|| snake.getLargo().get(0).equals(muro9.getPunto())|| snake.getLargo().get(0).equals(muro10.getPunto()))  {
			life--; //Cuando choca contra alguno de los muros presentes en la ventana las vidas disminuyen de uno en uno
			if (life > 0) { //Si las vidas restantes son mas de 0 se reinicia la partida
				inicializoObjetos();
				Musica.colision(volumenEfectos);
			} else if (life == 0){ //Si las vidas restantes son igual a 0:
				partidasTerminadas = BD.partidasSelect(st, usuario, 0) + 1;
				BD.partidasUpdate(st, usuario, partidasTerminadas, 0);
				BD.creditosUpdate(st, usuario, creditos + BD.creditosSelect(st, usuario)); //Se guardan los creditos en la base de datos, donde se suman a los ya obtenidos previamente
				if (score>0) { //Si la putuacion es mayor de 0: 
					BD.puntuacionesInsert(st, usuario, score); //Se guarda la puntuacion en la base de datos
				}
				this.dispose(); //Se cierra la ventana de juego y se inicia la pantalla de GameOver
				new GameOver(usuario, tiempoDemora, volumenEfectos, volumenMenu, volumenPartida, fondo, snakeSelected).main(usuario,tiempoDemora, volumenEfectos, volumenMenu, volumenPartida, fondo, snakeSelected);
			}
		}

		if (snake.getLargo().get(0).x < 0 || snake.getLargo().get(0).x > 39 || snake.getLargo().get(0).y < 1 || snake.getLargo().get(0).y > 29) {
			life--;
			if (life > 0) {
				inicializoObjetos();
				Musica.colision(volumenEfectos);
			} else if (life == 0){
				partidasTerminadas = BD.partidasSelect(st, usuario, 0) + 1;
				BD.partidasUpdate(st, usuario, partidasTerminadas, 0);
				BD.creditosUpdate(st, usuario, creditos + BD.creditosSelect(st, usuario));
				BD.puntuacionesInsert(st, usuario, score);
				this.dispose();
				new GameOver(usuario, tiempoDemora, volumenEfectos, volumenMenu, volumenPartida, fondo, snakeSelected).main(usuario,tiempoDemora, volumenEfectos, volumenMenu, volumenPartida, fondo, snakeSelected);
			}
		}
		

		for (int n = 1; n < snake.getLargo().size(); n++) {
			if (snake.getLargo().get(0).equals(snake.getLargo().get(n)) && snake.getLargo().size() > 2) {
				life--;
				if (life > 0) {	
					inicializoObjetos();
					Musica.colision(volumenEfectos);
				} else if (life == 0){
					partidasTerminadas = BD.partidasSelect(st, usuario, 0) + 1;
					BD.partidasUpdate(st, usuario, partidasTerminadas, 0);
					BD.creditosUpdate(st, usuario, creditos + BD.creditosSelect(st, usuario)); 
					BD.puntuacionesInsert(st, usuario, score);
					this.dispose();
					new GameOver(usuario, tiempoDemora, volumenEfectos, volumenMenu, volumenPartida, fondo, snakeSelected).main(usuario,tiempoDemora, volumenEfectos, volumenMenu, volumenPartida, fondo, snakeSelected);
				}
			}
		}
		
		for (Point punto : snake.getLargo()) {
			if (ghost.getLargo().get(0).equals(punto)) {
				life--;
				if (life > 0) {	
					inicializoObjetos();
					Musica.colision(volumenEfectos);
				} else if (life == 0){
					partidasTerminadas = BD.partidasSelect(st, usuario, 0) + 1;
					BD.partidasUpdate(st, usuario, partidasTerminadas, 0);
					BD.creditosUpdate(st, usuario, creditos + BD.creditosSelect(st, usuario)); 
					BD.puntuacionesInsert(st, usuario, score);
					this.dispose();
					new GameOver(usuario, tiempoDemora, volumenEfectos, volumenMenu, volumenPartida, fondo, snakeSelected).main(usuario,tiempoDemora, volumenEfectos, volumenMenu, volumenPartida, fondo, snakeSelected);
				}
			}
		}
}

	//Puntuacion en la pantalla de juego.
	private void puntuacion(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Consolas", Font.BOLD, 16));
		g.drawString("Score: " + score, 20, 50);
	}
	
	//Creditos en la pantalla de juego.
	private void creditos(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Consolas", Font.BOLD, 16));
		g.drawString("Creditos: " + creditos + "$", 20, 80);
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
		case KeyEvent.VK_W:
			ghost.direccion("ARR1");
			break;
		case KeyEvent.VK_S:
			ghost.direccion("ABA1");
			break;
		case KeyEvent.VK_A:
			ghost.direccion("IZQ1");
			break;
		case KeyEvent.VK_D:
			ghost.direccion("DER1");
			break;
		case KeyEvent.VK_ESCAPE:
			String resp = JOptionPane.showInputDialog("Ests seguro de que quieres abandonar la partida? (S/N)");
			if (resp.equalsIgnoreCase("S")) { //En caso de que la respuesta sea afirmativa
				partidasAbandonadas = partidasAbandonadas + 1;
				BD.partidasUpdate(st, usuario, partidasAbandonadas, 1);
				this.dispose(); //Cerramos la ventana 
				Musica.stop(1); //Se para la musica
				VentanaMenu v = new VentanaMenu(usuario, tiempoDemora, volumenEfectos, volumenMenu, volumenPartida, fondo, snakeSelected);
				v.main(usuario, tiempoDemora, volumenEfectos, volumenMenu, volumenPartida, fondo, snakeSelected); //Se inicia la ventanaMenu de nuevo
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