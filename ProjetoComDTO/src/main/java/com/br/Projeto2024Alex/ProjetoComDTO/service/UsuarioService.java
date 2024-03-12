package com.br.Projeto2024Alex.ProjetoComDTO.service;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.UsuarioDTO;
import com.br.Projeto2024Alex.ProjetoComDTO.entity.UsuarioEntity;
import com.br.Projeto2024Alex.ProjetoComDTO.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@Service
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public void salvarUsuario(UsuarioDTO usuarioDTO) {
        UsuarioEntity usuarioEntity = usuarioDTO.toEntity();
        usuarioRepository.save(usuarioEntity);
    }

    public List<UsuarioDTO> listarUsuarios() {
        List<UsuarioEntity> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(UsuarioDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public UsuarioDTO buscarUsuarioPorId(Long id) {
        Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findById(id);
        return usuarioOptional.map(UsuarioDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public void atualizarUsuario(UsuarioDTO usuarioDTO) {
        // Busca a entidade correspondente ao usuário a ser atualizado no banco de dados
        Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findById(usuarioDTO.getId());

        // Verifica se o usuário existe
        if (usuarioOptional.isPresent()) {
            UsuarioEntity usuarioEntity = usuarioOptional.get();

            // Atualiza os dados do usuário com base nos dados do DTO
            usuarioEntity.setNome(usuarioDTO.getNome());
            usuarioEntity.setCPF(usuarioDTO.getCPF());
            usuarioEntity.setGrupo(usuarioDTO.getGrupo());

            // Verifica se a senha foi fornecida pelo usuário
            if (StringUtils.hasText(usuarioDTO.getSenha())) {
                // Se a senha foi fornecida, atualiza a senha no banco de dados
                usuarioEntity.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
            }

            // Salva a entidade atualizada no banco de dados
            usuarioRepository.save(usuarioEntity);
        } else {
            // Caso o usuário não exista, lança uma exceção EntityNotFoundException
            throw new EntityNotFoundException("Usuário não encontrado");
        }
    }

    public List<UsuarioDTO> pesquisarUsuarioPorNome(String nome) {
        List<UsuarioEntity> usuarios = usuarioRepository.findByNomeContainingIgnoreCase(nome);
        return usuarios.stream()
                .map(UsuarioDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public void mudarStatusUsuario(Long id) {
        // Busca o usuário pelo ID
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        // Inverte o status do usuário
        usuario.setStatus(!usuario.isStatus());

        // Salva as alterações no banco de dados
        usuarioRepository.save(usuario);
    }

}
