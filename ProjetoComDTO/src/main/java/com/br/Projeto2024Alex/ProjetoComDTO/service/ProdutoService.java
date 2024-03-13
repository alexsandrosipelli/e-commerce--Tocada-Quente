package com.br.Projeto2024Alex.ProjetoComDTO.service;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ProdutoDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ImagemProdutoEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ProdutoEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.ProdutoRepository;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProdutoService {

    private static final String UPLOAD_DIR = "C:\\Users\\alexs\\OneDrive\\Área de Trabalho\\projeto atualizado via github\\Projeto-Integrador-2024\\imagens";

    @Autowired
    private ProdutoRepository produtoRepository;

    public Page<ProdutoDTO> listarProdutosPorNomePaginado(String keyword, Pageable pageable) {
        Page<ProdutoEntity> produtosPage;
        if (keyword != null && !keyword.isEmpty()) {
            produtosPage = produtoRepository.findByNomeContainingIgnoreCase(keyword, pageable);
        } else {
            produtosPage = produtoRepository.findAll(pageable);
        }
        return produtosPage.map(this::toDTO);
    }

    // Método para converter de ProdutoEntity para ProdutoDTO
    private ProdutoDTO toDTO(ProdutoEntity produtoEntity) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produtoEntity.getId());
        produtoDTO.setNome(produtoEntity.getNome());
        produtoDTO.setAvaliacao(produtoEntity.getAvaliacao());
        produtoDTO.setDescricaoDetalhada(produtoEntity.getDescricaoDetalhada());
        produtoDTO.setPreco(produtoEntity.getPreco());
        produtoDTO.setQtdEstoque(produtoEntity.getQtdEstoque());
        produtoDTO.setStatus(produtoEntity.isStatus());
        return produtoDTO;
    }

    public void criarProduto(ProdutoDTO produtoDTO) {
        // Converter ProdutoDTO para ProdutoEntity
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setNome(produtoDTO.getNome());
        produtoEntity.setAvaliacao(produtoDTO.getAvaliacao());
        produtoEntity.setDescricaoDetalhada(produtoDTO.getDescricaoDetalhada());
        produtoEntity.setPreco(produtoDTO.getPreco());
        produtoEntity.setQtdEstoque(produtoDTO.getQtdEstoque());
        produtoEntity.setStatus(produtoDTO.isStatus());

        // Associar imagens ao produto
        List<ImagemProdutoEntity> imagens = produtoDTO.getCaminhosImagens().stream()
                .map(caminho -> {
                    ImagemProdutoEntity imagem = new ImagemProdutoEntity();
                    imagem.setCaminho(caminho);
                    imagem.setPrincipal(produtoDTO.getImagemPrincipal() != null && caminho.equals(produtoDTO.getImagemPrincipal()));
                    imagem.setProduto(produtoEntity);
                    return imagem;
                })
                .collect(Collectors.toList());

        produtoEntity.setImagens(imagens);

        // Salvar o produto no banco de dados
        produtoRepository.save(produtoEntity);
    }

    public String[] salvarImagens(List<MultipartFile> imagens) {
        List<String> caminhosImagens = new ArrayList<>();

        // Percorre cada imagem enviada
        for (MultipartFile imagem : imagens) {
            try {
                // Gera um nome único para a imagem
                String nomeImagem = System.currentTimeMillis() + "_" + imagem.getOriginalFilename();

                // Cria o caminho completo para salvar a imagem
                String caminhoImagem = UPLOAD_DIR + File.separator + nomeImagem;

                // Salva a imagem no diretório
                imagem.transferTo(new File(caminhoImagem));

                // Adiciona o caminho da imagem à lista de caminhos
                caminhosImagens.add(caminhoImagem);
            } catch (IOException e) {
                e.printStackTrace();
                // Lida com erros de IO, se necessário
            }
        }

        // Converte a lista de caminhos para um array de strings
        return caminhosImagens.toArray(new String[0]);
    }

    // Outros métodos do serviço, como criarProduto, alterarProduto, etc.
}
