package com.senac.projetointegrador.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.senac.projetointegrador.entities.Paciente;
import com.senac.projetointegrador.repositorys.PacienteRepository;
import com.senac.projetointegrador.repositorys.PrescricaoRepository;
import com.senac.projetointegrador.service.AuthService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PacienteController {

    @Autowired(required = false)
    private PacienteRepository pacienteRepository;
    
    @Autowired(required = false)
    private PrescricaoRepository prescricaoRepository;
    
    @Autowired
    private AuthService authService;

    @GetMapping("/cadastro-paciente")
    public String formularioCadastro(HttpSession session) {
        if (session.getAttribute("usuarioLogado") != null) {
            return "redirect:/";
        }
        return "Views/Cadastro_Paciente";
    }
    
    @PostMapping("/pacientes")
    public String cadastrarPaciente(
            @RequestParam String nome,
            @RequestParam String cpf,
            @RequestParam String email,
            @RequestParam String telefone,
            @RequestParam String sexo,
            @RequestParam String cidade,
            @RequestParam String estado,
            @RequestParam String senha,
            @RequestParam String data,
            HttpSession session,
            Model model) {
        
        if (session.getAttribute("usuarioLogado") != null) {
            return "redirect:/";
        }

        try {
            // Parse the date
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date dataDeNascimento = null;
            try {
                dataDeNascimento = formatter.parse(data);
            } catch (Exception e) {
                // Se não conseguir parsear a data, continua sem ela
                System.err.println("Erro ao parsear a data: " + e.getMessage());
            }
            
            // Create paciente object
            Paciente paciente = new Paciente(
                nome, cpf, email, telefone, 
                sexo, cidade, estado, senha, dataDeNascimento);
            
            // Save to MongoDB if available
            if (pacienteRepository != null) {
                try {
                    pacienteRepository.save(paciente);
                } catch (Exception e) {
                    System.err.println("Erro ao salvar no MongoDB: " + e.getMessage());
                    // Continue to in-memory storage
                }
            }
            
            // Add to in-memory test users for fallback authentication
            authService.addTestUser(paciente);
            
            return "redirect:/cadastro-sucesso";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao cadastrar: " + e.getMessage());
            return "Views/Cadastro_Paciente";
        }
    }

    @GetMapping("/paciente/prescricoes")
    public String verPrescricoes(HttpSession session, Model model) {
        Object usuario = session.getAttribute("usuarioLogado");
        if (usuario == null || !(usuario instanceof Paciente)) {
            return "redirect:/login";
        }
        Paciente paciente = (Paciente) usuario;
        if (prescricaoRepository != null) {
            model.addAttribute("prescricoes", prescricaoRepository.findByPacienteId(paciente.getId()));
        }
        return "Views/Prescrever/relatorioPaciente";
    }
}
