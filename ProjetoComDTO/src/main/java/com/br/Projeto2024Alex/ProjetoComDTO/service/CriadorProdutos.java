/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.service;

/**
 *
 * @author alexs
 */
 

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ImagemProdutoDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.ProdutoDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CriadorProdutos {

    public static List<ProdutoDTO> criarProdutos() {
        List<ProdutoDTO> produtos = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            ProdutoDTO produto = new ProdutoDTO();
            produto.setId((long) i);
            produto.setNome("Produto " + i);
            produto.setAvaliacao(4.5); // Avaliação fixa para todos os produtos
            produto.setDescricaoDetalhada("Descrição do Produto " + i);
            produto.setPrecoProduto(new BigDecimal(100 + i * 10)); // Preço variável para cada produto
            produto.setQtdEstoque(100); // Quantidade de estoque fixa para todos os produtos
            produto.setStatus(true); // Status ativo para todos os produtos

            List<ImagemProdutoDTO> imagens = new ArrayList<>();
            imagens.add(criarImagem("1711656084865_img3")); // Nome da primeira imagem
            imagens.add(criarImagem("1711656084867_img4")); // Nome da segunda imagem
            imagens.add(criarImagem("1711658384432_bateria2")); // Nome da terceira imagem

            produto.setImagens(imagens);

            produtos.add(produto);
        }

        return produtos;
    }

    private static ImagemProdutoDTO criarImagem(String nomeImagem) {
        ImagemProdutoDTO imagem = new ImagemProdutoDTO();
        imagem.setId((long) Math.floor(Math.random() * 1000)); // ID aleatório para cada imagem
        imagem.setCaminho("/caminho/imagens/" + nomeImagem + ".jpg");
        imagem.setPrincipal(false); // Definir se é imagem principal conforme necessário
        imagem.setProdutoId(null); // Definir o ID do produto associado se necessário

        return imagem;
    }
}
