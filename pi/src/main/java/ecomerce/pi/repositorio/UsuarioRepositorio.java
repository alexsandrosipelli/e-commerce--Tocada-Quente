/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ecomerce.pi.repositorio;

import ecomerce.pi.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author hiago
 */
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{
    
}
