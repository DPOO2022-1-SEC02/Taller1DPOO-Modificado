package modelo;

public class Bebida implements Producto {
   	private String nombre;
	private int precioBase;
	private int calorias;
	
	public Bebida (String nombre, int precioBase, int calorias) {
	this.nombre = nombre;
	this.precioBase = precioBase;
	this.calorias = calorias;
	}
	
    public int getPrecio() {
        return precioBase;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String generarTextoFactura() {
        return (nombre+" ".repeat(80-nombre.length())+"$. . ."+precioBase);
    }
    
    public int getCalorias(){
        return calorias;
    }
}
