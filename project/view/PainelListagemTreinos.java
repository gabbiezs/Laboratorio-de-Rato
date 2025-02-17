package view;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import model.controller.TreinoController;
import model.controller.UsuarioController;
import model.exception.CampoInvalidoException;
import model.seletor.TreinoSeletor;
import model.vo.Email;
import model.vo.TipoUsuario;
import model.vo.Treino;
import model.vo.Usuario;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import email.GerenciadorEmail;

public class PainelListagemTreinos extends JPanel {

	private ArrayList<Treino> treinos;
	private String[] nomesColunas = { "Cliente", "Personal", "Nivel", "Treino", "Prazo Inicial", "Prazo Final"};
	private JTable tblTreinos;
	private JLabel lblCliente;
	private JLabel lblProfissional;
	private JComboBox cbCliente;
	private JComboBox cbProfissional;
	private JLabel lblNivel;
	private JComboBox cbNivel ;
	private JButton btnBuscarComFiltro;
	private JButton btnBuscarTodos;
	private JButton btnExcluir;
	private JLabel lblPaginacao;
	private JButton btnEditar;
	private JButton btnGerarPlanilha;
	private JButton btnAvancarPagina;
	private JButton btnVoltarPagina;
	private final int TAMANHO_PAGINA = 5;
	private int paginaAtual = 1;
	private int totalPaginas = 0;
	private TreinoSeletor seletor = new TreinoSeletor();
	private TreinoController controller = new TreinoController();
	private Treino treinoSelecionado;
	private UsuarioController usuarioController = new UsuarioController();
	private Usuario usuarioAutenticado;
	private JButton btnVoltar;
	private JButton btnEncaminharEmailTreino;

	
	private void limparTabelaTreinos() {
		tblTreinos.setModel(new DefaultTableModel(new Object[][] { nomesColunas, }, nomesColunas));
	}
	
	private void atualizarTabelaTreinos() {
		this.limparTabelaTreinos();

		DefaultTableModel model = (DefaultTableModel) tblTreinos.getModel();

		for (Treino t : treinos) {
			Object[] novaLinhaDaTabela = new Object[6];
			novaLinhaDaTabela[0] = t.getCliente();
			novaLinhaDaTabela[1] = t.getProfissional();
			novaLinhaDaTabela[2] = t.getNivelTreino();
			novaLinhaDaTabela[3] = t.getTreino();
			try {
				novaLinhaDaTabela[4] = t.getDtCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				novaLinhaDaTabela[5] = t.getDtTermino().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			} catch (Exception e) {
				novaLinhaDaTabela[4] = "Não cadastrado";
				novaLinhaDaTabela[5] = "Não cadastrado";
			}
	
			model.addRow(novaLinhaDaTabela);
		}
	}
	
	public PainelListagemTreinos(Usuario usuarioAutenticado) {
		this.usuarioAutenticado = usuarioAutenticado;
		setBackground(new Color(108, 255, 108));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(197dlu;pref):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(17dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(53dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(55dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(68dlu;pref):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(62dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.GROWING_BUTTON_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(253dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.GROWING_BUTTON_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("fill:pref:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:max(15dlu;pref)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:max(95dlu;default):grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				RowSpec.decode("fill:pref:grow"),}));
		
		lblProfissional = new JLabel("Personal:");
		lblProfissional.setFont(new Font("Segoe UI Black", Font.PLAIN, 13));
		lblProfissional.setForeground(Color.BLACK);
		add(lblProfissional, "12, 5, center, center");
		

		usuarioController = new UsuarioController();
		List<Usuario> usuariosAutenticados = new ArrayList<Usuario>();
		usuariosAutenticados.add(usuarioAutenticado);
		cbProfissional = new JComboBox(usuariosAutenticados.toArray());
		cbProfissional.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		cbProfissional.setForeground(Color.BLACK);
		add(cbProfissional, "14, 5, 5, 1, fill, top");
	
		
		btnBuscarComFiltro = new JButton("Buscar com Filtro");
		btnBuscarComFiltro.setBackground(Color.BLACK);
		btnBuscarComFiltro.setForeground(Color.WHITE);
		btnBuscarComFiltro.setFont(new Font("Segoe UI Black", Font.PLAIN, 13));
		btnBuscarComFiltro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarTreinosComFiltros();
				atualizarTabelaTreinos();
			}
		});
		add(btnBuscarComFiltro, "20, 5, fill, fill");
		
		lblCliente = new JLabel("Cliente: ");
		lblCliente.setFont(new Font("Segoe UI Black", Font.PLAIN, 13));
		lblCliente.setForeground(Color.BLACK);
		add(lblCliente, "12, 7, center, center");
		
		
		if (usuarioAutenticado.getTipoUsuario() == TipoUsuario.ADMINISTRADOR){
			cbCliente = new JComboBox(
					new UsuarioController().consultarPorTipoUsuario(TipoUsuario.CLIENTE.getValor()).toArray());
			cbCliente.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			cbCliente.setForeground(Color.BLACK);
			add(cbCliente, "14, 7, 5, 1, fill, top");
			
		} else {
			cbCliente = new JComboBox(new UsuarioController().
					consultarClientesUsuarioAutenticado(usuarioAutenticado.getId()).toArray());
			cbCliente.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			cbCliente.setForeground(Color.BLACK);
			add(cbCliente, "14, 7, 5, 1, fill, top");
			
		}
		
		btnBuscarTodos = new JButton("Buscar Todos");
		btnBuscarTodos.setBackground(Color.BLACK);
		btnBuscarTodos.setForeground(Color.WHITE);
		btnBuscarTodos.setFont(new Font("Segoe UI Black", Font.PLAIN, 13));
		btnBuscarTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarTreinos();
			
				
				atualizarTabelaTreinos();
			}
		});
		add(btnBuscarTodos, "20, 7, fill, fill");
		
		lblNivel = new JLabel("Nivel:");
		lblNivel.setFont(new Font("Segoe UI Black", Font.PLAIN, 13));
		lblNivel.setForeground(Color.BLACK);
		add(lblNivel, "12, 9, center, center");
		
		cbNivel = new JComboBox(new String[] {"BÁSICO","INTERMEDIÁRIO","AVANÇADO"});
		cbNivel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		cbNivel.setForeground(Color.BLACK);
		add(cbNivel, "14, 9, 5, 1, fill, top");
		
		lblCliente = new JLabel("Cliente: ");
		lblCliente.setFont(new Font("Segoe UI Black", Font.PLAIN, 13));
		lblCliente.setForeground(Color.BLACK);
		add(lblCliente, "12, 7, center, center");
		
		cbCliente = new JComboBox();
		cbCliente.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		cbCliente.setForeground(Color.BLACK);
		add(cbCliente, "14, 7, 5, 1, fill, top");
		
		tblTreinos = new JTable();
		tblTreinos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		limparTabelaTreinos();
		tblTreinos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int indiceSelecionado = tblTreinos.getSelectedRow();

				if (indiceSelecionado > 0) {
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					btnEncaminharEmailTreino.setEnabled(true);
					treinoSelecionado = treinos.get(indiceSelecionado - 1);
				} else {
					btnEditar.setEnabled(false);
					btnExcluir.setEnabled(false);
					btnEncaminharEmailTreino.setEnabled(false);
				}
			}
		});
		add(tblTreinos, "12, 11, 9, 1, fill, fill");
		
		btnEditar = new JButton("Editar");
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setBackground(Color.BLACK);
		btnEditar.setFont(new Font("Segoe UI Black", Font.PLAIN, 13));
		btnEditar.setEnabled(false);
		add(btnEditar, "12, 13, fill, fill");
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.setBackground(Color.BLACK);
		btnExcluir.setForeground(Color.WHITE);
		btnExcluir.setFont(new Font("Segoe UI Black", Font.PLAIN, 13));
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opcaoSelecionada = JOptionPane.showConfirmDialog(null, "Confirma a exclusão do treino selecionado?");
				
				if(opcaoSelecionada == JOptionPane.YES_OPTION) {
					try {
						controller.excluir(treinoSelecionado.getId());
						JOptionPane.showMessageDialog(null, "Treino excluído com sucesso");
						treinos = (ArrayList<Treino>) controller.consultarTodos();
						atualizarTabelaTreinos();
					} catch (CampoInvalidoException e1) {
						JOptionPane.showConfirmDialog(null, e1.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		add(btnExcluir, "14, 13, fill, top");
		
		btnAvancarPagina = new JButton("Avançar >>");
		btnAvancarPagina.setBackground(Color.BLACK);
		btnAvancarPagina.setForeground(Color.WHITE);
		btnAvancarPagina.setFont(new Font("Segoe UI Black", Font.PLAIN, 13));
		btnAvancarPagina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				paginaAtual++;
				buscarTreinosComFiltros();
				lblPaginacao.setText(paginaAtual + " / " + totalPaginas);
				btnVoltarPagina.setEnabled(paginaAtual > 1);
				btnAvancarPagina.setEnabled(paginaAtual < totalPaginas);
			}
		});
		add(btnAvancarPagina, "18, 13, fill, fill");
		
		lblPaginacao = new JLabel("1 / " + totalPaginas);
		lblPaginacao.setForeground(Color.BLACK);
		lblPaginacao.setFont(new Font("Segoe UI Black", Font.PLAIN, 13));
		lblPaginacao.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblPaginacao, "20, 9, right, fill");
		
		btnVoltarPagina = new JButton("<< Voltar");
		btnVoltarPagina.setFont(new Font("Segoe UI Black", Font.PLAIN, 13));
		btnVoltarPagina.setBackground(Color.BLACK);
		btnVoltarPagina.setForeground(Color.WHITE);
		btnVoltarPagina.setEnabled(false);
		btnVoltarPagina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual--;
				buscarTreinosComFiltros();
				lblPaginacao.setText(paginaAtual + " / " + totalPaginas);
				btnVoltarPagina.setEnabled(paginaAtual > 1);
				btnAvancarPagina.setEnabled(paginaAtual < totalPaginas);
			}
		});
		add(btnVoltarPagina, "20, 13, fill, fill");
		
		btnGerarPlanilha = new JButton("Gerar Planilha");
		btnGerarPlanilha.setFont(new Font("Segoe UI Black", Font.PLAIN, 13));
		btnGerarPlanilha.setBackground(Color.BLACK);
		btnGerarPlanilha.setForeground(Color.WHITE);
		btnGerarPlanilha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser janelaSelecaoDestinoArquivo = new JFileChooser();
				janelaSelecaoDestinoArquivo.setDialogTitle("Selecione um destino para a planilha...");
				int opcaoSelecionada = janelaSelecaoDestinoArquivo.showSaveDialog(null);
				if (opcaoSelecionada == JFileChooser.APPROVE_OPTION) {
					String caminhoEscolhido = janelaSelecaoDestinoArquivo.getSelectedFile().getAbsolutePath();
					String resultado;
					try {
						resultado = controller.gerarPlanilha(treinos, caminhoEscolhido);
						JOptionPane.showMessageDialog(null, resultado);
					} catch (CampoInvalidoException e1) {
						JOptionPane.showConfirmDialog(null, e1.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		
		btnVoltar = new JButton("Pagina Inicial");
		btnVoltar.setBackground(Color.BLACK);
		btnVoltar.setFont(new Font("Segoe UI Black", Font.PLAIN, 13));
		btnVoltar.setForeground(Color.WHITE);
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnEncaminharEmailTreino = new JButton("Encaminhar Email");
		btnEncaminharEmailTreino.setForeground(Color.WHITE);
		btnEncaminharEmailTreino.setFont(new Font("Segoe UI Black", Font.PLAIN, 13));
		btnEncaminharEmailTreino.setEnabled(false);
		btnEncaminharEmailTreino.setBackground(Color.BLACK);
		add(btnEncaminharEmailTreino, "12, 15");
		add(btnVoltar, "18, 15, fill, fill");

		add(btnGerarPlanilha, "20, 15, fill, fill");




	}
	
	protected void buscarTreinos() {
		if(usuarioAutenticado.getTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
			this.treinos = (ArrayList<Treino>) controller.consultarTodos();
		} else {
			this.treinos = (ArrayList<Treino>) controller.consultarTreinosUsuarioAutenticado(usuarioAutenticado.getId());
		}
		
		
		
	}

	protected void buscarTreinosComFiltros() {
//		seletor = new TreinoSeletor();
//		seletor.setLimite(TAMANHO_PAGINA);
//		seletor.setPagina(paginaAtual);
//		seletor.setCliente(cbCliente.getSelectedIndex());
//		seletor.setProfissional(cbProfissional.getSelectedIndex());
//		seletor.setNivel(cbNivel.getSelectedIndex());

		treinos = (ArrayList<Treino>) controller.consultarComFiltros(seletor);
		atualizarTabelaTreinos();
		atualizarQuantidadePaginas();
		
	}

	private void atualizarQuantidadePaginas() {
		//Cálculo do total de páginas
				int totalRegistros = controller.contarTotalRegistrosComFiltros(seletor);
				//QUOCIENTE da divisão inteira
				totalPaginas = totalRegistros / TAMANHO_PAGINA;
				//RESTO da divisão inteira
				if(totalRegistros % TAMANHO_PAGINA > 0) { 
					totalPaginas++;
				}
				lblPaginacao.setText(paginaAtual + " / " + totalPaginas);
	}
	
	public JButton getBtnVoltar() {
		return this.btnVoltar;
	}

		public JButton getBtnEditar() {
			return this.btnEditar;
		}

		public Treino getTreinoSelecionado() {
			return treinoSelecionado;
		}

		public JButton getBtnEncaminharEmailTreino() {
			return btnEncaminharEmailTreino;
		}

		public void encaminharEmailTreino() {
			String nomeCliente = this.treinoSelecionado.getCliente().getPessoa().getNome();
			String emailCliente = this.treinoSelecionado.getCliente().getEmail();
			String emailPersonal = this.treinoSelecionado.getProfissional().getEmail();
			String treinoCliente = this.treinoSelecionado.getTreino();
			String prazoInicial = this.treinoSelecionado.getDtCadastro().toString();
			String prazoFinal = this.treinoSelecionado.getDtTermino().toString();
			String nomePersonal = this.treinoSelecionado.getProfissional().getPessoa().getNome();
			
			Email email = new Email(nomeCliente, emailCliente, emailPersonal, treinoCliente, prazoInicial, prazoFinal, nomePersonal);
			
			GerenciadorEmail gerenciadorEmail = new GerenciadorEmail();
			gerenciadorEmail.encaminharEmail(email);
			
			
		}


		
}
