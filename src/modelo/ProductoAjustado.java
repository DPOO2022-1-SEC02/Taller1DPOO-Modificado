package modelo;

import java.util.ArrayList;

public class ProductoAjustado implements Producto {
	
	private ProductoMenu base;
	private ArrayList<Ingrediente> agregados;
	private ArrayList<Ingrediente> eliminados;
	
	public ProductoAjustado(ProductoMenu base) {
		this.base = base;
		agregados = new ArrayList<Ingrediente>();
		eliminados = new ArrayList<Ingrediente>();
	}

	public void agregarAlgo(Ingrediente ingrediente){
		base.addIngredient(ingrediente.getNombre(),ingrediente.getCostoAdicional());
	}
	public void quitarAlgo(Ingrediente ingrediente){
		base.deleteIngredient(ingrediente.getNombre());
	}
	
	public int getPrecio() {
		int rta = base.getPrecio();
		for (int i=0; i < agregados.size(); i++) {
			rta += agregados.get(i).getCostoAdicional();
		}
		return rta;
	}
	
	public String getNombre() {
		return base.getNombre();
	}



	public String generarTextoFactura() {
		return base.generarTextoFactura();
	}
	
	public void agregarAAgregados(Ingrediente ingrediente) {
		agregados.add(ingrediente);
	}
	
	public int getCalorias(){
		return base.getCalorias();
	}
	
}
