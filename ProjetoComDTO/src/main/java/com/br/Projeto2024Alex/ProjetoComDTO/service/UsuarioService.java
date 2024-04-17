package com.br.Projeto2024Alex.ProjetoComDTO.service;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    void salvarUsuario(UsuarioDTO usuarioDTO);

    List<UsuarioDTO> listarUsuarios();

    UsuarioDTO buscarUsuarioPorId(Long id);

    void atualizarUsuario(UsuarioDTO usuarioDTO);

    List<UsuarioDTO> pesquisarUsuarioPorNome(String nome);

    boolean existeEmail(String email);

    void mudarStatusUsuario(Long id);
}
