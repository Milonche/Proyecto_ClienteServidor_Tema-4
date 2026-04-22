package proyecto_clienteservidor_jeremyulate;
import java.util.ArrayList;
public class Combo extends Producto{
    
    private ArrayList<Producto> productos;

    public Combo(int idProducto, String nombre, String categoria, boolean disponible) {
        super(idProducto, nombre, 0, categoria, disponible, "Combo");
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto p) {
        productos.add(p);
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    @Override
    public double calcularPrecio() {
        double total = 0;
        for (Producto p : productos) {
            total += p.calcularPrecio();
        }
        return total;
    }

    @Override
    public String toString() {
        return nombre + " (Combo) - ₡" + calcularPrecio();
    }
}