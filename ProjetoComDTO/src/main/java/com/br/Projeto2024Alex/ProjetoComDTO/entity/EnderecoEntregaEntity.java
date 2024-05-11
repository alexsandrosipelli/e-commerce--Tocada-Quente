package com.br.Projeto2024Alex.ProjetoComDTO.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Entity
@Table(name = "endereco_entrega")
@Data // Anotação para gerar automaticamente getters, setters, toString, equals e hashCode
@EqualsAndHashCode(of = "id") // Gera automaticamente equals e hashCode usando apenas o campo "id"
public class EnderecoEntregaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 8, max = 8)
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

    @Column(name = "bairro", nullable = false)
    private String bairro;

    @Column(name = "cidade", nullable = false)
    private String cidade;

    @Size(min = 2, max = 2)
    @Column(name = "uf", nullable = false)
    private String uf;

    @ManyToOne(fetch = FetchType.LAZY) //define que os endereços de um cliente serão carregados apenas quando explicitamente solicitados, o que pode melhorar o desempenho em algumas situações.
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;

    @Temporal(TemporalType.TIMESTAMP) //Indica que o dado é do tipo de data e hora do banco de dados
    @Column(name = "data_inclusao", nullable = false, updatable = false) //Esse campo não vai ser atualizado
    private Date dataInclusao = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_atualizacao", nullable = false) //Esse campo é sempre atualizado quando o dado for alterado
    private Date dataAtualizacao = new Date();

}
