package com.contabilidade.service;

import java.util.Properties;
import java.util.Random;

public class ConfiguracaoService {
    
    private Properties configuracoes;
    
    public ConfiguracaoService() {
        configuracoes = new Properties();
        // Valores padrão para simulação
        configuracoes.setProperty("servidor", "api.nasajon.com.br");
        configuracoes.setProperty("porta", "443");
        configuracoes.setProperty("usuario", "usuario_api");
        configuracoes.setProperty("senha", "senha_api");
        configuracoes.setProperty("timeout", "30");
    }
    
    public String getServidor() {
        return configuracoes.getProperty("servidor", "");
    }
    
    public void setServidor(String servidor) {
        configuracoes.setProperty("servidor", servidor);
    }
    
    public String getPorta() {
        return configuracoes.getProperty("porta", "");
    }
    
    public void setPorta(String porta) {
        configuracoes.setProperty("porta", porta);
    }
    
    public String getUsuario() {
        return configuracoes.getProperty("usuario", "");
    }
    
    public void setUsuario(String usuario) {
        configuracoes.setProperty("usuario", usuario);
    }
    
    public String getSenha() {
        return configuracoes.getProperty("senha", "");
    }
    
    public void setSenha(String senha) {
        configuracoes.setProperty("senha", senha);
    }
    
    public String getTimeout() {  {
        configuracoes.setProperty("senha", senha);
    }
    
    public String getTimeout() {
        return configuracoes.getProperty("timeout", "30");
    }
    
    public void setTimeout(String timeout) {
        configuracoes.setProperty("timeout", timeout);
    }
    
    public boolean salvarConfiguracoes() {
        try {
            // Simulação de salvamento em arquivo ou banco de dados
            Thread.sleep(1000);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean testarConexao(String servidor, String porta, String usuario, String senha) {
        try {
            // Simulação de teste de conexão
            Thread.sleep(2000);
            
            // Simulando 90% de chance de sucesso na conexão
            Random random = new Random();
            return random.nextDouble() < 0.9;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}