import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/spring";
        String username = "sebas";
        String password = "NenayMilo_2024";

        System.out.println("Intentando conectar a MySQL...");
        System.out.println("URL: " + url);
        System.out.println("Usuario: " + username);

        try {
            // Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✓ Driver MySQL cargado correctamente");

            // Intentar conexión
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("✓ ¡CONEXIÓN EXITOSA!");
            System.out.println("✓ Base de datos: " + connection.getCatalog());

            connection.close();
            System.out.println("✓ Conexión cerrada correctamente");

        } catch (ClassNotFoundException e) {
            System.err.println("✗ ERROR: No se encontró el driver MySQL");
            System.err.println("  Asegúrate de que mysql-connector-j esté en el classpath");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("✗ ERROR DE CONEXIÓN:");
            System.err.println("  Código: " + e.getErrorCode());
            System.err.println("  Mensaje: " + e.getMessage());

            if (e.getMessage().contains("Access denied")) {
                System.err.println("\n  → Usuario o contraseña incorrectos");
            } else if (e.getMessage().contains("Unknown database")) {
                System.err.println("\n  → La base de datos 'spring' no existe");
                System.err.println("  → Ejecuta: CREATE DATABASE spring;");
            } else if (e.getMessage().contains("Communications link failure")) {
                System.err.println("\n  → MySQL no está corriendo o no está en el puerto 3306");
            }

            e.printStackTrace();
        }
    }
}
