package consola;

import modelo.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Aplicacion {
    private Restaurante restaurante = new Restaurante();


    private final File archivoMenu = new File("./data/menu.txt");
    private final File archivoCombos = new File("./data/combos.txt");
    private final File archivoIngredientes = new File("./data/ingredientes.txt");
    private final File archivoBebidas = new File("./data/bebidas.txt");

    public static void main(String[] args) throws Exception {
        Aplicacion aplicacion = new Aplicacion();

        aplicacion.ejecutarOpcion();

    }

    public void ejecutarOpcion() throws Exception {
        int opcion;
        boolean continuar = true;
        int id;
        restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos,archivoBebidas);

        while (continuar) {

            mostrarMenu();
            opcion = Integer.parseInt(input("Por favor selecciona una opcion"));
            switch (opcion) {


                case (1) -> {
                    restaurante.mostrarMenuBase();
                    restaurante.mostrarCombos();
                    restaurante.mostrarBebidas();
                    
                }

                case (2) -> {
                    String nombreCliente = input("Escriba el nombre del cliente");
                    String direccionCliente = input("Escriba la direccion del cliente");

                    restaurante.iniciarPedido(nombreCliente, direccionCliente);

                }


                case (3) -> restaurante.cerrarYGuardarPedido();
                case (4) -> {
                    id = pedirId();
                    Pedido pedido = restaurante.getPedidoPorId(id);
                    System.out.println("\nId de pedido: " + id);
                    System.out.println(pedido.generarTextoFactura());
                }

                case (5) -> continuar = false;
            }

        }
    }

    public int pedirId() {
        return Integer.parseInt(input("Escribe el id del pedido."));
    }

    public void mostrarMenu() {
        System.out.print("""              
                
                Bienvenido a El Corral
                Estas son las opciones que tenemos para ti:    
                \n1. Mostrar el menu
                2. Iniciar un nuevo pedido
                3. Cerrar un pedido y guardar la factura
                4. Consultar la información de un pedido dado su id
                5. Salir de la aplicacion
                """);
    }




    public String input(String mensaje) {
        try {
            System.out.print(mensaje + ": ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Error leyendo de la consola");
            e.printStackTrace();
        }
        return null;
    }
}


