/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.service.impl;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ClienteDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.ClienteRepository;
import com.br.Projeto2024Alex.ProjetoComDTO.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexs
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    private final PasswordEncoder passwordEncoder;
    private final ClienteRepository clienteRepository;
    private static final String ALERTA_TIPO_ERRO = "erros";
    private static final String ALERTA_TIPO_SUCESSO = "mensagem";

    @Autowired
    public ClienteServiceImpl(PasswordEncoder encoder, ClienteRepository repository) {
        this.passwordEncoder = encoder;
        this.clienteRepository = repository;
    }

    @Override
    public String salvarCliente(ClienteDTO clienteDto, BindingResult result, RedirectAttributes attributes, PasswordEncoder encoder) {
        //TODO FALTA FAZER O CEP SER CONSULTADO NA API EXTERNA E PREENCHER OS CAMPOS COM O RESULTADO.
        ModelMapper modelMapper = new ModelMapper();

        boolean temErros = validacaoCamposCadastro(clienteDto, result);

        if (temErros){
            return "criar-cliente";
        }else{
            clienteDto.setDataNascimento(clienteDto.getDataNascimento().replace("-", ""));
            String senhaCriptografada = encoder.encode(clienteDto.getSenha());
            clienteDto.setSenha(senhaCriptografada);
            ClienteEntity clienteEntity = modelMapper.map(clienteDto, ClienteEntity.class);
            clienteRepository.save(clienteEntity);
            return "redirect:/cliente/";
        }
    }

    @Override
    public List<String> capturaMensagensErroResult(BindingResult result) {
        List<String> mensagensErros = new ArrayList<>();

        // Itera sobre todos os erros de campo
        result.getFieldErrors().forEach(erros -> mensagensErros.add(erros.getDefaultMessage()));

        return mensagensErros;
    }

    private boolean validacaoCamposCadastro(ClienteDTO clienteDTO, BindingResult result) {
        if (clienteRepository.existsByEmailIgnoreCase(clienteDTO.getEmail())) {
            result.rejectValue("email", "email.exists", "O e-mail já está cadastrado. Por favor, escolha outro.");
        }

        if (clienteRepository.existsByCpf(clienteDTO.getCpf())) {
            result.rejectValue("cpf", "email.cpf", "O cpf já está cadastrado. Por favor, escolha outro.");
        }

        return result.hasErrors();
    }
}
