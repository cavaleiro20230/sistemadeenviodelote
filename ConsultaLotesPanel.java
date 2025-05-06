package com.contabilidade.ui;

import com.contabilidade.model.Lote;
import com.contabilidade.service.LoteService;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class ConsultaLotesPanel extends JPanel {
    
    private JTextField txtDataInicio;
    private JTextField txtDataFim;
    private JComboBox<String> cmbStatus;
    private JButton btnPesquisar;
    private JTable tblLotes;
    private DefaultTableModel tableModel;
    private JButton btnVisualizar;
    private JButton btnReenviar;
    private JButton btnCancelar;
    
    private final LoteService loteService;
    
    public ConsultaLotesPanel() {
        this.loteService = new LoteService();
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        // Painel de filtros
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.setBorder(new TitledBorder("Filtros"));
        
        // Data início
        filterPanel.add(new JLabel("Data Início:"));
        txtDataInicio = new JTextField(10);
        LocalDate dataInicio = LocalDate.now().minusMonths(1);
        txtDataInicio.setText(dataInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        filterPanel.add(txtDataInicio);
        
        // Data fim
        filterPanel.add(new JLabel("Data Fim:"));
        txtDataFim = new JTextField(10);
        txtDataFim.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        filterPanel.add(txtDataFim);
        
        // Status
        filterPanel.add(new JLabel("Status:"));
        String[] statusOptions = {"Todos", "Pendente", "Enviado", "Erro"};
        cmbStatus = new JComboBox<>(statusOptions);
        filterPanel.add(cmbStatus);
        
        // Botão pesquisar
        btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.addActionListener((ActionEvent e) -> {
            pesquisarLotes();
        });
        filterPanel.add(btnPesquisar);
        
        add(filterPanel, BorderLayout.NORTH);
        
        // Tabela de resultados
        String[] colunas = {"ID", "Nome", "Tipo", "Data Envio", "Status"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblLotes = new JTable(tableModel);
        tblLotes.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblLotes.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblLotes.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblLotes.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblLotes.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(tblLotes);
        add(scrollPane, BorderLayout.CENTER);
        
        // Painel de ações
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnVisualizar = new JButton("Visualizar");
        btnVisualizar.addActionListener((ActionEvent e) -> {
            visualizarLote();
        });
        
        btnReenviar = new JButton("Reenviar");
        btnReenviar.addActionListener((ActionEvent e) -> {
            reenviarLote();
        });
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener((ActionEvent e) -> {
            cancelarLote();
        });
        
        actionPanel.add(btnVisualizar);
        actionPanel.add(btnReenviar);
        actionPanel.add(btnCancelar);
        
        add(actionPanel, BorderLayout.SOUTH);
        
        // Carregar dados iniciais
        pesquisarLotes();
    }
    
    private void pesquisarLotes() {
        try {
            // Limpar tabela
            tableModel.setRowCount(0);
            
            // Obter filtros
            String status = cmbStatus.getSelectedItem().toString();
            if (status.equals("Todos")) {
                status = null;
            }
            
            // Buscar lotes
            List<Lote> lotes = loteService.consultarLotes(status);
            
            // Preencher tabela
            for (Lote lote : lotes) {
                Object[] row = {
                    lote.getId(),
                    lote.getNome(),
                    lote.getTipo(),
                    lote.getDataEnvio(),
                    lote.getStatus()
                };
                tableModel.addRow(row);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                    "Erro ao consultar lotes: " + ex.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void visualizarLote() {
        int selectedRow = tblLotes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                    "Selecione um lote para visualizar.", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Long idLote = (Long) tableModel.getValueAt(selectedRow, 0);
        Lote lote = loteService.buscarLotePorId(idLote);
        
        if (lote != null) {
            DetalhesLoteDialog dialog = new DetalhesLoteDialog(null, lote);
            dialog.setVisible(true);
        }
    }
    
    private void reenviarLote() {
        int selectedRow = tblLotes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                    "Selecione um lote para reenviar.", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Long idLote = (Long) tableModel.getValueAt(selectedRow, 0);
        String status = (String) tableModel.getValueAt(selectedRow, 4);
        
        if (!"Erro".equals(status)) {
            JOptionPane.showMessageDialog(this, 
                    "Apenas lotes com erro podem ser reenviados.", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Deseja realmente reenviar este lote?", 
                "Confirmação", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean sucesso = loteService.reenviarLote(idLote);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                            "Lote reenviado com sucesso!", 
                            "Sucesso", 
                            JOptionPane.INFORMATION_MESSAGE);
                    pesquisarLotes();
                } else {
                    JOptionPane.showMessageDialog(this, 
                            "Erro ao reenviar o lote.", 
                            "Erro", 
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                        "Erro ao reenviar lote: " + ex.getMessage(), 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cancelarLote() {
        int selectedRow = tblLotes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                    "Selecione um lote para cancelar.", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Long idLote = (Long) tableModel.getValueAt(selectedRow, 0);
        String status = (String) tableModel.getValueAt(selectedRow, 4);
        
        if ("Enviado".equals(status)) {
            JOptionPane.showMessageDialog(this, 
                    "Lotes já enviados não podem ser cancelados.", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Deseja realmente cancelar este lote?", 
                "Confirmação", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean sucesso = loteService.cancelarLote(idLote);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                            "Lote cancelado com sucesso!", 
                            "Sucesso", 
                            JOptionPane.INFORMATION_MESSAGE);
                    pesquisarLotes();
                } else {
                    JOptionPane.showMessageDialog(this, 
                            "Erro ao cancelar o lote.", 
                            "Erro", 
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                        "Erro ao cancelar lote: " + ex.getMessage(), 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}