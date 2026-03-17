package proyecto_clienteservidor_jeremyulate;
public class Factura {
    private Pedido pedido;
    private double subtotal;
    private double impuestos;
    private double total;
    
    public Factura(Pedido pedido){
        this.pedido = pedido;
        calcularFactura();
    }
    
    private void calcularFactura(){
        subtotal = pedido.calcularTotal();
        impuestos = subtotal * 0.13;
        total = subtotal + impuestos;
    }
    
    public double getSubtotal(){
        return subtotal;
    }
    
    public double getImpuestos(){
        return impuestos;
    }
    
    public double getTotal(){
        return total;
    }
}

