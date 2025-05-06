package com.contabilidade.ui;

import com.contabilidade.model.Usuario;
import com.contabilidade.service.LoteService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DashboardPanel extends JPanel {
    
    private final Usuario usuario;
    private final LoteService loteService;
    
    public DashboardPanel(Usuario usuario) {
        this.usuario = usuario;
        this.loteService = new LoteService();
        setLayout(new BorderLayout(10, 10));
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        // Painel de boas-vindas
        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblWelcome = new JLabel("Bem-vindo, " + usuario.getNome() + "!");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 18));
        welcomePanel.add(lblWelcome);
        
        add(welcomePanel, BorderLayout.NORTH);
        
        // Painel de estatísticas
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        
        // Card de lotes pendentes
        JPanel pendingPanel = createStatCard("Lotes Pendentes", 
                String.valueOf(loteService.contarLotesPendentes()), 
                new Color(255, 204, 0));
        
        // Card de lotes enviados
        JPanel sentPanel = createStatCard("Lotes Enviados", 
                String.valueOf(loteService.contarLotesEnviados()), 
                new Color(0, 153, 51));
        
        // Card de lotes com erro
        JPanel errorPanel = createStatCard("Lotes com Erro", 
                String.valueOf(loteService.contarLotesComErro()), 
                new Color(204, 0, 0));
        
        statsPanel.add(pendingPanel);
        statsPanel.add(sentPanel);
        statsPanel.add(errorPanel);
        
        add(statsPanel, BorderLayout.CENTER);
        
        // Painel de ações rápidas
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionsPanel.setBorder(BorderFactory.createTitledBorder("Ações Rápidas"));
        
        add(actionsPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(color, 2));
        panel.setPreferredSize(new Dimension(200, 100));
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel lblValue = new JLabel(value);
        lblValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblValue.setFont(new Font("Arial", Font.BOLD, 24));
        lblValue.setForeground(color);
        
        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(lblValue, BorderLayout.CENTER);
        
        return panel;
    }
}