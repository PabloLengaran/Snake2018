package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import baseDeDatos.BD;
import data.Musica;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import java.awt.Cursor;
import java.awt.Toolkit;

public class VentanaConfiguracion extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame();
	private JPanel contentPane;
	private Object[] dificultad = { "Principiante", "Sencillo", "Media", "Dificil", "Extremo" };
	private JComboBox comboBox;
	private JSlider sliderMenu, sliderPartida, sliderEfectos, sliderTodo;
	private int resultado = 65;
	private float volumenEfectos;
	private float volumenPartida;
	private float volumenMenu;
	private String fondo;
	private int snakeSelected;
	private Connection conUsuarios;	
	private Statement stUsuarios;
	private int creditos;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String args, int dificultadAnterior, float volumenEAnterior, float volumenMAnterior,
			float volumenPAnterior, String fondoAnterior, int serpienteSeleccionada) {
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaConfiguracion frame = new VentanaConfiguracion(args, dificultadAnterior, volumenEAnterior,
							volumenMAnterior, volumenPAnterior, fondoAnterior, serpienteSeleccionada);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaConfiguracion(String usuario, int dificultadAnterior, float volumenEAnterior, float volumenMAnterior,
			float volumenPAnterior, String fondoAnterior, int serpienteSeleccionada) {
		//Inicializacion de la tabla usuarios
		conUsuarios = BD.initBD("Usuarios");
		stUsuarios = BD.usarCrearTablasBD(conUsuarios);
		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaConfiguracion.class.getResource("/recursos/SnakeIcon.png")));
		this.fondo = fondoAnterior;
		VentanaMenu v = new VentanaMenu(usuario, resultado, volumenEAnterior, volumenMAnterior, volumenPAnterior, fondoAnterior, serpienteSeleccionada);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBorder(BorderFactory.createTitledBorder(".:SNAKE:."));
		contentPane.add(panelPrincipal, BorderLayout.CENTER);
		panelPrincipal.setLayout(new GridLayout(4, 1, 0, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelPrincipal.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JLabel lblDificultad = new JLabel("Dificultad");
		lblDificultad.setHorizontalAlignment(SwingConstants.CENTER);
		lblDificultad.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_1.add(lblDificultad, BorderLayout.NORTH);

		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		//Se crea el JComboBox de eleccion de la dificultad
		comboBox = new JComboBox();
		comboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		for (int i = 0; i < dificultad.length; i++) {
			comboBox.addItem(dificultad[i]);
		}
		
		if (dificultadAnterior == 65) { //Si la dificultad anterior es igual a 65 la dificultad sera Media
			comboBox.setSelectedItem("Media");
		} else if (dificultadAnterior == 100) { //Si la dificultad anterior es igual a 100 la dificultad sera Principiante
			comboBox.setSelectedItem("Principiante");
		} else if (dificultadAnterior == 80) { //Si la dificultad anterior es igual a 80 la dificultad sera Sencillo
			comboBox.setSelectedItem("Sencillo");
		} else if (dificultadAnterior == 45) { //Si la dificultad anterior es igual a 45 la dificultad sera Dificil
			comboBox.setSelectedItem("Dificil");
		} else { //Si no es ninguno de los anterirores la dificultad sera Extremo
			comboBox.setSelectedItem("Extremo");
		}

		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemSeleecionado = (String) comboBox.getSelectedItem();
				if (itemSeleecionado.equals("Principiante")) { //Si el item que hemos seleccionado es PRINCIPIANTE la dificultad sera de 100
					resultado = 100;
					v.setDificultad(resultado);
				} else if (itemSeleecionado.equals("Sencillo")) { //Si el item que hemos seleccionado es SENCILLO la dificultad sera de 100
					resultado = 80;
					v.setDificultad(resultado);
				} else if (itemSeleecionado.equals("Media")) { //Si el item que hemos seleccionado es MEDIA la dificultad sera de 100
					resultado = 65;
					v.setDificultad(resultado);
				} else if (itemSeleecionado.equals("Dificil")) { //Si el item que hemos seleccionado es DIFICIL la dificultad sera de 45
					resultado = 45;
					v.setDificultad(resultado);
				} else { //Si el item que hemos seleccionado no es ninguno de los anteriores la dificultad sera de 25
					resultado = 25;
					v.setDificultad(resultado);
				}
			}
		});
		panel_5.add(comboBox);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelPrincipal.add(panel_2);
		panel_2.setLayout(new GridLayout(4, 0, 0, 0));

		JPanel panel_7 = new JPanel();
		panel_2.add(panel_7);
		panel_7.setLayout(new GridLayout(1, 3, 0, 0));

		JLabel lblVolumenDelMen = new JLabel("Volumen del men\u00FA");
		lblVolumenDelMen.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_7.add(lblVolumenDelMen);

		JCheckBox chckbxSilenciarMenu = new JCheckBox("Silenciar");
		chckbxSilenciarMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxSilenciarMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxSilenciarMenu.isSelected()) {
					Musica.click(volumenEfectos);
					volumenMenu = -80;
					sliderMenu.setValue((int) volumenMenu);
				} else {
					Musica.click(volumenEfectos);
					volumenMenu = 0;
					sliderMenu.setValue((int) volumenMenu);
				}
			}
		});
		chckbxSilenciarMenu.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_7.add(chckbxSilenciarMenu);

		sliderMenu = new JSlider();
		sliderMenu.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				volumenMenu = sliderMenu.getValue();
				//Comprobamos si el volumen está al minimo y en caso de ser asi seleccionamos el checkbox.
				if (volumenMenu == -80) {
					chckbxSilenciarMenu.setSelected(true);
				} else {
					chckbxSilenciarMenu.setSelected(false);
				}
			}
		});
		sliderMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		sliderMenu.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sliderMenu.setMinorTickSpacing(5);
		sliderMenu.setMajorTickSpacing(20);
		sliderMenu.setPaintTicks(true);
		sliderMenu.setMinimum(-80);
		sliderMenu.setMaximum(0);
		panel_7.add(sliderMenu);

		if (volumenMAnterior == -80) {
			chckbxSilenciarMenu.setSelected(true);
			sliderMenu.setValue((int) volumenMAnterior);
		}

		JPanel panel_8 = new JPanel();
		panel_2.add(panel_8);
		panel_8.setLayout(new GridLayout(1, 3, 0, 0));

		JLabel lblVolumenDePartida = new JLabel("Volumen de partida");
		lblVolumenDePartida.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_8.add(lblVolumenDePartida);

		JCheckBox chckbxSilenciarPartida = new JCheckBox("Silenciar");
		chckbxSilenciarPartida.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxSilenciarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxSilenciarPartida.isSelected()) {
					Musica.click(volumenEfectos);
					volumenPartida = -80;
					sliderPartida.setValue((int) volumenPartida);
				} else {
					Musica.click(volumenEfectos);
					volumenPartida = 0;
					sliderPartida.setValue((int) volumenPartida);
				}
			}
		});
		chckbxSilenciarPartida.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_8.add(chckbxSilenciarPartida);

		sliderPartida = new JSlider();
		sliderPartida.addChangeListener(new ChangeListener() {
			 public void stateChanged(ChangeEvent e) {
				 volumenPartida = sliderPartida.getValue();
				 
				//Comprobamos si el volumen esta al minimo y en caso de ser asi seleccionamos el checkbox.
				 if (volumenPartida == -80) {
					chckbxSilenciarPartida.setSelected(true);
				} else {
					chckbxSilenciarPartida.setSelected(false);
				}
			 }
		});
		sliderPartida.setMinorTickSpacing(5);
		sliderPartida.setMajorTickSpacing(20);
		sliderPartida.setPaintTicks(true);
		sliderPartida.setMinimum(-80);
		sliderPartida.setMaximum(0);
		panel_8.add(sliderPartida);

		if (volumenPAnterior == -80) {
			chckbxSilenciarPartida.setSelected(true);
			sliderPartida.setValue((int) volumenPAnterior);
		}

		JPanel panel_9 = new JPanel();
		panel_2.add(panel_9);
		panel_9.setLayout(new GridLayout(1, 3, 0, 0));

		JLabel lblVolumenDeEfectos = new JLabel("Volumen de efectos");
		lblVolumenDeEfectos.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_9.add(lblVolumenDeEfectos);

		JCheckBox chckbxSilenciarEfectos = new JCheckBox("Silenciar");
		chckbxSilenciarEfectos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxSilenciarEfectos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chckbxSilenciarEfectos.isSelected()) {
					Musica.click(volumenEfectos);
					volumenEfectos = -80;
					sliderEfectos.setValue((int) volumenEfectos);
				} else {
					volumenEfectos = 0;
					sliderEfectos.setValue((int) volumenEfectos);
				}
			}
		});
		chckbxSilenciarEfectos.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_9.add(chckbxSilenciarEfectos);

		sliderEfectos = new JSlider();
		sliderEfectos.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				volumenEfectos = sliderEfectos.getValue();
				
				//Comprobamos si el volumen está al minimo y en caso de ser asi seleccionamos el checkbox.
				if (volumenEfectos == -80) {
					chckbxSilenciarEfectos.setSelected(true);
				} else {
					chckbxSilenciarEfectos.setSelected(false);
				}
			}
		});
		sliderEfectos.setMinorTickSpacing(5);
		sliderEfectos.setMajorTickSpacing(20);
		sliderEfectos.setPaintTicks(true);
		sliderEfectos.setMinimum(-80);
		sliderEfectos.setMaximum(0);
		panel_9.add(sliderEfectos);

		if (volumenEAnterior == -80) {
			chckbxSilenciarEfectos.setSelected(true);
			sliderEfectos.setValue((int) volumenEAnterior);
		}

		JPanel panel_6 = new JPanel();
		panel_2.add(panel_6);
		panel_6.setLayout(new GridLayout(0, 3, 0, 0));

		JLabel lblVolumenGeneral = new JLabel("Volumen general");
		lblVolumenGeneral.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_6.add(lblVolumenGeneral);

		JCheckBox chckbxSilenciarTodo = new JCheckBox("Silenciar");
		chckbxSilenciarTodo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxSilenciarTodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxSilenciarTodo.isSelected()) {
					Musica.click(volumenEfectos);
					sliderTodo.setValue(-80);
					volumenEfectos = -80;
					sliderEfectos.setValue((int) volumenEfectos);
					chckbxSilenciarEfectos.setSelected(true);
					volumenMenu = -80;
					sliderMenu.setValue((int) volumenMenu);
					chckbxSilenciarMenu.setSelected(true);
					volumenPartida = -80;
					sliderPartida.setValue((int) volumenPartida);
					chckbxSilenciarPartida.setSelected(true);
				} else if (!chckbxSilenciarTodo.isSelected()) {
					Musica.click(volumenEfectos);
					sliderTodo.setValue(0);
					volumenEfectos = 0;
					sliderEfectos.setValue((int) volumenEfectos);
					chckbxSilenciarEfectos.setSelected(false);
					chckbxSilenciarEfectos.setEnabled(true);
					sliderEfectos.setEnabled(true);
					volumenMenu = 0;
					sliderMenu.setValue((int) volumenMenu);
					chckbxSilenciarMenu.setSelected(false);
					chckbxSilenciarMenu.setEnabled(true);
					sliderMenu.setEnabled(true);
					volumenPartida = 0;
					sliderPartida.setValue((int) volumenPartida);
					chckbxSilenciarPartida.setSelected(false);
					chckbxSilenciarPartida.setEnabled(true);
					sliderPartida.setEnabled(true);
				}
				
				
			}
		});
		chckbxSilenciarTodo.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_6.add(chckbxSilenciarTodo);

		sliderTodo = new JSlider();
		sliderTodo.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (sliderTodo.getValue() != 0) {
					volumenEfectos = sliderTodo.getValue();
					sliderEfectos.setValue(sliderTodo.getValue());
					sliderPartida.setValue(sliderTodo.getValue());
					volumenPartida = sliderTodo.getValue();
					volumenMenu = sliderTodo.getValue();
					sliderMenu.setValue(sliderTodo.getValue());
				} else {
					sliderMenu.setValue(sliderTodo.getValue());
					sliderPartida.setValue(sliderTodo.getValue());
					sliderEfectos.setValue(sliderTodo.getValue());
				}
				
				//Comprobamos si el volumen está al minimo y en caso de ser asi seleccionamos el checkbox.
				if (sliderTodo.getValue() == -80) {
					chckbxSilenciarTodo.setSelected(true);
				} else {
					chckbxSilenciarTodo.setSelected(false);
				}
			}
		 });
		sliderTodo.setMinorTickSpacing(5);
		sliderTodo.setMajorTickSpacing(20);
		sliderTodo.setPaintTicks(true);
		sliderTodo.setMinimum(-80);
		sliderTodo.setMaximum(0);
		panel_6.add(sliderTodo);

		

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelPrincipal.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblApariencia = new JLabel("Apariencia");
		lblApariencia.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_4.add(lblApariencia);
		
		JPanel panel_10 = new JPanel();
		panel_3.add(panel_10, BorderLayout.CENTER);
		panel_10.setLayout(new GridLayout(0, 4, 2, 0));
		
		//Se crea el boton para el cambio del primer fondo de pantalla de juego (Gratis)
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setOpaque(false);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setIcon(new ImageIcon(VentanaConfiguracion.class.getResource("/recursos/Fondo.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (creditos >=0){
				fondo = "../recursos/Fondo.png";
				}
			}
		});
		panel_10.add(btnNewButton_1);
		
		//Se crea el boton para el cambio del segundo fondo de pantalla de juego (Minimo = 100 creditos)
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (BD.creditosSelect(stUsuarios, usuario) >= 100) { //Se comprueba la disposicion de creditos
					fondo = "../recursos/Fondo3.jpg"; //En caso de ser superior se pone el nuevo fondo
				} else {
					JOptionPane.showMessageDialog(panelPrincipal, "Necesita minimo 100$");
				}
			}
		});
		btnNewButton_3.setIcon(new ImageIcon(VentanaConfiguracion.class.getResource("/recursos/Fondo3.jpg")));
		panel_10.add(btnNewButton_3);
		
		//Se crea el boton para el cambio del tercer fondo de pantalla de juego (Minimo = 200 creditos)
		JButton btnNewButton = new JButton("");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (BD.creditosSelect(stUsuarios, usuario) >= 200) { //Se comprueba la disposicion de creditos
					fondo = "../recursos/Fondo4.jpg"; //En caso de ser superior se pone el nuevo fondo
				} else {
					JOptionPane.showMessageDialog(panelPrincipal, "Necesita minimo 200$");
				}
			}
		});
		btnNewButton.setIcon(new ImageIcon(VentanaConfiguracion.class.getResource("/recursos/Fondo4.jpg")));
		panel_10.add(btnNewButton);
		
		//Se crea el boton para el cambio del cuarto fondo de pantalla de juego (Minimo = 400 creditos)
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (BD.creditosSelect(stUsuarios, usuario) >=400) { //Se comprueba la disposicion de creditos
					fondo = "../recursos/Fondo2.png"; //En caso de ser superior se pone el nuevo fondo
				} else {
					JOptionPane.showMessageDialog(panelPrincipal, "Necesita minimo 400$");
				}
				
			}
		});
		btnNewButton_2.setIcon(new ImageIcon(VentanaConfiguracion.class.getResource("/recursos/Fondo2.png")));
		panel_10.add(btnNewButton_2);

		JPanel panel = new JPanel();
		panelPrincipal.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		//Se crea el boton cancelar el cual se utiliza para regresar a la ventanaMenu sin guardar los cambios realizados en esta ventana
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCancelar.setBackground(SystemColor.textHighlight);
		btnCancelar.setForeground(Color.BLACK);
		btnCancelar.setFont(new Font("Consolas", Font.PLAIN, 15));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Musica.clickBoton(volumenEAnterior);
				v.main(usuario, dificultadAnterior, volumenEAnterior, volumenMAnterior, volumenPAnterior, fondoAnterior, serpienteSeleccionada);
				dispose();
			}
		});
		panel.add(btnCancelar, BorderLayout.WEST);
		
		//Se crea el boton para guardar los diferentes cambios realizados en los diferentes hambitos que podemos encontrar en esta ventana
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGuardar.setForeground(Color.BLACK);
		btnGuardar.setBackground(SystemColor.textHighlight);
		btnGuardar.setFont(new Font("Consolas", Font.PLAIN, 15));
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Musica.clickBoton(volumenEfectos);
				v.main(usuario, resultado, volumenEfectos, volumenMenu, volumenPartida, fondo, snakeSelected);
				dispose();
			}
		});
		panel.add(btnGuardar, BorderLayout.EAST);
		
		JPanel panel_11 = new JPanel();
		panel.add(panel_11, BorderLayout.CENTER);
		panel_11.setLayout(null);
		
		//Se crea el boton para la visualizacion de los creditos del usuario
		JButton btn_VerCreditos = new JButton("Creditos");
		btn_VerCreditos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creditos = BD.creditosSelect(stUsuarios, usuario); //Se obtiene los creditos que estan guardados en la base de datos y se guardan en una nueva variable
				JOptionPane.showMessageDialog(panelPrincipal, "Creditos de " + usuario.toUpperCase() + ": " + creditos + "$"); //Se muestra un mensaje informativo con el valor de la variable previamente mend¡cionada
			}
		});
		btn_VerCreditos.setBounds(302, 42, 126, 52);
		panel_11.add(btn_VerCreditos);
		
		
		//Se crea el boton para la visualizacion de la ventanaPuntuaciones
		JButton btn_Puntuaciones = new JButton("Puntuaciones");
		btn_Puntuaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaTablaPuntuaciones vtp = new VentanaTablaPuntuaciones(usuario); //Se visualiza la ventanaPuntuaciones
				vtp.setVisible(true);
			}
		});
		btn_Puntuaciones.setBounds(440, 42, 126, 52);
		panel_11.add(btn_Puntuaciones);
		
		
		this.snakeSelected = serpienteSeleccionada;
		
		//Se crea el boton de seleccion de la serpiente verde
		JButton btnNewButton_4 = new JButton("Serpiente Verde");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				snakeSelected = 0; //En caso de pulsar el boton se designara la serpiente de color verde a la partida
			}
		});
		btnNewButton_4.setBounds(6, 42, 117, 52);
		panel_11.add(btnNewButton_4);
		
		//Se crea el boton de seleccion o compra de la serpiente roja
		JButton button = new JButton("Serpiente Roja");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (BD.serpienteSelect(stUsuarios, usuario) == 0) { //Se comprueba en la base de datos si el usuario dispone de la serpiente Roja
					int i = JOptionPane.showConfirmDialog(panelPrincipal, "¿Desea comprar la serpiente roja?"); //Se pregunta si el usuario desea realizar la compra de la nueva serpiente
					if (i == 0) { //Si la respuesta es afirmativa 
						int creditos = BD.creditosSelect(stUsuarios, usuario); //Se obtiene a traves de la base de datos los creditos disponibles del usuario
						if (creditos >= 300) { //Se comprueba si el usuario dispone de los creditos necesarios
							BD.serpienteUpdate(stUsuarios, usuario, 1); //Se realiza un cambio en la base de datos en el valor de la serpiente
							creditos = creditos - 300; //Se resta el valor de la serpiente a los creditos que tenia el usuario con el que se habia inicializado la aplicacion
							BD.creditosUpdate(stUsuarios, usuario, creditos); //Se realiza un cambio en la base de datos en el valor de los creditos
							snakeSelected = 1; //Se cambia el valor de la serpiente seleccionada y por lo tanto se visualizara la serpiente de color Roja
						} else {
							JOptionPane.showMessageDialog(panelPrincipal, "No tiene suficientes creditos"); //Se muestra un mensaje informativo
						}
					}
				} else if (BD.serpienteSelect(stUsuarios, usuario) == 1) {
					//Se cambia el valor de la serpiente seleccionada y por lo tanto se visualizara la serpiente de color Roja
					snakeSelected = 1;
				}
			}
		});
		button.setBounds(135, 42, 117, 52);
		panel_11.add(button);
	}
}
