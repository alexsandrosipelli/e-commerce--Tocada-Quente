package com.br.Projeto2024Alex.ProjetoComDTO.service.impl;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoEntregaDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoFaturamentoDTO;
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
    public EnderecoEntregaDTO executaBuscaEnderecoEntrega(EnderecoViacepDTO request) {
        return enderecoFeign.buscaEnderecoEntregaCep(request.getCep());
    }

    @Override
    public EnderecoFaturamentoDTO executaBuscaEnderecoFaturamento(EnderecoViacepDTO request) {
        return enderecoFeign.buscaEnderecoFaturamentoCep(request.getCep());
    }
}
