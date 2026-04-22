package proyecto_clienteservidor_jeremyulate;
import javax.swing.*;
import java.awt.*;
public class VentanaRegistroCajero extends JFrame {
    
    private ClienteSocket cliente;

    public VentanaRegistroCajero() {
        cliente = new ClienteSocket("127.0.0.1", 5000);

        setTitle("Registro de Cajero");
        setSize(350, 300);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 30, 100, 25);
        add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(130, 30, 150, 25);
        add(txtNombre);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(30, 70, 100, 25);
        add(lblUsuario);

        JTextField txtUsuario = new JTextField();
        txtUsuario.setBounds(130, 70, 150, 25);
        add(txtUsuario);

        JLabel lblContrasena = new JLabel("Contrasena:");
        lblContrasena.setBounds(30, 110, 100, 25);
        add(lblContrasena);

        JTextField txtContrasena = new JTextField();
        txtContrasena.setBounds(130, 110, 150, 25);
        add(txtContrasena);

        JLabel lblSucursal = new JLabel("Sucursal:");
        lblSucursal.setBounds(30, 150, 100, 25);
        add(lblSucursal);

        JTextField txtSucursal = new JTextField();
        txtSucursal.setBounds(130, 150, 150, 25);
        add(txtSucursal);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(110, 210, 120, 30);
        add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText();
                String usuario = txtUsuario.getText();
                String contrasena = txtContrasena.getText();
                String sucursal = txtSucursal.getText();

                if (nombre.isEmpty() || usuario.isEmpty() || contrasena.isEmpty() || sucursal.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe completar todos los datos");
                    return;
                }

                if (cliente.registrarCajero(nombre, usuario, contrasena, sucursal)) {
                    JOptionPane.showMessageDialog(null, "Cajero registrado correctamente");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo registrar el cajero");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error de conexion con el servidor");
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}