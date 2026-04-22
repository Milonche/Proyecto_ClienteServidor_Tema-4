package proyecto_clienteservidor_jeremyulate;
import java.sql.Connection;
import java.sql.DriverManager;
public class ConexionBD {
    
    private static final String URL = "jdbc:mysql://localhost:3306/fideburguesas";
    private static final String USER = "root";
    private static final String PASSWORD = "1234"; // cambia esto por tu contraseña real

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}