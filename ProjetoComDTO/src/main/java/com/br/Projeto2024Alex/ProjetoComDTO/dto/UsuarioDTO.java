package com.br.Projeto2024Alex.ProjetoComDTO.dto;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

public class UsuarioDTO {

    private Long id;
    private String nome;
    @Email
    private String email;
    @CPF
    private String CPF;
    private String grupo;
    private boolean status;
    private String senha;
    private String confirmacaoSenha; // Adicionando o campo confirmacaoSenha

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }

    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }

    public boolean isNomeValido() {
        return StringUtils.hasText(nome) && nome.matches("^[a-zA-Z\\s]+$");
    }

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String nome, String email, String CPF, String grupo, boolean status) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.CPF = CPF;
        this.grupo = grupo;
        this.status = status;
    }

    public UsuarioDTO(UsuarioDTO other) {
        this.id = other.id;
        this.nome = other.nome;
        this.email = other.email;
        this.CPF = other.CPF;
        this.grupo = other.grupo;
        this.status = other.status;
    }

    
    public static UsuarioDTO fromEntity(com.br.Projeto2024Alex.ProjetoComDTO.entity.UsuarioEntity usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        BeanUtils.copyProperties(usuario, usuarioDTO);
        return usuarioDTO;
    }

    public com.br.Projeto2024Alex.ProjetoComDTO.entity.UsuarioEntity toEntity() {
        com.br.Projeto2024Alex.ProjetoComDTO.entity.UsuarioEntity usuarioEntity = new com.br.Projeto2024Alex.ProjetoComDTO.entity.UsuarioEntity();
        BeanUtils.copyProperties(this, usuarioEntity);
        return usuarioEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
