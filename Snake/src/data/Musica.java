package data;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Musica {
	// Clase para la gesti�n de la m�sica, exceptuando el �ltimo m�todo todos reproducen sonidos
	private static Clip sonido1;
	private static Clip sonido2;
	private static Clip sonido3;
	private static Clip sonido4;
	private static Clip sonido5;
	private static Clip sonido6;
	private static Clip sonido7;

	//Sonido 1 (fondo):
	public static void musicaFondo(float i) {
		try {
			// Se obtiene un Clip de sonido
			sonido1 = AudioSystem.getClip();

			// Se carga con un fichero wav
			sonido1.open(AudioSystem.getAudioInputStream(new File("src/recursos/Music.wav")));

			// Control de volumen
			FloatControl gainControl = (FloatControl) sonido1.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(i);

			// Comienza la reproducci�n
			sonido1.start();

			// Bucle infinito
			sonido1.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			System.out.println("" + e);
		}
	}

	//Sonido 2 (recompensa):
	public static void sonidoRecompensa(float i) {
		try {
			// Se obtiene un Clip de sonido
			sonido2 = AudioSystem.getClip();

			// Se carga con un fichero wav
			sonido2.open(AudioSystem.getAudioInputStream(new File("src/recursos/Recompensa.wav")));

			// Control de volumen
			FloatControl gainControl = (FloatControl) sonido2.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(i);

			// Comienza la reproduccion
			sonido2.start();

			// Reproduce el fichero una vez
			sonido2.loop(0);

		} catch (Exception e) {
			System.out.println("" + e);
		}
	}

	//Sonido 3 (colision):
	public static void colision(float i) {
		try {
			// Se obtiene un Clip de sonido
			sonido3 = AudioSystem.getClip();

			// Se carga con un fichero wav
			sonido3.open(AudioSystem.getAudioInputStream(new File("src/recursos/Colision.wav")));

			// Control de volumen
			FloatControl gainControl = (FloatControl) sonido3.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(i);

			// Comienza la reproduccion
			sonido3.start();

			// Reproduce el fichero una vez
			sonido3.loop(0);
		} catch (Exception e) {
			System.out.println("" + e);
		}
	}
	
	//Sonido 4 (game over):
	public static void gameOver(float i) {
		try {
			// Se obtiene un Clip de sonido
			sonido4 = AudioSystem.getClip();

			// Se carga con un fichero wav
			sonido4.open(AudioSystem.getAudioInputStream(new File("src/recursos/GameOver.wav")));

			// Control de volumen
			FloatControl gainControl = (FloatControl) sonido4.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(i);

			// Comienza la reproducci�n
			sonido4.start();

			// Reproduce el fichero una vez
			sonido4.loop(0);
		} catch (Exception e) {
			System.out.println("" + e);
		}
	}

	//Sonido 5 (click del boton):
	public static void clickBoton(float i) {
		try {
			// Se obtiene un Clip de sonido
			sonido5 = AudioSystem.getClip();

			// Se carga con un fichero wav
			sonido5.open(AudioSystem.getAudioInputStream(new File("src/recursos/Boton.wav")));

			// Control de volumen
			FloatControl gainControl = (FloatControl) sonido5.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(i);

			// Comienza la reproducci�n
			sonido5.start();

			// Reproduce el fichero una vez
			sonido5.loop(0);
		} catch (Exception e) {
			System.out.println("" + e);
		}
	}
	
	//Sonido 6 (musica de la ventana menu):
	public static void musicaMenu(float i) {
		try {
			// Se obtiene un Clip de sonido
			sonido6 = AudioSystem.getClip();

			// Se carga con un fichero wav
			sonido6.open(AudioSystem.getAudioInputStream(new File("src/recursos/Menu.wav")));

			// Control de volumen
			FloatControl gainControl = (FloatControl) sonido6.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(i);

			// Comienza la reproducci�n
			sonido6.start();

			// Reproduce el fichero una vez
			sonido6.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			System.out.println("" + e);
		}
	}
		
	//Sonido 7 (click):
	public static void click(float i) {
		try {
			// Se obtiene un Clip de sonido
			sonido7 = AudioSystem.getClip();

			// Se carga con un fichero wav
			sonido7.open(AudioSystem.getAudioInputStream(new File("src/recursos/Click.wav")));

			// Control de volumen
			FloatControl gainControl = (FloatControl) sonido7.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(i);

			// Comienza la reproducci�n
			sonido7.start();

			// Reproduce el fichero una vez
			sonido7.loop(0);
		} catch (Exception e) {
			System.out.println("" + e);
		}
	}
		
	// Metodo para la interrupcion de cualquiera de los sonidos:
	public static void stop(int i) {
		switch (i) {
		case 1:
			sonido1.stop(); //Se para el sonido 1
			break;
		case 2:
			sonido2.stop(); //Se para el sonido 2
			break;
		case 3:
			sonido3.stop(); //Se para el sonido 3
			break;
		case 4:
			sonido4.stop(); //Se para el sonido 4
			break;
		case 5:
			sonido5.stop(); //Se para el sonido 5
			break;
		case 6:
			sonido6.stop(); //Se para el sonido 6
			break;
		case 7:
			sonido7.stop(); //Se para el sonido 7
			break;
		default:
			break;
		}
	}
}
