package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import baseDeDatos.BD;
import data.Usuario;
import gui.VentanaMenu;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Toolkit;

public class VentanaLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombreUsuario;
	private JPasswordField passwordField;
	static VentanaLogin frame = new VentanaLogin();
	
	private Connection connection;
	private Statement statement;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin frame = new VentanaLogin();
					frame.setLocationRelativeTo(null);
					frame.setResizable(false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void vaciarCampos() {
		txtNombreUsuario.setText("");
		passwordField.setText("");
	}

	/**
	 * Create the frame.
	 */
	public VentanaLogin() {
		setTitle("Snake");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaLogin.class.getResource("/recursos/SnakeIcon.png")));

		// crea la sentencia
		/*
		 * Hay que crear manejador de fichero para indicar a que fichero se mandaron los logs
		 */
		connection = BD.initBD("Usuarios");
		statement = BD.usarCrearTablasBD(connection);
		Handler fileHandler = null;
		 
		
		try {
			fileHandler = new FileHandler("./prueba.log", true);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		SimpleFormatter simpleFormatter = new SimpleFormatter();
		fileHandler.setFormatter(simpleFormatter);

		/* Creamos el log y se le asocia el manejador de ficheros */
		Logger logger = Logger.getLogger("myLogger");
		logger.addHandler(fileHandler);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panelNorte = new JPanel();
		contentPane.add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblLoging = new JLabel("SNAKE");
		lblLoging.setForeground(Color.RED);
		lblLoging.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panelNorte.add(lblLoging);

		JPanel panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		JFrame v = this;

		JButton btnAceptar = new JButton("ACEPTAR");
		/* REGISTRO USANDO LA BASE DE DATOS */
		btnAceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nombreUsuario = txtNombreUsuario.getText();
				String contraseniaUsuario = new String(passwordField.getPassword());
				if (nombreUsuario.equals("")) {
					//El textField del usuario se encuentra vacio
					JOptionPane.showMessageDialog(null, "El campo nombre no puede estar vac�o");
					logger.log(Level.INFO, "Ha dejado el campo nombre vac�o");
				} else if (contraseniaUsuario.equals("")) {
					//El passwordField de la contrasenya se encuentra vacio
					JOptionPane.showMessageDialog(null, "El campo contrase�a no puede estar vac�o", "ERROR!!",
							JOptionPane.ERROR_MESSAGE);
					logger.log(Level.INFO, "Ha dejado el campo contrase�a vac�o");
				} else {
					//Ambos campos se encuentran rellenados
					Usuario user = BD.usuarioSelect(statement, nombreUsuario); //Creamos un usuario recogiendo los datos de la base de datos teniendo en cuenta el nombre introducido
					if (user == null) {
						//Si el usuario no lo encontramos, preguntamos si se desea crear el usuario
						String resp = JOptionPane.showInputDialog("No est�s registrado. �Quieres registrarte? (S/N)");
						if (resp.equalsIgnoreCase("S")) {
							//En caso de que la respuesta sea afirmativa, creamos el usuario y lo añadimos a la base de datos. Una vez añadida pasamos a la siguiente ventana.
							BD.usuariosInsert(statement, nombreUsuario, contraseniaUsuario, 250);
							BD.administradoresInsert(statement, nombreUsuario, 0);
							BD.fondosInsert(statement, nombreUsuario, 1, 0, 0, 0);
							BD.partidasInsert(statement, nombreUsuario, 0, 0);
							BD.serpientesInsert(statement, nombreUsuario, 1, 0, 0, 0);
							JOptionPane.showMessageDialog(null, "Usuario registrado con �xito", "OK",
									JOptionPane.INFORMATION_MESSAGE);
							v.dispose();
							VentanaMenu v = new VentanaMenu(nombreUsuario,65,0,0,0,"", 0);
							v.main(nombreUsuario,65,0,0,0,"", 0);
						} else {
							//En caso de no querer crear un nuevo usuario, se muestra un mensaje y se cierra la aplicacion
							JOptionPane.showMessageDialog(null, "Hasta otra!!");
							dispose();
						}
					} else {
						// Usuario existe, Comprobar contraseña
						if(user.getConstrasenia().equals(contraseniaUsuario)){
							//Si la contrasenya es correcta, abrimos la siguiente ventana
							JOptionPane.showMessageDialog(null, "BIENVENIDO");
							v.dispose();
							VentanaMenu v = new VentanaMenu(nombreUsuario,65,0,0,0,"", 0);
							v.main(nombreUsuario,65,0,0,0,"", 0);
						}
						else {
							//En caso de que la contrasenya sea incorrecta se muestra un mensaje
							JOptionPane.showMessageDialog(null, "La contrase�a no es correcta!!");
							logger.log(Level.SEVERE, "Se ha equivocado en la contrase�a");
						}
						

					}

				}

			}

		});
		
		
		panelSur.add(btnAceptar);

		JPanel panelIzda = new JPanel();
		contentPane.add(panelIzda, BorderLayout.WEST);

		JPanel panelDcha = new JPanel();
		contentPane.add(panelDcha, BorderLayout.EAST);

		JPanel panelCentro = new JPanel();
		contentPane.add(panelCentro, BorderLayout.CENTER);
		panelCentro.setLayout(null);

		JLabel lblNombreDeUsuario = new JLabel("NOMBRE DE USUARIO");
		lblNombreDeUsuario.setBounds(42, 38, 122, 25);
		panelCentro.add(lblNombreDeUsuario);

		txtNombreUsuario = new JTextField();
		txtNombreUsuario.setBounds(188, 38, 176, 25);
		panelCentro.add(txtNombreUsuario);
		txtNombreUsuario.setColumns(10);

		JLabel lblContrasea = new JLabel("CONTRASE\u00D1A");
		lblContrasea.setBounds(42, 101, 122, 25);
		panelCentro.add(lblContrasea);

		passwordField = new JPasswordField();
		passwordField.setBounds(188, 103, 176, 23);
		panelCentro.add(passwordField);
		
		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFecha.setBounds(124, 11, 176, 14);
		panelCentro.add(lblFecha);
		long milis = System.currentTimeMillis(); // Fecha del sistema
		DateFormat df = new SimpleDateFormat("d/M/y hh:mm"); // Ponemos el formato que queremos a la fecha
		Date d = new Date(milis);
		lblFecha.setText("Fecha: " + df.format(d));
		
		
	}
}
