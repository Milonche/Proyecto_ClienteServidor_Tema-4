package proyecto_clienteservidor_jeremyulate;
import javax.swing.*;
import java.awt.*;
public class VentanaPedido extends JFrame{

    private Pedido pedido;
    private JTextArea areaPedido;

    public VentanaPedido(){

        pedido = new Pedido();

        setTitle("Crear Pedido");
        setSize(400,300);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        areaPedido = new JTextArea();
        areaPedido.setBounds(50, 30, 300, 100);
        add(areaPedido);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(50, 150, 100, 30);
        add(btnAgregar);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBounds(200, 150, 120, 30);
        add(btnConfirmar);

        btnAgregar.addActionListener(e -> {
            Producto p = new Producto("Hamburguesa", 2000);
            pedido.agregarProducto(p);

            areaPedido.append(p.getNombre() + " - ₡" + p.getPrecio() + "\n");
        });

        btnConfirmar.addActionListener(e -> {
            try {
                pedido.confirmarPedido();
                JOptionPane.showMessageDialog(null, "Pedido enviado a cocina");

                new VentanaFactura(pedido);
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
