package DAOAutoPeças;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ConAutoPeças.ConnectionFactory;

public class CaixaDAO {

    public int obterIdCaixaAberto() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int id = -1;
        String sql = "SELECT id FROM controle_caixa WHERE status = 'ABERTO'";

        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstm != null) pstm.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return id;
    }

    // Abre o caixa com um valor inicial de troco
    public boolean abrirCaixa(double valorInicial) {
        if (obterIdCaixaAberto() != -1) return false; // Já tem caixa aberto

        Connection conn = null;
        PreparedStatement pstm = null;
        String sql = "INSERT INTO controle_caixa (valor_inicial, status) VALUES (?, 'ABERTO')";

        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setDouble(1, valorInicial);
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

    // Calcula quanto dinheiro entrou em vendas enquanto este caixa esteve aberto
    public double calcularVendasDoCaixaAtual() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        double totalVendas = 0.0;
        
        // Soma as vendas que aconteceram depois da data de abertura do caixa atual
        String sql = "SELECT SUM(total) as total_vendas FROM vendas WHERE data_venda >= "
                   + "(SELECT data_abertura FROM controle_caixa WHERE status = 'ABERTO')";

        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                totalVendas = rs.getDouble("total_vendas");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstm != null) pstm.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return totalVendas;
    }

    // Fecha o caixa salvando o valor final acumulado e alterando o status
    public boolean fecharCaixa(int idCaixa, double valorFinal) {
        Connection conn = null;
        PreparedStatement pstm = null;
        String sql = "UPDATE controle_caixa SET valor_final = ?, status = 'FECHADO', data_fechamento = datetime('now', 'localtime') WHERE id = ?";

        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setDouble(1, valorFinal);
            pstm.setInt(2, idCaixa);
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