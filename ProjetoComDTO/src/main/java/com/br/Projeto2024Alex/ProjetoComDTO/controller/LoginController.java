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
        /*email é pesquisado no banco de dados*/
        UsuarioEntity usuario = usuarioRepository.findByEmail(email);
        /*se nao for nulo*/
        if (usuario != null) {
            /*comparando senhas*/
            if (encoder.matches(senha, usuario.getSenha())) {
                /*se o usuario for ativo*/
                if (usuario.isStatus()) {
                    /*se o usuario for adm*/
                    if (usuario.getGrupo().equals("ADMINISTRADOR")) {
                        /**/
                        model.addAttribute("tipoUsuario", "ADMINISTRADOR");
                    } /*se o usuario for estoquista*/ else if (usuario.getGrupo().equals("ESTOQUISTA")) {
                        model.addAttribute("tipoUsuario", "ESTOQUISTA");
                    }
                    session.setAttribute("usuarioLogado", usuario);
                    return "pagina-principal";
                    /*usuario inativo*/
                } else {
                    model.addAttribute("error", "Sua conta está inativa. Entre em contato com um administrador.");
                }
                /*se a senha for incorreta*/
            } else {
                model.addAttribute("error", "Senha incorreta. Por favor, tente novamente.");
            }
            /*se o email for nulo*/
        } else {
            model.addAttribute("error", "Usuário não encontrado. Por favor, verifique seu email.");
        }
        return "login";
    }

}
