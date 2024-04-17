/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.service.impl;

import com.br.Projeto2024Alex.ProjetoComDTO.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author alexs
 */
@Service
public class ClienteServiceImpl {

    private final PasswordEncoder passwordEncoder;

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(PasswordEncoder encoder, ClienteRepository repository) {
        this.passwordEncoder = encoder;
        this.clienteRepository = repository;
    }
}
