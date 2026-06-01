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


import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import javax.swing.JToggleButton;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import DAOAutoPeças.UsuarioDAO;

public class TelaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtSenha;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	/**
	 * Create the frame.
	 */
	public TelaLogin() {
		setTitle("Sistema AutoPeças - Acesso");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 380); // Ajustado o tamanho para caber o layout confortavelmente
		setLocationRelativeTo(null);   // Centraliza a janela na tela
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 240, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		// 1. Configura o CardLayout no painel mestre
		CardLayout cartoes = new CardLayout(0, 0);
		contentPane.setLayout(cartoes);
		
		// ==========================================
		// CARD 1: PAINEL DE LOGIN
		// ==========================================
		JPanel pnlLogin = new JPanel();
		pnlLogin.setBackground(new Color(255, 240, 245)); // Mantém a cor que você escolheu
		contentPane.add(pnlLogin, "tela_login");
		pnlLogin.setLayout(null);
		
		// Movidos os seus componentes originais para DENTRO do pnlLogin com coordenadas fixas (Bounds)
		JLabel lblUsuario = new JLabel("Usuário:");
		lblUsuario.setBounds(50, 60, 60, 20);
		pnlLogin.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(130, 60, 180, 20);
		txtUsuario.setToolTipText("Digite seu usuário");
		txtUsuario.setBackground(new Color(250, 240, 230));
		pnlLogin.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(50, 100, 60, 20);
		pnlLogin.add(lblSenha);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(130, 100, 180, 20);
		txtSenha.setBackground(new Color(250, 240, 230));
		pnlLogin.add(txtSenha);
		
		JButton btnentrar = new JButton("Login");
		btnentrar.setBounds(130, 150, 180, 25);
		pnlLogin.add(btnentrar);
		
		// NOVO BOTÃO: Criado no Login para empurrar o usuário para a tela de cadastro
		JButton btnIrParaCadastro = new JButton("Não tem conta? Cadastre-se");
		btnIrParaCadastro.setBounds(10, 186, 180, 25);
		pnlLogin.add(btnIrParaCadastro);
		
		// ==========================================
		// CARD 2: PAINEL DE CADASTRO
		// ==========================================
		JPanel pnlCadastro = new JPanel();
		pnlCadastro.setBackground(new Color(255, 240, 245));
		contentPane.add(pnlCadastro, "tela_cadastro");     // Identificador para a troca de tela
		pnlCadastro.setLayout(null);                        // Layout absoluto para você desenhar livremente
		
		// Elementos iniciais da tela de cadastro
		JLabel lblTituloCadastro = new JLabel("CADASTRAR USUÁRIO");
		lblTituloCadastro.setBounds(140, 20, 180, 20);
		pnlCadastro.add(lblTituloCadastro);
		
		// NOVO BOTÃO: Criado no Cadastro para permitir que o usuário desista e volte ao login
		JButton btnVoltarLogin = new JButton("Voltar para o Login");
		btnVoltarLogin.setBounds(130, 250, 180, 25);
		pnlCadastro.add(btnVoltarLogin);
		
		// ==========================================
		// AÇÕES DE NAVEGAÇÃO ENTRE OS CARDS
		// ==========================================
		
		btnIrParaCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cartoes.show(contentPane, "tela_cadastro"); // Mostra o painel de cadastro
			}
		});
		
		btnVoltarLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cartoes.show(contentPane, "tela_login");    // Mostra o painel de login
			}
		});
		
		// ==========================================
		// LÓGICA DE AUTENTICAÇÃO (SEU BOTÃO ENTRAR)
		// ==========================================
		btnentrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = txtUsuario.getText();
				String password = new String(txtSenha.getPassword());
				
				if (user.trim().isEmpty() || password.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				String nomeLogin = usuarioDAO.autenticarUsuario(user, password);
				
				if (nomeLogin != null) {
					JOptionPane.showMessageDialog(null, "Seja bem vindo, " + nomeLogin + "!");
					
					TelaPrincipal principal = new TelaPrincipal(nomeLogin);
					principal.setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos!", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
					txtSenha.setText(""); 
					txtUsuario.requestFocus(); 
				}
			}
		});
	private static class __Tmp {
		private static void __tmp() {
			  javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
			  
			  javax.swing.JButton button = new javax.swing.JButton("New button");
			  __wbp_panel.add(button);
		}
	}
	}