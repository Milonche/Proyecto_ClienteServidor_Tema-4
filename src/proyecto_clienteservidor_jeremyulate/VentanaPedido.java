package proyecto_clienteservidor_jeremyulate;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class VentanaPedido extends JFrame{

    private Pedido pedido;
    private ClienteSocket cliente;
    private DefaultListModel<Producto> modeloDisponibles;
    private DefaultListModel<Producto> modeloPedido;
    private JList<Producto> listaDisponibles;
    private JList<Producto> listaPedido;
    private JLabel lblTotal;

    public VentanaPedido(Usuario usuarioActual) {
        cliente = new ClienteSocket("127.0.0.1", 5000);
        pedido = new Pedido(usuarioActual.getIdUsuario());

        setTitle("Crear Pedido");
        setSize(650, 400);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblDisponibles = new JLabel("Productos y Combos");
        lblDisponibles.setBounds(40, 20, 150, 25);
        add(lblDisponibles);

        modeloDisponibles = new DefaultListModel<>();
        try {
            ArrayList<Producto> productos = cliente.listarProductos();
            for (Producto p : productos) {
                modeloDisponibles.addElement(p);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudieron cargar los productos");
        }

        listaDisponibles = new JList<>(modeloDisponibles);
        JScrollPane scroll1 = new JScrollPane(listaDisponibles);
        scroll1.setBounds(30, 50, 230, 180);
        add(scroll1);

        JLabel lblPedido = new JLabel("Pedido Actual");
        lblPedido.setBounds(380, 20, 150, 25);
        add(lblPedido);

        modeloPedido = new DefaultListModel<>();
        listaPedido = new JList<>(modeloPedido);
        JScrollPane scroll2 = new JScrollPane(listaPedido);
        scroll2.setBounds(360, 50, 230, 180);
        add(scroll2);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(90, 250, 100, 30);
        add(btnAgregar);

        JButton btnQuitar = new JButton("Quitar");
        btnQuitar.setBounds(250, 250, 100, 30);
        add(btnQuitar);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBounds(430, 250, 120, 30);
        add(btnConfirmar);

        lblTotal = new JLabel("Total: ₡0.0");
        lblTotal.setBounds(260, 300, 150, 25);
        add(lblTotal);

        btnAgregar.addActionListener(e -> {
            Producto seleccionado = listaDisponibles.getSelectedValue();

            if (seleccionado == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un producto");
                return;
            }

            pedido.agregarProducto(seleccionado);
            modeloPedido.addElement(seleccionado);
            actualizarTotal();
        });

        btnQuitar.addActionListener(e -> {
            Producto seleccionado = listaPedido.getSelectedValue();

            if (seleccionado == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un producto del pedido");
                return;
            }

            pedido.quitarProducto(seleccionado);
            modeloPedido.removeElement(seleccionado);
            actualizarTotal();
        });

        btnConfirmar.addActionListener(e -> {
            try {
                pedido.confirmarPedido();
                int idPedido = cliente.guardarPedido(pedido);
                pedido.setIdPedido(idPedido);

                JOptionPane.showMessageDialog(null, "Pedido registrado y enviado a cocina");
                new VentanaFactura(pedido.getIdPedido());
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void actualizarTotal() {
        lblTotal.setText("Total: ₡" + pedido.calcularTotal());
    }
}