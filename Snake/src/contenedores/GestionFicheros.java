package contenedores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GestionFicheros {
	/**
	 * Este m�todo va a comprobar si el usuario est� registrado en el fichero y si
	 * es as�, comprobar� si su contrase�a es correcta
	 * @param nom : nombre del usuario
	 * @param con : contrase�a del usuario
	 * @return Devuelve: 0 si el usuario no est� registrado
	 * 					 1 si el usuario s� est� registrado pero la contrase�a no es correcta
	 * 					 2 si el usuario s� est� registrado y la contrase�a es correcta
	 */
	public static int usuarioRegistrado(String nom, String con) {
		/*El formato del fichero de texto es:
		 * nom1 con1
		 * nom2 con2*/
		File f = new File("Usuarios.txt");
		int resul = 0;
		if (!f.exists()) {
			resul = 0;
		} else {
			try {
				boolean encontrado = false;
				BufferedReader br = new BufferedReader(new FileReader(f));
				/* Hemos abierto el fichero Usuarios.txt en modo lectura */
				String linea = br.readLine();
				while (!encontrado && linea != null) {
					// La l�nea tiene este formato: Marian 1234
					int posEspacio = linea.indexOf(" ");
					String n = linea.substring(0, posEspacio);
					String c = linea.substring(posEspacio + 1);
					if (nom.equalsIgnoreCase(n)) {
						encontrado = true;
						if (con.equalsIgnoreCase(c)) {
							resul = 2;
						} else {
							resul = 1;
						}
					} else {
						linea = br.readLine();
					}
				}
				if (!encontrado)
					resul = 0;
				br.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return resul;

	}

	public static void registrarUsuario(String nom, String con) {
		File f = new File("Usuarios.txt");
		PrintWriter pw = null;
		if (f.exists())
			try {
				pw = new PrintWriter(new FileWriter(f, true));
				/* Abrimos el fichero para a�adir al final un nuevo usuario */
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			try {
				pw = new PrintWriter(f);
				/* Como no existe el fichero, creamos un documento en blanco */
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		pw.println(nom + " " + con);
		pw.flush();
		pw.close();

	}

}
