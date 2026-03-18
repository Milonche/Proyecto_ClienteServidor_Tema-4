package proyecto_clienteservidor_jeremyulate;
import javax.swing.*;
import java.awt.*;
public class VentanaFactura extends JFrame{

    private Pedido pedido;

    public VentanaFactura(Pedido pedido){

        this.pedido = pedido;

        setTitle("Factura");
        setSize(300,300);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JTextArea area = new JTextArea();
        area.setBounds(20, 20, 240, 100);
        add(area);

        JButton btnGenerar = new JButton("Generar");
        btnGenerar.setBounds(80, 140, 120, 30);
        add(btnGenerar);

        JButton btnExportar = new JButton("Exportar");
        btnExportar.setBounds(80, 190, 120, 30);
        add(btnExportar);

        btnGenerar.addActionListener(e -> {
            Factura f = new Factura(pedido);

            area.setText(
                "Subtotal: " + f.getSubtotal() +
                "\nImpuestos: " + f.getImpuestos() +
                "\nTotal: " + f.getTotal()
            );
        });

        btnExportar.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Factura exportada");
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
