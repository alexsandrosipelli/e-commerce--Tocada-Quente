package com.br.Projeto2024Alex.ProjetoComDTO.controller;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoEntregaDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoFaturamentoDTO;
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
public class ViacepApiController {

    /*
    TODO ADICIONAR ENDPOINT PARA CADASTRO DE NOVOS ENDEREÇOS, DESATIVAR ENDEREÇOS E TORNAR O ENDEREÇO ESCOLHIDO COMO PRINCIPAL
    TODO ADICIONAR A TELA DO SPRINGWEB DO INDEX PARA LISTAR OS ENDEREÇOS E ADICIONAR O FORMULÁRIO DE CADASTRO DE CLIENTES NELE
    PARA SER O FORMULÁRIO DE CADASTRO DOS NOVOS ENDEREÇOS
     */

    private final EnderecoService enderecoService;

    @Autowired
    public ViacepApiController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/consulta/endereco-entrega")
    public ResponseEntity<EnderecoEntregaDTO> consultaEnderecoEntrega(@RequestBody EnderecoViacepDTO enderecoRequest){
        return ResponseEntity.ok(enderecoService.executaBuscaEnderecoEntrega(enderecoRequest));
    }

    @GetMapping("/consulta/enderec-faturamento")
    public ResponseEntity<EnderecoFaturamentoDTO> consultaEnderecoFaturamento(@RequestBody EnderecoViacepDTO enderecoRequest){
        return ResponseEntity.ok(enderecoService.executaBuscaEnderecoFaturamento(enderecoRequest));
    }
}
