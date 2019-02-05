package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;

import baseDeDatos.BD;
import data.HiloGameOver1;
import data.HiloGameOver2;
import data.Musica;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

public class GameOver extends JFrame {
	private JPanel panel;
	private JFrame frame;
	private JLabel lblGameOver, lbl1, lbl2;
	private String usuario;
	
	
	

	 
	
	/**
	 * Launch the application.
	 */
	public static void main(String args, int dificultad, float volumenE, float volumenM, float volumenP, String fondo, int serpienteSeleccionada) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameOver window = new GameOver(args, dificultad, volumenE, volumenM, volumenP, fondo, serpienteSeleccionada);
					window.frame.setLocationRelativeTo(null);
					window.frame.setVisible(true);
					window.hilos();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameOver(String usuario, int dificultad, float volumenE, float volumenM, float volumenP, String fondo, int serpienteSeleccionada) {
		this.usuario = usuario;
		initialize(dificultad, volumenE, volumenM, volumenP, fondo, serpienteSeleccionada);
		
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int dificultad, float volumenE, float volumenM, float volumenP, String fondo, int serpienteSeleccionada) {
		
		Musica.stop(1);
		Musica.gameOver(volumenP);
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(GameOver.class.getResource("/recursos/SnakeIcon.png")));
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					frame.dispose();
					VentanaMenu v = new VentanaMenu(usuario, dificultad, volumenE, volumenM, volumenP, fondo, serpienteSeleccionada);
					v.main(usuario, dificultad, volumenE, volumenM, volumenP, fondo, serpienteSeleccionada);
				} else if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
			}
		});
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(100, 100, 450, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setBackground(Color.BLACK);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		lblGameOver = new JLabel("GAME OVER");
		lblGameOver.setForeground(Color.WHITE);
		lblGameOver.setFont(new Font("OCR A Extended", Font.PLAIN, 50));
		lblGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblGameOver, BorderLayout.CENTER);
	}
	
	//Metodo en el que se ejecutan los distintos hilos del programa.
	public void hilos() {
		(new Thread() {

			@Override
			public void run() {
				HiloGameOver2 hilo3 = new HiloGameOver2(lblGameOver.getText(), lblGameOver, GameOver.this);
				Thread thread3 = new Thread(hilo3);
				thread3.start();
				try {
					thread3.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				lbl1 = new JLabel("Pulsa ENTER para volver aL men�");
				lbl1.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
				lbl1.setHorizontalAlignment(SwingConstants.CENTER);
				lbl1.setForeground(Color.WHITE);
				panel.add(lbl1, BorderLayout.NORTH);

				lbl2 = new JLabel("Pulsa ESC para salir");
				lbl2.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
				lbl2.setHorizontalAlignment(SwingConstants.CENTER);
				lbl2.setForeground(Color.WHITE);
				panel.add(lbl2, BorderLayout.SOUTH);
				panel.revalidate();
				HiloGameOver1 hilo1 = new HiloGameOver1(lbl1, GameOver.this, 800);
				Thread thread1 = new Thread(hilo1);

				HiloGameOver1 hilo2 = new HiloGameOver1(lbl2, GameOver.this, 800);
				Thread thread2 = new Thread(hilo2);

				thread1.start();
				thread2.start();
			}
		}).start();

	}

}
