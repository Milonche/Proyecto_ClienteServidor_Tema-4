package proyecto_clienteservidor_jeremyulate;
import javax.swing.*;
import java.awt.*;
public class VentanaFactura extends JFrame{

    private int idPedido;
    private JTextArea area;
    private ClienteSocket cliente;

    public VentanaFactura(int idPedido) {
        this.idPedido = idPedido;
        this.cliente = new ClienteSocket("127.0.0.1", 5000);

        setTitle("Facturacion");
        setSize(350, 350);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        area = new JTextArea();
        area.setEditable(false);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(20, 20, 290, 170);
        add(scroll);

        JButton btnGenerar = new JButton("Generar Factura");
        btnGenerar.setBounds(90, 210, 150, 30);
        add(btnGenerar);

        JButton btnExportar = new JButton("Exportar TXT");
        btnExportar.setBounds(90, 255, 150, 30);
        add(btnExportar);

        btnGenerar.addActionListener(e -> {
            try {
                area.setText(cliente.generarFacturaTexto(idPedido));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al generar factura");
            }
        });

        btnExportar.addActionListener(e -> {
            try {
                if (cliente.exportarFacturaTxt(idPedido)) {
                    JOptionPane.showMessageDialog(null, "Factura exportada en TXT");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo exportar");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al exportar factura");
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}