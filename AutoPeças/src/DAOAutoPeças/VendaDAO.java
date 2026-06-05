package DAOAutoPeças;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ConAutoPeças.ConnectionFactory;

public class VendaDAO {

    public boolean registrarVenda(int clienteId, double total, String formaPagamento, List<Object[]> itensCarrinho) {
        Connection conn = null;
        PreparedStatement pstmVenda = null;
        PreparedStatement pstmItem = null;
        PreparedStatement pstmEstoque = null;
        ResultSet rs = null;

        String sqlVenda = "INSERT INTO vendas (cliente_id, total, forma_pagamento) VALUES (?, ?, ?)";
        String sqlItem = "INSERT INTO itens_venda (venda_id, peca_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        String sqlEstoque = "UPDATE pecas SET quantidade = quantidade - ? WHERE id = ?";

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false); // Ativa modo transação (ou faz tudo ou não faz nada)

            // 1. Salva a Venda principal
            pstmVenda = conn.prepareStatement(sqlVenda, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmVenda.setInt(1, clienteId);
            pstmVenda.setDouble(2, total);
            pstmVenda.setString(3, formaPagamento);
            pstmVenda.executeUpdate();

            // Pega o ID gerado automaticamente para essa venda
            rs = pstmVenda.getGeneratedKeys();
            int vendaId = -1;
            if (rs.next()) {
                vendaId = rs.getInt(1);
            }

            // 2. Salva os Itens e Atualiza o Estoque de cada um
            pstmItem = conn.prepareStatement(sqlItem);
            pstmEstoque = conn.prepareStatement(sqlEstoque);

            for (Object[] item : itensCarrinho) {
                int pecaId = (int) item[0];
                int qtdComprada = (int) item[2];
                double precoUnit = (double) item[3];

                // Cadastra o item da venda
                pstmItem.setInt(1, vendaId);
                pstmItem.setInt(2, pecaId);
                pstmItem.setInt(3, qtdComprada);
                pstmItem.setDouble(4, precoUnit);
                pstmItem.addBatch();

                // Prepara a baixa do estoque da peça
                pstmEstoque.setInt(1, qtdComprada);
                pstmEstoque.setInt(2, pecaId);
                pstmEstoque.addBatch();
            }

            pstmItem.executeBatch();   // Executa todos os itens juntos
            pstmEstoque.executeBatch(); // Executa todas as baixas juntas

            conn.commit(); // Salva definitivamente no SQLite
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            return false;
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmVenda != null) pstmVenda.close(); } catch (Exception e) {}
            try { if (pstmItem != null) pstmItem.close(); } catch (Exception e) {}
            try { if (pstmEstoque != null) pstmEstoque.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
    public List<Object[]> listarVendasPorCaixa(int idCaixa) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Object[]> lista = new ArrayList<>();
        
        // Busca as vendas que batem com o período de abertura/fechamento do caixa selecionado
        String sql = "SELECT v.id, c.nome, v.data_venda, v.total, v.forma_pagamento " +
                     "FROM vendas v " +
                     "JOIN clientes c ON v.cliente_id = c.id " +
                     "WHERE v.data_venda >= (SELECT data_abertura FROM controle_caixa WHERE id = ?) " +
                     "AND (v.data_venda <= (SELECT data_fechamento FROM controle_caixa WHERE id = ?) " +
                     "     OR (SELECT status FROM controle_caixa WHERE id = ?) = 'ABERTO') " +
                     "ORDER BY v.data_venda DESC";

        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, idCaixa);
            pstm.setInt(2, idCaixa);
            pstm.setInt(3, idCaixa);
            rs = pstm.executeQuery();

            while (rs.next()) {
                lista.add(new Object[] {
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("data_venda"),
                    rs.getDouble("total"),
                    rs.getString("forma_pagamento")
                });
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
    public List<Object[]> listarPeriodosCaixa() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id, data_abertura, status FROM controle_caixa ORDER BY id DESC";

        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id"),
                    rs.getString("data_abertura"),
                    rs.getString("status")
                });
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