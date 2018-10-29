package data;

public class Usuario {
	private String nombre, constrasenia;

	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Usuario(String nombre, String constrasenia) {
		super();
		this.nombre = nombre;
		this.constrasenia = constrasenia;
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

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", constrasenia=" + constrasenia + "]";
	}

}
