package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import baseDeDatos.BD;
import contenedores.GestionFicheros;
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

	private JPanel contentPane;
	private JTextField txtNombreUsuario;
	private JPasswordField passwordField;
	public BD bd; // el atributo BD (public) para poder usarlo en todo el proyecto
	static VentanaLogin frame = new VentanaLogin();

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaLogin.class.getResource("/recursos/SnakeIcon.png")));

		bd = new BD(); // Dentro del constructor se conecta a la base de datos y
		// crea la sentencia
		/*
		 * hay que crear manejador de fichero para indicar a qué fichero se
		 * mandarán los logs
		 */
		Handler fileHandler = null;
		try {
			fileHandler = new FileHandler("./prueba.log", true);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
				String nom = txtNombreUsuario.getText();
				String con = passwordField.getText();
				if (nom.equals("")) {
					JOptionPane.showMessageDialog(null, "El campo nombre no puede estar vacío");
					logger.log(Level.INFO, "Ha dejado el campo nombre vacío");
				} else if (con.equals("")) {
					JOptionPane.showMessageDialog(null, "El campo contraseña no puede estar vacío", "ERROR!!",
							JOptionPane.ERROR_MESSAGE);
					logger.log(Level.INFO, "Ha dejado el campo contraseña vacío");
				} else {
					int resul = bd.existeUsuario(nom, con);
					if (resul == 0) {
						String resp = JOptionPane.showInputDialog("No estás registrado. ¿Quieres registrarte? (S/N)");
						if (resp.equalsIgnoreCase("S")) {
							bd.registrarUsuario(nom, con);
							JOptionPane.showMessageDialog(null, "Usuario registrado con éxito", "OK",
									JOptionPane.INFORMATION_MESSAGE);
							vaciarCampos();
						} else {
							JOptionPane.showMessageDialog(null, "Hasta otra!!");
						}
					} else if (resul == 1) {
						JOptionPane.showMessageDialog(null, "La contraseña no es correcta!!");
						logger.log(Level.SEVERE, "Se ha equivocado en la contraseña");
					} else {
						JOptionPane.showMessageDialog(null, "BIENVENIDO");
						v.dispose();
						VentanaMenu v = new VentanaMenu(nom,65,0,0,0,"");
						v.main(nom,65,0,0,0,"");

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
		long milis = System.currentTimeMillis(); // fecha del sistema
		DateFormat df = new SimpleDateFormat("d/M/y hh:mm"); // dar formato a la
		// fecha
		Date d = new Date(milis);
		lblFecha.setText("Fecha: " + df.format(d));
	}
}
