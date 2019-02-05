package gui;

import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import baseDeDatos.BD;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class VentanaTablaPuntuacionesAdministradores extends JFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultTableModel modeloTabla = new DefaultTableModel();
	private JTable tabla = new JTable(modeloTabla);
	private Connection conUsuarios;	
	private Statement stUsuarios;
	
	public static void main(String args) {
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaTablaPuntuacionesAdministradores frame = new VentanaTablaPuntuacionesAdministradores(args);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		});
	}
	
	
	
	public VentanaTablaPuntuacionesAdministradores(String usuario) {
		setResizable(false);
		//Conectamos con la base de datos
		conUsuarios = BD.initBD("Usuarios");
		stUsuarios = BD.usarCrearTablasBD(conUsuarios);
		
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setSize( 536, 300 );
		getContentPane().setLayout(null);
		
		//Metemos la tabla en el scrollPane
		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setBounds(6, 6, 382, 266);
		getContentPane().add(scrollPane);
		
		//Añadimos las columnas al modelo de la tabla
		modeloTabla.addColumn("Nombre");
		modeloTabla.addColumn("Puntuaciones");
		
		
		//Creamos el boton "Privadas" donde veremos los nombres y las partidas del jugador con el que nos hemos logeado.
		JButton btnNewButton = new JButton("Privadas");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Cogemos todas las puntuaciones del usuario al que estamos conectado.
				ResultSet rs = BD.puntuacionesSelect(stUsuarios, usuario);
				try {
					tabla.setModel(buildTableModel(rs));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(410, 44, 117, 29);
		getContentPane().add(btnNewButton);
		
		//Creamos el boton "Generales" donde veremos los nombres y las partidas de todos los jugadores.
		JButton btnGenerales = new JButton("Generales");
		btnGenerales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Cogemos los datos de la base de datos de todas las puntuaciones de todos los usuarios.
				ResultSet rs = BD.puntuacionesSelect(stUsuarios, null);
				try {
					tabla.setModel(buildTableModel(rs)); //Se añade el resultado obtenido en la llamada a funcion previamente realizada
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnGenerales.setBounds(410, 85, 117, 29);
		getContentPane().add(btnGenerales);
		
		//Creamos el boton regresar para que cuando le demos regresemos a la ventana anterior
		JButton btnRegresar = new JButton("Regresar");
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); //Utilizamos el metodo dispose para cerrar la ventana
			}
		});
		btnRegresar.setBounds(410, 231, 117, 29);
		getContentPane().add(btnRegresar);
		
		//Creamos un JLabel para especificar que se pueden elegir dos tipos de partidas
		JLabel lblTiposDePartidas = new JLabel("Tipos de partidas:");
		lblTiposDePartidas.setBounds(400, 6, 130, 26);
		getContentPane().add(lblTiposDePartidas);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuarioBorrar = JOptionPane.showInputDialog("¿Que puntuaciones desea suprimir? Escriba el nombre del usuario");			
				BD.puntuacionesTodasDelete(stUsuarios, usuarioBorrar);
				ResultSet rs = BD.puntuacionesSelect(stUsuarios, null);
				try {
					tabla.setModel(buildTableModel(rs)); //Se añade el resultado obtenido en la llamada a funcion previamente realizada
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnEliminar.setBounds(410, 190, 117, 29);
		getContentPane().add(btnEliminar);
		
		JButton btnEliminarLinea = new JButton("Eliminar Linea");
		btnEliminarLinea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tabla.getSelectedRow();
				Object u = tabla.getValueAt(fila, 0);
				Object punto = tabla.getValueAt(fila, 1);
				String usuarioBorrar = u + "";
				int puntos = (Integer) punto;
				BD.puntuacionUnicaDelete(stUsuarios, usuarioBorrar, puntos);
				ResultSet rs = BD.puntuacionesSelect(stUsuarios, null);
				try {
					tabla.setModel(buildTableModel(rs)); //Se añade el resultado obtenido en la llamada a funcion previamente realizada
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnEliminarLinea.setBounds(410, 149, 117, 29);
		getContentPane().add(btnEliminarLinea);
		
	}
	
	//Metodo general para obtener los datos de una base de datos y añadirlos a una tabla
	private static DefaultTableModel buildTableModel(ResultSet rs)
	        throws SQLException {

	    ResultSetMetaData metaData = rs.getMetaData();

	    // Nombre de las columnas:
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // Datos de la tabla:
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames);

	}
}
