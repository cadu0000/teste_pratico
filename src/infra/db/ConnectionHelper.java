package infra.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionHelper {
    private static final Dotenv dotenv = Dotenv.load();

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        final String url = dotenv.get("DB_URL");
        final String user = dotenv.get("USER_LOGIN");
        final String password = dotenv.get("USER_PASSWORD");
        final String driver = dotenv.get("DB_DRIVER");

        if (url == null || user == null || password == null || driver == null) {
            throw new IllegalStateException("Configurações do banco de dados não encontradas no arquivo .env");
        }

        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão estabelecida com sucesso.");
            return connection;
        } catch (SQLException e) {
            System.out.println("Erro ao estabelecer conexão com o banco de dados: " + e.getMessage());
            throw e;
        } catch (ClassNotFoundException e) {
            System.out.println("Driver não encontrado: " + e.getMessage());
            throw e;
        }
    }
}
