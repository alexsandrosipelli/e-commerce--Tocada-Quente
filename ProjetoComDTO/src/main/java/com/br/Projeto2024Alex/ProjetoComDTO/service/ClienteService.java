package com.br.Projeto2024Alex.ProjetoComDTO.service;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ClienteDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface ClienteService {

    String salvarCliente(ClienteDTO clienteDTO, BindingResult result, RedirectAttributes attributes);

    ClienteDTO consultarCep(ClienteEntity clienteEntity);

    void salvarEnderecos(ClienteDTO clienteDTO);
}
