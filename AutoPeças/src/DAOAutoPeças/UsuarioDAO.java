package DAOAutoPeças;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ConAutoPeças.ConnectionFactory;

public class UsuarioDAO {

    // Método simples que retorna verdadeiro se encontrar o usuário e senha no banco
    public boolean autenticarUsuario(String login, String senha) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        boolean existe = false;

        // SQL direto comparando usuário e senha em texto limpo
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";

        try {
            conn = ConnectionFactory.getConnection(); // Abre a conexão usando sua factory
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, login);
            pstm.setString(2, senha);

            rs = pstm.executeQuery();

            // Se o rs.next() for verdadeiro, significa que achou uma linha correspondente
            if (rs.next()) {
                existe = true; 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fecha os recursos da conexão (ajuste se sua ConnectionFactory tiver fecharConexao)
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstm != null) pstm.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }

        return existe;
    }
}