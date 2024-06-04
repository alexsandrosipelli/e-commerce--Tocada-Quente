package com.br.Projeto2024Alex.ProjetoComDTO.service;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ClienteDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoEntregaDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.EnderecoEntregaEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface ClienteService {

    String salvarCliente(ClienteEntity clienteEntity, BindingResult result, RedirectAttributes attributes);

    String editarCliente(HttpSession session, Model model);

    String confirmarAtualizacao(ClienteDTO clienteDTO, BindingResult result, HttpSession session);

    String deslogarCliente(HttpSession session);

    String listarEnderecos(Model model, HttpSession session);

    String salvarEndereco(EnderecoEntregaDTO enderecoEntregaDTO, BindingResult result, RedirectAttributes attributes, HttpSession session);

    String novoEndereco(Model model, HttpSession session);

    String desativarEndereco(Long id, Model model, HttpSession session);

    String alterarEnderecoPrincipal(Long id, HttpSession session);
}
