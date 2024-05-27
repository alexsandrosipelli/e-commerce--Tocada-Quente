package com.br.Projeto2024Alex.ProjetoComDTO.service.impl;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.UsuarioEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.UsuarioRepository;
import com.br.Projeto2024Alex.ProjetoComDTO.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class LoginServiceImpl implements LoginService {

    private static LoginServiceImpl instance;

    private LoginServiceImpl(){

    }

    public static LoginServiceImpl getInstance(){
        if (instance == null){
            return instance = new LoginServiceImpl();
        }else{
            return instance;
        }
    }

    @Override
    public String efetuarLogin(String email, String senha, Model model, HttpSession session, UsuarioRepository usuarioRepository,
                               PasswordEncoder encoder) {
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
