package TelaAutoPeças;

import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class TelaCadastro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtpeça;
	private JTextField txtserial;
	private JTextField txtcarro;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastro frame = new TelaCadastro();
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
	public TelaCadastro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 438, 224);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtpeça = new JTextField();
		txtpeça.setBounds(104, 31, 298, 20);
		contentPane.add(txtpeça);
		txtpeça.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nome da peça:");
		lblNewLabel.setBounds(19, 34, 75, 14);
		contentPane.add(lblNewLabel);
		
		txtserial = new JTextField();
		txtserial.setBounds(104, 59, 298, 20);
		contentPane.add(txtserial);
		txtserial.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Numero Serial:");
		lblNewLabel_1.setBounds(19, 59, 75, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Modelo do carro:");
		lblNewLabel_2.setBounds(19, 90, 91, 14);
		contentPane.add(lblNewLabel_2);
		
		txtcarro = new JTextField();
		txtcarro.setBounds(104, 87, 298, 20);
		contentPane.add(txtcarro);
		txtcarro.setColumns(10);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBackground(Color.LIGHT_GRAY);
		btnSalvar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSalvar.setBounds(162, 137, 89, 23);
		contentPane.add(btnSalvar);
		
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (txtpeça.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "informe o nome da peça");
					return;
				}
				
				if (txtserial.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "informar o numero serial da peça");
					return;
				}
				
				if (txtcarro.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "informar o modelo do carro");
					return;
				}
					
		
		
		
		
		
		
		
			}
		});
		
		JLabel lblNewLabel_3 = new JLabel("Cadastro de  nova peça");
		lblNewLabel_3.setFont(new Font("Segoe UI Black", Font.BOLD | Font.ITALIC, 11));
		lblNewLabel_3.setBounds(150, 6, 160, 14);
		contentPane.add(lblNewLabel_3);

	}
}
