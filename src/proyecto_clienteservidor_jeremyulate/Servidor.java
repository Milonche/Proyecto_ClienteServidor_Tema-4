package proyecto_clienteservidor_jeremyulate;
import java.net.ServerSocket;
import java.net.Socket;
public class Servidor {
        public static void main(String[] args) {
        int puerto = 5000;

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado en el puerto " + puerto);

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado");
                new ManejadorCliente(cliente).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}