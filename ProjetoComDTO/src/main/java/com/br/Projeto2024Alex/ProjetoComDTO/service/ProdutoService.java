package com.br.Projeto2024Alex.ProjetoComDTO.service;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ProdutoDTO;
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
import com.br.Projeto2024Alex.ProjetoComDTO.dto.ImagemProdutoDTO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private static final String UPLOAD_DIR = "src/main/resources/static/imagem";

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ImagemProdutoRepository imagemProdutoRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public void editarProduto(ProdutoDTO produtoDTO, List<MultipartFile> imagens) throws IOException {
        ProdutoEntity produtoEntity = produtoRepository.findById(produtoDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + produtoDTO.getId()));

        // Atualiza todas as informações do produto com base nos dados do DTO
        produtoEntity.setNome(produtoDTO.getNome());
        produtoEntity.setAvaliacao(produtoDTO.getAvaliacao());
        produtoEntity.setDescricaoDetalhada(produtoDTO.getDescricaoDetalhada());
        produtoEntity.setPrecoProduto(produtoDTO.getPrecoProduto());
        produtoEntity.setQtdEstoque(produtoDTO.getQtdEstoque());
        produtoEntity.setStatus(produtoDTO.isStatus());

        // Se imagens foram enviadas, processa as imagens
        if (!imagens.isEmpty()) {
            // Salva as novas imagens e atualiza as imagens do produtoEntity
            salvarImagensProdutoEdicao(produtoEntity, imagens, produtoDTO.getImagemPrincipal());
        }

        // Salva as alterações no banco de dados
        produtoRepository.save(produtoEntity);
    }

    private void salvarImagensProdutoEdicao(ProdutoEntity produtoEntity, List<MultipartFile> imagens, int indiceImagemPrincipal) throws IOException {
        // Salvar as novas imagens no diretório e obter os caminhos salvos
        String[] caminhosImagens = salvarImagensEdicao(imagens);

        // Converter os caminhos das imagens em entidades ImagemProdutoEntity
        List<ImagemProdutoEntity> novasImagensEntities = Arrays.stream(caminhosImagens)
                .map(caminho -> {
                    ImagemProdutoEntity imagemEntity = new ImagemProdutoEntity();
                    imagemEntity.setCaminho(caminho);
                    imagemEntity.setProduto(produtoEntity); // Associar imagem ao produto
                    imagemEntity.setPrincipal(false); // Define todas as novas imagens como não principais inicialmente
                    return imagemEntity;
                })
                .collect(Collectors.toList());

        // Definir a nova imagem principal com base no índice fornecido
        if (indiceImagemPrincipal >= 0 && indiceImagemPrincipal < novasImagensEntities.size()) {
            novasImagensEntities.get(indiceImagemPrincipal).setPrincipal(true);
        }

        // Adicionar as novas imagens ao produtoEntity (substituir as imagens antigas)
        produtoEntity.getImagens().clear();
        produtoEntity.getImagens().addAll(novasImagensEntities);
    }

    private String[] salvarImagensEdicao(List<MultipartFile> imagens) throws IOException {
        List<String> caminhosImagens = new ArrayList<>();

        // Obtém o caminho absoluto do diretório de upload
        String absoluteUploadPath = new File(UPLOAD_DIR).getAbsolutePath();
        // Percorre cada imagem enviada
        for (MultipartFile imagem : imagens) {
            // Gera um nome único para a imagem
            String nomeImagem = System.currentTimeMillis() + "_" + imagem.getOriginalFilename();

            // Cria o caminho completo para salvar a imagem
            String caminhoImagem = absoluteUploadPath + File.separator + nomeImagem;

            // Salva a imagem no diretório
            imagem.transferTo(new File(caminhoImagem));

            // Adiciona o caminho relativo da imagem à lista de caminhos
            caminhosImagens.add(nomeImagem); // Caminho relativo para ser usado na visualização
        }

        // Converte a lista de caminhos para um array de strings
        return caminhosImagens.toArray(new String[0]);
    }

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
            // Obtém o índice da imagem principal do DTO
            int indiceImagemPrincipal = produtoDTO.getImagemPrincipal();

            // Salvar as imagens associadas ao produto e definir a imagem principal
            salvarImagensProduto(produtoEntity, imagens, indiceImagemPrincipal);
        } else {
            // Lidar com o ID nulo, se necessário
            // Aqui você pode lançar uma exceção, registrar um erro, ou realizar alguma outra ação adequada ao seu caso
            throw new IllegalArgumentException("O ID do produto não pode ser nulo após a criação.");
        }
    }

    private void salvarImagensProduto(ProdutoEntity produtoEntity, List<MultipartFile> imagens, int indiceImagemPrincipal) throws IOException {
        // Salvar as imagens no diretório e obter os caminhos salvos
        String[] caminhosImagens = salvarImagens(imagens);

        // Converter os caminhos das imagens em entidades ImagemProdutoEntity
        List<ImagemProdutoEntity> imagensEntities = Arrays.stream(caminhosImagens)
                .map(caminho -> {
                    ImagemProdutoEntity imagemEntity = new ImagemProdutoEntity();
                    imagemEntity.setCaminho(caminho);
                    imagemEntity.setProduto(produtoEntity); // Associar imagem ao produto
                    imagemEntity.setPrincipal(false); // Define todas as imagens como não principais inicialmente
                    return imagemEntity;
                })
                .collect(Collectors.toList());

        // Definir a imagem principal com base no índice fornecido
        if (indiceImagemPrincipal >= 0 && indiceImagemPrincipal < imagensEntities.size()) {
            imagensEntities.get(indiceImagemPrincipal).setPrincipal(true);
        }

        // Salvar as entidades ImagemProdutoEntity no banco de dados
        imagemProdutoRepository.saveAll(imagensEntities);
    }

    private String[] salvarImagens(List<MultipartFile> imagens) throws IOException {
        List<String> caminhosImagens = new ArrayList<>();

        // Obtém o caminho absoluto do diretório de upload
        String absoluteUploadPath = new File(UPLOAD_DIR).getAbsolutePath();
        // Percorre cada imagem enviada
        for (MultipartFile imagem : imagens) {
            // Gera um nome único para a imagem
            String nomeImagem = System.currentTimeMillis() + "_" + imagem.getOriginalFilename();

            // Cria o caminho completo para salvar a imagem
            String caminhoImagem = absoluteUploadPath + File.separator + nomeImagem;

            // Salva a imagem no diretório
            imagem.transferTo(new File(caminhoImagem));

            // Adiciona o caminho relativo da imagem à lista de caminhos
            caminhosImagens.add(nomeImagem); // Caminho relativo para ser usado na visualização

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

        for (ImagemProdutoEntity imagem : produtoEntity.getImagens()) {
            System.out.println("Caminho da imagem: " + imagem.getCaminho());
        }

        return modelMapper.map(produtoEntity, ProdutoDTO.class);
    }

}
