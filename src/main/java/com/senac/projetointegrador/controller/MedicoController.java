package com.senac.projetointegrador.controller;

import com.senac.projetointegrador.entities.Medico;
import com.senac.projetointegrador.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class MedicoController {

    @Autowired(required = false)
    private MedicoRepository medicoRepository;

    @GetMapping("/consultar-medicos")
    public String listarMedicos(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogado") == null || !"recepcionista".equals(session.getAttribute("tipoUsuario"))) {
            return "redirect:/login";
        }
        if (medicoRepository != null) {
            model.addAttribute("medicos", medicoRepository.findAll());
        }
        return "Views/GerenciadorMedico/gerenciadorDeMedicos";
    }

    @GetMapping("/medicos/cadastrar")
    public String formCadastrar(HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null || !"recepcionista".equals(session.getAttribute("tipoUsuario"))) {
            return "redirect:/login";
        }
        return "Views/GerenciadorMedico/cadastrarMedico";
    }

    @PostMapping("/medicos/cadastrar")
    public String cadastrarMedico(
            @RequestParam String nome,
            @RequestParam Integer crm,
            @RequestParam String cpf,
            @RequestParam String email,
            @RequestParam String telefone,
            @RequestParam String sexo,
            @RequestParam String cidade,
            @RequestParam String estado,
            @RequestParam String senha,
            @RequestParam String especialidade,
            HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null || !"recepcionista".equals(session.getAttribute("tipoUsuario"))) {
            return "redirect:/login";
        }
        if (medicoRepository != null) {
            Medico medico = new Medico(nome, crm, cpf, email, telefone, sexo, cidade, estado, senha, especialidade);
            medicoRepository.save(medico);
        }
        return "redirect:/consultar-medicos";
    }

    @GetMapping("/medicos/editar")
    public String formEditar(@RequestParam String id, HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogado") == null || !"recepcionista".equals(session.getAttribute("tipoUsuario"))) {
            return "redirect:/login";
        }
        if (medicoRepository != null) {
            Optional<Medico> medico = medicoRepository.findById(id);
            medico.ifPresent(m -> model.addAttribute("medico", m));
        }
        return "Views/GerenciadorMedico/editarMedico";
    }

    @PostMapping("/medicos/editar")
    public String editarMedico(
            @RequestParam String id,
            @RequestParam String nome,
            @RequestParam Integer crm,
            @RequestParam String cpf,
            @RequestParam String email,
            @RequestParam String telefone,
            @RequestParam String sexo,
            @RequestParam String cidade,
            @RequestParam String estado,
            @RequestParam String senha,
            @RequestParam String especialidade,
            HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null || !"recepcionista".equals(session.getAttribute("tipoUsuario"))) {
            return "redirect:/login";
        }
        if (medicoRepository != null) {
            Optional<Medico> medicoOpt = medicoRepository.findById(id);
            if (medicoOpt.isPresent()) {
                Medico medico = medicoOpt.get();
                medico.setNome(nome);
                medico.setCrm(crm);
                medico.setEmail(email);
                medico.setTelefone(telefone);
                medico.setSexo(sexo);
                medico.setCidade(cidade);
                medico.setEstado(estado);
                medico.setSenha(senha);
                medico.setEspecialidade(especialidade);
                medicoRepository.save(medico);
            }
        }
        return "redirect:/consultar-medicos";
    }

    @PostMapping("/medicos/excluir")
    public String excluirMedico(@RequestParam String id, HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null || !"recepcionista".equals(session.getAttribute("tipoUsuario"))) {
            return "redirect:/login";
        }
        if (medicoRepository != null) {
            medicoRepository.deleteById(id);
        }
        return "redirect:/consultar-medicos";
    }
}
