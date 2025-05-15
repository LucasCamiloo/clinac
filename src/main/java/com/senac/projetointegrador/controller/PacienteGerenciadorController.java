package com.senac.projetointegrador.controller;

import com.senac.projetointegrador.entities.Paciente;
import com.senac.projetointegrador.repositorys.PacienteRepository;
import com.senac.projetointegrador.repositorys.PrescricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class PacienteGerenciadorController {

    @Autowired(required = false)
    private PacienteRepository pacienteRepository;

    @Autowired(required = false)
    private PrescricaoRepository prescricaoRepository;

    @GetMapping("/consultar-pacientes")
    public String listarPacientes(HttpSession session, Model model) {
        Object usuario = session.getAttribute("usuarioLogado");
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        // Permite acesso para recepcionista OU médico
        if (usuario == null || (!"recepcionista".equals(tipoUsuario) && !"medico".equals(tipoUsuario))) {
            return "redirect:/login";
        }
        if (pacienteRepository != null) {
            model.addAttribute("pacientes", pacienteRepository.findAll());
        }
        return "Views/GerenciadorPaciente/gerenciadorDePacientes";
    }

    @PostMapping("/pacientes/cadastrar")
    public String cadastrarPaciente(
            @RequestParam String nome,
            @RequestParam String cpf,
            @RequestParam String email,
            @RequestParam String telefone,
            @RequestParam String sexo,
            @RequestParam String cidade,
            @RequestParam String estado,
            @RequestParam String senha,
            HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null || !"recepcionista".equals(session.getAttribute("tipoUsuario"))) {
            return "redirect:/login";
        }
        if (pacienteRepository != null) {
            Paciente paciente = new Paciente(nome, cpf, email, telefone, sexo, cidade, estado, senha, null);
            pacienteRepository.save(paciente);
        }
        return "redirect:/consultar-pacientes";
    }

    @GetMapping("/pacientes/editar")
    public String formEditar(@RequestParam String id, HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogado") == null || !"recepcionista".equals(session.getAttribute("tipoUsuario"))) {
            return "redirect:/login";
        }
        if (pacienteRepository != null) {
            Optional<Paciente> paciente = pacienteRepository.findById(id);
            paciente.ifPresent(p -> model.addAttribute("paciente", p));
        }
        return "Views/GerenciadorPaciente/editarPaciente";
    }

    @PostMapping("/pacientes/editar")
    public String editarPaciente(
            @RequestParam String id,
            @RequestParam String nome,
            @RequestParam String cpf,
            @RequestParam String email,
            @RequestParam String telefone,
            @RequestParam String sexo,
            @RequestParam String cidade,
            @RequestParam String estado,
            @RequestParam String senha,
            HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null || !"recepcionista".equals(session.getAttribute("tipoUsuario"))) {
            return "redirect:/login";
        }
        if (pacienteRepository != null) {
            Optional<Paciente> pacienteOpt = pacienteRepository.findById(id);
            if (pacienteOpt.isPresent()) {
                Paciente paciente = pacienteOpt.get();
                paciente.setNome(nome);
                paciente.setEmail(email);
                paciente.setTelefone(telefone);
                paciente.setSexo(sexo);
                paciente.setCidade(cidade);
                paciente.setEstado(estado);
                paciente.setSenha(senha);
                // Não tem setCpf, mas se quiser permitir edição, adicione em Pessoa/Paciente
                pacienteRepository.save(paciente);
            }
        }
        return "redirect:/consultar-pacientes";
    }

    @PostMapping("/pacientes/excluir")
    public String excluirPaciente(@RequestParam String id, HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null || !"recepcionista".equals(session.getAttribute("tipoUsuario"))) {
            return "redirect:/login";
        }
        if (pacienteRepository != null) {
            pacienteRepository.deleteById(id);
        }
        return "redirect:/consultar-pacientes";
    }

    @GetMapping("/pacientes/relatorio-prescricoes")
    public String relatorioPrescricoes(@RequestParam String pacienteId, HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }
        if (prescricaoRepository != null) {
            model.addAttribute("prescricoes", prescricaoRepository.findByPacienteId(pacienteId));
        }
        return "Views/Prescrever/relatorio";
    }
}
