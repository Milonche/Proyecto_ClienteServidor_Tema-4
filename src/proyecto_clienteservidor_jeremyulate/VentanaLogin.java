package proyecto_clienteservidor_jeremyulate;
import javax.swing.*;
import java.awt.*;
public class VentanaLogin extends JFrame{
    
    public VentanaLogin(){

        setTitle("Login");
        setSize(300,200);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //fondo
        getContentPane().setBackground(Color.WHITE);

        //etiqueta
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(30, 40, 80, 25);
        add(lblUsuario);

        //espacio de texto
        JTextField txtUsuario = new JTextField();
        txtUsuario.setBounds(110, 40, 130, 25);
        add(txtUsuario);

        //boton
        JButton btnLogin = new JButton("Ingresar");
        btnLogin.setBounds(90, 100, 120, 30);
        add(btnLogin);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}

