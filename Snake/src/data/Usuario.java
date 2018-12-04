package data;

import java.util.List;

public class Usuario {
	private String nombre, constrasenia;
	private List<Integer> puntuacion;

	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Usuario(String nombre, String constrasenia, List<Integer> puntuacion) {
		super();
		this.nombre = nombre;
		this.constrasenia = constrasenia;
		this.puntuacion = puntuacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getConstrasenia() {
		return constrasenia;
	}

	public void setConstrasenia(String constrasenia) {
		this.constrasenia = constrasenia;
	}
	
	public List<Integer> getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(List<Integer> puntuacion) {
		this.puntuacion = puntuacion;
	}

	@Override
	public String toString() {
		return "Nombre: " + nombre + "; Constraseña: " + constrasenia + "; Puntuacion: " + puntuacion;
	}

	

}
