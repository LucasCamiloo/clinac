package com.senac.projetointegrador.controller;

import com.senac.projetointegrador.entities.Consulta;
import com.senac.projetointegrador.entities.Paciente;
import com.senac.projetointegrador.repositorys.ConsultaRepository;
import com.senac.projetointegrador.repositorys.PacienteRepository;
import com.senac.projetointegrador.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class ConsultaController {

    @Autowired(required = false)
    private ConsultaRepository consultaRepository;

    @Autowired(required = false)
    private PacienteRepository pacienteRepository;

    @Autowired(required = false)
    private MedicoRepository medicoRepository;

    @GetMapping("/Views/AgendamentoConsulta-Paciente/index.html")
    public String abrirFormulario(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }
        if (medicoRepository != null) {
            model.addAttribute("medicos", medicoRepository.findAll());
        }
        // Se for recepcionista, carrega todos os pacientes
        Object tipoUsuario = session.getAttribute("tipoUsuario");
        if ("recepcionista".equals(tipoUsuario) && pacienteRepository != null) {
            model.addAttribute("pacientes", pacienteRepository.findAll());
        }
        return "Views/AgendamentoConsulta-Paciente/index";
    }

    @PostMapping("/agendar-consulta")
    public String agendarConsulta(
            @RequestParam("tipo-servico") String tipoDeServico,
            @RequestParam("profissional") String profissional,
            @RequestParam("data") String dataConsulta,
            @RequestParam("horario") String horario,
            @RequestParam(value = "pre-consulta", required = false) String preConsulta,
            @RequestParam(value = "pacienteId", required = false) String pacienteId,
            HttpSession session,
            Model model) {
        try {
            String idPaciente;
            Object tipoUsuario = session.getAttribute("tipoUsuario");
            if ("recepcionista".equals(tipoUsuario)) {
                idPaciente = pacienteId;
            } else {
                Paciente paciente = (Paciente) session.getAttribute("usuarioLogado");
                if (paciente == null) {
                    model.addAttribute("error", "Faça login para agendar uma consulta.");
                    return "Views/AgendamentoConsulta-Paciente/index";
                }
                idPaciente = paciente.getId();
            }

            // Monta data e hora
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date dataHora = sdf.parse(dataConsulta + " " + horario);

            Consulta consulta = new Consulta(
                UUID.randomUUID().toString(),
                tipoDeServico,
                profissional,
                dataHora,
                preConsulta,
                "Agendada",
                null, // idMedico (pode ser null por enquanto)
                idPaciente
            );

            if (consultaRepository != null) {
                consultaRepository.save(consulta);
            }

            model.addAttribute("success", "Consulta agendada com sucesso!");
            return "Views/AgendamentoConsulta-Paciente/index";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao agendar consulta: " + e.getMessage());
            return "Views/AgendamentoConsulta-Paciente/index";
        }
    }
}
