package proyecto_clienteservidor_jeremyulate;
import java.time.LocalDate;
public class Factura {

    private int idFactura;
    private int idPedido;
    private LocalDate fecha;
    private double subtotal;
    private double impuestos;
    private double total;

    public Factura(int idFactura, int idPedido, LocalDate fecha, double subtotal, double impuestos, double total) {
        this.idFactura = idFactura;
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.impuestos = impuestos;
        this.total = total;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getImpuestos() {
        return impuestos;
    }

    public double getTotal() {
        return total;
    }
}