/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repository;

import entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alexs
 *
 * essa inteface vai ser responsavel pela operaçoes de banco de dados do usuario
 */
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
 
}
