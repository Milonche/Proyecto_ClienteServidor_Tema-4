package proyecto_clienteservidor_jeremyulate;
import javax.swing.*;
import java.awt.*;
public class VentanaLogin extends JFrame{
    
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private ClienteSocket cliente;

    public VentanaLogin() {
        cliente = new ClienteSocket("127.0.0.1", 5000);

        setTitle("Login");
        setSize(320, 230);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("INICIO DE SESION");
        lblTitulo.setBounds(95, 15, 150, 25);
        add(lblTitulo);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(30, 60, 80, 25);
        add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(110, 60, 150, 25);
        add(txtUsuario);

        JLabel lblContrasena = new JLabel("Contrasena:");
        lblContrasena.setBounds(30, 95, 80, 25);
        add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(110, 95, 150, 25);
        add(txtContrasena);

        JButton btnLogin = new JButton("Ingresar");
        btnLogin.setBounds(100, 140, 120, 30);
        add(btnLogin);

        btnLogin.addActionListener(e -> {
            try {
                String usuario = txtUsuario.getText();
                String contrasena = new String(txtContrasena.getPassword());

                String[] respuesta = cliente.login(usuario, contrasena);

                if (!respuesta[0].equals("OK")) {
                    JOptionPane.showMessageDialog(null, "Usuario o contrasena incorrectos");
                    return;
                }

                int idUsuario = Integer.parseInt(respuesta[1]);
                String rol = respuesta[2];
                String nombre = respuesta[3];

                Usuario u = new Usuario(idUsuario, nombre, usuario, contrasena, rol);

                if (rol.equalsIgnoreCase("Cocina")) {
                    new VentanaCocina();
                } else {
                    new VentanaMenuPrincipal(u);
                }

                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error de conexion con el servidor");
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}