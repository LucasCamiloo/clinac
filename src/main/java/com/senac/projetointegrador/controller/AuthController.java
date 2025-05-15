package com.senac.projetointegrador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.senac.projetointegrador.service.AuthService;
import com.senac.projetointegrador.entities.Paciente;
import com.senac.projetointegrador.entities.Medico;
import com.senac.projetointegrador.entities.Recepcionista;
import com.senac.projetointegrador.repositorys.PacienteRepository;
import com.senac.projetointegrador.repositories.MedicoRepository;
import com.senac.projetointegrador.repositories.RecepcionistaRepository;
import com.senac.projetointegrador.repositorys.ConsultaRepository;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired(required = false)
    private PacienteRepository pacienteRepository;
    @Autowired(required = false)
    private MedicoRepository medicoRepository;
    @Autowired(required = false)
    private RecepcionistaRepository recepcionistaRepository;
    @Autowired(required = false)
    private ConsultaRepository consultaRepository;
    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("usuarioLogado") != null) {
            // Redireciona paciente logado para o painel
            if ("paciente".equals(session.getAttribute("tipoUsuario"))) {
                return "redirect:/Views/MainMenuPaciente/index";
            }
            // Redirecione outros tipos conforme necessário
        }
        return "Views/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String credencial,
                              @RequestParam String senha,
                              HttpSession session,
                              Model model) {
        // Verifica paciente (por email)
        if (pacienteRepository != null) {
            Optional<Paciente> pacienteOpt = pacienteRepository.findByEmail(credencial);
            if (pacienteOpt.isPresent() && pacienteOpt.get().getSenha().equals(senha)) {
                session.setAttribute("usuarioLogado", pacienteOpt.get());
                session.setAttribute("tipoUsuario", "paciente");
                model.addAttribute("tipoUsuario", "paciente");
                model.addAttribute("usuarioLogado", pacienteOpt.get());
                // Buscar consultas do paciente
                if (consultaRepository != null) {
                    model.addAttribute("consultas", consultaRepository.findByIdPaciente(pacienteOpt.get().getId()));
                }
                return "Views/MainMenuPaciente/index";
            }
        }
        // Verifica médico (por CRM)
        if (medicoRepository != null) {
            try {
                Integer crm = Integer.parseInt(credencial);
                Optional<Medico> medicoOpt = medicoRepository.findByCrm(crm);
                if (medicoOpt.isPresent() && medicoOpt.get().getSenha().equals(senha)) {
                    session.setAttribute("usuarioLogado", medicoOpt.get());
                    session.setAttribute("tipoUsuario", "medico");
                    model.addAttribute("tipoUsuario", "medico");
                    model.addAttribute("usuarioLogado", medicoOpt.get());
                    // Redirecione para o menu do médico se desejar
                    return "Views/dashboard";
                }
            } catch (NumberFormatException ignored) {}
        }
        // Verifica recepcionista (por email)
        if (recepcionistaRepository != null) {
            Optional<Recepcionista> recepOpt = recepcionistaRepository.findByEmail(credencial);
            if (recepOpt.isPresent() && recepOpt.get().getSenha().equals(senha)) {
                session.setAttribute("usuarioLogado", recepOpt.get());
                session.setAttribute("tipoUsuario", "recepcionista");
                model.addAttribute("tipoUsuario", "recepcionista");
                model.addAttribute("usuarioLogado", recepOpt.get());
                // Redireciona para o controller, não para a view diretamente
                return "redirect:/main-menu-recepcionista";
            }
        }
        // Fallback para autenticação em memória (caso MongoDB esteja offline)
        if (authService.authenticateUser(credencial, senha, session)) {
            model.addAttribute("tipoUsuario", "paciente");
            model.addAttribute("usuarioLogado", session.getAttribute("usuarioLogado"));
            // Fallback: não busca consultas se não houver MongoDB
            return "Views/MainMenuPaciente/index";
        }

        model.addAttribute("error", "Credenciais inválidas");
        return "Views/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }
        return "Views/dashboard";
    }

    @GetMapping("/cadastro-sucesso")
    public String cadastroSucesso(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogado") != null) {
            return "redirect:/";
        }
        model.addAttribute("message", "Cadastro realizado com sucesso! Faça login para continuar.");
        return "Views/login";
    }

    @GetMapping("/Views/MainMenuPaciente/index")
    public String mainMenuPaciente(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }
        // Adicione aqui a lógica para carregar consultas se necessário
        return "Views/MainMenuPaciente/index";
    }
}
