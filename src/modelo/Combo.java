package modelo;

import java.util.ArrayList;

public class Combo implements Producto {
    private double descuento;
    private String nombreCombo;
    private ArrayList<ProductoMenu> itemsCombo;

    public Combo( String nombreCombo, double descuento) {
        this.descuento = descuento;
        this.nombreCombo = nombreCombo;
        itemsCombo = new ArrayList<ProductoMenu>();
    }

    public void agregarItemACombo(ProductoMenu itemCombo) {
    	itemsCombo.add(itemCombo);
    }

    public int getPrecio() {
        int totalPrecio=0;
        ProductoMenu actual;
        for (int i=0; i<itemsCombo.size(); i++) {
            actual = itemsCombo.get(i);
            totalPrecio+=actual.getPrecio();
        }
        return totalPrecio;
    }

    public String getNombre() {
        return this.nombreCombo;
    }

    @Override
    public String generarTextoFactura() {
        return (nombreCombo+" ".repeat(40-nombreCombo.length())+"$"+getPrecio());
    }

    public String genererarTextoFactura() {
		
		return null;
	}
}
