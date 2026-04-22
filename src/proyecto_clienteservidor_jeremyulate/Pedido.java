package proyecto_clienteservidor_jeremyulate;
import java.util.ArrayList;
public class Pedido {
    
    private int idPedido;
    private int idUsuario;
    private ArrayList<Producto> productos;
    private String estado;

    public Pedido(int idUsuario) {
        this.idUsuario = idUsuario;
        productos = new ArrayList<>();
        estado = "Pendiente";
    }

    public Pedido(int idPedido, int idUsuario, String estado) {
        this.idPedido = idPedido;
        this.idUsuario = idUsuario;
        this.estado = estado;
        productos = new ArrayList<>();
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void agregarProducto(Producto p) {
        productos.add(p);
    }

    public void quitarProducto(Producto p) {
        productos.remove(p);
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public double calcularTotal() {
        double total = 0;
        for (Producto p : productos) {
            total += p.calcularPrecio();
        }
        return total;
    }

    public void confirmarPedido() throws Exception {
        if (productos.isEmpty()) {
            throw new Exception("El pedido esta vacio");
        }
        estado = "Pendiente";
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pedido #" + idPedido + " - " + estado;
    }
}