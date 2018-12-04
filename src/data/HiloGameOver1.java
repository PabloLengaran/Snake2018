package data;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class HiloGameOver1 implements Runnable {

	private JLabel lblInicio;
	private JFrame framePadre;
	private int tiempoDemora;

	public HiloGameOver1(JLabel lblInicio, JFrame framePadre, int tiempoDemora) {
		super();
		this.lblInicio = lblInicio;
		this.framePadre = framePadre;
		this.tiempoDemora = tiempoDemora;
	}

	// Hilo que "oculta" y "muestra" mediante cambio de color el label que deseemos
	public void run() {
		while (true) {
			lblInicio.setForeground(Color.WHITE);
			try {
				Thread.sleep(tiempoDemora);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lblInicio.setForeground(Color.BLACK);
			try {
				Thread.sleep(350);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
