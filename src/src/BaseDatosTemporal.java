import java.sql.*;

public class BaseDatosTemporal {
    public static void main(String[] args) {
        // Base de datos en memoria (solo para desarrollo)
        String url = "jdbc:h2:mem:banco;DB_CLOSE_DELAY=-1";

        try {
            Connection conn = DriverManager.getConnection(url, "sa", "");
            System.out.println("✅ Base de datos temporal creada");

            // Crear tabla de ejemplo
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE clientes (id INT PRIMARY KEY, nombre VARCHAR(50))");
            stmt.execute("INSERT INTO clientes VALUES (1, 'Cliente Temporal')");

            System.out.println("📊 Tabla creada con datos de ejemplo");
            conn.close();

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}