package com.br.Projeto2024Alex.ProjetoComDTO.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cliente")
@Data
@EqualsAndHashCode(of = "id")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo nome não deve estar nulo")
    @NotBlank(message = "O campo nome não deve estar em branco")
    @Size(min = 3, max = 100, message = "O nome deve conter no mínimo 3 caracteres!")
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull(message = "O campo Data de Nascimento não deve estar nulo")
    @NotBlank(message = "O campo Data de Nascimento não deve estar em branco")
    @Column(name = "dataNascimento", nullable = false)
    private String dataNascimento;

    @NotNull(message = "O campo Gênero não deve estar nulo")
    @NotBlank(message = "O campo Gênero não deve estar em branco")
    @Column(name = "genero", nullable = false)
    private String genero;

    @NotNull(message = "O campo E-mail não deve estar nulo")
    @NotBlank(message = "O campo E-mail não deve estar em branco")
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull(message = "O campo Senha não deve estar nulo")
    @NotBlank(message = "O campo Senha não deve estar em branco")
    @Size(min = 3, max = 100, message = "A senha deve conter no mínimo 3 e no máximo 100 caracteres")
    @Column(name = "senha", nullable = false)
    private String senha;

    @NotNull(message = "O campo CPF não deve estar nulo")
    @NotBlank(message = "O campo CPF não deve estar em branco")
    @CPF
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @NotNull(message = "O campo CEP não deve estar nulo")
    @NotBlank(message = "O campo CEP não deve estar em branco")
    @Size(min = 8, max = 8, message = "O CEP deve ter no máximo e mínimo 8 números")
    @Column(name = "cep", nullable = false)
    private String cep;

    @Transient
    private Integer numero;

    @Transient
    private String complemento;

    @NotNull(message = "O campo Cidade não deve estar nulo")
    @NotBlank(message = "O campo Cidade não deve estar em branco")
    @Column(name = "cidade", nullable = false)
    private String localidade;

    @NotNull(message = "O campo UF não deve estar nulo")
    @NotBlank(message = "O campo UF não deve estar em branco")
    @Size(min = 2, max = 2, message = "A UF deve ter no máximo e mínimo 2 caracteres")
    @Column(name = "uf", nullable = false)
    private String uf;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnderecoFaturamentoEntity> enderecosFaturamento = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnderecoEntregaEntity> enderecosEntrega = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_inclusao", nullable = false, updatable = false)
    private Date dataInclusao = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_atualizacao", nullable = false)
    private Date dataAtualizacao = new Date();
}
