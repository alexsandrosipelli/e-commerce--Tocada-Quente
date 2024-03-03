/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecomerce.pi.controller;

import ecomerce.pi.modelo.Usuario;
import ecomerce.pi.repositorio.UsuarioRepositorio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author hiago
 */

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @GetMapping("/novo")
    public String adicionarUsuario(Model model){
        model.addAttribute("usuario", new Usuario());
        return "/publica-criar-usuario";
    }
    
    @PostMapping("/salvar")
    public String salvarUsuario(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            return "/publica-criar-usuario";
        }
        usuarioRepositorio.save(usuario);
        attributes.addFlashAttribute("mensagem", "Usuario salvo com sucesso!");
        return "redirect:/usuario/novo";
    }
    
    @RequestMapping("/admin/listar")
    public String listarUsuario(Model model){
        model.addAttribute("Usuarios", usuarioRepositorio.findAll());
        return "/autenticacao/admin-listar-usuario";
    }
}
