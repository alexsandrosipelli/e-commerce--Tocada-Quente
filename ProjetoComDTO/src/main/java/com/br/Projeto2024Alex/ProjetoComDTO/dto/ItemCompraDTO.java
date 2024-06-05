/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.dto;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.CompraEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ProdutoEntity;
import lombok.Data;

import java.math.BigDecimal;
@Data
//@EqualsAndHashCode(of = "id")
public class ItemCompraDTO {

  private Long id;
  private ProdutoEntity produtoEntity;
  private CompraEntity compraEntity;
  private int quantidade = 0;
  private BigDecimal valorUnitario;
  private BigDecimal valorTotal;

  public void calcularValorTotal() {
    this.valorTotal = this.valorUnitario.multiply(new BigDecimal(this.quantidade));
    System.out.println(valorTotal);
    System.out.println(quantidade);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ProdutoEntity getProdutoEntity() {
    return produtoEntity;
  }

  public void setProdutoEntity(ProdutoEntity produtoEntity) {
    this.produtoEntity = produtoEntity;
  }

  public CompraEntity getCompraEntity() {
    return compraEntity;
  }

  public void setCompraEntity(CompraEntity compraEntity) {
    this.compraEntity = compraEntity;
  }

  public int getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(int quantidade) {
    this.quantidade = quantidade;
  }

  public BigDecimal getValorUnitario() {
    return valorUnitario;
  }

  public void setValorUnitario(BigDecimal valorUnitario) {
    this.valorUnitario = valorUnitario;
  }

  public BigDecimal getValorTotal() {
    return valorTotal;
  }

  public void setValorTotal(BigDecimal valorTotal) {
    this.valorTotal = valorTotal;
  }


}

