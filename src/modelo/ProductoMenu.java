package modelo;

public class ProductoMenu implements Producto {

	private String nombre;
	private int precioBase;
	private int calorias;

	public ProductoMenu (String nombre, int precioBase, int calorias) {
		this.nombre = nombre;
		this.precioBase = precioBase;
		this.calorias = calorias;
	}


	public String getNombre() {
		return nombre;
	}

	public void addIngredient(String name,int precioExtra){
		precioBase+=precioExtra;
		nombre+=" con extra "+name;
	}

	public void deleteIngredient(String name){
		nombre+=" sin "+name;
	}


	public String generarTextoFactura() {
		return (nombre+" ".repeat(80-nombre.length())+"$. . ."+precioBase);
	}

	public int getPrecio() {
		return precioBase;
	}

	
	public int getCalorias(){
		return calorias;
	}
}
