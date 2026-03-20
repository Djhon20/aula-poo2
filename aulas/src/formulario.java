
import java.awt.EventQueue;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;

public class formulario extends JFrame {
	
private static final long serialVersionUID = 1L;
	
	private JTextField txtnome;
	private JTextField txtNomeCompleto;
	private JTextField txtdatanascimento;
	private JTextField txtidade;
	private JLabel lblNewLabel_3;
	private JTextField txtcpf;
	private JLabel lblNewLabel_4;
	private JTextField txtcep;
	private JLabel lblNewLabel_6;
	private JTextField txtend;
	private JLabel lblNewLabel_7;
	private JTextField txtobs;
	private JTextField txtnomepai;
	private JTextField txtnomemae;
	private JLabel lblNewLabel_8;
	private JLabel lblNewLabel_9;
	

	
	
	
	public formulario() {
		getContentPane().setLayout(null);
		
		txtNomeCompleto = new JTextField();
		txtNomeCompleto.setBounds(92, 22, 249, 20);
		getContentPane().add(txtNomeCompleto);
		txtNomeCompleto.setColumns(55);
		
		JLabel lblNewLabel = new JLabel("Nome Completo:");
		lblNewLabel.setBounds(10, 25, 79, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Data de Nascimento:");
		lblNewLabel_1.setBounds(10, 51, 109, 14);
		getContentPane().add(lblNewLabel_1);
		
		try {
		    MaskFormatter mascaraData = new MaskFormatter("##/##/####");
		    mascaraData.setPlaceholderCharacter('_');
		    txtdatanascimento = new JFormattedTextField(mascaraData);
		    txtdatanascimento.setBounds(112, 50, 64, 20);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		getContentPane().add(txtdatanascimento);
		txtdatanascimento.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Idade:");
		lblNewLabel_2.setBounds(214, 51, 46, 14);
		getContentPane().add(lblNewLabel_2);
		
		txtidade = new JTextField();
		txtidade.setBounds(270, 53, 64, 20);
		txtidade.addFocusListener(new FocusAdapter() {
			
		});
		getContentPane().add(txtidade);
		txtidade.setColumns(10);
		
		lblNewLabel_3 = new JLabel("CPF:");
		lblNewLabel_3.setBounds(10, 110, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		txtcpf = new JTextField();
		txtcpf.setBounds(40, 107, 301, 20);
		getContentPane().add(txtcpf);
		txtcpf.setColumns(15);
		
		lblNewLabel_4 = new JLabel("Sexo");
		lblNewLabel_4.setBounds(10, 142, 46, 14);
		getContentPane().add(lblNewLabel_4);
		
		JComboBox cbsexo = new JComboBox();
		cbsexo.setBounds(46, 138, 94, 22);
		cbsexo.setModel(new DefaultComboBoxModel(new String[] {"Masculino", "Feminino"}));
		getContentPane().add(cbsexo);
		
		JLabel lblNewLabel_5 = new JLabel("CEP:");
		lblNewLabel_5.setBounds(10, 167, 23, 14);
		getContentPane().add(lblNewLabel_5);
		
		txtcep = new JTextField();
		txtcep.setBounds(40, 167, 301, 20);
		getContentPane().add(txtcep);
		txtcep.setColumns(10);
		
		lblNewLabel_6 = new JLabel("Endereço:");
		lblNewLabel_6.setBounds(10, 198, 92, 14);
		getContentPane().add(lblNewLabel_6);
		
		txtend = new JTextField();
		txtend.setBounds(64, 195, 277, 20);
		getContentPane().add(txtend);
		txtend.setColumns(10);
		
		lblNewLabel_7 = new JLabel("Observações:");
		lblNewLabel_7.setBounds(10, 243, 76, 14);
		getContentPane().add(lblNewLabel_7);
		
		txtobs = new JTextField();
		txtobs.setBounds(84, 223, 257, 48);
		getContentPane().add(txtobs);
		txtobs.setColumns(10);
		
		txtnomepai = new JTextField();
		txtnomepai.setBounds(64, 295, 277, 20);
		getContentPane().add(txtnomepai);
		txtnomepai.setColumns(10);
		
		txtnomemae = new JTextField();
		txtnomemae.setBounds(66, 326, 275, 20);
		getContentPane().add(txtnomemae);
		txtnomemae.setColumns(10);
		
		lblNewLabel_8 = new JLabel("Nome Pai:");
		lblNewLabel_8.setBounds(10, 298, 92, 14);
		getContentPane().add(lblNewLabel_8);
		
		lblNewLabel_9 = new JLabel("Nome Mãe:");
		lblNewLabel_9.setBounds(10, 329, 92, 14);
		getContentPane().add(lblNewLabel_9);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Possui Filhos");
		chckbxNewCheckBox.setBounds(64, 353, 97, 23);
		getContentPane().add(chckbxNewCheckBox);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(156, 413, 89, 23);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		getContentPane().add(btnSalvar);
	
		txtdatanascimento.addFocusListener(new FocusAdapter() {
		    @Override
		    public void focusLost(FocusEvent e) {
		        try {
		            String data = txtdatanascimento.getText();
		            
		            // Formato esperado: dd/MM/yyyy
		            java.time.format.DateTimeFormatter formatter = 
		                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
		            
		            java.time.LocalDate nascimento = 
		                java.time.LocalDate.parse(data, formatter);
		            
		            java.time.LocalDate hoje = java.time.LocalDate.now();
		            
		            int idade = java.time.Period.between(nascimento, hoje).getYears();
		            
		            txtidade.setText(String.valueOf(idade));
		            
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Data inválida! Use dd/MM/yyyy");
		        }
		    }
		});
	}
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					formulario frame = new formulario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
