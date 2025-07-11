package tech.ada;

import java.util.UUID;

public class Usuario {

    private String id;
    private String nome;
    private Integer idade;

    public Usuario(String nome, Integer idade) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.idade = idade;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void atualizaIdade(Integer novaIdade) {
        this.idade = novaIdade;
    }
}
