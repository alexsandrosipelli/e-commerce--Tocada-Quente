package com.br.Projeto2024Alex.ProjetoComDTO.controller;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ProdutoDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/listar")
    public String listarProdutos(Model model, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String keyword) {
        Page<ProdutoDTO> produtosPage = produtoService.listarProdutosPorNomePaginado(keyword, PageRequest.of(page, 10, Sort.by("id").descending()));
        model.addAttribute("produtosPage", produtosPage);
        model.addAttribute("keyword", keyword);
        return "listar-Produtos";
    }

    @GetMapping("/form")
    public String exibirFormularioProduto(Model model) {
        model.addAttribute("produto", new ProdutoDTO());
        return "formProduto";
    }

    @PostMapping("/criar")
    public String criarProduto(@ModelAttribute("produto") ProdutoDTO produtoDTO, @RequestParam("imagens") MultipartFile[] imagens) throws IOException {
        // Converter o array de imagens para uma lista
        List<MultipartFile> imagensList = Arrays.asList(imagens);

        // Salvar imagens e obter seus caminhos
        String[] caminhosImagens = produtoService.salvarImagens(imagensList);
        List<String> caminhosImagensList = Arrays.asList(caminhosImagens);
        // Associar caminhos das imagens ao produto
        produtoDTO.setCaminhosImagens(caminhosImagensList);

        // Criar o produto com as imagens associadas
        produtoService.criarProduto(produtoDTO);
        return "redirect:/produtos/listar";
    }

    // Adicionar outros métodos para ações como alterar, inativar, reativar e visualizar produtos
}
