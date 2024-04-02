/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.controller;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ImagemProdutoDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.ProdutoDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.service.ProdutoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author alexs
 */
@Controller
@RequestMapping("/site")
public class SiteController {

    @Autowired
    private ProdutoService produtoService;
    private static final String UPLOAD_DIR = "src/main/resources/static/imagem";

    @GetMapping("/")
    public String inicial(Model model) {
        List<ProdutoDTO> produtos = produtoService.listarProdutos();

        // Para cada produto na lista de produtos, define o caminho da imagem principal
        produtos.forEach(produto -> {
            int indexImagemPrincipal = produto.getImagemPrincipal();
            ImagemProdutoDTO imagemPrincipal = produto.getImagens().get(indexImagemPrincipal);
            produto.setImagemPrincipalString(imagemPrincipal.getCaminho());
        });

        model.addAttribute("produtos", produtos);

        return "/loja-apresentada";
    }

    @GetMapping("/{id}")
    public String detalheProduto(@PathVariable Long id, Model model) {
        ProdutoDTO produtoDTO = produtoService.buscarProdutoPorId(id);
        int indexImagemPrincipal = produtoDTO.getImagemPrincipal();
        ImagemProdutoDTO imagemPrincipal = produtoDTO.getImagens().get(indexImagemPrincipal);
        produtoDTO.setImagemPrincipalString(imagemPrincipal.getCaminho());

        model.addAttribute(produtoDTO);

        return "Tela-apresentar-detalhe";
    }
}
