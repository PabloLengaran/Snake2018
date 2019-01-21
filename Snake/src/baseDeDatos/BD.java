package baseDeDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.*;


import data.Usuario;

/** Clase de gesti�n de base de datos del sistema de analiticas
 * @author Pablo_Gaviria
 */
public class BD {

	private static Exception lastError = null;  // Informacion de ultimo error SQL ocurrido
	private static final String TABLA_USUARIO = "Usuarios";
	private static final String COLUMNAS_TABLA_USUARIO = "(nombre string PRIMARY KEY, contrasenia string, creditos integer, serpiente integer)";
	private static final String TABLA_PUNTUACION = "Puntuaciones";
	private static final String COLUMNAS_TABLA_PUNTUACIONES = "(nombre string, puntuaciones integer) ";
	
	
	
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
	 * @param con	Conexi�n ya creada y abierta a la base de datos
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
	 * @param con	Conexi�n ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se crea correctamente, null si hay cualquier error
	 */
	public static Statement usarCrearTablasBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  //Poner timeout 30 msg
			try {
				statement.executeUpdate("create table " + TABLA_USUARIO + COLUMNAS_TABLA_USUARIO);
				statement.executeUpdate("create table " + TABLA_PUNTUACION + COLUMNAS_TABLA_PUNTUACIONES);
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
	 * UTILIZAR ESTE M�TODO CON PRECAUCI�N. Borra todos los datos que hubiera ya en las tablas
	 * @param con	Conexi�n ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se borra correctamente, null si hay cualquier error
	 */
	public static Statement reiniciarBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  //Poner timeout 30 msg
			statement.executeUpdate("drop table if exists " + TABLA_USUARIO);
			statement.executeUpdate("drop table if exists " + TABLA_PUNTUACION);
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
	 * @param con	Conexi�n abierta de la BD
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
	
	
	
	/** A�ade un registro a la tabla abierta de BD, usando la sentencia INSERT de SQL
	 * @param st	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente)
	 * @param codigo	C�digo a a�adir a la BD (en nueva fila)
	 * @param contador	contador a a�adir a esa nueva fila de la BD
	 * @return	true si la inserci�n es correcta, false en caso contrario
	 */
	
	//USUARIOS:
	public static boolean usuariosInsert( Statement st, String nombre, String contrasenia, int creditos, int serpiente) {
		String sentSQL = "";
		try {
			sentSQL = "insert into usuarios values ('" + secu(nombre) + "', '" + contrasenia + "', " + creditos +  ", " + serpiente + ")";
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
	
	
	
	//PUNTUACIONES:
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
	

	/** Realiza una consulta a la tabla abierta de la BD, usando la sentencia SELECT de SQL
	 * @param st	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente a la analitica)
	 * @param codigo	C�digo a buscar en la tabla
	 * @return	contador cargado desde la base de datos para ese c�digo, Integer.MAX_VALUE si hay cualquier error
	 */
	
	  
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
	
	public static int serpienteSelect (Statement st, String nombre) {
		String sentSQL = "";
		int serpiente = 0;
		try {
			sentSQL = "select serpiente from " + TABLA_USUARIO + " where nombre= '" + nombre + "'";
			ResultSet rst = st.executeQuery (sentSQL );
			while (rst.next()) {
				serpiente = rst.getInt("serpiente");
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
	

	/** Modifica una anal�tica en la tabla abierta de BD, usando la sentencia UPDATE de SQL
	 * @param st	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente a la anal�tica)
	 * @param codigo	C�digo a modificar en la base de datos
	 * @param contador	Contador a modificar de ese c�digo
	 * @return	true si la inserci�n es correcta, false en caso contrario
	 */
	
	//Tabla USUARIOS:
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
	
	public static boolean serpienteUpdate( Statement st, String nombre, int serpiente) {
		String sentSQL = "";
		try {
			sentSQL = "update " + TABLA_USUARIO + " set serpiente= " + serpiente + " where nombre= '" + nombre + "'" ;
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

	/** Borrar una anal�tica de la tabla abierta de BD, usando la sentencia DELETE de SQL
	 * @param st	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente a la anal�tica)
	 * @param codigo	C�digo de anal�tica a borrar de la base de datos
	 * @return	true si el borrado es correcto, false en caso contrario
	 */

	//Tabla USUARIOS:
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
	
	//Tabla PUNTUACIONES:
	public static boolean puntuacionesDelete (Statement st, String nombre) {
		String sentSQL = "";
		try {
			sentSQL = "delete from puntuaciones where nombre= '" + nombre + "'";
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
