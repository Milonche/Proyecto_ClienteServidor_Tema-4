package proyecto_clienteservidor_jeremyulate;
public class Main {

    public static void main(String[] args) {
        
        //crea productos
        Producto p1 = new Producto("Hamburguesa", 2500);
        Producto p2 = new Producto("Papas", 1200);
        Producto p3 = new Producto("Refresco", 1000);

        //crea combo
        Combo combo1 = new Combo("Combo Clasico");
        combo1.agregarProducto(p1);
        combo1.agregarProducto(p2);
        combo1.agregarProducto(p3);

        //crea pedido
        Pedido pedido = new Pedido();
        pedido.agregarProducto(p1);
        pedido.agregarProducto(combo1);

        try {
            pedido.confirmarPedido();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //genera factura
        Factura factura = new Factura(pedido);

        System.out.println("Subtotal: " + factura.getSubtotal());
        System.out.println("Impuestos: " + factura.getImpuestos());
        System.out.println("Total: " + factura.getTotal());

        //mostrar interfaz
        // new VentanaLogin();
        // new VentanaPedido();
        // new VentanaCocina();
        new VentanaCocina();
    }
}