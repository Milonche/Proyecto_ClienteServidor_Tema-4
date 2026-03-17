package proyecto_clienteservidor_jeremyulate;
import javax.swing.*;
import java.awt.*;
public class VentanaPedido extends JFrame{
    
        public VentanaPedido(){

        setTitle("Crear Pedido");
        setSize(400,300);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblProductos = new JLabel("Productos:");
        lblProductos.setBounds(20, 20, 100, 25);
        add(lblProductos);

        JTextArea areaProductos = new JTextArea();
        areaProductos.setBounds(20, 50, 150, 120);
        add(areaProductos);

        JLabel lblCombos = new JLabel("Combos:");
        lblCombos.setBounds(200, 20, 100, 25);
        add(lblCombos);

        JTextArea areaCombos = new JTextArea();
        areaCombos.setBounds(200, 50, 150, 120);
        add(areaCombos);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(50, 200, 100, 30);
        add(btnAgregar);

        JButton btnQuitar = new JButton("Quitar");
        btnQuitar.setBounds(160, 200, 100, 30);
        add(btnQuitar);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBounds(270, 200, 100, 30);
        add(btnConfirmar);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
