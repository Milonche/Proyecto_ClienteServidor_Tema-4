package proyecto_clienteservidor_jeremyulate;
import java.util.ArrayList;
public class GestorCocina {
    
    private ClienteSocket cliente;

    public GestorCocina() {
        cliente = new ClienteSocket("127.0.0.1", 5000);
    }

    public ArrayList<String> listarPedidos() throws Exception {
        return cliente.listarPedidosTexto();
    }

    public boolean cambiarEstado(int idPedido, String estado) throws Exception {
        return cliente.cambiarEstadoPedido(idPedido, estado);
    }

    public boolean priorizarPedido(int idPedido) throws Exception {
        return cliente.priorizarPedido(idPedido);
    }
}