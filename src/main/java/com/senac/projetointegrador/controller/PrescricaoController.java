package com.senac.projetointegrador.controller;

import com.senac.projetointegrador.entities.Consulta;
import com.senac.projetointegrador.entities.Prescricao;
import com.senac.projetointegrador.repositorys.ConsultaRepository;
import com.senac.projetointegrador.repositorys.PacienteRepository;
import com.senac.projetointegrador.repositorys.PrescricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Controller
public class PrescricaoController {

    @Autowired(required = false)
    private ConsultaRepository consultaRepository;

    @Autowired(required = false)
    private PacienteRepository pacienteRepository;

    @Autowired(required = false)
    private PrescricaoRepository prescricaoRepository;

    @GetMapping("/prescrever")
    public String abrirPrescricao(@RequestParam("consultaId") String consultaId, HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogado") == null || !"medico".equals(session.getAttribute("tipoUsuario"))) {
            return "redirect:/login";
        }
        if (consultaRepository != null) {
            Optional<Consulta> consultaOpt = consultaRepository.findById(consultaId);
            if (consultaOpt.isPresent()) {
                Consulta consulta = consultaOpt.get();
                model.addAttribute("consulta", consulta);
                model.addAttribute("pacienteId", consulta.getIdPaciente());
            }
        }
        return "Views/Prescrever/index";
    }

    @PostMapping("/prescrever")
    public String salvarPrescricao(
            @RequestParam String consultaId,
            @RequestParam String pacienteId,
            @RequestParam String doseDeUso,
            @RequestParam String remedio,
            @RequestParam String remedioAlternativo,
            @RequestParam String dataInicio,
            @RequestParam String dataFim,
            @RequestParam String observacao,
            HttpSession session,
            Model model) {
        if (session.getAttribute("usuarioLogado") == null || !"medico".equals(session.getAttribute("tipoUsuario"))) {
            return "redirect:/login";
        }
        try {
            // Salva prescrição
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date inicio = sdf.parse(dataInicio);
            Date fim = sdf.parse(dataFim);

            Prescricao prescricao = new Prescricao(
                UUID.randomUUID().toString(),
                pacienteId,
                doseDeUso,
                remedio,
                remedioAlternativo,
                inicio,
                fim,
                observacao
            );
            if (prescricaoRepository != null) {
                prescricaoRepository.save(prescricao);
            }

            // Marca consulta como realizada
            if (consultaRepository != null) {
                Optional<Consulta> consultaOpt = consultaRepository.findById(consultaId);
                if (consultaOpt.isPresent()) {
                    Consulta consulta = consultaOpt.get();
                    consulta.setStatus("Realizada");
                    consultaRepository.save(consulta);
                }
            }

            model.addAttribute("success", "Prescrição realizada com sucesso!");
            return "redirect:/Views/MainMenuMedico/index";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao prescrever: " + e.getMessage());
            return "Views/Prescrever/index";
        }
    }
}
