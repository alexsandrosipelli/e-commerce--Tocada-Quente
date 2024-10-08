/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.controller;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ClienteDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoEntregaDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.ClienteRepository;
import com.br.Projeto2024Alex.ProjetoComDTO.service.impl.ClienteServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author alexs
 */
@Controller
@RequestMapping("/site/cliente")
public class ClienteController {
    private final ClienteRepository clienteRepository;
    private final ClienteServiceImpl clienteServiceImpl;
    private final PasswordEncoder encoder;
    private static final String CLIENTE_LOGADO = "clienteLogado";

    @Autowired
    public ClienteController(ClienteRepository repository, ClienteServiceImpl clienteServiceImpl, PasswordEncoder encode) {
        this.clienteRepository = repository;
        this.clienteServiceImpl = clienteServiceImpl;
        this.encoder = encode;
    }

    @GetMapping("/")
    public String index() {
        return "login-cliente";
    }

    @GetMapping("/novo")
    public String adicionarCliente(Model model, HttpSession session){
        model.addAttribute("cliente", new ClienteEntity());
        return "criar-cliente";
    }

    @PostMapping("/salvar")
    public String salvarCliente(@Valid @ModelAttribute("cliente") ClienteEntity clienteEntity, BindingResult result,
                                RedirectAttributes attributes, HttpSession session){

        return clienteServiceImpl.salvarCliente(clienteEntity, result, attributes);
    }

    @PostMapping("/enderecos/salvar")
    public String salvarEndereco(@Valid @ModelAttribute("enderecos") EnderecoEntregaDTO enderecoEntregaDTO, BindingResult result,
                                RedirectAttributes attributes, HttpSession session){
        return clienteServiceImpl.salvarEndereco(enderecoEntregaDTO, result, attributes, session);
    }

    @GetMapping("/editar")
    public String editarPerfilCliente(HttpSession session, Model model){
        return clienteServiceImpl.editarCliente(session, model);
    }

    @PostMapping("/sair")
    public String deslogarCliente(HttpSession session){
        return clienteServiceImpl.deslogarCliente(session);
    }

    @PostMapping("/atualizar")
    public String atualizarCliente(@Valid @ModelAttribute("clienteEditar") ClienteDTO clienteDTO, BindingResult result,
                                   HttpSession session){
        return clienteServiceImpl.confirmarAtualizacao(clienteDTO, result, session);
    }

    @PostMapping("/loginCliente")
    public String login(@RequestParam String email, @RequestParam String senha, Model model, HttpSession session) {
        ClienteEntity cliente = clienteRepository.findByEmail(email);
        if (cliente != null) {
            if (encoder.matches(senha, cliente.getSenha())) {
                    session.setAttribute(CLIENTE_LOGADO, cliente);
                    return "redirect:/site/";
            } else {
                model.addAttribute("error", "Senha incorreta. Por favor, tente novamente.");
                return "login-cliente";
            }
        } else {
            model.addAttribute("error", "Cliente não encontrado. Por favor, verifique seu email.");
        }
        return "login-cliente";
    }

    @GetMapping("/enderecos")
    public String listarEnderecos(Model model, HttpSession session){
        return clienteServiceImpl.listarEnderecos(model, session);
    }

    @GetMapping("/enderecos/novo")
    public String adicionarEndereco(Model model, HttpSession session){
        return clienteServiceImpl.novoEndereco(model, session);
    }

    @PostMapping("/enderecos/desativar/{id}")
    public String desativarEndereco(@PathVariable Long id, Model model, HttpSession session){
        return clienteServiceImpl.desativarEndereco(id, model, session);
    }

    @PostMapping("/enderecos/principal/{id}")
    public String enderecoPrincipal(@PathVariable Long id, Model model, HttpSession session) {
        return clienteServiceImpl.alterarEnderecoPrincipal(id, session);
    }
}
