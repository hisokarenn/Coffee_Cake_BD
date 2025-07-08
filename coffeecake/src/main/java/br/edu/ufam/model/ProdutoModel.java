package br.edu.ufam.model;

public class ProdutoModel {
    private int id;
    private String nome;
    private float preco;
    private String descricao;
    private int quantidade;

    public ProdutoModel(int id, String nome, float preco, String descricao, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void SetDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void SetQuantidade(int quantidade) {
        this.quantidade = quantidade;
    } 
}
