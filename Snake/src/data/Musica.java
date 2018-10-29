package data;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Musica {
	// Clase para la gestión de la música, exceptuando el último método todos reproducen sonidos
	private static Clip sonido1;
	private static Clip sonido2;
	private static Clip sonido3;
	private static Clip sonido4;
	private static Clip sonido5;
	private static Clip sonido6;
	private static Clip sonido7;

	public static void musicaFondo(float i) {
		try {
			// Se obtiene un Clip de sonido
			sonido1 = AudioSystem.getClip();

			// Se carga con un fichero wav
			sonido1.open(AudioSystem.getAudioInputStream(new File("src/recursos/Music.wav")));

			// Control de volumen
			FloatControl gainControl = (FloatControl) sonido1.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(i);

			// Comienza la reproducción
			sonido1.start();

			// Bucle infinito
			sonido1.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			System.out.println("" + e);
		}
	}

	public static void sonidoRecompensa(float i) {
		try {
			// Se obtiene un Clip de sonido
			sonido2 = AudioSystem.getClip();

			// Se carga con un fichero wav
			sonido2.open(AudioSystem.getAudioInputStream(new File("src/recursos/Recompensa.wav")));

			// Control de volumen
			FloatControl gainControl = (FloatControl) sonido2.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(i);

			// Comienza la reproducción
			sonido2.start();

			// Reproduce el fichero una vez
			sonido2.loop(0);

		} catch (Exception e) {
			System.out.println("" + e);
		}
	}

	public static void colision(float i) {
		try {
			// Se obtiene un Clip de sonido
			sonido3 = AudioSystem.getClip();

			// Se carga con un fichero wav
			sonido3.open(AudioSystem.getAudioInputStream(new File("src/recursos/Colision.wav")));

			// Control de volumen
			FloatControl gainControl = (FloatControl) sonido3.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(i);

			// Comienza la reproducción
			sonido3.start();

			// Reproduce el fichero una vez
			sonido3.loop(0);
		} catch (Exception e) {
			System.out.println("" + e);
		}
	}

	public static void gameOver(float i) {
		try {
			// Se obtiene un Clip de sonido
			sonido4 = AudioSystem.getClip();

			// Se carga con un fichero wav
			sonido4.open(AudioSystem.getAudioInputStream(new File("src/recursos/GameOver.wav")));

			// Control de volumen
			FloatControl gainControl = (FloatControl) sonido4.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(i);

			// Comienza la reproducción
			sonido4.start();

			// Reproduce el fichero una vez
			sonido4.loop(0);
		} catch (Exception e) {
			System.out.println("" + e);
		}
	}

	public static void clickBoton(float i) {
		try {
			// Se obtiene un Clip de sonido
			sonido5 = AudioSystem.getClip();

			// Se carga con un fichero wav
			sonido5.open(AudioSystem.getAudioInputStream(new File("src/recursos/Boton.wav")));

			// Control de volumen
			FloatControl gainControl = (FloatControl) sonido5.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(i);

			// Comienza la reproducción
			sonido5.start();

			// Reproduce el fichero una vez
			sonido5.loop(0);
		} catch (Exception e) {
			System.out.println("" + e);
		}
	}

	public static void musicaMenu(float i) {
		try {
			// Se obtiene un Clip de sonido
			sonido6 = AudioSystem.getClip();

			// Se carga con un fichero wav
			sonido6.open(AudioSystem.getAudioInputStream(new File("src/recursos/Menu.wav")));

			// Control de volumen
			FloatControl gainControl = (FloatControl) sonido6.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(i);

			// Comienza la reproducción
			sonido6.start();

			// Reproduce el fichero una vez
			sonido6.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			System.out.println("" + e);
		}
	}
		
		public static void click(float i) {
			try {
				// Se obtiene un Clip de sonido
				sonido7 = AudioSystem.getClip();

				// Se carga con un fichero wav
				sonido7.open(AudioSystem.getAudioInputStream(new File("src/recursos/Click.wav")));

				// Control de volumen
				FloatControl gainControl = (FloatControl) sonido7.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(i);

				// Comienza la reproducción
				sonido7.start();

				// Reproduce el fichero una vez
				sonido7.loop(0);
			} catch (Exception e) {
				System.out.println("" + e);
			}
	}
		
		// Método para la interrupción de cualquiera de los sonidos
	public static void stop(int i) {
		switch (i) {
		case 1:
			sonido1.stop();
			break;
		case 2:
			sonido2.stop();
			break;
		case 3:
			sonido3.stop();
			break;
		case 4:
			sonido4.stop();
			break;
		case 5:
			sonido5.stop();
			break;
		case 6:
			sonido6.stop();
			break;
		case 7:
			sonido7.stop();
			break;
		default:
			break;
		}
	}
}
