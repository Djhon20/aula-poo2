package TelaAutoPeças;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import DAOAutoPeças.UsuarioDAO;

public class TelaLogin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    // === Campos da tela de Login ===
    private JTextField txtUsuario;
    private JPasswordField txtSenha;

    // === Campos da tela de Cadastro ===
    private JTextField txtCadNome;
    private JTextField txtCadUsuario;
    private JPasswordField txtCadSenha;
    private JPasswordField txtCadConfirmaSenha;

    // === Campos da tela de Esqueci Senha ===
    private JTextField txtRecuperaUsuario;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaLogin frame = new TelaLogin();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TelaLogin() {
        setTitle("Sistema AutoPeças - Acesso");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 420, 420);
        setLocationRelativeTo(null);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 240, 245));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        // ── CardLayout principal ──────────────────────────────────────────────
        CardLayout cartoes = new CardLayout(0, 0);
        contentPane.setLayout(cartoes);

        // =====================================================================
        // CARD 1 – LOGIN
        // =====================================================================
        JPanel pnlLogin = new JPanel();
        pnlLogin.setBackground(new Color(255, 240, 245));
        pnlLogin.setLayout(null);
        contentPane.add(pnlLogin, "tela_login");

        JLabel lblTituloLogin = new JLabel("SISTEMA AUTOPEÇAS");
        lblTituloLogin.setFont(new Font("Arial", Font.BOLD, 16));
        lblTituloLogin.setBounds(100, 20, 230, 25);
        pnlLogin.add(lblTituloLogin);

        JLabel lblUsuario = new JLabel("Usuário:");
        lblUsuario.setBounds(50, 80, 70, 20);
        pnlLogin.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(130, 80, 210, 25);
        txtUsuario.setBackground(new Color(250, 240, 230));
        txtUsuario.setColumns(10);
        pnlLogin.add(txtUsuario);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(50, 120, 70, 20);
        pnlLogin.add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(130, 120, 210, 25);
        txtSenha.setBackground(new Color(250, 240, 230));
        pnlLogin.add(txtSenha);

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(130, 165, 210, 30);
        btnEntrar.setBackground(new Color(180, 100, 130));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 12));
        pnlLogin.add(btnEntrar);

        JButton btnIrCadastro = new JButton("Não tem conta? Cadastre-se");
        btnIrCadastro.setBounds(50, 210, 210, 25);
        btnIrCadastro.setBorderPainted(false);
        btnIrCadastro.setContentAreaFilled(false);
        btnIrCadastro.setForeground(new Color(100, 50, 150));
        pnlLogin.add(btnIrCadastro);

        JButton btnEsqueciSenha = new JButton("Esqueci minha senha");
        btnEsqueciSenha.setBounds(240, 210, 160, 25);
        btnEsqueciSenha.setBorderPainted(false);
        btnEsqueciSenha.setContentAreaFilled(false);
        btnEsqueciSenha.setForeground(new Color(100, 50, 150));
        pnlLogin.add(btnEsqueciSenha);

        // =====================================================================
        // CARD 2 – CADASTRO
        // =====================================================================
        JPanel pnlCadastro = new JPanel();
        pnlCadastro.setBackground(new Color(255, 240, 245));
        pnlCadastro.setLayout(null);
        contentPane.add(pnlCadastro, "tela_cadastro");

        JLabel lblTituloCadastro = new JLabel("CADASTRAR USUÁRIO");
        lblTituloCadastro.setFont(new Font("Arial", Font.BOLD, 15));
        lblTituloCadastro.setBounds(120, 20, 200, 25);
        pnlCadastro.add(lblTituloCadastro);

        JLabel lblCadNome = new JLabel("Nome:");
        lblCadNome.setBounds(50, 70, 70, 20);
        pnlCadastro.add(lblCadNome);

        txtCadNome = new JTextField();
        txtCadNome.setBounds(130, 70, 210, 25);
        txtCadNome.setBackground(new Color(250, 240, 230));
        pnlCadastro.add(txtCadNome);

        JLabel lblCadUsuario = new JLabel("Usuário:");
        lblCadUsuario.setBounds(50, 110, 70, 20);
        pnlCadastro.add(lblCadUsuario);

        txtCadUsuario = new JTextField();
        txtCadUsuario.setBounds(130, 110, 210, 25);
        txtCadUsuario.setBackground(new Color(250, 240, 230));
        pnlCadastro.add(txtCadUsuario);

        JLabel lblCadSenha = new JLabel("Senha:");
        lblCadSenha.setBounds(50, 150, 70, 20);
        pnlCadastro.add(lblCadSenha);

        txtCadSenha = new JPasswordField();
        txtCadSenha.setBounds(130, 150, 210, 25);
        txtCadSenha.setBackground(new Color(250, 240, 230));
        pnlCadastro.add(txtCadSenha);

        JLabel lblCadConfirma = new JLabel("Confirmar:");
        lblCadConfirma.setBounds(50, 190, 70, 20);
        pnlCadastro.add(lblCadConfirma);

        txtCadConfirmaSenha = new JPasswordField();
        txtCadConfirmaSenha.setBounds(130, 190, 210, 25);
        txtCadConfirmaSenha.setBackground(new Color(250, 240, 230));
        pnlCadastro.add(txtCadConfirmaSenha);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(130, 235, 210, 30);
        btnCadastrar.setBackground(new Color(180, 100, 130));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 12));
        pnlCadastro.add(btnCadastrar);

        JButton btnVoltarLoginDoCadastro = new JButton("← Voltar para o Login");
        btnVoltarLoginDoCadastro.setBounds(110, 278, 190, 25);
        btnVoltarLoginDoCadastro.setBorderPainted(false);
        btnVoltarLoginDoCadastro.setContentAreaFilled(false);
        btnVoltarLoginDoCadastro.setForeground(new Color(100, 50, 150));
        pnlCadastro.add(btnVoltarLoginDoCadastro);

        // =====================================================================
        // CARD 3 – REDEFINIR SENHA
        // =====================================================================
        JPanel pnlEsqueci = new JPanel();
        pnlEsqueci.setBackground(new Color(255, 240, 245));
        pnlEsqueci.setLayout(null);
        contentPane.add(pnlEsqueci, "tela_esqueci");

        JLabel lblTituloEsqueci = new JLabel("REDEFINIR SENHA");
        lblTituloEsqueci.setFont(new Font("Arial", Font.BOLD, 15));
        lblTituloEsqueci.setBounds(130, 20, 200, 25);
        pnlEsqueci.add(lblTituloEsqueci);

        JLabel lblInstrucao = new JLabel("<html>Informe seu usuário e escolha uma nova senha.</html>");
        lblInstrucao.setBounds(50, 58, 320, 30);
        lblInstrucao.setForeground(new Color(80, 80, 80));
        pnlEsqueci.add(lblInstrucao);

        JLabel lblRecuperaUsuario = new JLabel("Usuário:");
        lblRecuperaUsuario.setBounds(50, 105, 70, 20);
        pnlEsqueci.add(lblRecuperaUsuario);

        txtRecuperaUsuario = new JTextField();
        txtRecuperaUsuario.setBounds(130, 105, 210, 25);
        txtRecuperaUsuario.setBackground(new Color(250, 240, 230));
        pnlEsqueci.add(txtRecuperaUsuario);

        JLabel lblNovaSenha = new JLabel("Nova senha:");
        lblNovaSenha.setBounds(50, 145, 80, 20);
        pnlEsqueci.add(lblNovaSenha);

        JPasswordField txtNovaSenha = new JPasswordField();
        txtNovaSenha.setBounds(130, 145, 210, 25);
        txtNovaSenha.setBackground(new Color(250, 240, 230));
        pnlEsqueci.add(txtNovaSenha);

        JLabel lblConfirmaNovaSenha = new JLabel("Confirmar:");
        lblConfirmaNovaSenha.setBounds(50, 185, 80, 20);
        pnlEsqueci.add(lblConfirmaNovaSenha);

        JPasswordField txtConfirmaNovaSenha = new JPasswordField();
        txtConfirmaNovaSenha.setBounds(130, 185, 210, 25);
        txtConfirmaNovaSenha.setBackground(new Color(250, 240, 230));
        pnlEsqueci.add(txtConfirmaNovaSenha);

        JButton btnRedefinir = new JButton("Redefinir Senha");
        btnRedefinir.setBounds(130, 230, 210, 30);
        btnRedefinir.setBackground(new Color(180, 100, 130));
        btnRedefinir.setForeground(Color.WHITE);
        btnRedefinir.setFont(new Font("Arial", Font.BOLD, 12));
        pnlEsqueci.add(btnRedefinir);

        JButton btnVoltarLoginDoEsqueci = new JButton("← Voltar para o Login");
        btnVoltarLoginDoEsqueci.setBounds(110, 273, 190, 25);
        btnVoltarLoginDoEsqueci.setBorderPainted(false);
        btnVoltarLoginDoEsqueci.setContentAreaFilled(false);
        btnVoltarLoginDoEsqueci.setForeground(new Color(100, 50, 150));
        pnlEsqueci.add(btnVoltarLoginDoEsqueci);

        // =====================================================================
        // NAVEGAÇÃO ENTRE CARDS
        // =====================================================================
        btnIrCadastro.addActionListener(e -> cartoes.show(contentPane, "tela_cadastro"));
        btnEsqueciSenha.addActionListener(e -> cartoes.show(contentPane, "tela_esqueci"));
        btnVoltarLoginDoCadastro.addActionListener(e -> cartoes.show(contentPane, "tela_login"));
        btnVoltarLoginDoEsqueci.addActionListener(e -> cartoes.show(contentPane, "tela_login"));

        // =====================================================================
        // AÇÃO – ENTRAR (Login)
        // =====================================================================
        btnEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user     = txtUsuario.getText().trim();
                String password = new String(txtSenha.getPassword()).trim();

                if (user.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                        "Por favor, preencha todos os campos!",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                String nomeLogin = usuarioDAO.autenticarUsuario(user, password);

                if (nomeLogin != null) {
                    JOptionPane.showMessageDialog(null, "Seja bem-vindo, " + nomeLogin + "!");
                    TelaPrincipal principal = new TelaPrincipal(nomeLogin);
                    principal.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null,
                        "Usuário ou senha incorretos!",
                        "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
                    txtSenha.setText("");
                    txtUsuario.requestFocus();
                }
            }
        });

        // =====================================================================
        // AÇÃO – CADASTRAR
        // =====================================================================
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome     = txtCadNome.getText().trim();
                String usuario  = txtCadUsuario.getText().trim();
                String senha    = new String(txtCadSenha.getPassword()).trim();
                String confirma = new String(txtCadConfirmaSenha.getPassword()).trim();

                if (nome.isEmpty() || usuario.isEmpty() || senha.isEmpty() || confirma.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                        "Preencha todos os campos!",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!senha.equals(confirma)) {
                    JOptionPane.showMessageDialog(null,
                        "As senhas não coincidem!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                    txtCadConfirmaSenha.setText("");
                    return;
                }

                UsuarioDAO usuarioDAO = new UsuarioDAO();

                // Impede cadastrar login duplicado
                if (usuarioDAO.usuarioExiste(usuario)) {
                    JOptionPane.showMessageDialog(null,
                        "Este usuário já está cadastrado!\nEscolha outro nome de usuário.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                    txtCadUsuario.requestFocus();
                    return;
                }

                boolean ok = usuarioDAO.cadastrarUsuario(nome, usuario, senha);

                if (ok) {
                    JOptionPane.showMessageDialog(null,
                        "Usuário cadastrado com sucesso!\nFaça login para continuar.",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    txtCadNome.setText("");
                    txtCadUsuario.setText("");
                    txtCadSenha.setText("");
                    txtCadConfirmaSenha.setText("");
                    cartoes.show(contentPane, "tela_login");
                } else {
                    JOptionPane.showMessageDialog(null,
                        "Erro ao cadastrar. Tente novamente.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // =====================================================================
        // AÇÃO – REDEFINIR SENHA
        // =====================================================================
        btnRedefinir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario  = txtRecuperaUsuario.getText().trim();
                String novaSenha = new String(txtNovaSenha.getPassword()).trim();
                String confirma  = new String(txtConfirmaNovaSenha.getPassword()).trim();

                if (usuario.isEmpty() || novaSenha.isEmpty() || confirma.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                        "Preencha todos os campos!",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!novaSenha.equals(confirma)) {
                    JOptionPane.showMessageDialog(null,
                        "As senhas não coincidem!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                    txtNovaSenha.setText("");
                    txtConfirmaNovaSenha.setText("");
                    return;
                }

                UsuarioDAO usuarioDAO = new UsuarioDAO();

                if (!usuarioDAO.usuarioExiste(usuario)) {
                    JOptionPane.showMessageDialog(null,
                        "Usuário não encontrado!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                    txtRecuperaUsuario.requestFocus();
                    return;
                }

                boolean ok = usuarioDAO.atualizarSenha(usuario, novaSenha);

                if (ok) {
                    JOptionPane.showMessageDialog(null,
                        "Senha redefinida com sucesso!\nFaça login com sua nova senha.",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    txtRecuperaUsuario.setText("");
                    txtNovaSenha.setText("");
                    txtConfirmaNovaSenha.setText("");
                    cartoes.show(contentPane, "tela_login");
                } else {
                    JOptionPane.showMessageDialog(null,
                        "Erro ao redefinir a senha. Tente novamente.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
