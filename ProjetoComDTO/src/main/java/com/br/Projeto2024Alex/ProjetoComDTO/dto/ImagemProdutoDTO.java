/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.dto;

/**
 *
 * @author alexs
 */
public class ImagemProdutoDTO {

    private Long id;
    private String caminho;
    private boolean principal;
    private Long produtoId; // ID do produto associado à imagem

    public ImagemProdutoDTO() {
    }

    // Construtor com todos os atributos
    public ImagemProdutoDTO(Long id, String caminho, boolean principal, Long produtoId) {
        this.id = id;
        this.caminho = caminho;
        this.principal = principal;
        this.produtoId = produtoId;
    }

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
