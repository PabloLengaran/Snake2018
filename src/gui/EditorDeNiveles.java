package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class EditorDeNiveles extends JFrame {

	private JPanel contentPane, panelCentro;
	private String imagen;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditorDeNiveles frame = new EditorDeNiveles();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void inicializarPanel(){
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				panelCentro.add(new JLabel(" "));
			}
		}
	}
	/**
	 * Create the frame.
	 */
	public EditorDeNiveles() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditorDeNiveles.class.getResource("/recursos/SnakeIcon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		panelSur.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		//boton del que se selecciona la imagen de MuroHorizontal de la carpeta de recursos.
		JButton btnNewButton = new JButton("Horizontal");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imagen = "/recursos/MuroHorizontal.jpg";
				
				
			}
		});
		panelSur.add(btnNewButton);
	//boton del que se selecciona la imagen de MuroVertical de la carpeta de recursos.
		
		JButton btnNewButton_1 = new JButton("Vertical");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imagen = "/recursos/MuroVertical.jpg";}
		}); 
		panelSur.add(btnNewButton_1);
		//Metodo que guarda el contenido del panel central en un con extension .dat(fichero de datos).
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int seleccion = fc.showSaveDialog(getContentPane());
				if (seleccion == JFileChooser.APPROVE_OPTION)
				{
				   File fichero = fc.getSelectedFile();
				   ObjectOutputStream oos;
				try {
					oos = new ObjectOutputStream(new FileOutputStream(fichero));
					oos.writeObject(panelCentro);
					oos.flush();
					oos.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				  
				}
				dispose();
			}
		});
		panelSur.add(btnGuardar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		panelSur.add(btnVolver);
		
		panelCentro = new JPanel();
		contentPane.add(panelCentro, BorderLayout.CENTER);
		panelCentro.setLayout(new GridLayout(10, 10, 0, 0));
		inicializarPanel();
		panelCentro.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			//metodo que al clickar coloca la imagen seleccionada donde se ha hecho el ultimo click .
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				Point p = e.getPoint();
				JLabel l = (JLabel)panelCentro.getComponentAt(p);
				ImageIcon im = new ImageIcon(EditorDeNiveles.class.getResource(imagen));
				im.setDescription(imagen);
				l.setIcon(im);
				panelCentro.updateUI();
				
			}
		});
	}

}
