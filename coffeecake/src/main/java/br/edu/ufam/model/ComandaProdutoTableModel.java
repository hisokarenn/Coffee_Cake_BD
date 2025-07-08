package br.edu.ufam.model;

public class ComandaProdutoTableModel {
    private int id;
    private String nome;
    private int quantidade;
    private float preco;

    public ComandaProdutoTableModel(int id, String nome, int quantidade, float preco) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
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

    public void setNome(String nomeProduto) {
        this.nome = nomeProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float precoUnitario) {
        this.preco = precoUnitario;
    }

}
