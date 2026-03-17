package proyecto_clienteservidor_jeremyulate;
import java.util.ArrayList;
public class GestorCocina {
    
    private ArrayList<Pedido> pedidos;
    
    public GestorCocina(){
        pedidos = new ArrayList<>();
    }
    
    public void agregarPedido(Pedido p){
        pedidos.add(p);
    }
    
    public void cambiarEstado(Pedido p, String estado){
        p.setEstado(estado);
    }
    
    public void priorizarPedido(Pedido p){
        pedidos.remove(p);
        pedidos.add(0, p);
    }
}

