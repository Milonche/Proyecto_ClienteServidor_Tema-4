package proyecto_clienteservidor_jeremyulate;
import java.util.ArrayList;
public class Combo extends Producto{
    private ArrayList<Producto> productos;
    
    public Combo (String nombre){
        super(nombre, 0);
        productos = new ArrayList<>();
    }
    
    public void agregarProducto(Producto p){
        productos.add(p);
    }
    
    @Override
    public double calcularPrecio(){
        double total = 0;
        for(Producto p : productos){
            total += p.calcularPrecio();
        }
        return total;
    }
}
