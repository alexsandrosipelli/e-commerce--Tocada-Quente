/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.dto;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.ProdutoEntity;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ProdutoDTO {

    @Autowired
    private ModelMapper modelMapper = new ModelMapper();

    private Long id;
    private String nome;
    private double avaliacao;
    private String descricaoDetalhada;
    private BigDecimal precoProduto;
    private int qtdEstoque;
    private boolean status;
    private int ImagemPrincipal;
    private String imagemPrincipalString;
    @Valid
    private List<ImagemProdutoDTO> imagens;

    public ProdutoEntity toEntity() {
        return modelMapper.map(this, ProdutoEntity.class);

    }

    public ProdutoDTO fromEntity(ProdutoEntity produtoEntity) {
        return modelMapper.map(produtoEntity, ProdutoDTO.class);
    }

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public String getImagemPrincipalString() {
        return imagemPrincipalString;
    }

    public void setImagemPrincipalString(String imagemPrincipalString) {
        this.imagemPrincipalString = imagemPrincipalString;
    }

    public int getImagemPrincipal() {
        return ImagemPrincipal;
    }

    // Getters e Setters
    public void setImagemPrincipal(int ImagemPrincipal) {
        this.ImagemPrincipal = ImagemPrincipal;
    }

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

    public BigDecimal getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(BigDecimal precoProduto) {
        this.precoProduto = precoProduto;
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

    public List<ImagemProdutoDTO> getImagens() {
        return imagens;
    }

    public void setImagens(List<ImagemProdutoDTO> imagens) {
        this.imagens = imagens;
    }

    public String getImagemPrincipalStringIndex() {
        ImagemProdutoDTO imagemPrincipal = getImagemPrincipal1();
        if (imagemPrincipal != null) {
            return imagemPrincipal.getCaminho();
        } else {
            return null;
        }
    }

    public ImagemProdutoDTO getImagemPrincipal1() {
        // Percorre a lista de imagens do produto
        for (ImagemProdutoDTO imagem : imagens) {
            // Verifica se a imagem tem o status principal ativo
            if (imagem.isPrincipal()) {
                // Retorna a imagem principal encontrada
                return imagem;
            }
        }
        // Se não encontrar nenhuma imagem principal, retorna null
        return null;
    }

    public String getCaminhoImagemPrincipal() {
        String caminhoImagemPrincipal = null;
        for (ImagemProdutoDTO imagem : imagens) {
            if (imagem.isPrincipal()) {
                caminhoImagemPrincipal = imagem.getCaminho();
                break; // Encontrou a imagem principal, pode sair do loop
            }
        }
        return caminhoImagemPrincipal;
    }

    public int getIndiceImagemPrincipal() {
        int indiceImagemPrincipal = -1; // Valor padrão caso não encontre a imagem principal
        for (int i = 0; i < imagens.size(); i++) {
            ImagemProdutoDTO imagem = imagens.get(i);
            if (imagem.isPrincipal()) {
                indiceImagemPrincipal = i; // Atualiza o índice da imagem principal
                break; // Encontrou a imagem principal, pode sair do loop
            }
        }
        return indiceImagemPrincipal;
    }
}
