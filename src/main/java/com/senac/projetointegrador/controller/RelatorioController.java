package com.senac.projetointegrador.controller;

import com.senac.projetointegrador.entities.Consulta;
import com.senac.projetointegrador.entities.Medico;
import com.senac.projetointegrador.entities.Paciente;
import com.senac.projetointegrador.entities.Prescricao;
import com.senac.projetointegrador.repositories.MedicoRepository;
import com.senac.projetointegrador.repositorys.ConsultaRepository;
import com.senac.projetointegrador.repositorys.PacienteRepository;
import com.senac.projetointegrador.repositorys.PrescricaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import java.util.List;

@Controller
public class RelatorioController {

    @Autowired(required = false)
    private ConsultaRepository consultaRepository;

    @Autowired(required = false)
    private PacienteRepository pacienteRepository;
    
    @Autowired(required = false)
    private MedicoRepository medicoRepository;
    
    @Autowired(required = false)
    private PrescricaoRepository prescricaoRepository;

    @GetMapping("/relatorio-medico")
    public String gerarRelatorio(
            @RequestParam String consultaId,
            HttpSession session,
            Model model) {
        
        // Verificar permissão (médico ou recepcionista)
        Object tipoUsuario = session.getAttribute("tipoUsuario");
        if (!"medico".equals(tipoUsuario) && !"recepcionista".equals(tipoUsuario)) {
            return "redirect:/login";
        }
        
        // Buscar dados da consulta
        if (consultaRepository != null) {
            Optional<Consulta> consultaOpt = consultaRepository.findById(consultaId);
            if (consultaOpt.isPresent()) {
                Consulta consulta = consultaOpt.get();
                model.addAttribute("consulta", consulta);
                
                // Buscar dados do paciente
                if (pacienteRepository != null && consulta.getIdPaciente() != null) {
                    pacienteRepository.findById(consulta.getIdPaciente())
                        .ifPresent(paciente -> model.addAttribute("paciente", paciente));
                }
                
                // Buscar dados do médico
                if (medicoRepository != null && consulta.getProfissional() != null) {
                    medicoRepository.findByNome(consulta.getProfissional())
                        .ifPresent(medico -> model.addAttribute("medico", medico));
                }
                
                // Buscar prescrições
                if (prescricaoRepository != null && consulta.getIdPaciente() != null) {
                    model.addAttribute("prescricoes", prescricaoRepository.findByPacienteId(consulta.getIdPaciente()));
                }
            }
        }
        
        return "Views/RelatorioMedico/index";
    }

    @GetMapping("/relatorio-consultas")
    public String relatorioConsultasMedico(HttpSession session, Model model) {
        Object usuario = session.getAttribute("usuarioLogado");
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        if (usuario == null || !"medico".equals(tipoUsuario)) {
            return "redirect:/login";
        }
        Medico medico = (Medico) usuario;
        if (consultaRepository != null) {
            List<Consulta> consultas = consultaRepository.findByProfissional(medico.getNome());
            model.addAttribute("consultas", consultas);
        }
        return "Views/RelatorioMedico/relatorioConsultas";
    }
}
