package proyecto_clienteservidor_jeremyulate;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class ManejadorCliente extends Thread{
    private Socket socket;

    public ManejadorCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String mensaje = entrada.readLine();
            String respuesta = procesarMensaje(mensaje);
            salida.println(respuesta);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String procesarMensaje(String mensaje) {
        try {
            String[] partes = mensaje.split("\\|");
            String comando = partes[0];

            switch (comando) {
                case "LOGIN":
                    return login(partes[1], partes[2]);

                case "LISTAR_PRODUCTOS":
                    return listarProductos();

                case "GUARDAR_PEDIDO":
                    return guardarPedido(Integer.parseInt(partes[1]), partes[2]);

                case "LISTAR_PEDIDOS":
                    return listarPedidos();

                case "CAMBIAR_ESTADO":
                    return cambiarEstado(Integer.parseInt(partes[1]), partes[2]);

                case "PRIORIZAR_PEDIDO":
                    return priorizarPedido(Integer.parseInt(partes[1]));

                case "GENERAR_FACTURA":
                    return generarFacturaTexto(Integer.parseInt(partes[1]));

                case "EXPORTAR_FACTURA_TXT":
                    return exportarFacturaTxt(Integer.parseInt(partes[1]));

                case "REGISTRAR_PRODUCTO":
                    return registrarProducto(partes[1], Double.parseDouble(partes[2]), partes[3]);

                case "REGISTRAR_CAJERO":
                    return registrarCajero(partes[1], partes[2], partes[3], partes[4]);

                case "REGISTRAR_COMBO":
                    return registrarCombo(partes[1], partes[2]);

                default:
                    return "ERROR|Comando no reconocido";
            }
        } catch (Exception e) {
            return "ERROR|" + e.getMessage();
        }
    }

    private String login(String usuario, String contrasena) throws Exception {
        try (Connection con = ConexionBD.getConnection()) {
            String sql = "SELECT id, nombre, rol FROM usuarios WHERE usuario = ? AND contrasena = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return "OK|" + rs.getInt("id") + "|" + rs.getString("rol") + "|" + rs.getString("nombre");
            }

            return "ERROR|Credenciales invalidas";
        }
    }

    private String listarProductos() throws Exception {
        StringBuilder sb = new StringBuilder();

        try (Connection con = ConexionBD.getConnection()) {
            String sql = "SELECT * FROM productos WHERE disponible = true ORDER BY id";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                sb.append(rs.getInt("id")).append(",")
                  .append(rs.getString("nombre")).append(",")
                  .append(rs.getDouble("precio")).append(",")
                  .append(rs.getString("categoria")).append(",")
                  .append(rs.getBoolean("disponible")).append(",")
                  .append(rs.getString("tipo")).append(";");
            }
        }

        return "OK|" + sb.toString();
    }

    private String guardarPedido(int idUsuario, String idsProductos) throws Exception {
        try (Connection con = ConexionBD.getConnection()) {
            String sqlPedido = "INSERT INTO pedidos(usuario_id, estado, prioridad) VALUES(?, 'Pendiente', 0)";
            PreparedStatement psPedido = con.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            psPedido.setInt(1, idUsuario);
            psPedido.executeUpdate();

            ResultSet rs = psPedido.getGeneratedKeys();
            rs.next();
            int idPedido = rs.getInt(1);

            String[] ids = idsProductos.split(",");
            for (String id : ids) {
                String sqlDetalle = "INSERT INTO pedido_productos(pedido_id, producto_id) VALUES(?, ?)";
                PreparedStatement psDetalle = con.prepareStatement(sqlDetalle);
                psDetalle.setInt(1, idPedido);
                psDetalle.setInt(2, Integer.parseInt(id));
                psDetalle.executeUpdate();
            }

            return "OK|" + idPedido;
        }
    }

    private String listarPedidos() throws Exception {
        StringBuilder sb = new StringBuilder();

        try (Connection con = ConexionBD.getConnection()) {
            String sql = """
                SELECT p.id, p.estado, p.prioridad, COALESCE(SUM(pr.precio),0) AS total
                FROM pedidos p
                LEFT JOIN pedido_productos pp ON p.id = pp.pedido_id
                LEFT JOIN productos pr ON pp.producto_id = pr.id
                GROUP BY p.id, p.estado, p.prioridad
                ORDER BY p.prioridad DESC, p.id ASC
            """;

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                sb.append(rs.getInt("id")).append(",")
                  .append(rs.getString("estado")).append(",")
                  .append(rs.getDouble("total")).append(";");
            }
        }

        return "OK|" + sb.toString();
    }

    private String cambiarEstado(int idPedido, String estado) throws Exception {
        try (Connection con = ConexionBD.getConnection()) {
            String sql = "UPDATE pedidos SET estado = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setInt(2, idPedido);
            ps.executeUpdate();
            return "OK|Estado actualizado";
        }
    }

    private String priorizarPedido(int idPedido) throws Exception {
        try (Connection con = ConexionBD.getConnection()) {
            Statement st = con.createStatement();
            st.executeUpdate("UPDATE pedidos SET prioridad = 0");
            PreparedStatement ps = con.prepareStatement("UPDATE pedidos SET prioridad = 1 WHERE id = ?");
            ps.setInt(1, idPedido);
            ps.executeUpdate();
            return "OK|Pedido priorizado";
        }
    }

    private String generarFacturaTexto(int idPedido) throws Exception {
        crearFacturaSiNoExiste(idPedido);

        try (Connection con = ConexionBD.getConnection()) {
            String sqlFactura = "SELECT * FROM facturas WHERE pedido_id = ?";
            PreparedStatement psFactura = con.prepareStatement(sqlFactura);
            psFactura.setInt(1, idPedido);
            ResultSet rsFactura = psFactura.executeQuery();

            if (!rsFactura.next()) {
                return "ERROR|No se pudo generar factura";
            }

            StringBuilder texto = new StringBuilder();
            texto.append("FACTURA##");
            texto.append("Id Factura: ").append(rsFactura.getInt("id")).append("##");
            texto.append("Pedido #: ").append(idPedido).append("##");
            texto.append("Fecha: ").append(rsFactura.getTimestamp("fecha")).append("####");

            String sqlProductos = """
                SELECT pr.nombre, pr.precio
                FROM pedido_productos pp
                INNER JOIN productos pr ON pp.producto_id = pr.id
                WHERE pp.pedido_id = ?
            """;

            PreparedStatement psProductos = con.prepareStatement(sqlProductos);
            psProductos.setInt(1, idPedido);
            ResultSet rsProductos = psProductos.executeQuery();

            while (rsProductos.next()) {
                texto.append(rsProductos.getString("nombre"))
                     .append(" - ₡")
                     .append(rsProductos.getDouble("precio"))
                     .append("##");
            }

            texto.append("##Subtotal: ₡").append(rsFactura.getDouble("subtotal")).append("##");
            texto.append("Impuestos: ₡").append(rsFactura.getDouble("impuestos")).append("##");
            texto.append("Total: ₡").append(rsFactura.getDouble("total"));

            return "OK|" + texto.toString();
        }
    }

    private String exportarFacturaTxt(int idPedido) throws Exception {
        String respuesta = generarFacturaTexto(idPedido);

        if (!respuesta.startsWith("OK|")) {
            return respuesta;
        }

        String texto = respuesta.substring(3).replace("##", System.lineSeparator());
        String nombreArchivo = "factura_pedido_" + idPedido + ".txt";

        java.io.PrintWriter writer = new java.io.PrintWriter(nombreArchivo, "UTF-8");
        writer.println(texto);
        writer.close();

        return "OK|Factura exportada";
    }

    private String registrarProducto(String nombre, double precio, String categoria) throws Exception {
        try (Connection con = ConexionBD.getConnection()) {
            String sql = "INSERT INTO productos(nombre, precio, categoria, disponible, tipo) VALUES(?, ?, ?, true, 'Producto')";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setString(3, categoria);
            ps.executeUpdate();
            return "OK|Producto registrado";
        }
    }

    private String registrarCajero(String nombre, String usuario, String contrasena, String sucursal) throws Exception {
        try (Connection con = ConexionBD.getConnection()) {
            String sql = "INSERT INTO usuarios(nombre, usuario, contrasena, rol, sucursal) VALUES(?, ?, ?, 'Cajero', ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, usuario);
            ps.setString(3, contrasena);
            ps.setString(4, sucursal);
            ps.executeUpdate();
            return "OK|Cajero registrado";
        }
    }

    private String registrarCombo(String nombre, String idsProductos) throws Exception {
        try (Connection con = ConexionBD.getConnection()) {
            double total = 0;
            String[] ids = idsProductos.split(",");

            for (String id : ids) {
                PreparedStatement psPrecio = con.prepareStatement("SELECT precio FROM productos WHERE id = ?");
                psPrecio.setInt(1, Integer.parseInt(id));
                ResultSet rs = psPrecio.executeQuery();
                if (rs.next()) {
                    total += rs.getDouble("precio");
                }
            }

            String sqlCombo = "INSERT INTO productos(nombre, precio, categoria, disponible, tipo) VALUES(?, ?, 'Combo', true, 'Combo')";
            PreparedStatement psCombo = con.prepareStatement(sqlCombo, Statement.RETURN_GENERATED_KEYS);
            psCombo.setString(1, nombre);
            psCombo.setDouble(2, total);
            psCombo.executeUpdate();

            ResultSet rsKey = psCombo.getGeneratedKeys();
            rsKey.next();
            int comboId = rsKey.getInt(1);

            for (String id : ids) {
                PreparedStatement psDetalle = con.prepareStatement("INSERT INTO combos_productos(combo_id, producto_id) VALUES(?, ?)");
                psDetalle.setInt(1, comboId);
                psDetalle.setInt(2, Integer.parseInt(id));
                psDetalle.executeUpdate();
            }

            return "OK|Combo registrado";
        }
    }

    private void crearFacturaSiNoExiste(int idPedido) throws Exception {
        try (Connection con = ConexionBD.getConnection()) {
            PreparedStatement psExiste = con.prepareStatement("SELECT id FROM facturas WHERE pedido_id = ?");
            psExiste.setInt(1, idPedido);
            ResultSet rsExiste = psExiste.executeQuery();

            if (rsExiste.next()) {
                return;
            }

            PreparedStatement psTotal = con.prepareStatement("""
                SELECT COALESCE(SUM(pr.precio),0) AS subtotal
                FROM pedido_productos pp
                INNER JOIN productos pr ON pp.producto_id = pr.id
                WHERE pp.pedido_id = ?
            """);
            psTotal.setInt(1, idPedido);
            ResultSet rsTotal = psTotal.executeQuery();
            rsTotal.next();

            double subtotal = rsTotal.getDouble("subtotal");
            double impuestos = subtotal * 0.13;
            double total = subtotal + impuestos;

            PreparedStatement psInsert = con.prepareStatement(
                "INSERT INTO facturas(pedido_id, subtotal, impuestos, total) VALUES(?, ?, ?, ?)"
            );
            psInsert.setInt(1, idPedido);
            psInsert.setDouble(2, subtotal);
            psInsert.setDouble(3, impuestos);
            psInsert.setDouble(4, total);
            psInsert.executeUpdate();
        }
    }
}
