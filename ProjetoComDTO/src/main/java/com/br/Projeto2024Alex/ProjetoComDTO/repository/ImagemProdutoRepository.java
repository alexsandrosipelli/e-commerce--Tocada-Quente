package com.br.Projeto2024Alex.ProjetoComDTO.repository;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.ImagemProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagemProdutoRepository extends JpaRepository<ImagemProdutoEntity, Long> {
    
    ImagemProdutoEntity save(ImagemProdutoEntity imagem);

    public Object findByProdutoIdAndPrincipalTrue(Long id);
}
