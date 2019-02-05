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
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.ScrollPaneConstants;

public class VentanaMensajes extends JFrame {

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
					VentanaMensajes frame = new VentanaMensajes(args);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		});
	}
	
	public VentanaMensajes(String usuario) {
		setTitle("Mensajes");
		setResizable(false);
		//Conectamos con la base de datos
		conUsuarios = BD.initBD("Usuarios");
		stUsuarios = BD.usarCrearTablasBD(conUsuarios);
		
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setSize( 536, 300 );
		getContentPane().setLayout(null);
		
		//Metemos la tabla en el scrollPane
		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(6, 6, 382, 266);
		getContentPane().add(scrollPane);
		
		//Añadimos las columnas al modelo de la tabla
		modeloTabla.addColumn("Emisor");
		modeloTabla.addColumn("Mensaje");
		
		JButton btnCargarMensajes = new JButton("Cargar Mensajes");
		btnCargarMensajes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ResultSet rs = BD.mensajesSelect(stUsuarios, usuario);
				try {
					tabla.setModel(buildTableModel(rs)); 
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCargarMensajes.setBounds(400, 44, 127, 29);
		getContentPane().add(btnCargarMensajes);
		
		//Creamos el boton regresar para que cuando le demos regresemos a la ventana anterior
		JButton btnRegresar = new JButton("Regresar");
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); //Utilizamos el metodo dispose para cerrar la ventana
			}
		});
		btnRegresar.setBounds(400, 231, 127, 29);
		getContentPane().add(btnRegresar);
		
		JButton btnEliminarLinea = new JButton("Eliminar Linea");
		btnEliminarLinea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tabla.getSelectedRow();
				Object u = tabla.getValueAt(fila, 0);
				Object mensaje = tabla.getValueAt(fila, 1);
				String usuarioBorrar = u + "";
				String mensajeBorrar = mensaje + "";
				BD.mensajeUnicoDelete(stUsuarios, usuarioBorrar, mensajeBorrar);
				ResultSet rs = BD.mensajesSelect(stUsuarios, usuario);
				try {
					tabla.setModel(buildTableModel(rs)); 
					} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnEliminarLinea.setBounds(400, 190, 127, 29);
		getContentPane().add(btnEliminarLinea);	
		
		JButton btnNewButton = new JButton("Responder");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet rs = BD.mensajesSelect(stUsuarios, usuario);
				int fila = tabla.getSelectedRow();
				Object u = tabla.getValueAt(fila, 0);
				String usuarioResponder = u + "";
				String mensaje = JOptionPane.showInputDialog(null, "Escriba su mensaje aqui:");
				if (!mensaje.isEmpty()) {
					if (!usuarioResponder.isEmpty()) {
						int existeUsuarioResponder = BD.usuarioExisteSelect(stUsuarios, usuarioResponder);
						if (existeUsuarioResponder == 1) {
							BD.mensajesInsert(stUsuarios, usuarioResponder, mensaje, usuario);
						} else {
							JOptionPane.showMessageDialog(null, "El usuario introducido no existe");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Destinatario vacio");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Mensaje vacio");
				}
			}
		});
		btnNewButton.setBounds(400, 85, 127, 29);
		getContentPane().add(btnNewButton);
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
