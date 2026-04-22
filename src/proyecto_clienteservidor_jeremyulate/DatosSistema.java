package proyecto_clienteservidor_jeremyulate;
import java.util.ArrayList;
public class DatosSistema {

    public static ArrayList<Usuario> usuarios = new ArrayList<>();
    public static ArrayList<Producto> productos = new ArrayList<>();
    public static ArrayList<Pedido> pedidos = new ArrayList<>();

    private static int siguienteIdUsuario = 1;
    private static int siguienteIdProducto = 1;
    private static int siguienteIdPedido = 1;
    private static int siguienteIdFactura = 1;

    private static boolean inicializado = false;

    public static void inicializarDatos() {
        if (inicializado) {
            return;
        }

        usuarios.add(new Cajero( //ejemplos
                generarIdUsuario(),
                "Jeremy Ulate",
                "cajero",
                "123",
                "Cajero",
                "Sucursal Central"
        ));

        usuarios.add(new Usuario(
                generarIdUsuario(),
                "Karina Cocina",
                "cocina",
                "123",
                "Cocina"
        ));

        usuarios.add(new Usuario(
                generarIdUsuario(),
                "Admin",
                "admin",
                "123",
                "Administrador"
        ));

        Producto p1 = new Producto(
                generarIdProducto(),
                "Hamburguesa",
                2500,
                "Comida",
                true
        );

        Producto p2 = new Producto(
                generarIdProducto(),
                "Papas",
                1200,
                "Acompanamiento",
                true
        );

        Producto p3 = new Producto(
                generarIdProducto(),
                "Refresco",
                1000,
                "Bebida",
                true
        );

        productos.add(p1);
        productos.add(p2);
        productos.add(p3);

        Combo combo = new Combo(
                generarIdProducto(),
                "Combo clasico",
                "Combo",
                true
        );

        combo.agregarProducto(p1);
        combo.agregarProducto(p2);
        combo.agregarProducto(p3);

        productos.add(combo);

        inicializado = true;
    }

    public static Usuario validarLogin(String usuario, String contrasena) { //valida usuario y contrasena
        for (Usuario u : usuarios) {
            if (u.getUsuario().equalsIgnoreCase(usuario)
                    && u.getContrasena().equals(contrasena)) {
                return u;
            }
        }
        return null;
    }

    public static void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public static void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public static void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public static int generarIdUsuario() {
        return siguienteIdUsuario++;
    }

    public static int generarIdProducto() {
        return siguienteIdProducto++;
    }

    public static int generarIdPedido() {
        return siguienteIdPedido++;
    }

    public static int generarIdFactura() {
        return siguienteIdFactura++;
    }
}