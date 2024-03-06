package ecomerce.pi.controller;

import ecomerce.pi.modelo.Usuario;
import ecomerce.pi.repositorio.UsuarioRepositorio;
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

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder encoder;

    @Autowired
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
        // Verificar se as senhas coincidem
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
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        model.addAttribute("usuarios", usuarios);
        return "lista-usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, HttpSession session, Model model) {
        // Recupera o usuário logado da sessão
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            // Usuário não está logado, redireciona para a página de login
            return "redirect:/";
        }

        // Verifica se o ID do usuário logado é diferente do ID do usuário que está sendo editado
        if (!usuarioLogado.getId().equals(id)) {
            // Encontra o usuário no banco de dados
            Usuario usuario = usuarioRepositorio.findById(id).orElse(null);

            if (usuario != null) {
                usuario.setStatus(true);

                model.addAttribute("usuario", usuario);
                return "editar-usuario";
            } else {
                // Usuário não encontrado, redireciona para a página de listagem
                return "redirect:/usuario/listar";
            }
        } else {
            // Usuário logado está tentando editar seu próprio grupo, redireciona para a página de editar
            Usuario usuario = usuarioRepositorio.findById(id).orElse(null);

            model.addAttribute("usuario", usuario);
            return "editar-usuario-logado";

        }
    }

    @PostMapping("/excluir")
    public String excluirUsuario(@RequestParam Long id, RedirectAttributes attributes) {
        usuarioRepositorio.deleteById(id);
        attributes.addFlashAttribute("mensagem", "Usuário excluído com sucesso!");
        return "redirect:/usuario/listar";
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

        // Buscar o usuário original no banco de dados
        Optional<Usuario> optionalUsuarioOriginal = usuarioRepositorio.findById(usuarioAtualizado.getId());

        if (optionalUsuarioOriginal.isPresent()) {
            Usuario usuarioOriginal = optionalUsuarioOriginal.get();

            // Definir o email do usuário original no objeto atualizado
            usuarioAtualizado.setEmail(usuarioOriginal.getEmail());

            // Encriptar a senha antes de salvar
            String senhaEncriptada = encoder.encode(usuarioAtualizado.getSenha());
            usuarioAtualizado.setSenha(senhaEncriptada);
            usuarioAtualizado.setStatus(true);
            usuarioRepositorio.save(usuarioAtualizado);
            attributes.addFlashAttribute("mensagem", "Usuário atualizado com sucesso!");
        } else {
            // Lidar com o caso em que o usuário não foi encontrado
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