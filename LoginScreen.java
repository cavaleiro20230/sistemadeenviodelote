package com.contabilidade.ui;

import com.contabilidade.model.Usuario;
import com.contabilidade.service.AutenticacaoService;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginScreen extends JFrame {
    
    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnLogin;
    private JButton btnCancelar;
    private final AutenticacaoService autenticacaoService;
    
    public LoginScreen() {
        autenticacaoService = new AutenticacaoService();
        configurarJanela();
        inicializarComponentes();
    }
    
    private void configurarJanela() {
        setTitle("Sistema de Envio de Lote Contabilidade - Nasajon");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void inicializarComponentes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("Login do Sistema");
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("Usuário:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        txtUsuario = new JTextField(20);
        loginPanel.add(txtUsuario, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        loginPanel.add(new JLabel("Senha:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        txtSenha = new JPasswordField(20);
        loginPanel.add(txtSenha, gbc);
        
        panel.add(loginPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        btnLogin = new JButton("Login");
        btnCancelar = new JButton("Cancelar");
        
        btnLogin.addActionListener(e -> realizarLogin());
        btnCancelar.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnCancelar);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(panel);
    }
    
    private void realizarLogin() {
        String usuario = txtUsuario.getText();
        String senha = new String(txtSenha.getPassword());
        
        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Por favor, preencha todos os campos.", 
                    "Erro de Login", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            Usuario usuarioAutenticado = autenticacaoService.autenticar(usuario, senha);
            if (usuarioAutenticado != null) {
                MainScreen mainScreen = new MainScreen(usuarioAutenticado);
                mainScreen.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                        "Usuário ou senha inválidos.", 
                        "Erro de Login", 
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                    "Erro ao realizar login: " + ex.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}