package com.br.Projeto2024Alex.ProjetoComDTO.entity;

import com.br.Projeto2024Alex.ProjetoComDTO.dto.UsuarioDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import org.hibernate.validator.constraints.br.CPF;

@Entity
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, message = "O nome deve conter no mínimo 3 caracteres!",max = 100)
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "senha", nullable = false)
    private String senha;

    @NotNull
    @CPF
    @Column(name = "cpf", nullable = false)
    private String CPF;

    @NotNull
    /*permitir apenas um ou outro*/
    @Pattern(regexp = "ESTOQUISTA|ADMINISTRADOR")
    @Column(name = "grupo", nullable = false)
    private String grupo;

    @Column(nullable = false)
    private boolean status;

    public UsuarioEntity() {
    }

    public UsuarioEntity(UsuarioDTO usuarioDTO) {
        this.nome = usuarioDTO.getNome();
        this.email = usuarioDTO.getEmail();
        this.senha = usuarioDTO.getSenha();
        this.CPF = usuarioDTO.getCPF();
        this.grupo = usuarioDTO.getGrupo();
        this.status = usuarioDTO.isStatus();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioEntity other = (UsuarioEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "UsuarioEntity{"
                + "id=" + id
                + ", nome='" + nome + '\''
                + ", email='" + email + '\''
                + ", senha='" + senha + '\''
                + ", CPF='" + CPF + '\''
                + ", grupo='" + grupo + '\''
                + ", status=" + status
                + '}';
    }
}
