package com.br.Projeto2024Alex.ProjetoComDTO.service;

import com.br.Projeto2024Alex.ProjetoComDTO.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

public interface LoginService {
    String efetuarLogin(String email, String senha, Model model, HttpSession session, UsuarioRepository repository,
                        PasswordEncoder encoder);
}
