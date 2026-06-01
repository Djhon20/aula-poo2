package DAOAutoPeças;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ConAutoPeças.ConnectionFactory;

public class UsuarioDAO {

    // Método que retorna o Nome se encontrar o usuário e senha correspondentes
    public String autenticarUsuario(String login, String senha) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String nomeUsuario = null;

        String sql = "SELECT nome FROM usuarios WHERE login = ? AND senha = ?";

        try {
            conn = ConnectionFactory.getConnection(); 
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, login);
            pstm.setString(2, senha);

            rs = pstm.executeQuery();

            if (rs.next()) {
                nomeUsuario = rs.getString("nome");
                
                if (nomeUsuario == null || nomeUsuario.trim().isEmpty()) {
                    nomeUsuario = login;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstm != null) pstm.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }

        return nomeUsuario;
    }

    // Método para cadastrar um novo usuário no sistema
    public boolean cadastrarUsuario(String nome, String login, String senha) {
        Connection conn = null;
        PreparedStatement pstm = null;
        String sql = "INSERT INTO usuarios (nome, login, senha) VALUES (?, ?, ?)";
        
        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, nome);
            pstm.setString(2, login);
            pstm.setString(3, senha);
            pstm.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (pstm != null) pstm.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    // === ADICIONADO: Método que verifica se o Login já existe no SQLite ===
    public boolean usuarioExiste(String login) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        boolean existe = false;
        String sql = "SELECT 1 FROM usuarios WHERE login = ?";

        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, login);
            rs = pstm.executeQuery();

            if (rs.next()) {
                existe = true; // Achou um registro com esse login
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstm != null) pstm.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return existe;
    }

    // === ADICIONADO: Método que atualiza a senha no caso de redefinição ===
    public boolean atualizarSenha(String login, String novaSenha) {
        Connection conn = null;
        PreparedStatement pstm = null;
        boolean atualizou = false;
        String sql = "UPDATE usuarios SET senha = ? WHERE login = ?";

        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, novaSenha);
            pstm.setString(2, login);

            // executeUpdate retorna quantas linhas foram modificadas no banco
            int linhasAfetadas = pstm.executeUpdate(); 
            if (linhasAfetadas > 0) {
                atualizou = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (pstm != null) pstm.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return atualizou;
    }
}