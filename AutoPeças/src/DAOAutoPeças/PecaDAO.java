package DAOAutoPeças;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ConAutoPeças.ConnectionFactory;

// Esta é a classe modelo para transferir os dados da tabela
public class PecaDAO {

    // 1. CREATE - Salvar Nova Peça
    public boolean salvar(String nome, int quantidade, double preco) {
        Connection conn = null;
        PreparedStatement pstm = null;
        String sql = "INSERT INTO pecas (nome, quantidade, preco) VALUES (?, ?, ?)";

        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, nome);
            pstm.setInt(2, quantidade);
            pstm.setDouble(3, preco);
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

    // 2. READ - Listar Todas as Peças do Banco
    public List<Object[]> listarTodos() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT * FROM pecas";

        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            while (rs.next()) {
                Object[] linha = new Object[] {
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("quantidade"),
                    rs.getDouble("preco")
                };
                lista.add(linha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstm != null) pstm.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return lista;
    }

    // 3. UPDATE - Atualizar Peça Existente
    public boolean editar(int id, String nome, int quantidade, double preco) {
        Connection conn = null;
        PreparedStatement pstm = null;
        String sql = "UPDATE pecas SET nome = ?, quantidade = ?, preco = ? WHERE id = ?";

        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, nome);
            pstm.setInt(2, quantidade);
            pstm.setDouble(3, preco);
            pstm.setInt(4, id);
            
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (pstm != null) pstm.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    // 4. DELETE - Excluir Peça do Banco
    public boolean excluir(int id) {
        Connection conn = null;
        PreparedStatement pstm = null;
        String sql = "DELETE FROM pecas WHERE id = ?";

        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (pstm != null) pstm.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}