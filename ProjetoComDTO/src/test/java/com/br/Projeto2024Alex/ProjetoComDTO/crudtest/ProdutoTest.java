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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

@SpringBootTest
public class ProdutoTest {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private ImagemProdutoRepository imagemProdutoRepository;

    @Test
    void testeCadastrarProduto(){
        ProdutoDTO produto = new ProdutoDTO();
        ImagemProdutoDTO imgProduto = new ImagemProdutoDTO();

        produto.setNome("Produto teste1");
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

        produto.setNome("Produto teste2");
        produto.setAvaliacao(5.00);
        produto.setDescricaoDetalhada("Produto de teste para os testes unitários");
        produto.setPrecoProduto(new BigDecimal("1100.60"));
        produto.setQtdEstoque(5);
        produto.setStatus(true);
        produto.setImagemPrincipal(1);
        produto.setImagemPrincipalString("1715919637259_guitarra.jpg");

        ProdutoEntity entity = produto.toEntity();

        repository.save(entity);
        ProdutoEntity produtoEntity = repository.findById(entity.getId()).orElse(null);

        assertEquals(entity, produtoEntity);
    }

    @Test
    void testeEditarProduto(){
        ProdutoDTO produto = new ProdutoDTO();

        produto.setNome("Produto teste3");
        produto.setAvaliacao(5.00);
        produto.setDescricaoDetalhada("Produto de teste para os testes unitários");
        produto.setPrecoProduto(new BigDecimal("1100.60"));
        produto.setQtdEstoque(5);
        produto.setStatus(true);
        produto.setImagemPrincipal(1);
        produto.setImagemPrincipalString("1715919637259_guitarra.jpg");

        ProdutoEntity produtoEntity = produto.toEntity();

        repository.save(produtoEntity);

        //Atualizei o valor
        produtoEntity.setPrecoProduto(new BigDecimal("1100.70"));

        repository.save(produtoEntity);

        ProdutoEntity produtoAtualizado = repository.findById(produtoEntity.getId()).orElse(null);

        assertNotNull(produtoAtualizado);
        assertEquals(new BigDecimal("1100.70"), produtoAtualizado.getPrecoProduto());
    }

    @Test
    void testeExcluirProduto(){
        ProdutoDTO produto = new ProdutoDTO();

        produto.setNome("Produto teste4");
        produto.setAvaliacao(5.00);
        produto.setDescricaoDetalhada("Produto de teste para os testes unitários");
        produto.setPrecoProduto(new BigDecimal("1100.60"));
        produto.setQtdEstoque(5);
        produto.setStatus(true);
        produto.setImagemPrincipal(1);
        produto.setImagemPrincipalString("1715919637259_guitarra.jpg");

        ProdutoEntity produtoEntity = produto.toEntity();

        repository.save(produtoEntity);

        // Excluir o produto do banco de dados
        repository.delete(produtoEntity);

        // Verificar se o produto foi excluído com sucesso
        assertNull(repository.findById(produtoEntity.getId()).orElse(null));
    }
}
