package com.br.Projeto2024Alex.ProjetoComDTO.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data // Anotação para gerar automaticamente getters, setters, toString, equals e hashCode
@EqualsAndHashCode(of = "id") // Gera automaticamente equals e hashCode usando apenas o campo "id"
public class EnderecoEntregaDTO {
    public EnderecoEntregaDTO() {

    }

    public EnderecoEntregaDTO(String cep, String logradouro, Integer numero, String complemento, String bairro,
                              String localidade, ClienteDTO cliente, String uf, Boolean enderecoPrincipal,
                              Boolean status) {
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
    @NotNull(message = "O campo CEP não deve estar nulo")
    @NotBlank(message = "O campo CEP não deve estar vazio")
    private String cep;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String localidade;
    private ClienteDTO cliente;
    private String uf;
    private Boolean enderecoPrincipal;
    private Boolean status;
}
