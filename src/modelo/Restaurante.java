package modelo;

import procesamiento.Procesamiento;

import java.io.*;
import java.util.ArrayList;

public class Restaurante {
    private ArrayList<Ingrediente> ingredientes;
    private ArrayList<ProductoMenu> menuBase;
    private ArrayList<Combo> combos;
    private ArrayList<Pedido> pedidos;
    private int pedidoEnCurso;

    private Procesamiento procesador = new Procesamiento();


    public Restaurante() {
        this.ingredientes = new ArrayList<>();
        this.menuBase = new ArrayList<>();
        this.combos = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        this.pedidoEnCurso = 0;
    }


    public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos) throws Exception {
        cargarMenuBase(archivoMenu);
        cargarCombos(archivoCombos);
        cargaIngredientes(archivoIngredientes);

    }


    private void cargarMenuBase(File archivoMenu) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(archivoMenu));
        String line = br.readLine();
        String[] info;
        ProductoMenu productoMenu;

        while (line != null) {
            info = line.split(";");
            String nombre = info[0];
            int precio = Integer.parseInt(info[1]);
            productoMenu = new ProductoMenu(nombre, precio);
            menuBase.add(productoMenu);
            line = br.readLine();
        }
        br.close();

    }


    private void cargarCombos(File archivoCombos) throws Exception {

        BufferedReader bufr = new BufferedReader(new FileReader(archivoCombos));
        String linea = bufr.readLine();
        Combo combo;

        while (linea != null) {
            String[] partes = linea.split(";");
            String nombreCombo = partes[0];
            Double descuento = Double.parseDouble(partes[1].replace("%", ""));
            descuento = descuento / 100;
            combo = new Combo(nombreCombo, descuento);
            for (int i = 2; i < partes.length; i++) {
                combo.agregarItemACombo(procesador.buscarProducto(menuBase, partes[i]));
            }
            combos.add(combo);

            linea = bufr.readLine();
        }
        bufr.close();
    }


    private void mostrarInfo() {
        mostrarCombos();
        mostrarMenuBase();
    }

    public void mostrarCombos() {
        int cont = 1;
        System.out.println("\nCombos: \n");
        for (Combo combo : combos) {
            System.out.println(cont + ". " + combo.getNombre() + " : $" + combo.getPrecio());
            cont++;
        }
    }

    public void mostrarMenuBase() {
        int cont = 1;
        System.out.println("\nMenú clásico: \n");
        for (ProductoMenu producto : menuBase) {
            System.out.println(cont + ". " + producto.getNombre() + " : $" + producto.getPrecio());
            cont++;
        }
    }

    private void cargaIngredientes(File archivoIngredientes) throws Exception {


        BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes));
        String message = " "; //*
        String line = br.readLine();
        String[] ingInfo;
        Ingrediente ingrediente;
        while (line != null) {

            ingInfo = line.split(";");
            String nombre = ingInfo[0];
            int precio = Integer.parseInt(ingInfo[1]);
            ingrediente = new Ingrediente(nombre, precio);
            ingredientes.add(ingrediente);

            line = br.readLine();
        }
        br.close();

    }


    int id_actual = 0;

    public void iniciarPedido(String nombreCliente, String direccionCliente) {
        pedidoEnCurso = id_actual;
        boolean continuar = true;

        Pedido pedido = new Pedido(id_actual, 0, nombreCliente, direccionCliente);


        while (continuar) {
            int seleccion = Integer.parseInt(input("""
                    1. Ver menú clásico.
                    2. Ver combos
                    0. Volver"""));
            if (seleccion == 1) {

                agregarProducto(pedido);

            } else if (seleccion == 2) {
                agregarCombo(pedido);

            } else {
                if (pedido.getCantidadItems() != 0) pedidos.add(pedido);
                continuar = false;
            }
        }


    }


    private void agregarCombo(Pedido pedido) {
        mostrarCombos();
        int cmSeleccion = Integer.parseInt(input("Selecciona uno de los combos que tenemos para tí")) - 1;
        Combo combo = combos.get(cmSeleccion);
        pedido.agregarProducto(combo);
    }


    private void agregarProducto(Pedido pedido) {
        mostrarMenuBase();
        ProductoAjustado modificado = null;
        int prSeleccion = Integer.parseInt(input("Selcciona un producto por favor")) - 1;
        ProductoMenu producto = menuBase.get(prSeleccion);
        boolean continueIngr = true;
        while (continueIngr) {
            int extra = Integer.parseInt(input("""
                    Deseas agregar o quitar algo a tu producto?
                    1. Poner
                    2. Quitar
                    3. No"""));
            if (extra == 1 || extra == 2) {
                Ingrediente ingSeleccionado;
                if (modificado == null) {
                    modificado = new ProductoAjustado(producto);
                }
                showIngredientes();
                int ingredienteNum = Integer.parseInt(input("Escribe el ingrediente que deseas")) - 1;

                ingSeleccionado = ingredientes.get(ingredienteNum);
                if (extra == 1) modificado.agregarAlgo(ingSeleccionado);
                else modificado.quitarAlgo(ingSeleccionado);
            } else {
                if (modificado == null) {
                    pedido.agregarProducto(producto);
                } else {
                    pedido.agregarProducto(modificado);
                }
                continueIngr = false;
            }
        }
    }

    private void showIngredientes() {
        int cont = 1;
        System.out.println("\nIngredientes:\n ");
        for (Ingrediente ingrediente : ingredientes) {
            System.out.println(cont + ". " + ingrediente.getNombre() + ": " + ingrediente.getCostoAdicional());
            cont++;
        }

    }

    public ArrayList<ProductoMenu> getMenuBase() {
        return this.menuBase;
    }

    public ArrayList<Ingrediente> getIngredientes() {
        return this.ingredientes;
    }

    public ArrayList<Combo> getCombos() {
        return this.combos;
    }


    public Pedido getPedidoPorId(int id) {
        return pedidos.get(id);
    }

    public void cerrarYGuardarPedido() throws Exception {
        Pedido cosa = pedidos.get(id_actual);
        System.out.println(cosa.getIdPedido());
        cosa.guardarFactura(new File("./data/facturas/" + id_actual + ".txt"));
        pedidoEnCurso = 0;
        id_actual++;

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
