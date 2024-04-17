/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.dto;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 *
 * @author alexs
 */
public class ClienteDTO {

    private Long id;
    private String nome;
    private String dataNascimento;
    private String genero;
    private String email;
    private String senha;
    private String cpf;
    private Integer cep;
    private String cidade;
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
