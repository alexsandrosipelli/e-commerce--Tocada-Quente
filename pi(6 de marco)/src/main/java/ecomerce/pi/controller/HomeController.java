package ecomerce.pi.controller;

import ecomerce.pi.modelo.Usuario;
import ecomerce.pi.repositorio.UsuarioRepositorio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("msnBemVindo", "Login");
        return "login";
    }

    @PostMapping("/paginaInicial")
    public String login(@RequestParam String email, @RequestParam String senha, Model model, HttpSession session) {
        Usuario usuario = usuarioRepositorio.findByEmail(email).orElse(null);
        if (usuario != null) {
            if (encoder.matches(senha, usuario.getSenha())) {
                // Verifica se o usuário está ativo
                if (usuario.isStatus()) {
                    // Verifica o tipo de usuário e define o atributo correspondente no modelo
                    if (usuario.getGrupo().equals("ADMINISTRADOR")) {
                        model.addAttribute("tipoUsuario", "ADMINISTRADOR");
                    } else if (usuario.getGrupo().equals("ESTOQUISTA")) {
                        model.addAttribute("tipoUsuario", "ESTOQUISTA");
                    }
                    // Adiciona o usuário logado como atributo de sessão
                    session.setAttribute("usuarioLogado", usuario);
                    return "pagina-principal"; // Redireciona para a página principal após o login bem-sucedido
                } else {
                    model.addAttribute("error", "Sua conta está inativa. Entre em contato com um administrador.");
                    return "login"; // Redireciona de volta para a página de login com uma mensagem de erro
                }
            } else {
                model.addAttribute("error", "Senha incorreta. Por favor, tente novamente.");
                return "login"; // Redireciona de volta para a página de login com uma mensagem de erro
            }
        } else {
            model.addAttribute("error", "Usuário não encontrado. Por favor, verifique seu email.");
            return "login"; // Redireciona de volta para a página de login com uma mensagem de erro
        }
    }

}
   