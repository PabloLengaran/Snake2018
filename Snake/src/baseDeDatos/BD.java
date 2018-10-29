package baseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BD {

	private Connection con;
	private static Statement stmt;

	/**
	 * Metodo que crea una sentencia para acceder a la base de datos
	 */
	public void crearSentencia() {
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Metodo que permite conectarse a la base de datos
	 */

	public void conectar() {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos.db");
			crearSentencia();
		} catch (Exception e) {
			System.out.println("No se ha podido conectar a la base de datos");
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que cierra una sentencia
	 */
	public void cerrarSentencia() {
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que permite desconectarse de la base de datos
	 */
	public void desconectar() {
		try {
			cerrarSentencia();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BD() {
		conectar();
	}

	/* A partir de aquí hacemos las consultas específicas de cada proyecto */

	/**
	 * 
	 * @param nom: Nombre introducido por el usuario
	 * @param con: Contraseña introducida por el usuario
	 * @return : 
	 * 			0 - Si no existe el usuario
	 * 			1 - Si sí existe el usuario pero la contraseña no es correcta
	 * 			2 - Si el nombre de usuario es correcto y la contraseña también
	 */
	public int existeUsuario(String nom, String con) {

		String query = "SELECT * FROM Usuario WHERE nombre='" + nom + "'";
		ResultSet rs = null;
		int resul = 0;
		try {
			rs = stmt.executeQuery(query);
			if (rs.next()) { // Aquí estamos comprobando si la SELECT ha
				// devuelto alguna fila
				String n = rs.getString("nombre");
				String c = rs.getString("contrasenia");
				if (!n.equals(nom))
					resul = 0;
				else if (!c.equals(con))
					resul = 1;
				else
					resul = 2;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resul;
	}

	public void registrarUsuario(String nom, String con) {
		String query = "INSERT INTO Usuario(nombre,contrasenia) VALUES('" + nom + "','" + con + "')";
		// No podemos REsultSet pq una INSERT no devuelve filas, solo inserta en
		// la tabla
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
	}

}
