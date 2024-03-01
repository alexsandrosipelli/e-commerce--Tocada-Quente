/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import dto.UsuarioDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import java.util.Objects;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author alexs
 */
@Entity
@Table(name = "USUARIO")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String CPF;

    @Column(nullable = false)
    private String grupo;

    @Column(nullable = false)
    private boolean status;

    public UsuarioEntity() {
    }

    public UsuarioEntity(UsuarioDTO usuario) {
        BeanUtils.copyProperties(usuario, this);
    }

    ;


    /* o codigo aabaixo  
    Nesse trecho de código, está sendo definido o método hashCode() e o método equals(Object obj) para a classe UsuarioEntity. Esses métodos são comumente implementados em classes que são usadas em estruturas de dados que dependem de comparação de igualdade, como HashMaps e HashSet.

hashCode(): Este método retorna um valor numérico que representa o hash code do objeto. O hash code é um valor inteiro usado por algumas estruturas de dados para indexação rápida e comparação de objetos. No método hashCode(), é gerado um hash code baseado no ID do usuário (id). Se o ID for nulo, o valor retornado será 0.

equals(Object obj): Este método é usado para verificar se dois objetos são iguais. Ele recebe um objeto como parâmetro e retorna true se o objeto atual for igual ao objeto passado como parâmetro. Caso contrário, retorna false. No método equals(), primeiro verifica-se se os dois objetos são a mesma instância (ou seja, referenciam o mesmo endereço de memória). Se forem a mesma instância, retorna-se true imediatamente. Se o objeto passado como parâmetro for nulo ou não for uma instância da classe UsuarioEntity, retorna-se false. Caso contrário, compara-se os IDs dos dois objetos. Se os IDs forem iguais, retorna-se true; caso contrário, retorna-se false.

Essa implementação de hashCode() e equals() garante que dois objetos UsuarioEntity serão considerados iguais se tiverem o mesmo ID. Isso é útil ao trabalhar com coleções como HashSet, onde você deseja garantir que não haja duplicatas com base em alguma propriedade específica do objeto.
    */
    
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

}
