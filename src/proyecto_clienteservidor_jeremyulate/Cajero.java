package proyecto_clienteservidor_jeremyulate;
public class Cajero extends Usuario {
    
    private String sucursal;

    public Cajero(int idUsuario, String nombre, String usuario, String contrasena, String rol, String sucursal) {
        super(idUsuario, nombre, usuario, contrasena, rol);
        this.sucursal = sucursal;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void crearPedido() {
    }

    public void generarFactura() {
    }

    @Override
    public String toString() {
        return getNombre() + " - " + getRol() + " - " + sucursal;
    }
}