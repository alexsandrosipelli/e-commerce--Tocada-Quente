package com.br.Projeto2024Alex.ProjetoComDTO.service.impl;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoEntregaDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoFaturamentoDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoViacepDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.service.feign.ViacepApiFeng;
import com.br.Projeto2024Alex.ProjetoComDTO.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    private final ViacepApiFeng viacepApiFeng;

    @Autowired
    public EnderecoServiceImpl(ViacepApiFeng viacepApiFeng) {
        this.viacepApiFeng = viacepApiFeng;
    }

    @Override
    public EnderecoEntregaDTO executaBuscaEnderecoEntrega(EnderecoViacepDTO request) {
        return viacepApiFeng.buscaEnderecoEntregaCep(request.getCep());
    }

    @Override
    public EnderecoFaturamentoDTO executaBuscaEnderecoFaturamento(EnderecoViacepDTO request) {
        return viacepApiFeng.buscaEnderecoFaturamentoCep(request.getCep());
    }
}
