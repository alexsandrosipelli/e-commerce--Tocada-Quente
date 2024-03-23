/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.dto;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.ImagemProdutoEntity;
import org.modelmapper.ModelMapper;

public class ImagemProdutoDTO {

    private Long id;
    private String caminho;
    private boolean principal;
    private Long produtoId; // Id do produto associado à imagem

    public ImagemProdutoEntity toEntity() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ImagemProdutoEntity.class);
    }

    public static ImagemProdutoDTO fromEntity(ImagemProdutoEntity imagemProdutoEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(imagemProdutoEntity, ImagemProdutoDTO.class);
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

}
