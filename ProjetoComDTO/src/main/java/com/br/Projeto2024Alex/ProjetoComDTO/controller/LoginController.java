package com.br.Projeto2024Alex.ProjetoComDTO.controller;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.UsuarioEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public LoginController(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @PostMapping("/paginaInicial")
    public String login(@RequestParam String email, @RequestParam String senha, Model model, HttpSession session) {
        UsuarioEntity usuario = usuarioRepository.findByEmail(email);
        if (usuario != null) {
            if (encoder.matches(senha, usuario.getSenha())) {
                if (usuario.isStatus()) {
                    if (usuario.getGrupo().equals("ADMINISTRADOR")) {
                        model.addAttribute("tipoUsuario", "ADMINISTRADOR");
                    } else if (usuario.getGrupo().equals("ESTOQUISTA")) {
                        model.addAttribute("tipoUsuario", "ESTOQUISTA");
                    }
                    session.setAttribute("usuarioLogado", usuario);
                    return "pagina-principal";
                } else {
                    model.addAttribute("error", "Sua conta está inativa. Entre em contato com um administrador.");
                }
            } else {
                model.addAttribute("error", "Senha incorreta. Por favor, tente novamente.");
            }
        } else {
            model.addAttribute("error", "Usuário não encontrado. Por favor, verifique seu email.");
        }
        return "login";
    }

}
