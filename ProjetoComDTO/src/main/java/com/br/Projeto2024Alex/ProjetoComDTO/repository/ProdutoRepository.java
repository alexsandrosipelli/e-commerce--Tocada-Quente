/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.ProdutoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author alexs
 */
@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

    // Método para buscar produtos por nome, com busca parcial
    Page<ProdutoEntity> findByNomeContainingIgnoreCase(String keyword, Pageable pageable);

    // Outros métodos de consulta personalizados, se necessário
}
