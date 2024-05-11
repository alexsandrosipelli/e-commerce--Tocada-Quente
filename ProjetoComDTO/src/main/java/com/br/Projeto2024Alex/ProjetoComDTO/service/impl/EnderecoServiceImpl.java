package com.br.Projeto2024Alex.ProjetoComDTO.service.impl;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoEntregaDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoViacepDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.service.feign.EnderecoFeign;
import com.br.Projeto2024Alex.ProjetoComDTO.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoFeign enderecoFeign;

    @Autowired
    public EnderecoServiceImpl(EnderecoFeign enderecoFeign) {
        this.enderecoFeign = enderecoFeign;
    }

    @Override
    public EnderecoEntregaDTO executa(EnderecoViacepDTO request) {
        return enderecoFeign.buscaEnderecoCep(request.getCep());
    }
}
