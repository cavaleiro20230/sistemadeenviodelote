package com.contabilidade.ui;

import com.contabilidade.model.Usuario;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class MainScreen extends JFrame {
    
    private final Usuario usuarioLogado;
    private JTabbedPane tabbedPane;
    
    public MainScreen(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        configurarJanela();
        inicializarComponentes();
    }
    
    private void configurarJanela() {
        setTitle("Sistema de Envio de Lote Contabilidade - Nasajon");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void inicializarComponentes() {
        // Configurar menu
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuArquivo = new JMenu("Arquivo");
        JMenuItem itemSair = new JMenuItem("Sair");
        itemSair.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        menuArquivo.add(itemSair);
        
        JMenu menuLotes = new JMenu("Lotes");
        JMenuItem itemNovoLote = new JMenuItem("Novo Lote");
        itemNovoLote.addActionListener((ActionEvent e) -> {
            abrirTelaNovoLote();
        });
        
        JMenuItem itemConsultarLotes = new JMenuItem("Consultar Lotes");
        itemConsultarLotes.addActionListener((ActionEvent e) -> {
            abrirTelaConsultaLotes();
        });
        
        menuLotes.add(itemNovoLote);
        menuLotes.add(itemConsultarLotes);
        
        JMenu menuConfiguracoes = new JMenu("Configurações");
        JMenuItem itemConfigConexao = new JMenuItem("Configurar Conexão");
        itemConfigConexao.addActionListener((ActionEvent e) -> {
            abrirTelaConfiguracao();
        });
        menuConfiguracoes.add(itemConfigConexao);
        
        menuBar.add(menuArquivo);
        menuBar.add(menuLotes);
        menuBar.add(menuConfiguracoes);
        
        setJMenuBar(menuBar);
        
        // Painel principal com abas
        tabbedPane = new JTabbedPane();
        
        // Adicionar painel de dashboard como aba inicial
        DashboardPanel dashboardPanel = new DashboardPanel(usuarioLogado);
        tabbedPane.addTab("Dashboard", dashboardPanel);
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPanel.add(tabbedPane, BorderLayout.CENTER);
        
        add(contentPanel);
    }
    
    private void abrirTelaNovoLote() {
        // Verificar se já existe uma aba de novo lote
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (tabbedPane.getTitleAt(i).equals("Novo Lote")) {
                tabbedPane.setSelectedIndex(i);
                return;
            }
        }
        
        // Criar nova aba
        NovoLotePanel novoLotePanel = new NovoLotePanel();
        tabbedPane.addTab("Novo Lote", novoLotePanel);
        tabbedPane.setSelectedComponent(novoLotePanel);
    }
    
    private void abrirTelaConsultaLotes() {
        // Verificar se já existe uma aba de consulta
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (tabbedPane.getTitleAt(i).equals("Consulta de Lotes")) {
                tabbedPane.setSelectedIndex(i);
                return;
            }
        }
        
        // Criar nova aba
        ConsultaLotesPanel consultaPanel = new ConsultaLotesPanel();
        tabbedPane.addTab("Consulta de Lotes", consultaPanel);
        tabbedPane.setSelectedComponent(consultaPanel);
    }
    
    private void abrirTelaConfiguracao() {
        // Verificar se já existe uma aba de configuração
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (tabbedPane.getTitleAt(i).equals("Configurações")) {
                tabbedPane.setSelectedIndex(i);
                return;
            }
        }
        
        // Criar nova aba
        ConfiguracaoPanel configPanel = new ConfiguracaoPanel();
        tabbedPane.addTab("Configurações", configPanel);
        tabbedPane.setSelectedComponent(configPanel);
    }
}