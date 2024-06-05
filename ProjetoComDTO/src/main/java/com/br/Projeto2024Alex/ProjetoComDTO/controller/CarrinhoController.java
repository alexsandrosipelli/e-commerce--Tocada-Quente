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

import com.br.Projeto2024Alex.ProjetoComDTO.service.impl.CarrinhoServiceImpl;
import jakarta.servlet.http.HttpSession;
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
@RequestMapping("/site/carrinhoDeCompras")
public class CarrinhoController {

    private CarrinhoServiceImpl carrinhoService;

    private ProdutoRepository produtoRepository;

    @Autowired
    public CarrinhoController(CarrinhoServiceImpl carrinhoService, ProdutoRepository produtoRepository) {
        this.carrinhoService = carrinhoService;
        this.produtoRepository = produtoRepository;
    }

    @GetMapping("/")
    public ModelAndView chamarCarrinho() {
        return carrinhoService.carrinhoExibir();
    }

    @GetMapping("/adicionar/{id}")
    public String adicionarCarrinho(@PathVariable Long id) {
        return carrinhoService.adicionarItemCarrinho(id);
    }

    @GetMapping("/alterarQuantidade/{id}/{acao}")
    public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {
        return carrinhoService.editarQuantidade(id, acao);
    }

    @GetMapping("/removerProduto/{id}")
    public String removerProduto(@PathVariable Long id) {
        return carrinhoService.removerProduto(id);
    }

    @GetMapping("/pagamento")
    public String adicionarFormaPagamento(ModelAndView modelAndView, HttpSession session){
        return carrinhoService.adicionarMetoPagamento(modelAndView, session);
    }

}
