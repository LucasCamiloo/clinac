package com.senac.projetointegrador.controller;

import com.senac.projetointegrador.entities.Consulta;
import com.senac.projetointegrador.repositorys.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class RecepcionistaController {

    @Autowired(required = false)
    private ConsultaRepository consultaRepository;

    @GetMapping("/main-menu-recepcionista")
    public String painelRecepcionista(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogado") == null || !"recepcionista".equals(session.getAttribute("tipoUsuario"))) {
            return "redirect:/login";
        }
        if (consultaRepository != null) {
            List<Consulta> consultas = consultaRepository.findAll();
            model.addAttribute("consultas", consultas);
        }
        return "Views/MainMenuRecepcionista/index";
    }

    @PostMapping("/consultas/realizar")
    public String marcarComoRealizada(@RequestParam("id") String id, HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null || !"recepcionista".equals(session.getAttribute("tipoUsuario"))) {
            return "redirect:/login";
        }
        if (consultaRepository != null) {
            Optional<Consulta> consultaOpt = consultaRepository.findById(id);
            if (consultaOpt.isPresent()) {
                Consulta consulta = consultaOpt.get();
                consulta.setStatus("Realizada");
                consultaRepository.save(consulta);
            }
        }
        return "redirect:/main-menu-recepcionista";
    }

    @PostMapping("/consultas/excluir")
    public String excluirConsulta(@RequestParam("id") String id, HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null || !"recepcionista".equals(session.getAttribute("tipoUsuario"))) {
            return "redirect:/login";
        }
        if (consultaRepository != null) {
            consultaRepository.deleteById(id);
        }
        return "redirect:/main-menu-recepcionista";
    }
}
