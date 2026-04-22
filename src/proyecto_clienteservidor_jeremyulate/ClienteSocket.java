package proyecto_clienteservidor_jeremyulate;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
public class ClienteSocket {
    
    private String host;
    private int puerto;

    public ClienteSocket(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    private String enviarPeticion(String mensaje) throws Exception {
        try (Socket socket = new Socket(host, puerto);
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            salida.println(mensaje);
            return entrada.readLine();
        }
    }

    public String[] login(String usuario, String contrasena) throws Exception {
        String respuesta = enviarPeticion("LOGIN|" + usuario + "|" + contrasena);
        return respuesta.split("\\|");
    }

    public ArrayList<Producto> listarProductos() throws Exception {
        ArrayList<Producto> lista = new ArrayList<>();
        String respuesta = enviarPeticion("LISTAR_PRODUCTOS");

        if (!respuesta.startsWith("OK|")) {
            return lista;
        }

        String datos = respuesta.substring(3);
        if (datos.isEmpty()) {
            return lista;
        }

        String[] registros = datos.split(";");
        for (String r : registros) {
            String[] campos = r.split(",");
            int id = Integer.parseInt(campos[0]);
            String nombre = campos[1];
            double precio = Double.parseDouble(campos[2]);
            String categoria = campos[3];
            boolean disponible = Boolean.parseBoolean(campos[4]);
            String tipo = campos[5];

            lista.add(new Producto(id, nombre, precio, categoria, disponible, tipo));
        }

        return lista;
    }

    public int guardarPedido(Pedido pedido) throws Exception {
        StringBuilder ids = new StringBuilder();

        for (int i = 0; i < pedido.getProductos().size(); i++) {
            ids.append(pedido.getProductos().get(i).getIdProducto());
            if (i < pedido.getProductos().size() - 1) {
                ids.append(",");
            }
        }

        String respuesta = enviarPeticion("GUARDAR_PEDIDO|" + pedido.getIdUsuario() + "|" + ids);
        String[] partes = respuesta.split("\\|");

        if (partes[0].equals("OK")) {
            return Integer.parseInt(partes[1]);
        }

        throw new Exception(partes[1]);
    }

    public ArrayList<String> listarPedidosTexto() throws Exception {
        ArrayList<String> lista = new ArrayList<>();
        String respuesta = enviarPeticion("LISTAR_PEDIDOS");

        if (!respuesta.startsWith("OK|")) {
            return lista;
        }

        String datos = respuesta.substring(3);
        if (datos.isEmpty()) {
            return lista;
        }

        String[] registros = datos.split(";");
        for (String r : registros) {
            lista.add(r);
        }

        return lista;
    }

    public boolean cambiarEstadoPedido(int idPedido, String estado) throws Exception {
        String respuesta = enviarPeticion("CAMBIAR_ESTADO|" + idPedido + "|" + estado);
        return respuesta.startsWith("OK");
    }

    public boolean priorizarPedido(int idPedido) throws Exception {
        String respuesta = enviarPeticion("PRIORIZAR_PEDIDO|" + idPedido);
        return respuesta.startsWith("OK");
    }

    public String generarFacturaTexto(int idPedido) throws Exception {
        String respuesta = enviarPeticion("GENERAR_FACTURA|" + idPedido);

        if (!respuesta.startsWith("OK|")) {
            throw new Exception(respuesta);
        }

        return respuesta.substring(3).replace("##", "\n");
    }

    public boolean exportarFacturaTxt(int idPedido) throws Exception {
        String respuesta = enviarPeticion("EXPORTAR_FACTURA_TXT|" + idPedido);
        return respuesta.startsWith("OK");
    }

    public boolean registrarProducto(String nombre, double precio, String categoria) throws Exception {
        String respuesta = enviarPeticion("REGISTRAR_PRODUCTO|" + nombre + "|" + precio + "|" + categoria);
        return respuesta.startsWith("OK");
    }

    public boolean registrarCajero(String nombre, String usuario, String contrasena, String sucursal) throws Exception {
        String respuesta = enviarPeticion("REGISTRAR_CAJERO|" + nombre + "|" + usuario + "|" + contrasena + "|" + sucursal);
        return respuesta.startsWith("OK");
    }

    public boolean registrarCombo(String nombre, ArrayList<Producto> seleccionados) throws Exception {
        StringBuilder ids = new StringBuilder();
        for (int i = 0; i < seleccionados.size(); i++) {
            ids.append(seleccionados.get(i).getIdProducto());
            if (i < seleccionados.size() - 1) {
                ids.append(",");
            }
        }

        String respuesta = enviarPeticion("REGISTRAR_COMBO|" + nombre + "|" + ids);
        return respuesta.startsWith("OK");
    }
}