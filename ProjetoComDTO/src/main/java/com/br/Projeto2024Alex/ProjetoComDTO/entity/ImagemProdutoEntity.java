package com.br.Projeto2024Alex.ProjetoComDTO.entity;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ImagemProdutoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "imagem_produto")
public class ImagemProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "caminho", nullable = false)
    private String caminho;

    @Column(name = "principal", nullable = false)
    private boolean principal;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private ProdutoEntity produto;

    // outros atributos e métodos
    // Getters e setters
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

    public ProdutoEntity getProduto() {
        return produto;
    }

    public void setProduto(ProdutoEntity produto) {
        this.produto = produto;
    }

    public ImagemProdutoDTO toDTO() {
        ImagemProdutoDTO imagemProdutoDTO = new ImagemProdutoDTO();
        imagemProdutoDTO.setId(this.id);
        imagemProdutoDTO.setCaminho(this.caminho);
        imagemProdutoDTO.setPrincipal(this.principal);
        // Não é necessário definir o ProdutoEntity no DTO, a menos que seja necessário
        return imagemProdutoDTO;
    }
}
