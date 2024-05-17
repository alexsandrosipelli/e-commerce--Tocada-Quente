/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.controller;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ClienteDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoEntregaDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoFaturamentoDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.UsuarioEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.ClienteRepository;
import com.br.Projeto2024Alex.ProjetoComDTO.service.impl.ClienteServiceImpl;
import com.br.Projeto2024Alex.ProjetoComDTO.service.impl.EnderecoServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 *
 * @author alexs
 */
@Controller
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteRepository clienteRepository;
    private final ClienteServiceImpl clienteServiceImpl;
    private final PasswordEncoder encoder;

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

    @GetMapping("/loja")
    public String lojaApresentada(){
        return "loja-apresentada";
    }

    @GetMapping("/novo")
    public String adicionarCliente(Model model, HttpSession session){
        model.addAttribute("cliente", new ClienteDTO());
        return "criar-cliente";
    }

    @PostMapping("/salvar")
    public String salvarCliente(@Valid @ModelAttribute("cliente") ClienteDTO clienteDto, BindingResult result,
                                RedirectAttributes attributes, HttpSession session){

        return clienteServiceImpl.salvarCliente(clienteDto, result, attributes);
    }

    @PostMapping("/loginCliente")
    public String login(@RequestParam String email, @RequestParam String senha, Model model, HttpSession session) {
        ClienteEntity cliente = clienteRepository.findByEmail(email);
        if (cliente != null) {
            if (encoder.matches(senha, cliente.getSenha())) {
                    session.setAttribute("clienteLogado", cliente);
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
}
