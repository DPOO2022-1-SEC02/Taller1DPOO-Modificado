package modelo;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Restaurante {
    private ArrayList<Ingrediente> ingredientes;
    private ArrayList<ProductoMenu> menuBase;
    private ArrayList<Combo> combos;
    private ArrayList<Pedido> pedidos;
    private ArrayList<Bebida> bebidas;
    private ArrayList<Pedido> pedidosListos;
    private int pedidoEnCurso;


    public Restaurante() {
        this.ingredientes = new ArrayList<>();
        this.menuBase = new ArrayList<>();
        this.combos = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        this.pedidosListos = new ArrayList<>();
        this.bebidas = new ArrayList<>();
        this.pedidoEnCurso = 0;
    }


    public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos, File archivoBebidas) throws Exception {
        cargarMenuBase(archivoMenu);
        cargarBebidas(archivoBebidas);
        cargaIngredientes(archivoIngredientes);
        cargarCombos(archivoCombos);
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
            int calorias = Integer.parseInt(info[2]);
            productoMenu = new ProductoMenu(nombre, precio, calorias);
            menuBase.add(productoMenu);
            line = br.readLine();
        }
        br.close();

    }


    public void cargarBebidas(File archivoBebidas) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(archivoBebidas));
        String line = br.readLine();
        String[] info;
        Bebida bebida;

        while (line != null) {
            info = line.split(";");
            String nombre = info[0];
            int precio = Integer.parseInt(info[1]);
            int calorias = Integer.parseInt(info[2]);
            bebida = new Bebida(nombre, precio, calorias);
            bebidas.add(bebida);
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
                Producto productoAdd = buscarProducto(menuBase, partes[i]);
                Bebida bebidaAdd = buscarBebida(bebidas,partes[i]);

                if (productoAdd != null) {
                      combo.agregarItemACombo(productoAdd);
                      }
//                else{
//                    combo.agregarItemACombo(bebidaAdd);
//                   // System.out.println(bebidaAdd.getPrecio());
               // }

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

    public void mostrarBebidas() {
        int cont = 1;
        System.out.println("\nBebidas:\n");
        for (Bebida bebida : bebidas) {
            System.out.println(cont + ". " + bebida.getNombre() + ": $" + bebida.getPrecio());
            cont++;
        }
    }

    private void cargaIngredientes(File archivoIngredientes) throws Exception {


        BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes));
        String line = br.readLine();
        String[] ingInfo;
        Ingrediente ingrediente;
        while (line != null) {

            ingInfo = line.split(";");
            String nombre = ingInfo[0];
            int precio = Integer.parseInt(ingInfo[1]);
            int calorias = Integer.parseInt(ingInfo[2]);
            ingrediente = new Ingrediente(nombre, precio, calorias);
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
                    2. Ver combos.
                    3. Ver bebidas.
                    0. Volver"""));
            if (seleccion == 1) {

                agregarProducto(pedido);

            } else if (seleccion == 2) {
                agregarCombo(pedido);

            } else if (seleccion == 3) {
                agregarBebida(pedido);
            } else {
                if (pedido.getCantidadItems() != 0) pedidos.add(pedido);
                continuar = false;
            }
        }


    }


    private void agregarBebida(Pedido pedido) {
        mostrarBebidas();
        int bSeleccion = Integer.parseInt(input("Selecciona una de las bebidas disponibles por favor")) - 1;
        Bebida bebida = bebidas.get(bSeleccion);
        pedido.agregarProducto(bebida);
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
            System.out.println(cont + ". " + ingrediente.getNombre() + ": $" + ingrediente.getCostoAdicional());
            cont++;
        }

    }


    public ProductoMenu buscarProducto(ArrayList<ProductoMenu> menuBase, String nombre) {
        for (ProductoMenu producto : menuBase) {
            if (producto.getNombre().equals(nombre)) {
                return producto;
            }
        }
        return null;
    }

    public Bebida buscarBebida(ArrayList<Bebida> bebidas, String nombre) {

        for (Bebida bebida:bebidas)
        {
            if (bebida.getNombre().equals(nombre)){
                return bebida;
            }
        }
        return null;
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
        Pedido orden = pedidos.get(id_actual);
        for(Pedido pedido : pedidosListos){
            System.out.println(pedido);
            if (orden.equals(pedido)){
                System.err.print("Ya existe un pedido igual, por favor intente de nuevo.");
                return;
            }
        }
        pedidosListos.add(orden);
        orden.guardarFactura(new File("./data/facturas/" + id_actual + ".txt"));
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
