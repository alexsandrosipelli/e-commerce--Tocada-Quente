/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.controller;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ProdutoDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ClienteEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.CompraEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ItemCompraEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ProdutoEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.ProdutoRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.br.Projeto2024Alex.ProjetoComDTO.service.impl.CarrinhoServiceImpl;
import com.br.Projeto2024Alex.ProjetoComDTO.service.impl.ProdutoServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    private ProdutoServiceImpl produtoService;

    @Autowired
    public CarrinhoController(CarrinhoServiceImpl carrinhoService, ProdutoRepository produtoRepository, ProdutoServiceImpl produtoService) {
        this.carrinhoService = carrinhoService;
        this.produtoRepository = produtoRepository;
      this.produtoService = produtoService;
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

    @PostMapping("/finalizar-compra/{indexPagamento}")
    public String finalizarCompra(@RequestParam("indexPagamento") String opcaoPagamento, Model model, HttpSession session){
        return carrinhoService.finalizarCompra(opcaoPagamento, model, session);
    }

    @GetMapping("/retornar-site")
    public String retornarTelaHome(HttpSession session, Model model){
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("clienteLogado");
        if (cliente != null) {
            model.addAttribute("clienteLogado", cliente);
        }
        List<ProdutoDTO> produtos = produtoService.listarProdutos();

        // Para cada produto na lista de produtos, define o caminho da imagem principal
        produtos.forEach(produto -> produto.setImagemPrincipalString(produto.getImagemPrincipalStringIndex()));

        model.addAttribute("produtos", produtos);

        return "loja-apresentada";
    }

    @GetMapping("/compra-finalizada")
    public String comprarFinalizada(Model model, HttpSession session){
        return carrinhoService.compraFinalizada(model, session);
    }


}
