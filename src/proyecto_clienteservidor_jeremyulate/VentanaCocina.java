package proyecto_clienteservidor_jeremyulate;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class VentanaCocina extends JFrame{

    private DefaultListModel<String> modeloPendientes;
    private DefaultListModel<String> modeloPreparacion;
    private DefaultListModel<String> modeloListos;

    private JList<String> listaPendientes;
    private JList<String> listaPreparacion;
    private JList<String> listaListos;

    private GestorCocina gestor;

    public VentanaCocina() {
        gestor = new GestorCocina();

        setTitle("Monitor de Cocina");
        setSize(750, 360);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblPendientes = new JLabel("Pendientes");
        lblPendientes.setBounds(60, 20, 100, 25);
        add(lblPendientes);

        JLabel lblPreparacion = new JLabel("En Preparacion");
        lblPreparacion.setBounds(300, 20, 120, 25);
        add(lblPreparacion);

        JLabel lblListos = new JLabel("Listos");
        lblListos.setBounds(560, 20, 100, 25);
        add(lblListos);

        modeloPendientes = new DefaultListModel<>();
        modeloPreparacion = new DefaultListModel<>();
        modeloListos = new DefaultListModel<>();

        listaPendientes = new JList<>(modeloPendientes);
        listaPreparacion = new JList<>(modeloPreparacion);
        listaListos = new JList<>(modeloListos);

        JScrollPane scroll1 = new JScrollPane(listaPendientes);
        scroll1.setBounds(30, 50, 180, 180);
        add(scroll1);

        JScrollPane scroll2 = new JScrollPane(listaPreparacion);
        scroll2.setBounds(270, 50, 180, 180);
        add(scroll2);

        JScrollPane scroll3 = new JScrollPane(listaListos);
        scroll3.setBounds(510, 50, 180, 180);
        add(scroll3);

        JButton btnEstado = new JButton("Cambiar Estado");
        btnEstado.setBounds(130, 260, 140, 30);
        add(btnEstado);

        JButton btnPriorizar = new JButton("Priorizar Pedido");
        btnPriorizar.setBounds(300, 260, 150, 30);
        add(btnPriorizar);

        JButton btnListo = new JButton("Marcar Listo");
        btnListo.setBounds(490, 260, 140, 30);
        add(btnListo);

        btnEstado.addActionListener(e -> {
            try {
                Integer idPedido = obtenerIdSeleccionado();
                if (idPedido == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione un pedido");
                    return;
                }

                if (listaPendientes.getSelectedValue() != null) {
                    gestor.cambiarEstado(idPedido, "En preparacion");
                } else if (listaPreparacion.getSelectedValue() != null) {
                    gestor.cambiarEstado(idPedido, "Listo");
                }

                actualizarListas();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al cambiar estado");
            }
        });

        btnPriorizar.addActionListener(e -> {
            try {
                Integer idPedido = obtenerIdDesdeTexto(listaPendientes.getSelectedValue());
                if (idPedido == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione un pedido pendiente");
                    return;
                }

                gestor.priorizarPedido(idPedido);
                actualizarListas();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al priorizar pedido");
            }
        });

        btnListo.addActionListener(e -> {
            try {
                Integer idPedido = obtenerIdDesdeTexto(listaPreparacion.getSelectedValue());
                if (idPedido == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione un pedido en preparacion");
                    return;
                }

                gestor.cambiarEstado(idPedido, "Listo");
                actualizarListas();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al marcar pedido listo");
            }
        });

        actualizarListas();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void actualizarListas() {
        modeloPendientes.clear();
        modeloPreparacion.clear();
        modeloListos.clear();

        try {
            ArrayList<String> pedidos = gestor.listarPedidos();

            for (String p : pedidos) {
                String[] campos = p.split(",");
                int id = Integer.parseInt(campos[0]);
                String estado = campos[1];
                String total = campos[2];

                String texto = "Pedido #" + id + " - " + estado + " - ₡" + total;

                if (estado.equals("Pendiente")) {
                    modeloPendientes.addElement(texto);
                } else if (estado.equals("En preparacion")) {
                    modeloPreparacion.addElement(texto);
                } else if (estado.equals("Listo")) {
                    modeloListos.addElement(texto);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar pedidos");
        }
    }

    private Integer obtenerIdSeleccionado() {
        if (listaPendientes.getSelectedValue() != null) {
            return obtenerIdDesdeTexto(listaPendientes.getSelectedValue());
        }
        if (listaPreparacion.getSelectedValue() != null) {
            return obtenerIdDesdeTexto(listaPreparacion.getSelectedValue());
        }
        if (listaListos.getSelectedValue() != null) {
            return obtenerIdDesdeTexto(listaListos.getSelectedValue());
        }
        return null;
    }

    private Integer obtenerIdDesdeTexto(String texto) {
        if (texto == null) {
            return null;
        }

        try {
            String numero = texto.split(" ")[1].replace("#", "");
            return Integer.parseInt(numero);
        } catch (Exception e) {
            return null;
        }
    }
}