package com.br.Projeto2024Alex.ProjetoComDTO.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

public interface CarrinhoService {

    String adicionarItemCarrinho(Long id);

    ModelAndView carrinhoExibir();

    String editarQuantidade(Long id, Integer acao);

    String removerProduto(Long id);

    String adicionarMetoPagamento(ModelAndView modelAndView, HttpSession session);

    String finalizarCompra(String opcaoPagamento, Model model, HttpSession session);

    String compraFinalizada(Model model, HttpSession session);
}
