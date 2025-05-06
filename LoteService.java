package com.contabilidade.service;

import com.contabilidade.model.Lote;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoteService {
    
    private static final List<Lote> lotesSimulados = new ArrayList<>();
    private static Long proximoId = 1L;
    
    static {
        // Simulando alguns lotes para teste
        for (int i = 1; i <= 10; i++) {
            Lote lote = new Lote();
            lote.setId(proximoId++);
            lote.setNome("Lote de teste " + i);
            lote.setTipo(i % 3 == 0 ? "Notas Fiscais" : (i % 2 == 0 ? "Lançamentos Contábeis" : "Folha de Pagamento"));
            lote.setDescricao("Descrição do lote de teste " + i);
            lote.setDataEnvio(LocalDateTime.now().minusDays(i));
            
            // Distribuir status para simulação
            if (i <= 5) {
                lote.setStatus("Enviado");
            } else if (i <= 8) {
                lote.setStatus("Pendente");
            } else {
                lote.setStatus("Erro");
                lote.setMensagemErro("Erro de conexão com o servidor Nasajon");
            }
            
            lotesSimulados.add(lote);
        }
    }
    
    public boolean enviarLote(Lote lote) {
        try {
            // Simulação de envio para o sistema Nasajon
            Thread.sleep(1500); // Simular processamento
            
            Random random = new Random();
            boolean sucesso = random.nextDouble() < 0.8; // 80% de chance de sucesso
            
            lote.setId(proximoId++);
            lote.setDataEnvio(LocalDateTime.now());
            
            if (sucesso) {
                lote.setStatus("Enviado");
            } else {
                lote.setStatus("Erro");
                lote.setMensagemErro("Erro ao processar o lote no servidor Nasajon");
            }
            
            lotesSimulados.add(lote);
            return sucesso;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public List<Lote> consultarLotes(String status) {
        if (status == null || status.isEmpty()) {
            return new ArrayList<>(lotesSimulados);
        }
        
        List<Lote> resultado = new ArrayList<>();
        for (Lote lote : lotesSimulados) {
            if (status.equals(lote.getStatus())) {
                resultado.add(lote);
            }
        }
        
        return resultado;
    }
    
    public Lote buscarLotePorId(Long id) {
        for (Lote lote : lotesSimulados) {
            if (lote.getId().equals(id)) {
                return lote;
            }
        }
        return null;
    }
    
    public boolean reenviarLote(Long id) {
        Lote lote = buscarLotePorId(id);
        if (lote != null && "Erro".equals(lote.getStatus())) {
            try {
                // Simulação de reenvio
                Thread.sleep(1500);
                
                Random random = new Random();
                boolean sucesso = random.nextDouble() < 0.8; // 80% de chance de sucesso
                
                lote.setDataEnvio(LocalDateTime.now());
                
                if (sucesso) {
                    lote.setStatus("Enviado");
                    lote.setMensagemErro(null);
                }
                
                return sucesso;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return false;
    }
    
    public boolean cancelarLote(Long id) {
        Lote lote = buscarLotePorId(id);
        if (lote != null && !"Enviado".equals(lote.getStatus())) {
            lotesSimulados.remove(lote);
            return true;
        }
        return false;
    }
    
    public int contarLotesPendentes() {
        int count = 0;
        for (Lote lote : lotesSimulados) {
            if ("Pendente".equals(lote.getStatus())) {
                count++;
            }
        }
        return count;
    }
    
    public int contarLotesEnviados() {
        int count = 0;
        for (Lote lote : lotesSimulados) {
            if ("Enviado".equals(lote.getStatus())) {
                count++;
            }
        }
        return count;
    }
    
    public int contarLotesComErro() {
        int count = 0;
        for (Lote lote : lotesSimulados) {
            if ("Erro".equals(lote.getStatus())) {
                count++;
            }
        }
        return count;
    }
}