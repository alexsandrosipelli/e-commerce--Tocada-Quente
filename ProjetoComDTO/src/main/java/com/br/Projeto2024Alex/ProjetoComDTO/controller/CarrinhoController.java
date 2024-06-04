/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.controller;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.CompraEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ItemCompraEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ProdutoEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.ProdutoRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hiago
 */
@Controller
@RequestMapping("/carrinhoDeCompras")
public class CarrinhoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarrinhoController.class);

    private List<ItemCompraEntity> itemCompra = new ArrayList<>();
    private CompraEntity compra = new CompraEntity();

    private void calcularTotal() {
        // Inicializa o valor total como BigDecimal zero.
        BigDecimal total = BigDecimal.ZERO;

        // Itera sobre os itens da compra.
        for (ItemCompraEntity it : itemCompra) {
            // Soma o valor total do item ao total.
            total = total.add(it.getValorTotal());
        }

        // Define o valor total na compra.
        compra.setValorTotal(total);
    }

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/")
    public ModelAndView chamarCarrinho() {
        ModelAndView mv = new ModelAndView("carrinho");
        calcularTotal();
        mv.addObject("compra", compra);
        mv.addObject("listaItens", itemCompra);
        return mv;
    }

    @GetMapping("/adicionar/{id}")
    public String adicionarCarrinho(@PathVariable Long id) {
        LOGGER.info("Chamando o método adicionarCarrinho() para o id: {}", id);

        Optional<ProdutoEntity> produtoOpt = produtoRepository.findById(id);

        if (produtoOpt.isPresent()) {
            ProdutoEntity prod = produtoOpt.get();
            LOGGER.info("Produto encontrado: {}", prod);

            int controle = 0;
            for (ItemCompraEntity it : itemCompra) {
                if (it.getProdutoEntity().getId().equals(prod.getId())) {
                    LOGGER.info("Produto já existe no carrinho. Atualizando a quantidade.");
                    it.setQuantidade(it.getQuantidade() + 1);
                    it.calcularValorTotal();
                    controle = 1;
                    break;
                }
            }

            if (controle == 0) {
                LOGGER.info("Produto não encontrado no carrinho. Adicionando um novo item.");
                ItemCompraEntity item = new ItemCompraEntity();
                item.setProdutoEntity(prod);
                item.setValorUnitario(prod.getPrecoProduto());
                item.setQuantidade(1); // Inicializa a quantidade como 1 para um novo item
                item.calcularValorTotal();
                itemCompra.add(item);
            }

        } else {
            LOGGER.error("Produto com id {} não encontrado.", id);
        }

        return "redirect:/carrinhoDeCompras/";
    }

    @GetMapping("/alterarQuantidade/{id}/{acao}")
    public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {

        for (ItemCompraEntity it : itemCompra) {
            if (it.getProdutoEntity().getId().equals(id)) {
                if (acao == 1) {
                    it.setQuantidade(it.getQuantidade() + 1);
                    it.calcularValorTotal();
                } else if (acao == 0) {
                    it.setQuantidade(it.getQuantidade() - 1);
                    it.calcularValorTotal();
                }
                break;
            }
        }

        return "redirect:/carrinhoDeCompras/";
    }

    @GetMapping("/removerProduto/{id}")
    public String removerProduto(@PathVariable Long id) {

        for (ItemCompraEntity it : itemCompra) {
            if (it.getProdutoEntity().getId().equals(id)) {
                itemCompra.remove(it);
                it.calcularValorTotal();
                break;
            }
        }

        return "redirect:/carrinhoDeCompras/";
    }

}
