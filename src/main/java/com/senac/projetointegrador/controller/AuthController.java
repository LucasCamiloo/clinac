package com.senac.projetointegrador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.senac.projetointegrador.service.AuthService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String loginPage() {
        return "Views/login";
    }
    
    @PostMapping("/login")
    public String processLogin(@RequestParam String credencial, 
                              @RequestParam String senha,
                              HttpSession session, 
                              Model model) {
        
        if (authService.authenticateUser(credencial, senha, session)) {
            return "redirect:/dashboard";
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
    public String cadastroSucesso(Model model) {
        model.addAttribute("message", "Cadastro realizado com sucesso! Faça login para continuar.");
        return "Views/login";
    }
}
