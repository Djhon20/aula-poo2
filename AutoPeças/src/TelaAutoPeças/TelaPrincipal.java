package TelaAutoPeças;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import DAOAutoPeças.PecaDAO; // Importação do DAO de peças
import DAOAutoPeças.ClienteDAO; //import de dao clientes
public class TelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	// Componentes do CRUD de Peças
	private JTextField txtIdPeca;
	private JTextField txtNomePeca;
	private JTextField txtQuantidade;
	private JTextField txtPreco;
	private JTable tabelaPecas;
	private DefaultTableModel modeloTabela;
	// Componentes da aba Clientes
	private JTextField txtBuscaCliente;
	private JTable tabelaClientes;
	private DefaultTableModel modeloClientes;
	private JButton btnEditarCliente;
	// Componentes da aba Vendas
	private javax.swing.JComboBox<String> cbClientesVenda;
	private javax.swing.JComboBox<String> cbPecasVenda;
	private JTextField txtQtdVenda;
	private JTable tabelaCarrinho;
	private DefaultTableModel modeloCarrinho;
	private JLabel lblTotalVenda;
	private javax.swing.JComboBox<String> cbPagamento;

	// Listas para mapear os IDs reais que estão dentro dos ComboBoxes
	private java.util.List<Integer> listaIdsClientes = new java.util.ArrayList<>();
	private java.util.List<Object[]> listaDadosPecas = new java.util.ArrayList<>();
	private double totalGeralVenda = 0.0;

	/**
	 * Launch the application (Para o WindowBuilder e testes isolados)
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal("Administrador");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Construtor padrão necessário para o WindowBuilder aba Design
	 */
	public TelaPrincipal() {
		this("Usuário");
	}

	/**
	 * Construtor Real que recebe o nome do usuário logado
	 */
	public TelaPrincipal(String nomeLogin) {
		setTitle("Sistema de Gestão de AutoPeças");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Abre o sistema ocupando a tela inteira (FullHD ou qualquer monitor)
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setMinimumSize(new java.awt.Dimension(1024, 720)); // Garante um tamanho mínimo aceitável

		// Painel Mestre com BorderLayout
		contentPane = new JPanel(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// =====================================================================
		// 1. PAINEL SUPERIOR (Barra de Status / Usuário / Logout)
		// =====================================================================
		JPanel pnlSuperior = new JPanel(new BorderLayout());
		pnlSuperior.setBackground(new Color(180, 100, 130));
		pnlSuperior.setPreferredSize(new java.awt.Dimension(100, 50));
		contentPane.add(pnlSuperior, BorderLayout.NORTH);

		JLabel lblBemVindo = new JLabel("  Usuário Ativo: " + nomeLogin);
		lblBemVindo.setForeground(Color.WHITE);
		lblBemVindo.setFont(new Font("Arial", Font.BOLD, 14));
		pnlSuperior.add(lblBemVindo, BorderLayout.WEST);

		JButton btnLogout = new JButton("Sair do Sistema ");
		btnLogout.setFocusable(false);
		pnlSuperior.add(btnLogout, BorderLayout.EAST);
		
		// Ação do botão de fechar e voltar para o login
		btnLogout.addActionListener(e -> {
			TelaLogin login = new TelaLogin();
			login.setVisible(true);
			dispose();
		});

		// =====================================================================
		// 2. SISTEMA DE ABAS (JTabbedPane) - Ocupa o Centro da Tela Cheia
		// =====================================================================
		JTabbedPane abasSistema = new JTabbedPane(JTabbedPane.TOP);
		abasSistema.setFont(new Font("Arial", Font.BOLD, 13));
		contentPane.add(abasSistema, BorderLayout.CENTER);

		// =====================================================================
		// ABA 1: CRUD DE PEÇAS 
		// =====================================================================
		JPanel pnlPecas = new JPanel();
		pnlPecas.setLayout(null); // Layout Absoluto para desenhar livremente
		pnlPecas.setBackground(new Color(255, 245, 247));
		abasSistema.addTab("Gerenciamento de Peças", null, pnlPecas, "Clique para gerenciar o estoque de peças");

		// --- Formulário de Entrada de Dados ---
		JLabel lblIdPeca = new JLabel("Código (ID):");
		lblIdPeca.setBounds(30, 30, 80, 20);
		pnlPecas.add(lblIdPeca);

		txtIdPeca = new JTextField();
		txtIdPeca.setEditable(false); // Travado pois o banco gera o ID automaticamente
		txtIdPeca.setBounds(120, 30, 80, 25);
		pnlPecas.add(txtIdPeca);

		JLabel lblNomePeca = new JLabel("Nome da Peça:");
		lblNomePeca.setBounds(30, 70, 100, 20);
		pnlPecas.add(lblNomePeca);

		txtNomePeca = new JTextField();
		txtNomePeca.setBounds(120, 70, 300, 25);
		pnlPecas.add(txtNomePeca);

		JLabel lblQuantidade = new JLabel("Quantidade:");
		lblQuantidade.setBounds(30, 110, 80, 20);
		pnlPecas.add(lblQuantidade);

		txtQuantidade = new JTextField();
		txtQuantidade.setBounds(120, 110, 100, 25);
		pnlPecas.add(txtQuantidade);

		JLabel lblPreco = new JLabel("Preço de Venda:");
		lblPreco.setBounds(30, 150, 100, 20);
		pnlPecas.add(lblPreco);

		txtPreco = new JTextField();
		txtPreco.setBounds(120, 150, 100, 25);
		pnlPecas.add(txtPreco);

		// --- Botões de Ação do CRUD Peças ---
		JButton btnSalvar = new JButton("Salvar Peça");
		btnSalvar.setBounds(30, 210, 110, 30);
		pnlPecas.add(btnSalvar);

		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(150, 210, 90, 30);
		pnlPecas.add(btnEditar);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(250, 210, 90, 30);
		pnlPecas.add(btnExcluir);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(350, 210, 90, 30);
		pnlPecas.add(btnLimpar);

		// --- Tabela para Listar as Peças ---
		JScrollPane scrollTabela = new JScrollPane();
		scrollTabela.setBounds(480, 30, 850, 500); 
		pnlPecas.add(scrollTabela);

		// Configuração das colunas da tabela
		modeloTabela = new DefaultTableModel(
			new Object[][] {},
			new String[] { "ID", "Nome da Peça", "Estoque", "Preço (R$)" }
		) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Evita que o usuário edite digitando direto nas células da tabela
			}
		};
		
		tabelaPecas = new JTable(modeloTabela);
		scrollTabela.setViewportView(tabelaPecas);

		// =====================================================================
				// ABA 2: CLIENTES
				// =====================================================================
				JPanel pnlClientes = new JPanel();
				pnlClientes.setLayout(null); // Layout absoluto para FullHD
				pnlClientes.setBackground(new Color(245, 245, 250));
				abasSistema.addTab("Clientes", null, pnlClientes, "Gerenciamento de Clientes");

				// Barra de Busca
				JLabel lblBusca = new JLabel("Buscar Cliente por Nome:");
				lblBusca.setBounds(30, 30, 160, 20);
				pnlClientes.add(lblBusca);

				txtBuscaCliente = new JTextField();
				txtBuscaCliente.setBounds(190, 30, 350, 25);
				pnlClientes.add(txtBuscaCliente);

				JButton btnBuscar = new JButton("Pesquisar");
				btnBuscar.setBounds(550, 30, 100, 25);
				pnlClientes.add(btnBuscar);

				// Botões de Ação
				JButton btnNovoCliente = new JButton("+ Novo Cliente");
				btnNovoCliente.setBackground(new Color(100, 150, 100));
				btnNovoCliente.setForeground(Color.WHITE);
				btnNovoCliente.setFont(new Font("Arial", Font.BOLD, 12));
				btnNovoCliente.setBounds(30, 80, 140, 30);
				pnlClientes.add(btnNovoCliente);

				btnEditarCliente = new JButton("Editar Selecionado");
				btnEditarCliente.setEnabled(false); // Nasce DESATIVADO conforme solicitado
				btnEditarCliente.setBounds(180, 80, 160, 30);
				pnlClientes.add(btnEditarCliente);

				// Tabela de Clientes
				JScrollPane scrollClientes = new JScrollPane();
				scrollClientes.setBounds(30, 130, 1300, 450); // Ajustado para FullHD
				pnlClientes.add(scrollClientes);

				modeloClientes = new DefaultTableModel(
					new Object[][] {},
					new String[] { "ID", "Nome do Cliente", "CPF", "Endereço", "Telefone" }
				) {
					@Override
					public boolean isCellEditable(int row, int column) { return false; }
				};

				tabelaClientes = new JTable(modeloClientes);
				scrollClientes.setViewportView(tabelaClientes);

				// =====================================================================
				// LOGICAS DA ABA DE CLIENTES
				// =====================================================================

				// 1. Ação do Botão Buscar / Pesquisar
				ActionListener acaoBuscaCliente = e -> {
					modeloClientes.setRowCount(0);
					ClienteDAO dao = new ClienteDAO();
					List<Object[]> dados = dao.buscarPorNome(txtBuscaCliente.getText().trim());
					for (Object[] linha : dados) {
						modeloClientes.addRow(linha);
					}
					btnEditarCliente.setEnabled(false); // Reseta o botão editar após pesquisar
				};
				btnBuscar.addActionListener(acaoBuscaCliente);
				
				// Faz a busca disparar também ao apertar "Enter" dentro do campo de texto
				txtBuscaCliente.addActionListener(acaoBuscaCliente); 

				// 2. Evento de Clique na Tabela (Habilita o botão Editar)
				tabelaClientes.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int linha = tabelaClientes.getSelectedRow();
						if (linha >= 0) {
							btnEditarCliente.setEnabled(true); // HABILITA O BOTÃO DE EDITAR
						}
					}
				});

				// 3. Ação do Botão "+ Novo Cliente" (Abre o Modal em modo Cadastro)
				btnNovoCliente.addActionListener(e -> {
					ModalCliente modal = new ModalCliente(this, "Cadastrar Novo Cliente");
					modal.setVisible(true); // O programa pausa aqui até o modal fechar
					
					if (modal.isSalvouComSucesso()) {
						JOptionPane.showMessageDialog(this, "Cliente gravado com sucesso!");
						btnBuscar.doClick(); // Atualiza a tabela fingindo um clique em pesquisar
					}
				});

				// 4. Ação do Botão "Editar Selecionado" (Abre o mesmo Modal em modo Edição)
				btnEditarCliente.addActionListener(e -> {
					int linha = tabelaClientes.getSelectedRow();
					if (linha >= 0) {
						// Captura os dados direto da linha selecionada na tabela
						int id = Integer.parseInt(modeloClientes.getValueAt(linha, 0).toString());
						String nome = modeloClientes.getValueAt(linha, 1).toString();
						String cpf = modeloClientes.getValueAt(linha, 2).toString();
						String end = modeloClientes.getValueAt(linha, 3).toString();
						String tel = modeloClientes.getValueAt(linha, 4).toString();

						// Cria o modal, injeta os dados nele e exibe
						ModalCliente modal = new ModalCliente(this, "Atualizar Cadastro do Cliente");
						modal.prepararParaEdicao(id, nome, cpf, end, tel);
						modal.setVisible(true);

						if (modal.isSalvouComSucesso()) {
							JOptionPane.showMessageDialog(this, "Cadastro do cliente atualizado!");
							btnBuscar.doClick(); // Atualiza a tabela
						}
					}
				});

		// =====================================================================
		// ABA 3: VENDAS (Apenas um painel vazio aguardando o futuro)
		// =====================================================================
				// =====================================================================
				// ABA 3: VENDAS
				// =====================================================================
				JPanel pnlVendas = new JPanel();
				pnlVendas.setLayout(null);
				pnlVendas.setBackground(new Color(240, 245, 240));
				abasSistema.addTab("Vendas", null, pnlVendas, "Efetuar Vendas e Recebimentos");

				// Seleção do Cliente
				JLabel lblSelectCli = new JLabel("Selecionar Cliente:");
				lblSelectCli.setBounds(30, 30, 120, 20);
				pnlVendas.add(lblSelectCli);

				cbClientesVenda = new javax.swing.JComboBox<>();
				cbClientesVenda.setBounds(150, 30, 300, 25);
				pnlVendas.add(cbClientesVenda);

				// Seleção da Peça
				JLabel lblSelectPeca = new JLabel("Selecionar Peça:");
				lblSelectPeca.setBounds(30, 80, 120, 20);
				pnlVendas.add(lblSelectPeca);

				cbPecasVenda = new javax.swing.JComboBox<>();
				cbPecasVenda.setBounds(150, 80, 300, 25);
				pnlVendas.add(cbPecasVenda);

				// Quantidade da Venda
				JLabel lblQtdVenda = new JLabel("Quantidade:");
				lblQtdVenda.setBounds(30, 130, 120, 20);
				pnlVendas.add(lblQtdVenda);

				txtQtdVenda = new JTextField();
				txtQtdVenda.setBounds(150, 130, 80, 25);
				pnlVendas.add(txtQtdVenda);

				JButton btnAddCarrinho = new JButton("Adicionar ao Carrinho");
				btnAddCarrinho.setBounds(250, 130, 200, 25);
				pnlVendas.add(btnAddCarrinho);

				// Tabela do Carrinho de Compras
				JLabel lblCarrinho = new JLabel("Itens no Carrinho:");
				lblCarrinho.setBounds(480, 10, 150, 20);
				pnlVendas.add(lblCarrinho);

				JScrollPane scrollCarrinho = new JScrollPane();
				scrollCarrinho.setBounds(480, 30, 850, 350);
				pnlVendas.add(scrollCarrinho);

				modeloCarrinho = new DefaultTableModel(
					new Object[][] {},
					new String[] { "ID Peça", "Nome do Produto", "Qtd", "Preço Unitário", "Subtotal" }
				) {
					@Override
					public boolean isCellEditable(int row, int column) { return false; }
				};

				tabelaCarrinho = new JTable(modeloCarrinho);
				scrollCarrinho.setViewportView(tabelaCarrinho);

				// Painel de Fechamento / Registro de Pagamento
				JPanel pnlPagamento = new JPanel();
				pnlPagamento.setBorder(javax.swing.BorderFactory.createTitledBorder("Fechamento do Pedido"));
				pnlPagamento.setBackground(new Color(230, 235, 230));
				pnlPagamento.setBounds(480, 400, 850, 150);
				pnlPagamento.setLayout(null);
				pnlVendas.add(pnlPagamento);

				lblTotalVenda = new JLabel("TOTAL GERAL: R$ 0.00");
				lblTotalVenda.setFont(new Font("Arial", Font.BOLD, 18));
				lblTotalVenda.setForeground(new Color(150, 30, 30));
				lblTotalVenda.setBounds(30, 40, 300, 30);
				pnlPagamento.add(lblTotalVenda);

				JLabel lblFormaPag = new JLabel("Forma de Pagamento:");
				lblFormaPag.setBounds(30, 90, 150, 20);
				pnlPagamento.add(lblFormaPag);

				cbPagamento = new javax.swing.JComboBox<>(new String[] { "Dinheiro", "Cartão de Crédito", "Cartão de Débito", "Pix" });
				cbPagamento.setBounds(180, 90, 180, 25);
				pnlPagamento.add(cbPagamento);

				JButton btnFinalizarVenda = new JButton("✨ Finalizar e Registrar Venda");
				btnFinalizarVenda.setBackground(new Color(50, 120, 50));
				btnFinalizarVenda.setForeground(Color.WHITE);
				btnFinalizarVenda.setFont(new Font("Arial", Font.BOLD, 14));
				btnFinalizarVenda.setBounds(550, 50, 250, 60);
				pnlPagamento.add(btnFinalizarVenda);

				// Button para esvaziar carrinho
				JButton btnLimparCarrinho = new JButton("Limpar Carrinho");
				btnLimparCarrinho.setBounds(30, 180, 150, 25);
				pnlVendas.add(btnLimparCarrinho);

				// =====================================================================
				// LÓGICAS DA ABA DE VENDAS
				// =====================================================================

				
				
				// 1. Ação de Adicionar Peça ao Carrinho
				btnAddCarrinho.addActionListener(e -> {
					int indexPeca = cbPecasVenda.getSelectedIndex();
					if (indexPeca < 0) return;
					
					String qtdStr = txtQtdVenda.getText().trim();
					if (qtdStr.isEmpty()) {
						JOptionPane.showMessageDialog(this, "Informe a quantidade desejada!");
						return;
					}

					try {
						int qtdDesejada = Integer.parseInt(qtdStr);
						Object[] pecaSelecionada = listaDadosPecas.get(indexPeca);
						
						int idPeca = (int) pecaSelecionada[0];
						String nomePeca = pecaSelecionada[1].toString();
						int estoqueAtual = (int) pecaSelecionada[2];
						double precoUnit = (double) pecaSelecionada[3];

						if (qtdDesejada <= 0) {
							JOptionPane.showMessageDialog(this, "A quantidade deve ser maior que zero!");
							return;
						}

						if (qtdDesejada > estoqueAtual) {
							JOptionPane.showMessageDialog(this, "Estoque insuficiente! Estoque atual: " + estoqueAtual);
							return;
						}

						double subtotal = qtdDesejada * precoUnit;
						
						// Adiciona o item na tabela do carrinho
						modeloCarrinho.addRow(new Object[] { idPeca, nomePeca, qtdDesejada, precoUnit, subtotal });
						
						// Atualiza o total acumulado
						totalGeralVenda += subtotal;
						lblTotalVenda.setText(String.format("TOTAL GERAL: R$ %.2f", totalGeralVenda));
						txtQtdVenda.setText("");

					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(this, "Digite uma quantidade numérica válida!");
					}
				});

				// 2. Ação de Limpar o Carrinho
				btnLimparCarrinho.addActionListener(e -> {
					modeloCarrinho.setRowCount(0);
					totalGeralVenda = 0.0;
					lblTotalVenda.setText("TOTAL GERAL: R$ 0.00");
				});

				// 3. Ação do Botão de Finalizar Venda
				btnFinalizarVenda.addActionListener(e -> {
					if (cbClientesVenda.getSelectedIndex() < 0) {
						JOptionPane.showMessageDialog(this, "Cadastre um cliente antes de realizar uma venda!");
						return;
					}
					if (modeloCarrinho.getRowCount() == 0) {
						JOptionPane.showMessageDialog(this, "O carrinho está vazio! Adicione itens para vender.");
						return;
					}

					int clienteId = listaIdsClientes.get(cbClientesVenda.getSelectedIndex());
					String formaPagto = cbPagamento.getSelectedItem().toString();

					// Extrai a lista de itens do carrinho para enviar ao DAO
					java.util.List<Object[]> itensCarrinho = new java.util.ArrayList<>();
					for (int i = 0; i < modeloCarrinho.getRowCount(); i++) {
						int pecaId = (int) modeloCarrinho.getValueAt(i, 0);
						int qtd = (int) modeloCarrinho.getValueAt(i, 2);
						double preco = (double) modeloCarrinho.getValueAt(i, 3);
						itensCarrinho.add(new Object[] { pecaId, null, qtd, preco });
					}

					DAOAutoPeças.VendaDAO vendaDAO = new DAOAutoPeças.VendaDAO();
					if (vendaDAO.registrarVenda(clienteId, totalGeralVenda, formaPagto, itensCarrinho)) {
						JOptionPane.showMessageDialog(this, "Venda registrada e paga com sucesso!\nEstoque atualizado.");
						
						// Reseta os componentes de venda
						modeloCarrinho.setRowCount(0);
						totalGeralVenda = 0.0;
						lblTotalVenda.setText("TOTAL GERAL: R$ 0.00");
						
						// Atualiza os componentes globais e a tabela da aba de estoque de peças
						carregarCombosVenda();
						atualizarTabelaVisual(); 
					} else {
						JOptionPane.showMessageDialog(this, "Erro crítico ao processar o fechamento da venda.", "Erro", JOptionPane.ERROR_MESSAGE);
					}
				});

				// 4. Listener para atualizar os Combos sempre que o usuário clicar na Aba Vendas
				abasSistema.addChangeListener(e -> {
					if (abasSistema.getSelectedIndex() == 2) { // Índice 2 representa a Aba de Vendas
						carregarCombosVenda();
					}
				});
				
				
		// =====================================================================
		// EVENTOS E LÓGICAS DO CRUD DE PEÇAS
		// =====================================================================
		
		// 1. AÇÃO DO BOTÃO SALVAR (Inserção)
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nome = txtNomePeca.getText().trim();
					String qtdStr = txtQuantidade.getText().trim();
					String precoStr = txtPreco.getText().trim();
					
					if (nome.isEmpty() || qtdStr.isEmpty() || precoStr.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos do formulário!", "Aviso", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					int quantidade = Integer.parseInt(qtdStr);
					double preco = Double.parseDouble(precoStr.replace(",", ".")); // Aceita pontos ou vírgulas
					
					PecaDAO pecaDAO = new PecaDAO();
					if (pecaDAO.salvar(nome, quantidade, preco)) {
						JOptionPane.showMessageDialog(null, "Peça cadastrada com sucesso no estoque!");
						limparCampos();
						atualizarTabelaVisual();
					} else {
						JOptionPane.showMessageDialog(null, "Erro ao salvar a peça no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Os campos Quantidade e Preço devem conter apenas números válidos!", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// 2. AÇÃO DE CLIQUE NA TABELA (Mapeia a linha selecionada para os inputs)
		tabelaPecas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int linhaSelecionada = tabelaPecas.getSelectedRow();
				if (linhaSelecionada >= 0) {
					txtIdPeca.setText(modeloTabela.getValueAt(linhaSelecionada, 0).toString());
					txtNomePeca.setText(modeloTabela.getValueAt(linhaSelecionada, 1).toString());
					txtQuantidade.setText(modeloTabela.getValueAt(linhaSelecionada, 2).toString());
					txtPreco.setText(modeloTabela.getValueAt(linhaSelecionada, 3).toString());
				}
			}
		});

		// 3. AÇÃO DO BOTÃO EDITAR (Alteração)
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idStr = txtIdPeca.getText();
				if (idStr.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Selecione uma peça na lista lateral antes de clicar em Editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				try {
					int id = Integer.parseInt(idStr);
					String nome = txtNomePeca.getText().trim();
					int quantidade = Integer.parseInt(txtQuantidade.getText().trim());
					double preco = Double.parseDouble(txtPreco.getText().trim().replace(",", "."));
					
					PecaDAO pecaDAO = new PecaDAO();
					if (pecaDAO.editar(id, nome, quantidade, preco)) {
						JOptionPane.showMessageDialog(null, "Dados da peça atualizados com sucesso!");
						limparCampos();
						atualizarTabelaVisual();
					} else {
						JOptionPane.showMessageDialog(null, "Não foi possível atualizar o registro.", "Erro", JOptionPane.ERROR_MESSAGE);
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Verifique se a quantidade e o preço são números válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// 4. AÇÃO DO BOTÃO EXCLUIR (Deleção)
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idStr = txtIdPeca.getText();
				if (idStr.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Selecione a peça que deseja excluir clicando na lista lateral!", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza absoluta que deseja remover esta peça do sistema?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
				if (confirmacao == JOptionPane.YES_OPTION) {
					int id = Integer.parseInt(idStr);
					PecaDAO pecaDAO = new PecaDAO();
					if (pecaDAO.excluir(id)) {
						JOptionPane.showMessageDialog(null, "Peça removida do estoque.");
						limparCampos();
						atualizarTabelaVisual();
					} else {
						JOptionPane.showMessageDialog(null, "Erro ao tentar remover a peça.", "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		// 5. AÇÃO DO BOTÃO LIMPAR
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});

		// Inicializa a tabela trazendo os registros existentes do SQLite logo na abertura da tela
		atualizarTabelaVisual();
	}
	
	// Método auxiliar para limpar os inputs do formulário
	private void limparCampos() {
		txtIdPeca.setText("");
		txtNomePeca.setText("");
		txtQuantidade.setText("");
		txtPreco.setText("");
		tabelaPecas.clearSelection();
	}

	// Método que limpa o visual antigo da tabela e renderiza os dados atuais vindos direto do SQLite
	private void atualizarTabelaVisual() {
		modeloTabela.setRowCount(0); // Esvazia as linhas do componente visual do Swing
		
		PecaDAO pecaDAO = new PecaDAO();
		List<Object[]> pecasDoBanco = pecaDAO.listarTodos(); // Busca a lista atual do banco
		
		for (Object[] peca : pecasDoBanco) {
			modeloTabela.addRow(peca); // Preenche a tabela visual linha por linha
			
			carregarCombosVenda();
	    } // Fim do construtor
		
	}
	private void carregarCombosVenda() {
	    cbClientesVenda.removeAllItems();
	    listaIdsClientes.clear();
	    
	    // Carrega Clientes
	    DAOAutoPeças.ClienteDAO cliDAO = new DAOAutoPeças.ClienteDAO();
	    List<Object[]> clientes = cliDAO.buscarPorNome(""); // Traz todos
	    for (Object[] c : clientes) {
	        listaIdsClientes.add((Integer) c[0]); // Guarda o ID real
	        cbClientesVenda.addItem(c[1].toString() + " (CPF: " + c[2].toString() + ")");
	    }

	    cbPecasVenda.removeAllItems();
	    listaDadosPecas.clear();
	    
	    // Carrega Peças
	    DAOAutoPeças.PecaDAO pecaDAO = new DAOAutoPeças.PecaDAO();
	    listaDadosPecas = pecaDAO.listarTodos();
	    for (Object[] p : listaDadosPecas) {
	        cbPecasVenda.addItem(p[1].toString() + " - R$ " + p[3].toString() + " [Qtd: " + p[2].toString() + "]");
	    }
	}
}