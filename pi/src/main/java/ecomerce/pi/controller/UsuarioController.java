package ecomerce.pi.controller;

import ecomerce.pi.modelo.Usuario;
import ecomerce.pi.repositorio.UsuarioRepositorio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PathVariable;

@Controller

@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private final UsuarioRepositorio usuarioRepositorio;/*para usado os dados do banco*/
    private final PasswordEncoder encoder;/*para codificar e verificar senha*/
    public UsuarioController(UsuarioRepositorio usuarioRepositorio, PasswordEncoder encoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.encoder = encoder;
    }

    @GetMapping("/novo")
    public String adicionarUsuario(Model model) {
        Usuario novoUsuario = new Usuario();

        model.addAttribute("usuario", novoUsuario);
        return "publica-criar-usuario";
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, RedirectAttributes attributes) {

        if (!usuario.getSenha().equals(usuario.getConfirmacaoSenha())) {
            result.rejectValue("confirmacaoSenha", "error.usuario", "As senhas não coincidem");
        }
        Optional<Usuario> usuarioExistente = usuarioRepositorio.findByEmail(usuario.getEmail());

        if (usuarioExistente.isPresent()) {
            result.rejectValue("email", "error.usuario", "Email já cadastrado");
        }
        if (result.hasErrors()) {
            return "publica-criar-usuario";
        }
        String senhaEncriptada = encoder.encode(usuario.getSenha());
        usuario.setSenha(senhaEncriptada);
        usuario.setStatus(true);
        usuarioRepositorio.save(usuario);

        attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
        return "redirect:/usuario/novo";
    }

    @GetMapping("/listar")
    public String listarUsuarios(Model model, HttpServletRequest request) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        model.addAttribute("usuarios", usuarios);

        String mensagem = (String) request.getSession().getAttribute("mensagemListar");
        if (mensagem != null && !mensagem.isEmpty()) {
            model.addAttribute("mensagem", mensagem);

        }

        return "lista-usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, HttpSession session, Model model) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            return "redirect:/";
        }
        if (!usuarioLogado.getId().equals(id)) {
            Usuario usuario = usuarioRepositorio.findById(id).orElse(null);

            if (usuario != null) {
                               model.addAttribute("senha", usuario.getSenha());

                model.addAttribute("usuario", usuario);
                return "editar-usuario";
            } else {
                return "redirect:/usuario/listar";
            }
        } else {
            Usuario usuario = usuarioRepositorio.findById(id).orElse(null);
            model.addAttribute("usuario", usuario);
            return "editar-usuario-logado";

        }
    }

    @PostMapping("/mudar-status")
    public String mudarStatusUsuario(@RequestParam Long id, RedirectAttributes attributes) {
        Optional<Usuario> optionalUsuario = usuarioRepositorio.findById(id);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setStatus(!usuario.isStatus());
            usuarioRepositorio.save(usuario);
            attributes.addFlashAttribute("mensagem", "Status do usuário alterado com sucesso!");
        } else {
            attributes.addFlashAttribute("mensagem", "Usuário não encontrado!");
        }
        return "redirect:/usuario/listar";
    }

    @GetMapping("/produtos")
    public String listarProdutos(Model model) {
        return "listar-produtos";
    }
    @PostMapping("/atualizar")
    public String atualizarUsuario(@Valid @ModelAttribute("usuario") Usuario usuarioAtualizado, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "editar-usuario";
        }

        Optional<Usuario> optionalUsuarioOriginal = usuarioRepositorio.findById(usuarioAtualizado.getId());

        if (optionalUsuarioOriginal.isPresent()) {
            Usuario usuarioOriginal = optionalUsuarioOriginal.get();
            usuarioAtualizado.setEmail(usuarioOriginal.getEmail());

            String senhaEncriptada = encoder.encode(usuarioAtualizado.getSenha());
            usuarioAtualizado.setSenha(senhaEncriptada);
            usuarioAtualizado.setStatus(usuarioOriginal.isStatus());
            usuarioRepositorio.save(usuarioAtualizado);

            attributes.addFlashAttribute("usuarios", usuarioRepositorio.findAll());
            attributes.addFlashAttribute("mensagem", "Usuário atualizado com sucesso!");

        } else {
            attributes.addFlashAttribute("erro", "Usuário não encontrado para atualização");
        }

        return "redirect:/usuario/listar";
    }
    @GetMapping("/pesquisar")
    public String pesquisarUsuarioPorNome(@RequestParam("nome") String nome, Model model) {
        List<Usuario> usuarios = usuarioRepositorio.findByNomeContainingIgnoreCase(nome);
        model.addAttribute("usuarios", usuarios);
        return "lista-usuarios";
    }

}
