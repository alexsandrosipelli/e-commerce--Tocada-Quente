/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.br.Projeto2024Alex.ProjetoComDTO.repository;

import com.br.Projeto2024Alex.ProjetoComDTO.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alexs
 */
public interface ImagemProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

}
