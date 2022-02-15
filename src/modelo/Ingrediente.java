package modelo;

public class Ingrediente {
	private String nombre;
	private int costoAdicional;
	private int calorias;
	
	public Ingrediente(String nombre, int costoAdicional, int calorias) {
		this.nombre = nombre;
		this.costoAdicional = costoAdicional;
		this.calorias = calorias;
	}

	public String getNombre() {

		return this.nombre;
	}

	public int getCostoAdicional() {
		return this.costoAdicional;
	}
}
