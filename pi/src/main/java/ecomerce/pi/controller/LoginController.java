package ecomerce.pi.controller;

import ecomerce.pi.modelo.Usuario;
import ecomerce.pi.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private PasswordEncoder encoder;
    
    @PostMapping("/pagina-principal")
    public String login(@RequestParam String email, @RequestParam String senha, Model model) {
        Usuario usuario = usuarioRepositorio.findByEmail(email).orElse(null);
        if (usuario != null && encoder.matches(senha, usuario.getSenha())) {
            // Verifica o tipo de usuário e define o atributo correspondente no modelo
            if (usuario.getGrupo().equals("ADMINISTRADOR")) {
                model.addAttribute("tipoUsuario", "ADMINISTRADOR");
            } else if (usuario.getGrupo().equals("ESTOQUISTA")) {
                model.addAttribute("tipoUsuario", "ESTOQUISTA");
            }
            model.addAttribute("usuarioLogado", usuario);
            return "pagina-principal"; // Redireciona para a página principal após o login bem-sucedido
        } else {
            return "redirect:/login?error"; // Redireciona de volta para a página de login com um parâmetro de erro
        }
    }
}
