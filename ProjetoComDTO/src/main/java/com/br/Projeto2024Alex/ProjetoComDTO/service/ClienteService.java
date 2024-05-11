package com.br.Projeto2024Alex.ProjetoComDTO.service;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ClienteDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface ClienteService {

    String salvarCliente(ClienteDTO clienteDTO, BindingResult result, RedirectAttributes attributes,
                       PasswordEncoder encoder);

    List<String> capturaMensagensErroResult(BindingResult result);

}
