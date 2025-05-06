package com.contabilidade.ui;

import com.contabilidade.service.ConfiguracaoService;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class ConfiguracaoPanel extends JPanel {
    
    private JTextField txtServidorNasajon;
    private JTextField txtPorta;
    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JTextField txtTimeoutConexao;
    private JButton btnTestarConexao;
    private JButton btnSalvar;
    
    private final ConfiguracaoService configuracaoService;
    
    public ConfiguracaoPanel() {
        this.configuracaoService = new ConfiguracaoService();
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        inicializarComponentes();
        carregarConfiguracoes();
    }
    
    private void inicializarComponentes() {
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Aba de Conexão
        JPanel conexaoPanel = new JPanel(new BorderLayout(10, 10));
        conexaoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new TitledBorder("Configurações de Conexão com Nasajon"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Servidor
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Servidor:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        txtServidorNasajon = new JTextField(20);
        formPanel.add(txtServidorNasajon, gbc);
        
        // Porta
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Porta:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        txtPorta = new JTextField(5);
        formPanel.add(txtPorta, gbc);
        
        // Usuário
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Usuário:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        txtUsuario = new JTextField(20);
        formPanel.add(txtUsuario, gbc);
        
        // Senha
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Senha:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        txtSenha = new JPasswordField(20);
        formPanel.add(txtSenha, gbc);
        
        // Timeout
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Timeout (segundos):"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        txtTimeoutConexao = new JTextField(5);
        formPanel.add(txtTimeoutConexao, gbc);
        
        conexaoPanel.add(formPanel, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel();
        btnTestarConexao = new JButton("Testar Conexão");
        btnSalvar = new JButton("Salvar");
        
        btnTestarConexao.addActionListener(e -> testarConexao());
        btnSalvar.addActionListener(e -> salvarConfiguracoes());
        
        buttonPanel.add(btnTestarConexao);
        buttonPanel.add(btnSalvar);
        
        conexaoPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        tabbedPane.addTab("Conexão", conexaoPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private void carregarConfiguracoes() {
        try {
            txtServidorNasajon.setText(configuracaoService.getServidor());
            txtPorta.setText(configuracaoService.getPorta());
            txtUsuario.setText(configuracaoService.getUsuario());
            txtSenha.setText(configuracaoService.getSenha());
            txtTimeoutConexao.setText(configuracaoService.getTimeout());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                    "Erro ao carregar configurações: " + ex.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void salvarConfiguracoes() {
        try {
            configuracaoService.setServidor(txtServidorNasajon.getText().trim());
            configuracaoService.setPorta(txtPorta.getText().trim());
            configuracaoService.setUsuario(txtUsuario.getText().trim());
            configuracaoService.setSenha(new String(txtSenha.getPassword()));
            configuracaoService.setTimeout(txtTimeoutConexao.getText().trim());
            
            boolean sucesso = configuracaoService.salvarConfiguracoes();
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, 
                        "Configurações salvas com sucesso!", 
                        "Sucesso", 
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                        "Erro ao salvar configurações.", 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                    "Erro ao salvar configurações: " + ex.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void testarConexao() {
        try {
            boolean sucesso = configuracaoService.testarConexao(
                    txtServidorNasajon.getText().trim(),
                    txtPorta.getText().trim(),
                    txtUsuario.getText().trim(),
                    new String(txtSenha.getPassword()));
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, 
                        "Conexão realizada com sucesso!", 
                        "Sucesso", 
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                        "Não foi possível conectar ao servidor Nasajon.", 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                    "Erro ao testar conexão: " + ex.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}