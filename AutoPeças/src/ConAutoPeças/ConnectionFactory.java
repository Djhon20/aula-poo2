package ConAutoPeças;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    // A 'direção' do banco de dados (Atenção à porta 3306 ou 3307)
    private static final String URL = "jdbc:mysql://localhost:3306/escola_db";
    private static final String USER = "root";
    private static final String PASS = ""; 
    private static Connection conexao;

    public static Connection getConexao() throws SQLException {
        if (conexao == null || conexao.isClosed()) {
            conexao = DriverManager.getConnection(URL, USER, PASS);
        }
        return conexao;
    }
}