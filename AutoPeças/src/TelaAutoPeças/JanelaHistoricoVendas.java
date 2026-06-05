package TelaAutoPeças;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import DAOAutoPeças.VendaDAO;

public class JanelaHistoricoVendas extends JDialog {
    private JComboBox<String> cbPeriodos;
    private JTable tabelaHist;
    private DefaultTableModel modeloHist;
    private List<Integer> listaIdsCaixa = new ArrayList<>();

    public JanelaHistoricoVendas(Frame parent) {
        super(parent, "Histórico de Vendas por Período de Caixa", true);
        setSize(900, 500);
        setLocationRelativeTo(parent);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 245, 245));

        // Filtro Superior
        JLabel lblFiltro = new JLabel("Selecionar Turno de Caixa:");
        lblFiltro.setBounds(30, 20, 180, 25);
        add(lblFiltro);

        cbPeriodos = new JComboBox<>();
        cbPeriodos.setBounds(210, 20, 450, 25);
        add(cbPeriodos);

        JButton btnFiltrar = new JButton("🔍 Buscar Vendas");
        btnFiltrar.setBounds(680, 20, 160, 25);
        add(btnFiltrar);

        // Tabela de Histórico
        JScrollPane scroll = new JScrollPane();
        scroll.setBounds(30, 70, 824, 360);
        add(scroll);

        modeloHist = new DefaultTableModel(
            new Object[][] {},
            new String[] { "Nº Venda", "Cliente", "Data/Hora da Venda", "Total Geral", "Forma Pagto" }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabelaHist = new JTable(modeloHist);
        scroll.setViewportView(tabelaHist);

        // Carrega os caixas existentes no ComboBox
        carregarCombosCaixa();

        // Ação do botão buscar
        btnFiltrar.addActionListener(e -> {
            int index = cbPeriodos.getSelectedIndex();
            if (index >= 0) {
                int idCaixa = listaIdsCaixa.get(index);
                atualizarTabela(idCaixa);
            }
        });
    }

    private void carregarCombosCaixa() {
        cbPeriodos.removeAllItems();
        listaIdsCaixa.clear();
        VendaDAO dao = new VendaDAO();
        List<Object[]> periodos = dao.listarPeriodosCaixa();

        for (Object[] p : periodos) {
            listaIdsCaixa.add((Integer) p[0]);
            cbPeriodos.addItem("Caixa #" + p[0] + " - Aberto em: " + p[1] + " [" + p[2] + "]");
        }
    }

    private void atualizarTabela(int idCaixa) {
        modeloHist.setRowCount(0);
        VendaDAO dao = new VendaDAO();
        List<Object[]> vendas = dao.listarVendasPorCaixa(idCaixa);
        for (Object[] v : vendas) {
            modeloHist.addRow(v);
        }
        if(vendas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma venda realizada neste turno de caixa.");
        }
    }
}
