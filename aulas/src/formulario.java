
import java.awt.EventQueue;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class formulario extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtNomeCompleto;
    private JFormattedTextField txtdatanascimento;
    private JTextField txtidade;
    private JTextField txtcpf;
    private JTextField txtcep;
    private JTextField txtend;
    private JTextField txtobs;
    private JTextField txtnomepai;
    private JTextField txtnomemae;

    public formulario() {

        setTitle("Formulário");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // Nome
        JLabel lblNome = new JLabel("Nome Completo:");
        lblNome.setBounds(10, 20, 120, 14);
        getContentPane().add(lblNome);

        txtNomeCompleto = new JTextField();
        txtNomeCompleto.setBounds(130, 20, 200, 20);
        getContentPane().add(txtNomeCompleto);

        // Data nascimento
        JLabel lblData = new JLabel("Data Nasc:");
        lblData.setBounds(10, 51, 120, 14);
        getContentPane().add(lblData);

        try {
            MaskFormatter mascara = new MaskFormatter("##/##/####");
            txtdatanascimento = new JFormattedTextField(mascara);
            txtdatanascimento.setBounds(130, 51, 80, 20);
            getContentPane().add(txtdatanascimento);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Idade
        JLabel lblIdade = new JLabel("Idade:");
        lblIdade.setBounds(220, 51, 50, 14);
        getContentPane().add(lblIdade);

        txtidade = new JTextField();
        txtidade.setBounds(270, 51, 60, 20);
        txtidade.setEditable(false);
        getContentPane().add(txtidade);

        // Evento calcular idade
        txtdatanascimento.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                try {
                    String data = txtdatanascimento.getText();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate nascimento = LocalDate.parse(data, formatter);
                    LocalDate hoje = LocalDate.now();

                    int idade = Period.between(nascimento, hoje).getYears();
                    txtidade.setText(String.valueOf(idade));

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Data inválida!");
                    txtidade.setText("");
                }
            }
        });

        // CPF
        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setBounds(10, 82, 23, 14);
        getContentPane().add(lblCpf);

        txtcpf = new JTextField();
        txtcpf.setBounds(130, 82, 200, 20);
        getContentPane().add(txtcpf);

        // CEP
        JLabel lblCep = new JLabel("CEP:");
        lblCep.setBounds(10, 113, 23, 14);
        getContentPane().add(lblCep);

        txtcep = new JTextField();
        txtcep.setBounds(130, 113, 200, 20);
        getContentPane().add(txtcep);

        // Validação do CEP ao sair do campo
        txtcep.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                String cep = txtcep.getText().replaceAll("\\D", "");

                if (!cep.isEmpty() && cep.length() != 8) {
                    JOptionPane.showMessageDialog(null, "CEP inválido!");
                    txtcep.requestFocus();
                }
            }
        });

        // Endereço
        JLabel lblEnd = new JLabel("Endereço:");
        lblEnd.setBounds(10, 144, 49, 14);
        getContentPane().add(lblEnd);

        txtend = new JTextField();
        txtend.setBounds(130, 144, 200, 20);
        getContentPane().add(txtend);

        // Observações
        JLabel lblObs = new JLabel("Obs:");
        lblObs.setBounds(10, 175, 120, 14);
        getContentPane().add(lblObs);

        txtobs = new JTextField();
        txtobs.setBounds(130, 175, 200, 40);
        getContentPane().add(txtobs);

        // Pai
        JLabel lblPai = new JLabel("Nome Pai:");
        lblPai.setBounds(10, 226, 48, 14);
        getContentPane().add(lblPai);

        txtnomepai = new JTextField();
        txtnomepai.setBounds(130, 226, 200, 20);
        getContentPane().add(txtnomepai);

        // Mãe
        JLabel lblMae = new JLabel("Nome Mãe:");
        lblMae.setBounds(10, 257, 120, 14);
        getContentPane().add(lblMae);

        txtnomemae = new JTextField();
        txtnomemae.setBounds(130, 257, 200, 20);
        getContentPane().add(txtnomemae);

        // Botão salvar
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(168, 307, 63, 30);
        getContentPane().add(btnSalvar);

        // Evento salvar
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // 🔴 Validações
                if (txtNomeCompleto.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Informe o nome!");
                    return;
                }

                if (txtdatanascimento.getText().contains("_")) {
                    JOptionPane.showMessageDialog(null, "Data inválida!");
                    return;
                }

                if (txtcpf.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Informe o CPF!");
                    return;
                }

                if (txtcep.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Informe o CEP!");
                    return;
                }

                // 🟢 Coletando dados
                String dados =
                        "Nome: " + txtNomeCompleto.getText() +
                        "\nData: " + txtdatanascimento.getText() +
                        "\nIdade: " + txtidade.getText() +
                        "\nCPF: " + txtcpf.getText() +
                        "\nCEP: " + txtcep.getText() +
                        "\nEndereço: " + txtend.getText();

                JOptionPane.showMessageDialog(null, "Salvo com sucesso!\n\n" + dados);
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                formulario frame = new formulario();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}