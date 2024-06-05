/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.dto;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data // Anotação para gerar automaticamente getters, setters, toString, equals e hashCode
@EqualsAndHashCode(of = "id") // Gera automaticamente equals e hashCode usando apenas o campo "id"
public class ClienteDTO {

    private Long id;
    private String nome;
    private String dataNascimento;
    private String genero;
    private String email;
    private String senha;
    private String cpf;
    private String cep;
    private Integer numero;
    private String complemento;
    private String localidade;
    private String uf;
    private List<EnderecoFaturamentoDTO> enderecoFaturamento;
    private List<EnderecoEntregaDTO> enderecoEntrega;

    public static ClienteDTO fromEntity(ClienteDTO usuario) {
        ClienteDTO clienteDto = new ClienteDTO();
        BeanUtils.copyProperties(usuario, clienteDto);
        return clienteDto;
    }

    public ClienteEntity toEntity() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(this.getId());
        clienteEntity.setNome(this.getNome());
        clienteEntity.setDataNascimento(this.getDataNascimento());
        clienteEntity.setGenero(this.getGenero());
        clienteEntity.setEmail(this.getEmail());
        clienteEntity.setSenha(this.getSenha());
        clienteEntity.setCpf(this.getCpf());
        clienteEntity.setCep(this.getCep());
        clienteEntity.setLocalidade(this.getLocalidade());
        clienteEntity.setUf(this.getUf());
        clienteEntity.setNumero(this.getNumero());
        clienteEntity.setComplemento(this.getComplemento());
        return clienteEntity;
    }

}
