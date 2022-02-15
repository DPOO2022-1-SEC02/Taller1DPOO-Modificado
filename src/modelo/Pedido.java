package modelo;


import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Pedido {


    private int numeroPedidos;
    private int idPedido;
    private String nombreCliente;
    private String direccionCliente;
    private ArrayList<Producto> itemsPedido;
    private int numeroItems;

    private Restaurante restaurante = new Restaurante();

    public Pedido(int idPedido, int numeroPedidos, String nombreCliente, String direccionCliente) {
        this.numeroPedidos = numeroPedidos;
        this.idPedido = idPedido;
        this.nombreCliente = nombreCliente;
        this.direccionCliente = direccionCliente;
        this.itemsPedido = new ArrayList<>();

    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return numeroPedidos == pedido.numeroPedidos  && numeroItems == pedido.numeroItems && Objects.equals(itemsPedido, pedido.itemsPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroPedidos, idPedido, nombreCliente, direccionCliente, itemsPedido, numeroItems, restaurante);
    }

    public ArrayList<Producto> getItemsPedido(){
        return itemsPedido;
    }


    public int getIdPedido() {
        return this.idPedido;
    }

    public void agregarProducto(Producto nuevoItem) {
        itemsPedido.add(nuevoItem);
        numeroItems++;
    }

    public int getCantidadItems(){
        return numeroItems;
    }

    private int getPrecioPedido() {
        int precio = 0;
        for (Producto item : itemsPedido) {
            precio += item.getPrecio();
        }
        return precio;
    }


    public String generarTextoFactura() {
        String textoFactura = "              Factura de compra\n";
        textoFactura += ("Nombre del cliente: " + nombreCliente + "\n");
        textoFactura += ("Dirección del cliente: " + direccionCliente + "\n");
        for (Producto producto : itemsPedido) {
            textoFactura += (producto.generarTextoFactura() + "\n");
        }

        textoFactura +=("Calorias del producto : "+getCalorias());
        textoFactura += ("\nPrecio total " + " ".repeat(27) + "$ . . ."+getPrecioPedido()) + "\n";
        textoFactura += ("Precio total con iva" + " ".repeat(20) + "$ . . ."+ (getPrecioPedido() * 0.19 + getPrecioPedido()));
        return textoFactura;
    }



    public int getCalorias(){
        int calorias=0;
        for(Producto producto: itemsPedido){
            calorias+= producto.getCalorias();
        }
        return calorias;
    }

    public void guardarFactura(File archivo) throws Exception {
        try {

            if (archivo.createNewFile()) {
            } else {
                System.out.println("⚠️ File already exists");
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
            String txtFactura = generarTextoFactura();
            bw.write(txtFactura);
            bw.close();


        } catch (Exception i) {
            System.out.println(i);
            System.err.println("⚠️Hubo un error");
        }
    }
}

