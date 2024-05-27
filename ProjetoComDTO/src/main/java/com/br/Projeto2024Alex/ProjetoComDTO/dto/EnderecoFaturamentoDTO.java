package com.br.Projeto2024Alex.ProjetoComDTO.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data // Anotação para gerar automaticamente getters, setters, toString, equals e hashCode
@EqualsAndHashCode(of = "id") // Gera automaticamente equals e hashCode usando apenas o campo "id"
public class EnderecoFaturamentoDTO {
    public EnderecoFaturamentoDTO() {

    }

    public EnderecoFaturamentoDTO(String cep, String logradouro, String numero, String complemento, String bairro,
                                  String localidade, ClienteDTO cliente, String uf, boolean enderecoPrincipal,
                                  boolean status) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.localidade = localidade;
        this.cliente = cliente;
        this.uf = uf;
        this.enderecoPrincipal = enderecoPrincipal;
        this.status = status;
    }

    private Long id;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String localidade;
    private ClienteDTO cliente;
    private String uf;
    private boolean enderecoPrincipal;
    private boolean status;
}
