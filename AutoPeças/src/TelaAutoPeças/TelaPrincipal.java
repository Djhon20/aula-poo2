package TelaAutoPeças;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class TelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Chama o construtor padrão (vazio). O erro some de vez!
                    TelaPrincipal frame = new TelaPrincipal(); 
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * CONSTRUTOR 1: Construtor Padrão (Sem parâmetros)
     * Necessário para o WindowBuilder abrir a aba Design e para o método main rodar sozinho.
     */
    public TelaPrincipal() {
        // Ele apenas chama o construtor real de baixo passando um nome padrão
        this("Administrador Local"); 
    }

    /**
     * CONSTRUTOR 2: Construtor Real (O que o seu sistema usa)
     * Este é o cara que a sua TelaLogin vai chamar de verdade.
     */
    public TelaPrincipal(String nomeLogin) {
		setBackground(new Color(0, 0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 663, 385);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 220));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

	}

}
