/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;
import java.util.List;

/**
 *
 * @author alexs
 */
@Entity
@Table(name = "cliente")
@Data // Anotação para gerar automaticamente getters, setters, toString, equals e hashCode
@EqualsAndHashCode(of = "id") // Gera automaticamente equals e hashCode usando apenas o campo "id"
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 100, message = "O nome deve conter no mínimo 3 caracteres!")
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "dataNascimento", nullable = false)
    private String dataNascimento;

    @NotNull
    @Column(name = "genero", nullable = false)
    private String genero;

    @NotNull
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(min = 3, max = 100, message = "A senha deve conter no mínimo 3 e no máximo 100 caracteres")
    @Column(name = "senha", nullable = false)
    private String senha;

    @NotNull
    @CPF
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @NotNull
    @Size(min = 8, max = 8, message = "O CEP deve ter no máximo e mínimo 8 números")
    @Column(name = "cep", nullable = false)
    private String cep;

    @NotNull
    @Column(name = "cidade", nullable = false)
    private String localidade;

    @NotNull
    @Column(name = "uf", nullable = false)
    private String uf;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<EnderecoFaturamentoEntity> enderecosFaturamento;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<EnderecoEntregaEntity> enderecosEntrega;

    @Temporal(TemporalType.TIMESTAMP) //Indica que o dado é do tipo de data e hora do banco de dados
    @Column(name = "data_inclusao", nullable = false, updatable = false) //Esse campo não vai ser atualizado
    private Date dataInclusao = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_atualizacao", nullable = false) //Esse campo é sempre atualizado quando o dado for alterado
    private Date dataAtualizacao = new Date();
}
