package com.br.Projeto2024Alex.ProjetoComDTO.controller;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoViacepDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/endereco")
@RestController
public class EnderecoController {

    private final EnderecoService enderecoService;

    @Autowired
    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/consulta")
    public ResponseEntity consultaCep(@RequestBody EnderecoViacepDTO enderecoRequest){
        return ResponseEntity.ok(enderecoService.executa(enderecoRequest));
    }
}
