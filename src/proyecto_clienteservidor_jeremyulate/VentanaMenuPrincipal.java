package proyecto_clienteservidor_jeremyulate;
import javax.swing.*;
import java.awt.*;
public class VentanaMenuPrincipal extends JFrame {
    
    private Usuario usuarioActual;

    public VentanaMenuPrincipal(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;

        setTitle("Menu Principal");
        setSize(380, 350);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("MENU PRINCIPAL");
        lblTitulo.setBounds(110, 20, 150, 25);
        add(lblTitulo);

        JLabel lblUsuario = new JLabel("Usuario: " + usuarioActual.getNombre());
        lblUsuario.setBounds(20, 50, 300, 25);
        add(lblUsuario);

        JButton btnPedido = new JButton("Crear Pedido");
        btnPedido.setBounds(100, 90, 150, 30);
        add(btnPedido);

        JButton btnVerPedidos = new JButton("Ver Pedidos");
        btnVerPedidos.setBounds(100, 130, 150, 30);
        add(btnVerPedidos);

        JButton btnRegistrarProducto = new JButton("Registrar Producto");
        btnRegistrarProducto.setBounds(100, 170, 150, 30);
        add(btnRegistrarProducto);

        JButton btnRegistrarCajero = new JButton("Registrar Cajero");
        btnRegistrarCajero.setBounds(100, 210, 150, 30);
        add(btnRegistrarCajero);

        JButton btnCerrar = new JButton("Cerrar Sesion");
        btnCerrar.setBounds(100, 250, 150, 30);
        add(btnCerrar);

        btnPedido.addActionListener(e -> new VentanaPedido(usuarioActual));
        btnVerPedidos.addActionListener(e -> new VentanaCocina());
        btnRegistrarProducto.addActionListener(e -> new VentanaRegistroProducto());
        btnRegistrarCajero.addActionListener(e -> new VentanaRegistroCajero());

        btnCerrar.addActionListener(e -> {
            new VentanaLogin();
            dispose();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}