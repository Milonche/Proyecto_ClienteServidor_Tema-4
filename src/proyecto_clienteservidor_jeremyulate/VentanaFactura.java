package proyecto_clienteservidor_jeremyulate;
import javax.swing.*;
import java.awt.*;
public class VentanaFactura extends JFrame{
    
    public VentanaFactura(){

        setTitle("Factura");
        setSize(300,300);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblDetalle = new JLabel("Detalle:");
        lblDetalle.setBounds(20, 20, 100, 25);
        add(lblDetalle);

        JTextArea areaDetalle = new JTextArea();
        areaDetalle.setBounds(20, 50, 240, 100);
        add(areaDetalle);

        JLabel lblSubtotal = new JLabel("Subtotal:");
        lblSubtotal.setBounds(20, 160, 100, 25);
        add(lblSubtotal);

        JLabel lblImpuestos = new JLabel("Impuestos:");
        lblImpuestos.setBounds(20, 190, 100, 25);
        add(lblImpuestos);

        JLabel lblTotal = new JLabel("Total:");
        lblTotal.setBounds(20, 220, 100, 25);
        add(lblTotal);

        JButton btnGenerar = new JButton("Generar");
        btnGenerar.setBounds(150, 160, 100, 25);
        add(btnGenerar);

        JButton btnExportar = new JButton("Exportar");
        btnExportar.setBounds(150, 200, 100, 25);
        add(btnExportar);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
