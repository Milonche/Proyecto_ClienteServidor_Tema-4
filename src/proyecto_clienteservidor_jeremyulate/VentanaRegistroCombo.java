package proyecto_clienteservidor_jeremyulate;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class VentanaRegistroCombo extends JFrame {
    
    private ClienteSocket cliente;

    public VentanaRegistroCombo() {
        cliente = new ClienteSocket("127.0.0.1", 5000);

        setTitle("Registro de Combo");
        setSize(420, 350);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblNombre = new JLabel("Nombre del combo:");
        lblNombre.setBounds(20, 20, 120, 25);
        add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(150, 20, 180, 25);
        add(txtNombre);

        JLabel lblProductos = new JLabel("Seleccione productos:");
        lblProductos.setBounds(20, 60, 150, 25);
        add(lblProductos);

        DefaultListModel<Producto> modelo = new DefaultListModel<>();
        try {
            for (Producto p : cliente.listarProductos()) {
                if (!p.getTipo().equalsIgnoreCase("Combo")) {
                    modelo.addElement(p);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudieron cargar productos");
        }

        JList<Producto> listaProductos = new JList<>(modelo);
        listaProductos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scroll = new JScrollPane(listaProductos);
        scroll.setBounds(20, 90, 360, 150);
        add(scroll);

        JButton btnGuardar = new JButton("Guardar Combo");
        btnGuardar.setBounds(120, 260, 150, 30);
        add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText();

                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe escribir el nombre del combo");
                    return;
                }

                ArrayList<Producto> seleccionados = new ArrayList<>(listaProductos.getSelectedValuesList());

                if (seleccionados.size() < 2) {
                    JOptionPane.showMessageDialog(null, "Seleccione al menos 2 productos");
                    return;
                }

                if (cliente.registrarCombo(nombre, seleccionados)) {
                    JOptionPane.showMessageDialog(null, "Combo registrado correctamente");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo registrar el combo");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error de conexion con el servidor");
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}