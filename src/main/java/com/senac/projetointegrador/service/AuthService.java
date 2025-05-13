package com.senac.projetointegrador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.projetointegrador.entities.Medico;
import com.senac.projetointegrador.entities.Paciente;
import com.senac.projetointegrador.entities.Recepcionista;
import com.senac.projetointegrador.repositorys.PacienteRepository;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired(required = false)
    private PacienteRepository pacienteRepository;
    
    // Simulação em memória para testes quando MongoDB estiver offline
    private static final Map<String, Paciente> testUsers = new HashMap<>();
    
    static {
        // Adicionar usuário de teste
        Paciente testUser = new Paciente("Usuário Teste", "123.456.789-00", 
                "teste@teste.com", "11999999999", "Masculino", 
                "São Paulo", "SP", "123456", null);
        testUser.setId("mock-id");
        testUsers.put("teste@teste.com", testUser);
    }
    
    public void addTestUser(Paciente paciente) {
        testUsers.put(paciente.getEmail(), paciente);
    }

    public boolean authenticateUser(String credencial, String senha, HttpSession session) {
        // Primeiro tenta autenticar com o MongoDB
        if (pacienteRepository != null) {
            try {
                Optional<Paciente> pacienteOpt = pacienteRepository.findByEmail(credencial);
                if (pacienteOpt.isPresent() && pacienteOpt.get().getSenha().equals(senha)) {
                    session.setAttribute("usuarioLogado", pacienteOpt.get());
                    session.setAttribute("tipoUsuario", "paciente");
                    return true;
                }
            } catch (Exception e) {
                System.err.println("Erro ao buscar paciente no MongoDB: " + e.getMessage());
                // Continua para o fallback em memória
            }
        }
        
        // Fallback para autenticação em memória quando MongoDB está offline
        Paciente testPaciente = testUsers.get(credencial);
        if (testPaciente != null && testPaciente.getSenha().equals(senha)) {
            session.setAttribute("usuarioLogado", testPaciente);
            session.setAttribute("tipoUsuario", "paciente");
            return true;
        }
        
        return false;
    }
}
