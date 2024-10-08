/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ProdutoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author alexs
 */
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

    ProdutoEntity save(ProdutoEntity produto);

    public Page<ProdutoEntity> findByNomeContainingIgnoreCase(String keyword, Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from ProdutoEntity u where u.nome like 'Produto teste%'")
    void deleteByNomeStartingWithProdutoTeste();

    List<ProdutoEntity> findByNomeStartingWith(String produtoTeste);
}
