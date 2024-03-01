/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dto.UsuarioDTO;
import entity.UsuarioEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UsuarioRepository;

/**
 *
 * @author alexs
 classe UsuarioService
 * 
 */
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
            
    public List<UsuarioDTO>listarTodos(){
    List<UsuarioEntity> usuarios=usuarioRepository.findAll();
    return usuarios.stream().map(UsuarioDTO::new).toList();
    }
    
    public void inserir (UsuarioDTO usuario){
    
    UsuarioEntity usuarioEntity= new UsuarioEntity(usuario);
    usuarioRepository.save(usuarioEntity);
    
    }
    public UsuarioDTO alterar (UsuarioDTO usuario){
    UsuarioEntity usuarioEntity= new UsuarioEntity(usuario);
    return new UsuarioDTO(usuarioRepository.save(usuarioEntity));
    
    }
    public  void excluir (Long id){
    
    UsuarioEntity usuario=usuarioRepository.findById(id).get();
    usuarioRepository.delete(usuario);
    
    }
    public UsuarioDTO buscarPorId(Long id){
    
    return new UsuarioDTO(usuarioRepository.findById(id).get());
    }
}
