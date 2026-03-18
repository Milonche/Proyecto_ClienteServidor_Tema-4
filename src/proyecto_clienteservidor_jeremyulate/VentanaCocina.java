package proyecto_clienteservidor_jeremyulate;
import javax.swing.*;
import java.awt.*;
public class VentanaCocina extends JFrame{

    public VentanaCocina(){

        setTitle("Cocina");
        setSize(400,300);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblPendientes = new JLabel("Pendientes");
        lblPendientes.setBounds(20, 20, 100, 25);
        add(lblPendientes);

        JTextArea areaPendientes = new JTextArea();
        areaPendientes.setBounds(20, 50, 100, 120);
        add(areaPendientes);

        JLabel lblPreparacion = new JLabel("En preparación");
        lblPreparacion.setBounds(140, 20, 120, 25);
        add(lblPreparacion);

        JTextArea areaPrep = new JTextArea();
        areaPrep.setBounds(140, 50, 100, 120);
        add(areaPrep);

        JLabel lblListos = new JLabel("Listos");
        lblListos.setBounds(260, 20, 100, 25);
        add(lblListos);

        JTextArea areaListos = new JTextArea();
        areaListos.setBounds(260, 50, 100, 120);
        add(areaListos);

        JButton btnEstado = new JButton("Cambiar Estado");
        btnEstado.setBounds(50, 200, 130, 30);
        add(btnEstado);

        JButton btnPrioridad = new JButton("Priorizar");
        btnPrioridad.setBounds(200, 200, 120, 30);
        add(btnPrioridad);
        
        btnEstado.addActionListener(e -> {
        JOptionPane.showMessageDialog(null, "Pedido en preparación");
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
