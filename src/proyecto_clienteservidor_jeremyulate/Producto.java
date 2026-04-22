package proyecto_clienteservidor_jeremyulate;
public class Producto {
    
    protected int idProducto;
    protected String nombre;
    protected double precio;
    protected String categoria;
    protected boolean disponible;
    protected String tipo;

    public Producto(int idProducto, String nombre, double precio, String categoria, boolean disponible) {
        this(idProducto, nombre, precio, categoria, disponible, "Producto");
    }

    public Producto(int idProducto, String nombre, double precio, String categoria, boolean disponible, String tipo) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.disponible = disponible;
        this.tipo = tipo;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public String getTipo() {
        return tipo;
    }

    public void cambiarDisponibilidad(boolean disponible) {
        this.disponible = disponible;
    }

    public void actualizarPrecio(double precio) {
        this.precio = precio;
    }

    public double calcularPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return nombre + " - ₡" + calcularPrecio();
    }
}