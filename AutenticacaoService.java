package com.contabilidade.service;

import com.contabilidade.model.Usuario;

public class AutenticacaoService {
    
    // Simulação de autenticação - em um sistema real, isso seria conectado a um banco de dados
    public Usuario autenticar(String login, String senha) {
        // Simulando um usuário para teste
        if ("admin".equals(login) && "admin".equals(senha)) {
            return new Usuario(1L, "Administrador", "admin", "admin", "admin@exemplo.com", true);
        }
        
        return null;
    }
}