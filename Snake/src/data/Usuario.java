package data;

public class Usuario {
	private String nombre, constrasenia;
	private int puntuacion;

	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Usuario(String nombre, String constrasenia, int puntuacion) {
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
	
	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}
	
	@Override
	public String toString() {
		return "Nombre: " + nombre + "; Constraseña: " + constrasenia + "; Puntuacion: " + puntuacion;
	}

	

}
