/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.dto;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data // Anotação para gerar automaticamente getters, setters, toString, equals e hashCode
@EqualsAndHashCode(of = "id") // Gera automaticamente equals e hashCode usando apenas o campo "id"
public class ClienteDTO {

    private Long id;

    @NotNull(message = "O campo nome não deve estar nulo")
    @NotBlank(message = "O campo nome não deve estar em branco")
    @Size(min = 3, message = "O nome deve conter no mínimo 3 caracteres!")
    private String nome;

    @NotNull(message = "O campo Data de Nascimento não deve estar nulo")
    @NotBlank(message = "O campo Data de Nascimento não deve estar em branco")
    private String dataNascimento;

    @NotNull(message = "O campo Gênero não deve estar nulo")
    @NotBlank(message = "O campo Gênero não deve estar em branco")
    private String genero;

    @NotNull(message = "O campo E-mail não deve estar nulo")
    @NotBlank(message = "O campo E-mail não deve estar em branco")
    @Email
    private String email;

    @NotNull(message = "O campo Senha não deve estar nulo")
    @NotBlank(message = "O campo Senha não deve estar em branco")
    @Size(min = 3, max = 100, message = "A senha deve conter no mínimo 3 e no máximo 100 caracteres")
    private String senha;

    @NotNull(message = "O campo CPF não deve estar nulo")
    @NotBlank(message = "O campo CPF não deve estar em branco")
    @CPF
    private String cpf;

    @NotNull(message = "O campo CEP não deve estar nulo")
    @NotBlank(message = "O campo CEP não deve estar em branco")
    @Size(min = 8, max = 8, message = "O CEP deve ter no máximo e mínimo 8 números")
    private String cep;

    @NotNull(message = "O campo Cidade não deve estar nulo")
    @NotBlank(message = "O campo Cidade não deve estar em branco")
    private String cidade;

    @NotNull(message = "O campo UF não deve estar nulo")
    @NotBlank(message = "O campo UF não deve estar em branco")
    private String uf;

    private List<EnderecoFaturamentoDTO> enderecoFaturamentoDto;
    private List<EnderecoEntregaDTO> enderecoEntregaDto;

    public static ClienteDTO fromEntity(ClienteDTO usuario) {
        ClienteDTO clienteDto = new ClienteDTO();
        BeanUtils.copyProperties(usuario, clienteDto);
        return clienteDto;
    }

    public ClienteEntity toEntity() {
        ClienteEntity usuarioEntity = new ClienteEntity();
        BeanUtils.copyProperties(this, usuarioEntity);
        return usuarioEntity;
    }
}
