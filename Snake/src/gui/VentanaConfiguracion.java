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
import data.Musica;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import java.awt.Cursor;
import java.awt.Toolkit;

public class VentanaConfiguracion extends JFrame {

	private JFrame frame = new JFrame();
	private JPanel contentPane;
	private Object[] dificultad = { "Principiante", "Fácil", "Media", "Difícil", "Extremo" };
	private JComboBox comboBox;
	private JSlider slider, slider_1, slider_2, slider_3;
	private int resultado = 65;
	private float volumenEfectos;
	private float volumenPartida;
	private float volumenMenu;
	private String fondo;

	/**
	 * Launch the application.
	 */
	public static void main(String args, int dificultadAnterior, float volumenEAnterior, float volumenMAnterior,
			float volumenPAnterior, String fondoAnterior) {
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaConfiguracion frame = new VentanaConfiguracion(args, dificultadAnterior, volumenEAnterior,
							volumenMAnterior, volumenPAnterior, fondoAnterior);
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
			float volumenPAnterior, String fondoAnterior) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaConfiguracion.class.getResource("/recursos/SnakeIcon.png")));
		this.fondo = fondoAnterior;
		VentanaMenu v = new VentanaMenu(usuario, resultado, volumenEAnterior, volumenMAnterior, volumenPAnterior, fondoAnterior);
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

		comboBox = new JComboBox();
		comboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		for (int i = 0; i < dificultad.length; i++) {
			comboBox.addItem(dificultad[i]);
		}

		if (dificultadAnterior == 65) {
			comboBox.setSelectedItem("Media");
		} else if (dificultadAnterior == 100) {
			comboBox.setSelectedItem("Principiante");
		} else if (dificultadAnterior == 80) {
			comboBox.setSelectedItem("Fácil");
		} else if (dificultadAnterior == 45) {
			comboBox.setSelectedItem("Difícil");
		} else {
			comboBox.setSelectedItem("Extremo");
		}

		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemSeleecionado = (String) comboBox.getSelectedItem();
				if (itemSeleecionado.equals("Principiante")) {
					resultado = 100;
					v.setDificultad(resultado);
				} else if (itemSeleecionado.equals("Fácil")) {
					resultado = 80;
					v.setDificultad(resultado);
				} else if (itemSeleecionado.equals("Media")) {
					resultado = 65;
					v.setDificultad(resultado);
				} else if (itemSeleecionado.equals("Difícil")) {
					resultado = 45;
					v.setDificultad(resultado);
				} else {
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

		JCheckBox chckbxSilenciar = new JCheckBox("Silenciar");
		chckbxSilenciar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxSilenciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxSilenciar.isSelected()) {
					Musica.click(volumenEfectos);
					volumenMenu = -80;
					slider.setValue((int) volumenMenu);
				} else {
					Musica.click(volumenEfectos);
					volumenMenu = 0;
					slider.setValue((int) volumenMenu);
				}
			}
		});
		chckbxSilenciar.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_7.add(chckbxSilenciar);

		slider = new JSlider();
		slider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		slider.setFont(new Font("Tahoma", Font.PLAIN, 15));
		slider.setMinorTickSpacing(5);
		slider.setMajorTickSpacing(20);
		slider.setPaintTicks(true);
		// slider.addChangeListener(new ChangeListener() {
		// public void stateChanged(ChangeEvent arg0) {
		// volumenMenu = slider.getValue();
		// }
		// });
		slider.setMinimum(-80);
		slider.setMaximum(0);
		panel_7.add(slider);

		if (volumenMAnterior == -80) {
			chckbxSilenciar.setSelected(true);
			slider.setValue((int) volumenMAnterior);
		}

		JPanel panel_8 = new JPanel();
		panel_2.add(panel_8);
		panel_8.setLayout(new GridLayout(1, 3, 0, 0));

		JLabel lblVolumenDePartida = new JLabel("Volumen de partida");
		lblVolumenDePartida.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_8.add(lblVolumenDePartida);

		JCheckBox chckbxSilenciar_1 = new JCheckBox("Silenciar");
		chckbxSilenciar_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxSilenciar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxSilenciar_1.isSelected()) {
					Musica.click(volumenEfectos);
					volumenPartida = -80;
					slider_1.setValue((int) volumenPartida);
				} else {
					Musica.click(volumenEfectos);
					volumenPartida = 0;
					slider_1.setValue((int) volumenPartida);
				}
			}
		});
		chckbxSilenciar_1.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_8.add(chckbxSilenciar_1);

		slider_1 = new JSlider();
		// slider_1.addChangeListener(new ChangeListener() {
		// public void stateChanged(ChangeEvent e) {
		// volumenPartida = slider_1.getValue();
		// }
		// });
		slider_1.setMinorTickSpacing(5);
		slider_1.setMajorTickSpacing(20);
		slider_1.setPaintTicks(true);
		slider_1.setMinimum(-80);
		slider_1.setMaximum(0);
		panel_8.add(slider_1);

		if (volumenPAnterior == -80) {
			chckbxSilenciar_1.setSelected(true);
			slider_1.setValue((int) volumenPAnterior);
		}

		JPanel panel_9 = new JPanel();
		panel_2.add(panel_9);
		panel_9.setLayout(new GridLayout(1, 3, 0, 0));

		JLabel lblVolumenDeEfectos = new JLabel("Volumen de efectos");
		lblVolumenDeEfectos.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_9.add(lblVolumenDeEfectos);

		JCheckBox chckbxSilenciar_2 = new JCheckBox("Silenciar");
		chckbxSilenciar_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxSilenciar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chckbxSilenciar_2.isSelected()) {
					Musica.click(volumenEfectos);
					volumenEfectos = -80;
					slider_2.setValue((int) volumenEfectos);
				} else {
					volumenEfectos = 0;
					slider_2.setValue((int) volumenEfectos);
				}
			}
		});
		chckbxSilenciar_2.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_9.add(chckbxSilenciar_2);

		slider_2 = new JSlider();
		// slider_2.addChangeListener(new ChangeListener() {
		// public void stateChanged(ChangeEvent e) {
		// if(slider_2.getValue()!= 0) {
		// volumenEfectos = slider.getValue();
		// } else {
		//
		// }
		// }
		// });
		slider_2.setMinorTickSpacing(5);
		slider_2.setMajorTickSpacing(20);
		slider_2.setPaintTicks(true);
		slider_2.setMinimum(-80);
		slider_2.setMaximum(0);
		panel_9.add(slider_2);

		if (volumenEAnterior == -80) {
			chckbxSilenciar_2.setSelected(true);
			slider_2.setValue((int) volumenEAnterior);
		}

		JPanel panel_6 = new JPanel();
		panel_2.add(panel_6);
		panel_6.setLayout(new GridLayout(0, 3, 0, 0));

		JLabel lblVolumenGeneral = new JLabel("Volumen general");
		lblVolumenGeneral.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_6.add(lblVolumenGeneral);

		JCheckBox chckbxSilenciar_3 = new JCheckBox("Silenciar");
		chckbxSilenciar_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxSilenciar_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxSilenciar_3.isSelected()) {
					Musica.click(volumenEfectos);
					slider_3.setValue(-80);
					volumenEfectos = -80;
					slider_2.setValue((int) volumenEfectos);
					chckbxSilenciar_2.setSelected(true);
					chckbxSilenciar_2.setEnabled(false);
					slider_2.setEnabled(false);
					volumenMenu = -80;
					slider.setValue((int) volumenMenu);
					chckbxSilenciar.setSelected(true);
					chckbxSilenciar.setEnabled(false);
					slider.setEnabled(false);
					volumenPartida = -80;
					slider_1.setValue((int) volumenPartida);
					chckbxSilenciar_1.setSelected(true);
					chckbxSilenciar_1.setEnabled(false);
					slider_1.setEnabled(false);
				} else if (!chckbxSilenciar_3.isSelected()) {
					Musica.click(volumenEfectos);
					slider_3.setValue(0);
					volumenEfectos = 0;
					slider_2.setValue((int) volumenEfectos);
					chckbxSilenciar_2.setSelected(false);
					chckbxSilenciar_2.setEnabled(true);
					slider_2.setEnabled(true);
					volumenMenu = 0;
					slider.setValue((int) volumenMenu);
					chckbxSilenciar.setSelected(false);
					chckbxSilenciar.setEnabled(true);
					slider.setEnabled(true);
					volumenPartida = 0;
					slider_1.setValue((int) volumenPartida);
					chckbxSilenciar_1.setSelected(false);
					chckbxSilenciar_1.setEnabled(true);
					slider_1.setEnabled(true);
				}
			}
		});
		chckbxSilenciar_3.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_6.add(chckbxSilenciar_3);

		slider_3 = new JSlider();
		// slider_3.addChangeListener(new ChangeListener() {
		// public void stateChanged(ChangeEvent e) {
		// if (slider_3.getValue() != 0) {
		// volumenEfectos = slider_3.getValue();
		// slider_2.setValue(slider_3.getValue());
		// slider_2.setEnabled(false);
		// volumenMenu = slider_3.getValue();
		// slider_1.setValue(slider_3.getValue());
		// slider_1.setEnabled(false);
		// volumenPartida = slider_3.getValue();
		// slider.setValue(slider_3.getValue());
		// slider.setEnabled(false);
		// } else {
		// slider.setEnabled(true);
		// slider.setValue(slider_3.getValue());
		// slider_1.setEnabled(true);
		// slider_1.setValue(slider_3.getValue());
		// slider_2.setEnabled(true);
		// slider_2.setValue(slider_3.getValue());
		// }
		// }
		// });
		slider_3.setMinorTickSpacing(5);
		slider_3.setMajorTickSpacing(20);
		slider_3.setPaintTicks(true);
		slider_3.setMinimum(-80);
		slider_3.setMaximum(0);
		panel_6.add(slider_3);

		if (volumenPAnterior == -80 && volumenMAnterior == -80 && volumenEAnterior == -80) {
			chckbxSilenciar_3.setSelected(true);
			slider_3.setValue(-80);
			chckbxSilenciar.setSelected(true);
			chckbxSilenciar.setEnabled(false);
			slider.setValue((int) volumenMAnterior);
			chckbxSilenciar_1.setSelected(true);
			chckbxSilenciar_1.setEnabled(false);
			slider_1.setValue((int) volumenPAnterior);
			chckbxSilenciar_2.setSelected(true);
			chckbxSilenciar_2.setEnabled(false);
			slider_2.setValue((int) volumenEAnterior);
		}

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
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setOpaque(false);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setIcon(new ImageIcon(VentanaConfiguracion.class.getResource("/recursos/Fondo.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fondo = "../recursos/Fondo.png";
			}
		});
		panel_10.add(btnNewButton_1);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fondo = "../recursos/Fondo3.jpg";
			}
		});
		btnNewButton_3.setIcon(new ImageIcon(VentanaConfiguracion.class.getResource("/recursos/Fondo3.jpg")));
		panel_10.add(btnNewButton_3);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fondo = "../recursos/Fondo4.jpg";
			}
		});
		btnNewButton.setIcon(new ImageIcon(VentanaConfiguracion.class.getResource("/recursos/Fondo4.jpg")));
		panel_10.add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fondo = "../recursos/Fondo2.png";
			}
		});
		btnNewButton_2.setIcon(new ImageIcon(VentanaConfiguracion.class.getResource("/recursos/Fondo2.png")));
		panel_10.add(btnNewButton_2);

		JPanel panel = new JPanel();
		panelPrincipal.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCancelar.setBackground(SystemColor.textHighlight);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Consolas", Font.PLAIN, 15));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Musica.clickBoton(volumenEAnterior);
				v.main(usuario, dificultadAnterior, volumenEAnterior, volumenMAnterior, volumenPAnterior, fondoAnterior);
				dispose();
			}
		});
		panel.add(btnCancelar, BorderLayout.WEST);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setBackground(SystemColor.textHighlight);
		btnGuardar.setFont(new Font("Consolas", Font.PLAIN, 15));
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Musica.clickBoton(volumenEfectos);
				v.main(usuario, resultado, volumenEfectos, volumenMenu, volumenPartida,fondo);
				dispose();
			}
		});
		panel.add(btnGuardar, BorderLayout.EAST);
	}

}
