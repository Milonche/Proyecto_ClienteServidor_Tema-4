package proyecto_clienteservidor_jeremyulate;
import javax.swing.*;
import java.awt.*;
public class VentanaRegistroProducto extends JFrame{
    
    private ClienteSocket cliente;

    public VentanaRegistroProducto() {
        cliente = new ClienteSocket("127.0.0.1", 5000);

        setTitle("Registro de Producto");
        setSize(330, 280);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 30, 100, 25);
        add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(120, 30, 150, 25);
        add(txtNombre);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(30, 70, 100, 25);
        add(lblPrecio);

        JTextField txtPrecio = new JTextField();
        txtPrecio.setBounds(120, 70, 150, 25);
        add(txtPrecio);

        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(30, 110, 100, 25);
        add(lblCategoria);

        JTextField txtCategoria = new JTextField();
        txtCategoria.setBounds(120, 110, 150, 25);
        add(txtCategoria);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(100, 170, 120, 30);
        add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText();
                double precio = Double.parseDouble(txtPrecio.getText());
                String categoria = txtCategoria.getText();

                if (nombre.isEmpty() || categoria.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe completar todos los datos");
                    return;
                }

                if (cliente.registrarProducto(nombre, precio, categoria)) {
                    JOptionPane.showMessageDialog(null, "Producto registrado correctamente");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo registrar el producto");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Datos invalidos o error de conexion");
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}