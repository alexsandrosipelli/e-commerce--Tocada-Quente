/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.dto;

/**
 *
 * @author alexs
 */
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ProdutoEntity;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoDTO {

    private Long id;
    private String nome;
    private double avaliacao;
    private String descricaoDetalhada;
    private BigDecimal preco;
    private int qtdEstoque;
    private boolean status;
    private List<String> caminhosImagens; // Lista de caminhos das imagens associadas ao produto
    private String imagemPrincipal; // Ca
    // Construtores

    public ProdutoDTO() {
    }

    public ProdutoDTO(Long id, String nome, double avaliacao, String descricaoDetalhada, BigDecimal preco, int qtdEstoque, boolean status) {
        this.id = id;
        this.nome = nome;
        this.avaliacao = avaliacao;
        this.descricaoDetalhada = descricaoDetalhada;
        this.preco = preco;
        this.qtdEstoque = qtdEstoque;
        this.status = status;
    }

    public static ProdutoDTO fromEntity(ProdutoEntity entity) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setAvaliacao(entity.getAvaliacao());
        dto.setDescricaoDetalhada(entity.getDescricaoDetalhada());
        dto.setPreco(entity.getPreco());
        dto.setQtdEstoque(entity.getQtdEstoque());
        dto.setStatus(entity.isStatus());
        return dto;
    }

    public static List<ProdutoDTO> fromEntityList(List<ProdutoEntity> entities) {
        return entities.stream()
                .map(ProdutoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public ProdutoEntity toEntity() {
        ProdutoEntity entity = new ProdutoEntity();
        entity.setId(this.getId());
        entity.setNome(this.getNome());
        entity.setAvaliacao(this.getAvaliacao());
        entity.setDescricaoDetalhada(this.getDescricaoDetalhada());
        entity.setPreco(this.getPreco());
        entity.setQtdEstoque(this.getQtdEstoque());
        entity.setStatus(this.isStatus());
        return entity;
    }

    public List<String> getCaminhosImagens() {
        return caminhosImagens;
    }

    public void setCaminhosImagens(List<String> caminhosImagens) {
        this.caminhosImagens = caminhosImagens;
    }

    public String getImagemPrincipal() {
        return imagemPrincipal;
    }

    public void setImagemPrincipal(String imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getDescricaoDetalhada() {
        return descricaoDetalhada;
    }

    public void setDescricaoDetalhada(String descricaoDetalhada) {
        this.descricaoDetalhada = descricaoDetalhada;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
