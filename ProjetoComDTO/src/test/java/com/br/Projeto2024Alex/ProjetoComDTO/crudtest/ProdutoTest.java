package com.br.Projeto2024Alex.ProjetoComDTO.crudtest;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.ImagemProdutoDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.ProdutoDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.dto.UsuarioDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ImagemProdutoEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ProdutoEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.UsuarioEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.ImagemProdutoRepository;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.ProdutoRepository;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.UsuarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ProdutoTest {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private ImagemProdutoRepository imagemProdutoRepository;

    @BeforeEach
    @AfterEach
    void limparDadosDeTeste() {
        List<ProdutoEntity> produtos = repository.findByNomeStartingWith("Produto Teste");
        repository.deleteAll(produtos);
    }

    @Test
    void testeCadastrarProduto(){
        ProdutoDTO produto = new ProdutoDTO();
        ImagemProdutoDTO imgProduto = new ImagemProdutoDTO();

        produto.setNome("Produto Teste1");
        produto.setAvaliacao(5.00);
        produto.setDescricaoDetalhada("Produto de teste para os testes unitários");
        produto.setPrecoProduto(new BigDecimal("1100.60"));
        produto.setQtdEstoque(5);
        produto.setStatus(true);
        produto.setImagemPrincipal(1);
        produto.setImagemPrincipalString("1715919637259_guitarra.jpg");

        ProdutoEntity produtoEntity = produto.toEntity();

        repository.save(produtoEntity);

        imgProduto.setCaminho("1715919637259_guitarra.jpg");
        imgProduto.setPrincipal(true);
        imgProduto.setProdutoId(produtoEntity.getId());

        ImagemProdutoEntity imgProdutoEntity = imgProduto.toEntity();

        imagemProdutoRepository.save(imgProdutoEntity);

        assertNotNull(produtoEntity.getId());
        assertNotNull(imgProdutoEntity.getId());
    }

    @Test
    void testeBuscarProduto(){
        ProdutoDTO produto = new ProdutoDTO();

        produto.setNome("Produto Teste2");
        produto.setAvaliacao(5.00);
        produto.setDescricaoDetalhada("Produto de teste para os testes unitários");
        produto.setPrecoProduto(new BigDecimal("1100.60"));
        produto.setQtdEstoque(5);
        produto.setStatus(true);

        ProdutoEntity produtoEntity = produto.toEntity();

        repository.save(produtoEntity);

        ProdutoEntity produtoEntityBusca = repository.findById(produtoEntity.getId()).orElse(null);
        assertNotNull(produtoEntityBusca, "O produto não foi encontrado após seu cadastro");
        assertEquals(produtoEntity.getId(), produtoEntityBusca.getId(), "O produto não foi cadastrado corretamente");
    }

    @Test
    void testeEditarProduto() {
        // Configuração do ProdutoDTO
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Produto Teste3");
        produtoDTO.setAvaliacao(5.00);
        produtoDTO.setDescricaoDetalhada("Produto de teste para os testes unitários");
        produtoDTO.setPrecoProduto(new BigDecimal("1100.60"));
        produtoDTO.setQtdEstoque(5);
        produtoDTO.setStatus(true);

        // Conversão para ProdutoEntity e salvamento inicial
        ProdutoEntity produtoEntity = produtoDTO.toEntity();
        repository.save(produtoEntity);

        // Verifica se o produto foi salvo corretamente
        ProdutoEntity produtoSalvo = repository.findById(produtoEntity.getId()).orElse(null);
        assertNotNull(produtoSalvo, "O produto não foi encontrado após salvar");
        assertEquals(new BigDecimal("1100.60"), produtoSalvo.getPrecoProduto(), "O preço do produto não foi salvo corretamente");

        // Atualização do valor do produto
        produtoSalvo.setPrecoProduto(new BigDecimal("1100.70"));

        // Salvamento da atualização
        repository.save(produtoSalvo);

        // Verificação se o produto foi atualizado corretamente
        ProdutoEntity produtoAtualizado = repository.findById(produtoSalvo.getId()).orElse(null);
        assertNotNull(produtoAtualizado, "O produto não foi encontrado após a atualização");
        assertEquals(new BigDecimal("1100.70"), produtoAtualizado.getPrecoProduto(), "O preço do produto não foi atualizado corretamente");
    }


    @Test
    void testeExcluirProduto(){
        ProdutoDTO produto = new ProdutoDTO();

        produto.setNome("Produto Teste4");
        produto.setAvaliacao(5.00);
        produto.setDescricaoDetalhada("Produto de teste para os testes unitários");
        produto.setPrecoProduto(new BigDecimal("1100.60"));
        produto.setQtdEstoque(5);
        produto.setStatus(true);

        ProdutoEntity produtoEntity = produto.toEntity();

        repository.save(produtoEntity);

        // Excluir o produto do banco de dados
        repository.delete(produtoEntity);

        // Verificar se o produto foi excluído com sucesso
        assertNull(repository.findById(produtoEntity.getId()).orElse(null), "Produto não excluído corretamene");
    }
}
