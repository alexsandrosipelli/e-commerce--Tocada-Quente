package com.br.Projeto2024Alex.ProjetoComDTO.repository;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.ImagemProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ImagemProdutoRepository extends JpaRepository<ImagemProdutoEntity, Long> {
    
    ImagemProdutoEntity save(ImagemProdutoEntity imagem);

    public Object findByProdutoIdAndPrincipalTrue(Long id);

    @Transactional
    @Modifying
    @Query("delete from ImagemProdutoEntity i where i.caminho like '1715919637259_guitarra%'")
    void deleteByCaminhoStartingWithImagemTeste();
}
