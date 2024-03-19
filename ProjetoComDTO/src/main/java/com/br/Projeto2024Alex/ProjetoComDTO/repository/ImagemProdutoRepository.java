package com.br.Projeto2024Alex.ProjetoComDTO.repository;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.ImagemProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ImagemProdutoRepository extends JpaRepository<ImagemProdutoEntity, Long> {
    
    ImagemProdutoEntity save(ImagemProdutoEntity imagem);
}
