package br.edu.ufam.model;

public class ComandaProdutoModel {
    private int id;
    private int quantidade;
    private ComandaModel comanda;
    private ProdutoModel produto;

    public ComandaProdutoModel(int id, int quantidade, ComandaModel comanda, ProdutoModel produto) {
        this.id = id;
        this.quantidade = quantidade;
        this.comanda = comanda;
        this.produto = produto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public ComandaModel getComanda() {
        return comanda;
    }

    public void setIdComanda(ComandaModel comanda) {
        this.comanda = comanda;
    }

    public ProdutoModel getProduto() {
        return produto;
    }

    public void setIdProduto(ProdutoModel produto) {
        this.produto = produto;
    }
}
