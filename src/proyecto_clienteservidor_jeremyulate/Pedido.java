package proyecto_clienteservidor_jeremyulate;
import java.util.ArrayList;
public class Pedido {
    private ArrayList<Producto> productos;
    private String estado;
    
    public Pedido(){
        productos = new ArrayList<>();
        estado = "Pendiente";
    }
    
    public void agregarProductos(Producto p){
        productos.add(p);
    }
    
    public void quitarProducto(Producto p){
        productos.remove(p);
    }
    
    public double calcularTotal(){
        double total = 0;
        for(Producto p : productos){
            total += p.calcularPrecio();
        }
        return total;
    }
    
    public void setEstado(String estado){
        this.estado = estado;
    }
    
    public void confirmarPedido() throws Exception{
        if (productos.isEmpty()){
            throw new Exception("El pedido esta vacio");
        }
        estado = "En preparacion";
    }
    
    public void getEstado(){
        this.estado = estado;
    }
}
