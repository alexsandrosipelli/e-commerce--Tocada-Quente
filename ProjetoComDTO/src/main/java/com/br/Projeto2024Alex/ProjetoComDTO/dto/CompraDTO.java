
package com.br.Projeto2024Alex.ProjetoComDTO.dto;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(of = "id")
public class CompraDTO {

  private Long id;
  private ClienteEntity cliente;
  private Date dataCompra = new Date();
  private String formaPagamento;
  private BigDecimal valorTotal = BigDecimal.ZERO;
}
