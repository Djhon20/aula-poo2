package DAOAutoPeças;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ConAutoPeças.ConnectionFactory;

public class ClienteDAO {

    // Salvar Cliente
    public boolean salvar(String nome, String cpf, String endereco, String telefone) {
        Connection conn = null;
        PreparedStatement pstm = null;
        String sql = "INSERT INTO clientes (nome, cpf, endereco, telefone) VALUES (?, ?, ?, ?)";

        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, nome);
            pstm.setString(2, cpf);
            pstm.setString(3, endereco);
            pstm.setString(4, telefone);
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

    // Editar Cliente
    public boolean editar(int id, String nome, String cpf, String endereco, String telefone) {
        Connection conn = null;
        PreparedStatement pstm = null;
        String sql = "UPDATE clientes SET nome = ?, cpf = ?, endereco = ?, telefone = ? WHERE id = ?";

        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, nome);
            pstm.setString(2, cpf);
            pstm.setString(3, endereco);
            pstm.setString(4, telefone);
            pstm.setInt(5, id);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (pstm != null) pstm.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    // Buscar Clientes por Nome (Filtro Dinâmico)
    public List<Object[]> buscarPorNome(String nomeBusca) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Object[]> lista = new ArrayList<>();
        // O LIKE %?% permite buscar por partes do nome (ex: digita "Silva" e acha "João Silva")
        String sql = "SELECT * FROM clientes WHERE nome LIKE ?";

        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, "%" + nomeBusca + "%");
            rs = pstm.executeQuery();

            while (rs.next()) {
                Object[] linha = new Object[] {
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("endereco"),
                    rs.getString("telefone")
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
}