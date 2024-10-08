package com.br.Projeto2024Alex.ProjetoComDTO.controller;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.UsuarioDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.UsuarioEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.service.impl.UsuarioServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioServiceImpl usuarioServiceImpl;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioController(UsuarioServiceImpl usuarioServiceImpl, PasswordEncoder passwordEncoder) {
        this.usuarioServiceImpl = usuarioServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/listar")
    public String listarUsuarios(Model model, HttpSession session) {
        UsuarioEntity usuarioLogado = (UsuarioEntity) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            return "redirect:/";
        }
        List<UsuarioDTO> usuarios = usuarioServiceImpl.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "lista-usuarios";
    }

    @GetMapping("/novo")
    public String adicionarUsuario(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "publica-criar-usuario";
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@Valid @ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "publica-criar-usuario";
        }
        if (!usuarioDTO.isNomeValido() || usuarioDTO.getNome().length() < 3) {
            result.rejectValue("nome", "nome.invalido", "O nome deve conter apenas letras e espaços.");
            return "publica-criar-usuario";
        }

        if (!usuarioDTO.getSenha().equals(usuarioDTO.getConfirmacaoSenha())) {
            result.rejectValue("confirmacaoSenha", "senha.mismatch", "As senhas digitadas não são iguais.");
            return "publica-criar-usuario";
        }

        if (usuarioServiceImpl.existeEmail(usuarioDTO.getEmail())) {
            result.rejectValue("email", "email.exists", "O e-mail já está cadastrado. Por favor, escolha outro.");
            return "publica-criar-usuario";
        }

        String senhaCriptografada = passwordEncoder.encode(usuarioDTO.getSenha());
        usuarioDTO.setSenha(senhaCriptografada);
        usuarioDTO.setStatus(true);

        usuarioServiceImpl.salvarUsuario(usuarioDTO);

        attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
        return "redirect:/usuario/novo";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, HttpSession session, Model model) {
        UsuarioEntity usuarioLogado = (UsuarioEntity) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            return "redirect:/";
        }
        // Verifica se o usuário está tentando editar sua própria conta
        UsuarioDTO usuario = usuarioServiceImpl.buscarUsuarioPorId(id);
        if (usuarioLogado.getId().equals(id)) {

            if (usuario != null) {
                model.addAttribute("senha", usuario.getSenha());
                model.addAttribute("email", usuario.getEmail());
                model.addAttribute("usuario", usuario);
                return "editar-usuario-logado";
            } else {
                return "redirect:/usuario/listar";
            }
        } else {
            // Se não for o próprio usuário tentando se alterar, permite a edição normal

            if (usuario != null) {
                model.addAttribute("senha", usuario.getSenha());
                model.addAttribute("email", usuario.getEmail());
                model.addAttribute("usuario", usuario);
                return "editar-usuario";
            } else {
                return "redirect:/usuario/listar";
            }
        }
    }

    @PostMapping("/atualizar")
    public String atualizarUsuario(@Valid @ModelAttribute("usuario") UsuarioDTO usuarioDTO, BindingResult result, RedirectAttributes attributes) {
        // Verifica se houve erros de validação nos campos do formulário
        if (result.hasErrors()) {
            return "editar-usuario";
        }

        // Verifica se a senha foi fornecida pelo usuário
        if (StringUtils.isEmpty(usuarioDTO.getSenha())) {
            // Se a senha estiver vazia, mantém a senha atual do usuário
            usuarioDTO.setSenha(null);
        }

        // Verifica se a confirmação de senha foi fornecida e se é igual à senha
        if (!StringUtils.isEmpty(usuarioDTO.getConfirmacaoSenha()) && usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().equals(usuarioDTO.getConfirmacaoSenha())) {
            // Se a confirmação de senha não coincidir com a senha, adiciona um erro de validação
            result.rejectValue("confirmacaoSenha", "error.usuario", "As senhas não coincidem");
            return "editar-usuario";
        }

        // Verifica se a senha não foi fornecida e se a confirmação de senha foi
        if (StringUtils.isEmpty(usuarioDTO.getSenha()) && !StringUtils.isEmpty(usuarioDTO.getConfirmacaoSenha())) {
            // Se a senha estiver vazia e a confirmação de senha não, define a senha como a confirmação de senha
            usuarioDTO.setSenha(usuarioDTO.getConfirmacaoSenha());
        }

        // Atualiza a senha do usuário se fornecida
        if (usuarioDTO.getSenha() != null) {
            usuarioServiceImpl.atualizarUsuario(usuarioDTO);
        }
        usuarioServiceImpl.atualizarUsuario(usuarioDTO);

        // Redireciona para a página de listagem de usuários com uma mensagem de sucesso
        attributes.addFlashAttribute("mensagem", "Usuário atualizado com sucesso!");
        return "redirect:/usuario/listar";
    }

    @GetMapping("/pesquisar")
    public String pesquisarUsuarioPorNome(@RequestParam("nome") String nome, Model model) {
        List<UsuarioDTO> usuarios = usuarioServiceImpl.pesquisarUsuarioPorNome(nome);
        model.addAttribute("usuarios", usuarios);
        return "lista-usuarios";
    }

    @PostMapping("/mudar-status")
    public String mudarStatusUsuario(@RequestParam Long id, RedirectAttributes attributes) {
        try {
            // Chama o método da service para mudar o status do usuário
            usuarioServiceImpl.mudarStatusUsuario(id);

            attributes.addFlashAttribute("mensagem", "Status do usuário alterado com sucesso!");
        } catch (EntityNotFoundException ex) {
            attributes.addFlashAttribute("mensagem", "Usuário não encontrado!");
        }
        return "redirect:/usuario/listar";
    }
}
