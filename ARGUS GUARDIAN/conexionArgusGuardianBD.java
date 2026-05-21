import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class conexionArgusGuardianBD {
    private static final String CONFIG_FILE = "db.properties";
    private static final Properties CONFIG = loadConfig();

    private static final String URL = CONFIG.getProperty("db.url", "jdbc:mysql://localhost:3306/argus_guardian");
    private static final String USER = CONFIG.getProperty("db.user", "root");
    private static final String PASSWORD = CONFIG.getProperty("db.password", "");

    private static Properties loadConfig() {
        Properties props = new Properties();

        // Cargar archivo de configuración externo si existe.
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            props.load(fis);
        } catch (IOException ignored) {
            // Si no existe el archivo, se usan variables de entorno o valores por defecto.
        }

        // Permitir sobreescritura mediante variables de entorno para evitar credenciales en el repositorio.
        String envUrl = System.getenv("DB_URL");
        if (envUrl != null && !envUrl.isBlank()) {
            props.setProperty("db.url", envUrl);
        }

        String envUser = System.getenv("DB_USER");
        if (envUser != null && !envUser.isBlank()) {
            props.setProperty("db.user", envUser);
        }

        String envPassword = System.getenv("DB_PASSWORD");
        if (envPassword != null && !envPassword.isBlank()) {
            props.setProperty("db.password", envPassword);
        }

        return props;
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: No se encontró el driver 'com.mysql.cj.jdbc.Driver'.");
            System.err.println("Asegúrate de que MySQL Connector/J esté en el classpath.");
            e.printStackTrace();
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
