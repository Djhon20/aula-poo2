package TelaAutoPeças;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import DAOAutoPeças.ClienteDAO;

public class ModalCliente extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTextField txtNome, txtCpf, txtEndereco, txtTelefone;
    private JButton btnAcao;
    private boolean salvouComSucesso = false;
    private JButton btnExcluir;
    
    private int idClienteEdicao = -1; // Se continuar -1 significa que é Cadastro

    // Construtor do Modal
    public ModalCliente(JFrame pai, String titulo) {
        super(pai, titulo, true); // O "true" ativa o modo Modal (bloqueia a tela de trás)
        setBounds(100, 100, 400, 359);
        setLocationRelativeTo(pai);
        setResizable(false);
        
        JPanel pnlForm = new JPanel();
        pnlForm.setBackground(new Color(245, 245, 250));
        pnlForm.setLayout(null);
        getContentPane().add(pnlForm, BorderLayout.CENTER);

        JLabel lblNome = new JLabel("Nome Completo:");
        lblNome.setBounds(40, 30, 120, 20);
        pnlForm.add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(40, 55, 300, 25);
        pnlForm.add(txtNome);

        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setBounds(40, 95, 120, 20);
        pnlForm.add(lblCpf);

        txtCpf = new JTextField();
        txtCpf.setBounds(40, 120, 300, 25);
        pnlForm.add(txtCpf);

        JLabel lblEndereco = new JLabel("Endereço:");
        lblEndereco.setBounds(40, 160, 120, 20);
        pnlForm.add(lblEndereco);

        txtEndereco = new JTextField();
        txtEndereco.setBounds(40, 185, 300, 25);
        pnlForm.add(txtEndereco);

        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setBounds(40, 225, 120, 20);
        pnlForm.add(lblTelefone);

        txtTelefone = new JTextField();
        txtTelefone.setBounds(40, 250, 150, 25);
        pnlForm.add(txtTelefone);

        btnAcao = new JButton("Salvar");
        btnAcao.setFont(new Font("Arial", Font.BOLD, 12));
        btnAcao.setBounds(240, 250, 100, 25);
        pnlForm.add(btnAcao);

        // Lógica do botão Salvar do Modal
        btnAcao.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String cpf = txtCpf.getText().trim();
            String end = txtEndereco.getText().trim();
            String tel = txtTelefone.getText().trim();

            if (nome.isEmpty() || cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            

            ClienteDAO dao = new ClienteDAO();
            boolean ok;

            if (idClienteEdicao == -1) {
                ok = dao.salvar(nome, cpf, end, tel); // Modo Cadastro
            } else {
                ok = dao.editar(idClienteEdicao, nome, cpf, end, tel); // Modo Edição
            }

            if (ok) {
                salvouComSucesso = true;
                dispose(); // Fecha o modal
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao processar dados. Verifique o CPF.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnExcluir = new JButton("Excluir Cliente");
        btnExcluir.setBackground(new Color(180, 50, 50));
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setFont(new Font("Arial", Font.BOLD, 11));
        btnExcluir.setBounds(124, 286, 120, 25); // Posicionado no canto esquerdo inferior
        btnExcluir.setVisible(false); // NASCE OCULTO (só aparece se for Edição)
        pnlForm.add(btnExcluir);

        // Lógica do botão Excluir do Modal
        btnExcluir.addActionListener(e -> {
            int confirmacao = JOptionPane.showConfirmDialog(this, 
                "Deseja realmente excluir este cliente em definitivo?", 
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmacao == JOptionPane.YES_OPTION) {
                ClienteDAO dao = new ClienteDAO();
                if (dao.excluir(idClienteEdicao)) {
                    salvouComSucesso = true; // Ativamos para forçar a atualização da tabela principal
                    dispose(); // Fecha o modal
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao tentar excluir o cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Método para preparar o modal em Modo Edição preenchendo os campos
    public void prepararParaEdicao(int id, String nome, String cpf, String endereco, String telefone) {
        this.idClienteEdicao = id;
        txtNome.setText(nome);
        txtCpf.setText(cpf);
        txtEndereco.setText(endereco);
        txtTelefone.setText(telefone);
        btnAcao.setText("Atualizar");
        btnExcluir.setVisible(true);
    }

    public boolean isSalvouComSucesso() {
        return salvouComSucesso;
    }
    
}