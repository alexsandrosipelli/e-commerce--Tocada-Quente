package com.br.Projeto2024Alex.ProjetoComDTO.service.feign;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoEntregaDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoFaturamentoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://viacep.com.br/ws/", name = "guerra")
public interface ViacepApiFeng {

    //Por ser uma API externa, não é possível debugar
    @GetMapping("{cep}/json/")
    EnderecoEntregaDTO buscaEnderecoEntregaCep(@PathVariable("cep") String cep);

    @GetMapping("{cep}/json/")
    EnderecoFaturamentoDTO buscaEnderecoFaturamentoCep(@PathVariable("cep") String cep);
}
