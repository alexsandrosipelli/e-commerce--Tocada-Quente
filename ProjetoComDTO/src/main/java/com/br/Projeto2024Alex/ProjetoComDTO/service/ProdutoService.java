package com.br.Projeto2024Alex.ProjetoComDTO.service;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ProdutoDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.ImagemProdutoDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ProdutoEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ImagemProdutoEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.ProdutoRepository;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.ImagemProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private static final String UPLOAD_DIR = "src/main/resources/imagem";

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ImagemProdutoRepository imagemProdutoRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public Page<ProdutoDTO> listarProdutosPorNomePaginado(String keyword, Pageable pageable) {
        Page<ProdutoEntity> produtosPage;
        if (keyword != null && !keyword.isEmpty()) {
            produtosPage = produtoRepository.findByNomeContainingIgnoreCase(keyword, pageable);
        } else {
            produtosPage = produtoRepository.findAll(pageable);
        }
        return produtosPage.map(this::toDTO);
    }

    @Transactional
    public void criarProduto(ProdutoDTO produtoDTO, List<MultipartFile> imagens) throws IOException {
        // Salvar o produto primeiro para obter o ID gerado automaticamente
        ProdutoEntity produtoEntity = produtoRepository.save(produtoDTO.toEntity());

        // Verificar se o ID do produto não é nulo após salvá-lo
        if (produtoEntity.getId() != null) {
            // Salvar as imagens associadas ao produto
            salvarImagensProduto(produtoEntity, imagens);
        } else {
            // Lidar com o ID nulo, se necessário
            // Aqui você pode lançar uma exceção, registrar um erro, ou realizar alguma outra ação adequada ao seu caso
            throw new IllegalArgumentException("O ID do produto não pode ser nulo após a criação.");
        }
    }

    private void salvarImagensProduto(ProdutoEntity produtoEntity, List<MultipartFile> imagens) throws IOException {
        // Salvar as imagens no diretório e obter os caminhos salvos
        String[] caminhosImagens = salvarImagens(imagens);

        // Converter os caminhos das imagens em entidades ImagemProdutoEntity
        List<ImagemProdutoEntity> imagensEntities = Arrays.stream(caminhosImagens)
                .map(caminho -> {
                    ImagemProdutoEntity imagemEntity = new ImagemProdutoEntity();
                    imagemEntity.setCaminho(caminho);
                    imagemEntity.setProduto(produtoEntity); // Associar imagem ao produto

                    return imagemEntity;
                })
                .collect(Collectors.toList());

        // Salvar as entidades ImagemProdutoEntity no banco de dados
        imagemProdutoRepository.saveAll(imagensEntities);
    }

    private String[] salvarImagens(List<MultipartFile> imagens) throws IOException {
        List<String> caminhosImagens = new ArrayList<>();

        // Obtém o caminho absoluto do diretório de upload
        String absoluteUploadPath = new File(UPLOAD_DIR).getAbsolutePath();

        // Verifica se o diretório de upload existe e cria se necessário
        File uploadDir = new File(absoluteUploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Cria o diretório e subdiretórios se necessário
        }

        // Percorre cada imagem enviada
        for (MultipartFile imagem : imagens) {
            // Gera um nome único para a imagem
            String nomeImagem = System.currentTimeMillis() + "_" + imagem.getOriginalFilename();

            // Cria o caminho completo para salvar a imagem
            String caminhoImagem = absoluteUploadPath + File.separator + nomeImagem;

            // Salva a imagem no diretório
            imagem.transferTo(new File(caminhoImagem));

            // Adiciona o caminho relativo da imagem à lista de caminhos
            caminhosImagens.add("imagens/" + nomeImagem); // Caminho relativo para ser usado na visualização

            // Adiciona o caminho da imagem à lista de caminhos
            caminhosImagens.add(caminhoImagem);

        }

        // Converte a lista de caminhos para um array de strings
        return caminhosImagens.toArray(new String[0]);
    }

    private ProdutoDTO toDTO(ProdutoEntity produtoEntity) {
        return modelMapper.map(produtoEntity, ProdutoDTO.class);
    }

    @Transactional
    public void mudarStatusProduto(Long id) {
        ProdutoEntity produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));

        // Altera o status do produto
        produto.setStatus(!produto.isStatus());

        // Salva as alterações no banco de dados
        produtoRepository.save(produto);
    }

    public ProdutoDTO buscarProdutoPorId(Long id) {
        ProdutoEntity produtoEntity = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com o ID: " + id));

        return modelMapper.map(produtoEntity, ProdutoDTO.class);
    }

}
