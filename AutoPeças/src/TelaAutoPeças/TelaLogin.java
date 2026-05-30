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
	public TelaLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 275, 252);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 240, 245));
		contentPane.setForeground(new Color(255, 222, 173));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuário");
		lblUsuario.setBounds(42, 34, 49, 14);
		contentPane.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setBackground(new Color(250, 240, 230));
		txtUsuario.setBounds(42, 59, 169, 23);
		txtUsuario.setText("usuario");
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(42, 93, 49, 14);
		contentPane.add(lblSenha);
		
		txtSenha = new JPasswordField();
		txtSenha.setBackground(new Color(250, 240, 230));
		txtSenha.setBounds(42, 115, 169, 20);
		contentPane.add(txtSenha);
		
		JButton btnentrar = new JButton("Login");
		btnentrar.setBounds(68, 146, 101, 23);
		contentPane.add(btnentrar);
		
		btnentrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuarioDigitado = txtUsuario.getText();
				String senhaDigitada = new String(txtSenha.getPassword());
				
				if (usuarioDigitado.trim().isEmpty() || senhaDigitada.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!","Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				
				boolean loginValido = usuarioDAO.autenticarUsuario(usuarioDigitado,senhaDigitada);
				
				if (loginValido) {
					JOptionPane.showMessageDialog(null, "Acesso autorizado! Bem-vindo ao sistema.");
					
					TelaCadastro principal = new TelaCadastro();
					principal.setVisible(true);
					dispose();
 
			} else {
				// Se o banco não achar o login ou a senha correspondente
				JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos!", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
				
				// Limpa o campo de senha para o usuário digitar novamente com facilidade
				txtSenha.setText(""); 
				txtUsuario.requestFocus(); // Devolve o cursor de digitação para o campo de usuário
			}
				
			}
		}
				
				
				
				);

	}
}
