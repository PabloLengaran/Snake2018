package baseDeDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.*;


import data.Usuario;

/** Clase de gestion de base de datos del sistema de analiticas
 * @author Pablo_Gaviria
 */
public class BD {

	private static Exception lastError = null;  // Informacion de ultimo error SQL ocurrido
	private static final String TABLA_USUARIO = "Usuarios";
	private static final String COLUMNAS_TABLA_USUARIO = "(nombre string PRIMARY KEY, contrasenia string, creditos integer)";
	private static final String TABLA_PUNTUACION = "Puntuaciones";
	private static final String COLUMNAS_TABLA_PUNTUACIONES = "(nombre string, puntuaciones integer)";
	private static final String TABLA_ADMINISTRADOR = "Administradores";
	private static final String COLUMNAS_TABLA_ADMINISTRADORES = "(nombre string, admin integer)";
	private static final String TABLA_FONDOS = "Fondos";
	private static final String COLUMNAS_TABLA_FONDOS = "(nombre string, fondo1 integer, fondo2 integer, fondo3 integer, fondo4 integer)";
	private static final String TABLA_PARTIDAS = "Partidas";
	private static final String COLUMNAS_TABLA_PARTIDAS = "(nombre string, partidasTerminadas integer, partidasAbandonadas integer)";
	private static final String TABLA_SERPIENTES = "Serpientes";
	private static final String COLUMNAS_TABLA_SERPIENTES = "(nombre string, serpiente1 integer, serpiente2 integer, serpiente3 integer, serpiente4 integer)";
	private static final String TABLA_MENSAJES = "Mensajes";
	private static final String COLUMNAS_TABLA_MENSAJES = "(nombre string, mensaje string, emisor string)";
	
	
	/** Inicializa una BD SQLITE y devuelve una conexion con ella
	 * @param nombreBD	Nombre de fichero de la base de datos
	 * @return	Conexion con la base de datos indicada. Si hay algun error, se devuelve null
	 */
	public static Connection initBD( String nombreBD ) {
		try {
		    Class.forName("org.sqlite.JDBC");
		    Connection con = DriverManager.getConnection("jdbc:sqlite:" + nombreBD );
			log( Level.INFO, "Conectada base de datos " + nombreBD, null );
		    return con;
		} catch (ClassNotFoundException | SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en conexi�n de base de datos " + nombreBD, e );
			e.printStackTrace();
			return null;
		}
	}
	
	/** Devuelve statement para usar la base de datos
	 * @param con	Conexion ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se crea correctamente, null si hay cualquier error
	 */
	public static Statement usarBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  //Poner timeout 30 msg
			return statement;
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en uso de base de datos", e );
			e.printStackTrace();
			return null;
		}
	}
	
	/** Crea las tablas de la base de datos. Si ya existen, las deja tal cual
	 * @param con	Conexion ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se crea correctamente, null si hay cualquier error
	 */
	public static Statement usarCrearTablasBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  //Poner timeout 30 msg
			try {
				statement.executeUpdate("create table " + TABLA_USUARIO + COLUMNAS_TABLA_USUARIO);
				statement.executeUpdate("create table " + TABLA_PUNTUACION + COLUMNAS_TABLA_PUNTUACIONES);
				statement.executeUpdate("create table " + TABLA_ADMINISTRADOR + COLUMNAS_TABLA_ADMINISTRADORES);
				statement.executeUpdate("create table " + TABLA_FONDOS + COLUMNAS_TABLA_FONDOS);
				statement.executeUpdate("create table " + TABLA_PARTIDAS + COLUMNAS_TABLA_PARTIDAS);
				statement.executeUpdate("create table " + TABLA_SERPIENTES + COLUMNAS_TABLA_SERPIENTES);
				statement.executeUpdate("create table " + TABLA_MENSAJES + COLUMNAS_TABLA_MENSAJES);
			} catch (SQLException e) {} //Tabla ya existe. Nada que hacer
			log( Level.INFO, "Creada base de datos", null );
			return statement;
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en creaci�n de base de datos", e );
			e.printStackTrace();
			return null;
		}
	}
	
	/** Reinicia en blanco las tablas de la base de datos. 
	 * UTILIZAR ESTE METODO CON PRECAUCION. Borra todos los datos que hubiera ya en las tablas
	 * @param con	Conexion ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se borra correctamente, null si hay cualquier error
	 */
	public static Statement reiniciarBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  //Poner timeout 30 msg
			statement.executeUpdate("drop table if exists " + TABLA_USUARIO);
			statement.executeUpdate("drop table if exists " + TABLA_PUNTUACION);
			statement.executeUpdate("drop table if exists " + TABLA_ADMINISTRADOR);
			statement.executeUpdate("drop table if exists " + TABLA_FONDOS);
			statement.executeUpdate("drop table if exists " + TABLA_PARTIDAS);
			statement.executeUpdate("drop table if exists " + TABLA_SERPIENTES);
			statement.executeUpdate("drop table if exists " + TABLA_MENSAJES);
			log( Level.INFO, "Reiniciada base de datos", null );
			return usarCrearTablasBD( con );
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en reinicio de base de datos", e );
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	/** Cierra la base de datos abierta
	 * @param con	Conexion abierta de la BD
	 * @param st	Sentencia abierta de la BD
	 */
	public static void cerrarBD( Connection con, Statement st ) {
		try {
			if (st!=null) st.close();
			if (con!=null) con.close();
			log( Level.INFO, "Cierre de base de datos", null );
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en cierre de base de datos", e );
			e.printStackTrace();
		}
	}
	
	/** Devuelve la informaci�n de excepci�n del �ltimo error producido por cualquiera 
	 * de los m�todos de gesti�n de base de datos
	 */
	public static Exception getLastError() {
		return lastError;
	}
	
	
	
//METODOS INSERT:	
	
	//Tabla USUARIOS:
	public static boolean usuariosInsert( Statement st, String nombre, String contrasenia, int creditos) {
		String sentSQL = "";
		try {
			sentSQL = "insert into usuarios values ('" + secu(nombre) + "', '" + contrasenia + "', " + creditos +  ")";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD fila a�adida " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que anyadir 1 - error si no
				log( Level.SEVERE, "Error en insert de BD\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	//Tabla PUNTUACIONES:
	public static boolean puntuacionesInsert( Statement st, String nombre, int puntuacion ) {
		String sentSQL = "";
		try {
			sentSQL = "insert into "+ TABLA_PUNTUACION +" values ('" + secu(nombre) + "', " + puntuacion + ")";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD fila a�adida " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que anyadir 1 - error si no
				log( Level.SEVERE, "Error en insert de BD\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	//Tabla ADMINISTRADORES:
	public static boolean administradoresInsert( Statement st, String nombre, int admin) {
		String sentSQL = "";
		try {
			sentSQL = "insert into administradores values ('" + secu(nombre) + "', " +  admin + ")";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD fila a�adida " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que anyadir 1 - error si no
				log( Level.SEVERE, "Error en insert de BD\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	//Tabla FONDOS:
	public static boolean fondosInsert( Statement st, String nombre, int fondo1, int fondo2, int fondo3, int fondo4) {
		String sentSQL = "";
		try {
			sentSQL = "insert into fondos values ('" + secu(nombre) + "', " +  fondo1 + ", "+fondo2 + ", " + fondo3 +  ", " + fondo4 + ")";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD fila a�adida " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que anyadir 1 - error si no
				log( Level.SEVERE, "Error en insert de BD\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	//Tabla PARTIDAS:
	public static boolean partidasInsert( Statement st, String nombre, int terminadas, int abandonadas) {
		String sentSQL = "";
		try {
			sentSQL = "insert into partidas values ('" + secu(nombre) + "', " +  terminadas + ", " + abandonadas + ")";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD fila a�adida " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que anyadir 1 - error si no
				log( Level.SEVERE, "Error en insert de BD\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}	
	
	//Tabla SERPIENTES:
	public static boolean serpientesInsert( Statement st, String nombre, int serpiente1, int serpiente2, int serpiente3, int serpiente4) {
		String sentSQL = "";
		try {
			sentSQL = "insert into serpientes values ('" + secu(nombre) + "', " +  serpiente1 + ", " + serpiente2 + ", " + serpiente3 + ", " + serpiente4 + ")";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD fila a�adida " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que anyadir 1 - error si no
				log( Level.SEVERE, "Error en insert de BD\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	//TABLA MENSAJES: 
	public static boolean mensajesInsert( Statement st, String nombre,String mensaje, String emisor) {
		String sentSQL = "";
		try {
			sentSQL = "insert into mensajes values ('" + secu(nombre) + "', '"+ mensaje + "', '" + emisor +"')";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD fila a�adida " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que anyadir 1 - error si no
				log( Level.SEVERE, "Error en insert de BD\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	
//METODOS SELECT:
	
	//Tabla USUARIOS CONTRASENYA:
	public static Usuario usuarioSelect (Statement st, String txtNombreUsuario) {
		String sentSQL = "";
		Usuario user = null;
		try {
			sentSQL = "select * from " + TABLA_USUARIO + " where nombre='" + txtNombreUsuario + "'";
			ResultSet rs = st.executeQuery( sentSQL );
			if (rs.next()) {
				String nombre = rs.getString( "nombre" );
				String pass = rs.getString( "contrasenia" );
				ArrayList<Integer> list = new ArrayList<Integer>();
				user =  new Usuario(nombre, pass, list);
			}
			rs.close();
			log( Level.INFO, "BD\t" + sentSQL, null);
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
		}
		return user;
	}
	
	//Tabla PUNTUACIONES:
	public static ResultSet puntuacionesSelect (Statement st, String nombre) {
		String sentSQL = "";
		ResultSet rs = null;
		try {
			if(nombre == null || nombre.length() == 0) {
				sentSQL = "select distinct nombre, puntuaciones from " + TABLA_PUNTUACION + " ORDER BY puntuaciones DESC";	//Cogemos todos los usuarios
			}else {
				sentSQL = "select distinct nombre, puntuaciones from " + TABLA_PUNTUACION + " where nombre= '" + nombre + "' ORDER BY puntuaciones DESC";	//Cogemos un solo usuario
			}
			rs = st.executeQuery(sentSQL);
			log( Level.INFO, "BD\t" + sentSQL, null);
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
		}
		return rs;
	}
	
	//Tabla CREDITOS: 
	public static int creditosSelect (Statement st, String nombre) {
		String sentSQL = "";
		int creditos = 0;
		try {
			sentSQL = "select creditos from " + TABLA_USUARIO + " where nombre= '" + nombre + "'";
			ResultSet rst = st.executeQuery (sentSQL );
			while (rst.next()) {
				creditos = rst.getInt("creditos");
			}
			rst.close();
			log(Level.INFO, "BD\t " +sentSQL, null);
			return creditos;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return 0;
		}		
	}
	
	//Tabla ADMINISTRADORES:
	public static int administradoresSelect (Statement st, String nombre) {
		String sentSQL = "";
		int admin = 0;
		try {
			sentSQL = "select admin from " + TABLA_ADMINISTRADOR + " where nombre= '" + nombre + "'";
			ResultSet rst = st.executeQuery (sentSQL );
			while (rst.next()) {
				admin = rst.getInt("admin");
			}
			rst.close();
			log(Level.INFO, "BD\t " +sentSQL, null);
			return admin;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return 0;
		}		
	}
	
	//Tabla FONDOS:
	public static int fondosSelect (Statement st, String nombre, int fondoSeleccionado) {
		String sentSQL = "";
		int fondo = 0;
		try {
			if (fondoSeleccionado == 1) {
				sentSQL = "select fondo1 from " + TABLA_FONDOS + " where nombre= '" + nombre + "'";
			} else if (fondoSeleccionado == 2){
				sentSQL = "select fondo2 from " + TABLA_FONDOS + " where nombre= '" + nombre + "'";
			} else if (fondoSeleccionado == 3){
				sentSQL = "select fondo3 from " + TABLA_FONDOS + " where nombre= '" + nombre + "'";
			} else if (fondoSeleccionado == 4){
				sentSQL = "select fondo4 from " + TABLA_FONDOS + " where nombre= '" + nombre + "'";
			}
			ResultSet rst = st.executeQuery (sentSQL );
			while (rst.next()) {
				if (fondoSeleccionado == 1) {
					fondo = rst.getInt("fondo1");
				} else if (fondoSeleccionado == 2) {
					fondo = rst.getInt("fondo2");
				}else if (fondoSeleccionado == 3) {
					fondo = rst.getInt("fondo3");
				}else if (fondoSeleccionado == 4) {
					fondo = rst.getInt("fondo4");
				}
			}
			rst.close();
			log(Level.INFO, "BD\t " +sentSQL, null);
			return fondo;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return 0;
		}
	}
	
	//Tabla PARTIDAS:
	public static int partidasSelect (Statement st, String nombre, int tipoPartida) {
		String sentSQL = "";
		int partidas = 0;
		try {
			if (tipoPartida == 0) {
				sentSQL = "select partidasTerminadas from " + TABLA_PARTIDAS + " where nombre= '" + nombre + "'";
			} else if (tipoPartida == 1){
				sentSQL = "select partidasAbandonadas from " + TABLA_PARTIDAS + " where nombre= '" + nombre + "'";
			}			
			ResultSet rst = st.executeQuery (sentSQL );
			while (rst.next()) {
				if (tipoPartida == 0) {
					partidas = rst.getInt("partidasTerminadas");
				} else if (tipoPartida == 1) {
					partidas = rst.getInt("partidasAbandonadas");
				}
			}
			rst.close();
			log(Level.INFO, "BD\t " +sentSQL, null);
			return partidas;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return 0;
		}		
	}
	
	//Tabla SERPIENTES:
	public static int serpientesSelect (Statement st, String nombre, int numeroSerpiente) {
		String sentSQL = "";
		int serpiente = 0;
		try {
			if (numeroSerpiente == 0) {
				sentSQL = "select serpiente1 from " + TABLA_SERPIENTES + " where nombre= '" + nombre + "'";
			} else if (numeroSerpiente == 1){
				sentSQL = "select serpiente2 from " + TABLA_SERPIENTES + " where nombre= '" + nombre + "'";
			} else if (numeroSerpiente == 2){
				sentSQL = "select serpiente3 from " + TABLA_SERPIENTES + " where nombre= '" + nombre + "'";
			} else if (numeroSerpiente == 3){
				sentSQL = "select serpiente4 from " + TABLA_SERPIENTES + " where nombre= '" + nombre + "'";
			}
			ResultSet rst = st.executeQuery (sentSQL );
			while (rst.next()) {
				if (numeroSerpiente == 0) {
					serpiente = rst.getInt("serpiente1");
				} else if (numeroSerpiente == 1) {
					serpiente = rst.getInt("serpiente2");
				} else if (numeroSerpiente == 2) {
					serpiente = rst.getInt("serpiente3");
				} else if (numeroSerpiente == 3) {
					serpiente = rst.getInt("serpiente4");
				}
			}
			rst.close();
			log(Level.INFO, "BD\t " +sentSQL, null);
			return serpiente;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return 0;
		}
	}
	
	//Tabla USUARIOS EXISTE:
	public static int usuarioExisteSelect (Statement st, String nombre) {
		String sentSQL = "";
		String Usuario = "";
		int devolucion = 0;
		try {
				sentSQL = "select nombre from " + TABLA_USUARIO + " where nombre= '"+ nombre + "'";
				ResultSet rst = st.executeQuery (sentSQL );
			while (rst.next()) {
				Usuario = rst.getString("nombre");
				if (Usuario.equals(nombre)) {
					devolucion = 1;
				}
			}
			rst.close();
			log(Level.INFO, "BD\t " +sentSQL, null);
			return devolucion;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return 0;
		}
	}
	
	//Tabla MENSAJES:
	public static ResultSet mensajesSelect (Statement st, String nombre) {
		String sentSQL = "";
		ResultSet rs = null;
		try {
			sentSQL = "select distinct emisor, mensaje from " + TABLA_MENSAJES + " where nombre= '" + nombre + "'";
			rs = st.executeQuery(sentSQL);
			log( Level.INFO, "BD\t" + sentSQL, null);
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
		}
		return rs;
	}	
	
//METODOS UPDATE:	
	
	//Tabla USUARIOS CONTRASENYA:
	public static boolean usuariosUpdate( Statement st, String nombre, String contrasenia ) {
		String sentSQL = "";
		try {
			sentSQL = "update usuarios set nombre=" + nombre + " where contrasenia='" + contrasenia + "'";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD modificada " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que modificar 1 - error si no
				log( Level.SEVERE, "Error en update de BD\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	//Tabla PUNTUACIONES:
	public static boolean puntuacionesUpdate( Statement st, String nombre, int puntuacion ) {
		String sentSQL = "";
		try {
			sentSQL = "update puntuaciones set contador=" + secu(nombre) + " where codigo='" + puntuacion + "'";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD modificada " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que modificar 1 - error si no
				log( Level.SEVERE, "Error en update de BD\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	//Tabla CREDITOS:
	public static boolean creditosUpdate( Statement st, String nombre, int creditos) {
		String sentSQL = "";
		try {
			sentSQL = "update " + TABLA_USUARIO + " set creditos= " + creditos + " where nombre= '" + nombre + "'" ;
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD modificada " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que modificar 1 - error si no
				log( Level.SEVERE, "Error en update de BD\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	//Tabla ADMINISTRADORES
	public static boolean administradorUpdate( Statement st, String nombre, int admin) {
		String sentSQL = "";
		try {
			sentSQL = "update " + TABLA_ADMINISTRADOR + " set admin= " + admin + " where nombre= '" + nombre + "'" ;
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD modificada " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que modificar 1 - error si no
				log( Level.SEVERE, "Error en update de BD\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	//Tabla FONDOS: 
	public static boolean fondosUpdate( Statement st, String nombre, int fondoSeleccionado, int fondo) {
		String sentSQL = "";
		try {
			if (fondoSeleccionado == 1) {
				sentSQL = "update " + TABLA_FONDOS + " set fondo1= " + fondo + " where nombre= '" + nombre + "'" ;
			} else if (fondoSeleccionado == 2){
				sentSQL = "update " + TABLA_FONDOS + " set fondo2= " + fondo + " where nombre= '" + nombre + "'" ;
			} else if (fondoSeleccionado == 3){
				sentSQL = "update " + TABLA_FONDOS + " set fondo3= " + fondo + " where nombre= '" + nombre + "'" ;
			} else if (fondoSeleccionado == 4){
				sentSQL = "update " + TABLA_FONDOS + " set fondo4= " + fondo + " where nombre= '" + nombre + "'" ;
			}
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD modificada " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que modificar 1 - error si no
				log( Level.SEVERE, "Error en update de BD\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	//Tabla PARTIDAS:
	public static boolean partidasUpdate( Statement st, String nombre, int partidas, int tipoPartidas) {
		String sentSQL = "";
		try {
			if (tipoPartidas == 0) {
				sentSQL = "update " + TABLA_PARTIDAS + " set partidasTerminadas= " + partidas + " where nombre= '" + nombre + "'" ;
			} else if (tipoPartidas == 1) {
				sentSQL = "update " + TABLA_PARTIDAS + " set partidasAbandonadas= " + partidas + " where nombre= '" + nombre + "'" ;
			}
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD modificada " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que modificar 1 - error si no
				log( Level.SEVERE, "Error en update de BD\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	//Tabla SERPIENTES: 
	public static boolean serpientesUpdate( Statement st, String nombre, int serpiente, int tipoSerpiente) {
		String sentSQL = "";
		try {
			if (tipoSerpiente == 0) {
				sentSQL = "update serpientes set serpiente1= " + serpiente + " where nombre= '" + nombre + "'";
			} else if (tipoSerpiente == 1) {
				sentSQL = "update serpientes set serpiente2= " + serpiente + " where nombre= '" + nombre + "'";
			} else if (tipoSerpiente == 2) {
				sentSQL = "update serpientes set serpiente3= " + serpiente + " where nombre= '" + nombre + "'";
			} else if (tipoSerpiente == 3) {
				sentSQL = "update serpientes set serpiente4= " + serpiente + " where nombre= '" + nombre + "'";
			}
			
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD modificada " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que modificar 1 - error si no
				log( Level.SEVERE, "Error en update de BD\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
//METODOS DELETE:

	//Tabla USUARIOS CONTRASENYA:
	public static boolean usuariosDelete( Statement st, String nombre ) {
		String sentSQL = "";
		try {
			sentSQL = "delete from usuarios where codigo= '" + secu(nombre) + "'";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD borrada " + val + " fila\t" + sentSQL, null );
			return (val==1);
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	//Tabla PUNTUACIONES UNICA:
	public static boolean puntuacionUnicaDelete (Statement st, String usuario, int numero) {
		String sentSQL = "";
		try {
			sentSQL = "delete from puntuaciones where nombre= '" + usuario + "' AND puntuaciones= " + numero;
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD borrada " + val + " fila\t" + sentSQL, null );
			return (val==1);
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	//Tabla PUNTUACIONES TODAS:
	public static boolean puntuacionesTodasDelete (Statement st, String nombre) {
		String sentSQL = "";
		try {
			sentSQL = "delete from puntuaciones where nombre= '" + nombre + "'" ;
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD borrada " + val + " fila\t" + sentSQL, null );
			return (val==1);
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}

	//Tabla CREDITOS:
	public static boolean creditosDelete (Statement st, String nombre) {
		String sentSQL = "";
		try {
			sentSQL = "delete from usuarios where nombre= '" + secu(nombre) + "'";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD borrada " + val + " fila\t" + sentSQL, null );
			return (val==1);
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
		
	//Tabla MENSAJES TODOS:
	public static boolean mensajeTodosDelete (Statement st, String nombre) {
		String sentSQL = "";
		try {
			sentSQL = "delete from mensajes where nombre= '" + secu(nombre) + "'";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD borrada " + val + " fila\t" + sentSQL, null );
			return (val==1);
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	//Tabla MENSAJES UNICO:
	public static boolean mensajeUnicoDelete (Statement st, String emisor, String mensaje) {
		String sentSQL = "";
		try {
			sentSQL = "delete from mensajes where emisor= '" + secu(emisor) + "' AND mensaje= '" + mensaje + "'";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD borrada " + val + " fila\t" + sentSQL, null );
			return (val==1);
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	/////////////////////////////////////////////////////////////////////
	//                      Metodos privados                           //
	/////////////////////////////////////////////////////////////////////

	// Devuelve el string "securizado" para volcarlo en SQL
	// (Implementacion 1) Sustituye ' por '' y quita saltos de l�nea
	// (Implementacion 2) Mantiene solo los caracteres seguros en espa�ol
	private static String secu( String string ) {
		// Implementacion (1)
		// return string.replaceAll( "'",  "''" ).replaceAll( "\\n", "" );
		// Implementacion (2)
		StringBuffer ret = new StringBuffer();
		for (char c : string.toCharArray()) {
			if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ��������������.,:;-_(){}[]-+*=<>'\"�?�!&%$@#/\\0123456789".indexOf(c)>=0) ret.append(c);
		}
		return ret.toString();
	}
	

	/////////////////////////////////////////////////////////////////////
	//                      Logging                                    //
	/////////////////////////////////////////////////////////////////////
	
	private static Logger logger = null;
	// Metodo publico para asignar un logger externo
	/** Asigna un logger ya creado para que se haga log de las operaciones de base de datos
	 * @param logger	Logger ya creado
	 */
	public static void setLogger( Logger logger ) {
		BD.logger = logger;
	}
	// Metodo local para loggear (si no se asigna un logger externo, se asigna uno local)
	private static void log( Level level, String msg, Throwable excepcion ) {
		if (logger==null) {  // Logger por defecto local:
			logger = Logger.getLogger( BD.class.getName() );  // Nombre del logger - el de la clase
			logger.setLevel( Level.ALL );  // Loguea todos los niveles
			try {
				// logger.addHandler( new FileHandler( "bd-" + System.currentTimeMillis() + ".log.xml" ) );  // Y saca el log a fichero xml
				logger.addHandler( new FileHandler( "bd.log.xml", true ) );  // Y saca el log a fichero xml
			} catch (Exception e) {
				logger.log( Level.SEVERE, "No se pudo crear fichero de log", e );
			}
		}
		if (excepcion==null)
			logger.log( level, msg );
		else
			logger.log( level, msg, excepcion );
	}
}
