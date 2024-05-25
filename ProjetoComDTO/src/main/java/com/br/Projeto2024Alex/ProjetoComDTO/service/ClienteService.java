package com.br.Projeto2024Alex.ProjetoComDTO.service;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ClienteDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface ClienteService {

    String salvarCliente(ClienteEntity clienteEntity, BindingResult result, RedirectAttributes attributes);

    void consultarCep(ClienteDTO clienteDTO);

    String editarCliente(HttpSession session, Model model);

    String confirmarAtualizacao(ClienteDTO clienteDTO, BindingResult result, HttpSession session);
}
