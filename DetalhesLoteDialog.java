package com.contabilidade.ui;

import com.contabilidade.model.Lote;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class DetalhesLoteDialog extends JDialog {
    
    private final Lote lote;
    
    public DetalhesLoteDialog(Frame parent, Lote lote) {
        super(parent, "Detalhes do Lote", true);
        this.lote = lote;
        
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(new JLabel("ID:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        JTextField txtId = new JTextField(String.valueOf(lote.getId()));
        txtId.setEditable(false);
        contentPanel.add(txtId, gbc);
        
        // Nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Nome:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        JTextField txtNome = new JTextField(lote.getNome());
        txtNome.setEditable(false);
        contentPanel.add(txtNome, gbc);
        
        // Tipo
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Tipo:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        JTextField txtTipo = new JTextField(lote.getTipo());
        txtTipo.setEditable(false);
        contentPanel.add(txtTipo, gbc);
        
        // Data Envio
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Data Envio:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        JTextField txtDataEnvio = new JTextField(lote.getDataEnvio());
        txtDataEnvio.setEditable(false);
        contentPanel.add(txtDataEnvio, gbc);
        
        // Status
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Status:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        JTextField txtStatus = new JTextField(lote.getStatus());
        txtStatus.setEditable(false);
        contentPanel.add(txtStatus, gbc);
        
        // Arquivo
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Arquivo:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        JTextField txtArquivo = new JTextField(lote.getArquivo() != null ? lote.getArquivo().getAbsolutePath() : "");
        txtArquivo.setEditable(false);
        contentPanel.add(txtArquivo, gbc);
        
        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.0;
        contentPanel.add(new JLabel("Descrição:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JTextArea txtDescricao = new JTextArea(lote.getDescricao());
        txtDescricao.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtDescricao);
        scrollPane.setPreferredSize(new Dimension(300, 100));
        contentPanel.add(scrollPane, gbc);
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Botão fechar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        buttonPanel.add(btnFechar);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
}