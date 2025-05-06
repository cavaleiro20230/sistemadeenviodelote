package com.contabilidade.ui;

import com.contabilidade.model.Lote;
import com.contabilidade.service.LoteService;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class NovoLotePanel extends JPanel {
    
    private JTextField txtNomeLote;
    private JTextField txtDataReferencia;
    private JComboBox<String> cmbTipoLote;
    private JTextField txtArquivo;
    private JButton btnSelecionarArquivo;
    private JTextArea txtDescricao;
    private JButton btnEnviar;
    private JButton btnLimpar;
    
    private final LoteService loteService;
    private File arquivoSelecionado;
    
    public NovoLotePanel() {
        this.loteService = new LoteService();
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        // Painel de formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new TitledBorder("Dados do Lote"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Nome do lote
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nome do Lote:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        txtNomeLote = new JTextField(20);
        formPanel.add(txtNomeLote, gbc);
        
        // Data de referência
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Data de Referência:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        txtDataReferencia = new JTextField(10);
        txtDataReferencia.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        formPanel.add(txtDataReferencia, gbc);
        
        // Tipo de lote
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Tipo de Lote:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        String[] tiposLote = {"Lançamentos Contábeis", "Notas Fiscais", "Folha de Pagamento", "Outros"};
        cmbTipoLote = new JComboBox<>(tiposLote);
        formPanel.add(cmbTipoLote, gbc);
        
        // Arquivo
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Arquivo:"), gbc);
        
        JPanel filePanel = new JPanel(new BorderLayout(5, 0));
        txtArquivo = new JTextField(20);
        txtArquivo.setEditable(false);
        btnSelecionarArquivo = new JButton("Selecionar...");
        btnSelecionarArquivo.addActionListener(e -> selecionarArquivo());
        
        filePanel.add(txtArquivo, BorderLayout.CENTER);
        filePanel.add(btnSelecionarArquivo, BorderLayout.EAST);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        formPanel.add(filePanel, gbc);
        
        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Descrição:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtDescricao = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(txtDescricao);
        formPanel.add(scrollPane, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnEnviar = new JButton("Enviar Lote");
        btnLimpar = new JButton("Limpar");
        
        btnEnviar.addActionListener(e -> enviarLote());
        btnLimpar.addActionListener(e -> limparFormulario());
        
        buttonPanel.add(btnLimpar);
        buttonPanel.add(btnEnviar);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void selecionarArquivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecionar Arquivo de Lote");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos XML, CSV, TXT", "xml", "csv", "txt"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            arquivoSelecionado = fileChooser.getSelectedFile();
            txtArquivo.setText(arquivoSelecionado.getAbsolutePath());
        }
    }
    
    private void enviarLote() {
        // Validar campos
        if (txtNomeLote.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Por favor, informe o nome do lote.", 
                    "Validação", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (arquivoSelecionado == null) {
            JOptionPane.showMessageDialog(this, 
                    "Por favor, selecione um arquivo para envio.", 
                    "Validação", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Criar objeto de lote
            Lote lote = new Lote();
            lote.setNome(txtNomeLote.getText().trim());
            lote.setTipo(cmbTipoLote.getSelectedItem().toString());
            lote.setDescricao(txtDescricao.getText().trim());
            lote.setArquivo(arquivoSelecionado);
            
            // Enviar lote
            boolean sucesso = loteService.enviarLote(lote);
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, 
                        "Lote enviado com sucesso!", 
                        "Sucesso", 
                        JOptionPane.INFORMATION_MESSAGE);
                limparFormulario();
            } else {
                JOptionPane.showMessageDialog(this, 
                        "Erro ao enviar o lote. Verifique os dados e tente novamente.", 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                    "Erro ao processar o lote: " + ex.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limparFormulario() {
        txtNomeLote.setText("");
        txtDataReferencia.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        cmbTipoLote.setSelectedIndex(0);
        txtArquivo.setText("");
        txtDescricao.setText("");
        arquivoSelecionado = null;
    }
}