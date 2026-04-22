package proyecto_clienteservidor_jeremyulate;
public class Usuario {
    
    private int idUsuario;
    private String nombre;
    private String usuario;
    private String contrasena;
    private String rol;

    public Usuario(int idUsuario, String nombre, String usuario, String contrasena, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void iniciarSesion() {
    }

    public void cerrarSesion() {
    }

    @Override
    public String toString() {
        return nombre + " - " + rol;
    }
}