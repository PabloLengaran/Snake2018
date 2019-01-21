package gui;

import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import data.Musica;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.Toolkit;

public class VentanaMenu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel, panel2, panel3;
	private JFrame frame;
	private String usuario;
	private int dificultad;
	private float volumenEfectos = 0;
	private float volumenPartida = 0;
	private float volumenMenu = 0;
	private String background;
	private int selectedSnake;

	/**
	 * Launch the application.
	 */
	public static void main(String args, int dificultad, float volumenE, float volumenM, float volumenP,
			String imagen, int serpienteSeleccionada) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMenu window = new VentanaMenu(args, dificultad, volumenE, volumenM, volumenP, imagen, serpienteSeleccionada);
					window.frame.setLocationRelativeTo(null);
					window.frame.setResizable(false);
					window.frame.setVisible(true);
					Musica.musicaMenu(volumenM);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaMenu(String usuario, int dificultad, float volumenE, float volumenM, float volumenP, String imagen, int serpienteSeleccionada) {
		this.usuario = usuario;
		this.dificultad = dificultad;
		this.volumenEfectos = volumenE;
		this.volumenMenu = volumenM;
		this.volumenPartida = volumenP;
		this.background = imagen;
		this.selectedSnake = serpienteSeleccionada;
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaMenu.class.getResource("/recursos/SnakeIcon.png")));
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon fondo = new ImageIcon(VentanaMenu.class.getResource("/recursos/FondoMenu.gif"));
		frame.getContentPane().setLayout(null);

		//Se crea el boton de play, en caso de pulsarlo accedemos a la ventana de juego
		JButton btn1 = new JButton("");
		btn1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new Thread() {
					@Override
					public void run() {
						Musica.clickBoton(volumenEfectos);
						frame.dispose();
						Musica.stop(6);
						VentanaJuego.main(usuario, dificultad, volumenEfectos, volumenMenu, volumenPartida, background, selectedSnake);
					}
				}).start();
			}
		});
		btn1.setBounds(292, 228, 200, 105);
		btn1.setOpaque(false);
		btn1.setContentAreaFilled(false);
		btn1.setBorderPainted(false);
		btn1.setIcon(new ImageIcon(new ImageIcon(VentanaMenu.class.getResource("/recursos/Boton1.png")).getImage()
				.getScaledInstance(btn1.getWidth(), btn1.getHeight(), Image.SCALE_DEFAULT)));
		frame.getContentPane().add(btn1);

		//Se crea el boton de acceso a la ventana de configuracion, en caso de pulsarlo accedemos a la ventana de configuracion
		JButton btn2 = new JButton("");
		btn2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Musica.clickBoton(volumenEfectos);
				VentanaConfiguracion v = new VentanaConfiguracion(usuario, dificultad, volumenEfectos, volumenMenu,
						volumenPartida, background, selectedSnake);
				v.main(usuario, dificultad, volumenEfectos, volumenMenu, volumenPartida, background, selectedSnake);
				Musica.stop(6);
				frame.dispose();
			}
		});
		btn2.setBounds(699, 475, 75, 75);
		btn2.setOpaque(false);
		btn2.setContentAreaFilled(false);
		btn2.setBorderPainted(false);
		btn2.setIcon(new ImageIcon(new ImageIcon(VentanaMenu.class.getResource("/recursos/Boton2.png")).getImage()
				.getScaledInstance(btn2.getWidth(), btn2.getHeight(), Image.SCALE_DEFAULT)));
		frame.getContentPane().add(btn2);

		//Se crea el boton de eleccion de niveles
		JButton btn3 = new JButton("");
		btn3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel panelNivel= new JPanel();
				Musica.clickBoton(volumenEfectos);
				JFileChooser fc = new JFileChooser();
				int seleccion = fc.showOpenDialog(getContentPane());
				if (seleccion == JFileChooser.APPROVE_OPTION) {
					File fichero = fc.getSelectedFile();
					try {
						ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero));
						 panel3 = (JPanel)ois.readObject();
						ois.close();
					new VentanaNivel(panel3);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						 } catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}

		});
		btn3.setBounds(392, 450, 100, 100);
		btn3.setOpaque(false);
		btn3.setContentAreaFilled(false);
		btn3.setBorderPainted(false);
		btn3.setIcon(new ImageIcon(new ImageIcon(VentanaMenu.class.getResource("/recursos/Boton3.png")).getImage()
				.getScaledInstance(btn3.getWidth(), btn3.getHeight(), Image.SCALE_DEFAULT)));
		frame.getContentPane().add(btn3);

		VentanaLogin vl = new VentanaLogin();
		
		//Se crea el boton para salir de la aplicacion
		JButton btn4 = new JButton("");
		btn4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //Cuando se pulsa el boton se muestra un mensaje para confirmar si se desea salir.
				Musica.clickBoton(volumenEfectos);
				int i = JOptionPane.showConfirmDialog(frame, "¿Está seguro de que quiere salir?"); //Se muestra el mensaje
				if ( i== 0) { //En caso de que la respuesta sea afirmativa se sale de la aplicacion.
					System.exit(0); //Se cierra la aplicacion
				} else if (i == 1){ //En caso de que la respuesta sea negativa, se muestra otro mensaje para comprobar si se desea cambiar de usuario
					int a = JOptionPane.showConfirmDialog(frame, "¿Desea cambiar de usuario?"); //Se muestra el segundo mensaje
					if (a == 0) { // //En caso de que la respuesta sea afirmativa se cierra la ventanaMenu y se abre la ventanaLogin donde podremos cambiar de usuario.
						vl.main(null);
						frame.dispose();
					}
					
				}
			}
		});
		btn4.setBounds(10, 475, 75, 75);
		btn4.setOpaque(false);
		btn4.setContentAreaFilled(false);
		btn4.setBorderPainted(false);
		btn4.setIcon(new ImageIcon(new ImageIcon(VentanaMenu.class.getResource("/recursos/Boton4.png")).getImage()
				.getScaledInstance(btn4.getWidth(), btn4.getHeight(), Image.SCALE_DEFAULT)));
		frame.getContentPane().add(btn4);

		//Se crea el boton para desconectar el volumen general
		JButton btn5 = new JButton("");
		btn5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn5.setBounds(10, 5, 45, 45);
		ImageIcon on = new ImageIcon(VentanaMenu.class.getResource("/recursos/SonidoON.png"));
		ImageIcon iconoON = new ImageIcon(
				on.getImage().getScaledInstance(btn5.getWidth(), btn5.getHeight(), Image.SCALE_DEFAULT));
		ImageIcon off = new ImageIcon(VentanaMenu.class.getResource("/recursos/SonidoOFF.png"));
		ImageIcon iconoOFF = new ImageIcon(
				off.getImage().getScaledInstance(btn5.getWidth(), btn5.getHeight(), Image.SCALE_DEFAULT));
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Musica.clickBoton(volumenEfectos);
				if (btn5.getIcon().equals(iconoON)) {
					btn5.setIcon(iconoOFF);
					Musica.stop(6);
					volumenMenu = -80;
					volumenEfectos = -80;
					volumenPartida = -80;
				} else {
					btn5.setIcon(iconoON);
					volumenMenu = 0;
					volumenEfectos = 0;
					volumenPartida = 0;
					Musica.musicaMenu(volumenMenu);
				}
			}
		});
		btn5.setOpaque(false);
		btn5.setContentAreaFilled(false);
		btn5.setBorderPainted(false);
		if (volumenMenu == -80) { //En caso de que el volumen este bajado 
			btn5.setIcon(iconoOFF); //El icono se pone en modo OFF
		} else { //En caso de que el volumen este subido
			btn5.setIcon(iconoON); //El icono se pone en modo ON
		}
		frame.getContentPane().add(btn5);

		//Se crea el boton de acceso al editor de niveles
		JButton btn6 = new JButton("");
		btn6.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditorDeNiveles.main(null);
			}
		});
		btn6.setBounds(282, 450, 100, 100);
		btn6.setOpaque(false);
		btn6.setContentAreaFilled(false);
		btn6.setBorderPainted(false);
		btn6.setIcon(new ImageIcon(new ImageIcon(VentanaMenu.class.getResource("/recursos/Boton5.png")).getImage()
				.getScaledInstance(btn6.getWidth(), btn6.getHeight(), Image.SCALE_DEFAULT)));
		frame.getContentPane().add(btn6);

		//Se crea el Lavel donde se muestra el titulo principal de la aplicacion
		JLabel label = new JLabel("");
		label.setBounds(142, 5, 500, 150);
		label.setOpaque(false);
		label.setIcon(new ImageIcon(new ImageIcon(VentanaMenu.class.getResource("/recursos/Title.png")).getImage()
				.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT)));
		frame.getContentPane().add(label);

		//Se crea el JLavel del fondo donde se muestra el fondo
		JLabel lblFondo = new JLabel("");
		lblFondo.setBounds(-8, 5, 800, 600);
		lblFondo.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon icono = new ImageIcon(
				fondo.getImage().getScaledInstance(lblFondo.getWidth(), lblFondo.getHeight(), Image.SCALE_DEFAULT));

		//Se crea el JLavel donde se muestra el nombre del usuario con el que se ha iniciado la aplicacion
		JLabel lblUsuario = new JLabel("Hola, " + this.usuario.toUpperCase() + "!");
		lblUsuario.setFont(new Font("Consolas", Font.PLAIN, 15));
		lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setBounds(654, 17, 126, 19);
		frame.getContentPane().add(lblUsuario);

		lblFondo.setIcon(icono);
		frame.getContentPane().add(lblFondo);
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getDificultad() {
		return dificultad;
	}

	public void setDificultad(int dificultad) {
		this.dificultad = dificultad;
	}

	public float getVolumenEfectos() {
		return volumenEfectos;
	}

	public void setVolumenEfectos(float volumenEfectos) {
		this.volumenEfectos = volumenEfectos;
	}

	public float getVolumenPartida() {
		return volumenPartida;
	}

	public void setVolumenPartida(float volumenPartida) {
		this.volumenPartida = volumenPartida;
	}

	public float getVolumenMenu() {
		return volumenMenu;
	}

	public void setVolumenMenu(float volumenMenu) {
		this.volumenMenu = volumenMenu;
	}
}
