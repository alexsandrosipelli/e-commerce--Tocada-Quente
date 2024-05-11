package com.br.Projeto2024Alex.ProjetoComDTO.service;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ProdutoDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ProdutoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProdutoService {

    List<ProdutoDTO> listarProdutos();

    void editarProdutoEstoquista(ProdutoDTO produtoDTO);

    void editarProduto(ProdutoDTO produtoDTO, List<MultipartFile> imagens) throws IOException;

    Page<ProdutoDTO> listarProdutosPorNomePaginado(String keyword, Pageable pageable);

    void criarProduto(ProdutoDTO produtoDTO, List<MultipartFile> imagens) throws IOException;

    void mudarStatusProduto(Long id);

    ProdutoDTO buscarProdutoPorId(Long id);

    ProdutoEntity buscarProdutoPorIdEntity(Long id);
}
