package com.br.Projeto2024Alex.ProjetoComDTO.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Entity
@Table(name = "endereco_faturamento")
@Data
@EqualsAndHashCode(of = "id")
public class EnderecoFaturamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cep", nullable = false)
    private String cep;

    @NotNull
    @Column(name = "logradouro", nullable = false)
    private String logradouro;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @Column(name = "complemento")
    private String complemento;

    @NotNull
    @Column(name = "bairro", nullable = false)
    private String bairro;

    @NotNull
    @Column(name = "cidade", nullable = false)
    private String localidade;

    @Size(min = 2, max = 2)
    @Column(name = "uf", nullable = false)
    private String uf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;

    @Column(name = "enderecoPrincipal")
    private Boolean enderecoPrincipal;

    @Column(name = "status")
    private Boolean status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_inclusao", nullable = false, updatable = false)
    private Date dataInclusao = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_atualizacao", nullable = false)
    private Date dataAtualizacao = new Date();
}
