package com.br.Projeto2024Alex.ProjetoComDTO.service.feign;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.EnderecoEntregaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://viacep.com.br/ws/", name = "guerra")
public interface EnderecoFeign {

    //Por ser uma API externa, não é possível debugar
    @GetMapping("{cep}/json")
    EnderecoEntregaDTO buscaEnderecoCep(@PathVariable("cep") String cep);
}
