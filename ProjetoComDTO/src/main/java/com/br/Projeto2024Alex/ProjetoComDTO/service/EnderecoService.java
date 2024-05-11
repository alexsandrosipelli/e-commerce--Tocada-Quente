package com.br.Projeto2024Alex.ProjetoComDTO.service;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoEntregaDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoViacepDTO;


public interface EnderecoService {
    EnderecoEntregaDTO executa(EnderecoViacepDTO request);
}
