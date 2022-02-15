package modelo;

import java.util.ArrayList;

public class Combo implements Producto {
    private double descuento;
    private String nombreCombo;
    private ArrayList<Producto> itemsCombo;

    public Combo( String nombreCombo, double descuento) {
        this.descuento = descuento;
        this.nombreCombo = nombreCombo;
        itemsCombo = new ArrayList<Producto>();
    }

    public void agregarItemACombo(Producto itemCombo) {
    	itemsCombo.add(itemCombo);
    }

    public int getPrecio() {
        int totalPrecio=0;
        Producto actual;
        for (int i=0; i<itemsCombo.size(); i++) {
            actual = itemsCombo.get(i);
            totalPrecio+=actual.getPrecio();
        }
        return totalPrecio;
    }

    public String getNombre() {
        return this.nombreCombo;
    }

    public String generarTextoFactura() {
        return (nombreCombo+" ".repeat(40-nombreCombo.length())+"$"+getPrecio());
    }
    

    public int getCalorias(){
        int totalCalorias=0;
        Producto actual;
        for (int i=0; i<itemsCombo.size(); i++) {
            actual = itemsCombo.get(i);
            totalCalorias+=actual.getCalorias();
        }
        return totalCalorias;
    }
    
}
