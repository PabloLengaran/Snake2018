package data;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class HiloGameOver2 implements Runnable {

	private String prueba;
	private JLabel lblInicio;
	private JFrame framePadre;

	public HiloGameOver2(String prueba, JLabel lblInicio, JFrame framePadre) {
		super();
		this.prueba = prueba;
		this.lblInicio = lblInicio;
		this.framePadre = framePadre;
	}
	
	// Hilo que escribe carcater a caracter los que conformen el label que deseemos
	public void run() {
		String resultado;
		lblInicio.setText("");
		for (int i = 0; i < prueba.length(); i++) {
			resultado = lblInicio.getText() + prueba.charAt(i);
			lblInicio.setText(resultado);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
